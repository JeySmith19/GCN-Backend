package ruleta.com.subastas.serviceinterfaces;

import ruleta.com.subastas.dtos.EventoDTO;
import ruleta.com.subastas.entities.Evento;

import java.util.List;

public interface IEventoService {

    void insert(Evento evento);
    List<Evento> list();
    Evento listId(Long id);
    void delete(Long id);
}
