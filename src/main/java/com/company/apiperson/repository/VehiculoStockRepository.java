package com.company.apiperson.repository;

import com.company.apiperson.model.entity.VehiculoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface VehiculoStockRepository extends JpaRepository<VehiculoStock, Long> {

    @Query("SELECT v FROM VehiculoStock v WHERE v.id = :id and v.unidades > 0")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<VehiculoStock> findByIdAndUnidadesGreaterThanZero(@Param("id") Long id);
}
