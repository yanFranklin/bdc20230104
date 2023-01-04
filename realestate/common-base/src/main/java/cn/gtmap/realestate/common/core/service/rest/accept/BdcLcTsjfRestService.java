package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcLcTsjfGxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 流程与推送缴费关系Rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-16 13:49
 **/
public interface BdcLcTsjfRestService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id查询推送缴费流程数据
     * @date : 2021/9/16 13:50
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/lctsjf/{gzlslid}")
    List<BdcLcTsjfGxDO> listLcTsjfGx(@PathVariable(value = "gzlslid") String gzlslid);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id删除推送缴费流程数据
     * @date : 2021/9/16 13:52
     */
    @DeleteMapping(value = "/realestate-accept/rest/v1.0/lctsjf/{gzlslid}")
    int deleteLcTsJfxx(@PathVariable(value = "gzlslid") String gzlslid);


    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量新增流程推送缴费关系
     * @date : 2021/9/16 14:05
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/lctsjf")
    int batchInsertLcTsjfGx(@RequestBody List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList);

    /**
     * @param sfxxidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收费信息id批量删除
     * @date : 2021/10/26 16:19
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/lctsjf/sfxxids/{gzlslid}")
    int deleteLcTsJfxxBySfxxid(@RequestBody List<String> sfxxidList, @PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcLcTsjfGxDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新流程推送缴费关系的信息
     * @date : 2021/11/3 14:31
     */
    @PutMapping(value = "/realestate-accept/rest/v1.0/lctsjf")
    void batchUpdateLcTsjfGx(@RequestBody List<BdcLcTsjfGxDO> bdcLcTsjfGxDOList);

    /**
     * 清除推送ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/lctsjf/clear/tsid/{gzlslid}")
    void clearTsid(@PathVariable(value = "gzlslid") String gzlslid);
}
