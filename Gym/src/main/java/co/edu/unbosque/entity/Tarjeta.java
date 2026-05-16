package co.edu.unbosque.entity;

import co.edu.unbosque.entity.enums.FranquiciaTarjeta;
import co.edu.unbosque.entity.enums.TipoTarjeta;
import jakarta.persistence.*;

@Entity
@Table(name = "tarjeta")
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarjeta;
    private Double monto;
    @Enumerated(EnumType.STRING)
    private FranquiciaTarjeta franquicia;

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipo;

    @ManyToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;

    public Tarjeta () {}

    public Long getIdTarjeta() {return idTarjeta;}
    public void setIdTarjeta(Long idTarjeta) {this.idTarjeta = idTarjeta;}
    public Double getMonto() {return monto;}
    public void setMonto(Double monto) {this.monto = monto;}
    public FranquiciaTarjeta getFranquicia() {return franquicia;}
    public void setFranquicia(FranquiciaTarjeta franquicia) {this.franquicia = franquicia;}
    public TipoTarjeta getTipo() {return tipo;}
    public void setTipo(TipoTarjeta tipo) {this.tipo = tipo;}
    public Pago getPago() {return pago;}
    public void setPago(Pago pago) {this.pago = pago;}
}
