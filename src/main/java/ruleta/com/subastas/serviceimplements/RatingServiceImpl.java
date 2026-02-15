package ruleta.com.subastas.serviceimplements;

import org.springframework.stereotype.Service;
import ruleta.com.subastas.dtos.RatingDTO;
import ruleta.com.subastas.entities.Rating;
import ruleta.com.subastas.repositories.IRatingRepository;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;
import ruleta.com.subastas.serviceinterfaces.IRatingService;

import java.util.List;

@Service
public class RatingServiceImpl implements IRatingService {

    private final IRatingRepository ratingRepository;
    private final IUsersRepository usersRepository;

    public RatingServiceImpl(IRatingRepository ratingRepository,
                             IUsersRepository usersRepository) {
        this.ratingRepository = ratingRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void rateUser(Long raterId, RatingDTO dto) {

        if (raterId.equals(dto.getRatedUserId())) {
            throw new RuntimeException("No puedes calificarte a ti mismo");
        }

        if (ratingRepository
                .findByRaterIdAndRatedId(raterId, dto.getRatedUserId())
                .isPresent()) {
            throw new RuntimeException("Ya calificaste a este usuario");
        }

        Users rater = usersRepository.findById(raterId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Users rated = usersRepository.findById(dto.getRatedUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rating rating = new Rating();
        rating.setRater(rater);
        rating.setRated(rated);
        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());

        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(Long raterId, RatingDTO dto) {
        // Validamos que la calificación exista Y pertenezca al raterId de la sesión actual
        Rating rating = ratingRepository
                .findByRaterIdAndRatedId(raterId, dto.getRatedUserId())
                .orElseThrow(() -> new RuntimeException("No tienes permiso para editar esta calificación o no existe"));

        rating.setScore(dto.getScore());
        rating.setComment(dto.getComment());

        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long raterId, Long ratedId) {
        // Solo borra si la calificación fue hecha por el raterId actual
        Rating rating = ratingRepository
                .findByRaterIdAndRatedId(raterId, ratedId)
                .orElseThrow(() -> new RuntimeException("No se encontró una calificación tuya para eliminar"));

        ratingRepository.delete(rating);
    }
    @Override
    public List<Rating> getRatingsOfUser(Long userId) {
        return ratingRepository.findByRatedId(userId);
    }

    @Override
    public Double getAverageRating(Long userId) {
        return ratingRepository.findAverageScoreByRatedId(userId);
    }

    @Override
    public Long getTotalRatings(Long userId) {
        return ratingRepository.countByRatedId(userId);
    }
}
