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
import ruleta.com.subastas.security.content.entities.Role;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IRolRepository;
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

    @Autowired
    private IRolRepository roleRepo;

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

    @PostMapping("/request-subastador")
    public ResponseEntity<?> requestSubastador(Authentication auth) {

        Users user = userRepo.findByUsername(auth.getName());

        boolean esUser = user.getRoles().stream()
                .anyMatch(r -> r.getRol().equals("USER"));

        if (!esUser) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "No puedes solicitar"));
        }

        // quitar ROLE_USER
        user.getRoles().removeIf(r -> r.getRol().equals("USER"));

        // agregar ROLE_SUBASTADOR_PENDIENTE
        Role pendiente = new Role();
        pendiente.setRol("SUBASTADOR_PENDIENTE");
        pendiente.setUser(user);
        roleRepo.save(pendiente);

        return ResponseEntity.ok(Map.of("message", "Solicitud enviada"));
    }

    @GetMapping("/admin/subastadores-pendientes")
    public List<UserDTO> pendientes() {
        return userRepo.findAll().stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getRol().equals("SUBASTADOR_PENDIENTE")))
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(u.getId());
                    dto.setUsername(u.getUsername());
                    dto.setName(u.getName());
                    dto.setLastName(u.getLastName());
                    dto.setPhone(u.getPhone());
                    dto.setCity(u.getCity());
                    dto.setRoles(u.getRoles().stream()
                            .map(Role::getRol)
                            .toList());
                    return dto;
                }).toList();
    }

    @PostMapping("/admin/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {

        Users user = userRepo.findById(id).orElseThrow();

        user.getRoles().removeIf(r ->
                r.getRol().equals("SUBASTADOR_PENDIENTE"));

        Role sub = new Role();
        sub.setRol("SUBASTADOR");
        sub.setUser(user);
        roleRepo.save(sub);

        return ResponseEntity.ok(Map.of("message", "Usuario aprobado"));
    }

    @PostMapping("/admin/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id) {

        Users user = userRepo.findById(id).orElseThrow();

        user.getRoles().removeIf(r ->
                r.getRol().equals("SUBASTADOR_PENDIENTE"));

        Role userRole = new Role();
        userRole.setRol("USER");
        userRole.setUser(user);
        roleRepo.save(userRole);

        return ResponseEntity.ok(Map.of("message", "Solicitud rechazada"));
    }
}
