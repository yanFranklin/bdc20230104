package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Table;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/08/25
 * @description 承包土地用途
 */
@Table(name = "s_zd_cbtdyt")
public class SZdCbtdytDO {
    private String dm;
    private String mc;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
