package com.example.controller;

import com.example.domain.Initial;
import com.example.domain.InitialExport;
import com.example.service.InitService;
import com.example.utils.ExcelReaderUtil;
import com.example.utils.ExportExcelWrapper;
import com.example.utils.PageUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class InitController {

            @Autowired
            private InitService initService;

            List<Initial> all;
        
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
                int i = 1;
                return initService.totalAccount(i);
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
                all = initService.findAll(pageUtils);
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
            public boolean uploadExcel(@RequestParam("excelFile")MultipartFile file) throws Exception {
                
                boolean flag = false;
                
                List<Initial> initials = ExcelReaderUtil.readExcel(file.getInputStream());
                int i = initService.saveByExcel(initials);
                if (i>0){
                    flag = true;
                }
                if (i<0){
                    flag = false;
                }
                return flag;
            }

    /**
     * 将本页面的数据导出到Excel
     * @param request
     * @param response
     */
    @RequestMapping("/exportToExcel")
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response){
        //表头
        String[] header = {"厂家代码","厂家名称","批次号","物料编码","物料名称","来料档次","物料状态","接收时间","接收人","是否退货"};

        List<InitialExport> exportList = new ArrayList<>();

        for (Initial list : all){
            InitialExport initialExport = new InitialExport();
            initialExport.setProviderCode(list.getProviderCode());
            initialExport.setProviderName(list.getProviderName());
            initialExport.setMaterialName(list.getMaterialName());
            initialExport.setBatchCode(list.getBatchCode());
            initialExport.setMaterialCode(list.getMaterialCode());
            initialExport.setMaterialName(list.getMaterialName());
            initialExport.setMaterialGrade(list.getMaterialGrade());
            initialExport.setArrivalDate(list.getArrivalDate());
            initialExport.setStatus(list.getStatus());

            initialExport.setOperator(list.getOperator());

            if (list.isTestReturn()){
                initialExport.setTestReturn("是");
            }else {
                initialExport.setTestReturn("否");
            }
            exportList.add(initialExport);
        }
        System.out.println(exportList);
        ExportExcelWrapper<InitialExport> initialUtil = new ExportExcelWrapper<>();
        initialUtil.exportExcel("物料数据导出","excel1",header,exportList,response,"yyyy-MM-dd HH:mm");
    }
}
