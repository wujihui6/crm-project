package com.wu;

import com.wu.crm.commons.utils.HSSFUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;

public class paserexceltest {
    public static void main(String[] args) throws  Exception {
        FileInputStream is = new FileInputStream("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\activities.xls");
        //将文件中的所有数据封装到sheets中去
        HSSFWorkbook wb = new HSSFWorkbook(is);
        //获取当前页的数据
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        for(int i = 0; i < sheet.getLastRowNum(); i++){
            //逐次获取每行的数据
            //取出每行的数据放入到row中
            row = sheet.getRow(i);
            for(int j = 0; j < row.getLastCellNum(); j++){
                //取出每列的数据，放入到cell中
                cell = row.getCell(j);
                System.out.print(HSSFUtils.getCellValueForstr(cell));

            }
            System.out.println();
        }
        wb.close();
        is.close();
    }
}
