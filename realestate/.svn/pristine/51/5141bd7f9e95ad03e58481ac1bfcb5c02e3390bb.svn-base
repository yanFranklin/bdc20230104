package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcTddysfDyhDO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhShDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 不动产项目土地抵押释放关系服务
 */
public interface BdcTddysfDyhRestService {

    /**
     * @param gzlslid 工作流实例ID
     * @return 单元号集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取土地抵押释放单元号集合
     */
    @GetMapping("/realestate-register/rest/v1.0/tddysf/{gzlslid}")
    List<BdcTddysfDyhDO> listTddysfBdcdyhByGzlslid(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcTddysfDyhDTO 土地抵押释放单元号信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成土地抵押释放单元号信息
     */
    @PostMapping("/realestate-register/rest/v1.0/tddysf")
    void initBdcTddysfDyh(@RequestBody BdcTddysfDyhDTO bdcTddysfDyhDTO);

    /**
     * @param bdcTddysfDyhDTO 土地抵押释放单元号信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除土地抵押释放单元号信息
     */
    @DeleteMapping("/realestate-register/rest/v1.0/tddysf")
    void deleteBdcTddysfDyh(@RequestBody BdcTddysfDyhDTO bdcTddysfDyhDTO);

    /**
     * @param bdcTddysfDyhShDTO 审核信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新土地抵押释放审核信息
     */
    @PutMapping("/realestate-register/rest/v1.0/tddysf/sh")
    void updateBdcTddysfDyhShxx(@RequestBody BdcTddysfDyhShDTO bdcTddysfDyhShDTO);


}
