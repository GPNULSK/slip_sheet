package com.example.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_record
 * @author 
 */
@Data
public class Record implements Serializable {
    private Integer id;

    /**
     * 唯一UUID
     */
    private String uuid;

    /**
     * 物料代码
     */
    private String materialcode;

    /**
     * 厂家代码
     */
    private String providerCode;

    /**
     * 记录操作的字段
     */
    private String operation;

    /**
     * 最后操作时间
     */
    private Date operatorTime;

    /**
     * 最后操作人
     */
    private String operator;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;
}