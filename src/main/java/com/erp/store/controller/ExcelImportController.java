package com.erp.store.controller;

import com.erp.store.constant.ApiConstants;
import com.erp.store.exception.ErrorResponse;
import com.erp.store.service.UnitService;
import com.erp.store.serviceImpl.UnitServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Services to import Excel File and save data to DB and view them.
 */
@CrossOrigin
@RestController
@RequestMapping(ApiConstants.REST_ROOT_EXCEL_END_POINT)
public class ExcelImportController {

    private static final Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);

    @Autowired
    UnitService unitService;

    /**
     * ****************** API Service 1 *****************************
     * This Api service is used to import excel file (Format must be .xlsx).
     * @param file excelFile
     * @return Success or error response.
     */
    @PostMapping(ApiConstants.REST_EXCEL_IMPORT_END_POINT)
    public ResponseEntity<?> importExcelApi(@RequestParam("file") MultipartFile file) {
        JSONObject response = new JSONObject();
        logger.info("Importing excel file...");
        if (UnitServiceImpl.hasExcelFormat(file)) {
            unitService.save(file);
            logger.info("Imported successfully : STATUS: {}", HttpStatus.OK.value());
            response.put(
                    ApiConstants.MESSAGE,
                    ApiConstants.FILE_UPLOAD_SUCCESS_MESSAGE + file.getOriginalFilename()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
        logger.info("Invalid File type : STATUS: {}", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ApiConstants.INVALID_FILE_ERROR_MESSAGE
                ));
    }

    /**
     * ****************** API Service 2 ***********
     * This Api service is used to get list of all unit according to the page, size and applied filters.
     * Filters can only be applied on valid unit name and unit types
     * else InvalidFilterException is thrown.
     * @param filter type of filter either unit name or unit type [Optional]
     * @param page page number
     * @param size page size
     * @return List of all the units according to page and filters.
     */
    @GetMapping(ApiConstants.REST_FETCH_ALL_UNIT_END_POINT)
    public ResponseEntity<Map<String, Object>> getAllUnitsApi(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam int size
    ) {
        Map<String, Object> response;
        Pageable pageable = PageRequest.of(page, size);
        if (filter == null) {
            logger.info("Fetching all units without filter, Page: {}, Size: {}",page, size);
            response = unitService.getAllUnits(pageable);
        } else {
            logger.info("Fetching all units :filter: {}, Page: {}, Size: {}", filter, page, size);
            response = unitService.getAllUnits(pageable, filter);
        }
        logger.info("Fetched successfully : STATUS: {}", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
