package cn.gtmap.realestate.exchange.core.dto.nantong.dian.request;

public class DianHxxQO {

    /**
     * flag : 1
     * electricFeeNum : 3201000836333
     * org_no : 3240101
     */

    private String flag;
    private String electricFeeNum;
    private String org_no;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getElectricFeeNum() {
        return electricFeeNum;
    }

    public void setElectricFeeNum(String electricFeeNum) {
        this.electricFeeNum = electricFeeNum;
    }

    public String getOrg_no() {
        return org_no;
    }

    public void setOrg_no(String org_no) {
        this.org_no = org_no;
    }

    @Override
    public String toString() {
        return "DianHxxQO{" +
                "flag='" + flag + '\'' +
                ", electricFeeNum='" + electricFeeNum + '\'' +
                ", org_no='" + org_no + '\'' +
                '}';
    }
}
