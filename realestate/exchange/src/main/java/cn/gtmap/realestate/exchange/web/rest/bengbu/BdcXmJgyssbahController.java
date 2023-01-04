package cn.gtmap.realestate.exchange.web.rest.bengbu;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import io.swagger.annotations.Api;
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
 * @description 查询竣工验收设备编号
 */
@RestController
@Api(tags = "查询竣工验收设备编号")
public class BdcXmJgyssbahController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    /**
     * 传入工作流实例id获取，竣工验收备案表编号
     * @author  <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @param   bdcXmQO 不动产项目
     * @return  {List} 不动产项目信息
     * @description 通过传入参数返回竣工验收备案表编号
     */
    @PostMapping(path = "/jyxx/jgysbabhlist")
    Object listJgysbabh(@RequestBody BdcXmQO bdcXmQO) {
        Map qxdz = new HashMap<String,String>();
        // 用工作流定义id，查询·项目表，获取得到的项目集合，获取第一个项目的区县code,在获取其地区名称
        List<BdcXmDO> bdcXmDOList =  bdcXmFeignService.listBdcXm(bdcXmQO);
        // 去重之前的
        List<BdcXmFbDO> bdcXmFbDOList  = new ArrayList<>();
        // 去重以后直接返回的
        List<BdcXmFbDO> xmFbDOList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            // 根据项目id去重
            List<BdcXmDO> bdcXmDOs = bdcXmDOList.stream().collect(
                    collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getXmid()))),
                            ArrayList::new));
            // 查询yxxx
            for (BdcXmDO bdcXmDO:bdcXmDOs) {
                if (StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                    BdcXmFbQO bdcXmFbQO =new BdcXmFbQO();
                    bdcXmFbQO.setXmid(bdcXmDO.getXmid());
                    List<BdcXmFbDO> bdcXmFbDOS = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                    if(CollectionUtils.isNotEmpty(bdcXmFbDOS)) {
                        bdcXmFbDOList.addAll(bdcXmFbDOS);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList) ) {
            // 根据竣工验收备案编号去重
            xmFbDOList = bdcXmFbDOList.stream().collect(
                    collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(o -> StringUtils.isNotBlank(o.getJgysbabh())))),
                            ArrayList::new));
        }
        return xmFbDOList;
    }
}
