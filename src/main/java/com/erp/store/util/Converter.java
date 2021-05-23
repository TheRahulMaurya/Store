package com.erp.store.util;

import com.erp.store.constant.ApiConstants;
import com.erp.store.exception.InvalidStatusException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class Converter {

    /**
     * Converting integer status into boolean
     * @param intStatus status as integer.
     * @return corresponding status as boolean.
     */
    public static boolean intToBoolean(int intStatus){
        boolean boolStatus;
        if(intStatus == 0) {
            boolStatus = false;
        }else if(intStatus == 1){
            boolStatus = true;
        }else{
            throw new InvalidStatusException(ApiConstants.INVALID_STATUS_MESSAGE);
        }
        return boolStatus;
    }

    /**
     * Converting boolean status into int
     * @param intStatus status as integer.
     * @return corresponding status as boolean.
     */
    public static int booleanToInt(boolean boolStatus){
        int intStatus;
        if(boolStatus) {
            intStatus = 1;
        }else{
            intStatus = 0;
        }
        return intStatus;
    }

    /**
     * Converting String to Long.
     * @param cell current cell.
     * @return Long value if valid string else null.
     */
    public static Integer getIntegerFromCell(Cell cell){
        Integer intValue;
        // check if cell is blank
        if(cell == null || cell.getCellType() == CellType.BLANK) {
            intValue = null;
        }else{
            intValue = (int) cell.getNumericCellValue();
        }
        return intValue;
    }

    /**
     * Converting Double to Long.
     * @param cell current cell
     * @return Double value if valid string else null.
     */
    public static Double getDoubleFromCell(Cell cell){
        Double doubleValue;
        // check if cell is blank
        if(cell == null || cell.getCellType() == CellType.BLANK) {
            doubleValue = null;
        }else{
            if(cell.getCellType() == CellType.STRING){
                System.out.println("cell : "+cell.getStringCellValue());
                doubleValue = Double.parseDouble(cell.getStringCellValue());
            }else{
                doubleValue = cell.getNumericCellValue();
            }
        }
        return doubleValue;
    }

}
