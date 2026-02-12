package ruleta.com.subastas.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lista_negra")
public class ListaNegra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Column(length = 255)
    private String motivo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public ListaNegra() {
    }

    public ListaNegra(Long id, String phone, String motivo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.phone = phone;
        this.motivo = motivo;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
