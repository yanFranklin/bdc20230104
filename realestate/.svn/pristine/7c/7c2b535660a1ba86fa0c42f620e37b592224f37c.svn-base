package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * 非结构化文档
 * Created by xhc on 2015/11/20.
 */
@XmlRootElement(name = "FJ_F_100")
@XmlAccessorType(XmlAccessType.NONE)
public class FjFOldDO implements Serializable, AccessData {

    @RequiredFk
    private String ywh;
    private String fjmc;//附件名称
    private String fjlx;//附件类型
    private String fjnr;//附件内容，Base64位编码格式

    private String fjnrurl;
    private String wjzxfid;

    private Date createtime;
    private Date updatetime;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "FJNRURL")
    public String getFjnrurl() {
        return fjnrurl;
    }

    public void setFjnrurl(String fjnrurl) {
        this.fjnrurl = fjnrurl;
    }

    @XmlAttribute(name = "WJZXFID")
    public String getWjzxfid() {
        return wjzxfid;
    }

    public void setWjzxfid(String wjzxfid) {
        this.wjzxfid = wjzxfid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @XmlAttribute(name = "FJMC")
    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    @XmlAttribute(name = "FJLX")
    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    @XmlAttribute(name = "FJNR")
    public String getFjnr() {
        return fjnr;
    }

    public void setFjnr(String fjnr) {
        this.fjnr = fjnr;
    }

}
