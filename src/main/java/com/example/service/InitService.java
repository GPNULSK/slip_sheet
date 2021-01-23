package com.example.service;

import com.example.domain.Initial;

import java.util.List;

public interface InitService {

    //录入功能
    int record(Initial initial);

    //查询所有
    List<Initial> findAll();

    //模糊查询
    List<Initial> search(Initial initial);

    int changeStatus(Initial initial);

}