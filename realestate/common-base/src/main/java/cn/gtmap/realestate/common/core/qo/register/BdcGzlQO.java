package cn.gtmap.realestate.common.core.qo.register;

import java.util.List;

/**
 * @program: realestate
 * @description: 工作流查询条件
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-30 15:24
 **/
public class BdcGzlQO {

    private String sjid;

    private String sjbs;

    private String jdmc;

    private String jkid;

    private String jkmc;

    private Integer jklx;

    private String yymc;

    private String gljk;

    private List<String> jkidList;

    private String sjmc;

    public String getSjid() {
        return sjid;
    }

    public void setSjid(String sjid) {
        this.sjid = sjid;
    }

    public String getSjbs() {
        return sjbs;
    }

    public void setSjbs(String sjbs) {
        this.sjbs = sjbs;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    public Integer getJklx() {
        return jklx;
    }

    public void setJklx(Integer jklx) {
        this.jklx = jklx;
    }

    public List<String> getJkidList() {
        return jkidList;
    }

    public void setJkidList(List<String> jkidList) {
        this.jkidList = jkidList;
    }

    public String getYymc() {
        return yymc;
    }

    public void setYymc(String yymc) {
        this.yymc = yymc;
    }

    public String getGljk() {
        return gljk;
    }

    public void setGljk(String gljk) {
        this.gljk = gljk;
    }

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    @Override
    public String toString() {
        return "BdcGzlQO{" +
                "sjid='" + sjid + '\'' +
                ", sjbs='" + sjbs + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", jkid='" + jkid + '\'' +
                ", jkmc='" + jkmc + '\'' +
                ", jklx=" + jklx +
                ", yymc='" + yymc + '\'' +
                ", gljk='" + gljk + '\'' +
                ", jkidList=" + jkidList +
                ", sjmc='" + sjmc + '\'' +
                '}';
    }
}
