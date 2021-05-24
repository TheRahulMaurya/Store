package com.erp.store.serviceImpl;

import com.erp.store.dto.ExcelDataDto;
import com.erp.store.entity.Unit;
import com.erp.store.entity.UnitConversionMapping;
import com.erp.store.repository.UnitConversionMapperRepository;
import com.erp.store.repository.UnitRepository;
import com.erp.store.repository.UnitTypeRepository;
import com.erp.store.service.UnitConversionMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UnitConversionMapServiceImpl implements UnitConversionMapService {

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    UnitConversionMapperRepository ucmRepository;

    /**
     * Conversion of excel DTO to UnitConversionMapping entity.
     * @param excelDataMap map with key as row number and value as ExcelDataDto.
     * @return List of UnitConversionMapping Entity
     */
    @Override
    public List<UnitConversionMapping> excelDtoToUnitConvMapping(Map<Integer, ExcelDataDto> excelDataMap){
        List<UnitConversionMapping> ucmList = new ArrayList<>();
        excelDataMap.values().forEach(excelDataDto -> {
            if(excelDataDto.getConversionUid() != null){
                UnitConversionMapping ucm = new UnitConversionMapping();
                Unit convertedFrom;
                Unit convertedTo;
                Optional<Unit> cfUnitOpt = unitRepository.findByName(excelDataDto.getUnitName());
                // Getting unit name from HashMap
                String convertToUnitName = excelDataMap.get(excelDataDto.getConversionUid() - 1).getUnitName();
                Optional<Unit> ctUnitOpt = unitRepository.findByName(convertToUnitName);
                // if Unit is present in the database than only mapping occurs.
                if(cfUnitOpt.isPresent() && ctUnitOpt.isPresent()){
                    convertedFrom = cfUnitOpt.get();
                    convertedTo = ctUnitOpt.get();
                    ucm.setConvertedFrom(convertedFrom);
                    ucm.setConvertedTo(convertedTo);
                    ucm.setValue(excelDataDto.getConversionValue());
                    ucmList.add(ucm);
                }
            }
        });
        return ucmList;
    }

    /**
     * Saving all the unit conversion mappings avoiding duplicates.
     * @param ucmList list of unit conversion mapping inside excel.
     */
    @Override
    public void saveUnitConversionMappings(List<UnitConversionMapping> ucmList){
        ucmList.forEach(ucm -> {
            if(
                    !ucmRepository.findByConvertedFromAndConvertedTo(ucm.getConvertedFrom(),ucm.getConvertedTo())
                            .isPresent()
            ){
                ucmRepository.save(ucm);
            }
        });
    }

}
