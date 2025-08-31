package com.example.gestor_gastos.gestor_gastos.model;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gastos")
public class Gasto {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String descripcion;
    private Double monto;
    private String categoria;
    private LocalDate fecha;

    public Gasto(String descripcion, Double monto, String categoria, LocalDate fecha) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
    }

    public Gasto() {
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
    

    
}
