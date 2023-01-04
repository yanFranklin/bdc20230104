package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.18
 * @description:
 */
@Api(value = "DtcxDO", description = "动态查询传输对象")
@Table(name = "dtcx_cx")
public class DtcxDO {
    @ApiModelProperty("查询服务名称")
    private String cxmc;
    @ApiModelProperty("查询服务代号")
    private String cxdh;
    @ApiModelProperty("主键id")
    @Id
    private String cxid;
    @ApiModelProperty("查询sql")
    private String cxsql;
    @ApiModelProperty("创建人")
    private String cjr;
    @ApiModelProperty("创建时间")
    private Date cjsj;
    @ApiModelProperty("变更人")
    private String bgr;
    @ApiModelProperty("变更时间")
    private Date bgsj;
    @ApiModelProperty("查询方式")
    private String cxfs;
    @ApiModelProperty("当前状态")
    private String dqzt;
    @ApiModelProperty("服务url地址")
    private String url;
    @ApiModelProperty("页面工具")
    private String ymgj;
    @ApiModelProperty("行工具")
    private String hgj;
    @ApiModelProperty("备注")
    private String bz;
    @ApiModelProperty("是否支持模糊 精确查询相互切换")
    private Integer canmhcx;
    @ApiModelProperty("动态加载js")
    private String js;
    @ApiModelProperty("自定义页面工具")
    private String zdyymgj;
    @ApiModelProperty("复选框颜色")
    private String fxkys;
    @ApiModelProperty("是否接口查询")
    private String sfjkcx;
    @ApiModelProperty("接口方法")
    private String jkff;
    @ApiModelProperty("调用接口")
    private String jk;
    @ApiModelProperty("返回值key")
    private String fhzkey;
    @ApiModelProperty("日志名称")
    private String rzmc;
    @ApiModelProperty("日志类型")
    private String rzlx;


    public String getCxmc() {
        return cxmc;
    }

    public void setCxmc(String cxmc) {
        this.cxmc = cxmc;
    }

    public String getCxdh() {
        return cxdh;
    }

    public void setCxdh(String cxdh) {
        this.cxdh = cxdh;
    }

    public String getCxid() {
        return cxid;
    }

    public void setCxid(String cxid) {
        this.cxid = cxid;
    }

    public String getCxsql() {
        return cxsql;
    }

    public void setCxsql(String cxsql) {
        this.cxsql = cxsql;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getBgr() {
        return bgr;
    }

    public void setBgr(String bgr) {
        this.bgr = bgr;
    }

    public Date getBgsj() {
        return bgsj;
    }

    public void setBgsj(Date bgsj) {
        this.bgsj = bgsj;
    }

    public String getCxfs() {
        return cxfs;
    }

    public void setCxfs(String cxfs) {
        this.cxfs = cxfs;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYmgj() {
        return ymgj;
    }

    public void setYmgj(String ymgj) {
        this.ymgj = ymgj;
    }

    public String getHgj() {
        return hgj;
    }

    public void setHgj(String hgj) {
        this.hgj = hgj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getCanmhcx() {
        return canmhcx;
    }

    public void setCanmhcx(Integer canmhcx) {
        this.canmhcx = canmhcx;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getZdyymgj() {
        return zdyymgj;
    }

    public void setZdyymgj(String zdyymgj) {
        this.zdyymgj = zdyymgj;
    }

    public String getFxkys() {
        return fxkys;
    }

    public void setFxkys(String fxkys) {
        this.fxkys = fxkys;
    }

    public String getSfjkcx() {
        return sfjkcx;
    }

    public void setSfjkcx(String sfjkcx) {
        this.sfjkcx = sfjkcx;
    }

    public String getJkff() {
        return jkff;
    }

    public void setJkff(String jkff) {
        this.jkff = jkff;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getFhzkey() {
        return fhzkey;
    }

    public void setFhzkey(String fhzkey) {
        this.fhzkey = fhzkey;
    }

    public String getRzmc() {
        return rzmc;
    }

    public void setRzmc(String rzmc) {
        this.rzmc = rzmc;
    }

    public String getRzlx() {
        return rzlx;
    }

    public void setRzlx(String rzlx) {
        this.rzlx = rzlx;
    }

    @Override
    public String toString() {
        return "DtcxDO{" +
                "cxmc='" + cxmc + '\'' +
                ", cxdh='" + cxdh + '\'' +
                ", cxid='" + cxid + '\'' +
                ", cxsql='" + cxsql + '\'' +
                ", cjr='" + cjr + '\'' +
                ", cjsj=" + cjsj +
                ", bgr='" + bgr + '\'' +
                ", bgsj=" + bgsj +
                ", cxfs='" + cxfs + '\'' +
                ", dqzt='" + dqzt + '\'' +
                ", url='" + url + '\'' +
                ", ymgj='" + ymgj + '\'' +
                ", hgj='" + hgj + '\'' +
                ", bz='" + bz + '\'' +
                ", canmhcx=" + canmhcx +
                ", js='" + js + '\'' +
                ", zdyymgj='" + zdyymgj + '\'' +
                ", fxkys='" + fxkys + '\'' +
                ", sfjkcx='" + sfjkcx + '\'' +
                ", jkff='" + jkff + '\'' +
                ", jk='" + jk + '\'' +
                ", fhzkey='" + fhzkey + '\'' +
                ", rzmc='" + rzmc + '\'' +
                ", rzlx='" + rzlx + '\'' +
                '}';
    }
}