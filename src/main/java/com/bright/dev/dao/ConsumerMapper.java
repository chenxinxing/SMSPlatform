package com.bright.dev.dao;

import com.bright.dev.entity.Consumer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerMapper {
    int deleteByPrimaryKey(String userId);

    int insert(Consumer record);

    int insertSelective(Consumer record);

    Consumer selectByPrimaryKey(String userId);

    Consumer selectByUserNameAndPassword(@Param("user_name") String userName, @Param("user_password") String userPassword);

    int updateByPrimaryKeySelective(Consumer record);

    int updateByPrimaryKey(Consumer record);
}