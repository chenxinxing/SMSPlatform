package com.bright.dev.dao;

import com.bright.dev.entity.Consumer;

public interface ConsumerMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);
}