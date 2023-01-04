package cn.gtmap.realestate.exchange.core.qo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 未销账报文信息查询条件
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-21 18:10
 **/
public class JgWxzBwxxQO {
    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("项目id")
    private String ywh;

    @ApiModelProperty("报文id")
    private String bwid;
    @ApiModelProperty("销账状态")
    private String xzzt;
    @ApiModelProperty("销账报文类型")
    private String xzbwlx;

    @ApiModelProperty("xzzt集合")
    private List<String> xzztList;

    @ApiModelProperty("xmidList")
    private List<String> xmidList;

    @ApiModelProperty("开始时间")
    private String kssj;


    @ApiModelProperty("结束时间")
    private String jssj;
    @ApiModelProperty("受理编号")
    private String slbh;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBwid() {
        return bwid;
    }

    public void setBwid(String bwid) {
        this.bwid = bwid;
    }

    public String getXzzt() {
        return xzzt;
    }

    public void setXzzt(String xzzt) {
        this.xzzt = xzzt;
    }

    public String getXzbwlx() {
        return xzbwlx;
    }

    public void setXzbwlx(String xzbwlx) {
        this.xzbwlx = xzbwlx;
    }

    public List<String> getXzztList() {
        return xzztList;
    }

    public void setXzztList(List<String> xzztList) {
        this.xzztList = xzztList;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "JgWxzBwxxQO{" +
                "id='" + id + '\'' +
                ", ywh='" + ywh + '\'' +
                ", bwid='" + bwid + '\'' +
                ", xzzt='" + xzzt + '\'' +
                ", xzbwlx='" + xzbwlx + '\'' +
                ", xzztList=" + xzztList +
                ", xmidList=" + xmidList +
                '}';
    }
}
