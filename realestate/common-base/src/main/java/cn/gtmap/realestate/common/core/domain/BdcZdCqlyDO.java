package cn.gtmap.realestate.common.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 产权来源字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-28 19:34
 **/
@Table(name = "BDC_ZD_CQLY")
public class BdcZdCqlyDO {

    @Id
    /**代码*/
    private Integer dm;
    /**
     * 名称
     */
    private String mc;

    public Integer getDm() {
        return dm;
    }

    public void setDm(Integer dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @Override
    public String toString() {
        return "BdcZdCqlyDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
