package ruleta.com.subastas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.entities.Subasta;
import ruleta.com.subastas.repositories.IEventoRepository;
import ruleta.com.subastas.serviceinterfaces.ISubastaService;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdministracionController {

    @Autowired
    private IEventoRepository eventoRepo;

    @Autowired
    private ISubastaService subastaService;

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
