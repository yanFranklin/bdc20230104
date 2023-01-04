package cn.gtmap.realestate.common.core.domain.accept;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wangyinghao
 * @version 1.0  2021/7/22
 * @description 资金监管监管协议对象缴存明细
 */
@Table(name = "BDC_SL_ZJJGXYJC")
@ApiModel(value = "BdcSlZjjgxyjcDO", description = "资金监管监管协议对象缴存明细")
public class BdcSlZjjgxyjcDO {
    @Id
    @ApiModelProperty(value = "缴存明细Id")
    private String jcmxid;

    @ApiModelProperty(value = "监管协议编号")
    private String jgxybh;

    @ApiModelProperty(value = "存款银行")
    private String ckyh;

    @ApiModelProperty(value = "存款金额（元）")
    private String yjye;

    @ApiModelProperty(value = "资金性质")
    private String zjxz;

    @ApiModelProperty(value = "资金余额（元）")
    private String zjye;

    @ApiModelProperty(value = "交易日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jrrq;

    public String getJcmxid() {
        return jcmxid;
    }

    public void setJcmxid(String jcmxid) {
        this.jcmxid = jcmxid;
    }

    public String getJgxybh() {
        return jgxybh;
    }

    public void setJgxybh(String jgxybh) {
        this.jgxybh = jgxybh;
    }

    public String getCkyh() {
        return ckyh;
    }

    public void setCkyh(String ckyh) {
        this.ckyh = ckyh;
    }

    public String getYjye() {
        return yjye;
    }

    public void setYjye(String yjye) {
        this.yjye = yjye;
    }

    public String getZjxz() {
        return zjxz;
    }

    public void setZjxz(String zjxz) {
        this.zjxz = zjxz;
    }

    public String getZjye() {
        return zjye;
    }

    public void setZjye(String zjye) {
        this.zjye = zjye;
    }

    public Date getJrrq() {
        return jrrq;
    }

    public void setJrrq(Date jrrq) {
        this.jrrq = jrrq;
    }

    @Override
    public String toString() {
        return "BdcSlZjjgxyjcDO{" +
                "jcmxid='" + jcmxid + '\'' +
                ", jgxybh='" + jgxybh + '\'' +
                ", ckyh='" + ckyh + '\'' +
                ", yjye='" + yjye + '\'' +
                ", zjxz='" + zjxz + '\'' +
                ", zjye='" + zjye + '\'' +
                ", jrrq=" + jrrq +
                '}';
    }
}
