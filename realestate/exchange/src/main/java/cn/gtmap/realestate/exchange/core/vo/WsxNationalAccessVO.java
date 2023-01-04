package cn.gtmap.realestate.exchange.core.vo;

import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-25
 * @description
 */
public class WsxNationalAccessVO {

    /**
     * 区县代码
     */
    private String qxdm;

    /**
     * 请求列表
     */
    private List<EtlNationalAccessRequestDTO> requestList;

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public List<EtlNationalAccessRequestDTO> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<EtlNationalAccessRequestDTO> requestList) {
        this.requestList = requestList;
    }
}
