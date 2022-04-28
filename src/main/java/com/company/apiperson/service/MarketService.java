package com.company.apiperson.service;

import com.company.apiperson.model.entity.Person;
import com.company.apiperson.model.entity.VehiculoStock;
import com.company.apiperson.model.request.MarketRQ;
import com.company.apiperson.model.response.VehiculoRS;
import com.company.apiperson.model.response.VehiculoStockRS;
import com.company.apiperson.service.Mapper;
import com.company.apiperson.service.PersonService;
import com.company.apiperson.service.VehiculoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MarketService {

    @Autowired
    private VehiculoStockService vehiculoStockService;

    @Autowired
    private PersonService personService;

    @Autowired
    private Mapper mapper;

    public List<VehiculoStockRS> getVehiculosStock(){
        return vehiculoStockService.getAllVehiculosStock();
    }

    @Transactional
    public void compraVehiculoPerson(MarketRQ marketRQ) {
        VehiculoStock vehiculoStock = vehiculoStockService.getVehiculoStockById(marketRQ.getIdVehiculo());

        vehiculoStock.setUnidades(vehiculoStock.getUnidades()-1);

        Person person = personService.getPersonByDni(marketRQ.getDniPerson());

        person.getVehiculosPerson().add(mapper.vehiculoStockToVehiculoPerson(vehiculoStock));

        personService.savePersonVehiculos(person);

        vehiculoStockService.saveVehiculoStock(vehiculoStock);
    }

}
