package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request;

/*
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0, 2020/05/15
 * @description 依据差额征收标记
 */
public class CrfSdsjcxm {
    // 房屋原值购置房价款
    private String yzfk;
    // 房屋原值契税[页面新增字段【原契税金额】]
    private String yzqs;
    // 房屋原值印花税[新增【原印花税】]
    private String yzyhs;
    // 屋原值产权登记费[新增【原登记费】]
    private String yzdjf;
    // 房屋原值房地产交易手续费[调取接口时后台自动传空值即可]
    private String yzjysxf;
    // 房屋原值按规定缴纳的其他税费[新增【原其他税费】]
    private String yzqt;
    // 转让住房过程中缴纳的税费土地增值税[调取接口时后台自动传空值即可]
    private String zrzzs;
    // 转让住房过程中缴纳的税费 印花税[调取接口时后台自动传空值即可]
    private String zryhs;
    // 转让住房过程中缴纳的税费 按规定缴纳的其他税费
    private String zrqt;
    // 合理费用 住房装修费用
    private String hlzx;
    // 合理费用 住房贷款利息[新增【住房贷款利息】]
    private String hldklx;
    // 合理费用 按规定缴纳的公证费[新增【公证费用】]
    private String hlgzf;
    // 合理费用 按规定缴纳的其他费用[新增【其他费用】]
    private String hlqt;


    public String getYzfk() {
        return yzfk;
    }

    public void setYzfk(String yzfk) {
        this.yzfk = yzfk;
    }

    public String getYzqs() {
        return yzqs;
    }

    public void setYzqs(String yzqs) {
        this.yzqs = yzqs;
    }

    public String getYzyhs() {
        return yzyhs;
    }

    public void setYzyhs(String yzyhs) {
        this.yzyhs = yzyhs;
    }

    public String getYzdjf() {
        return yzdjf;
    }

    public void setYzdjf(String yzdjf) {
        this.yzdjf = yzdjf;
    }

    public String getYzjysxf() {
        return yzjysxf;
    }

    public void setYzjysxf(String yzjysxf) {
        this.yzjysxf = yzjysxf;
    }

    public String getYzqt() {
        return yzqt;
    }

    public void setYzqt(String yzqt) {
        this.yzqt = yzqt;
    }

    public String getZrzzs() {
        return zrzzs;
    }

    public void setZrzzs(String zrzzs) {
        this.zrzzs = zrzzs;
    }

    public String getZryhs() {
        return zryhs;
    }

    public void setZryhs(String zryhs) {
        this.zryhs = zryhs;
    }

    public String getZrqt() {
        return zrqt;
    }

    public void setZrqt(String zrqt) {
        this.zrqt = zrqt;
    }

    public String getHlzx() {
        return hlzx;
    }

    public void setHlzx(String hlzx) {
        this.hlzx = hlzx;
    }

    public String getHldklx() {
        return hldklx;
    }

    public void setHldklx(String hldklx) {
        this.hldklx = hldklx;
    }

    public String getHlgzf() {
        return hlgzf;
    }

    public void setHlgzf(String hlgzf) {
        this.hlgzf = hlgzf;
    }

    public String getHlqt() {
        return hlqt;
    }

    public void setHlqt(String hlqt) {
        this.hlqt = hlqt;
    }
}
