package com.company.apiperson.service;

import com.company.apiperson.exception.ServiceException;
import com.company.apiperson.model.entity.VehiculoStock;
import com.company.apiperson.model.response.VehiculoStockRS;
import com.company.apiperson.repository.VehiculoStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoStockService {

    @Autowired
    private VehiculoStockRepository vehiculoStockRepository;

    @Autowired
    private Mapper mapper;

    public List<VehiculoStockRS> getAllVehiculosStock() {
        return vehiculoStockRepository.findAll().stream()
                .map(vehiculoStock -> mapper.vehiculoStockToVehiculoStockRS(vehiculoStock))
                .collect(Collectors.toList());
    }

    public VehiculoStock getVehiculoStockById(Long id) {
        Optional<VehiculoStock> vehiculoStock = vehiculoStockRepository.findByIdAndUnidadesGreaterThanZero(id);
        if(vehiculoStock.isPresent()) {
            return vehiculoStock.get();
        } else {
            throw new ServiceException(HttpStatus.NOT_FOUND, String.format("Vehiculo with id: '%s' not found", id));
        }
    }

    public VehiculoStock saveVehiculoStock(VehiculoStock vehiculoStock) {
        return vehiculoStockRepository.save(vehiculoStock);
    }
}
