package ruleta.com.subastas.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruleta.com.subastas.entities.Evento;
import ruleta.com.subastas.repositories.IEventoRepository;
import ruleta.com.subastas.serviceinterfaces.IEventoService;

import java.util.List;

@Service
public class EventoServiceImpl implements IEventoService {

    @Autowired
    private IEventoRepository eventoRepo;

    @Override
    public void insert(Evento evento) {
        eventoRepo.save(evento);
    }

    @Override
    public List<Evento> list() {
        return eventoRepo.findAll();
    }

    @Override
    public Evento listId(Long id) {
        return eventoRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        eventoRepo.deleteById(id);
    }
}
