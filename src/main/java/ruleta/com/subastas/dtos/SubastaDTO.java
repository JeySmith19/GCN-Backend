package ruleta.com.subastas.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class SubastaDTO {
    private Long id;
    private Long userId;
    private String username;
    private String phone;
    private String city;

    private Long eventoId;
    private String eventoNombre;
    private LocalDate fechaEvento;
    private LocalTime horaInicio; // hora de inicio del evento
    private Integer duracionSubastaMinutos;
    private Integer descansoMinutos; // nuevo campo

    private String planta;
    private String maceta;
    private String observaciones;
    private String imagen;
    private String estado;
    private Double precioBase;

    private Integer numeroSubasta; // nuevo
    private LocalTime horaInicioAsignada; // nuevo
    private LocalTime horaFinAsignada; // nuevo

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Long getEventoId() { return eventoId; }
    public void setEventoId(Long eventoId) { this.eventoId = eventoId; }

    public String getEventoNombre() { return eventoNombre; }
    public void setEventoNombre(String eventoNombre) { this.eventoNombre = eventoNombre; }

    public LocalDate getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDate fechaEvento) { this.fechaEvento = fechaEvento; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public Integer getDuracionSubastaMinutos() { return duracionSubastaMinutos; }
    public void setDuracionSubastaMinutos(Integer duracionSubastaMinutos) { this.duracionSubastaMinutos = duracionSubastaMinutos; }

    public Integer getDescansoMinutos() { return descansoMinutos; }
    public void setDescansoMinutos(Integer descansoMinutos) { this.descansoMinutos = descansoMinutos; }

    public String getPlanta() { return planta; }
    public void setPlanta(String planta) { this.planta = planta; }

    public String getMaceta() { return maceta; }
    public void setMaceta(String maceta) { this.maceta = maceta; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Double getPrecioBase() { return precioBase; }
    public void setPrecioBase(Double precioBase) { this.precioBase = precioBase; }

    public Integer getNumeroSubasta() { return numeroSubasta; }
    public void setNumeroSubasta(Integer numeroSubasta) { this.numeroSubasta = numeroSubasta; }

    public LocalTime getHoraInicioAsignada() { return horaInicioAsignada; }
    public void setHoraInicioAsignada(LocalTime horaInicioAsignada) { this.horaInicioAsignada = horaInicioAsignada; }

    public LocalTime getHoraFinAsignada() { return horaFinAsignada; }
    public void setHoraFinAsignada(LocalTime horaFinAsignada) { this.horaFinAsignada = horaFinAsignada; }
}
