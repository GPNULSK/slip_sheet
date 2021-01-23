package com.example.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class ExcelReaderUtil {

    public static List<Integer> excelToShopIdList(InputStream inputStream) {

        List<Integer> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i < rowLength; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(STRING);
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        System.out.println(data);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
