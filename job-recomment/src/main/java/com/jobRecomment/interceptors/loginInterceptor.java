package com.jobRecomment.interceptors;
import com.jobRecomment.utils.JwtUtil;
import com.jobRecomment.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //验证
        try {
            Map<String, Object> claim = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claim);
            //验证成功，放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            //验证失败，不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
