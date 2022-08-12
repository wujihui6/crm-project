package com.wu.crm.workbench.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class workbenchloginController {

    @RequestMapping("/workbench/index.do")
    public String index(){
        return "workbench/index";
    }

}
