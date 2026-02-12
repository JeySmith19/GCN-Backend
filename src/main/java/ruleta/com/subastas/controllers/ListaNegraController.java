package ruleta.com.subastas.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ruleta.com.subastas.dtos.ListaNegraDTO;
import ruleta.com.subastas.entities.ListaNegra;
import ruleta.com.subastas.serviceinterfaces.IListaNegraService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lista-negra")
@CrossOrigin
public class ListaNegraController {

    @Autowired
    private IListaNegraService listaNegraService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void insertar(@RequestBody ListaNegraDTO dto) {
        ModelMapper mapper = new ModelMapper();
        ListaNegra entidad = mapper.map(dto, ListaNegra.class);
        listaNegraService.insert(entidad);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void actualizar(@PathVariable Long id, @RequestBody ListaNegraDTO dto) {
        ListaNegra existente = listaNegraService.listId(id);
        if (existente != null) {
            existente.setPhone(dto.getPhone());
            existente.setMotivo(dto.getMotivo());
            listaNegraService.insert(existente);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        listaNegraService.delete(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ListaNegraDTO> listar() {
        ModelMapper mapper = new ModelMapper();
        return listaNegraService.list()
                .stream()
                .map(e -> mapper.map(e, ListaNegraDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/buscar")
    public ResponseEntity<ListaNegraDTO> buscarPorPhone(@RequestParam String phone) {
        ListaNegra e = listaNegraService.findByPhone(phone);

        if (e == null) {
            return ResponseEntity.notFound().build();
        }

        ListaNegraDTO dto = new ModelMapper().map(e, ListaNegraDTO.class);
        return ResponseEntity.ok(dto);
    }

}