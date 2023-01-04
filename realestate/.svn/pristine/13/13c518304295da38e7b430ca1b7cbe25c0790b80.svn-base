package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/10/27
 * @description 不动产受理云签附件信息
 */
@Table(name = "BDC_SL_YQ_FJXX")
@ApiModel(value = "BdcSlYqFjxxDO", description = "不动产受理云签附件信息")
public class BdcSlYqFjxxDO implements Serializable {
    private static final long serialVersionUID = 2508031530770726451L;
    @Id
    @ApiModelProperty(value = "收件材料ID")
    private String id;

    @ApiModelProperty(value = "文件中心NodeId(记录文件中心与其对应的NodeId)")
    private String wjzxid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "签署状态：0（未签署）1（签署中）2（签署完成）3（签署失败）4（拒签）")
    private String qszt;

    @ApiModelProperty(value = "附件名称")
    private String fjmc;

    @ApiModelProperty(value = "文件夹名称")
    private String wjjmc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWjzxid() {
        return wjzxid;
    }

    public void setWjzxid(String wjzxid) {
        this.wjzxid = wjzxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public String getWjjmc() {
        return wjjmc;
    }

    public void setWjjmc(String wjjmc) {
        this.wjjmc = wjjmc;
    }

    @Override
    public String toString() {
        return "BdcSlYqFjxxDO{" +
                "id='" + id + '\'' +
                ", wjzxid='" + wjzxid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qszt='" + qszt + '\'' +
                ", fjmc='" + fjmc + '\'' +
                ", wjjmc='" + wjjmc + '\'' +
                '}';
    }
}
