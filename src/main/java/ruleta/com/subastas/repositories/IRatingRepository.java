package ruleta.com.subastas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ruleta.com.subastas.entities.Rating;
import ruleta.com.subastas.security.content.entities.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByRaterIdAndRatedId(Long raterId, Long ratedId);

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.rated.id = :ratedId")
    Double findAverageScoreByRatedId(@Param("ratedId") Long ratedId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.rated.id = :ratedId")
    Long countByRatedId(@Param("ratedId") Long ratedId);

    List<Rating> findByRatedId(Long ratedId);

    @Query("SELECT u FROM Users u WHERE " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Users> searchByName(@Param("term") String term);

    @Query("SELECT u FROM Users u WHERE u.id <> :currentUserId")
    List<Users> findAllExceptMe(@Param("currentUserId") Long currentUserId);
}