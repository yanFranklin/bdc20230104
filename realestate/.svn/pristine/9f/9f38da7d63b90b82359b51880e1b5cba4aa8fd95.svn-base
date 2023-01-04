package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcTbcxDO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcTbcxRestService;
import cn.gtmap.realestate.inquiry.service.BdcTbcxService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2021-04-14
 * @description 调拨查询
 */
@RestController
@Api(value = "调拨查询服务")
public class BdcTbcxRestController implements BdcTbcxRestService {

    @Autowired
    private BdcTbcxService bdcTbcxService;


    /**
     * @param bdcTbcxDO 调拨信息实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存调拨信息实体
     */
    @Override
    public BdcTbcxDO saveBdcTbxx(@RequestBody BdcTbcxDO bdcTbcxDO) {
        return bdcTbcxService.saveBdcTbxx(bdcTbcxDO);
    }

    /**
     * @param bdcTbcxDOList
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除调拨信息
     */
    @Override
    public int batchDeleteBdcTbxx(@RequestBody List<BdcTbcxDO> bdcTbcxDOList) {
        return bdcTbcxService.batchDeleteBdcTbxx(bdcTbcxDOList);
    }

    /**
     * @param id
     * @return BdcTbcxDO 资产调拨数据
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询资产调拨数据
     */
    @Override
    public BdcTbcxDO queryZctbxx(@RequestParam(value="id")  String id) {
        return bdcTbcxService.queryZctbxx(id);
    }


}
