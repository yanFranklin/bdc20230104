package cn.gtmap.realestate.register.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/11/16
 * @description 海域信息
 */
@RestController
@RequestMapping("/rest/v1.0/hyxx")
public class BdcHyxxController extends BaseController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private DjxxFeignService djxxFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    /**
     * @param xmid 项目ID
     * @return BdcdjbZhjbxxVO 登记簿宗海基本信息VO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取海籍调查表信息
     */
    @GetMapping(value = "/{xmid}/hjdcb")
    public DjxxResponseDTO queryHjdcbxx(@PathVariable(value = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("项目ID{}，未查询到项目信息！");
        }
        BdcXmFbQO bdcXmFbQO =new BdcXmFbQO();
        bdcXmFbQO.setXmid(xmid);
        List<BdcXmFbDO> bdcXmFbDOList=bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        String qjgldm ="";
        if(CollectionUtils.isNotEmpty(bdcXmFbDOList)){
            qjgldm =bdcXmFbDOList.get(0).getQjgldm();

        }
        String bdcdyh = bdcXmDOList.get(0).getBdcdyh();
        return djxxFeignService.queryDjxxByBdcdyh(bdcdyh,qjgldm);
    }

}
