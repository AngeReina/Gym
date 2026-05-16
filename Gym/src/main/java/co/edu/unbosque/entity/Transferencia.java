package co.edu.unbosque.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transferencia")
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransferencia;
    private Double monto;
    @Column(name = "banco_origen")
    private String bancoOrigen;
    @Column(name = "numero_transaccion")
    private String numeroTransaccion;

    @ManyToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;

    public Transferencia () {}

    public Long getIdTransferencia() {return idTransferencia;}
    public void setIdTransferencia(Long idTransferencia) {this.idTransferencia = idTransferencia;}
    public Double getMonto() {return monto;}
    public void setMonto(Double monto) {this.monto = monto;}
    public String getBancoOrigen() {return bancoOrigen;}
    public void setBancoOrigen(String bancoOrigen) {this.bancoOrigen = bancoOrigen;}
    public String getNumeroTransaccion() {return numeroTransaccion;}
    public void setNumeroTransaccion(String numeroTransaccion) {this.numeroTransaccion = numeroTransaccion;}
    public Pago getPago() {return pago;}
    public void setPago(Pago pago) {this.pago = pago;}
}