package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionresponse;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 16:13
 * @description 2.8最高法-司法判决信息结果反馈接口
 */
public class SupremeCourtDecisionResponseRequestDTO {

    private String slbh;

    private List<String> cxqqdhList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否分页
     */
    private boolean loadpage;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<String> getCxqqdhList() {
        return cxqqdhList;
    }

    public void setCxqqdhList(List<String> cxqqdhList) {
        this.cxqqdhList = cxqqdhList;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SupremeCourtDecisionResponseRequestDTO{");
        sb.append("slbh='").append(slbh).append('\'');
        sb.append(", cxqqdhList=").append(cxqqdhList);
        sb.append(", loadpage=").append(loadpage);
        sb.append('}');
        return sb.toString();
    }
}
