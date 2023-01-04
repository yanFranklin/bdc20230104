package cn.gtmap.realestate.common.core.domain.exchange;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: bdcdj3.0
 * @description: 接入校验结果实体类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-11-23 17:55
 **/
@Table(name = "BDC_JR_JYJG")
public class BdcJrJyjgDO {

    @Id
    private String id;

    private String xmid;

    private String bdcdyh;

    private String jyjg;

    private Date jcsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getJyjg() {
        return jyjg;
    }

    public void setJyjg(String jyjg) {
        this.jyjg = jyjg;
    }

    public Date getJcsj() {
        return jcsj;
    }

    public void setJcsj(Date jcsj) {
        this.jcsj = jcsj;
    }

    @Override
    public String toString() {
        return "BdcJrJyjgDO{" +
                "id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", jyjg='" + jyjg + '\'' +
                ", jcsj=" + jcsj +
                '}';
    }
}
