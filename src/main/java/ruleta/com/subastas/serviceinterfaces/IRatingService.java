package ruleta.com.subastas.serviceinterfaces;

import ruleta.com.subastas.dtos.RatingDTO;
import ruleta.com.subastas.entities.Rating;

import java.util.List;

public interface IRatingService {
    void rateUser(Long raterId, RatingDTO dto);
    Double getAverageRating(Long userId);
    Long getTotalRatings(Long userId);
    void updateRating(Long raterId, RatingDTO dto);
    void deleteRating(Long raterId, Long ratedId);
    List<Rating> getRatingsOfUser(Long userId);
}
