package com.erp.store.constant;

public class ApiConstants {

    /**
     * Restricting instances.
     */
    private ApiConstants(){}

    /************************************************************************************
     *                         Rest End Points
     ************************************************************************************/
    public static final String REST_ROOT_EXCEL_END_POINT = "/api/excel";
    public static final String REST_EXCEL_IMPORT_END_POINT = "/import";
    public static final String REST_FETCH_ALL_UNIT_END_POINT = "/all";

    /************************************************************************************
     *                         Excel Constants
     ************************************************************************************/

    public static final String FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String SHEET = "Sheet0";
    public static final String UNIT_NAME = "UnitName";
    public static final String UNIT_STATUS = "Status";
    public static final String UNIT_DESCRIPTION = "Description";
    public static final String UNIT_TYPE = "Type";

    /************************************************************************************
    *                         Exception Messages
    ************************************************************************************/

    public static final String INVALID_STATUS_MESSAGE = "Invalid status, The status can be 0 or 1 only";
    public static final String INVALID_FILTER_MESSAGE = "Invalid filter, Filter must be related to unit or its type";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error, Please try again after sometime.";

    /************************************************************************************
     *                         String Literals
     ************************************************************************************/



}
