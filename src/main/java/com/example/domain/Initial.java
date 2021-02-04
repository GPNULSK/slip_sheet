package com.example.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * t_initial
 * @author 
 */
@Data
public class Initial implements Serializable {
    /**
     * 唯一主键
     */
    private Integer id;

    /**
     * 逻辑外键
     */
    private String orderId;

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
     * 物料类型
     */
    private String materialType;

    /**
     * 包装数量
     */
    private Integer account;

    /**
     * 退货数量
     */
    private Integer returnAccount;

    /**
     * 发货时间
     */
    private Date issueDate;

    /**
     * 到达时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd ",timezone = "GMT+8")
    private Date arrivalDate;

    /**
     * 生产日期
     */
    private Date manufactureDate;

    /**
     * 录入时间
     */
    private Date recordedDate;

    /**
     * 货物状态
     */
    private String status;

    /**
     * 仓库名
     */
    private String warehouseName;

    /**
     * 库位
     */
    private String location;

    /**
     * 所属分厂
     */
    private String ownerFactory;

    /**
     * 操作人员名字
     */
    private String operator;

    /**
     * 责任人
     */
    private String charger;

    /**
     * 是否需要退货
     */
    private Byte isReturn;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作记录
     */
    private String operationHistory;


}