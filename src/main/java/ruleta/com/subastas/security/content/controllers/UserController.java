package ruleta.com.subastas.security.content.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.security.content.dto.UserDTO;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/details")
    public UserDTO getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        List<String> roles = user.getRoles().stream().map(role -> role.getRol()).collect(Collectors.toList());
        userDTO.setRoles(roles);

        return userDTO;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Usuario no encontrado"));
        }

        userRepo.delete(user);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
    }

}
