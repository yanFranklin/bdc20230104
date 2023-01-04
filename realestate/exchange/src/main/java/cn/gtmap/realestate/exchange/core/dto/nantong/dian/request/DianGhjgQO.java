package cn.gtmap.realestate.exchange.core.dto.nantong.dian.request;

public class DianGhjgQO {

    /**
     * {
     * "contractId":"3201000210016",
     * "org_no":"3240101"
     * }
     */
    private String contractId;
    private String org_no;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOrg_no() {
        return org_no;
    }

    public void setOrg_no(String org_no) {
        this.org_no = org_no;
    }

    @Override
    public String toString() {
        return "DianGhjgQO{" +
                "contractId='" + contractId + '\'' +
                ", org_no='" + org_no + '\'' +
                '}';
    }
}
