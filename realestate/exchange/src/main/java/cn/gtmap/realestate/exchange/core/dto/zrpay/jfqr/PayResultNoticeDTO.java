package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;


import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 缴费结果通知返回
 */
public class PayResultNoticeDTO {

    /**
     * 接收状态代码  00-成功，01-失败
     */
    @JSONField(name = "Rcv_StCd")
    private String rcvStCd;

    /**
     * #响应码
     */
    @JSONField(name = "SYS_RESP_CODE")
    private String sysRespCode;

    /**
     * #响应信息
     */
    @JSONField(name = "Rsp_Inf")
    private String rspInf;

    /**
     * 接收时间
     */
    @JSONField(name = "Rcv_Tm")
    private String recTm;

    public String getRcvStCd() {
        return rcvStCd;
    }

    public void setRcvStCd(String rcvStCd) {
        this.rcvStCd = rcvStCd;
    }

    public String getSysRespCode() {
        return sysRespCode;
    }

    public void setSysRespCode(String sysRespCode) {
        this.sysRespCode = sysRespCode;
    }

    public String getRspInf() {
        return rspInf;
    }

    public void setRspInf(String rspInf) {
        this.rspInf = rspInf;
    }

    public String getRecTm() {
        return recTm;
    }

    public void setRecTm(String recTm) {
        this.recTm = recTm;
    }

    @Override
    public String toString() {
        return "PayResultNoticeDTO{" +
                "rcvStCd='" + rcvStCd + '\'' +
                ", sysRespCode='" + sysRespCode + '\'' +
                ", rspInf='" + rspInf + '\'' +
                ", recTm='" + recTm + '\'' +
                '}';
    }
}
