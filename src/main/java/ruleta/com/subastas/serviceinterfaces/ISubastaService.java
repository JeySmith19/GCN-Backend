package ruleta.com.subastas.serviceinterfaces;
import ruleta.com.subastas.entities.Subasta;

import java.util.List;

public interface ISubastaService {

    void insert(Subasta subasta);
    List<Subasta> list();
    Subasta listId(Long id);
    void delete(Long id);

    List<Subasta> findByEventoId(Long eventoId);
    List<Subasta> findByUserId(Long userId);
    List<Subasta> findByEstado(String estado);
    List<Subasta> findByEventoIdAndEstado(Long eventoId, String estado);
    List<Subasta> findByUserIdAndEventoId(Long userId, Long eventoId);
}