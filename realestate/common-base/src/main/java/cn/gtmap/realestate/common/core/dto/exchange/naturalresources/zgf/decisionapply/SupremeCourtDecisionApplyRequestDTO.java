package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.zgf.decisionapply;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 16:02
 * @description 2.7最高法-司法判决信息查询申请接口
 */
@XmlRootElement(name = "datas")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupremeCourtDecisionApplyRequestDTO {

    @XmlTransient
    private String slbh;

    @XmlElement(name = "data")
    private List<SupremeCourtDecisionApplyParamDTO> paramDTOList;

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

    public List<SupremeCourtDecisionApplyParamDTO> getParamDTOList() {
        return paramDTOList;
    }

    public void setParamDTOList(List<SupremeCourtDecisionApplyParamDTO> paramDTOList) {
        this.paramDTOList = paramDTOList;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SupremeCourtDecisionApplyRequestDTO{");
        sb.append("slbh='").append(slbh).append('\'');
        sb.append(", paramDTOList=").append(paramDTOList);
        sb.append(", loadpage=").append(loadpage);
        sb.append('}');
        return sb.toString();
    }
}
