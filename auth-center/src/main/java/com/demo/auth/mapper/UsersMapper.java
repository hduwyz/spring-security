package com.demo.auth.mapper;

import com.demo.auth.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UsersMapper {

    @Select("select * from users where username=#{username}")
    Users findByUserName(@Param("username") String username);
}
