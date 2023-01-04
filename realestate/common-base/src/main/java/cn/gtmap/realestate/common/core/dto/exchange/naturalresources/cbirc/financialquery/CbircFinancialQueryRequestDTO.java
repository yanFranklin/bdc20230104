package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 10:50
 * @description 2.5银保监会-金融许可证查询接口
 */
public class CbircFinancialQueryRequestDTO {

    private String slbh;

    private List<CbircFinancialQueryParamDTO> paramDTOS;

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

    public List<CbircFinancialQueryParamDTO> getParamDTOS() {
        return paramDTOS;
    }

    public void setParamDTOS(List<CbircFinancialQueryParamDTO> paramDTOS) {
        this.paramDTOS = paramDTOS;
    }

    public boolean isLoadpage() {
        return loadpage;
    }

    public void setLoadpage(boolean loadpage) {
        this.loadpage = loadpage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CbircFinancialQueryRequestDTO{");
        sb.append("slbh='").append(slbh).append('\'');
        sb.append(", paramDTOS=").append(paramDTOS);
        sb.append(", loadpage=").append(loadpage);
        sb.append('}');
        return sb.toString();
    }
}
