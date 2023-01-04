package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcMatchDataPageDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/2
 * @description 数据匹配
 */
@Controller
@RequestMapping("/matchData")
public class BdcMatchDataController extends BaseController {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 匹配台账页面类型, 土地证/不动产单元号
     */
    private static final String PPTZ_LX_TDZ = "tdz";

    private static final String BDCDYH = "bdcdyh";

    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;

    /**
     * @param bdcdyh
     * @return
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     * @description 获取落宗状态
     */
    @ResponseBody
    @GetMapping("/getlzzt")
    public Object getlzzt(@RequestParam("bdcdyh") String bdcdyh) {
        BdcMatchDataPageDTO bdcMatchDataPageDTO = new BdcMatchDataPageDTO();
        //落宗状态
        if (StringUtils.isNotBlank(bdcdyh) && BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)) {
            bdcMatchDataPageDTO.setClfsjlzzt(CommonConstantUtils.SF_F_DM);
        } else {
            bdcMatchDataPageDTO.setClfsjlzzt(CommonConstantUtils.SF_S_DM);
        }
        return bdcMatchDataPageDTO;
    }

    /**
     * @param xmid
     * @param bdcdyh
     * @return
     * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
     * @description 获取匹配状态
     */
    @ResponseBody
    @GetMapping("/getppzt")
    public Object getppzt(@RequestParam("xmid") String xmid,
                          @RequestParam("bdcdyh") String bdcdyh) {
        BdcMatchDataPageDTO bdcMatchDataPageDTO = new BdcMatchDataPageDTO();
        bdcMatchDataPageDTO.setClfsjppzt(CommonConstantUtils.SF_F_DM);
        //匹配状态
        if (StringUtils.isNotBlank(xmid)) {
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(xmid);
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                bdcMatchDataPageDTO.setClfsjppzt(CommonConstantUtils.SF_S_DM);
            } else if (StringUtils.isNotBlank(bdcdyh) && StringUtils.equals(CommonConstantUtils.BDCLX_TZM_F, BdcdyhToolUtils.getDzwTzm(bdcdyh))) {
                //房产证与土地证存在相同单元号,为已匹配
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setBdcdyh(bdcdyh);
                bdcXmQO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);
                bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    bdcMatchDataPageDTO.setClfsjppzt(CommonConstantUtils.SF_S_DM);
                }

            }
        }
        return bdcMatchDataPageDTO;
    }

}
