package com.example.controller;

import com.example.domain.Initial;
import com.example.domain.User;
import com.example.service.InitService;
import com.example.utils.ExcelReaderUtil;
import com.example.utils.ExcelUtil;
import com.example.utils.OfficeUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.apache.poi.ss.usermodel.CellType.STRING;

@CrossOrigin
@RestController
public class InitController {

    @Autowired
    private InitService initService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 录入到中间库
     * @param initial
     * @return
     */
    @RequestMapping("/record")
    public String record( Initial initial){
        initial.setIsReturn("否");
        int record = initService.record(initial);
        if (record >0 ){
            return "SUCCESS";

        }else {
            return "FAIL";
        }
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<Initial> findAll(){
        return initService.findAll();
    }

    @RequestMapping("/search")
    public List<Initial> search(Initial initial){
        List<Initial> search = initService.search(initial);
        return search;
    }

    @RequestMapping("/uploadExcel")
    public boolean Listexcel2Supplier(@RequestParam("excelFile") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();  //获得上传的excel文件名
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);  //获取上传的excel文件名后缀

        List<User> list = null;

        if("xlsx".equals(fileSuffix)) {
            logger.info("excel2007及以上版本");

            XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream()); //获取excel工作簿

            XSSFSheet xssfSheet = xwb.getSheetAt(0); //获取excel的sheet

            if(xssfSheet == null) {
                return false;
            }

            list = new ArrayList<User>();

            //循环获取excel每一行
            for(int rowNum = 1; rowNum < xssfSheet.getLastRowNum()+1; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if(xssfRow == null) {
                    continue;
                }

               User user=new User();

                //循环获取excel每一行的每一列
                for(int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
                    XSSFCell xssCell = xssfRow.getCell(cellNum);
                    if(xssCell == null) {
                        continue;
                    }

                    if(cellNum == 0) {
                        user.setId((Integer) ExcelUtil.getXSSFValue(xssCell));
                    }else if(cellNum == 1) {
                        user.setUsername((String)ExcelUtil.getXSSFValue(xssCell));
                    }else if(cellNum == 2) {
                        //Integer类型需要自行处理
                        String res = String.valueOf((Double)ExcelUtil.getXSSFValue(xssCell));
                        Integer.parseInt(res.substring(0, res.length()-2));
                        user.setAge(Integer.parseInt(res.substring(0, res.length()-2)));
                    }else if(cellNum == 3) {
                        user.setPassport((String)ExcelUtil.getXSSFValue(xssCell));
                    }

                    System.out.print(" "+ExcelUtil.getXSSFValue(xssCell));

                }
                list.add(user);  //将excel每一行的数据封装到user对象,并将user对象添加到list
                System.out.println("");
            }
        }else if("xls".equals(fileSuffix)) {
            logger.info("excel2003版本");

            Workbook wb=new HSSFWorkbook(file.getInputStream()); //获取excel工作簿

            Sheet sheet=wb.getSheetAt(0);  //获取excel的sheet

            if(sheet==null) {
                return false;
            }

            list = new ArrayList<User>();

            //循环获取excel每一行
            for(int rowNum=1;rowNum<sheet.getLastRowNum()+1;rowNum++) {
                Row row=sheet.getRow(rowNum);
                if(row==null) {
                    continue;
                }

                User user = new User();

                //循环获取excel每一行的每一列
                for(int cellNum=0;cellNum<row.getLastCellNum();cellNum++) {
                    Cell cell=row.getCell(cellNum);
                    if(cell==null) {
                        continue;
                    }

                    if(cellNum==0) {
                        user.setId((Integer) ExcelUtil.getValue(cell));
                    }else if(cellNum==1) {
                        user.setUsername((String)ExcelUtil.getValue(cell));
                    }else if(cellNum==2) {
                        //Integer类型需要自行处理
                        String res = String.valueOf((Double)ExcelUtil.getValue(cell));
                        Integer.parseInt(res.substring(0, res.length()-2));
                        user.setAge(Integer.parseInt(res.substring(0, res.length()-2)));
                    }else if(cellNum==3) {
                        user.setPassport((String)ExcelUtil.getValue(cell));
                    }

                    System.out.print(" "+ExcelUtil.getValue(cell));
                }
                list.add(user);    //将excel每一行的数据封装到user对象,并将user对象添加到list
                System.out.println("");
            }

        }
        //将list批量添加到数据库
        System.out.println(list);

        return true;
    }

    @RequestMapping("/changeStatus")
    public String changeStatus(int id,String testReturn){
        Initial initial = new Initial();
        initial.setId(id);
        if ("false".equals(testReturn)){
            initial.setTestReturn(false);
        }else if("true".equals(testReturn)){
            initial.setTestReturn(true);
        }
        String flag="false";
        boolean f = false;
        if (initial.isTestReturn()){
            f = true;
        }
        initial.setTestReturn(f);
        int i = initService.changeStatus(initial);
        System.out.println(i);
       if (i>0){
           flag = "success";
       }
        return flag;
    }
}
