package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcNwCjRzDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcNwCjRzQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.bengbu.BdcNwCjRzFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021/01/25
 * @description 蚌埠_互联网+业务内网创建日志查询
 */
@RestController
@RequestMapping(value = "/rest/v1.0/bengbu/nwcjrz")
public class BdcNwCjRzController extends BaseController {

    @Autowired
    private BdcNwCjRzFeignService bdcNwCjRzFeignService;

    /**
     * 分页查询日志
     *
     * @param pageable
     * @param bdcNwCjRzQO
     * @return BdcNwCjRzDTO                                                             ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/list")
    public Object dwgzListByPage(@LayuiPageable Pageable pageable, BdcNwCjRzQO bdcNwCjRzQO) {
        String bdcNwCjRzQOStr = JSON.toJSONString(bdcNwCjRzQO);
        Page<BdcNwCjRzDTO> bdcDwgzglDOPage = bdcNwCjRzFeignService.listNwCjRz(pageable,bdcNwCjRzQOStr);
        return super.addLayUiCode(bdcDwgzglDOPage);
    }
}
