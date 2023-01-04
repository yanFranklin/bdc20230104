package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 不动产系统证书相关属性配置模板
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/13.
 * @description
 */
@Table(name = "BDC_XT_ZSMB_PZ")
public class BdcXtZsmbPzDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键ID")
    private String zsmbid;
    /**
     * 权利类型
     */
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    /**
     * 模板语句 参数xmid 可配置多个语句用;隔开
     */
    @ApiModelProperty(value = "模板语句 参数xmid 可配置多个语句用;隔开")
    private String mbsql;

    @ApiModelProperty(value = "配置日期")
    private Date pzrq;

    public String getZsmbid() {
        return zsmbid;
    }

    public void setZsmbid(String zsmbid) {
        this.zsmbid = zsmbid;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getMbsql() {
        return mbsql;
    }

    public void setMbsql(String mbsql) {
        this.mbsql = mbsql;
    }

    public Date getPzrq() {
        return pzrq;
    }

    public void setPzrq(Date pzrq) {
        this.pzrq = pzrq;
    }

    @Override
    public String toString() {
        return "BdcXtZsmbPzDO{" +
                "zsmbid='" + zsmbid + '\'' +
                ", qllx=" + qllx +
                ", mbsql='" + mbsql + '\'' +
                ", pzrq=" + pzrq +
                '}';
    }
}
