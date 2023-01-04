package cn.gtmap.realestate.exchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/01,1.0
 * @description
 */
@Table(name = "dv_bzcx")
public class DvBzcxDo {

    /**
     * 外网受理编号
     */
    private String wregnum;
    /**
     * 内网受理编号
     */
    private String nregnum;
    /**
     * 状态
     */
    private String status;


    public String getWregnum() {
        return wregnum;
    }

    public void setWregnum(String wregnum) {
        this.wregnum = wregnum;
    }


    public String getNregnum() {
        return nregnum;
    }

    public void setNregnum(String nregnum) {
        this.nregnum = nregnum;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DvBzcx{" +
                "wregnum='" + wregnum + '\'' +
                ", nregnum='" + nregnum + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
