package ruleta.com.subastas.security.content.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ruleta.com.subastas.security.content.entities.Role;


@Repository
public interface IRolRepository extends JpaRepository<Role, Long> {
}
