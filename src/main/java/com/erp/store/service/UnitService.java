package com.erp.store.service;

import com.erp.store.dto.ExcelDataDto;
import com.erp.store.entity.Unit;
import org.json.JSONArray;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UnitService {

    void save(MultipartFile file);

    void saveUnits(List<Unit> units);

    JSONArray getAllUnits(Pageable pageable, String filter);

    JSONArray getAllUnits(Pageable pageable);

    List<Unit> excelDtoToUnit(Map<Integer, ExcelDataDto> excelDataMap);


}
