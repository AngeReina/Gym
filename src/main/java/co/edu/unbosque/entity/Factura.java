package co.edu.unbosque.entity;

import co.edu.unbosque.entity.enums.EstadoFactura;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision = LocalDateTime.now();
    @Column(name = "total_factura")
    private Double totalFactura;
    @Enumerated(EnumType.STRING)
    private EstadoFactura estado = EstadoFactura.PENDIENTE; // Valor por defecto

    @ManyToOne
    @JoinColumn(name = "id_suscripcion")
    private Suscripcion suscripcion;

    public Factura() {}

    public Long getIdFactura() {return idFactura;}
    public void setIdFactura(Long idFactura) {this.idFactura = idFactura;}
    public LocalDateTime getFechaEmision() {return fechaEmision;}
    public void setFechaEmision(LocalDateTime fechaEmision) {this.fechaEmision = fechaEmision;}
    public Double getTotalFactura() {return totalFactura;}
    public void setTotalFactura(Double totalFactura) {this.totalFactura = totalFactura;}
    public EstadoFactura getEstado() {return estado;}
    public void setEstado(EstadoFactura estado) {this.estado = estado;}
    public Suscripcion getSuscripcion() {return suscripcion;}
    public void setSuscripcion(Suscripcion suscripcion) {this.suscripcion = suscripcion;}
}
