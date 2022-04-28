package com.company.apiperson.controller;

import com.company.apiperson.model.request.MarketRQ;
import com.company.apiperson.model.response.VehiculoStockRS;
import com.company.apiperson.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMERCIAL')")
    public ResponseEntity<List<VehiculoStockRS>> getAllVehiculosStock() {
        return ResponseEntity.ok(marketService.getVehiculosStock());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COMERCIAL')")
    public ResponseEntity<Void> compraVehiculoPerson(@Valid @RequestBody MarketRQ marketRQ) {
        marketService.compraVehiculoPerson(marketRQ);
        return ResponseEntity.ok().build();
    }

}
