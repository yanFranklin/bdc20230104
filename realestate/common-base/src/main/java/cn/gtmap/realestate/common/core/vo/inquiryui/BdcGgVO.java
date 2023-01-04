package cn.gtmap.realestate.common.core.vo.inquiryui;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 常州公告页面数据保存处理
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-02 16:26
 **/
public class BdcGgVO {

    @ApiModelProperty("公告类型")
    private Integer gglx;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;

    @ApiModelProperty("公告编号")
    private String ggbh;

    @ApiModelProperty("公告结束时间-年")
    private String ggjssjn;

    @ApiModelProperty("公告结束时间-月")
    private String ggjssjy;

    @ApiModelProperty("公告结束时间-日")
    private String ggjssjr;

    @ApiModelProperty("异议材料送达地址")
    private String yyclsddz;

    @ApiModelProperty("联系电话")
    private String lxdh;

    @ApiModelProperty("公告开始时间-年")
    private String ggkssjn;

    @ApiModelProperty("公告开始时间-月")
    private String ggkssjy;

    @ApiModelProperty("公告开始时间-日")
    private String ggkssjr;

    @ApiModelProperty("义务人死亡时间-年")
    private String ywrSwsjN;

    @ApiModelProperty("义务人死亡时间-月")
    private String ywrSwsjY;

    @ApiModelProperty("义务人死亡时间-日")
    private String ywrSwsjR;

    @ApiModelProperty("房屋注销时间-年")
    private String fwzxsjN;

    @ApiModelProperty("房屋注销时间-月")
    private String fwzxsjY;

    @ApiModelProperty("房屋注销时间-日")
    private String fwzxsjR;

    @ApiModelProperty("图片名称")
    private String tpmc;

    @ApiModelProperty("声明人")
    private String smr;

    @ApiModelProperty("权利人死亡时间集合")
    private List<Map<String,String>> qlrswsjList;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("不动产权证号")
    private String ycqzh;

    public Integer getGglx() {
        return gglx;
    }

    public void setGglx(Integer gglx) {
        this.gglx = gglx;
    }

    public String getGgbh() {
        return ggbh;
    }

    public void setGgbh(String ggbh) {
        this.ggbh = ggbh;
    }

    public String getGgjssjn() {
        return ggjssjn;
    }

    public void setGgjssjn(String ggjssjn) {
        this.ggjssjn = ggjssjn;
    }

    public String getGgjssjy() {
        return ggjssjy;
    }

    public void setGgjssjy(String ggjssjy) {
        this.ggjssjy = ggjssjy;
    }

    public String getGgjssjr() {
        return ggjssjr;
    }

    public void setGgjssjr(String ggjssjr) {
        this.ggjssjr = ggjssjr;
    }

    public String getYyclsddz() {
        return yyclsddz;
    }

    public void setYyclsddz(String yyclsddz) {
        this.yyclsddz = yyclsddz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getGgkssjn() {
        return ggkssjn;
    }

    public void setGgkssjn(String ggkssjn) {
        this.ggkssjn = ggkssjn;
    }

    public String getGgkssjy() {
        return ggkssjy;
    }

    public void setGgkssjy(String ggkssjy) {
        this.ggkssjy = ggkssjy;
    }

    public String getGgkssjr() {
        return ggkssjr;
    }

    public void setGgkssjr(String ggkssjr) {
        this.ggkssjr = ggkssjr;
    }

    public String getYwrSwsjN() {
        return ywrSwsjN;
    }

    public void setYwrSwsjN(String ywrSwsjN) {
        this.ywrSwsjN = ywrSwsjN;
    }

    public String getYwrSwsjY() {
        return ywrSwsjY;
    }

    public void setYwrSwsjY(String ywrSwsjY) {
        this.ywrSwsjY = ywrSwsjY;
    }

    public String getYwrSwsjR() {
        return ywrSwsjR;
    }

    public void setYwrSwsjR(String ywrSwsjR) {
        this.ywrSwsjR = ywrSwsjR;
    }

    public String getFwzxsjN() {
        return fwzxsjN;
    }

    public void setFwzxsjN(String fwzxsjN) {
        this.fwzxsjN = fwzxsjN;
    }

    public String getFwzxsjY() {
        return fwzxsjY;
    }

    public void setFwzxsjY(String fwzxsjY) {
        this.fwzxsjY = fwzxsjY;
    }

    public String getFwzxsjR() {
        return fwzxsjR;
    }

    public void setFwzxsjR(String fwzxsjR) {
        this.fwzxsjR = fwzxsjR;
    }

    public String getTpmc() {
        return tpmc;
    }

    public void setTpmc(String tpmc) {
        this.tpmc = tpmc;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSmr() {
        return smr;
    }

    public void setSmr(String smr) {
        this.smr = smr;
    }

    public List<Map<String, String>> getQlrswsjList() {
        return qlrswsjList;
    }

    public void setQlrswsjList(List<Map<String, String>> qlrswsjList) {
        this.qlrswsjList = qlrswsjList;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    @Override
    public String toString() {
        return "BdcGgVO{" +
                "gglx=" + gglx +
                ", gzlslid='" + gzlslid + '\'' +
                ", ggbh='" + ggbh + '\'' +
                ", ggjssjn='" + ggjssjn + '\'' +
                ", ggjssjy='" + ggjssjy + '\'' +
                ", ggjssjr='" + ggjssjr + '\'' +
                ", yyclsddz='" + yyclsddz + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", ggkssjn='" + ggkssjn + '\'' +
                ", ggkssjy='" + ggkssjy + '\'' +
                ", ggkssjr='" + ggkssjr + '\'' +
                ", ywrSwsjN='" + ywrSwsjN + '\'' +
                ", ywrSwsjY='" + ywrSwsjY + '\'' +
                ", ywrSwsjR='" + ywrSwsjR + '\'' +
                ", fwzxsjN='" + fwzxsjN + '\'' +
                ", fwzxsjY='" + fwzxsjY + '\'' +
                ", fwzxsjR='" + fwzxsjR + '\'' +
                ", tpmc='" + tpmc + '\'' +
                ", smr='" + smr + '\'' +
                ", qlrswsjList=" + qlrswsjList +
                '}';
    }
}
