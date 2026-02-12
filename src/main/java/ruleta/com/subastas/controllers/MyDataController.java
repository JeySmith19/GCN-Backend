package ruleta.com.subastas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.security.content.dto.UserDTO;
import ruleta.com.subastas.security.content.entities.Users;
import ruleta.com.subastas.security.content.repositories.IUsersRepository;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/mydata")
public class MyDataController {

    @Autowired
    private IUsersRepository userRepo;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUBASTADOR', 'USER')")
    public ResponseEntity<UserDTO> getMyData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) auth.getPrincipal()).getUsername();

        Users user = userRepo.findByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setCity(user.getCity());
        dto.setRoles(user.getRoles().stream().map(r -> r.getRol()).collect(Collectors.toList()));

        return ResponseEntity.ok(dto);
    }
}
