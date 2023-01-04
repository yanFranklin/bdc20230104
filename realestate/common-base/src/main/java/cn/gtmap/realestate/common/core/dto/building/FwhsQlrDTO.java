package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;

/**
 * @program: realestate
 * @description: 户室资源包含权利人信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-03 09:49
 **/
public class FwhsQlrDTO extends FwHsDO {
    private String qlr;

    private String msr;

    public String getMsr() {
        return msr;
    }

    public void setMsr(String msr) {
        this.msr = msr;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    @Override
    public String toString() {
        return "FwhsQlrDTO{" +
                "qlr='" + qlr + '\'' +
                ", msr='" + msr + '\'' +
                '}';
    }
}
