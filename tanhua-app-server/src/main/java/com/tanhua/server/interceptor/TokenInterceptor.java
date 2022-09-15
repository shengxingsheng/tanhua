package com.tanhua.server.interceptor;

import com.tanhua.commons.utils.JwtUtils;
import com.tanhua.model.domain.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sxs
 * @create 2022-09-10 16:59
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String token = request.getHeader("Authorization");
        boolean b = JwtUtils.verifyToken(token);
        if (!b){
            log.info("拦截[未登录]：{} {}",method,uri);
            response.setStatus(401);
            return false;
        }
        log.info("放行[已登录]：{} {}",method,uri);
        Claims claims = JwtUtils.getClaims(token);
        Long id = (Long) claims.get("id");
        String phone = (String) claims.get("phone");

        User user = new User();
        user.setId( id);
        user.setMobile(phone);

        UserHolder.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
