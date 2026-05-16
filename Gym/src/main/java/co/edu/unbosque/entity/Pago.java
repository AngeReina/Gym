package co.edu.unbosque.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(name = "total_recibo")
    private Double totalRecibo;
    private LocalDateTime fecha = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

    public Pago() {}

    public Long getIdPago() {return idPago;}
    public void setIdPago(Long idPago) {this.idPago = idPago;}
    public Double getTotalRecibo() {return totalRecibo;}
    public void setTotalRecibo(Double totalRecibo) {this.totalRecibo = totalRecibo;}
    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public Factura getFactura() {return factura;}
    public void setFactura(Factura factura) {this.factura = factura;}
}
