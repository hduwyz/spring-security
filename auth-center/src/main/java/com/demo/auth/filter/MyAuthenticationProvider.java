package com.demo.auth.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    @Qualifier("myUserDetailService")
    private UserDetailsService userDetailsService;

    public MyAuthenticationProvider(PasswordEncoder passwordEncoder){
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //编写更多校验逻辑
        //实现图形验证码的校验逻辑

//        //校验密码
//        if (usernamePasswordAuthenticationToken.getCredentials() == null){
//            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码不能为空"));
//        }else {
//            String presentedPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
//            if (!presentedPassword.equals(userDetails.getPassword())){
//                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误"));
//            }
//        }
        super.additionalAuthenticationChecks(userDetails, usernamePasswordAuthenticationToken);
    }
}
