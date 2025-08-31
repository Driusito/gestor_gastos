package com.example.gestor_gastos.gestor_gastos.model.dto;


public class EstadisticaCategoriaDTO {
    private String categoria;
    private Double total;

    public EstadisticaCategoriaDTO(String categoria, Double total) {
        this.categoria = categoria;
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getTotal() {
        return total;
    }
}
