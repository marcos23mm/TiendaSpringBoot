package org.example.tiendaspringboot.Controlador.Controladores;

import org.example.tiendaspringboot.Controlador.Servicios.ClienteService;
import org.example.tiendaspringboot.Controlador.Servicios.HistorialService;
import org.example.tiendaspringboot.Controlador.Servicios.ProductoService;
import org.example.tiendaspringboot.Modelo.DTOs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historial")
public class HistorialController {
    private final HistorialService historialService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    @Autowired
    public HistorialController(HistorialService historialService, ClienteService clienteService, ProductoService productoService) {
        this.historialService = historialService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @GetMapping
    public List<Historial> getHistorial() {
        return historialService.findAll();
    }
    @GetMapping("/{id}")
    public Historial getHistorialById(@PathVariable int id) {
        Optional<Historial> historial = historialService.findById(id);
        return historial.orElse(null);
    }
    @PostMapping("/historial_compra")
    public ResponseEntity<String> compraHistorial(@Valid @RequestBody HistorialDTO historialDTO) {
        return historialService.realizarCompra(historialDTO);
    }

    @PostMapping("/historial_devolucion")
    public ResponseEntity<String> devolucionHistorial(@Valid @RequestBody HistorialDTO historialDTO) {
        return historialService.realizarDevolucion(historialDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarHistorial(@PathVariable int id, @Valid @RequestBody HistorialDTO historialDTO) {
        return historialService.actualizarHistorial(id, historialDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarHistorial(@PathVariable int id) {
        if(!historialService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }else{
            historialService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}


