package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 第三方收费需要字段
 */
public class DsfSfxxDTO {

    // 用户名
    private String userName;

    // 缴费人电话
    private String jfrdh;

    private String slbh;

    //缴费人证件号
    private String jfrzjh;
    //票据代码
    private String pjdm;

    //收费类型
    private String sftype;

    // 收费项目id 用于作为推送收费的唯一id
    private String sfxmid;

    //缴费人姓名
    private String jfrxm;

    //区县代码
    private String qxdm;

    //受理人姓名
    private String slrxm;

    //抵押登记小类
    private String dydjxl;

    //权利类型
    private String qllx;

    //预告登记种类
    private String ygdjzl;

    //收款人证件号
    private String skrzjh;

    //坐落
    private String zl;

    public String getJfrxm() {
        return jfrxm;
    }

    public void setJfrxm(String jfrxm) {
        this.jfrxm = jfrxm;
    }

    public String getSftype() {
        return sftype;
    }

    public void setSftype(String sftype) {
        this.sftype = sftype;
    }

    public String getPjdm() {
        return pjdm;
    }

    public void setPjdm(String pjdm) {
        this.pjdm = pjdm;
    }

    public String getJfrzjh() {
        return jfrzjh;
    }

    public void setJfrzjh(String jfrzjh) {
        this.jfrzjh = jfrzjh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJfrdh() {
        return jfrdh;
    }

    public void setJfrdh(String jfrdh) {
        this.jfrdh = jfrdh;
    }

    public String getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(String sfxmid) {
        this.sfxmid = sfxmid;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSlrxm() {
        return slrxm;
    }

    public void setSlrxm(String slrxm) {
        this.slrxm = slrxm;
    }

    public String getDydjxl() {
        return dydjxl;
    }

    public void setDydjxl(String dydjxl) {
        this.dydjxl = dydjxl;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public String getSkrzjh() {
        return skrzjh;
    }

    public void setSkrzjh(String skrzjh) {
        this.skrzjh = skrzjh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        return "DsfSfxxDTO{" +
                "userName='" + userName + '\'' +
                ", jfrdh='" + jfrdh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", jfrzjh='" + jfrzjh + '\'' +
                ", pjdm='" + pjdm + '\'' +
                ", sftype='" + sftype + '\'' +
                ", sfxmid='" + sfxmid + '\'' +
                ", jfrxm='" + jfrxm + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", slrxm='" + slrxm + '\'' +
                ", dydjxl='" + dydjxl + '\'' +
                ", qllx='" + qllx + '\'' +
                ", ygdjzl='" + ygdjzl + '\'' +
                ", skrzjh='" + skrzjh + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }
}
