package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;

import cn.gtmap.gtc.clients.RegionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RegionDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
 * @version 1.0, 2021/11/15
 * @description 获取区县信息
 */
@RestController
@RequestMapping(value = "/rest/v1.0/xm")
public class BdcXmController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private RegionManagerClient regionManagerClient;

    /**
     * 传入工作流实例id获取，项目区县代码，和区县名称
     * @author  <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @param   bdcXmQO 不动产项目
     * @return  {List} 不动产项目信息
     * @description 通过传入参数返回区县信息
     */
    @PostMapping(path = "qxxx/list")
    Object listBdcXm(@RequestBody BdcXmQO bdcXmQO) {
        Map qxdz = new HashMap<String,String>();
        // 用工作流定义id，查询·项目表，获取得到的项目集合，获取第一个项目的区县code,在获取其地区名称
       List<BdcXmDO> bdcXmDOList =  bdcXmFeignService.listBdcXm(bdcXmQO);
       List<RegionDto> regionDtoList  = new ArrayList<>();
       if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
           // 根据区县代码去重
           List<BdcXmDO> bdcXmDOs = bdcXmDOList.stream().collect(
                   collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getQxdm()))),
                           ArrayList::new));
           for (BdcXmDO bdcXmDO:bdcXmDOs) {
               if (StringUtils.isNotBlank(bdcXmDO.getQxdm())) {
                   RegionDto region = regionManagerClient.findRegionByCode(bdcXmDO.getQxdm());
                   regionDtoList.add(region);
               }
           }
       }
       return regionDtoList;
    }
}
