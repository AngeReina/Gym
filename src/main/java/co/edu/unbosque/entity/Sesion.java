package co.edu.unbosque.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sesion")
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSesion;
    private LocalDateTime fecha;
    @Column(name = "hora_inicio")
    private String horaInicio;
    @Column(name = "hora_fin")
    private String horaFin;
    @Column(name = "cupo_max")
    private Integer cupoMax;
    @Column(name = "cupos_disponibles")
    private Integer cuposDisponibles;

    @ManyToOne
    @JoinColumn(name = "id_clase")
    private Clase clase;

    @ManyToOne
    @JoinColumn(name = "id_instructor")
    private Instructor instructor;

    public Sesion(){}

    public Long getIdSesion() {return idSesion;}
    public void setIdSesion(Long idSesion) {this.idSesion = idSesion;}
    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public String getHoraInicio() {return horaInicio;}
    public void setHoraInicio(String horaInicio) {this.horaInicio = horaInicio;}
    public String getHoraFin() {return horaFin;}
    public void setHoraFin(String horaFin) {this.horaFin = horaFin;}
    public Integer getCupoMax() {return cupoMax;}
    public void setCupoMax(Integer cupoMax) {this.cupoMax = cupoMax;}
    public Integer getCuposDisponibles() {return cuposDisponibles;}
    public void setCuposDisponibles(Integer cuposDisponibles) {this.cuposDisponibles = cuposDisponibles;}
    public Clase getClase() {return clase;}
    public void setClase(Clase clase) {this.clase = clase;}
    public Instructor getInstructor() {return instructor;}
    public void setInstructor(Instructor instructor) {this.instructor = instructor;}
}
