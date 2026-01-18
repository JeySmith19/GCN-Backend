package ruleta.com.subastas.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaEvento; // el “día de subasta”

    @Column(nullable = false)
    private LocalTime horaInicio; // 2:00 pm o 3:00 pm

    @Column(nullable = false)
    private Integer duracionSubastaMinutos; // 5 o 10

    @Column(nullable = false)
    private Integer descansoMinutos; // pausa entre subastas

    @Column(nullable = false)
    private String estado; // ABIERTO / CERRADO

    public Evento() {}

    public Evento(Long id, String nombre, LocalDate fechaEvento, LocalTime horaInicio, Integer duracionSubastaMinutos, Integer descansoMinutos, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaEvento = fechaEvento;
        this.horaInicio = horaInicio;
        this.duracionSubastaMinutos = duracionSubastaMinutos;
        this.descansoMinutos = descansoMinutos;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracionSubastaMinutos() {
        return duracionSubastaMinutos;
    }

    public void setDuracionSubastaMinutos(Integer duracionSubastaMinutos) {
        this.duracionSubastaMinutos = duracionSubastaMinutos;
    }

    public Integer getDescansoMinutos() {
        return descansoMinutos;
    }

    public void setDescansoMinutos(Integer descansoMinutos) {
        this.descansoMinutos = descansoMinutos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
