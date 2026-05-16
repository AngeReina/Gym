package co.edu.unbosque.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "suscripcion")
public class Suscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscripcion")
    private Long idSuscripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    private Double costo;
    private String beneficios;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    @JsonIgnore
    private Plan plan;

    public Suscripcion() {}

    public Long getIdSuscripcion() {return idSuscripcion;}
    public void setIdSuscripcion(Long idSuscripcion) {this.idSuscripcion = idSuscripcion;}
    public LocalDate getFechaInicio() {return fechaInicio;}
    public void setFechaInicio(LocalDate fechaInicio) {this.fechaInicio = fechaInicio;}
    public LocalDate getFechaFin() {return fechaFin;}
    public void setFechaFin(LocalDate fechaFin) {this.fechaFin = fechaFin;}
    public Double getCosto() {return costo;}
    public void setCosto(Double costo) {this.costo = costo;}
    public String getBeneficios() {return beneficios;}
    public void setBeneficios(String beneficios) {this.beneficios = beneficios;}
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    public Plan getPlan() {return plan;}
    public void setPlan(Plan plan) {this.plan = plan;}
}