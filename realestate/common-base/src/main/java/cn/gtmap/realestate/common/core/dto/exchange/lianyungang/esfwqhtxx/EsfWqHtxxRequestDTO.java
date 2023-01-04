package cn.gtmap.realestate.common.core.dto.exchange.lianyungang.esfwqhtxx;

import org.apache.poi.ss.formula.functions.T;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/12/16/15:51
 * @Description: 连云港二手房网签合同信息RequestDTO
 */
public class EsfWqHtxxRequestDTO {

    private EsfWqHtxxRequestHead head;

    private EsfWqHtxxRequestBody data;


    public EsfWqHtxxRequestHead getHead() {
        return head;
    }

    public void setHead(EsfWqHtxxRequestHead head) {
        this.head = head;
    }

    public EsfWqHtxxRequestBody getData() {
        return data;
    }

    public void setData(EsfWqHtxxRequestBody data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EsfWqHtxxRequestDTO{" +
                "head=" + head +
                ", data=" + data +
                '}';
    }
}
