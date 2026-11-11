package com.ejemplo.prestamos.repositorio;

import com.ejemplo.prestamos.dominio.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {
    boolean existsByReferencia(String referencia);
}