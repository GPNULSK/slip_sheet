package com.example.service.impl;

import com.example.domain.Initial;
import com.example.mappers.InitialDao;
import com.example.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitServiceImpl implements InitService {

    @Autowired
    private InitialDao initialDao;

    //录入
    @Override
    public int record(Initial initial) {
        int i = 0;
        try {
            initialDao.record(initial);
            i=1;
        }catch (Exception e){
            System.out.println(e);
            i=-1;
        }
        return i;
    }

    @Override
    public List<Initial> findAll() {
        return initialDao.findAll();
    }

    @Override
    public List<Initial> search(Initial initial) {
        return initialDao.search(initial);
    }

    @Override
    public int changeStatus(Initial initial) {
        int i=0;
        try {
            initialDao.changeStatus(initial);
            i=1;
        } catch (Exception e) {
            i=-1;
            e.printStackTrace();
        }
        return i;
    }
}
