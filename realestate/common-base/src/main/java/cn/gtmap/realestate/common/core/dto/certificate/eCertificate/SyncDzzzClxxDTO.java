package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.accept.DzzzClxxDTO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/28
 * @description
 */
public class SyncDzzzClxxDTO {

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  电子证照材料信息
      */
    private DzzzClxxDTO dzzzClxxDTO;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  证书信息
     */
    private BdcZsDO bdcZsDO;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  业务号
     */
    private String slbh;

    public DzzzClxxDTO getDzzzClxxDTO() {
        return dzzzClxxDTO;
    }

    public void setDzzzClxxDTO(DzzzClxxDTO dzzzClxxDTO) {
        this.dzzzClxxDTO = dzzzClxxDTO;
    }

    public BdcZsDO getBdcZsDO() {
        return bdcZsDO;
    }

    public void setBdcZsDO(BdcZsDO bdcZsDO) {
        this.bdcZsDO = bdcZsDO;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "SyncDzzzClxxDTO{" +
                "dzzzClxxDTO=" + dzzzClxxDTO +
                ", bdcZsDO=" + bdcZsDO +
                ", slbh='" + slbh + '\'' +
                '}';
    }
}
