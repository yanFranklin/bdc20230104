package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;

/**
 * @program: realestate
 * @description: 手里项目信息和原证书的证书id信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-11-15 13:38
 **/
public class BdcSlXmZsDTO {

    private String zsid;

    private BdcSlXmDO bdcSlXmDO;

    private BdcXmDO bdcXmDO;

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    @Override
    public String toString() {
        return "BdcSlXmZsDTO{" +
                "zsid='" + zsid + '\'' +
                ", bdcSlXmDO=" + bdcSlXmDO +
                ", bdcXmDO=" + bdcXmDO +
                '}';
    }
}
