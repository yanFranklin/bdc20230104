package cn.gtmap.realestate.supervise.web;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.supervise.core.domain.BdcLfYcbjyjCkbjDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfYcbjyjCqbjDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfYcbjyjFgzsjbjDO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfSfxxDTO;
import cn.gtmap.realestate.supervise.core.qo.LfCkbjQO;
import cn.gtmap.realestate.supervise.core.qo.LfCqbjQO;
import cn.gtmap.realestate.supervise.core.qo.LfFgzsjbjQO;
import cn.gtmap.realestate.supervise.core.qo.LfSfxxQO;
import cn.gtmap.realestate.supervise.service.LfSfycService;
import cn.gtmap.realestate.supervise.service.LfYcbjyjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/27
 * @description 收费异常
 */
@RestController
@RequestMapping("/rest/v1.0/sfyc")
public class LfSfycRestController extends BaseController{
    @Autowired
    private LfSfycService lfSfycService;

    /**
     * 分页查询异常收费信息
     * @param pageable 分页参数
     * @param sfxxQO 查询参数
     * @return {Page} 异常收费信息
     */
    @GetMapping("/sfycxx")
    public Object listSfycxxData(@LayuiPageable Pageable pageable, LfSfxxQO sfxxQO) {
        Page<BdcLfSfxxDTO> cqbjData = lfSfycService.listSfycxxData(pageable, sfxxQO);
        return super.addLayUiCode(cqbjData);
    }
}
