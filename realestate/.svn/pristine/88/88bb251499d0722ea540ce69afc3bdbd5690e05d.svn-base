package cn.gtmap.realestate.common.core.service.rest.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description 不动产组合关系服务接口
 */
public interface BdcGzGxRestService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@return bdcGzGxDOList
     *@description 通过zhid获取组合关系列表
     */
    @GetMapping("/realestate-engine/rest/v1.0/gzgx/{zhid}")
    List<BdcGzGxDO> listBdcGzGxByZhid(@PathVariable("zhid") String zhid);

    /**
     * @param gzid
     * @return bdcGzGxDOList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 通过gzid获取所有组合关系
     */
    @GetMapping("/realestate-engine/rest/v1.0/gzgx/listBdcGzGx/{gzid}")
    List<BdcGzGxDO> listBdcGzGxByGzid(@PathVariable("gzid") String gzid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzGxDO
     *@return bdcGzGxDO
     *@description 新增不动产规则关系
     */
    @PostMapping("/realestate-engine/rest/v1.0/gzgx")
    BdcGzGxDO insertBdcGzGx(@RequestBody BdcGzGxDO bdcGzGxDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gxid
     *@return num
     *@description 通过规则关系主键gxid删除规则关系
     */
    @DeleteMapping("/realestate-engine/rest/v1.0/gzgx/{gxid}")
    void delBdcGzGxByGxid(@PathVariable("gxid") String gxid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@return num
     *@description 通过zhid删除规则关系
     */
    @DeleteMapping("/realestate-engine/rest/v1.0/gzgx/list/{zhid}")
    void delBdcGzGxByZhid(@PathVariable("zhid") String zhid);

    /**
     *@author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     *@param  gzid
     *@return num 删除的记录数
     *@description 通过gzid删除规则关系
     */
    @DeleteMapping("/realestate-engine/rest/v1.0/gzgx/delete/{gzid}")
    void delBdcGzGxByGzid(@PathVariable("gzid") String gzid);
}
