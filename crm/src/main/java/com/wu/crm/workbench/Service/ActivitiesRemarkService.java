package com.wu.crm.workbench.Service;

import com.wu.crm.workbench.domain.ActivitiesRemark;

import java.util.List;

public interface ActivitiesRemarkService {
      List<ActivitiesRemark> queryActivityRemarkForDetailByActivityId(String id);

      int saveCreateActivityRemark(ActivitiesRemark activitiesRemark);


      int deleteActivityRemarkById(String id);

      int saveEditActivityRemark(ActivitiesRemark activitiesRemark);
}
