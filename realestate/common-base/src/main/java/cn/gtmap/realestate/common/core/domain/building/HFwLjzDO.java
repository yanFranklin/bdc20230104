package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Table;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name="h_fw_ljz")
public class HFwLjzDO extends FwLjzDO {

    private String bgbh;

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }
}
