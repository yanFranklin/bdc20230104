package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhdpDTO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZhdpCxFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/12
 * @description 综合大屏-所有中心办理业务的集合展示
 */
@RestController
@RequestMapping(value = "/rest/v1.0")
public class BdcZhdpCxController extends BaseController {
    @Autowired
    private BdcZhdpCxFeignService bdcZhdpCxFeignService;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 综合大屏切换时间,单位毫秒
      */
    @Value("${zhdp.qhsj:3000}")
    protected Integer qhsj;

    /**
     * version 1.0
     *
     * @return List 当前所有中心办理业务
     * @description 综合大屏所有中心办理业务查询
     * @date 2019/7/12
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    @GetMapping("/zhdp")
    public BdcZhdpDTO listBdcZhdp() {
        BdcZhdpDTO bdcZhdpDTO =new BdcZhdpDTO();
        bdcZhdpDTO.setQhsj(qhsj);
        List<Map> dataList =bdcZhdpCxFeignService.listBdcZhdp();
        if(CollectionUtils.isNotEmpty(dataList)) {
            bdcZhdpDTO.setZhdpDataList(dataList);
        }
        return bdcZhdpDTO;
    }
}
