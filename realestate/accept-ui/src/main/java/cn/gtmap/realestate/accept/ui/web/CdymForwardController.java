package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcCdxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlCdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
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
import java.util.Objects;

/**
 * @program: realestate
 * @description: 查档页面跳转controller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-25 08:33
 **/
@Controller
@RequestMapping("rest/v1.0/cdym")
public class CdymForwardController extends BaseController {
    private static final String SLYMCDXX_URL = "/view/cdym/cdym.html";
    private static final String SLYMGZS_URL = "/view/cdym/gzd.html";
    private static final String SLYMBYSL_URL = "/view/cdym/bysld.html";


    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlCdxxFeignService bdcSlCdxxFeignService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardCdymHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        LOGGER.info("gzlslid获取项目类型，gzlslid为：{}", processInsId);
        //判断逻辑为根据初始化生成的查档信息的cdlb 字段判断是哪种页面1.有查询结果创建 2. 无结果创建 3. 不予受理创建
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            String xmid = bdcSlXmDOList.get(0).getXmid();
            BdcCdxxQO bdcCdxxQO = new BdcCdxxQO();
            bdcCdxxQO.setXmid(xmid);
            BdcSlCdxxDO bdcSlCdxxDO = bdcSlCdxxFeignService.queryBdcCdxx(bdcCdxxQO);
            if (Objects.nonNull(bdcSlCdxxDO) && StringUtils.isNotBlank(bdcSlCdxxDO.getCdxxid()) && StringUtils.isNotBlank(bdcSlCdxxDO.getCdlb())) {
                switch (bdcSlCdxxDO.getCdlb()) {
                    case "1":
                        return SLYMCDXX_URL;
                    case "2":
                        return SLYMGZS_URL;
                    case "3":
                        return SLYMBYSL_URL;
                    default:
                        return SLYMCDXX_URL;
                }
            } else {
                throw new AppException("未生成查档信息");
            }
        } else {
            throw new AppException("查档流程未生成对应的受理项目数据");
        }
    }
}
