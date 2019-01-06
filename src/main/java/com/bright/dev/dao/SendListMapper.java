package com.bright.dev.dao;

import com.bright.dev.entity.SendList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendListMapper {
    int deleteByPrimaryKey(String listId);

    int insert(SendList record);

    int insertSelective(SendList record);

    SendList selectByPrimaryKey(String listId);

    List<SendList> selectByUserId(String userId);

    List<SendList> selectByUserIdAndPhone(@Param("user_id") String userId, @Param("send_to") String phone);

    int updateByPrimaryKeySelective(SendList record);

    int updateByPrimaryKey(SendList record);
}