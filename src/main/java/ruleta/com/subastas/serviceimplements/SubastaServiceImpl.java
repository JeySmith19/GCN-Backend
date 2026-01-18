package ruleta.com.subastas.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruleta.com.subastas.entities.Subasta;
import ruleta.com.subastas.repositories.ISubastaRepository;
import ruleta.com.subastas.serviceinterfaces.ISubastaService;

import java.util.List;

@Service
public class SubastaServiceImpl implements ISubastaService {

    @Autowired
    private ISubastaRepository subastaRepo;

    @Override
    public void insert(Subasta subasta) {
        subastaRepo.save(subasta);
    }

    @Override
    public List<Subasta> list() {
        return subastaRepo.findAll();
    }

    @Override
    public Subasta listId(Long id) {
        return subastaRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        subastaRepo.deleteById(id);
    }

    @Override
    public List<Subasta> findByEventoId(Long eventoId) {
        return subastaRepo.findByEventoId(eventoId);
    }

    @Override
    public List<Subasta> findByUserId(Long userId) {
        return subastaRepo.findByUserId(userId);
    }

    @Override
    public List<Subasta> findByEstado(String estado) {
        return subastaRepo.findByEstado(estado);
    }

    @Override
    public List<Subasta> findByEventoIdAndEstado(Long eventoId, String estado) {
        return subastaRepo.findByEventoIdAndEstado(eventoId, estado);
    }

}
