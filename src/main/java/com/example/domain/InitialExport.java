package com.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 导出到Excel的实体类
 */
@Data
public class InitialExport {

    /**
     * 厂家代号
     */
    private String providerCode;

    /**
     * 厂家名称
     */
    private String providerName;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 物料代码
     */
    private String materialCode;

    /**
     * 物料名称
     */
    private String materialName;


    /**
     * 物料档次
     */
    private String materialGrade;


    /**
     * 货物状态
     */
    private String status;

    /**
     * 接收时间
     */
    private Date arrivalDate;

    /**
     * 操作人员名字
     */
    private String operator;

    /**
     * 是否退货，这个变量正在使用，上面的弃用
     */
    private String testReturn;
}
