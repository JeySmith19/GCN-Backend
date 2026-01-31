package ruleta.com.subastas.entities;

import ruleta.com.subastas.security.content.entities.Users;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "subastas")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Integer numeroSubasta; // número asignado en el evento

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private String planta;

    @Column(nullable = false)
    private String maceta;

    @Column(nullable = true)
    private String observaciones;

    @Column(columnDefinition = "TEXT")
    private String imagen;

    @Column(nullable = true)
    private String imagenId;

    @Column(nullable = false)
    private Double precioBase;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = true)
    private LocalTime horaInicioAsignada;

    @Column(nullable = true)
    private LocalTime horaFinAsignada;

    public Subasta() {
    }

    public Subasta(Long id, Users user, Evento evento, String planta, String maceta, String observaciones,
                   String imagen, String imagenId, Double precioBase, String estado, Integer numeroSubasta,
                   LocalTime horaInicioAsignada, LocalTime horaFinAsignada) {
        this.id = id;
        this.user = user;
        this.evento = evento;
        this.planta = planta;
        this.maceta = maceta;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.imagenId = imagenId;
        this.precioBase = precioBase;
        this.estado = estado;
        this.numeroSubasta = numeroSubasta;
        this.horaInicioAsignada = horaInicioAsignada;
        this.horaFinAsignada = horaFinAsignada;
    }

    // ================== GETTERS Y SETTERS ==================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getPlanta() {
        return planta;
    }

    public void setPlanta(String planta) {
        this.planta = planta;
    }

    public String getMaceta() {
        return maceta;
    }

    public void setMaceta(String maceta) {
        this.maceta = maceta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNumeroSubasta() {
        return numeroSubasta;
    }

    public void setNumeroSubasta(Integer numeroSubasta) {
        this.numeroSubasta = numeroSubasta;
    }

    public LocalTime getHoraInicioAsignada() {
        return horaInicioAsignada;
    }

    public void setHoraInicioAsignada(LocalTime horaInicioAsignada) {
        this.horaInicioAsignada = horaInicioAsignada;
    }

    public LocalTime getHoraFinAsignada() {
        return horaFinAsignada;
    }

    public void setHoraFinAsignada(LocalTime horaFinAsignada) {
        this.horaFinAsignada = horaFinAsignada;
    }
}
