package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 宗地变化情况
 */
@Table(
        name = "BDC_BDCDJB_ZDJBXX_ZDBHQK"
)
@ApiModel(value = "BdcBdcdjbZdjbxxZdbhqkDO", description = "登记簿宗地变化情况")
public class BdcBdcdjbZdjbxxZdbhqkDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**宗地代码*/
    @ApiModelProperty(value = "宗地代码")
    private String zddm;
    /**变化原因*/
    @ApiModelProperty(value = "变化原因")
    private String bhyy;
    /**变化内容*/
    @ApiModelProperty(value = "变化内容")
    private String bhnr;
    /**登记时间*/
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    private Date djsj;
    /**登簿人*/
    @ApiModelProperty(value = "登簿人")
    private String dbr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
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

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
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
        return "BdcBdcdjbZdjbxxZdbhqkDO{" +
                "id='" + id + '\'' +
                ", zddm='" + zddm + '\'' +
                ", bhyy='" + bhyy + '\'' +
                ", bhnr='" + bhnr + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                '}';
    }
}
