package cn.gtmap.realestate.exchange.core.dto.changzhou.fssrdzh.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2020/12/16
 * @description 非税收入电子化-data数据集
 */
public class DataModel {

    /**
     * adm_div_code : 320400
     * bill_date : 20201029092830
     * agency_code : 005000
     * agency_name :
     * payname : 庄女士
     * payaccount :
     * paybank :
     * orderamount : 1000
     * bankcode : 105304000490
     * payeename : 常州市财政局
     * payeeaccount : 32001629101052500075
     * payeebank : 建行行政中心支行
     * tel : 13685292888
     * email :
     * einvoicecodecode : 320101
     * user_login_name : admin
     * replace_agency_code :
     * pay_type : 1
     * pay_agency_code :
     * iden_no :
     * rem_type : 0
     * pay_way :
     * business_id : 100
     * node : 测试
     * hold1 :
     * hold2 :
     * hold3 :
     * hold4 :
     */

    //行政区划
    private String adm_div_code;
    //填制日期，格式为yyyyMMddHHmmss
    private String bill_date;
    //执收单位编码（需要和非税收入票据电子化系统一致）传默认值：132000
    private String agency_code;
    //执收单位名称（需要和非税收入票据电子化系统一致）传默认值：常州市不动产登记交易中心
    private String agency_name;
    //缴（付）款人全称
    private String payname;
    //缴（付）款人账号
    private String payaccount;
    //缴（付）款人开户行
    private String paybank;
    //缴款单金额
//    private Double orderamount;
    private String orderamount;
    //行网点编码（需要和非税收入票据电子化系统一致）传默认值：105304000490
    private String bankcode;
    //收款人全称传默认值：常州市财政局
    private String payeename;
    //收款人账号传默认值：32001629101052500075
    private String payeeaccount;
    //收款人开户行传默认值：046建行行政中心支行
    private String payeebank;
    //手机号
    private String tel;
    //电子邮箱
    private String email;
    //财政票据代码（需要和非税收入票据电子化系统一致）传默认值：320101
    private String einvoicecodecode;
    //当前登录用户名（需要和非税收入票据电子化系统一致）
    private String user_login_name;
    //代编单位代码（需要和非税收入票据电子化系统一致）
    private String replace_agency_code;
    //缴款类型(1-个人缴费，2-单位往来)传默认值：1
//    private Integer pay_type;
    private String pay_type;
    //付款单位代码（需要和非税收入票据电子化系统一致）,pay_type为2时必填
    private String pay_agency_code;
    //身份证号码
    private String iden_no;
    //汇缴标志(1、汇缴明细；2、汇总缴费单；0：缴费通知单)传默认值：0
//    private Integer rem_type;
    private String rem_type;
    //缴费方式(0-现金；1--POS)，rem_type为1时必填
//    private Integer pay_way;
    private String pay_way;
    //业务唯一码(唯一性要素)
    private String business_id;
    //备注
    private String node;
    //预留字段
    private String hold1;
    //预留字段
    private String hold2;
    //预留字段
    private String hold3;
    //预留字段
//    private Date hold4;
    private String hold4;
    //节点集合
    private ArrayList<FssrDetails> details;

    public ArrayList<FssrDetails> getDetails() {
        return details;
    }

    public String getAdm_div_code() {
        return adm_div_code;
    }

    public void setAdm_div_code(String adm_div_code) {
        this.adm_div_code = adm_div_code;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getAgency_code() {
        return agency_code;
    }

    public void setAgency_code(String agency_code) {
        this.agency_code = agency_code;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getPayname() {
        return payname;
    }

    public void setPayname(String payname) {
        this.payname = payname;
    }

    public String getPayaccount() {
        return payaccount;
    }

    public void setPayaccount(String payaccount) {
        this.payaccount = payaccount;
    }

    public String getPaybank() {
        return paybank;
    }

    public void setPaybank(String paybank) {
        this.paybank = paybank;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getPayeename() {
        return payeename;
    }

    public void setPayeename(String payeename) {
        this.payeename = payeename;
    }

    public String getPayeeaccount() {
        return payeeaccount;
    }

    public void setPayeeaccount(String payeeaccount) {
        this.payeeaccount = payeeaccount;
    }

    public String getPayeebank() {
        return payeebank;
    }

    public void setPayeebank(String payeebank) {
        this.payeebank = payeebank;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEinvoicecodecode() {
        return einvoicecodecode;
    }

    public void setEinvoicecodecode(String einvoicecodecode) {
        this.einvoicecodecode = einvoicecodecode;
    }

    public String getUser_login_name() {
        return user_login_name;
    }

    public void setUser_login_name(String user_login_name) {
        this.user_login_name = user_login_name;
    }

    public String getReplace_agency_code() {
        return replace_agency_code;
    }

    public void setReplace_agency_code(String replace_agency_code) {
        this.replace_agency_code = replace_agency_code;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_agency_code() {
        return pay_agency_code;
    }

    public void setPay_agency_code(String pay_agency_code) {
        this.pay_agency_code = pay_agency_code;
    }

    public String getIden_no() {
        return iden_no;
    }

    public void setIden_no(String iden_no) {
        this.iden_no = iden_no;
    }

    public String getRem_type() {
        return rem_type;
    }

    public void setRem_type(String rem_type) {
        this.rem_type = rem_type;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
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

    public String getHold3() {
        return hold3;
    }

    public void setHold3(String hold3) {
        this.hold3 = hold3;
    }

    public String getHold4() {
        return hold4;
    }

    public void setHold4(String hold4) {
        this.hold4 = hold4;
    }

    public void setDetails(ArrayList<FssrDetails> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "{" +
                "adm_div_code='" + adm_div_code + '\'' +
                ", bill_date='" + bill_date + '\'' +
                ", agency_code='" + agency_code + '\'' +
                ", agency_name='" + agency_name + '\'' +
                ", payname='" + payname + '\'' +
                ", payaccount='" + payaccount + '\'' +
                ", paybank='" + paybank + '\'' +
                ", orderamount='" + orderamount + '\'' +
                ", bankcode='" + bankcode + '\'' +
                ", payeename='" + payeename + '\'' +
                ", payeeaccount='" + payeeaccount + '\'' +
                ", payeebank='" + payeebank + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", einvoicecodecode='" + einvoicecodecode + '\'' +
                ", user_login_name='" + user_login_name + '\'' +
                ", replace_agency_code='" + replace_agency_code + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", pay_agency_code='" + pay_agency_code + '\'' +
                ", iden_no='" + iden_no + '\'' +
                ", rem_type='" + rem_type + '\'' +
                ", pay_way='" + pay_way + '\'' +
                ", business_id='" + business_id + '\'' +
                ", node='" + node + '\'' +
                ", hold1='" + hold1 + '\'' +
                ", hold2='" + hold2 + '\'' +
                ", hold3='" + hold3 + '\'' +
                ", hold4='" + hold4 + '\'' +
                ", details=" + details +
                '}';
    }
}

