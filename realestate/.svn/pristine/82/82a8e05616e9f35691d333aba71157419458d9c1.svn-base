package cn.gtmap.realestate.building.ui.web;

import cn.gtmap.realestate.building.ui.config.omp.OmpConfig;
import cn.gtmap.realestate.building.ui.core.bo.OmpParamWhereBO;
import cn.gtmap.realestate.building.ui.util.Constants;
import cn.gtmap.realestate.building.ui.util.OmpUtil;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description 一张图功能
 */
@Controller
@RequestMapping("/omp")
public class OmpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmpController.class);


    @Autowired
    private OmpUtil ompUtil;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;


    /**
     * @param response
     * @param bdcdyh
     * @param bdclx
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 拼接URL 跳转到一张图定位页面
     */
    @RequestMapping("/redirect/{bdcdyh}/{bdclx}")
    public void redirect(HttpServletResponse response,
                         @PathVariable(name = "bdcdyh") String bdcdyh,
                         @PathVariable(name = "bdclx") String bdclx) throws IOException {
        OmpConfig ompConfig = ompUtil.getOmpConfigByBdclx(bdclx);
        if (ompConfig == null) {
            throw new AppException("找不到不动产类型" + bdclx + "对应的一张图定位配置");
        }
        OmpParamWhereBO ompParamWhereBO = new OmpParamWhereBO(bdcdyh);
        //合肥一张图发布三个服务 宗地，地上宗地，地下宗地
        if (StringUtils.equals(Constants.HFTPL, ompConfig.getTpl(bdcdyh))) {
            String zdlx = bdcdyh.substring(12, 14);
            String tdlx = bdcdyh.substring(19, 20);
            LOGGER.info("宗地类型是{}", zdlx);
            //GB是宗地，GX是地下，GS是地上
            switch (zdlx) {
                case Constants.HFTPL_GB:
                    if (StringUtils.equals(Constants.BDCDY_F, StringUtils.upperCase(tdlx))) {
                        ompConfig.setLayerAlias(Constants.HFTPL_FW);
                        LOGGER.info("判断后房屋类型GBF{}", tdlx);
                    } else {
                        ompConfig.setLayerAlias(Constants.HFTPL_ZD);
                        LOGGER.info("判断后宗地类型GB{}", zdlx);
                    }
                    break;
                case Constants.HFTPL_GS:
                    if (StringUtils.equals(Constants.BDCDY_F, StringUtils.upperCase(tdlx))) {
                        ompConfig.setLayerAlias(Constants.HFTPL_DSFW);
                        LOGGER.info("判断后房屋类型GSF{}", tdlx);
                    } else {
                        ompConfig.setLayerAlias(Constants.HFTPL_DSZD);
                        LOGGER.info("判断后宗地类型GS{}", zdlx);
                    }

                    break;
                case Constants.HFTPL_GX:
                    if (StringUtils.equals(Constants.BDCDY_F, StringUtils.upperCase(tdlx))) {
                        ompConfig.setLayerAlias(Constants.HFTPL_DXFW);
                        LOGGER.info("判断后房屋类型GXF{}", tdlx);
                    } else {
                        ompConfig.setLayerAlias(Constants.HFTPL_DXZD);
                        LOGGER.info("判断后宗地类型Gx{}", zdlx);
                    }
                    break;
                default:
                    break;
            }
        }
        response.sendRedirect(ompUtil.getOmpUrlWithParam(ompConfig, ompParamWhereBO));
    }


    /**
     * @param response
     * @param processInsId
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不动产资源
     */
    @RequestMapping("/bdcres/redirect")
    public void bdcResourceRedirect(HttpServletResponse response,
                                    String processInsId) throws IOException {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
        String url = ompUtil.getOmpUrlWithBdcXmList(bdcXmList);
        response.sendRedirect(url);
    }
}
