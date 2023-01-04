package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/06/29
 * @description  上报接入异常消息提醒配置表
 */
@Table(name = "BDC_JR_XXTX")
@ApiModel(value = "BdcJrXxtxDO", description = "上报接入异常消息提醒配置表")
public class BdcJrXxtxDO {
    @ApiModelProperty(value = "预警类型")
    private Integer yjlx;

    @ApiModelProperty(value = "预警类型名称")
    private String yjlxmc;

    @ApiModelProperty(value = "提醒方式")
    private Integer txfs;

    @ApiModelProperty(value = "提醒方式名称")
    private String txfsmc;

    @ApiModelProperty(value = "提醒角色")
    private String txjs;

    @ApiModelProperty(value = "手机号")
    private String xxmb;

    @ApiModelProperty(value = "消息模板")
    private String sjh;


    public Integer getYjlx() {
        return yjlx;
    }

    public void setYjlx(Integer yjlx) {
        this.yjlx = yjlx;
    }

    public String getYjlxmc() {
        return yjlxmc;
    }

    public void setYjlxmc(String yjlxmc) {
        this.yjlxmc = yjlxmc;
    }

    public Integer getTxfs() {
        return txfs;
    }

    public void setTxfs(Integer txfs) {
        this.txfs = txfs;
    }

    public String getTxfsmc() {
        return txfsmc;
    }

    public void setTxfsmc(String txfsmc) {
        this.txfsmc = txfsmc;
    }

    public String getTxjs() {
        return txjs;
    }

    public void setTxjs(String txjs) {
        this.txjs = txjs;
    }

    public String getXxmb() {
        return xxmb;
    }

    public void setXxmb(String xxmb) {
        this.xxmb = xxmb;
    }

    public String getSjh() {
        return sjh;
    }

    public void setSjh(String sjh) {
        this.sjh = sjh;
    }

    @Override
    public String toString() {
        return "BdcJrXxtxDO{" +
                "yjlx=" + yjlx +
                ", yjlxmc='" + yjlxmc + '\'' +
                ", txfs=" + txfs +
                ", txfsmc='" + txfsmc + '\'' +
                ", txjs='" + txjs + '\'' +
                ", xxmb='" + xxmb + '\'' +
                ", sjh='" + sjh + '\'' +
                '}';
    }
}
