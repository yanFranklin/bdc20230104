package cn.gtmap.realestate.common.core.dto;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/19/15:38
 * @Description:
 */
public class BdcXmCfDTO extends BdcXmDO {
    String cfbh;

    String zh;

    String fjh;

    public String getCfbh() {
        return cfbh;
    }

    public void setCfbh(String cfbh) {
        this.cfbh = cfbh;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    @Override
    public String toString() {
        return "BdcXmCfDTO{" +
                "cfbh='" + cfbh + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                '}';
    }
}
