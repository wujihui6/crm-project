package com.wu.crm.workbench.mapper;

import com.wu.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Thu Aug 04 21:40:35 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Thu Aug 04 21:40:35 CST 2022
     */
    int insert(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Thu Aug 04 21:40:35 CST 2022
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Thu Aug 04 21:40:35 CST 2022
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Thu Aug 04 21:40:35 CST 2022
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Thu Aug 04 21:40:35 CST 2022
     */
    int updateByPrimaryKey(Activity record);

    /*
    保存创建的市场活动
     */
    int insertActivity(Activity activity);
    /*
    根据条件返回搜寻到的活动
     */
    List<Activity>  selectActivityByConditionForPage(Map<String,Object> map);
    /*
    根据条件返回搜寻到的活动的总条数
     */
    int selectCountOfActivityByCondition(Map<String,Object> map);

    /*
        删除选中的checked
     */
    int deleteActivityByIds(String[] ids);


    /*
        修改模态框弹出的数据

     */
    Activity selectActivityById(String id);

    /*
    修改数据
     */

    int updateActivity(Activity activity );

    /*
    查询返回所有数据
     */
    List<Activity> selectAllActivity();

    /*
    查询选中的数据并且返回
     */
    List<Activity> selectCheckedActvity(String[] id);

    /*
    将excel表格中的数据插入到数据库中
     */
    int insertCreateActivityByList(List<Activity> activityList);

    /*
    返回市场的所有信息
     */
    Activity selectActivityForDetail(String id);
}