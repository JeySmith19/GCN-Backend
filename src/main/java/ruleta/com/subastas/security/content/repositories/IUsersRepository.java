package ruleta.com.subastas.security.content.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ruleta.com.subastas.security.content.entities.Users;


@Repository
public interface IUsersRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);

    @Query("select count(u.username) from Users u where u.username =:username")
    int buscarUsername(@Param("username") String nombre);

    @Transactional
    @Modifying
    @Query(value = "insert into roles (rol, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
    void insRol(@Param("rol") String authority, @Param("user_id") Long user_id);

}
