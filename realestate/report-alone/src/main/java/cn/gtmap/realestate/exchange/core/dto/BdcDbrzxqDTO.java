package cn.gtmap.realestate.exchange.core.dto;

import cn.gtmap.realestate.exchange.core.domain.exchange.BdcDbrzjlXqDO;

/**
 * @program: realestate
 * @description: 登簿日志详情DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-27 16:09
 **/
public class BdcDbrzxqDTO extends BdcDbrzjlXqDO {

    private String slbh;

    private String bdcqzh;

    private String xh;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }
}
