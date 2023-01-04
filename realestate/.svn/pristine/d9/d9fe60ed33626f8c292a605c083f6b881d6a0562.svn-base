package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGrzfCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGrzfQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcGrzfFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/10/5 15:44
 * @description 个人住房查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/grzf")
public class BdcGrzfController extends BaseController {

    @Autowired
    BdcGrzfFeignService grzfFeignService;

    @GetMapping("/listGrzfByPage")
    public Object listGrzfByPage(@LayuiPageable Pageable pageable, BdcGrzfQO grzfQO) {
        Page<BdcGrzfCxDTO> bdcGrzfDTOPage = grzfFeignService.listGrzfByPage(pageable, JSON.toJSONString(grzfQO));
        return super.addLayUiCode(bdcGrzfDTOPage);
    }

}
