package com.example.utils;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class SearchUtil {
    private String providerCode;

    private String providerName;

    private String batchCode;

    private String materialGrade;

    private String materialCode;

    private String materialName;

    private Date startDate;

    private Date endDate;

    private Integer totalPage;

    private Integer pageSize;

    private Integer curPage;

    private Integer pageStart;

    private Integer isShow;
}
