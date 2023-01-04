package cn.gtmap.realestate.common.core.qo.accept;

import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/2
 * @description 初始化受理信息
 */
@ApiModel(value = "InitSlxxQO", description = "初始化受理信息的参数结构")
public class InitSlxxQO {

    @ApiModelProperty(value = "存储不动产受理项目前台信息")
    private BdcSlYwxxDTO bdcSlYwxxDTO;

    @ApiModelProperty(value = "操作人ID")
    private String czrid;

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "原项目ID")
    private String yxmid;

    @ApiModelProperty(value = "存储对应项目与原抵押证明号关系")
    private Map<String,String> xmidDyzmhMap;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    public BdcSlYwxxDTO getBdcSlYwxxDTO() {
        return bdcSlYwxxDTO;
    }

    public void setBdcSlYwxxDTO(BdcSlYwxxDTO bdcSlYwxxDTO) {
        this.bdcSlYwxxDTO = bdcSlYwxxDTO;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public Map<String, String> getXmidDyzmhMap() {
        return xmidDyzmhMap;
    }

    public void setXmidDyzmhMap(Map<String, String> xmidDyzmhMap) {
        this.xmidDyzmhMap = xmidDyzmhMap;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        return "InitSlxxQO{" +
                "bdcSlYwxxDTO=" + bdcSlYwxxDTO +
                ", czrid='" + czrid + '\'' +
                ", jbxxid='" + jbxxid + '\'' +
                ", yxmid='" + yxmid + '\'' +
                ", xmidDyzmhMap=" + xmidDyzmhMap +
                ", gzldyid='" + gzldyid + '\'' +
                ", sxh=" + sxh +
                '}';
    }
}
