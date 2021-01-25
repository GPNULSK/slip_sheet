package com.example.controller;

import com.example.domain.Initial;
import com.example.domain.User;
import com.example.service.InitService;
import com.example.utils.ExcelReaderUtil;
import com.example.utils.ExcelUtil;
import com.example.utils.OfficeUtils;
import com.example.utils.PageUtils;
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
       initial.setIsShow(1);
       initial.setTestReturn(true);
        int record = initService.record(initial);
        if (record >0 ){
            return "SUCCESS";

        }else {
            return "FAIL";
        }
    }

    @RequestMapping("/totalAccount")
    public int totalAccount(){
        return initService.totalAccount();
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<Initial> findAll(String curPage,String pageSize){
        PageUtils pageUtils = new PageUtils();
        pageUtils.setPageSize(Integer.parseInt(pageSize));
        pageUtils.setCurPage(Integer.parseInt(curPage));
        pageUtils.setIsShow(1);
        List<Initial> all = initService.findAll(pageUtils);
        return all;
    }

    /**
     * 搜索
     * @param initial
     * @return
     */
    @RequestMapping("/search")
    public List<Initial> search(Initial initial){
        List<Initial> search = initService.search(initial);
        return search;
    }
    @RequestMapping("/delete")
    public String delete(String id){
        int id1 = Integer.parseInt(id);
        Initial initial = new Initial();
        initial.setId(id1);
        initial.setIsShow(0);
        int delete = initService.delete(initial);
        if (delete>0){
            return "success";
        }else {
            return "fail";
        }
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
       if (i>0){
           flag = "success";
       }
        return flag;
    }

    @RequestMapping("/uploadExcel")
    public boolean Listexcel2Supplier(@RequestParam("excelFile") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();  //获得上传的excel文件名
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);  //获取上传的excel文件名后缀

        Workbook workbook = new HSSFWorkbook(file.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (Row row : sheet){
            if (row.getRowNum() < lastRowNum){
                continue;
            }

            String id = row.getCell(0).getStringCellValue();
        }
        return true;
    }
}
