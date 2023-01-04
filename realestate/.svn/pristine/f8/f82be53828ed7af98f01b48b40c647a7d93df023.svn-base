package cn.gtmap.realestate.exchange.core.dto.zrpay.jfqr;


import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 缴费结果通知入参
 */
public class PayResultNoticeVo {

    /**
     * 接口版本号  固定值2
     */
    @JSONField(name = "VNo")
    private String VNo = "2";

    /**
     * 签名算法  只支持SHA256withRSA
     */
    @JSONField(name = "Sgn_Algr")
    private String sgnAlgr = "SHA256withRSA";

    /**
     * 商品订单号
     */
    @JSONField(name = "Cmdty_Ordr_No")
    private String cmdtyOrdrNo;

    /**
     * 支付订单号
     */
    @JSONField(name = "Py_Ordr_No")
    private String pyOrdeNo;

    /**
     * 缴费渠道 1网站
     * 2 APP
     * 3 H5
     * 4大厅收银台
     * 5自助终端 6系统发起 7POS
     * 8微信小程序
     * 9其他
     */
    @JSONField(name = "Py_Chnl_Cd")
    private String pyChnlCd;

    /**
     * 支付方式代码
     * 1 龙支付
     * 2 银联（网银）
     * 3 银联（快捷付）
     * 4 微信
     * 5 支付宝
     * 6 他行APP
     * 7 扫脸支付
     * 8 POS刷卡
     * 9 现金
     * 10 跳转各银行网银页面支付
     * 11POS主扫
     * 12POS被扫
     */
    @JSONField(name = "PyMd_Cd")
    private String pyMdCd;

    /**
     * 订单状态代码
     */
    @JSONField(name = "Ordr_StCd")
    private String orderStCd;

    /**
     * 交易时间
     */
    @JSONField(name = "Txn_tm")
    private String txnTm;

    /**
     * 总金额
     */
    @JSONField(name = "TAmt")
    private String tAmt;

    /**
     * 备注一
     */
    @JSONField(name = "Rmrk1")
    private String remark1;

    /**
     * 备注二
     */
    @JSONField(name = "Rmrk2")
    private String remark2;

    @JSONField(name = "LIST1")
    private List<PayResultNoticeItemVo> list1;

    public String getVNo() {
        return VNo;
    }

    public void setVNo(String VNo) {
        this.VNo = VNo;
    }

    public String getSgnAlgr() {
        return sgnAlgr;
    }

    public void setSgnAlgr(String sgnAlgr) {
        this.sgnAlgr = sgnAlgr;
    }

    public String getCmdtyOrdrNo() {
        return cmdtyOrdrNo;
    }

    public void setCmdtyOrdrNo(String cmdtyOrdrNo) {
        this.cmdtyOrdrNo = cmdtyOrdrNo;
    }

    public String getPyOrdeNo() {
        return pyOrdeNo;
    }

    public void setPyOrdeNo(String pyOrdeNo) {
        this.pyOrdeNo = pyOrdeNo;
    }

    public String getPyChnlCd() {
        return pyChnlCd;
    }

    public void setPyChnlCd(String pyChnlCd) {
        this.pyChnlCd = pyChnlCd;
    }

    public String getPyMdCd() {
        return pyMdCd;
    }

    public void setPyMdCd(String pyMdCd) {
        this.pyMdCd = pyMdCd;
    }

    public String getOrderStCd() {
        return orderStCd;
    }

    public void setOrderStCd(String orderStCd) {
        this.orderStCd = orderStCd;
    }

    public String getTxnTm() {
        return txnTm;
    }

    public void setTxnTm(String txnTm) {
        this.txnTm = txnTm;
    }

    public String gettAmt() {
        return tAmt;
    }

    public void settAmt(String tAmt) {
        this.tAmt = tAmt;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public List<PayResultNoticeItemVo> getList1() {
        return list1;
    }

    public void setList1(List<PayResultNoticeItemVo> list1) {
        this.list1 = list1;
    }

    @Override
    public String toString() {
        return "PayResultNoticeVo{" +
                "VNo='" + VNo + '\'' +
                ", sgnAlgr='" + sgnAlgr + '\'' +
                ", cmdtyOrdrNo='" + cmdtyOrdrNo + '\'' +
                ", pyOrdeNo='" + pyOrdeNo + '\'' +
                ", pyChnlCd='" + pyChnlCd + '\'' +
                ", pyMdCd='" + pyMdCd + '\'' +
                ", orderStCd='" + orderStCd + '\'' +
                ", txnTm='" + txnTm + '\'' +
                ", tAmt='" + tAmt + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", list1=" + list1 +
                '}';
    }
}
