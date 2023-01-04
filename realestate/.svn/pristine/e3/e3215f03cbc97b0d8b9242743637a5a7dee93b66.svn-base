package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.request;

import cn.gtmap.realestate.common.core.dto.accept.FileDTO;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/12
 * @description 一体化推送邮寄信息实体
 */
public class YthYjxxRequestDTO {

    /**
     * ywbh : 2022062100021
     * waybillNo : 1295553985499
     * fjxx : [{"fileName":"pdf面单","fileUrl":"2022/06/17/2022061700014/1655707139557301.PDF"},{"fileName":"pdf面单2","fileUrl":"2022/06/17/2022061700014/16557071395573021.PDF"}]
     */
    /**
     * 一体化业务编号
     */
    private String ywbh;
    /**
     * Ems订单号
     */
    private String waybillNo;
    /**
     * EMS收件人地址(省市区县)
     */
    private String receiverAddr;
    /**
     * 附件信息
     */
    private List<FileDTO> fjxx;

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public List<FileDTO> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FileDTO> fjxx) {
        this.fjxx = fjxx;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    @Override
    public String toString() {
        return "YthYjxxRequestDTO{" +
                "ywbh='" + ywbh + '\'' +
                ", waybillNo='" + waybillNo + '\'' +
                ", receiverAddr='" + receiverAddr + '\'' +
                ", fjxx=" + fjxx +
                '}';
    }
}
