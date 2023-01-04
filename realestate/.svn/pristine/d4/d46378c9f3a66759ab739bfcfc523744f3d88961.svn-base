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
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/18
 * @description 收费地址
 */
@Controller
@RequestMapping("rest/v1.0/sfxx")
public class BdcSfForwardController {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费列表页面
     */
    private static final String SFXX_LIST_URL = "/view/sfxx/sfxxList.html";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    private static final String SFXX_URL = "/view/sfxx/sfxx.html";

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;


    /**
     * @param processInsId 工作流实例ID
     * @return 页面地址
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费页面统一入口
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardSfxxHtml(@RequestParam(name = "processInsId") String processInsId,@RequestParam(name = "sfymlx", required = false) String sfymlx) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        if(Constants.SFYWMLX_YCSL.equals(sfymlx)){
            //一窗流程根据受理项目判断
            BdcSlJbxxDO bdcSlJbxxDO =bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(processInsId);
            if(bdcSlJbxxDO != null) {
                List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                    if(bdcSlXmDOList.size() >1){
                        //组合流程
                        return SFXX_LIST_URL;
                    }else{
                        //简单流程
                        return SFXX_URL;
                    }
                }
            }
            //没有返回地址，抛出异常
            throw new AppException("缺失受理基本项目信息");
        }else {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            int xmlx = bdcXmFeignService.makeSureBdcXmLx(processInsId);
            if(CommonConstantUtils.LCLX_PLZH.equals(xmlx) || CommonConstantUtils.LCLX_ZH.equals(xmlx)) {
                //组合流程
                return SFXX_LIST_URL;
            } else if(CommonConstantUtils.LCLX_PT.equals(xmlx) && CollectionUtils.size(bdcXmDOList) > 1) {
                //分割合并特殊流程返回普通流程，若数量大于1，用列表展示
                return SFXX_LIST_URL;
            } else {
                return SFXX_URL;
            }
        }
    }
}
