package com.example.gestor_gastos.gestor_gastos.model.dto;


import java.time.LocalDate;

public class GastoDTO {
    private String descripcion;
    private Double monto;
    private String categoria;
    private LocalDate fecha;

    public GastoDTO(String descripcion, Double monto, String categoria, LocalDate fecha) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.categoria = categoria;
        this.fecha = fecha;
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
}
