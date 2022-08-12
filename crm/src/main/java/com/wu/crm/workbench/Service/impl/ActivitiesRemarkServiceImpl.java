package com.wu.crm.workbench.Service.impl;

import com.wu.crm.workbench.Service.ActivitiesRemarkService;
import com.wu.crm.workbench.domain.ActivitiesRemark;
import com.wu.crm.workbench.mapper.ActivitiesRemarkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("activitiesRemarkService")
public class ActivitiesRemarkServiceImpl implements ActivitiesRemarkService {
    @Resource
    private ActivitiesRemarkMapper activitiesRemarkMapper;

    @Override
    public List<ActivitiesRemark> queryActivityRemarkForDetailByActivityId(String id) {
        return activitiesRemarkMapper.selectActivityRemarkForDetailByActivityId(id);
    }

    @Override
    public int saveCreateActivityRemark(ActivitiesRemark activitiesRemark) {
        return activitiesRemarkMapper.insertCreateActivityRemark(activitiesRemark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activitiesRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public int saveEditActivityRemark(ActivitiesRemark activitiesRemark) {
        return activitiesRemarkMapper.updateActivityRemark(activitiesRemark);
    }
}
