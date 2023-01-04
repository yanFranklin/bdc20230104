package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 工作流事件批量信息实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-04-21 14:23
 **/
public class BdcGzlsjPlDTO {

    //当前事件id
    @ApiModelProperty("当前事件id")
    private String sjid;

    @ApiModelProperty("事件名称")
    private String sjmc;

    @ApiModelProperty("勾选多个流程生成多个事件标识")
    private List<String> sjbsList;

    @ApiModelProperty("勾选关联多个接口")
    private List<String> jkidList;

    @ApiModelProperty("勾选多个节点")
    private List<String> jdmcList;

    @ApiModelProperty("更新类型insert/update")
    private String type;

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public List<String> getSjbsList() {
        return sjbsList;
    }

    public void setSjbsList(List<String> sjbsList) {
        this.sjbsList = sjbsList;
    }

    public List<String> getJkidList() {
        return jkidList;
    }

    public void setJkidList(List<String> jkidList) {
        this.jkidList = jkidList;
    }

    public List<String> getJdmcList() {
        return jdmcList;
    }

    public void setJdmcList(List<String> jdmcList) {
        this.jdmcList = jdmcList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    @Override
    public String toString() {
        return "BdcGzlsjPlDTO{" +
                "sjid='" + sjid + '\'' +
                ", sjbsList=" + sjbsList +
                ", jkidList=" + jkidList +
                ", jdmcList=" + jdmcList +
                ", type='" + type + '\'' +
                '}';
    }
}
