package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Venta} entity.
 */
public class VentaDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    private String tipoPago;

    private Double total;

    private CocheDTO coches;

    private EmpleadoDTO empleado;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public CocheDTO getCoches() {
        return coches;
    }

    public void setCoches(CocheDTO coches) {
        this.coches = coches;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VentaDTO)) {
            return false;
        }

        VentaDTO ventaDTO = (VentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ventaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VentaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", tipoPago='" + getTipoPago() + "'" +
            ", total=" + getTotal() +
            ", coches=" + getCoches() +
            ", empleado=" + getEmpleado() +
            ", cliente=" + getCliente() +
            "}";
    }
}
