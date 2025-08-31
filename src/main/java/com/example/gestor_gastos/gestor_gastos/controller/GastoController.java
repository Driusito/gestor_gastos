package com.example.gestor_gastos.gestor_gastos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestor_gastos.gestor_gastos.model.Gasto;
import com.example.gestor_gastos.gestor_gastos.service.GastoService;

@RestController
@RequestMapping("/api/v1/gastos")
@CrossOrigin(origins = "*")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping
    public List<Gasto> getAllGastos() {
        return gastoService.getAllGastos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> getGastoById(@PathVariable Long id) {
        Optional<Gasto> gasto = gastoService.getGastoById(id);
        return gasto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public List<Gasto> getGastosByCategoria(@PathVariable String categoria) {
        return gastoService.getGastosByCategoria(categoria);
    }

    @PostMapping
    public ResponseEntity<Gasto> createGasto(@RequestBody Gasto gasto) {
        return ResponseEntity.ok(gastoService.saveGasto(gasto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gasto> updateGasto(@PathVariable Long id, @RequestBody Gasto gastoDetalles) {
        return gastoService.getGastoById(id).map(gasto -> {
            gasto.setDescripcion(gastoDetalles.getDescripcion());
            gasto.setMonto(gastoDetalles.getMonto());
            gasto.setCategoria(gastoDetalles.getCategoria());
            gasto.setFecha(gastoDetalles.getFecha());

            return ResponseEntity.ok(gastoService.saveGasto(gasto));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGasto(@PathVariable Long id) {
        gastoService.deleteGasto(id);
        return ResponseEntity.noContent().build();
    }
}
