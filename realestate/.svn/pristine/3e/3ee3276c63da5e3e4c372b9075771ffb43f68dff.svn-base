package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/4
 * @description 不动产组合规则实体定义
 */
@Table(name = "BDC_GZ_ZHGZ")
public class BdcGzZhgzDO {
    /**
     * 组合ID
     */
    @Id
    @ApiModelProperty(value = "组合ID")
    private String zhid;
    /**
     * 组合名称
     */
    @ApiModelProperty(value = "组合名称")
    private String zhmc;
    /**
     * 组合说明
     */
    @ApiModelProperty(value = "组合说明")
    private String zhsm;
    /**
     * 组合标识
     */
    @ApiModelProperty(value = "组合标识")
    private String zhbs;

    /**
     * 配置日期
     */
    @ApiModelProperty(value = "配置日期", example = "2018-10-01 12:18:48")
    private Date pzrq;


    public String getZhid() {
        return zhid;
    }

    public void setZhid(String zhid) {
        this.zhid = zhid;
    }

    public String getZhmc() {
        return zhmc;
    }

    public void setZhmc(String zhmc) {
        this.zhmc = zhmc;
    }

    public String getZhsm() {
        return zhsm;
    }

    public void setZhsm(String zhsm) {
        this.zhsm = zhsm;
    }

    public String getZhbs() {
        return zhbs;
    }

    public void setZhbs(String zhbs) {
        this.zhbs = zhbs;
    }

    public Date getPzrq() {
        return pzrq;
    }


    public void setPzrq(Date pzrq) {
        this.pzrq = pzrq;
    }

    @Override
    public String toString() {
        return "BdcGzZhgzDO{" +
                "zhid='" + zhid + '\'' +
                ", zhmc='" + zhmc + '\'' +
                ", zhsm='" + zhsm + '\'' +
                ", zhbs='" + zhbs + '\'' +
                ", pzrq=" + pzrq +
                '}';
    }
}
