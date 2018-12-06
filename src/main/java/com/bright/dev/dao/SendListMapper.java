package com.bright.dev.dao;

import com.bright.dev.entity.SendList;
import org.springframework.stereotype.Repository;

@Repository
public interface SendListMapper {
    int deleteByPrimaryKey(Integer listId);

    int insert(SendList record);

    int insertSelective(SendList record);

    SendList selectByPrimaryKey(Integer listId);

    int updateByPrimaryKeySelective(SendList record);

    int updateByPrimaryKey(SendList record);
}