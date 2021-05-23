package com.erp.store.service;

import com.erp.store.dto.ExcelDataDto;
import com.erp.store.entity.UnitConversionMapping;

import java.util.List;
import java.util.Map;

public interface UnitConversionMapService {
    void saveUnitConversionMappings(List<UnitConversionMapping> ucmList);

    List<UnitConversionMapping> excelDtoToUnitConvMapping(Map<Integer, ExcelDataDto> excelDataMap);
}
