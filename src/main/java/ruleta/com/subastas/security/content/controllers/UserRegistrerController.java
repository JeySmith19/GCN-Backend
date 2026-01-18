package ruleta.com.subastas.security.content.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.security.RegistrationRequest;
import ruleta.com.subastas.security.content.entities.Role;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IRolRepository;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;

import java.util.Map;

@RestController
@CrossOrigin
public class UserRegistrerController {

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private IRolRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(
            @RequestBody RegistrationRequest registrationRequest) {

        if (userRepo.findByUsername(registrationRequest.getUsername()) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "El correo ya se encuentra registrado"));
        }

        Users newUser = new Users();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setEnabled(true);
        newUser.setName(registrationRequest.getName());
        newUser.setLastName(registrationRequest.getLastName());
        newUser.setPhone(registrationRequest.getPhone());
        newUser.setCity(registrationRequest.getCity());

        userRepo.save(newUser);

        for (String roleName : registrationRequest.getRoles()) {
            Role role = new Role();
            role.setRol(roleName);
            role.setUser(newUser);
            roleRepo.save(role);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Usuario registrado con éxito"));
    }
}
