package cn.gtmap.realestate.common.core.service.rest.init;


import cn.gtmap.realestate.common.core.domain.BdcHmdDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021/10/25
 * @description 不动产黑名单Rest服务
 */
public interface BdcHmdRestService {

    /**
     * 更新不动产黑名单
     * @param bdcHmdDO 不动产黑名单DO
     * @return 更新数据的数量
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(path = "/init/rest/v1.0/bdchmd")
    void updateBdcHmd(@RequestBody BdcHmdDO bdcHmdDO);

    /**
     * 保存不动产黑名单
     * @param bdcHmdDO 不动产黑名单DO
     * @return 更新数据的数量
     * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/init/rest/v1.0/bdchmd/save")
    BdcHmdDO saveBdcHmd(@RequestBody BdcHmdDO bdcHmdDO);

    /**
     * 解锁黑名单
     * <p>大云调用接口，用于业务审核通过后，自动解锁原项目的不动产权证书的不良记录</p>
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(path = "/init/rest/v1.0/bdchmd/bljl/js")
    void jsBljl(@RequestParam(name="processInsId") String processInsId);

    /**
     * @param hmdIdList 黑名单ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量删除黑名单信息
     */
    @PostMapping(path = "/init/rest/v1.0/bdchmd/plsc")
    void batchDeleteBdcHmd(@RequestBody List<String> hmdIdList);

    /**
     * 查询不动产黑名单信息
     * @param bdcHmdDO 不动产黑名单DO
     * @return 黑名单信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/init/rest/v1.0/bdchmd/list")
    List<BdcHmdDO> queryBdcHmd(@RequestBody BdcHmdDO bdcHmdDO);

    /**
     * 解锁黑名单
     * <p>解锁不动产权证书的不良记录</p>
     * @param bdcHmdDOList 不动产黑名单DO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(path = "/init/rest/v1.0/bdchmd/jsBljlxx")
    void jsBljlxx(@RequestBody List<BdcHmdDO> bdcHmdDOList);

}
