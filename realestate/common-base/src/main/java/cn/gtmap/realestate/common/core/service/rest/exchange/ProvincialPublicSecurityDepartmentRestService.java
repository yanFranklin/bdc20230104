package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.provincialpublicsecuritydepartment.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/17 09:58
 */
public interface ProvincialPublicSecurityDepartmentRestService{

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return PoliceRealNameRequestDTO
     * @description 4.1省公安厅-公民基本信息在线比对接口[已实现]
     */
    @PostMapping("/realestate-exchange/rest/v1.0/provincialPublicSecurityDepartment/policeRealName")
    PoliceRealNameResponseDTO policeRealName(@RequestBody PoliceRealNameRequestDTO info);

    /**
     * @param info
     * @return PoliceRealNameRequestDTO
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 4.3省公安厅-居民户成员信息在线查询接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/provincialPublicSecurityDepartment/policeHouseholdMembers")
    PoliceHouseholdMembersResponseDTO policeHouseholdMembers(@RequestBody PoliceHouseholdMembersRequestDTO info);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 4.2 人像对比
     * @date : 2022/10/10 16:46
     */
    @PostMapping("/realestate-exchange/rest/v1.0/provincialPublicSecurityDepartment/rxdb")
    RxbdResponseDTO gmrxdb(@RequestBody RxbdRequestDTO rxbdRequestDTO);

}
