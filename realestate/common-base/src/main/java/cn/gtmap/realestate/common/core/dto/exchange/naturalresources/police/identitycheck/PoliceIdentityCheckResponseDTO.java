package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 15:49
 * @description
 */
public class PoliceIdentityCheckResponseDTO {

    private List<PoliceIdentityCheckReturnDTO> returnInfos;

    public List<PoliceIdentityCheckReturnDTO> getReturnInfos() {
        return returnInfos;
    }

    public void setReturnInfos(List<PoliceIdentityCheckReturnDTO> returnInfos) {
        this.returnInfos = returnInfos;
    }

    @Override
    public String toString() {
        return "PoliceIdentityCheckResponseDTO{" +
                "returnInfos=" + returnInfos +
                '}';
    }
}
