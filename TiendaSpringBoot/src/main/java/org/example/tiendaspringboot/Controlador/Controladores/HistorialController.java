package org.example.tiendaspringboot.Controlador.Controladores;

import org.example.tiendaspringboot.Controlador.Servicios.HistorialService;
import org.example.tiendaspringboot.Modelo.DTOs.Historial;
import org.example.tiendaspringboot.Modelo.DTOs.HistorialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historiales")
public class HistorialController {

    private final HistorialService historialService;

    @Autowired
    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    @GetMapping
    public ResponseEntity<List<Historial>> getAllHistoriales() {
        return ResponseEntity.ok(historialService.findAll());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Historial> getHistorialById(@RequestParam Integer id) {
        return historialService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> realizarCompra(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId,
            @RequestParam Integer cantidad,
            @RequestParam String tipo,
            @RequestParam String descripcion
    ) {
        HistorialDTO historialDTO = new HistorialDTO();
        historialDTO.setClienteId(clienteId);
        historialDTO.setProductoId(productoId);
        historialDTO.setCantidad(cantidad);
        historialDTO.setTipo(tipo);
        historialDTO.setDescripcion(descripcion);

        return historialService.realizarCompra(historialDTO);
    }

    @PostMapping("/devolver")
    public ResponseEntity<String> realizarDevolucion(
            @RequestParam Integer clienteId,
            @RequestParam Integer productoId,
            @RequestParam Integer cantidad,
            @RequestParam String descripcion
    ) {
        HistorialDTO historialDTO = new HistorialDTO();
        historialDTO.setClienteId(clienteId);
        historialDTO.setProductoId(productoId);
        historialDTO.setCantidad(cantidad);
        historialDTO.setTipo("DEVOLUCION");
        historialDTO.setDescripcion(descripcion);

        return historialService.realizarDevolucion(historialDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarHistorial(
            @RequestParam Integer id,
            @RequestParam Integer cantidad,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String descripcion
    ) {
        HistorialDTO historialDTO = new HistorialDTO();
        historialDTO.setCantidad(cantidad);
        historialDTO.setTipo(tipo);
        historialDTO.setDescripcion(descripcion);

        return historialService.actualizarHistorial(id, historialDTO);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> deleteHistorial(@RequestParam Integer id) {
        if (historialService.findById(id).isPresent()) {
            historialService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

