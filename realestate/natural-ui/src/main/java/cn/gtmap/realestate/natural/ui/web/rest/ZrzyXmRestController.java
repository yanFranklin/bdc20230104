package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyXmListQO;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyXmFeignService;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2021/10/25
 * @description 自然资源产权信息接口
 */
@RestController
@Api(tags = "自然资源产权信息接口")
@RequestMapping("/rest/v1.0/xm")
public class ZrzyXmRestController extends BaseController {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyXmRestController.class.getName());

    @Autowired
    ZrzyXmFeignService zrzyXmFeignService;

    /**
     * 自然资源单元信息分页查询
     *
     * @param pageable
     * @param zrzyXmListQO
     * @return
     */
    @GetMapping("list")
    public Object zrzyXmList(@LayuiPageable Pageable pageable, ZrzyXmListQO zrzyXmListQO) {
        Page<ZrzyXmDTO> zrzyXmDTOS = zrzyXmFeignService.zrzyXmList(pageable, zrzyXmListQO.getZrzydjdyh(), zrzyXmListQO.getZl(),
                zrzyXmListQO.getDjyy());
        return super.addLayUiCode(zrzyXmDTOS);
    }
}
