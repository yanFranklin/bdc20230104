package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-29
 * @description
 */
@Table(name = "s_sj_bdcdyhlszt")
public class SSjBdcdyhlsztDO extends SSjBdcdyhxsztDO {

    @Id
    private String ztid;


    public String getZtid() {
        return ztid;
    }

    public void setZtid(String ztid) {
        this.ztid = ztid;
    }
}
