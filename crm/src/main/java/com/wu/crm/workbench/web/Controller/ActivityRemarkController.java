package com.wu.crm.workbench.web.Controller;


import com.wu.crm.Settings.domain.User;
import com.wu.crm.commons.contains.Contains;
import com.wu.crm.commons.domain.ReturnObject;
import com.wu.crm.commons.utils.Dateutil;
import com.wu.crm.commons.utils.UUIDutil;
import com.wu.crm.workbench.Service.ActivitiesRemarkService;
import com.wu.crm.workbench.domain.ActivitiesRemark;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    //定义service层变量
    @Resource
    private ActivitiesRemarkService activitiesRemarkService;

    @RequestMapping("/workbench/activity/saveActivityRemark.do")
    @ResponseBody
    public Object saveActivityRemark(String noteContent, String activityId, HttpSession session){
        //获取用户信息
        User user =  (User)session.getAttribute(Contains.session_user_key);
        //封装参数
        ActivitiesRemark remark = new ActivitiesRemark();
        remark.setActivityId(activityId);
        remark.setNoteContent(noteContent);
        remark.setCreateBy(user.getId());
        remark.setId(UUIDutil.getUUID());
        remark.setCreateTime(Dateutil.dateformattime(new Date()));
        remark.setEditFlag(Contains.Return_Object_Code_Fail);

        //返回到json数据
        ReturnObject object = new ReturnObject();
        //保存到数据库
        try {
            int i = activitiesRemarkService.saveCreateActivityRemark(remark);
            if(i > 0){
                object.setCode("1");
                object.setOther(remark);
            }else{
                object.setCode("0");
                object.setMessage("系统忙，请稍后。。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            object.setCode("0");
            object.setMessage("系统忙，请稍后。。。");
        }
        return object;
    }

    @RequestMapping("/workbench/activity/deleteRemark.do")
    @ResponseBody
    public Object deleteRemark(String id){
        //执行sql语句
        ReturnObject returnObject = new ReturnObject();
        try {
            int i = activitiesRemarkService.deleteActivityRemarkById(id);
            if(i > 0){
                returnObject.setCode(Contains.Return_Object_Code_Success);
            }else{
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("系统忙请稍后");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("系统忙请稍后");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/updateActivityRemark.do")
    @ResponseBody
    public Object updateActivityRemark(String id,String noteContent,HttpSession session ){
        User user = (User) session.getAttribute(Contains.session_user_key);
        System.out.println(id +noteContent);
        //封装数据
        ActivitiesRemark remark = new ActivitiesRemark();
        remark.setNoteContent(noteContent);
        remark.setId(id);
        remark.setEditTime(Dateutil.dateformattime(new Date()));
        remark.setEditBy(user.getId());
        remark.setEditFlag(Contains.remark_edit_true);

        ReturnObject returnObject = new ReturnObject();
        //传入数据
        try {
            int i = activitiesRemarkService.saveEditActivityRemark(remark);
            if(i > 0){
                returnObject.setCode(Contains.Return_Object_Code_Success);
                returnObject.setOther(remark);
            }else{
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("系统忙，请稍后。。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("系统忙，请稍后。。。");
        }
        return returnObject;
    }
}
