package cn.gtmap.realestate.common.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: bdcdj3.0
 * @description: 销账比对类型字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-11-08 16:47
 **/
@Table(name = "BDC_ZD_XZBDLX")
public class BdcZdXzBdlxDO {

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
        return "BdcZdXzBdlxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
