package ruleta.com.subastas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.dtos.SubastaDTO;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.entities.Subasta;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;
import ruleta.com.subastas.repositories.IEventoRepository;
import ruleta.com.subastas.serviceinterfaces.ISubastaService;

import java.util.List;
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

    // =================== CREAR ===================
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void crear(@RequestBody SubastaDTO dto) {
        ModelMapper mapper = new ModelMapper();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();

        Users user = userRepo.findByUsername(username);
        Evento evento = eventoRepo.findById(dto.getEventoId()).orElseThrow();

        Subasta s = mapper.map(dto, Subasta.class);
        s.setUser(user);
        s.setEvento(evento);
        s.setEstado("PENDIENTE");

        subastaService.insert(s);
    }

    // =================== LISTAR MIS SUBASTAS ===================
    @GetMapping("/mis-subastas")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<SubastaDTO> misSubastas() {

        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new org.modelmapper.PropertyMap<Subasta, SubastaDTO>() {
            @Override
            protected void configure() {
                // Info del usuario
                map().setUserId(source.getUser().getId());
                map().setUsername(source.getUser().getName());
                map().setPhone(source.getUser().getPhone());
                map().setCity(source.getUser().getCity());

                // Info del evento
                map().setEventoId(source.getEvento().getId());
                map().setEventoNombre(source.getEvento().getNombre());
                map().setFechaEvento(source.getEvento().getFechaEvento());
                map().setHoraInicio(source.getEvento().getHoraInicio());
                map().setDuracionSubastaMinutos(source.getEvento().getDuracionSubastaMinutos());
                map().setDescansoMinutos(source.getEvento().getDescansoMinutos());

                // Info de la subasta
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



    // =================== LISTAR POR ID (para editar) ===================
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public SubastaDTO listarId(@PathVariable Long id) {

        ModelMapper mapper = new ModelMapper();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Subasta s = subastaService.listId(id);

        if (s != null && s.getUser().getId().equals(user.getId())) {
            return mapper.map(s, SubastaDTO.class);
        }
        return null;
    }

    // =================== ACTUALIZAR ===================
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void actualizar(@PathVariable Long id, @RequestBody SubastaDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Subasta existente = subastaService.listId(id);

        if (existente != null && existente.getUser().getId().equals(user.getId())) {
            Subasta actualizada = mapper.map(dto, Subasta.class);
            actualizada.setId(id); // asegurarse de usar el mismo id
            actualizada.setUser(user);
            actualizada.setEvento(existente.getEvento());
            actualizada.setEstado(existente.getEstado());

            subastaService.insert(actualizada);
        }
    }

    // =================== CAMBIAR ESTADO ===================
    @PutMapping("/{id}/estado/{estado}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void cambiarEstado(@PathVariable Long id,
                              @PathVariable String estado) {

        Subasta s = subastaService.listId(id);
        if (s != null) {
            s.setEstado(estado);
            subastaService.insert(s);
        }
    }

    // =================== ELIMINAR ===================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        Subasta s = subastaService.listId(id);

        if (s != null && s.getUser().getId().equals(user.getId())) {
            subastaService.delete(id);
        }
    }

    @GetMapping("/evento/{eventoId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<SubastaDTO> listarPorEvento(@PathVariable Long eventoId) {

        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new org.modelmapper.PropertyMap<Subasta, SubastaDTO>() {
            @Override
            protected void configure() {
                map().setEventoId(source.getEvento().getId());
                map().setNumeroSubasta(source.getNumeroSubasta());
                map().setEstado(source.getEstado());
                map().setHoraInicioAsignada(source.getHoraInicioAsignada());
                map().setHoraFinAsignada(source.getHoraFinAsignada());
            }
        });

        return subastaService.findByEventoId(eventoId)
                .stream()
                .map(s -> mapper.map(s, SubastaDTO.class))
                .collect(Collectors.toList());
    }

}
