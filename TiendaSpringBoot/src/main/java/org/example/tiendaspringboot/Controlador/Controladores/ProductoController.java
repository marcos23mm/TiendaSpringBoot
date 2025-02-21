package org.example.tiendaspringboot.Controlador.Controladores;

import jakarta.validation.Valid;
import org.example.tiendaspringboot.Controlador.Servicios.ProductoService;
import org.example.tiendaspringboot.Modelo.DTOs.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Producto> getProductoById(@RequestParam Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createProducto(@Valid @ModelAttribute Producto producto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updateProducto(
            @RequestParam Integer id,
            @Valid @ModelAttribute Producto productoActualizado,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return productoService.findById(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    Producto productoGuardado = productoService.save(producto);
                    return ResponseEntity.ok(productoGuardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> deleteProducto(@RequestParam Integer id) {
        if (productoService.findById(id).isPresent()) {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
