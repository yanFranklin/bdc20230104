package cn.gtmap.realestate.exchange.core.dto.hefei.ems.ddjr.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-16
 * @description
 */
public class EmsDdjrRequestAddress {

    // 用户详细地址
    private String address;

    // 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
    private String phone;

    // 用户所在市，使用国标全称
    private String city;

    // 用户姓名
    private String name;

    // 用户所在县（区），使用国标全称
    private String county;

    //用户所在省，使用国标全称
    private String prov;

    // 邮编
    private String postCode;

    // 地址ID，如果填入地址ID，其余字段通过2.1.3.6名址库维护的 “名址信息”获取，
    // 并且可以不做非空校验
    private String addressId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
