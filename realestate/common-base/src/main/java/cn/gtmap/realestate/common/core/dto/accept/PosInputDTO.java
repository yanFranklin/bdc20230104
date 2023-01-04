package cn.gtmap.realestate.common.core.dto.accept;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2022/8/16 10:24
 * @description pos机入参
 */
public class PosInputDTO {

    private String transType; //交易指令
    private String transAmount; //交易金额
    private String installmentTimes; //分期期数
    private String oldPostrace; //原交易流水号（撤销交易需要）
    private String oldHostTrace; //原交易系统检索号（退货交易需要）
    private String oldTransDate; //原交易日期（退货交易需要 MMDD）
    private String oldAuthNo; //原交易授权号
    private String projectCode; //分期项目编号
    private String personNum; //权益服务人数
    private String oldTerminalNum; //原终端号
    private String oldBatchNum; //原批次号
    private String track2; //二磁道信息 预留
    private String track3; //三磁道信息 预留
    private String merchantNum; //商户编号 预留
    private String terminalNum; //终端编号 预留
    private String merchantNameCH; //商户中文(备用)
    private String merchantNameEN; //商户英文(备用)
    private String cashTraceNum; //收银流水号，MQTT 模式为必传项
    private String cashPcNum; //后补空格 款台号
    private String cashierNum; //后补空格 操作员号
    private String longQRPay; //龙支付二维码\银联二维码
    private String couponNum; //惠兜圈优惠券号
    private String platID; //柜台编号 集中收银使用 左对齐 右补空格
    private String merOrderID; //订单号 集中收银使用，按下文订单号规则生成
    private String allCardAndExp;//密文完整卡号+密文卡号有效期用于无卡撤销，预授权撤销，预授权完成

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public String getInstallmentTimes() {
        return installmentTimes;
    }

    public void setInstallmentTimes(String installmentTimes) {
        this.installmentTimes = installmentTimes;
    }

    public String getOldPostrace() {
        return oldPostrace;
    }

    public void setOldPostrace(String oldPostrace) {
        this.oldPostrace = oldPostrace;
    }

    public String getOldHostTrace() {
        return oldHostTrace;
    }

    public void setOldHostTrace(String oldHostTrace) {
        this.oldHostTrace = oldHostTrace;
    }

    public String getOldTransDate() {
        return oldTransDate;
    }

    public void setOldTransDate(String oldTransDate) {
        this.oldTransDate = oldTransDate;
    }

    public String getOldAuthNo() {
        return oldAuthNo;
    }

    public void setOldAuthNo(String oldAuthNo) {
        this.oldAuthNo = oldAuthNo;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getOldTerminalNum() {
        return oldTerminalNum;
    }

    public void setOldTerminalNum(String oldTerminalNum) {
        this.oldTerminalNum = oldTerminalNum;
    }

    public String getOldBatchNum() {
        return oldBatchNum;
    }

    public void setOldBatchNum(String oldBatchNum) {
        this.oldBatchNum = oldBatchNum;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getTrack3() {
        return track3;
    }

    public void setTrack3(String track3) {
        this.track3 = track3;
    }

    public String getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum;
    }

    public String getMerchantNameCH() {
        return merchantNameCH;
    }

    public void setMerchantNameCH(String merchantNameCH) {
        this.merchantNameCH = merchantNameCH;
    }

    public String getMerchantNameEN() {
        return merchantNameEN;
    }

    public void setMerchantNameEN(String merchantNameEN) {
        this.merchantNameEN = merchantNameEN;
    }

    public String getCashTraceNum() {
        return cashTraceNum;
    }

    public void setCashTraceNum(String cashTraceNum) {
        this.cashTraceNum = cashTraceNum;
    }

    public String getCashPcNum() {
        return cashPcNum;
    }

    public void setCashPcNum(String cashPcNum) {
        this.cashPcNum = cashPcNum;
    }

    public String getCashierNum() {
        return cashierNum;
    }

    public void setCashierNum(String cashierNum) {
        this.cashierNum = cashierNum;
    }

    public String getLongQRPay() {
        return longQRPay;
    }

    public void setLongQRPay(String longQRPay) {
        this.longQRPay = longQRPay;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public String getPlatID() {
        return platID;
    }

    public void setPlatID(String platID) {
        this.platID = platID;
    }

    public String getMerOrderID() {
        return merOrderID;
    }

    public void setMerOrderID(String merOrderID) {
        this.merOrderID = merOrderID;
    }

    public String getAllCardAndExp() {
        return allCardAndExp;
    }

    public void setAllCardAndExp(String allCardAndExp) {
        this.allCardAndExp = allCardAndExp;
    }

    @Override
    public String toString() {
        return "PosInputDto{" +
                "transType='" + transType + '\'' +
                ", transAmount='" + transAmount + '\'' +
                ", installmentTimes='" + installmentTimes + '\'' +
                ", oldPostrace='" + oldPostrace + '\'' +
                ", oldHostTrace='" + oldHostTrace + '\'' +
                ", oldTransDate='" + oldTransDate + '\'' +
                ", oldAuthNo='" + oldAuthNo + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", personNum='" + personNum + '\'' +
                ", oldTerminalNum='" + oldTerminalNum + '\'' +
                ", oldBatchNum='" + oldBatchNum + '\'' +
                ", track2='" + track2 + '\'' +
                ", track3='" + track3 + '\'' +
                ", merchantNum='" + merchantNum + '\'' +
                ", terminalNum='" + terminalNum + '\'' +
                ", merchantNameCH='" + merchantNameCH + '\'' +
                ", merchantNameEN='" + merchantNameEN + '\'' +
                ", cashTraceNum='" + cashTraceNum + '\'' +
                ", cashPcNum='" + cashPcNum + '\'' +
                ", cashierNum='" + cashierNum + '\'' +
                ", longQRPay='" + longQRPay + '\'' +
                ", couponNum='" + couponNum + '\'' +
                ", platID='" + platID + '\'' +
                ", merOrderID='" + merOrderID + '\'' +
                ", allCardAndExp='" + allCardAndExp + '\'' +
                '}';
    }
}
