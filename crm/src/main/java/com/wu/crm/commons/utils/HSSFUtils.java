package com.wu.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class HSSFUtils {
    public static String getCellValueForstr(HSSFCell cell){
        String ret="";
        if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
            ret = cell.getStringCellValue();
        }else if ( cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
           ret =  cell.getBooleanCellValue() + "";
        }else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
            ret =  cell.getNumericCellValue() + "";
        }else {
            ret = " " + " ";
        }
        return ret;
    }
}
