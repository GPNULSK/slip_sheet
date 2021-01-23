package com.example.mappers;

import com.example.domain.Initial;
import com.sun.org.apache.xml.internal.security.Init;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InitialDao {

    //录入功能
    void record(Initial initial);

    //查询所有
    List<Initial> findAll();

    //模糊查询
    List<Initial> search(Initial initial);

    void changeStatus(Initial initial);
}