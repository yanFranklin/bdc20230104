package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 09:39
 * @description 2.3民政部-婚姻登记信息查询服务接口
 */
public class CivilMarriageQueryRequestDTO {

    private String slbh;

    private List<CivilMarriageQueryParamDTO> paramDTOList;

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

    public List<CivilMarriageQueryParamDTO> getParamDTOList() {
        return paramDTOList;
    }

    public void setParamDTOList(List<CivilMarriageQueryParamDTO> paramDTOList) {
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
        final StringBuilder sb = new StringBuilder("CivilMarriageQueryRequestDTO{");
        sb.append("slbh='").append(slbh).append('\'');
        sb.append(", paramDTOList=").append(paramDTOList);
        sb.append(", loadpage=").append(loadpage);
        sb.append('}');
        return sb.toString();
    }
}
