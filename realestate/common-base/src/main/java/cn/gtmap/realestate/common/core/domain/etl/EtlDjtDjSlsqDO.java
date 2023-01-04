package cn.gtmap.realestate.common.core.domain.etl;

import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-01
 * @description
 */
public class EtlDjtDjSlsqDO extends DjtDjSlsqDO {

    private String bdcdyh;
    private String dealflag;

    public String getDealflag() {
        return dealflag;
    }

    public void setDealflag(String dealflag) {
        this.dealflag = dealflag;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }
}
