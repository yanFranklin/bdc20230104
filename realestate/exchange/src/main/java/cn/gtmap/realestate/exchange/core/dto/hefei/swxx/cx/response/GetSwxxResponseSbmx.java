package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.cx.response;
import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

import java.util.List;

/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 税务申报明细
 */
@IgnoreCast
public class GetSwxxResponseSbmx {
    private String cjjg;//成交价格
    private String fwwzdz;//房屋地址
    private List<GetSwxxResponseSklb> jyskblb;
    private String jyuuid;//交易uuid
    private String jzmj;//建筑面积
    private String mmfbz;//买卖方标志
    private String nsrmc;//纳税人名称
    private String nsrsbh;//纳税人编号
    private String ynse;//应纳税额

    public String getCjjg() {
        return cjjg;
    }

    public void setCjjg(String cjjg) {
        this.cjjg = cjjg;
    }

    public String getFwwzdz() {
        return fwwzdz;
    }

    public void setFwwzdz(String fwwzdz) {
        this.fwwzdz = fwwzdz;
    }

    public List<GetSwxxResponseSklb> getJyskblb() {
        return jyskblb;
    }

    public void setJyskblb(List<GetSwxxResponseSklb> jyskblb) {
        this.jyskblb = jyskblb;
    }

    public String getJyuuid() {
        return jyuuid;
    }

    public void setJyuuid(String jyuuid) {
        this.jyuuid = jyuuid;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getMmfbz() {
        return mmfbz;
    }

    public void setMmfbz(String mmfbz) {
        this.mmfbz = mmfbz;
    }

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getYnse() {
        return ynse;
    }

    public void setYnse(String ynse) {
        this.ynse = ynse;
    }
}
