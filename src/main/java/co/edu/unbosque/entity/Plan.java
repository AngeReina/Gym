package co.edu.unbosque.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long idPlan;

    @Column(name = "nombre_plan")
    private String nombrePlan;
    private Double costo;
    private String duracion;

    @OneToMany(mappedBy = "plan")
    private List<Suscripcion> suscripciones;

    public Plan() {}

    public Long getIdPlan() {return idPlan;}
    public void setIdPlan(Long idPlan) {this.idPlan = idPlan;}
    public String getNombrePlan() {return nombrePlan;}
    public void setNombrePlan(String nombrePlan) {this.nombrePlan = nombrePlan;}
    public Double getCosto() {return costo;}
    public void setCosto(Double costo) {this.costo = costo;}
    public String getDuracion() {return duracion;}
    public void setDuracion(String duracion) {this.duracion = duracion;}
    public List<Suscripcion> getSuscripciones() {return suscripciones;}
    public void setSuscripciones(List<Suscripcion> suscripciones) {this.suscripciones = suscripciones;}
}
