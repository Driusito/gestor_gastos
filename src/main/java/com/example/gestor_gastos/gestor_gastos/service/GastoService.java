package com.example.gestor_gastos.gestor_gastos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.gestor_gastos.gestor_gastos.model.Gasto;

public interface GastoService {

    List<Gasto> getGastosByCategoria(String categoria);

    Gasto saveGasto(Gasto gasto);

    List<Gasto> getAllGastos();

    void deleteGasto(Long id);

    Optional<Gasto> getGastoById(Long id);

    Double getTotalMesActual();

    Double getTotalPorRango(LocalDate inicio, LocalDate fin);

}
