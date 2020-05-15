package com.demo.auth.config;

import com.demo.auth.entity.Users;
import com.demo.auth.mapper.UsersMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //从数据库尝试获取该用户
        Users users = usersMapper.findByUserName(s);
        //用户不存在，抛出异常
        if (users == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //将数据库形式的role解析为userDetails的权限
        users.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(users.getRoles()));
        return users;
    }

    private List<GrantedAuthority> generateAuthorities(String roles){
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (StringUtils.isNotBlank(roles)){
            String[] roleArray = roles.split(";");
            for (String role: roleArray){
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }
}
