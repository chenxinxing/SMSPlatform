package com.bright.dev.dao;

import com.bright.dev.entity.Sendlist;

public interface SendlistMapper {
    int deleteByPrimaryKey(Integer listId);

    int insert(Sendlist record);

    int insertSelective(Sendlist record);

    Sendlist selectByPrimaryKey(Integer listId);

    int updateByPrimaryKeySelective(Sendlist record);

    int updateByPrimaryKey(Sendlist record);
}