package ruleta.com.subastas.dtos;

import java.time.LocalDateTime;

public class RatingDTO {

    private Long id;
    private Long ratedUserId;
    private Long raterUserId;
    private String raterUsername;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(Long ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    public Long getRaterUserId() {
        return raterUserId;
    }

    public void setRaterUserId(Long raterUserId) {
        this.raterUserId = raterUserId;
    }

    public String getRaterUsername() {
        return raterUsername;
    }

    public void setRaterUsername(String raterUsername) {
        this.raterUsername = raterUsername;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
