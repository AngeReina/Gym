package co.edu.unbosque.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clase")
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClase;
    @Column(name = "nombre_clase")
    private String nombreClase;
    private String descripcion;

    public Clase () {}

    public Long getIdClase() {return idClase;}
    public void setIdClase(Long idClase) {this.idClase = idClase;}
    public String getNombreClase() {return nombreClase;}
    public void setNombreClase(String nombreClase) {this.nombreClase = nombreClase;}
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
}
