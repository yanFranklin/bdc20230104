package cn.gtmap.realestate.exchange.core.dto.zrpay.dzwj;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022/08/10
 * @description 政融支付平台 对账文件推送入参
 */
public class VerifyFileNoticeQo {

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
     * 对账日期
     */
    @JSONField(name = "Rcncl_Dt")
    private String rcnclDt;

    /**
     * 文件名
     */
    @JSONField(name = "File_Nm")
    private String fileNm;

    /**
     * 文件类型
     * 1-对账单
     * 2-交易明细
     * 默认为1
     */
    @JSONField(name = "File_Type")
    private String fileType;

    /**
     * 文件的MD5
     */
    @JSONField(name = "MD5")
    private String md5;

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

    public String getRcnclDt() {
        return rcnclDt;
    }

    public void setRcnclDt(String rcnclDt) {
        this.rcnclDt = rcnclDt;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "VerifyFileNoticeQo{" +
                "VNo='" + VNo + '\'' +
                ", sgnAlgr='" + sgnAlgr + '\'' +
                ", rcnclDt='" + rcnclDt + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", fileType='" + fileType + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
