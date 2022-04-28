package com.company.apiperson.model.request;

import javax.validation.constraints.NotNull;

public class MarketRQ {

    @NotNull(message="The dniPerson is mandatory")
    private String dniPerson;
    @NotNull(message="The idVehiculo is mandatory")
    private Long idVehiculo;

    public String getDniPerson() {
        return dniPerson;
    }

    public void setDniPerson(String dniPerson) {
        this.dniPerson = dniPerson;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }
}
