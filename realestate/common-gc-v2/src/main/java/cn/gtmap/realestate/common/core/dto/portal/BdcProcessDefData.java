package cn.gtmap.realestate.common.core.dto.portal;

import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;

/**
 * 流程定义实体
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/27.
 * @description
 */
public class BdcProcessDefData extends ProcessDefData {
    /**
     * 常用标识  0：否  1：是
     */
    private Integer commonUse;

    @Override
    public Integer getCommonUse() {
        return commonUse;
    }

    public void setCommonUse(Integer commonUse) {
        this.commonUse = commonUse;
    }


}
