package ruleta.com.subastas.security.content.controllers.RecuperarContrasena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@CrossOrigin
public class PasswordResetController {

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("Buscando usuario con correo: " + email);

        Users user = userRepo.findByUsername(email);
        if (user == null) {
            System.out.println("Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }

        String verificationCode = String.valueOf((int)(Math.random() * 900000) + 100000);

        user.setResetPasswordCode(verificationCode);
        user.setResetPasswordCodeExpiration(LocalDateTime.now().plusMinutes(10));
        userRepo.save(user);

        try {
            emailService.sendPasswordResetEmail(user.getUsername(), verificationCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error enviando el correo de recuperación: " + e.getMessage());
        }

        return ResponseEntity.ok("Código de recuperación enviado");
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        Users user = userRepo.findByUsername(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }

        if (!code.equals(user.getResetPasswordCode()) || user.getResetPasswordCodeExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido o expirado");
        }

        return ResponseEntity.ok("Código verificado correctamente");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        String newPassword = request.get("newPassword");

        Users user = userRepo.findByUsername(email);

        if (user == null || !code.equals(user.getResetPasswordCode()) || user.getResetPasswordCodeExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido o expirado");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordCode(null);
        user.setResetPasswordCodeExpiration(null);
        userRepo.save(user);

        return ResponseEntity.ok("Contraseña restablecida exitosamente");
    }
}
