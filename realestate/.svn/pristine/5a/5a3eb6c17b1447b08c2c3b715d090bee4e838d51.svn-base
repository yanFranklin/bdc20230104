package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/28
 * @description 不动产已选不动产单元开关配置 Rest服务
 */
public interface BdcYxbdcdyKgPzRestService {

    /**
     * @param gzldyid 工作流定义id
     * @return  不动产已选不动产单元开关配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   根据工作流定义id获取已选不动产单元开关配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/yxbdcdykgpz")
    BdcYxbdcdyKgPzDO queryBdcYxbdcdyKgPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid);

    /**
     * @param gzldyid 工作流定义id
     * @return  不动产已选不动产单元开关配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   根据工作流定义id获取已选不动产单元开关配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/yxbdcdykgpz/list")
    List<BdcYxbdcdyKgPzDO> queryBdcYxbdcdyKgPzDOListByGzldyid(@RequestParam(value = "gzldyid") String gzldyid);


    /**
     * @param bdcYxbdcdyKgPzDO 已选不动产单元开关配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存已选不动产单元开关配置
     */
    @PutMapping("/realestate-accept/rest/v1.0/yxbdcdykgpz")
    int saveBdcYxbdcdyKgPzDO(@RequestBody BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO);

    /**
     * @param gzldyid 工作流定义ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID删除不动产单元开关配置
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/yxbdcdykgpz")
    int deleteBdcYxbdcdyKgPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid);


}
