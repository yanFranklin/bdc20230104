package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/1/3
 * @description 查封
 */
public class BdcdjbCfVO extends BdcCfDO {
    /**
     * 查封类型名称
     */
    String cflxmc;

    /**
     * 查封起始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date cfqsrq;

    /**
     * 查封结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date cfjsrq;

    /**
     * 解封登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date jfdjrq;



    /**
     * 登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date djrq;


    public Date getCfqsrq() {
        return cfqsrq;
    }

    public void setCfqsrq(Date cfqsrq) {
        this.cfqsrq = cfqsrq;
    }

    public Date getCfjsrq() {
        return cfjsrq;
    }

    public void setCfjsrq(Date cfjsrq) {
        this.cfjsrq = cfjsrq;
    }

    public Date getJfdjrq() {
        return jfdjrq;
    }

    public void setJfdjrq(Date jfdjrq) {
        this.jfdjrq = jfdjrq;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }

    public String getCflxmc() {
        return cflxmc;
    }

    public void setCflxmc(String cflxmc) {
        this.cflxmc = cflxmc;
    }
    @Override
    public String toString() {
        return "BdcdjbCfVO{" +
                "cflxmc='" + cflxmc + '\'' +
                ", cfqsrq=" + cfqsrq +
                ", cfjsrq=" + cfjsrq +
                ", jfdjrq=" + jfdjrq +
                ", djrq=" + djrq +
                '}';
    }
}
