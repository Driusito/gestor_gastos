package com.example.gestor_gastos.gestor_gastos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.gestor_gastos.gestor_gastos.model.Gasto;
import com.example.gestor_gastos.gestor_gastos.model.dto.EstadisticaCategoriaDTO;

@Repository
public interface GastoRepository extends CrudRepository<Gasto, Long> {

    List<Gasto> findByCategoria(String categoria);

    @Query("SELECT SUM(g.monto) FROM Gasto g WHERE MONTH(g.fecha) = MONTH(CURRENT_DATE) AND YEAR(g.fecha) = YEAR(CURRENT_DATE)")
    Double getTotalMesActual();

    

    @Query("SELECT SUM(g.monto) FROM Gasto g WHERE g.fecha BETWEEN :inicio AND :fin")
    Double getTotalPorRango(LocalDate inicio, LocalDate fin);
} 