package ruleta.com.subastas.security.content.controllers.RecuperarContrasena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String code) {
        try {
            String subject = "Recuperación de Contraseña";
            String text = "Tu código de verificación para restablecer la contraseña es: " + code + "\n\n" +
                    "Este código es válido por 10 minutos.";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            System.out.println("Correo enviado a: " + to);

        } catch (Exception e) {
            System.out.println("Error enviando el correo: " + e.getMessage());
        }
    }
}