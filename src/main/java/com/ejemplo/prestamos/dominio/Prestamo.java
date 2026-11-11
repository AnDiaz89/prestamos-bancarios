package com.ejemplo.prestamos.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
    name = "prestamos",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_prestamo_referencia",
            columnNames = "referencia"
        )
    }
)
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String referencia;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @Column(name = "tasa_anual", nullable = false, precision = 9, scale = 6)
    private BigDecimal tasaAnual;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Column(nullable = false, length = 20)
    private String estado = "SOLICITADO";

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    protected Prestamo() {
    }

    public Prestamo(
        String referencia,
        BigDecimal monto,
        BigDecimal tasaAnual,
        Integer plazoMeses
    ) {
        this.referencia = referencia;
        this.monto = monto;
        this.tasaAnual = tasaAnual;
        this.plazoMeses = plazoMeses;
        this.estado = "SOLICITADO";
        this.fechaSolicitud = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getReferencia() {
        return referencia;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public BigDecimal getTasaAnual() {
        return tasaAnual;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}