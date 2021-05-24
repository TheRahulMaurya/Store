package com.erp.store.helper;

import com.erp.store.constant.ApiConstants;
import com.erp.store.dto.ExcelDataDto;
import com.erp.store.entity.Unit;
import com.erp.store.entity.UnitConversionMapping;
import com.erp.store.entity.UnitType;
import com.erp.store.exception.ExcelImportException;
import com.erp.store.repository.UnitRepository;
import com.erp.store.util.Converter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelImportHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImportHelper.class);

    public static Unit getUnitFromExcelDto(ExcelDataDto excelData){
        Unit unit = new Unit();
        UnitType unitType = new UnitType();
        unit.setName(excelData.getUnitName());
        unit.setStatus(Converter.intToBoolean(excelData.getStatus()));
        unit.setDescription(excelData.getDescription());
        unitType.setName(excelData.getType());
        unit.setUnitType(unitType);
        return unit;
    }

    public Map<Integer, ExcelDataDto> excelToExcelDataDto(InputStream is) {
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(ApiConstants.SHEET);
            Iterator<Row> rows = sheet.iterator();
            Map<Integer, ExcelDataDto> excelDtoMap = new HashMap<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                logger.info("Processing Row number : {}", currentRow.getRowNum());
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                ExcelDataDto excelDataDto = new ExcelDataDto();
                excelDataDto.setRowNumber(currentRow.getRowNum());
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            excelDataDto.setUnitName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            excelDataDto.setStatus((int) currentCell.getNumericCellValue());
                            break;
                        case 2:
                            excelDataDto.setDescription(currentCell.getStringCellValue());
                            break;
                        case 3:
                            excelDataDto.setType(currentCell.getStringCellValue());
                            break;
                        case 4:
                            excelDataDto.setConversionUid(Converter.getIntegerFromCell(currentCell));
                            break;
                        case 5:
                            excelDataDto.setConversionValue(Converter.getDoubleFromCell(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                excelDtoMap.put(currentRow.getRowNum(), excelDataDto);
            }
            return excelDtoMap;
        } catch (IOException e) {
            throw new ExcelImportException(ApiConstants.EXCEL_IMPORT_EXCEPTION_MESSAGE + e.getMessage());
        }
    }
}
