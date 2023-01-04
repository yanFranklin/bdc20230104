package cn.gtmap.realestate.exchange.core.dto.changzhou.fssrdzh.request;

import java.util.List;

public class FssrDzhData {

    /**
     * token : cztoken
     * method : bus.order.sync
     * timestamp : 20200701121200
     * version : 1.0
     * datakey : 69e9c4b8c0c03b821284ef2683df2b5c
     * data : [{"adm_div_code":"320400","bill_date":"20201029092830","agency_code":"005000","agency_name":"","payname":"庄女士","payaccount":"","paybank":"","orderamount":"1000","bankcode":"105304000490","payeename":"常州市财政局","payeeaccount":"32001629101052500075","payeebank":"建行行政中心支行","tel":"13685292888","email":"","einvoicecodecode":"320101","user_login_name":"admin","replace_agency_code":"","pay_type":"1","pay_agency_code":"","iden_no":"","rem_type":"0","pay_way":"","business_id":"100","node":"测试","hold1":"","hold2":"","hold3":"","hold4":""}]
     */

    private String token;
    private String method;
    private String timestamp;
    private String version;
    private String datakey;
    private List<DataModel> data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDatakey() {
        return datakey;
    }

    public void setDatakey(String datakey) {
        this.datakey = datakey;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "token='" + token + '\'' +
                ", method='" + method + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", version='" + version + '\'' +
                ", datakey='" + datakey + '\'' +
                ", data=" + data +
                '}';
    }

}
