
package model;

import java.time.LocalDate;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reparacion {

    private int id;
    @JsonProperty("fecha_ingreso")
    private LocalDate fechaIngreso;
    private String descripcion;
    private BigDecimal costo;
    private String estado;
    private String observaciones;

    @JsonProperty("vehiculo_id")
    private int vehiculoId;

    // Constructor vacío
    public Reparacion() {
    }

    // Constructor con todos los atributos
    public Reparacion(int id, LocalDate fechaIngreso,
            String descripcion, BigDecimal costo,
            String estado, String observaciones, int vehiculoId) {

        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.descripcion = descripcion;
        this.costo = costo;
        this.estado = estado;
        this.observaciones = observaciones;
        this.vehiculoId = vehiculoId;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
}
