package com.example.gestor_gastos.gestor_gastos.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
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
import org.springframework.web.multipart.MultipartFile;

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
                            gasto.getFecha());
        }

        writer.flush();
        writer.close();
    }

    @PostMapping("/import/excel")
public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
        return ResponseEntity.badRequest().body("El archivo está vacío");
    }
    try (InputStream inputStream = file.getInputStream();
         Workbook workbook = new XSSFWorkbook(inputStream)) {

        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // saltamos cabecera
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Gasto gasto = new Gasto();
            gasto.setDescripcion(row.getCell(1).getStringCellValue());  // ahora columna 0
            gasto.setMonto(row.getCell(2).getNumericCellValue());       // ahora columna 1
            gasto.setCategoria(row.getCell(3).getStringCellValue());    // ahora columna 2
            gasto.setFecha(LocalDate.parse(row.getCell(4).getStringCellValue())); // ahora columna 3

            gastoService.saveGasto(gasto);
        }
        return ResponseEntity.ok("Archivo Excel importado correctamente");
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error al importar: " + e.getMessage());
    }
}


    @PostMapping("/import/csv")
public ResponseEntity<String> importCSV(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
        return ResponseEntity.badRequest().body("El archivo está vacío");
    }
    try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
        String line;
        boolean firstLine = true;
        while ((line = br.readLine()) != null) {
            if (firstLine) { // saltar cabecera
                firstLine = false;
                continue;
            }
            String[] fields = line.split(",");

            if (fields.length < 4) continue; // seguridad

            Gasto gasto = new Gasto();
            gasto.setDescripcion(fields[0].trim());
            gasto.setMonto(Double.parseDouble(fields[1].trim()));
            gasto.setCategoria(fields[2].trim());
            gasto.setFecha(LocalDate.parse(fields[3].trim())); // formato yyyy-MM-dd

            gastoService.saveGasto(gasto);
        }
        return ResponseEntity.ok("Archivo CSV importado correctamente");
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error al importar: " + e.getMessage());
    }
}


}
