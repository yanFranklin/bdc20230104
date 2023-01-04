package cn.gtmap.realestate.exchange.core.dto.changzhou.fssrdzh.jfcx.request;

public class JfcxDataModel {
    /**
     * business_id : 100
     * businessnumber : 32040020000000002527
     * hold1 :
     * hold2 :
     */

    private String business_id;
    private String businessnumber;
    private String hold1;
    private String hold2;

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getBusinessnumber() {
        return businessnumber;
    }

    public void setBusinessnumber(String businessnumber) {
        this.businessnumber = businessnumber;
    }

    public String getHold1() {
        return hold1;
    }

    public void setHold1(String hold1) {
        this.hold1 = hold1;
    }

    public String getHold2() {
        return hold2;
    }

    public void setHold2(String hold2) {
        this.hold2 = hold2;
    }

    @Override
    public String toString() {
        return "{" +
                "business_id='" + business_id + '\'' +
                ", businessnumber='" + businessnumber + '\'' +
                ", hold1='" + hold1 + '\'' +
                ", hold2='" + hold2 + '\'' +
                '}';
    }
}



