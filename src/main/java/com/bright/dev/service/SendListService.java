package com.bright.dev.service;

import com.bright.dev.entity.SendList;

/**
 * @Author: Bright
 * @Date: 2018/11/30 12:24
 */

public interface SendListService {

    int deleteByPrimaryKey(Integer listId);

    int insert(SendList record);

    int insertSelective(SendList record);

    SendList selectByPrimaryKey(Integer listId);

    int updateByPrimaryKeySelective(SendList record);

    int updateByPrimaryKey(SendList record);

}
