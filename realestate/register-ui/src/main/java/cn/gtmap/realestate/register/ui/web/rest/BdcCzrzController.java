package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/3/28
 * @description 操作日志
 */
@RestController
@RequestMapping("/rest/v1.0/czrz")
public class BdcCzrzController {

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @GetMapping(value = "/tjxx/{gzlslid}")
    public List<BdcCzrzDO> listBdcCzrzBySpxtywh(@PathVariable(value = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO =new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO>  bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOList) &&StringUtils.isNotBlank(bdcXmDOList.get(0).getSpxtywh())) {
            return bdcCzrzFeignService.listBdcCzrzBySpxtywh(bdcXmDOList.get(0).getSpxtywh(), "czsj asc");
        }
        return Collections.emptyList();

    }
}
