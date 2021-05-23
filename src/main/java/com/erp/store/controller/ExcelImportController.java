package com.erp.store.controller;

import com.erp.store.constant.ApiConstants;
import com.erp.store.exception.ErrorResponse;
import com.erp.store.service.UnitService;
import com.erp.store.serviceImpl.UnitServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin
@Controller
@RequestMapping(ApiConstants.REST_ROOT_EXCEL_END_POINT)
public class ExcelImportController {

    @Autowired
    UnitService unitService;

    /**
     * API Service 1
     * This Api is used to import excel file (Format must be .xslx).
     * @param file excelFile
     * @return Success or error response.
     */
    @PostMapping(ApiConstants.REST_EXCEL_IMPORT_END_POINT)
    public ResponseEntity<?> importExcelApi(@RequestParam("file") MultipartFile file) {
        String message = "";
        JSONObject response = new JSONObject();
        if (UnitServiceImpl.hasExcelFormat(file)) {
            unitService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }

    @GetMapping(ApiConstants.REST_FETCH_ALL_UNIT_END_POINT)
    public ResponseEntity<?> getAllUnitsApi(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam int size
    ) {
        JSONArray unitJsonArray;
        Pageable pageable = PageRequest.of(page, size);
        if (filter == null) {
            unitJsonArray = unitService.getAllUnits(pageable);
        } else {
            unitJsonArray = unitService.getAllUnits(pageable, filter);
        }
        return new ResponseEntity<>(unitJsonArray.toString(), HttpStatus.OK);
    }
}
