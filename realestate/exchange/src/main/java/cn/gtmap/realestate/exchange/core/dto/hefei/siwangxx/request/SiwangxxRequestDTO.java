package cn.gtmap.realestate.exchange.core.dto.hefei.siwangxx.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-04
 * @description 死亡信息 请求实体
 */
public class SiwangxxRequestDTO {

    // 身份证号码
    @JSONField(name = "SFZH")
    private String SFZH;

    // 姓名
    @JSONField(name = "XM")
    private String XM;

    public String getSFZH() {
        return SFZH;
    }

    public void setSFZH(String SFZH) {
        this.SFZH = SFZH;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }
}
