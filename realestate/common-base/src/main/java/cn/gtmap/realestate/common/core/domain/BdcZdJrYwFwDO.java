package cn.gtmap.realestate.common.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 接入业务服务字典项
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-07 16:46
 **/
@Table(name = "Bdc_ZD_JRYWFW")
public class BdcZdJrYwFwDO {


    @Id
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
