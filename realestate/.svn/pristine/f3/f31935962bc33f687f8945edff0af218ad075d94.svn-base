package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxbmcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGxbmcxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcGxbmcxfeignService;
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
 * @description 共享部门查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/gxbmcx")
public class BdcgxbmcxController extends BaseController {

    @Autowired
    BdcGxbmcxfeignService bdcGxbmcxfeignService;

    @GetMapping("/listGxbmcxByPage")
    public Object listGxbmcxByPage(@LayuiPageable Pageable pageable, BdcGxbmcxQO bdcGxbmcxQO) {
        Page<BdcGxbmcxDTO> bdcGxbmcxDTOPage = bdcGxbmcxfeignService.listBdcGxbmcxByPage(pageable, JSON.toJSONString(bdcGxbmcxQO));
        return super.addLayUiCode(bdcGxbmcxDTOPage);
    }

}
