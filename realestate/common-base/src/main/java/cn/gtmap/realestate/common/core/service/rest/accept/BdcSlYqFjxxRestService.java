package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYqFjxxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @description 不动产受理云签附件信息对外REST接口
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/10/28.
 */
public interface BdcSlYqFjxxRestService {

    /**
     * 查询不动产受理云签附件信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlYqFjxxDO  不动产云签附件信息DO
     * @return 不动产云签附件信息集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/yqfjxx/list")
    List<BdcSlYqFjxxDO> listBdcSlYqFjxx(@RequestBody BdcSlYqFjxxDO bdcSlYqFjxxDO);

    /**
     * 批量保存不动产云签附件信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlYqFjxxDOList 不动产云签附件信息集合
     */
    @PostMapping("/realestate-accept/rest/v1.0/yqfjxx")
    void batchSaveBdcSlYqFjxx(@RequestBody List<BdcSlYqFjxxDO> bdcSlYqFjxxDOList);

    /**
     * 根据工作流实例ID删除云签附件信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     */
    @GetMapping("/realestate-accept/rest/v1.0/sc/yqfjxx")
    void deleteBdcSlYqFjxxByGzlslid(@RequestParam(name="gzlslid") String gzlslid);

}
