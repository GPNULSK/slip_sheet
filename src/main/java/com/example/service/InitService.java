package com.example.service;

import com.example.domain.Initial;
import com.example.utils.PageUtils;
import com.example.utils.SearchUtil;
import io.swagger.models.auth.In;

import java.util.List;

public interface InitService {

    //录入功能
    int record(Initial initial);

    //查询所有
    List<Initial> findAll(PageUtils pageUtils);

    //模糊查询
    List<Initial> search(SearchUtil searchUtil);



    int delete(Initial initial);

    int totalAccount(int i);

    int saveByExcel(List<Initial> initialList);

    //搜索出来的总条数
    int searchingAccount(SearchUtil searchUtil);

    //退货操作
    String returnOrder(Initial initial);

    Initial findById(int id);   //通过id查找
}
