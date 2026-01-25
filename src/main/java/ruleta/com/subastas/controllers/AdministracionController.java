package ruleta.com.subastas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.dtos.SubastaDTO;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.entities.Subasta;
import ruleta.com.subastas.repositories.IEventoRepository;
import ruleta.com.subastas.serviceinterfaces.ISubastaService;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class AdministracionController {

    @Autowired
    private IEventoRepository eventoRepo;

    @Autowired
    private ISubastaService subastaService;


    @GetMapping("/subastas/evento/{eventoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubastaDTO> listarSubastasEventoAdmin(@PathVariable Long eventoId) {

        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new org.modelmapper.PropertyMap<Subasta, SubastaDTO>() {
            @Override
            protected void configure() {
                // Info subasta
                map().setNumeroSubasta(source.getNumeroSubasta());
                map().setEstado(source.getEstado());
                map().setHoraInicioAsignada(source.getHoraInicioAsignada());
                map().setHoraFinAsignada(source.getHoraFinAsignada());
                map().setPlanta(source.getPlanta());
                map().setMaceta(source.getMaceta());
                map().setPrecioBase(source.getPrecioBase());
                map().setObservaciones(source.getObservaciones());

                // Info evento
                map().setEventoId(source.getEvento().getId());
                map().setEventoNombre(source.getEvento().getNombre());
                map().setFechaEvento(source.getEvento().getFechaEvento());
                map().setHoraInicio(source.getEvento().getHoraInicio());
                map().setDuracionSubastaMinutos(source.getEvento().getDuracionSubastaMinutos());
                map().setDescansoMinutos(source.getEvento().getDescansoMinutos());

                // Info usuario (creador)
                map().setUserId(source.getUser().getId());
                map().setUsername(source.getUser().getName());
                map().setPhone(source.getUser().getPhone());
                map().setCity(source.getUser().getCity());
            }
        });

        return subastaService.findByEventoId(eventoId)
                .stream()
                .map(s -> mapper.map(s, SubastaDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/subastas/{id}/decision/{estado}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void decidirSubasta(@PathVariable Long id,
                               @PathVariable String estado) {

        Subasta s = subastaService.listId(id);

        if (s == null) {
            throw new RuntimeException("Subasta no encontrada");
        }

        if (!estado.equalsIgnoreCase("ACEPTADA") &&
                !estado.equalsIgnoreCase("RECHAZADA")) {
            throw new RuntimeException("Estado inválido");
        }

        s.setEstado(estado.toUpperCase());
        subastaService.insert(s);
    }

    @GetMapping("/subastas/aceptadas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubastaDTO> listarSubastasAceptadas() {

        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new org.modelmapper.PropertyMap<Subasta, SubastaDTO>() {
            @Override
            protected void configure() {
                // Info subasta
                map().setNumeroSubasta(source.getNumeroSubasta());
                map().setEstado(source.getEstado());
                map().setHoraInicioAsignada(source.getHoraInicioAsignada());
                map().setHoraFinAsignada(source.getHoraFinAsignada());
                map().setPlanta(source.getPlanta());
                map().setMaceta(source.getMaceta());
                map().setPrecioBase(source.getPrecioBase());
                map().setObservaciones(source.getObservaciones());

                // Info evento
                map().setEventoId(source.getEvento().getId());
                map().setEventoNombre(source.getEvento().getNombre());
                map().setFechaEvento(source.getEvento().getFechaEvento());
                map().setHoraInicio(source.getEvento().getHoraInicio());
                map().setDuracionSubastaMinutos(source.getEvento().getDuracionSubastaMinutos());
                map().setDescansoMinutos(source.getEvento().getDescansoMinutos());

                // Info usuario
                map().setUserId(source.getUser().getId());
                map().setUsername(source.getUser().getName());
                map().setPhone(source.getUser().getPhone());
                map().setCity(source.getUser().getCity());
            }
        });

        return subastaService.findByEstado("ACEPTADA")
                .stream()
                .map(s -> mapper.map(s, SubastaDTO.class))
                .collect(Collectors.toList());
    }

    // =================== ORGANIZAR SUBASTAS ===================
    @PostMapping("/organizar-subastas/{eventoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void organizarSubastas(@PathVariable Long eventoId) {
        Evento evento = eventoRepo.findById(eventoId).orElseThrow(
                () -> new RuntimeException("Evento no encontrado")
        );

        // Solo se puede organizar si el evento está cerrado
        if (!evento.getEstado().equalsIgnoreCase("CERRADO")) {
            throw new RuntimeException("El evento debe estar cerrado para organizar las subastas.");
        }

        // Obtener subastas aceptadas
        List<Subasta> aceptadas = subastaService.findByEventoIdAndEstado(eventoId, "ACEPTADA");

        if (aceptadas.isEmpty()) {
            throw new RuntimeException("No hay subastas aceptadas para este evento");
        }

        // Mezclar aleatoriamente (intercaladas)
        Collections.shuffle(aceptadas);

        LocalTime horaActual = evento.getHoraInicio();
        int duracion = evento.getDuracionSubastaMinutos();
        int descanso = evento.getDescansoMinutos();

        for (int i = 0; i < aceptadas.size(); i++) {
            Subasta s = aceptadas.get(i);
            s.setNumeroSubasta(i + 1);
            s.setHoraInicioAsignada(horaActual);
            s.setHoraFinAsignada(horaActual.plusMinutes(duracion));

            // Actualizar hora para la siguiente subasta
            horaActual = horaActual.plusMinutes(duracion + descanso);

            // Guardar cambios
            subastaService.insert(s);
        }
    }
}
