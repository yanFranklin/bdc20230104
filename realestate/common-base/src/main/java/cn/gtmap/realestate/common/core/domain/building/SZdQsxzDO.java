package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-27
 * @description 权属性质实体
 */
@Table(name = "s_zd_qsxz")
public class SZdQsxzDO {

    private String dm;

    private String mc;

    private String qslx;

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

    public String getQslx() {
        return qslx;
    }

    public void setQslx(String qslx) {
        this.qslx = qslx;
    }
}
