package org.example.tiendaspringboot.Controlador.Servicios;

import org.example.tiendaspringboot.Modelo.DTOs.Cliente;
import org.example.tiendaspringboot.Modelo.Repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Cacheable("cliente_cache")
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Cacheable(value = "cliente_cache", key = "#id")
    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void delete(Integer id) {
        clienteRepository.deleteById(id);
    }
}