package com.bright.dev.service.impl;

import com.bright.dev.dao.ConsumerMapper;
import com.bright.dev.entity.Consumer;
import com.bright.dev.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Bright
 * @Date: 2018/12/4 16:46
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper mapper;

    @Override
    public int deleteByPrimaryKey(String userId) {
        return mapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(Consumer record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Consumer record) {
        return mapper.insertSelective(record);
    }

    @Override
    public Consumer selectByPrimaryKey(String userId) {
        return mapper.selectByPrimaryKey(userId);
    }

    @Override
    public Consumer selectUser(String userName, String password) {
        return mapper.selectByUserNameAndPassword(userName,password);
    }

    @Override
    public int updateByPrimaryKeySelective(Consumer record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Consumer record) {
        return mapper.updateByPrimaryKey(record);
    }
}
