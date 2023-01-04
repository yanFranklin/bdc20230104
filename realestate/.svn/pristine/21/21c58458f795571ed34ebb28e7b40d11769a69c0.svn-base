package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/13
 * @description 不动产初始化受理项目
 */
@ApiModel(value = "BdcCshSlxmDTO", description = "不动产初始化受理项目")
public class BdcCshSlxmDTO implements Serializable {
    private static final long serialVersionUID = 2042712622237855538L;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;
    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "创建人ID")
    private String czrid;
    @ApiModelProperty(value = "不动产登记小类配置")
    private List<BdcDjxlPzDO> bdcDjxlPzDOList;
    @ApiModelProperty(value = "不动产受理项目前台")
    private List<BdcSlYwxxDTO> bdcSlYwxxDTOList;
    @ApiModelProperty(value = "需要添加外联证书的项目集合")
    private String xmids;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public List<BdcDjxlPzDO> getBdcDjxlPzDOList() {
        return bdcDjxlPzDOList;
    }

    public void setBdcDjxlPzDOList(List<BdcDjxlPzDO> bdcDjxlPzDOList) {
        this.bdcDjxlPzDOList = bdcDjxlPzDOList;
    }

    public List<BdcSlYwxxDTO> getBdcSlYwxxDTOList() {
        return bdcSlYwxxDTOList;
    }

    public void setBdcSlYwxxDTOList(List<BdcSlYwxxDTO> bdcSlYwxxDTOList) {
        this.bdcSlYwxxDTOList = bdcSlYwxxDTOList;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    public String getXmids() { return xmids; }

    public void setXmids(String xmids) { this.xmids = xmids; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcCshSlxmDTO{");
        sb.append("gzlslid='").append(gzlslid).append('\'');
        sb.append(", gzldyid='").append(gzldyid).append('\'');
        sb.append(", jbxxid='").append(jbxxid).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", czrid='").append(czrid).append('\'');
        sb.append(", xmids='").append(xmids).append('\'');
        sb.append(", bdcDjxlPzDOList=").append(bdcDjxlPzDOList);
        sb.append(", bdcSlYwxxDTOList=").append(bdcSlYwxxDTOList);
        sb.append('}');
        return sb.toString();
    }
}
