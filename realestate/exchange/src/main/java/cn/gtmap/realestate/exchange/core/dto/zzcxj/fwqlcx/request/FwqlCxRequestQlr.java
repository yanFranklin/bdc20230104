package cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request;

import cn.gtmap.realestate.exchange.core.dto.zzcxj.BaseQlrRequest;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description 房屋权属查询(以权利为主) 权利人请求参数
 */

public class FwqlCxRequestQlr extends BaseQlrRequest {
    private String bdcdyh;
    private String cqzh;
    private String zl;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
