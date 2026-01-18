package ruleta.com.subastas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.entities.Subasta;

import java.util.List;

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByEstado(String estado);
    List<Subasta> findByEventoIdAndEstado(Long eventoId, String estado);
}