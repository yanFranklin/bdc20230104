package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcTbcxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2021-04-17
 * @description 调拨查询
 */
public interface BdcTbcxRestService {

    /**
     * @param bdcTbcxDO 调拨信息实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存调拨信息实体
     */
    @PutMapping("/realestate-inquiry/rest/v1.0/bdcTbcx")
    BdcTbcxDO saveBdcTbxx(@RequestBody BdcTbcxDO bdcTbcxDO);

    /**
     * @param bdcTbcxDOList
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除调拨信息
     */
    @DeleteMapping("/realestate-inquiry/rest/v1.0/bdcTbcx")
    int batchDeleteBdcTbxx(@RequestBody List<BdcTbcxDO> bdcTbcxDOList);

    /**
     * @param id
     * @return BdcTbcxDO 资产调拨数据
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询资产调拨数据
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/bdcTbcx")
    BdcTbcxDO queryZctbxx(@RequestParam(value="id")  String id);

}
