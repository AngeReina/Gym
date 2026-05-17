package co.edu.unbosque.entity;

import co.edu.unbosque.entity.enums.EstadoAsistencia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "asistencia")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistencia;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_asistencia")
    private EstadoAsistencia estadoAsistencia = EstadoAsistencia.PRESENTE; // Valor por defecto

    @JsonIgnoreProperties({"suscripciones", "asistencias"})
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @JsonIgnoreProperties({"asistencias"})
    @ManyToOne
    @JoinColumn(name = "id_sesion")
    private Sesion sesion;

    public Asistencia(){}

    public Long getIdAsistencia() {return idAsistencia;}
    public void setIdAsistencia(Long idAsistencia) {this.idAsistencia = idAsistencia;}
    public LocalDateTime getFechaRegistro() {return fechaRegistro;}
    public void setFechaRegistro(LocalDateTime fechaRegistro) {this.fechaRegistro = fechaRegistro;}
    public EstadoAsistencia getEstadoAsistencia() {return estadoAsistencia;}
    public void setEstadoAsistencia(EstadoAsistencia estadoAsistencia) {this.estadoAsistencia = estadoAsistencia;}
    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    public Sesion getSesion() {return sesion;}
    public void setSesion(Sesion sesion) {this.sesion = sesion;}
}