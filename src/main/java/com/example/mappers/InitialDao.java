package com.example.mappers;

import com.example.domain.Initial;
import com.example.utils.PageUtils;
import com.example.utils.SearchUtil;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;


public interface InitialDao {

    //录入功能
    void record(Initial initial);

    //查询所有
    List<Initial> findAll(PageUtils pageUtils);

    //模糊查询
    List<Initial> search(SearchUtil searchUtil);

    //删除某条入库单
    void delete(Initial initial);

    //查找要显示的所有入库单
    int totalAccount(int i);

    //保存Excel导入的数据
    void saveByExcel(List<Initial> initialList);

    //查询出来的条数
    int searchingAccount(SearchUtil searchUtil);

    //退货操作
    void returnOrder(Initial initial);

    Initial findById(int id);
}