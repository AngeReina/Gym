package co.edu.unbosque.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
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
    private BigDecimal costo;
    private Integer duracionDias;

    @OneToMany(mappedBy = "plan")
    private List<Suscripcion> suscripciones;

    public Plan() {}

    public Long getIdPlan() {return idPlan;}
    public void setIdPlan(Long idPlan) {this.idPlan = idPlan;}
    public String getNombrePlan() {return nombrePlan;}
    public void setNombrePlan(String nombrePlan) {this.nombrePlan = nombrePlan;}
    public BigDecimal getCosto() {return costo;}
    public void setCosto(BigDecimal costo) {this.costo = costo;}
    public Integer getDuracionDias() {return duracionDias;}
    public void setDuracionDias(Integer duracionDias) {this.duracionDias = duracionDias;}
    public List<Suscripcion> getSuscripciones() {return suscripciones;}
    public void setSuscripciones(List<Suscripcion> suscripciones) {this.suscripciones = suscripciones;}
}
