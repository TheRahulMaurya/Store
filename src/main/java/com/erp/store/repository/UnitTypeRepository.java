package com.erp.store.repository;

import com.erp.store.entity.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {
    Optional<UnitType> findByName(String name);
}
