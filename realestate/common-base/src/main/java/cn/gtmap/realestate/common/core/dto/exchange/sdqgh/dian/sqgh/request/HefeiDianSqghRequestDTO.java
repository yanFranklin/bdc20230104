package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request;

import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.fjts.request.HefeiDianFjtsRequestDTO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-19
 * @description 合肥 电 申请过户接口请求参数实体
 */
public class HefeiDianSqghRequestDTO {

    /**
     * 过户信息
     */
    private HefeiDianSqghRequestData sqghData;

    /**
     * 附件List
     */
    private List<HefeiDianFjtsRequestDTO> fjList;

    private String slbh;

    private String xmid;

    public HefeiDianSqghRequestData getSqghData() {
        return sqghData;
    }

    public void setSqghData(HefeiDianSqghRequestData sqghData) {
        this.sqghData = sqghData;
    }

    public List<HefeiDianFjtsRequestDTO> getFjList() {
        return fjList;
    }

    public void setFjList(List<HefeiDianFjtsRequestDTO> fjList) {
        this.fjList = fjList;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }
}
