package cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description
 */
public class FwqlCxResponseData {
    private List<FwqlCxResponseQl> qllist;

    @JSONField(name = "YWBH")
    private String YWBH;

    @JSONField(name = "IsSuccessful")
    private String IsSuccessful;

    public List<FwqlCxResponseQl> getQllist() {
        return qllist;
    }

    public void setQllist(List<FwqlCxResponseQl> qllist) {
        this.qllist = qllist;
    }

    public String getYWBH() {
        return YWBH;
    }

    public void setYWBH(String YWBH) {
        this.YWBH = YWBH;
    }

    public String getIsSuccessful() {
        return IsSuccessful;
    }

    public void setIsSuccessful(String isSuccessful) {
        IsSuccessful = isSuccessful;
    }
}
