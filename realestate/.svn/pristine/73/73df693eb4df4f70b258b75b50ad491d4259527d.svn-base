package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFsPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcFsPzFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a herf="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/08/03
 * @description 非税配置
 */
@RestController
@RequestMapping(value = "/rest/v1.0/fspz")
public class BdcFsPzController extends BaseController {
    @Autowired
    private BdcFsPzFeignService fsPzFeignService;


    /**
     * 更新非税配置信息
     *
     * @param bdcFsPzDO
     */
    @PostMapping("/update")
    public int updateFspz(@RequestBody BdcFsPzDO bdcFsPzDO) {
        return fsPzFeignService.updateFspz(bdcFsPzDO);
    }

    /**
     * 批量保存非税配置信息
     *
     * @param fsPzDOList
     */
    @PostMapping("/save/pl")
    public int saveFspzPl(@RequestBody List<BdcFsPzDO> fsPzDOList) {
        if (CollectionUtils.isEmpty(fsPzDOList)) {
            throw new AppException("新增非税配置不可为空");
        }
        for (BdcFsPzDO fsPzDO : fsPzDOList) {
            fsPzDO.setFspzid(UUIDGenerator.generate16());
        }
        return fsPzFeignService.saveFspzPl(fsPzDOList);
    }

    /**
     * 获取非税配置信息
     *
     * @param fspzid
     */
    @GetMapping("/query")
    public BdcFsPzDO queryFspz(String fspzid) {
        return fsPzFeignService.queryFspz(fspzid, "");
    }

    /**
     * 删除非税配置
     *
     * @param fspzidList
     */
    @GetMapping("/delete")
    public void deleteFspz(@RequestParam("fspzidList") List<String> fspzidList) {
        if (CollectionUtils.isEmpty(fspzidList)) {
            throw new MissingArgumentException("删除非税配置，缺少参数fspzidList");
        }
        fsPzFeignService.deleteFspz(fspzidList);
    }

}
