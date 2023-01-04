package cn.gtmap.realestate.common.core.dto.portal;

import cn.gtmap.gtc.workflow.domain.manage.ForwardTaskDto;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/27
 * @description
 */
public class BdcZfhyzDTO extends ForwardTaskDto {

    /**
     * 工作流实例id
     */
    private String gzlslid;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }
}
