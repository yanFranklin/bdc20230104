package cn.gtmap.realestate.exchange.core.dto.shucheng.sq;

import java.io.Serializable;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/5/9
 * @description
 */
public class ShuChengCommonDto<T> implements Serializable {
    private static final long serialVersionUID = 4672830925436964425L;
    /**
     * 	head是报文数据头信息，记录了一系列约定数据，
     */
    private ShuChengHeadDto head;
    /**
     * 	body是报文数据主体内容，承载了报文的业务数据，具体每个报文包含了哪些业务数据将在具体的业务接口中详细描述
     */
    private T body;

    public static ShuChengCommonDto buildHead(String servCode) {
        ShuChengCommonDto dto = new ShuChengCommonDto();
        ShuChengHeadDto headDTO = new ShuChengHeadDto();
        headDTO.setServCode(servCode);
        dto.setHead(headDTO);
        return dto;
    }



    public ShuChengHeadDto getHead() {
        return head;
    }

    public void setHead(ShuChengHeadDto head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ShuChengCommonDto{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
