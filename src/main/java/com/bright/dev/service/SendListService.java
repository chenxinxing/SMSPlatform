package com.bright.dev.service;

import com.bright.dev.entity.SendList;

import java.util.List;

/**
 * @Author: Bright
 * @Date: 2018/11/30 12:24
 */

public interface SendListService {

    int insert(SendList record);

    SendList selectByPrimaryKey(String listId);

    List<SendList> selectByUserId(String userId);

    List<SendList> getSinglList(String userId,String phone);
}
