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
import org.springframework.data.domain.Page;
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
        return (ApiConstants.FILE_TYPE.equals(file.getContentType()));
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
    public Map<String, Object> getAllUnits(Pageable pageable, String filter) {
        List<Unit> unitList;
        Page<Unit> unitPage;
        Optional<Unit> unitOptional = unitRepository.findByName(filter);
        if (unitOptional.isPresent()) {
            unitPage = unitRepository.findByName(filter, pageable);
            unitList = unitPage.getContent();
        } else {
            Optional<UnitType> unitTypeOpt = unitTypeRepository.findByName(filter);
            if (unitTypeOpt.isPresent()) {
                unitPage = unitRepository.findByUnitType(unitTypeOpt.get(), pageable);
                unitList = unitPage.getContent();
            } else {
                throw new InvalidFilterException(ApiConstants.INVALID_FILTER_MESSAGE);
            }
        }
        return getUnitPages(
                unitPage,
                getUnitAsExcelFormat(unitList)
        );
    }

    @Override
    public Map<String, Object> getAllUnits(Pageable pageable) {
        Page<Unit> unitPage = unitRepository.findAll(pageable);
        List<Unit> unitList = unitPage.getContent();
        return getUnitPages(
                unitPage,
                getUnitAsExcelFormat(unitList)
        );
    }

    /**
     * Get Units in the form of excel.
     * @param units
     * @return
     */
    private List<ExcelDataDto> getUnitAsExcelFormat(List<Unit> units) {
        List<ExcelDataDto> excelDataDtos = new ArrayList<>();
        units.forEach(unit -> {
            ExcelDataDto excelDataDto = new ExcelDataDto();
            excelDataDto.setUnitName(unit.getName());
            excelDataDto.setStatus(Converter.booleanToInt(unit.isStatus()));
            excelDataDto.setDescription(unit.getDescription());
            excelDataDto.setType(unit.getUnitType().getName());
            excelDataDtos.add(excelDataDto);
        });
        return excelDataDtos;
    }

    /**
     * Get Unit Pages.
     * @param unitPage
     * @param excelDataDtos
     * @return
     */
    private Map<String, Object> getUnitPages(Page<Unit> unitPage, List<ExcelDataDto> excelDataDtos){
        Map<String, Object> unitPageMap = new HashMap<>();
        unitPageMap.put(ApiConstants.UNITS, excelDataDtos);
        unitPageMap.put(ApiConstants.CURRENT_PAGE, unitPage.getNumber());
        unitPageMap.put(ApiConstants.TOTAL_ITEMS, unitPage.getTotalElements());
        unitPageMap.put(ApiConstants.TOTAL_PAGES, unitPage.getTotalPages());
        return unitPageMap;
    }

    /**
     * Excel DTO to Unit entity conversion.
     * @param excelDataMap map with key as row number and value as ExcelDataDto.
     * @return List of units.
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
