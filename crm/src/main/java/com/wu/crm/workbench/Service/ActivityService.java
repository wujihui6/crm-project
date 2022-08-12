package com.wu.crm.workbench.Service;

import com.wu.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int SaveCreateActivity(Activity activity);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    int saveEditActivity(Activity activity);

    List<Activity> queryAllActivity();

    List<Activity> queryCheckedActicity(String[] id);


    int saveCreateActivityByList(List<Activity> activityList);

    Activity queryActivityForDetail(String id);
}
