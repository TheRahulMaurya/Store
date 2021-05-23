package com.erp.store.serviceImpl;

import java.io.IOException;
import java.util.*;

import com.erp.store.constant.ApiConstants;
import com.erp.store.dto.ExcelDataDto;
import com.erp.store.entity.Unit;
import com.erp.store.entity.UnitConversionMapping;
import com.erp.store.entity.UnitType;
import com.erp.store.exception.ExcelImportException;
import com.erp.store.exception.InvalidFilterException;
import com.erp.store.helper.ExcelImportHelper;
import com.erp.store.repository.UnitConversionMapperRepository;
import com.erp.store.repository.UnitRepository;
import com.erp.store.repository.UnitTypeRepository;
import com.erp.store.service.UnitConversionMapService;
import com.erp.store.service.UnitService;
import com.erp.store.util.Converter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UnitServiceImpl implements UnitService {

    private static final Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    UnitTypeRepository unitTypeRepository;

    @Autowired
    UnitConversionMapperRepository ucmRepository;

    @Autowired
    UnitConversionMapService ucmService;

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!ApiConstants.FILE_TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    @Override
    public void save(MultipartFile excelFile) {
        try {
            Map<Integer, ExcelDataDto> excelDataMap = new ExcelImportHelper().excelToExcelDataDto(excelFile.getInputStream());
            List<Unit> units = excelDtoToUnit(excelDataMap);
            saveUnits(units);
            List<UnitConversionMapping> ucmList = ucmService.excelDtoToUnitConvMapping(excelDataMap);
            ucmService.saveUnitConversionMappings(ucmList);
        } catch (IOException e) {
            throw new ExcelImportException("fail to store excel data: " + e.getMessage());
        }
    }

    /**
     * Saving all the units with the types avoiding duplicates.
     *
     * @param units list of units inside excel.
     */
    @Override
    public void saveUnits(List<Unit> units) {
        units.forEach(unit -> {
            UnitType unitType = unit.getUnitType();
            Optional<UnitType> unitTypeOpt = unitTypeRepository.findByName(unitType.getName());
            if (!unitTypeOpt.isPresent()) {
                unitTypeRepository.save(unitType);
            } else {
                unit.setUnitType(unitTypeOpt.get());
            }
            if (!unitRepository.findByName(unit.getName()).isPresent()) {
                unitRepository.save(unit);
            }
        });
    }


    @Override
    public JSONArray getAllUnits(Pageable pageable, String filter) {
        List<Unit> unitList = new ArrayList<>();
        Optional<Unit> unitOptional = unitRepository.findByName(filter);
        if (unitOptional.isPresent()) {
            unitList.add(unitOptional.get());
        } else {
            Optional<UnitType> unitTypeOpt = unitTypeRepository.findByName(filter);
            if (unitTypeOpt.isPresent()) {
                unitList = unitRepository.findByUnitType(unitTypeOpt.get());
            } else {
                throw new InvalidFilterException(ApiConstants.INVALID_FILTER_MESSAGE);
            }
        }
        return getUnitJsonArrayFromUnitList(unitList);
    }

    @Override
    public JSONArray getAllUnits(Pageable pageable) {
        List<Unit> units = unitRepository.findAll(pageable).getContent();
        return getUnitJsonArrayFromUnitList(units);
    }

    private JSONArray getUnitJsonArrayFromUnitList(List<Unit> units) {
        JSONArray unitJsonArray = new JSONArray();
        units.forEach(unit -> {
            JSONObject unitJson = new JSONObject();
            unitJson.put(ApiConstants.UNIT_NAME, unit.getName());
            unitJson.put(ApiConstants.UNIT_STATUS, Converter.booleanToInt(unit.isStatus()));
            unitJson.put(ApiConstants.UNIT_DESCRIPTION, unit.getDescription());
            unitJson.put(ApiConstants.UNIT_TYPE, unit.getUnitType().getName());
            unitJsonArray.put(unitJson);
        });
        return unitJsonArray;
    }

    /**
     * @param excelDataMap
     * @return
     */
    @Override
    public List<Unit> excelDtoToUnit(Map<Integer, ExcelDataDto> excelDataMap) {
        List<Unit> units = new ArrayList<>();
        excelDataMap.values().forEach(excelDataDto -> {
            Unit unit = ExcelImportHelper.getUnitFromExcelDto(excelDataDto);
            units.add(unit);
        });
        logger.info(units.toString());
        return units;
    }
}
