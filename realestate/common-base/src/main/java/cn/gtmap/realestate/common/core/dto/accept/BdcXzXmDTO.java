package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/27
 * @description 选择项目实体
 */
public class BdcXzXmDTO {

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "选择的产权证集合")
    private List<String> yxmidList;

    @ApiModelProperty(value = "当前选择的产权")
    private String yxmid;

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public List<String> getYxmidList() {
        return yxmidList;
    }

    public void setYxmidList(List<String> yxmidList) {
        this.yxmidList = yxmidList;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    @Override
    public String toString() {
        return "BdcXzXmDTO{" +
                "jbxxid='" + jbxxid + '\'' +
                ", yxmidList=" + yxmidList +
                ", yxmid='" + yxmid + '\'' +
                '}';
    }
}
