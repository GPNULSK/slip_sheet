package com.example.utils;


import com.example.domain.Initial;
import com.example.service.InitService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderUtil {

    @Autowired
    private InitService initService;

    //通过对单元格遍历的形式来获取信息 ，这里要判断单元格的类型才可以取出值
    public static  List<Initial> readExcel(InputStream inputStream) throws Exception {

        //两个控制sheet循环和单元格循环的下标标志
        int i = 0;
        int m = 0;
        Initial initial;
        List<Initial> initialList = new ArrayList<>();

        //InputStream inputStream = file.getInputStream();
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wb.getSheetAt(0);
        for (Iterator ite = sheet.rowIterator(); ite.hasNext(); ) {
            HSSFRow row = (HSSFRow) ite.next();
            System.out.println();
            m++;
            i = 0;
            initial = new Initial();
            for (Iterator itet = row.cellIterator(); itet.hasNext(); ) {
                i++;
                HSSFCell cell = (HSSFCell) itet.next();
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        //先看是否是日期格式
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            //读取日期格式
                            initial.setArrivalDate(cell.getDateCellValue());
                        } else {
                            //读取数字
                            System.out.println("读取了数字，注意！");
                        }
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        //读取公式
                        System.out.println("读取了公式，注意!");
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        //读取String
                        if (m !=1){
                            if (i == 1){
                                initial.setProviderCode(cell.getRichStringCellValue().toString());
                            }else if (i == 2){
                                initial.setProviderName(cell.getRichStringCellValue().toString());
                            }else if (i == 3){
                                initial.setBatchCode(cell.getRichStringCellValue().toString());
                            }else if(i == 4){
                                System.out.println(cell.getRichStringCellValue().toString());
                                initial.setMaterialCode(cell.getRichStringCellValue().toString());
                            }else if (i == 5){
                                initial.setMaterialName(cell.getRichStringCellValue().toString());
                            }else if (i == 6){
                                initial.setMaterialGrade(cell.getRichStringCellValue().toString());
                            }else if (i == 7){
                                initial.setStatus(cell.getRichStringCellValue().toString());
                            }else if (i == 8){
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                Date date =  simpleDateFormat.parse(cell.getRichStringCellValue().toString());
                                initial.setArrivalDate(date);
                            }else if (i == 9){
                                initial.setOperator(cell.getRichStringCellValue().toString());
                            }else if (i == 10){
                                String isReturn = cell.getRichStringCellValue().toString();
                                if ("是".equals(isReturn)){
                                    initial.setTestReturn(true);
                                }
                                if ("否".equals(isReturn)){
                                    initial.setTestReturn(false);
                                }
                                initial.setIsShow(1);
                                initialList.add(initial);
                            }
                        }
                        break;
                }
            }
        }

        return initialList;
    }

}
