package cn.gtmap.realestate.exchange.core.domain.zd;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 房屋建设状态字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 10:56
 **/
@Table(name = "BDC_ZD_FWJSZT")
public class BdcZdFwjsztDO {
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
        return "BdcZdFwjsztDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
