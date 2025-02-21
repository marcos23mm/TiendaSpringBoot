package org.example.tiendaspringboot.Controlador.Controladores;

import jakarta.validation.Valid;
import org.example.tiendaspringboot.Controlador.Servicios.ClienteService;
import org.example.tiendaspringboot.Modelo.DTOs.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Cliente> getClienteById(@RequestParam Integer id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createCliente(@Valid @ModelAttribute Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Cliente nuevoCliente = clienteService.save(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> updateCliente(
            @RequestParam Integer id,
            @Valid @ModelAttribute Cliente clienteActualizado,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return clienteService.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setApellido(clienteActualizado.getApellido());
                    cliente.setNickname(clienteActualizado.getNickname());
                    cliente.setPassword(clienteActualizado.getPassword());
                    cliente.setTelefono(clienteActualizado.getTelefono());
                    cliente.setDomicilio(clienteActualizado.getDomicilio());
                    Cliente clienteGuardado = clienteService.save(cliente);
                    return ResponseEntity.ok(clienteGuardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> deleteCliente(@RequestParam Integer id) {
        if (clienteService.findById(id).isPresent()) {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

