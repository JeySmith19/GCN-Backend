package ruleta.com.subastas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruleta.com.subastas.entities.Subasta;

import java.util.List;

@Repository
public interface ISubastaRepository extends JpaRepository<Subasta, Long> {

    List<Subasta> findByUserId(Long userId);
    List<Subasta> findByEventoId(Long eventoId);
    List<Subasta> findByEstado(String estado);
    List<Subasta> findByEventoIdAndEstado(Long eventoId, String estado);
    List<Subasta> findByUserIdAndEventoId(Long userId, Long eventoId);

}