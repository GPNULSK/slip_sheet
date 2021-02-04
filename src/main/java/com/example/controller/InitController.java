package com.example.controller;

import com.example.domain.Initial;
import com.example.domain.InitialExport;
import com.example.domain.Record;
import com.example.service.InitService;
import com.example.service.RecordService;
import com.example.utils.ExcelReaderUtil;
import com.example.utils.ExportExcelWrapper;
import com.example.utils.PageUtils;

import com.example.utils.SearchUtil;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api
@CrossOrigin
@RestController
public class InitController {

    @Autowired
    private InitService initService;

    @Autowired
    private RecordService recordService;

    List<Initial> all;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 录入到中间库
     *
     * @param initial
     * @return
     */
    @Transactional  //开启事务
    @ApiOperation("手动逐条录入入库信息接口")
    @PostMapping("/record")
    @ApiImplicitParams({@ApiImplicitParam(name = "providerCode",value = "厂家代码"),@ApiImplicitParam(name = "providerName",value = "厂家名称")})
    public String record(Initial initial) {
        String uuid = UUID.randomUUID().toString();
        initial.setOrderId(uuid);
        int flag = initService.record(initial);
        Record record = new Record();
        record.setUuid(uuid);
        record.setOperatorTime(new Date());
        recordService.addRecord(record);

        if (flag > 0) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    @RequestMapping("/returnOrder")
    public String returnOrder(String id, String account){
        Initial initial = new Initial();
        Initial in = initService.findById(Integer.parseInt(id));
        initial.setId(Integer.parseInt(id));
        if (in.getReturnAccount() != null){
            initial.setReturnAccount(Integer.parseInt(account)+in.getReturnAccount());
        }else {
            initial.setReturnAccount(Integer.parseInt(account));
        }

        System.out.println(initial);
        String flag = initService.returnOrder(initial);
        return flag;
    }

    /**
     * 查找一共有多少条
     * @return
     */
    @GetMapping(value = "/totalAccount")
    public int totalAccount() {
        int i = 1;
        return initService.totalAccount(i);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @ApiOperation("根据分页查找")
    @GetMapping("/findAll")
    @ApiImplicitParams({@ApiImplicitParam(name = "curPage",value = "当前页"),@ApiImplicitParam(name = "pageSize",value = "每页多少条数据")})
    public List<Initial> findAll(String curPage, String pageSize) {
        PageUtils pageUtils = new PageUtils();
        pageUtils.setPageSize(Integer.parseInt(pageSize));
        pageUtils.setCurPage(Integer.parseInt(curPage));
        pageUtils.setIsShow(1);
        all = initService.findAll(pageUtils);
        return all;
    }


    /**
     * 搜索方法
     * @param providerCode
     * @param providerName
     * @param batchCode
     * @param materialGrade
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    @PostMapping("/search")
    public Map<String, Object> search(String providerCode, String providerName, String materialCode,String materialName,
                                      String batchCode, String materialGrade,String startDate, String endDate, String curPage, String pageSize) throws ParseException {
        SearchUtil searchUtil = new SearchUtil();
        searchUtil.setProviderCode(providerCode);
        searchUtil.setMaterialCode(materialCode);
        searchUtil.setProviderName(providerName);
        searchUtil.setBatchCode(batchCode);
        searchUtil.setMaterialName(materialName);
        searchUtil.setMaterialGrade(materialGrade);
        searchUtil.setCurPage(Integer.parseInt(curPage));
        searchUtil.setPageSize(Integer.parseInt(pageSize));
        searchUtil.setIsShow(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (null != startDate && !"".equals(startDate) ){
            searchUtil.setStartDate(simpleDateFormat.parse(startDate));
        }

        if (!"".equals(endDate) && null != endDate){
            searchUtil.setEndDate(simpleDateFormat.parse(endDate));
        }
        List<Initial> search = initService.search(searchUtil);
        int totalAccount = initService.searchingAccount(searchUtil);
        Map<String,Object> result =new HashMap<>();
        result.put("result",search);
        result.put("account",totalAccount);
        return result;
    }

    @ApiOperation("根据id删除某一条单")
    @ApiImplicitParams(@ApiImplicitParam(name = "id",value = "单的唯一id"))
    @GetMapping("/delete")
    public String delete(String id) {
        int id1 = Integer.parseInt(id);
        Initial initial = new Initial();
        initial.setId(id1);
        int delete = initService.delete(initial);
        if (delete > 0) {
            return "success";
        } else {
            return "fail";
        }
    }


    /**
     * 读取Excel，然后保存到数据库
     * @param file
     * @return
     * @throws Exception
     */
    @ApiIgnore
    @RequestMapping("/uploadExcel")
    public boolean uploadExcel(@RequestParam("excelFile") MultipartFile file) throws Exception {
        boolean flag = false;
        List<Initial> initials = ExcelReaderUtil.readExcel(file.getInputStream());
        int i = initService.saveByExcel(initials);
        if (i > 0) {
            flag = true;
        }
        if (i < 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 将本页面的数据导出到Excel
     *
     * @param request
     * @param response
     */
    @RequestMapping("/exportToExcel")
    @ApiIgnore
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response) {
        //表头
        String[] header = {"厂家代码", "厂家名称", "批次号", "物料编码", "物料名称", "来料档次", "物料状态", "接收时间", "接收人", "是否退货"};

        List<InitialExport> exportList = new ArrayList<>();

        for (Initial list : all) {
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

            exportList.add(initialExport);
        }
        ExportExcelWrapper<InitialExport> initialUtil = new ExportExcelWrapper<>();
        initialUtil.exportExcel("物料数据导出", "excel1", header, exportList, response, "yyyy-MM-dd HH:mm");
    }
}
