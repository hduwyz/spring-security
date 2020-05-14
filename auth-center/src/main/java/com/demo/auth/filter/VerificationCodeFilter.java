package com.demo.auth.filter;

import com.demo.auth.exception.VerificationCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class VerificationCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/auth/form".equals(request.getRequestURI())){
            filterChain.doFilter(request, response);
        }else {
            try {
                verificationCode(request);
                filterChain.doFilter(request,response);
            }catch (VerificationCodeException e){
                new AuthenticationFailureHandler(){

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.setStatus(200);
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error_code\":\"500\", \"name\":\"" + e.getClass() + "\",  \"message\":\"" + e.getMessage() +"\"}");
                    }
                };
            }
        }
    }

    public void verificationCode(HttpServletRequest httpServletRequest) throws VerificationCodeException {
        String requestCode = httpServletRequest.getParameter("captcha");
        HttpSession session = httpServletRequest.getSession();
        String savedCode = (String)session.getAttribute("captcha");
        if (!StringUtils.isEmpty(savedCode)){
            //随手清楚验证码，无论失败还是成功，客户端应在登录失败时刷新验证码
            session.removeAttribute("captcha");
        }
        //校验不通过，抛出异常
        if(StringUtils.isEmpty(requestCode) || StringUtils.isEmpty(savedCode)
        || !requestCode.equals(savedCode)){
            throw new VerificationCodeException();
        }
    }
}
