package cn.gtmap.realestate.common.core.domain.building;


import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-04
 * @description
 */
@Table(name="h_cbzd_dcb")
public class HCbzdDcbDO extends CbzdDcbDO {
    private String bgbh;


    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }
}
