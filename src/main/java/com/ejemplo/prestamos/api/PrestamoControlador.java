package com.ejemplo.prestamos.api;

import com.ejemplo.prestamos.dominio.Prestamo;
import com.ejemplo.prestamos.repositorio.PrestamoRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoControlador {

    private final PrestamoRepositorio repositorio;

    public PrestamoControlador(PrestamoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Prestamo> listar() {
        return repositorio.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prestamo crear(@Valid @RequestBody CrearPrestamo solicitud) {
        if (repositorio.existsByReferencia(solicitud.referencia())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Ya existe un préstamo con esa referencia"
            );
        }

        Prestamo prestamo = new Prestamo(
            solicitud.referencia(),
            solicitud.monto(),
            solicitud.tasaAnual(),
            solicitud.plazoMeses()
        );

        return repositorio.save(prestamo);
    }

    @PatchMapping("/{id}/decision")
    public Prestamo decidir(
        @PathVariable Long id,
        @Valid @RequestBody DecisionPrestamo solicitud
    ) {
        Prestamo prestamo = repositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "No se encontró el préstamo"
            ));

        if (!"SOLICITADO".equals(prestamo.getEstado())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Solo se pueden decidir préstamos solicitados"
            );
        }

        String nuevoEstado = solicitud.decision().equals("APROBAR")
            ? "APROBADO"
            : "RECHAZADO";

        prestamo.setEstado(nuevoEstado);
        return repositorio.save(prestamo);
    }

    public record CrearPrestamo(
        @NotBlank
        @Size(max = 40)
        String referencia,

        @NotNull
        @Positive
        @Digits(integer = 17, fraction = 2)
        BigDecimal monto,

        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        @Digits(integer = 3, fraction = 6)
        BigDecimal tasaAnual,

        @NotNull
        @Positive
        @Max(600)
        Integer plazoMeses
    ) {
    }

    public record DecisionPrestamo(
        @NotBlank
        @Pattern(regexp = "APROBAR|RECHAZAR")
        String decision
    ) {
    }
}