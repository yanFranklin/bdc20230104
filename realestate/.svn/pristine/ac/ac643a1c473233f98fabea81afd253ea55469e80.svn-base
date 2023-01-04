package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXztzPzDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description 不动产受理选择台账配置 Rest服务
 */
public interface BdcSlXztzPzRestService {
    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/xztzpz")
    BdcSlXztzPzDO queryBdcSlXztzPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid);

    /**
     * @param bdcSlXztzPzDO 不动产受理选择台账配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理选择台账配置
     */
    @PutMapping("/realestate-accept/rest/v1.0/xztzpz")
    int saveBdcSlXztzPzDO(@RequestBody BdcSlXztzPzDO bdcSlXztzPzDO);

    /**
     * @param gzldyid 工作流定义ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID删除受理选择台账配置
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/xztzpz")
    int deleteBdcSlXztzPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid);

    /**
      * @param gzldyid 工作流定义ID
      * @return BdcSlXztzPzDTO
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据工作流定义ID获取不动产受理选择台账配置
      */
    @GetMapping("/realestate-accept/rest/v1.0/xztzpz/dto")
    BdcSlXztzPzDTO queryBdcSlXztzPzDTOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid);


}
