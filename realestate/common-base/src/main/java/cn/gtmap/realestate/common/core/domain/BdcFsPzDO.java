package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version v1.0,2022/8/2
 * @description 非税配置项
 */
@Table(name = "BDC_FS_PZ")
public class BdcFsPzDO {
    /**
     * 非税配置id
     */
    @Id
    @ApiModelProperty(value = "非税配置id")
    private String fspzid;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private String bmid;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String bmmc;

    /**
     * 人员id
     */
    @ApiModelProperty(value = "人员id")
    private String userid;

    /**
     * 人员名称
     */
    @ApiModelProperty(value = "人员名称")
    private String username;

    /**
     * 人员别名
     */
    @ApiModelProperty(value = "人员别名")
    private String alias;

    /**
     * 开票员账号
     */
    @ApiModelProperty(value = "开票员账号")
    private String kpyzh;

    /**
     * 票据版本代码
     */
    @ApiModelProperty(value = "票据版本代码")
    private String billvercode;

    /**
     * 票据版本名称
     */
    @ApiModelProperty(value = "票据版本名称")
    private String billvername;

    public String getFspzid() {
        return fspzid;
    }

    public void setFspzid(String fspzid) {
        this.fspzid = fspzid;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKpyzh() {
        return kpyzh;
    }

    public void setKpyzh(String kpyzh) {
        this.kpyzh = kpyzh;
    }

    public String getBillvercode() {
        return billvercode;
    }

    public void setBillvercode(String billvercode) {
        this.billvercode = billvercode;
    }

    public String getBillvername() {
        return billvername;
    }

    public void setBillvername(String billvername) {
        this.billvername = billvername;
    }

    @Override
    public String toString() {
        return "BdcFsPzDO{" +
                "fspzid='" + fspzid + '\'' +
                ", bmid='" + bmid + '\'' +
                ", bmmc='" + bmmc + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", alias='" + alias + '\'' +
                ", kpyzh='" + kpyzh + '\'' +
                ", billvercode='" + billvercode + '\'' +
                ", billvername='" + billvername + '\'' +
                '}';
    }
}
