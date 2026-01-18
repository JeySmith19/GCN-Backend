package ruleta.com.subastas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.dtos.EventoDTO;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.serviceinterfaces.IEventoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
@CrossOrigin
public class EventoController {

    @Autowired
    private IEventoService eventoService;

    @PostMapping
    public void crear(@RequestBody EventoDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Evento evento = mapper.map(dto, Evento.class);
        evento.setEstado("ABIERTO");
        eventoService.insert(evento);
    }

    @GetMapping
    public List<EventoDTO> listar() {
        ModelMapper mapper = new ModelMapper();
        return eventoService.list().stream()
                .map(e -> mapper.map(e, EventoDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventoDTO listarId(@PathVariable Long id) {
        Evento evento = eventoService.listId(id);
        if (evento == null) return null;

        ModelMapper mapper = new ModelMapper();
        return mapper.map(evento, EventoDTO.class);
    }

    @PutMapping
    public void actualizar(@RequestBody EventoDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Evento evento = mapper.map(dto, Evento.class);
        eventoService.insert(evento);
    }

    @PutMapping("/{id}/auto-cerrar")
    public void cerrarAutomaticamente(@PathVariable Long id) {
        Evento evento = eventoService.listId(id);
        if (evento != null) {

            LocalDateTime inicio = LocalDateTime.of(
                    evento.getFechaEvento(),
                    evento.getHoraInicio()
            );

            // 1 hora antes
            LocalDateTime cierreProgramado = inicio.minusHours(1);

            if (LocalDateTime.now().isAfter(cierreProgramado)) {
                evento.setEstado("CERRADO");
                eventoService.insert(evento);
            }
        }
    }


    @PutMapping("/{id}/estado/{estado}")
    public void cambiarEstado(@PathVariable Long id, @PathVariable String estado) {
        Evento evento = eventoService.listId(id);
        if (evento != null) {
            evento.setEstado(estado);
            eventoService.insert(evento);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        eventoService.delete(id);
    }
}
