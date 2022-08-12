package com.wu;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class excelTest {
    public static void main(String[] args) {
        //创建HSSFWorkbook对象，对应一个excel文件
        HSSFWorkbook book = new HSSFWorkbook();
        //使用book创建HSSFSheet对象，对应book中的一页
        HSSFSheet sheet = book.createSheet("学生信息");
        //使用sheet创建HSSFRow对象，对应sheet中的一行
        HSSFRow row = sheet.createRow(0);//行号，从0开始，一次增加
        //使用sheet创建HSSFRow对象，对应row中的一行
        HSSFCell cell = row.createCell(0);//列号，从0开始，一次增加
        cell.setCellValue("学号");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("年龄");
        //设置表格样式
        HSSFCellStyle cellStyle = book.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//剧中对齐

        for(int i = 1; i < 10; i++){
            row = sheet.createRow(i);
             cell = row.createCell(0);
            cell.setCellValue("学号" + i);
            cell = row.createCell(1);
            cell.setCellValue("姓名" + i);
            cell = row.createCell(2);
            cell.setCellValue("年龄" + i);
        }

        try {
            FileOutputStream os = new FileOutputStream("D://students.xls");
            book.write(os);

            os.close();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("===============执行成功===============");
    }
}
