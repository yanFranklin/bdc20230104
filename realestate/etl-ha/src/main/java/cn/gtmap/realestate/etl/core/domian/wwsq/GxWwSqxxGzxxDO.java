package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/8/19
 * @description 共享外网更正信息
 */
@Table(name = "GX_WW_SQXX_GZXX")
public class GxWwSqxxGzxxDO {

    /**
     * 主键
     */
    @Id
    private String gzxxid;

    /**
     * 申请信息id
     */
    private String sqxxid;

    /**
     * 更正登记类型
     */
    private String gzdjlx;

    /**
     * 更正主体
     */
    private String gzzt;

    /**
     * 更正意见
     */
    private String gzyj;

    /**
     * 更正内容
     */
    private String gznr;

    public String getGzxxid() {
        return gzxxid;
    }

    public void setGzxxid(String gzxxid) {
        this.gzxxid = gzxxid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getGzdjlx() {
        return gzdjlx;
    }

    public void setGzdjlx(String gzdjlx) {
        this.gzdjlx = gzdjlx;
    }

    public String getGzzt() {
        return gzzt;
    }

    public void setGzzt(String gzzt) {
        this.gzzt = gzzt;
    }

    public String getGzyj() {
        return gzyj;
    }

    public void setGzyj(String gzyj) {
        this.gzyj = gzyj;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr;
    }
}
