package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Coche} entity.
 */
public class CocheDTO implements Serializable {

    private Long id;

    private String marca;

    private String modelo;

    private String color;

    private String numeroSerie;

    private Double precio;

    private Boolean exposicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getExposicion() {
        return exposicion;
    }

    public void setExposicion(Boolean exposicion) {
        this.exposicion = exposicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CocheDTO)) {
            return false;
        }

        CocheDTO cocheDTO = (CocheDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cocheDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CocheDTO{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", color='" + getColor() + "'" +
            ", numeroSerie='" + getNumeroSerie() + "'" +
            ", precio=" + getPrecio() +
            ", exposicion='" + getExposicion() + "'" +
            "}";
    }
}
