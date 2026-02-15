package ruleta.com.subastas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.dtos.RatingDTO;
import ruleta.com.subastas.entities.Rating;
import ruleta.com.subastas.security.content.dto.UserDTO;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;
import ruleta.com.subastas.serviceinterfaces.IRatingService;
import ruleta.com.subastas.serviceinterfaces.ISubastaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
@CrossOrigin
public class RatingController {

    private final IRatingService ratingService;
    private final IUsersRepository userRepo;

    @Autowired
    private ISubastaService subastaService;

    public RatingController(IRatingService ratingService, IUsersRepository userRepo) {
        this.ratingService = ratingService;
        this.userRepo = userRepo;
    }

    @GetMapping("/destacados")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR', 'USER')")
    public List<UserDTO> getDestacados() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(currentUsername);

        return userRepo.findAll().stream()
                .filter(u -> !u.getId().equals(currentUser.getId()))
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setLastName(user.getLastName());
                    dto.setCity(user.getCity());
                    Double avg = ratingService.getAverageRating(user.getId());
                    dto.setAverageRating(avg != null ? avg : 0.0);
                    dto.setTotalSubastas((long) subastaService.findByUserId(user.getId()).size());
                    double activityScore = Math.min(dto.getTotalSubastas(), 10) / 2.0;
                    dto.setReputationScore((dto.getAverageRating() * 0.7) + (activityScore * 0.3));
                    return dto;
                })
                .sorted((u1, u2) -> Double.compare(u2.getReputationScore(), u1.getReputationScore()))
                .limit(8)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR', 'USER')")
    public List<UserDTO> searchUsers(@RequestParam String term) {
        return userRepo.findAll().stream()
                .filter(u -> (u.getName() + " " + u.getLastName()).toLowerCase().contains(term.toLowerCase()))
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(u.getId());
                    dto.setName(u.getName());
                    dto.setLastName(u.getLastName());
                    dto.setCity(u.getCity());
                    return dto;
                })
                .limit(10)
                .collect(Collectors.toList());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR', 'USER')")
    public ResponseEntity<List<UserDTO>> listUsersToRate() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = userRepo.findByUsername(auth.getName());
        return ResponseEntity.ok(userRepo.findAll().stream()
                .filter(u -> !u.getId().equals(currentUser.getId()))
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setName(user.getName());
                    dto.setLastName(user.getLastName());
                    dto.setCity(user.getCity());
                    return dto;
                }).collect(Collectors.toList()));
    }

    @PostMapping
    public void rateUser(@RequestBody RatingDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(username);
        ratingService.rateUser(currentUser.getId(), dto);
    }

    @PutMapping
    public void updateRating(@RequestBody RatingDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(username);
        ratingService.updateRating(currentUser.getId(), dto);
    }

    @DeleteMapping("/{ratedId}")
    public void deleteRating(@PathVariable Long ratedId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(username);
        ratingService.deleteRating(currentUser.getId(), ratedId);
    }

    @GetMapping("/user/{userId}")
    public List<Rating> getRatingsOfUser(@PathVariable Long userId) {
        return ratingService.getRatingsOfUser(userId);
    }

    @GetMapping("/user/{userId}/average")
    public Double getAverageRating(@PathVariable Long userId) {
        return ratingService.getAverageRating(userId);
    }

    @GetMapping("/user/{userId}/count")
    public Long getTotalRatings(@PathVariable Long userId) {
        return ratingService.getTotalRatings(userId);
    }

    @GetMapping("/me/summary")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR', 'USER')")
    public ResponseEntity<?> getMyRatingSummary() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = userRepo.findByUsername(username);

        Double average = ratingService.getAverageRating(currentUser.getId());
        Long total = ratingService.getTotalRatings(currentUser.getId());

        return ResponseEntity.ok().body(
                java.util.Map.of(
                        "averageRating", average != null ? average : 0.0,
                        "totalRatings", total != null ? total : 0L
                )
        );
    }
}