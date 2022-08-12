package com.wu.crm.web.Controlller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /*
        理论上，给Controller方法分配url :http://127.0.0.1:8080/crm/
        为了简便，协议：//ip:port/应用名称必须省去，用/代替根目录下的/
     */
    @RequestMapping("/")  //这个斜杠代表http://127.0.0.1:8080/crm/这些内容
    public String index(){   //因为以后无论访问哪个URL，都会访问localhost:8080，因为我们自己配置的，所有MVC将公共部分省略了
       //请求转发
        return "index";
    }
}
