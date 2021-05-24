package com.erp.store.dto;

import com.erp.store.constant.ApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Dto class to hold excel data.
 * <p>Considering conversionId and ConversionValue are not greater
 * than long and Double limit else we can use BigInteger and BigDecimal.</p>
 */
public class ExcelDataDto {

    @JsonIgnore
    private int rowNumber;

    @JsonProperty(value = ApiConstants.UNIT_NAME)
    private String unitName;

    @JsonProperty(value = ApiConstants.UNIT_STATUS)
    private int status;

    @JsonProperty(value = ApiConstants.UNIT_DESCRIPTION)
    private String description;

    @JsonProperty(value = ApiConstants.UNIT_TYPE)
    private String type;

    @JsonIgnore
    private Integer conversionUid;

    @JsonIgnore
    private Double conversionValue;

    // Getter and Setters


    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getConversionUid() {
        return conversionUid;
    }

    public void setConversionUid(Integer conversionUid) {
        this.conversionUid = conversionUid;
    }

    public Double getConversionValue() {
        return conversionValue;
    }

    public void setConversionValue(Double conversionValue) {
        this.conversionValue = conversionValue;
    }


    @Override
    public String toString() {
        return "ExcelDataDto{" +
                "rowNumber=" + rowNumber +
                ", unitName='" + unitName + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", conversionUid=" + conversionUid +
                ", conversionValue=" + conversionValue +
                '}';
    }
}
