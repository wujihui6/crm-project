package com.wu.crm.workbench.Service.impl;

import com.wu.crm.workbench.Service.ActivityService;
import com.wu.crm.workbench.domain.Activity;
import com.wu.crm.workbench.mapper.ActivityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    @Override
    public int SaveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public List<Activity> queryAllActivity() {
        return activityMapper.selectAllActivity();
    }

    @Override
    public List<Activity> queryCheckedActicity(String[] id) {
        return activityMapper.selectCheckedActvity(id);
    }

    @Override
    public int saveCreateActivityByList(List<Activity> activityList) {
        return activityMapper.insertCreateActivityByList(activityList);
    }

    @Override
    public Activity queryActivityForDetail(String id) {
        return activityMapper.selectActivityForDetail(id);
    }
}
