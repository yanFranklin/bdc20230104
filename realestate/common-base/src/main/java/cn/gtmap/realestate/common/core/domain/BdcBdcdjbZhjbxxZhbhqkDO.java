package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 宗海变化情况
 */
@Table(name = "BDC_BDCDJB_ZHJBXX_ZHBHQK")
@ApiModel(value = "BdcBdcdjbZhjbxxZhbhqkDO", description = "登记簿宗海变化情况")
public class BdcBdcdjbZhjbxxZhbhqkDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**宗海代码*/
    @ApiModelProperty(value = "宗海代码")
    private String zhdm;
    /**变化原因*/
    @ApiModelProperty(value = "变化原因")
    private String bhyy;
    /**变化内容*/
    @ApiModelProperty(value = "变化内容")
    private String bhnr;
    /**登记时间*/
    @ApiModelProperty(value = "登记时间")
    private String djsj;
    /**登簿人*/
    @ApiModelProperty(value = "登簿人")
    private String dbr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZhdm() {
        return zhdm;
    }

    public void setZhdm(String zhdm) {
        this.zhdm = zhdm;
    }

    public String getBhyy() {
        return bhyy;
    }

    public void setBhyy(String bhyy) {
        this.bhyy = bhyy;
    }

    public String getBhnr() {
        return bhnr;
    }

    public void setBhnr(String bhnr) {
        this.bhnr = bhnr;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    @Override
    public String toString() {
        return "BdcBdcdjbZhjbxxZhbhqkDO{" +
                "id='" + id + '\'' +
                ", zhdm='" + zhdm + '\'' +
                ", bhyy='" + bhyy + '\'' +
                ", bhnr='" + bhnr + '\'' +
                ", djsj='" + djsj + '\'' +
                ", dbr='" + dbr + '\'' +
                '}';
    }
}
