package cn.gtmap.realestate.exchange.core.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/20
 * @description 日志接入表
 */
@Table(name = "BDC_JR_DBRZJL")
public class BdcJrDbrzjlDO {
    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 日期
     */
    @Column(name = "jrrq")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date jrrq;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 行政区代码
     */
    @Column(name = "xzqdm")
    private String xzqdm;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description xml
     */
    @Column(name = "jrbw")
    private String jrbw;
    /**
     * @author <a href="mailto:liuyu@gtmap.cn">liuyu</a>
     * @description 是否成功（成功标识 true成功false失败）
     */
    @Column(name = "cgbs")
    private Integer cgbs;
    /**
     * @author <a href="mailto:liuyu@gtmap.cn">liuyu</a>
     * @description 响应信息
     */
    @Column(name = "xyxx")
    private String xyxx;

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 省级上报是否成功（成功标识 true成功false失败）
     */
    @Column(name = "sjcgbs")
    private Integer sjcgbs;

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">liuyu</a>
     * @description 省级响应信息
     */
    @Column(name = "sjxyxx")
    private String sjxyxx;

    /**
     * 上报日期 与报文的accessdate一致
     */
    @Column(name = "accessdate")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date accessdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getJrrq() {
        return jrrq;
    }

    public void setJrrq(Date jrrq) {
        this.jrrq = jrrq;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getJrbw() {
        return jrbw;
    }

    public void setJrbw(String jrbw) {
        this.jrbw = jrbw;
    }

    public Integer getCgbs() {
        return cgbs;
    }

    public void setCgbs(Integer cgbs) {
        this.cgbs = cgbs;
    }

    public String getXyxx() {
        return xyxx;
    }

    public void setXyxx(String xyxx) {
        this.xyxx = xyxx;
    }


    public Integer getSjcgbs() {
        return sjcgbs;
    }

    public void setSjcgbs(Integer sjcgbs) {
        this.sjcgbs = sjcgbs;
    }

    public String getSjxyxx() {
        return sjxyxx;
    }

    public void setSjxyxx(String sjxyxx) {
        this.sjxyxx = sjxyxx;
    }

    public Date getAccessdate() {
        return accessdate;
    }

    public void setAccessdate(Date accessdate) {
        this.accessdate = accessdate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcJrDbrzjlDO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", jrrq=").append(jrrq);
        sb.append(", xzqdm='").append(xzqdm).append('\'');
        sb.append(", jrbw='").append(jrbw).append('\'');
        sb.append(", cgbs=").append(cgbs);
        sb.append(", xyxx='").append(xyxx).append('\'');
        sb.append(", sjcgbs=").append(sjcgbs);
        sb.append(", sjxyxx='").append(sjxyxx).append('\'');
        sb.append(", accessdate=").append(accessdate);
        sb.append('}');
        return sb.toString();
    }
}
