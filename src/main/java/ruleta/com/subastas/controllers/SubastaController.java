package ruleta.com.subastas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ruleta.com.subastas.controllers.cloudinary.CloudinaryService;
import ruleta.com.subastas.dtos.SubastaDTO;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.entities.Subasta;
import ruleta.com.subastas.repositories.IEventoRepository;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;
import ruleta.com.subastas.serviceinterfaces.ISubastaService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subastas")
@CrossOrigin
public class SubastaController {

    @Autowired
    private ISubastaService subastaService;

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private IEventoRepository eventoRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR')")
    public void crear(
            @RequestPart(value = "archivo", required = false) MultipartFile archivo,
            @RequestPart("dto") SubastaDTO dto
    ) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Evento evento = eventoRepo.findById(dto.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        ModelMapper mapper = new ModelMapper();
        Subasta s = mapper.map(dto, Subasta.class);

        s.setUser(user);
        s.setEvento(evento);
        s.setEstado("PENDIENTE");

        if (archivo != null && !archivo.isEmpty()) {
            Map<String, String> upload = cloudinaryService.uploadFile(archivo, "subastas");
            s.setImagen(upload.get("url"));
            s.setImagenId(upload.get("public_id"));
        }

        subastaService.insert(s);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR')")
    public void actualizar(
            @PathVariable Long id,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo,
            @RequestPart("dto") SubastaDTO dto
    ) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Subasta existente = subastaService.listId(id);
        if (existente == null || !existente.getUser().getId().equals(user.getId())) {
            return;
        }

        ModelMapper mapper = new ModelMapper();
        Subasta actualizada = mapper.map(dto, Subasta.class);

        actualizada.setId(id);
        actualizada.setUser(user);
        actualizada.setEvento(existente.getEvento());
        actualizada.setEstado(existente.getEstado());

        if (archivo != null && !archivo.isEmpty()) {
            if (existente.getImagenId() != null) {
                cloudinaryService.deleteFile(existente.getImagenId());
            }
            Map<String, String> upload = cloudinaryService.uploadFile(archivo, "subastas");
            actualizada.setImagen(upload.get("url"));
            actualizada.setImagenId(upload.get("public_id"));
        } else {
            actualizada.setImagen(existente.getImagen());
            actualizada.setImagenId(existente.getImagenId());
        }

        subastaService.insert(actualizada);
    }

    @GetMapping("/mis-subastas")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR')")
    public List<SubastaDTO> misSubastas() {

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new org.modelmapper.PropertyMap<Subasta, SubastaDTO>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId());
                map().setUsername(source.getUser().getName());
                map().setPhone(source.getUser().getPhone());
                map().setCity(source.getUser().getCity());
                map().setEventoId(source.getEvento().getId());
                map().setEventoNombre(source.getEvento().getNombre());
                map().setFechaEvento(source.getEvento().getFechaEvento());
                map().setHoraInicio(source.getEvento().getHoraInicio());
                map().setDuracionSubastaMinutos(source.getEvento().getDuracionSubastaMinutos());
                map().setDescansoMinutos(source.getEvento().getDescansoMinutos());
                map().setNumeroSubasta(source.getNumeroSubasta());
                map().setHoraInicioAsignada(source.getHoraInicioAsignada());
                map().setHoraFinAsignada(source.getHoraFinAsignada());
            }
        });

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        return subastaService.findByUserId(user.getId())
                .stream()
                .map(s -> mapper.map(s, SubastaDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR')")
    public SubastaDTO listarId(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Subasta s = subastaService.listId(id);
        if (s != null && s.getUser().getId().equals(user.getId())) {
            return new ModelMapper().map(s, SubastaDTO.class);
        }
        return null;
    }

    @PutMapping("/{id}/estado/{estado}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void cambiarEstado(@PathVariable Long id, @PathVariable String estado) {
        Subasta s = subastaService.listId(id);
        if (s != null) {
            s.setEstado(estado);
            subastaService.insert(s);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR')")
    public void eliminar(@PathVariable Long id) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Subasta s = subastaService.listId(id);
        if (s != null && s.getUser().getId().equals(user.getId())) {
            if (s.getImagenId() != null) {
                cloudinaryService.deleteFile(s.getImagenId());
            }
            subastaService.delete(id);
        }
    }
}
