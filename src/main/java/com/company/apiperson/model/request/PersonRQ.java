package com.company.apiperson.model.request;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class PersonRQ {

    private Long id;
    @NotNull(message="The dni is mandatory")
    private String dni;
    @NotNull(message="The nombre is mandatory")
    private String nombre;
    @NotNull(message="The fechaNacimiento is mandatory")
    private Date fechaNacimiento;

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