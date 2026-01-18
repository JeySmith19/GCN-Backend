package ruleta.com.subastas.entities;

import ruleta.com.subastas.security.content.entities.Users;

import javax.persistence.*;

@Entity
@Table(name = "subastas")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String imagen; // aquí irá Base64

    @Column(nullable = false)
    private Double precioBase;


    @Column(nullable = false)
    private String estado; // PENDIENTE / ACEPTADA / RECHAZADA

    public Subasta() {
    }

    public Subasta(Long id, Users user, Evento evento, String planta, String maceta, String observaciones, String imagen, Double precioBase, String estado) {
        this.id = id;
        this.user = user;
        this.evento = evento;
        this.planta = planta;
        this.maceta = maceta;
        this.observaciones = observaciones;
        this.imagen = imagen;
        this.precioBase = precioBase;
        this.estado = estado;
    }

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
}
