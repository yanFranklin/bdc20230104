package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/29
 * @description 默认意见
 */
@Table(name = "BDC_XT_MRYJ")
public class BdcXtMryjDO {
    /**主键ID */
    @ApiModelProperty(value = "主键ID")
    @Id
    private String mryjid;
    /**工作流定义ID */
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;
    /**工作流名称 */
    @ApiModelProperty(value = "工作流名称")
    private String gzldymc;
    /**创建人ID */
    @ApiModelProperty(value = "创建人ID")
    private String cjrid;
    /**节点名称 */
    @ApiModelProperty(value = "节点名称")
    private String jdmc;
    /**是否可用 */
    @ApiModelProperty(value = "是否可用")
    private Integer sfky;
    /**意见 */
    @ApiModelProperty(value = "意见",hidden = true)
    private String yj;
    /**意见类型*/
    @ApiModelProperty(value = "意见类型")
    private Integer yjlx;
    /**数据类型 0：字符串 1：SQL*/
    @ApiModelProperty(value = "数据类型")
    private Integer sjlx;

    public Integer getSjlx() {
        return sjlx;
    }

    public void setSjlx(Integer sjlx) {
        this.sjlx = sjlx;
    }

    public String getMryjid() {
        return mryjid;
    }

    public void setMryjid(String mryjid) {
        this.mryjid = mryjid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public Integer getSfky() {
        return sfky;
    }

    public void setSfky(Integer sfky) {
        this.sfky = sfky;
    }

    public String getYj() {
        return yj;
    }

    public void setYj(String yj) {
        this.yj = yj;
    }

    public Integer getYjlx() {
        return yjlx;
    }

    public void setYjlx(Integer yjlx) {
        this.yjlx = yjlx;
    }

    @Override
    public String toString() {
        return "BdcXtMryjDO{" +
                "mryjid='" + mryjid + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", cjrid='" + cjrid + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", sfky=" + sfky +
                ", yj='" + yj + '\'' +
                ", yjlx=" + yjlx +
                ", sjlx=" + sjlx +
                '}';
    }
}
