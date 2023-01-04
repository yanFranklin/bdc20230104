package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcTbcxDO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcTbcxFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2021-04-14
 * @description 不动产调拨查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/tbcx")
public class BdcTbcxController extends BaseController {

    @Autowired
    private BdcTbcxFeignService bdcTbcxFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    StorageClientMatcher storageClient;


    /**
     * 批量删除调拨信息
     *
     * @param bdcTbcxDOList
     * @return int
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @DeleteMapping("/batchDelete")
    public Object batchDelete(@RequestBody List<BdcTbcxDO> bdcTbcxDOList) {
        return bdcTbcxFeignService.batchDeleteBdcTbxx(bdcTbcxDOList);
    }

    /**
     * 保存资产调拨信息
     *
     * @param bdcTbcxDO
     * @return int
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping("/save")
    public Object saveZctb(@RequestBody BdcTbcxDO bdcTbcxDO) {
        return bdcTbcxFeignService.saveBdcTbxx(bdcTbcxDO);
    }

    /**
     * 查询调拨信息
     *
     * @param id
     * @return Object
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("")
    public Object getZctbxx(@RequestParam(value="id") String id) {
        return bdcTbcxFeignService.queryZctbxx(id);
    }

}
