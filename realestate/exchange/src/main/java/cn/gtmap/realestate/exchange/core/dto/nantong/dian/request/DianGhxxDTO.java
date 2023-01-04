package cn.gtmap.realestate.exchange.core.dto.nantong.dian.request;

import java.util.List;

public class DianGhxxDTO {


    /**
     * newUserName : 汪大鹏
     * qxno : ******
     * electricFeeNum : 3201114628714
     * oldMobile : 15052181011
     * bdcno : 320281031032GB00100F92542317
     * address : 江苏省****东区1号502室
     * newMobile : 15052181012
     * originalUserName : 汪鹏
     * originalUserCard : 320525198606245389
     * contractId : YK2020121245014521
     * newOriginalUserCard : 320525198606245390
     * data : [{"fileName":"","fileData":""}]
     */

    private String newUserName;
    private String qxno;
    private String electricFeeNum;
    private String oldMobile;
    private String bdcno;
    private String address;
    private String newMobile;
    private String originalUserName;
    private String originalUserCard;
    private String contractId;
    private String newOriginalUserCard;
    private String org_no;
    private List<DianGhFileDTO> data;

    public String getOrg_no() {
        return org_no;
    }

    public void setOrg_no(String org_no) {
        this.org_no = org_no;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getQxno() {
        return qxno;
    }

    public void setQxno(String qxno) {
        this.qxno = qxno;
    }

    public String getElectricFeeNum() {
        return electricFeeNum;
    }

    public void setElectricFeeNum(String electricFeeNum) {
        this.electricFeeNum = electricFeeNum;
    }

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public String getBdcno() {
        return bdcno;
    }

    public void setBdcno(String bdcno) {
        this.bdcno = bdcno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getOriginalUserName() {
        return originalUserName;
    }

    public void setOriginalUserName(String originalUserName) {
        this.originalUserName = originalUserName;
    }

    public String getOriginalUserCard() {
        return originalUserCard;
    }

    public void setOriginalUserCard(String originalUserCard) {
        this.originalUserCard = originalUserCard;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getNewOriginalUserCard() {
        return newOriginalUserCard;
    }

    public void setNewOriginalUserCard(String newOriginalUserCard) {
        this.newOriginalUserCard = newOriginalUserCard;
    }

    public List<DianGhFileDTO> getData() {
        return data;
    }

    public void setData(List<DianGhFileDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DianGhxxDTO{" +
                "newUserName='" + newUserName + '\'' +
                ", qxno='" + qxno + '\'' +
                ", electricFeeNum='" + electricFeeNum + '\'' +
                ", oldMobile='" + oldMobile + '\'' +
                ", bdcno='" + bdcno + '\'' +
                ", address='" + address + '\'' +
                ", newMobile='" + newMobile + '\'' +
                ", originalUserName='" + originalUserName + '\'' +
                ", originalUserCard='" + originalUserCard + '\'' +
                ", contractId='" + contractId + '\'' +
                ", newOriginalUserCard='" + newOriginalUserCard + '\'' +
                ", org_no='" + org_no + '\'' +
                ", data=" + data +
                '}';
    }
}
