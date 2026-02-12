package ruleta.com.subastas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruleta.com.subastas.entities.ListaNegra;

@Repository
public interface IListaNegraRepository extends JpaRepository<ListaNegra, Long> {
    boolean existsByPhone(String phone);
    ListaNegra findByPhone(String phone);
}
