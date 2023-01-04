package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcJwcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJwcxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcJwcxfeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description 纪委查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/jwcx")
public class BdcJwcxController extends BaseController {

    @Autowired
    BdcJwcxfeignService bdcJwcxfeignService;

    @GetMapping("/listJwcxByPage")
    public Object listBdcJwcxByPage(@LayuiPageable Pageable pageable, BdcJwcxQO bdcJwcxQO) {
        Page<BdcJwcxDTO> bdcJwcxDTOPage = bdcJwcxfeignService.listBdcJwcxByPage(pageable, JSON.toJSONString(bdcJwcxQO));
        return super.addLayUiCode(bdcJwcxDTOPage);
    }

}
