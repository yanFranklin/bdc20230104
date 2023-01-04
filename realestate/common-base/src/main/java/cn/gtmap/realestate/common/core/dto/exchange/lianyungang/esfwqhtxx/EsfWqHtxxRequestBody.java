package cn.gtmap.realestate.common.core.dto.exchange.lianyungang.esfwqhtxx;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/12/16/16:13
 * @Description:
 */
public class EsfWqHtxxRequestBody {

    private String htbh;
    private String cqzh;
    private String mfr;
    private String mfzjh;
    private String zl;

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getMfr() {
        return mfr;
    }

    public void setMfr(String mfr) {
        this.mfr = mfr;
    }

    public String getMfzjh() {
        return mfzjh;
    }

    public void setMfzjh(String mfzjh) {
        this.mfzjh = mfzjh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        return "EsfWqHtxxRequestBody{" +
                "htbh='" + htbh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", mfr='" + mfr + '\'' +
                ", mfzjh='" + mfzjh + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }
}
