package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxx.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-09
 * @description 省级平台 婚姻信息 查询反馈
 */
public class XgbmHyxxResponseDTO {

    @JSONField(name = "head")
    private XgbmHyxxResponseHead head;

    @JSONField(name = "data")
    private XgbmHyxxResponseData data;

    public XgbmHyxxResponseHead getHead() {
        return head;
    }

    public void setHead(XgbmHyxxResponseHead head) {
        this.head = head;
    }

    public XgbmHyxxResponseData getData() {
        return data;
    }

    public void setData(XgbmHyxxResponseData data) {
        this.data = data;
    }
}
