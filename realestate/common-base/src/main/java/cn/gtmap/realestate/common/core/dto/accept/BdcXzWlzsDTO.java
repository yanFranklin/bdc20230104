package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/6
 * @description 选择外联证书实体
 */
public class BdcXzWlzsDTO {

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "选择的外联证书集合")
    private List<String> wlxmidList;

    @ApiModelProperty(value = "当前选择的外联证书")
    private String wlxmid;

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public List<String> getWlxmidList() {
        return wlxmidList;
    }

    public void setWlxmidList(List<String> wlxmidList) {
        this.wlxmidList = wlxmidList;
    }

    public String getWlxmid() {
        return wlxmid;
    }

    public void setWlxmid(String wlxmid) {
        this.wlxmid = wlxmid;
    }
}
