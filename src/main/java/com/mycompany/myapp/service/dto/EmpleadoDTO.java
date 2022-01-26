package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Empleado} entity.
 */
public class EmpleadoDTO implements Serializable {

    private Long id;

    private String dni;

    private String nombre;

    private Boolean activo;

    private Integer numeroVentas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getNumeroVentas() {
        return numeroVentas;
    }

    public void setNumeroVentas(Integer numeroVentas) {
        this.numeroVentas = numeroVentas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpleadoDTO)) {
            return false;
        }

        EmpleadoDTO empleadoDTO = (EmpleadoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empleadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpleadoDTO{" +
            "id=" + getId() +
            ", dni='" + getDni() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", activo='" + getActivo() + "'" +
            ", numeroVentas=" + getNumeroVentas() +
            "}";
    }
}
