package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2020/04/30
 * @description 电子证照证明主体信息实体DTO
 */
public class HefeiDzzzZsDataDTO {
    // 使用期限
    private String syqx;

    // 权利人
    private String qlr;

    // 不动产单元号
    private String bdcdyh;

    // 坐落
    private String zl;

    //共有情况
    private String gyqk;

    //权利类型
    private String qllx;

    //权利性质
    private String qlxz;

    //用途
    private String yt;

    //面积
    private String mj;

    //使用情况
    private String syqk;

    // 其他 qlqtzk
    private String qlqtzk;

    // 日 szsj 取日
    private String r1;

    // 月 szsj 取月
    private String y1;

    // 年 szsj 取年
    private String n1;

    // 编号
    private String no1;

    // 不动产编号a
    private String bh1;

    // 不动产编号b
    private String bh2;

    // 不动产编号c
    private String bh3;

    // 不动产编号d
    private String bh4;

    // 附记 fj
    private String fj1;

    // 附图
    private String photo;

    //持证者id qlrzjh
    private String czrsfzh1;

    //持证人姓名 qlrmc
    private String czr1;

    //证照编号 bdcqzh
    private String infoCode;

    // 附图2
    private String photo2;

    // zsid 二维码转base64
    private String ewm;

    //查询二维码
    private String cxewm;

    //信息二维码（芜湖）
    private String whewm;


    private List<Map<String, String>> czrZjhAndNames;

    public String getCxewm() {
        return cxewm;
    }

    public void setCxewm(String cxewm) {
        this.cxewm = cxewm;
    }

    public String getWhewm() {
        return whewm;
    }

    public void setWhewm(String whewm) {
        this.whewm = whewm;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getSyqk() {
        return syqk;
    }

    public void setSyqk(String syqk) {
        this.syqk = syqk;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String getNo1() {
        return no1;
    }

    public void setNo1(String no1) {
        this.no1 = no1;
    }

    public String getBh1() {
        return bh1;
    }

    public void setBh1(String bh1) {
        this.bh1 = bh1;
    }

    public String getBh2() {
        return bh2;
    }

    public void setBh2(String bh2) {
        this.bh2 = bh2;
    }

    public String getBh3() {
        return bh3;
    }

    public void setBh3(String bh3) {
        this.bh3 = bh3;
    }

    public String getBh4() {
        return bh4;
    }

    public void setBh4(String bh4) {
        this.bh4 = bh4;
    }

    public String getFj1() {
        return fj1;
    }

    public void setFj1(String fj1) {
        this.fj1 = fj1;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCzrsfzh1() {
        return czrsfzh1;
    }

    public void setCzrsfzh1(String czrsfzh1) {
        this.czrsfzh1 = czrsfzh1;
    }

    public String getCzr1() {
        return czr1;
    }

    public void setCzr1(String czr1) {
        this.czr1 = czr1;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public List<Map<String, String>> getCzrZjhAndNames() {
        return czrZjhAndNames;
    }

    public void setCzrZjhAndNames(List<Map<String, String>> czrZjhAndNames) {
        this.czrZjhAndNames = czrZjhAndNames;
    }

    @Override
    public String toString() {
        return "HefeiDzzzZsDataDTO{" +
                "syqx='" + syqx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", yt='" + yt + '\'' +
                ", mj='" + mj + '\'' +
                ", syqk='" + syqk + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", r1='" + r1 + '\'' +
                ", y1='" + y1 + '\'' +
                ", n1='" + n1 + '\'' +
                ", no1='" + no1 + '\'' +
                ", bh1='" + bh1 + '\'' +
                ", bh2='" + bh2 + '\'' +
                ", bh3='" + bh3 + '\'' +
                ", bh4='" + bh4 + '\'' +
                ", fj1='" + fj1 + '\'' +
                ", photo='" + photo + '\'' +
                ", czrsfzh1='" + czrsfzh1 + '\'' +
                ", czr1='" + czr1 + '\'' +
                ", infoCode='" + infoCode + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", ewm='" + ewm + '\'' +
                ", czrZjhAndNames=" + czrZjhAndNames +
                '}';
    }
}
