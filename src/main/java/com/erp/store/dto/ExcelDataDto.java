package com.erp.store.dto;

/**
 * Dto class to hold excel data.
 * <p>Considering conversionId and ConversionValue are not greater
 * than long and Double limit else we can use BigInteger and BigDecimal.</p>
 */
public class ExcelDataDto {

    private int rowNumber;

    private String unitName;

    private boolean status;

    private String description;

    private String type;

    private Integer conversionUid;

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
