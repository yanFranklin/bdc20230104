package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Table;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2018/12/14
 * @description 权利状态字典表
 */
@Table(name = "s_zd_qlzt")
public class SZdQlztDO {
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
