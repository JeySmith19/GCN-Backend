package ruleta.com.subastas.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruleta.com.subastas.entities.ListaNegra;
import ruleta.com.subastas.repositories.IListaNegraRepository;
import ruleta.com.subastas.serviceinterfaces.IListaNegraService;

import java.util.List;

@Service
public class ListaNegraServiceImpl implements IListaNegraService {

    @Autowired
    private IListaNegraRepository listaNegraRepo;

    @Override
    public void insert(ListaNegra listaNegra) {
        listaNegraRepo.save(listaNegra);
    }

    @Override
    public List<ListaNegra> list() {
        return listaNegraRepo.findAll();
    }

    @Override
    public ListaNegra listId(Long id) {
        return listaNegraRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        listaNegraRepo.deleteById(id);
    }

    @Override
    public ListaNegra findByPhone(String phone) {
        return listaNegraRepo.findByPhone(phone);
    }
}
