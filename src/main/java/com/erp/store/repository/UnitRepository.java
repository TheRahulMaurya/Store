package com.erp.store.repository;

import com.erp.store.entity.Unit;
import com.erp.store.entity.UnitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByName(String name);

    Page<Unit> findByName(String name, Pageable pageable);

    Page<Unit> findByUnitType(UnitType unitType, Pageable pageable);

    Page<Unit> findAll(Pageable pageable);
}
