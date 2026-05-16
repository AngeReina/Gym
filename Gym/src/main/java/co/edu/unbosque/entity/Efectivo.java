package co.edu.unbosque.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "efectivo")
public class Efectivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEfectivo;
    private Double monto;

    @ManyToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;

    public Efectivo(){}

    public Efectivo(Double monto, Pago pago) {
        this.monto = monto;
        this.pago = pago;
    }

    public Long getIdEfectivo() {return idEfectivo;}
    public void setIdEfectivo(Long idEfectivo) {this.idEfectivo = idEfectivo;}
    public Double getMonto() {return monto;}
    public void setMonto(Double monto) {this.monto = monto;}
    public Pago getPago() {return pago;}
    public void setPago(Pago pago) {this.pago = pago;}
}
