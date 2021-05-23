package com.erp.store.repository;

import com.erp.store.entity.Unit;
import com.erp.store.entity.UnitConversionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitConversionMapperRepository extends JpaRepository<UnitConversionMapping, Long> {

    Optional<UnitConversionMapping> findByConvertedFromAndConvertedTo(Unit convertedFrom, Unit convertedTo);
}
