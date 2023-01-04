package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @program: bdcdj3.0
 * @description: 税费统缴页面跳转
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-09-27 15:01
 **/
@Controller
@RequestMapping("/rest/v1.0/sftj")
public class BdcSftjForwardController {

    /*
     * 税费统缴列表页面
     * */
    private static final String SFTJ_LIST_URL = "/view/sftj/sftjList.html";
    /*
     * 税费统缴页面
     * */
    private static final String SFTJ_URL = "/view/sftj/sftj.html";

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardSfxxHtml(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "sfymlx", required = false) String sfymlx) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        if (Constants.SFYWMLX_YCSL.equals(sfymlx)) {
            //一窗流程根据受理项目判断
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(processInsId);
            if (bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    if (bdcSlXmDOList.size() > 1) {
                        //组合流程
                        return SFTJ_LIST_URL;
                    } else {
                        //简单流程
                        return SFTJ_URL;
                    }
                }
            }
            //没有返回地址，抛出异常
            throw new AppException("缺失受理基本项目信息");
        } else {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            int xmlx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
            if (CommonConstantUtils.LCLX_PLZH.equals(xmlx) || CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
                //组合流程
                return SFTJ_LIST_URL;
            } else if (CommonConstantUtils.LCLX_PT.equals(xmlx) && CollectionUtils.size(bdcXmDOList) > 1) {
                //分割合并特殊流程返回普通流程，若数量大于1，用列表展示
                return SFTJ_LIST_URL;
            } else {
                return SFTJ_URL;
            }
        }
    }
}
