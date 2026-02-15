package ruleta.com.subastas.entities;

import ruleta.com.subastas.security.content.entities.Users;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"rater_id", "rated_id"})
)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rater_id", nullable = false)
    private Users rater;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rated_id", nullable = false)
    private Users rated;

    @Column(nullable = false)
    private Integer score;

    @Column(length = 300)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Rating() {
    }

    public Rating(Long id, Users rater, Users rated, Integer score, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.rater = rater;
        this.rated = rated;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Users getRater() {
        return rater;
    }

    public void setRater(Users rater) {
        this.rater = rater;
    }

    public Users getRated() {
        return rated;
    }

    public void setRated(Users rated) {
        this.rated = rated;
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
