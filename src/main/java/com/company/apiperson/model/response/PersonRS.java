package com.company.apiperson.model.response;

import java.util.Date;
import java.util.List;

public class PersonRS {

    private Long id;

    private String dni;

    private String nombre;

    private Date fechaNacimiento;

    private List<VehiculoRS> vehiculosRS;

    public List<VehiculoRS> getVehiculosRS() {
        return vehiculosRS;
    }

    public void setVehiculosRS(List<VehiculoRS> vehiculosRS) {
        this.vehiculosRS = vehiculosRS;
    }

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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}