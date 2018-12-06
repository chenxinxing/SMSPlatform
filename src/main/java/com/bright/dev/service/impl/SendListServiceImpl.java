package com.bright.dev.service.impl;

import com.bright.dev.dao.SendListMapper;
import com.bright.dev.entity.SendList;
import com.bright.dev.service.SendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Bright
 * @Date: 2018/12/4 16:43
 */
@Service
public class SendListServiceImpl implements SendListService {

    @Autowired
    private SendListMapper mapper;


    @Override
    public int deleteByPrimaryKey(Integer listId) {
        return mapper.deleteByPrimaryKey(listId);
    }

    @Override
    public int insert(SendList record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(SendList record) {
        return mapper.insertSelective(record);
    }

    @Override
    public SendList selectByPrimaryKey(Integer listId) {
        return mapper.selectByPrimaryKey(listId);
    }

    @Override
    public int updateByPrimaryKeySelective(SendList record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SendList record) {
        return mapper.updateByPrimaryKey(record);
    }
}
