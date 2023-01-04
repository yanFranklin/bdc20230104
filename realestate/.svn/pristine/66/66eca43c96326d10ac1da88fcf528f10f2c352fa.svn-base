package cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm.ems;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/02  15:59
 * @description 淮安 中国邮政 寄件人
 */
@XmlType(propOrder = {"name", "post_code", "phone", "mobile", "prov", "city", "county","address"})
@XmlRootElement(name = "sender")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Sender {

   /* <sender>
        <name>陆安波</name>
        <post_code>510000</post_code>
        <phone>020-86210730</phone>
        <mobile>020-86210730</mobile>
        <prov>北京市</prov>
        <city>北京市</city>
        <county>西城区</county>
        <address>永安路173号</address>
    </sender>*/

    // 用户姓名
    private String name;

    // 用户邮编
    private String post_code;

    // 用户电话，包括区号、电话号码及分机号，中间用“-”分隔
    private String phone;

    // 用户移动电话
    private String mobile;

    // 用户所在省，使用国标全称
    private String prov;

    // 用户所在市，使用国标全称
    private String city;

    // 用户所在县（区），使用国标全称
    private String county;

    // 用户详细地址
    private String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "name='" + name + '\'' +
                ", post_code='" + post_code + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", prov='" + prov + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
