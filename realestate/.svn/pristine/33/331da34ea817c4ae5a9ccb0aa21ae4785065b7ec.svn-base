package cn.gtmap.realestate.common.core.dto.exchange.ykq.ddztv2.request;

import cn.gtmap.realestate.common.core.dto.exchange.RequestHead;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/29
 * @description 一卡清查询订单信息
 */
@ApiModel(value = "YkqDdztRequestDTO", description = "一卡清查询订单信息")
public class YkqDdztRequestDTO {

    private RequestHead head;

    private List<YkqDdztDataDTO> data;

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public List<YkqDdztDataDTO> getData() {
        return data;
    }

    public void setData(List<YkqDdztDataDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "YkqDdztRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
