package cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/12/7
 * @description 增量房合同信息-承购方
 */
@ApiModel(value = "ZlfHtxxBuyerList", description = "增量房合同信息-承购方")
public class HtxxBuyerDTO {

    /**
     * buyerName : 唐XX
     * idType : 身份证
     * buyerNo : 32062419740529XXXX
     * national : 中国
     * vendorTel : 13862800228
     * address : 江苏省南通市通州区中瑾世纪城XXXX
     * postalCode : 226300
     * agent :
     */

    private String buyerName;
    private String idType;
    private String buyerNo;
    private String national;
    private String vendorTel;
    private String address;
    private String postalCode;
    private List<AgentDTO> agent;

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getBuyerNo() {
        return buyerNo;
    }

    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getVendorTel() {
        return vendorTel;
    }

    public void setVendorTel(String vendorTel) {
        this.vendorTel = vendorTel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<AgentDTO> getAgent() {
        return agent;
    }

    public void setAgent(List<AgentDTO> agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "HtxxBuyerDTO{" +
                "buyerName='" + buyerName + '\'' +
                ", idType='" + idType + '\'' +
                ", buyerNo='" + buyerNo + '\'' +
                ", national='" + national + '\'' +
                ", vendorTel='" + vendorTel + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", agent=" + agent +
                '}';
    }
}
