package com.wu.crm.Settings.web.Controller;

import com.wu.crm.Settings.Service.userService;
import com.wu.crm.Settings.domain.User;

import com.wu.crm.commons.contains.Contains;
import com.wu.crm.commons.domain.ReturnObject;
import com.wu.crm.commons.utils.Dateutil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private userService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";  //将login.html改为jsp页面
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object Login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        //封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层的方法，查询用户
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();
        if(user == null){
            //登陆失败，账号或者密码不正确
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("账号或者密码不正确");
        }else{  //进一步判断账号是否合法

            String nowStr = Dateutil.dateformattime(new Date());
            if(nowStr.compareTo(user.getExpireTime()) >0){
                //登陆失败，账号过期
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("账号过期");
            }else if("0".equals(user.getLockState())){
                //登陆失败，账号被锁定
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("账号被锁定");
            }else if( !user.getAllowIps().contains(request.getRemoteAddr())){
                //登陆失败，Ip受限
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("Ip受限");
            }else{
                //登陆成功
                returnObject.setCode(Contains.Return_Object_Code_Success);
                //将user放入到session作用域中
                session.setAttribute(Contains.session_user_key,user);
                //如需要记住密码，将数据添加到cookie中
                if("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(24*60*60*10);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(24*60*60*10);
                    response.addCookie(c2);
                }else{
                    //把没有过期的cookie删除
                    Cookie c1 = new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return  returnObject;
    }
    @RequestMapping("/settings/qx/user/logout.do")
    public static String logout(HttpServletResponse response,HttpSession session){
        //销毁cookie对象
        Cookie c1 = new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session对象
        session.invalidate();
        return "redirect:/";   //借助springmvc来进行重定向，这里相当于response.sendRedirect("/crm/");
    }
}
