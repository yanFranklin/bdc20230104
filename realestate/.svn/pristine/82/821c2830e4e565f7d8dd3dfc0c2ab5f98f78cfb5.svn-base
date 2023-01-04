package cn.gtmap.realestate.exchange.core.dto.shucheng.sq.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/5/9
 * @description
 */
public class ShuChengShuiCheckResDto implements Serializable {


    private static final long serialVersionUID = -7302260712025656871L;
    /**
     * 结果代码（见附录），0000为成功
     */
    private String rtnCode;
    /**
     * 结果描述
     */
    private String rtnMsg;
    /**
     * 用户编号
     */
    private String userID;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户地址
     */
    private String userAddr;
    /**
     * 用户类型
     */
    private String consType;
    /**
     * 账户余额
     */
    private String prepayAmt;
    /**
     * 合计欠费金额
     */
    private String totalOweAmt;
    /**
     * 合计应收金额
     */
    private String totalRcvblAmt;
    /**
     * 合计实收金额
     */
    private String totalRcvedAmt;
    /**
     * 合计违约金
     */
    private String totalPenalty;
    /**
     * 明细记录条数
     */
    private String recordCount;
    /**
     * 明细记录项目
     */
    private List<RcvblDetDTO> rcvblDet;

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getConsType() {
        return consType;
    }

    public void setConsType(String consType) {
        this.consType = consType;
    }

    public String getPrepayAmt() {
        return prepayAmt;
    }

    public void setPrepayAmt(String prepayAmt) {
        this.prepayAmt = prepayAmt;
    }

    public String getTotalOweAmt() {
        return totalOweAmt;
    }

    public void setTotalOweAmt(String totalOweAmt) {
        this.totalOweAmt = totalOweAmt;
    }

    public String getTotalRcvblAmt() {
        return totalRcvblAmt;
    }

    public void setTotalRcvblAmt(String totalRcvblAmt) {
        this.totalRcvblAmt = totalRcvblAmt;
    }

    public String getTotalRcvedAmt() {
        return totalRcvedAmt;
    }

    public void setTotalRcvedAmt(String totalRcvedAmt) {
        this.totalRcvedAmt = totalRcvedAmt;
    }

    public String getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(String totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    public List<RcvblDetDTO> getRcvblDet() {
        return rcvblDet;
    }

    public void setRcvblDet(List<RcvblDetDTO> rcvblDet) {
        this.rcvblDet = rcvblDet;
    }

    public static class RcvblDetDTO {
        /**
         * 销账（1已销，0欠费），只有欠费
         */
        private String rcvWr;
        /**
         * 月份（年-月）
         */
        private String rcvblYm;

        private String fn;

        private String elv;
        /**
         * 用水量
         */
        private String tWq;
        /**
         * 上月抄码
         */
        private String lAmt;
        /**
         * 本月抄码
         */
        private String tAmt;
        /**
         * 总费用
         */
        private String feeAmt;
        /**
         * 水费
         */
        private String feeWa;
        /**
         * 污水处理费
         */
        private String feeWe;
        /**
         * 垃圾处理费
         */
        private String feeTr;
        /**
         * 违约金
         */
        private String feePe;
        /**
         * 阶梯信息
         */
        private String ladderMsg;


        public String getRcvWr() {
            return rcvWr;
        }

        public void setRcvWr(String rcvWr) {
            this.rcvWr = rcvWr;
        }

        public String getRcvblYm() {
            return rcvblYm;
        }

        public void setRcvblYm(String rcvblYm) {
            this.rcvblYm = rcvblYm;
        }

        public String getFn() {
            return fn;
        }

        public void setFn(String fn) {
            this.fn = fn;
        }

        public String getElv() {
            return elv;
        }

        public void setElv(String elv) {
            this.elv = elv;
        }

        public String gettWq() {
            return tWq;
        }

        public void settWq(String tWq) {
            this.tWq = tWq;
        }

        public String getlAmt() {
            return lAmt;
        }

        public void setlAmt(String lAmt) {
            this.lAmt = lAmt;
        }

        public String gettAmt() {
            return tAmt;
        }

        public void settAmt(String tAmt) {
            this.tAmt = tAmt;
        }

        public String getFeeAmt() {
            return feeAmt;
        }

        public void setFeeAmt(String feeAmt) {
            this.feeAmt = feeAmt;
        }

        public String getFeeWa() {
            return feeWa;
        }

        public void setFeeWa(String feeWa) {
            this.feeWa = feeWa;
        }

        public String getFeeWe() {
            return feeWe;
        }

        public void setFeeWe(String feeWe) {
            this.feeWe = feeWe;
        }

        public String getFeeTr() {
            return feeTr;
        }

        public void setFeeTr(String feeTr) {
            this.feeTr = feeTr;
        }

        public String getFeePe() {
            return feePe;
        }

        public void setFeePe(String feePe) {
            this.feePe = feePe;
        }

        public String getLadderMsg() {
            return ladderMsg;
        }

        public void setLadderMsg(String ladderMsg) {
            this.ladderMsg = ladderMsg;
        }
    }
}
