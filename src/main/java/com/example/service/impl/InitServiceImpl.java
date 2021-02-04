package com.example.service.impl;

import com.example.domain.Initial;
import com.example.mappers.InitialDao;
import com.example.service.InitService;
import com.example.utils.PageUtils;
import com.example.utils.SearchUtil;
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
    public List<Initial> findAll(PageUtils pageUtils) {
        pageUtils.setStart((pageUtils.getCurPage()-1)*pageUtils.getPageSize());
        return initialDao.findAll(pageUtils);
    }

    @Override
    public List<Initial> search(SearchUtil searchUtil) {
        searchUtil.setPageStart((searchUtil.getCurPage()-1)*searchUtil.getPageSize());
        return initialDao.search(searchUtil);
    }



    @Override
    public int delete(Initial initial) {
        int i = 0;
        try {
            initialDao.delete(initial);
            i = 1;
        } catch (Exception e) {
            i = -1;
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int totalAccount(int i) {
        return initialDao.totalAccount(1);
    }

    @Override
    public int saveByExcel(List<Initial> initialList) {
        int i = 0;
        try {
            initialDao.saveByExcel(initialList);
            i = 1;
        } catch (Exception e) {
            i = -1;
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int searchingAccount(SearchUtil searchUtil) {
        return initialDao.searchingAccount(searchUtil);
    }

    @Override
    public String returnOrder(Initial initial) {

        String flag = "false";
        try {
            initialDao.returnOrder(initial);
            flag = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Initial findById(int id) {
        return initialDao.findById(id);
    }
}
