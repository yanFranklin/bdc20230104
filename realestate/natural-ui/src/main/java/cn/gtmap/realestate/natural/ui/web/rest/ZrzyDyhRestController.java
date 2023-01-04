package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.naturalresource.JbzkDO;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import cn.gtmap.realestate.common.core.qo.naturalresource.ZrzyJbzkQO;
import cn.gtmap.realestate.common.core.service.feign.naturalresource.NaturalJbzkFeignService;
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
 * @description 自然资源单元信息接口
 */
@RestController
@Api(tags = "自然资源单元信息接口")
@RequestMapping("/rest/v1.0/dyh")
public class ZrzyDyhRestController extends BaseController {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyDyhRestController.class.getName());

    @Autowired
    NaturalJbzkFeignService naturalJbzkFeignService;

    /**
     * 自然资源单元信息分页查询
     *
     * @param pageable
     * @param zrzyXmListQO
     * @return
     */
    @GetMapping("list")
    public Object zrzyDyhList(@LayuiPageable Pageable pageable, ZrzyJbzkQO zrzyXmListQO) {
        Page<JbzkDO> jbzkDOS = naturalJbzkFeignService.listJbzkByPage(pageable, zrzyXmListQO.getYsdm(), zrzyXmListQO.getZl(),zrzyXmListQO.getMc());
        return super.addLayUiCode(jbzkDOS);
    }

    /**
     * 自然资源单元信息详情
     *
     * @param zrzydjdyh
     * @return
     */
    @GetMapping("info/{zrzydjdyh}")
    public JbzkDTO info(@PathVariable(name = "zrzydjdyh") String zrzydjdyh) {
        return naturalJbzkFeignService.queryJbzkByZrzydjdyh(zrzydjdyh);
    }
}
