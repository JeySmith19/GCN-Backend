package ruleta.com.subastas.serviceinterfaces;

import ruleta.com.subastas.entities.ListaNegra;

import java.util.List;

public interface IListaNegraService {
    void insert(ListaNegra listaNegra);
    List<ListaNegra> list();
    ListaNegra listId(Long id);
    void delete(Long id);
    ListaNegra findByPhone(String phone);
}
