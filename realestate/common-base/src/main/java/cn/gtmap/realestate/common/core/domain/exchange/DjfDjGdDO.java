package cn.gtmap.realestate.common.core.domain.exchange;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "DJF_DJ_GD")
@XmlRootElement(name = "DJF_DJ_GD")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjGdDO implements Serializable, AccessData {

    private String ysdm = "6004060000";//要素代码
    @Id
    private String ywh;//业务号
    private String djdl;//登记大类
    private Integer djxl;//登记小类
    private String zl;//坐落
    @Id
    private String qzhm;//权证号码
    private Integer wjjs;//文件件数
    private Integer zys;//总页数
    private String gdry;//归档人员
    private Date gdsj;//归档时间
    private String bz;//备注
    private String qxdm;//区县代码
    private Date createtime;
    private Date updatetime;

    private String dah;
    private String dacfjbm;

    @XmlAttribute(name = "DAH")
    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    @XmlAttribute(name = "DACFJBM")
    public String getDacfjbm() {
        return dacfjbm;
    }

    public void setDacfjbm(String dacfjbm) {
        this.dacfjbm = dacfjbm;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "DJDL")
    public String getDjdl() {
        return djdl;
    }

    public void setDjdl(String djdl) {
        this.djdl = djdl;
    }

    @XmlAttribute(name = "DJXL")
    public Integer getDjxl() {
        return djxl;
    }

    public void setDjxl(Integer djxl) {
        this.djxl = djxl;
    }

    @XmlAttribute(name = "ZL")
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "QZHM")
    public String getQzhm() {
        return qzhm;
    }

    public void setQzhm(String qzhm) {
        this.qzhm = qzhm;
    }

    @XmlAttribute(name = "WJJS")
    public Integer getWjjs() {
        return wjjs;
    }

    public void setWjjs(Integer wjjs) {
        this.wjjs = wjjs;
    }

    @XmlAttribute(name = "ZYS")
    public Integer getZys() {
        return zys;
    }

    public void setZys(Integer zys) {
        this.zys = zys;
    }

    @XmlAttribute(name = "GDRY")
    public String getGdry() {
        return gdry;
    }

    public void setGdry(String gdry) {
        this.gdry = gdry;
    }

    @XmlAttribute(name = "GDSJ")
    public Date getGdsj() {
        return gdsj;
    }

    public void setGdsj(Date gdsj) {
        this.gdsj = gdsj;
    }

    @XmlAttribute(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
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

}
