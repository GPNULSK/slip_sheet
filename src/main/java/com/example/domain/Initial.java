package com.example.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.spel.ast.BooleanLiteral;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * t_initial 原材料库
 * @author  114200563
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Initial implements Serializable {
    /**
     * 唯一主键
     */
    private Integer id;

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
    private String account;

    /**
     * 发货时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date issueDate;

    /**
     * 到达时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date arrivalDate;

    /**
     * 生产日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date manufactureDate;

    /**
     * 录入时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordedDate;

    /**
     * 是否需要退货 整数1需要  0不需要
     */
    private String isReturn;

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
     * 备注
     */
    private String remark;

    private boolean testReturn;


}