package cn.gtmap.realestate.exchange.core.dto.hefei.gs.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-07
 * @description 企业查询请求实体
 */
public class GsQyxxcxRequestBody {

    // 企业名称
    @JSONField(name = "ENTNAME")
    private String ENTNAME;

    // 信用代码
    @JSONField(name = "UNISCID")
    private String UNISCID;

    public String getENTNAME() {
        return ENTNAME;
    }

    public void setENTNAME(String ENTNAME) {
        this.ENTNAME = ENTNAME;
    }

    public String getUNISCID() {
        return UNISCID;
    }

    public void setUNISCID(String UNISCID) {
        this.UNISCID = UNISCID;
    }
}
