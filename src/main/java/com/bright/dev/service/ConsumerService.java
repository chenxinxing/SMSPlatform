package com.bright.dev.service;

import com.bright.dev.entity.Consumer;
import org.springframework.stereotype.Service;

/**
 * @Author: Bright
 * @Date: 2018/11/30 12:23
 */

public interface ConsumerService {

    int deleteByPrimaryKey(String userId);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(String userId);

    Consumer selectUser(String userName,String password);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);

}
