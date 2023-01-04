package cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.request;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2019/1/10
 * @description
 */

import org.hibernate.validator.constraints.NotBlank;

public class SjptSscxRequestData {
    private String cxsqdh;//请求单号
    private String xz;//请求类型
    @NotBlank
    private String xm;//被查询/核验人名称
    @NotBlank
    private String zjzl;//证件种类
    @NotBlank
    private String zjhm;//证件号码
    private String bdcqzh;//不动产权证号
    private String bdcdyh;//不动产单元号
    private String zl;//坐落
    private String cxqy;//查询区域  6位区县代码
    private String cxfw;//查询范围  0-历史+现势   1-现势
    private String wsbh ;//文书编号

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public String getXz() {
        return xz;
    }

    public void setXz(String xz) {
        this.xz = xz;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
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

    public String getCxqy() {
        return cxqy;
    }

    public void setCxqy(String cxqy) {
        this.cxqy = cxqy;
    }

    public String getCxfw() {
        return cxfw;
    }

    public void setCxfw(String cxfw) {
        this.cxfw = cxfw;
    }

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }
}
