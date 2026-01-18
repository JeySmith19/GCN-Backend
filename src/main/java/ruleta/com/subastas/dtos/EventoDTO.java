package ruleta.com.subastas.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoDTO {

    private Long id;
    private String nombre;
    private LocalDate fechaEvento;
    private LocalTime horaInicio;
    private Integer duracionSubastaMinutos;
    private Integer descansoMinutos;
    private String estado; // ABIERTO / CERRADO

    public EventoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDate fechaEvento) { this.fechaEvento = fechaEvento; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public Integer getDuracionSubastaMinutos() { return duracionSubastaMinutos; }
    public void setDuracionSubastaMinutos(Integer duracionSubastaMinutos) { this.duracionSubastaMinutos = duracionSubastaMinutos; }

    public Integer getDescansoMinutos() { return descansoMinutos; }
    public void setDescansoMinutos(Integer descansoMinutos) { this.descansoMinutos = descansoMinutos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
