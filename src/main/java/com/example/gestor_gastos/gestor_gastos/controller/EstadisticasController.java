package com.example.gestor_gastos.gestor_gastos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestor_gastos.gestor_gastos.model.dto.EstadisticaCategoriaDTO;
import com.example.gestor_gastos.gestor_gastos.service.GastoService;

@RestController
@RequestMapping("/api/v1/gastos/estadisticas")
public class EstadisticasController {

    @Autowired
    private GastoService gastoService;

    @GetMapping("/mes")
    public ResponseEntity<Double> getTotalMesActual() {
        return ResponseEntity.ok(gastoService.getTotalMesActual());
    }

    

    @GetMapping("/rango")
    public ResponseEntity<Double> getTotalPorRango(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        return ResponseEntity.ok(gastoService.getTotalPorRango(inicio, fin));
    }
}

