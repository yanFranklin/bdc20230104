package cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/12/7
 * @description 增量房合同信息-出售方
 */
@ApiModel(value = "ZlfHtxxVendorList", description = "增量房合同信息-出售方")
public class HtxxVendorDTO {

    /**
     * vendorName : 南通神辉置业有限公司
     * idType : 统一社会信用代码
     * vendorNo : 913206125691883949
     * national : 中国
     * vendorTel : 0513-80662888
     * address : 通州区恒隆大厦13楼
     * postalCode : 226300
     * agent :
     */

    private String vendorName;
    private String idType;
    private String vendorNo;
    private String national;
    private String vendorTel;
    private String address;
    private String postalCode;
    private List<AgentDTO> agent;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(String vendorNo) {
        this.vendorNo = vendorNo;
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
        return "HtxxVendorDTO{" +
                "vendorName='" + vendorName + '\'' +
                ", idType='" + idType + '\'' +
                ", vendorNo='" + vendorNo + '\'' +
                ", national='" + national + '\'' +
                ", vendorTel='" + vendorTel + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", agent=" + agent +
                '}';
    }
}
