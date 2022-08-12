package com.wu.crm.Settings.web.Interceptor;

import com.wu.crm.Settings.domain.User;
import com.wu.crm.commons.contains.Contains;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取session域，判断其中的user是否为空
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(Contains.session_user_key);
        if (user == null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());  //这里获得的路径就相当于/crm/
            return false;                                                           //使用response重定向时，url必须加项目名称
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
