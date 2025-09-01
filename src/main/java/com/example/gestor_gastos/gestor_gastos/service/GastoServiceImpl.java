package com.example.gestor_gastos.gestor_gastos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestor_gastos.gestor_gastos.model.Gasto;
import com.example.gestor_gastos.gestor_gastos.repository.GastoRepository;

@Service
public class GastoServiceImpl implements GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Override
    public List<Gasto> getGastosByCategoria(String categoria) {
        return gastoRepository.findByCategoria(categoria);
    }

    @Override
    public Gasto saveGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    @Override
    public List<Gasto> getAllGastos() {
        return (List<Gasto>) gastoRepository.findAll();
    }

    @Override
    public void deleteGasto(Long id) {
        if (!gastoRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el gasto con id: " + id);
        }
        gastoRepository.deleteById(id);
    }

    @Override
    public Optional<Gasto> getGastoById(Long id) {
        return gastoRepository.findById(id);
    }

    @Override
    public Double getTotalMesActual() {
        return gastoRepository.getTotalMesActual();
    }

    @Override
    public Double getTotalPorRango(LocalDate inicio, LocalDate fin) {
        return gastoRepository.getTotalPorRango(inicio, fin);
    }

    @Override
    // 👉 método para borrar todos
    public void deleteAllGastos() {
        gastoRepository.deleteAll();
    }
}
