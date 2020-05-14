package com.demo.auth.exception;

import javax.naming.AuthenticationException;

public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException(){
        super("图形验证码校验失败");
    }
}
