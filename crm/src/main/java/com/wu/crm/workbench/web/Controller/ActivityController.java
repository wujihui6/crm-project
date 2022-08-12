package com.wu.crm.workbench.web.Controller;

import com.wu.crm.Settings.Service.userService;
import com.wu.crm.Settings.domain.User;

import com.wu.crm.commons.contains.Contains;
import com.wu.crm.commons.domain.ReturnObject;
import com.wu.crm.commons.utils.Dateutil;
import com.wu.crm.commons.utils.HSSFUtils;
import com.wu.crm.commons.utils.UUIDutil;
import com.wu.crm.workbench.Service.ActivitiesRemarkService;
import com.wu.crm.workbench.Service.ActivityService;
import com.wu.crm.workbench.domain.ActivitiesRemark;
import com.wu.crm.workbench.domain.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class ActivityController {

    @Resource
    private userService userService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivitiesRemarkService activitiesRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> users = userService.squeryAllUsers();
        //将数据保存到request域中
        request.setAttribute("userList",users);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/SaveCreateActivity.do")
    @ResponseBody
    public Object SaveCreateActivity(Activity activity, HttpSession session){
        //通过session域活动user参数
        User user = (User) session.getAttribute(Contains.session_user_key);
        //封装参数
        activity.setId(UUIDutil.getUUID());
        activity.setCreateTime(Dateutil.dateformattime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service层方法，保存创建的市场活动
            int item = activityService.SaveCreateActivity(activity);
            if (item > 0){
                returnObject.setCode(Contains.Return_Object_Code_Success);
            }else {
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("系统忙,请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("系统忙,请稍后重试....");
        }
        return  returnObject;
    }
    @RequestMapping("/workbench/activity/queryActivityByCondition.do")
    @ResponseBody
    public Object queryActivityByCondition(String name,String owner,String startDate,String endDate,
                                            int pageNo,int pageSize){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Activity> activities = activityService.queryActivityByConditionForPage(map);
        int count = activityService.queryCountOfActivityByCondition(map);
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("activities",activities);
        hashMap.put("totalRows",count);
        return hashMap;
    }


    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        System.out.println(id);
        try {
            int i = activityService.deleteActivityByIds(id);
            if(i > 0){
                returnObject.setCode(Contains.Return_Object_Code_Success);
            }else{
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("系统忙，请稍后");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/updateActivity.do")
    @ResponseBody
    public Object updateActivity(@RequestBody String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(@RequestBody Activity activity, HttpSession session){
        User user = (User) session.getAttribute(Contains.session_user_key);
        activity.setEditBy(user.getId());
        activity.setEditTime(Dateutil.dateformattime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        try {
            int i = activityService.saveEditActivity(activity);
            if(i > 0){
                returnObject.setCode(Contains.Return_Object_Code_Success);
            }else{
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("系统忙，请稍后");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("系统忙，请稍后");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/filedownload.do")
    public void filedownload(HttpServletResponse response) throws Exception{
        //1、设置响应类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        //2、获取输出流
        OutputStream out = response.getOutputStream();

        //浏览器接收到响应信息之后，默认情况下，直接在显示窗口中打开响应信息；即使打不开，也会调用应用程序打开；只有实在打不开，才会激活文件下载窗口。
        //可以设置响应头信息，使浏览器接收到响应信息之后，直接激活文件下载窗口，即使能打开也不打开
        response.addHeader("Content-Disposition","attachment;filename=mystudentList.xls");

        //读取excel文件（InputStream），把数据输出到浏览器(Outputstream)
        InputStream is = new FileInputStream("D:\\students.xls");
        byte[] buff = new byte[256];
        int len = 0;
        while((len = is.read(buff)) != -1 ){
            out.write(buff,0,len);
        }

        //关闭资源
        is.close();
        out.flush();   //out不能cloes(),因为out是由response得到的，response是由tomcat管理的，tomcat会自己
                        //关闭out，如果手动关闭，tomcat还需要用，某一环节会报错
    }

    @RequestMapping("/workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws Exception {
        //获取数据库中的数据
        List<Activity> activities = activityService.queryAllActivity();
        //将数据放到excel文件中
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("活动信息");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("活动名称");
        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        cell = row.createCell(4);
        cell.setCellValue("结束时间");
        cell = row.createCell(5);
        cell.setCellValue("消费");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        if(activities != null && activities.size()>0){
            for(int  i = 0; i < activities.size(); i++){
                Activity activity = activities.get(i);
                 row = sheet.createRow(i + 1);
                 cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
        //生成文件
//       OutputStream os =  new FileOutputStream("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\activities.xls");
//        wb.write(os); //将数据从内存写道磁盘中，导致效率差
//        os.close();
//        wb.close();

        //下载excel文件
        response.setContentType("application/octct-stream;charset=UTF-8");
        OutputStream out = response.getOutputStream();
//        FileInputStream is = new FileInputStream("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\activities.xls");

        //浏览器接收到响应信息之后，默认情况下，直接在显示窗口中打开响应信息；即使打不开，也会调用应用程序打开；只有实在打不开，才会激活文件下载窗口。
        //可以设置响应头信息，使浏览器接收到响应信息之后，直接激活文件下载窗口，即使能打开也不打开
        response.addHeader("Content-Disposition","attachment;filename=mystudentList.xls");
       //下面这一段代码是将磁盘的数据读到内存中，效率也差
//        byte[] bytes = new byte[256];
//        int len = 0;
//        while ((len = is.read(bytes)) != -1){
//            out.write(bytes,0,len);
//        }

        wb.write(out);
        out.flush();  //这里是由response打开的流，收到tomcat的影响，不能随意关闭

    }

    @RequestMapping("/workbench/activity/exportCheckedActivities.do")
    public void exportCheckedActivities(@RequestParam(value = "id") String[] id, HttpServletResponse response) throws Exception {
        //获取数据库中的数据
        List<Activity> activities = activityService.queryCheckedActicity(id);
        //将数据放到excel文件中
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("活动信息");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("活动名称");
        cell = row.createCell(3);
        cell.setCellValue("开始时间");
        cell = row.createCell(4);
        cell.setCellValue("结束时间");
        cell = row.createCell(5);
        cell.setCellValue("消费");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        if(activities != null && activities.size()>0){
            for(int  i = 0; i < activities.size(); i++){
                Activity activity = activities.get(i);
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
        //生成文件
//       OutputStream os =  new FileOutputStream("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\activities.xls");
//        wb.write(os); //将数据从内存写道磁盘中，导致效率差
//        os.close();
//        wb.close();

        //下载excel文件
        response.setContentType("application/octct-stream;charset=UTF-8");
        OutputStream out = response.getOutputStream();
//        FileInputStream is = new FileInputStream("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\activities.xls");

        //浏览器接收到响应信息之后，默认情况下，直接在显示窗口中打开响应信息；即使打不开，也会调用应用程序打开；只有实在打不开，才会激活文件下载窗口。
        //可以设置响应头信息，使浏览器接收到响应信息之后，直接激活文件下载窗口，即使能打开也不打开
        response.addHeader("Content-Disposition","attachment;filename=mystudentList.xls");
        //下面这一段代码是将磁盘的数据读到内存中，效率也差
//        byte[] bytes = new byte[256];
//        int len = 0;
//        while ((len = is.read(bytes)) != -1){
//            out.write(bytes,0,len);
//        }

        wb.write(out);
        out.flush();  //这里是由response打开的流，收到tomcat的影响，不能随意关闭

    }


    //测试导入文件
    @RequestMapping("workbench/activity/fileupload.do")
    @ResponseBody
    public Object fileupload(String username, MultipartFile multipartFile) throws Exception{
        //把文本数据打印到工具台
        System.out.println("username = " + username);
        //把文件在服务指定的目录中生成一个同样的文件
        String originalFilename = multipartFile.getOriginalFilename();  //活动原始文件名，带有后缀的
        File file = new File("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\" + originalFilename);
        multipartFile.transferTo(file);//将解析到的文件传到file中，如果file中的文件不存在，会自动创建

        //返回响应信息
        ReturnObject returnObject = new ReturnObject();
        returnObject.setCode(Contains.Return_Object_Code_Success);
        returnObject.setMessage("上传成功");
        return returnObject;
    }

    @RequestMapping("/workbench/activity/exportupload.do")
    @ResponseBody
    public Object exportupload(MultipartFile myfile,HttpSession session){
            User user = (User) session.getAttribute(Contains.session_user_key);
        ReturnObject returnObject = new ReturnObject();
        try {
           /* //把文件在服务指定的目录中生成一个同样的文件
            String originalFilename =myfile.getOriginalFilename();  //活动原始文件名，带有后缀的
            File file = new File("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\" + originalFilename);
            myfile.transferTo(file);//将解析到的文件传到file中，如果file中的文件不存在，会自动创建

            InputStream is = new FileInputStream("G:\\crm\\code\\crm-project\\crm\\src\\main\\webapp\\lib\\" + originalFilename);*/
            //以上代码是将从内存读取到的文件写道磁盘中去，再从磁盘中读取到内存，速度慢
            //因此可以直接内存读到内存中
            InputStream is = myfile.getInputStream();
            //将文件中的所有数据封装到sheets中去
            HSSFWorkbook wb = new HSSFWorkbook(is);
            //获取当前页的数据
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for(int i = 1; i < sheet.getLastRowNum() + 1; i++){
                //逐次获取每行的数据
                row = sheet.getRow(i);
                //取出每行的数据放入到row中
                activity = new Activity();
                activity.setId(UUIDutil.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(Dateutil.dateformattime(new Date()));
                activity.setCreateBy(user.getId());  //默认为本账户创建

                for(int j = 0; j < row.getLastCellNum(); j++){
                    //取出每列的数据，放入到cell中
                    cell = row.getCell(j);
                    //获取参数列表的值
                    String cellvalue = HSSFUtils.getCellValueForstr(cell);
                   if(j == 0){
                       activity.setName(cellvalue);
                   }else if(j == 1){
                       activity.setStartDate(cellvalue);
                   }else if(j == 2){
                       activity.setEndDate(cellvalue);
                   }else if (j == 3){
                       activity.setCost(cellvalue);
                    }else if(j == 4){
                       activity.setDescription(cellvalue);
                   }

                }
                activityList.add(activity);
             }

            int list = activityService.saveCreateActivityByList(activityList);

            if(list > 0){
                returnObject.setCode(Contains.Return_Object_Code_Success);
                returnObject.setOther(list);
            }else{
                returnObject.setCode(Contains.Return_Object_Code_Fail);
                returnObject.setMessage("导入失败");
            }
            wb.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
            returnObject.setCode(Contains.Return_Object_Code_Fail);
            returnObject.setMessage("导入失败");

        }
        return  returnObject;
    }

@RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){
        Activity activity = activityService.queryActivityForDetail(id);
        List<ActivitiesRemark> detailByActivityId = activitiesRemarkService.queryActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("detailByActivityId",detailByActivityId);

        //请求转发
        return "workbench/activity/detail";
    }
}
