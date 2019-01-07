package com.bright.dev.service.impl;

import com.bright.dev.dao.SendListMapper;
import com.bright.dev.entity.SendList;
import com.bright.dev.service.SendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Bright
 * @Date: 2018/12/4 16:43
 */
@Service
public class SendListServiceImpl implements SendListService {

    @Autowired
    private SendListMapper mapper;

    @Override
    public int insert(SendList record) {
        return mapper.insert(record);
    }

    @Override
    public SendList selectByPrimaryKey(String listId) {
        return mapper.selectByPrimaryKey(listId);
    }

    @Override
    public List<SendList> selectByUserId(String userId) {
        return mapper.selectByUserId(userId);
    }

    @Override
    public List<SendList> getSinglList(String userId, String phone) {
        return mapper.selectByUserIdAndPhone(userId,phone);
    }

}
