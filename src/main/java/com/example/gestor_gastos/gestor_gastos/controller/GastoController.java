package com.example.gestor_gastos.gestor_gastos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestor_gastos.gestor_gastos.model.Gasto;
import com.example.gestor_gastos.gestor_gastos.service.GastoService;

import jakarta.servlet.http.HttpServletResponse;

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

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=gastos.xlsx";
        response.setHeader(headerKey, headerValue);

        List<Gasto> listaGastos = gastoService.getAllGastos();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Gastos");

        Row headerRow = sheet.createRow(0);
        String[] columnas = { "Descripción", "Monto", "Categoría", "Fecha" };
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        int rowIdx = 1;
        for (Gasto gasto : listaGastos) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(1).setCellValue(gasto.getDescripcion());
            row.createCell(2).setCellValue(gasto.getMonto());
            row.createCell(3).setCellValue(gasto.getCategoria());
            row.createCell(4).setCellValue(gasto.getFecha().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
    @GetMapping("/export/csv")
public void exportToCSV(HttpServletResponse response) throws IOException {
    response.setContentType("text/csv");
    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=gastos.csv";
    response.setHeader(headerKey, headerValue);

    List<Gasto> listaGastos = gastoService.getAllGastos();

    PrintWriter writer = response.getWriter();
    writer.println("Descripción,Monto,Categoría,Fecha");

    for (Gasto gasto : listaGastos) {
        writer.println(            
            gasto.getDescripcion() + "," +
            gasto.getMonto() + "," +
            gasto.getCategoria() + "," +
            gasto.getFecha()
        );
    }

    writer.flush();
    writer.close();
}


}
