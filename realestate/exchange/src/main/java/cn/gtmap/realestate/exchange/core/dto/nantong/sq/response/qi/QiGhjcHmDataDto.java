package cn.gtmap.realestate.exchange.core.dto.nantong.sq.response.qi;

import java.io.Serializable;
/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //南通大市，过户检查返回参数
 * @Date 2022/5/26 9:34
 **/
public class QiGhjcHmDataDto implements Serializable {

    private static final long serialVersionUID = -1073440568645929813L;
    private String respCode;

    private String respDesc;

    private String customerName;

    private String address;

    private String idType;

    private String idTypeDesc;

    private String idNmb;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdTypeDesc() {
        return idTypeDesc;
    }

    public void setIdTypeDesc(String idTypeDesc) {
        this.idTypeDesc = idTypeDesc;
    }

    public String getIdNmb() {
        return idNmb;
    }

    public void setIdNmb(String idNmb) {
        this.idNmb = idNmb;
    }

    @Override
    public String toString() {
        return "QiGhjcHmDataDto{" +
                "respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", idType='" + idType + '\'' +
                ", idTypeDesc='" + idTypeDesc + '\'' +
                ", idNmb='" + idNmb + '\'' +
                '}';
    }
}
