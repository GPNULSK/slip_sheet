package com.example.service.impl;

import com.example.domain.Record;
import com.example.mappers.RecordDao;
import com.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordDao recordDao;
    @Override
    public int addRecord(Record record) {
        int insert = recordDao.insert(record);
        System.out.println(insert);
        return insert;
    }
}
