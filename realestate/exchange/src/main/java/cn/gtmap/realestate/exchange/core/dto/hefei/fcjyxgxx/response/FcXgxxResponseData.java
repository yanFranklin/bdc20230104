package cn.gtmap.realestate.exchange.core.dto.hefei.fcjyxgxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-30
 * @description 限购验证的 响应数据
 */
public class FcXgxxResponseData {

    // 限购查询编号
    private String LimitBuyNo;

    // 限购查询申请机构
    private String ApplyOrg;

    // 是否可以购买 （是、否）
    private String IsCanBuy;

    // 是否已经购买 （是、否）
    private String IsAlreadyBuy;

    private List<FcXgxxResponseProposer> Proposers;

    public String getLimitBuyNo() {
        return LimitBuyNo;
    }

    public void setLimitBuyNo(String limitBuyNo) {
        LimitBuyNo = limitBuyNo;
    }

    public String getApplyOrg() {
        return ApplyOrg;
    }

    public void setApplyOrg(String applyOrg) {
        ApplyOrg = applyOrg;
    }

    public String getIsCanBuy() {
        return IsCanBuy;
    }

    public void setIsCanBuy(String isCanBuy) {
        IsCanBuy = isCanBuy;
    }

    public String getIsAlreadyBuy() {
        return IsAlreadyBuy;
    }

    public void setIsAlreadyBuy(String isAlreadyBuy) {
        IsAlreadyBuy = isAlreadyBuy;
    }

    public List<FcXgxxResponseProposer> getProposers() {
        return Proposers;
    }

    public void setProposers(List<FcXgxxResponseProposer> proposers) {
        Proposers = proposers;
    }
}
