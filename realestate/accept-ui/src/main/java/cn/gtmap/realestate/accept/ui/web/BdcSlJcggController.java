package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJcggDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJcggFeignService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理继承公告controller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-12-02 09:31
 **/
@RestController
@RequestMapping("/slym/jcgg")
public class BdcSlJcggController extends BaseController {
    @Autowired
    BdcSlJcggFeignService bdcSlJcggFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询继承公告信息
     * @date : 2021/12/2 9:31
     */
    @GetMapping("")
    public Object queryBdcSlJcgg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlJcggDO> bdcSlJcggDOList = bdcSlJcggFeignService.listBdcSlJcgg(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlJcggDOList)) {
                return bdcSlJcggDOList.get(0);
            }
        }
        return new BdcSlJcggDO();
    }

    @PostMapping("")
    public Object saveBdcSlJcgg(@RequestBody String json) {
        //判断是否有主键
        BdcSlJcggDO bdcSlJcggDO = JSON.parseObject(json, BdcSlJcggDO.class);
        if (StringUtils.isBlank(bdcSlJcggDO.getGgid())) {
            bdcSlJcggDO = bdcSlJcggFeignService.saveBdcSlJcgg(bdcSlJcggDO);
        } else {
            bdcSlEntityFeignService.updateByJsonEntity(json, BdcSlJcggDO.class.getName());
        }
        return bdcSlJcggDO;
    }
}
