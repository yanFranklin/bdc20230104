package cn.gtmap.realestate.exchange.core.dto.hefei.xwqycx.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">huangjian</a>
 * @version 1.0  2020-10-19
 * @description 小微公司查询请求参数
 */
public class XwqyRequestDTO {

    // 公司名称
    @JSONField(name = "corporation")
    private String corporation;

    // 统一社会信用代码
    @JSONField(name = "tyshxydm")
    private String tyshxydm;


    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getTyshxydm() {
        return tyshxydm;
    }

    public void setTyshxydm(String tyshxydm) {
        this.tyshxydm = tyshxydm;
    }

    @Override
    public String toString() {
        return "XwqyRequestDTO{" +
                "corporation='" + corporation + '\'' +
                ", tyshxydm='" + tyshxydm + '\'' +
                '}';
    }
}
