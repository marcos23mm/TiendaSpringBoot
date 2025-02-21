package org.example.tiendaspringboot.Modelo.DTOs;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    /* ¯\_(ツ)_/¯ devuelve la entidad con BigDecimal aún para esta tienda,
    así que vamos a dejar al IntelliJ hacer su magia...*/

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock", nullable = false)
    private Integer stock;


    @OneToMany(mappedBy = "producto")
    @JsonManagedReference
    private Set<Historial> historials = new LinkedHashSet<>();

    public Producto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Historial> getHistorials() {
        return historials;
    }

    public void setHistorials(Set<Historial> historials) {
        this.historials = historials;
    }

}