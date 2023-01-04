package cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.request;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2019/1/10
 * @description
 */

public class SjptSscxRequestHead {
    private String cxjgbs;
    private String openid;

    public String getCxjgbs() {
        return cxjgbs;
    }

    public void setCxjgbs(String cxjgbs) {
        this.cxjgbs = cxjgbs;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
