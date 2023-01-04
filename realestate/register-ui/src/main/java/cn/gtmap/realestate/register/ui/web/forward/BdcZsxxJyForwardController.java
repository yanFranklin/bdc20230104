package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: 3.0
 * @description: 证书校验信息跳转页面
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-02 16:02
 **/
@Controller
@RequestMapping("/rest/v1.0/zsjy")
public class BdcZsxxJyForwardController {

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;

    private static final String ZS_JYXX_URL = "/view/xxjy/xxjy.html";
    private static final String ZS_JYXX_LIST_URL = "/view/xxjy/bdcZsJyList.html";

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardZsHtml(@RequestParam(name = "processInsId", required = false) String processInsId,
                                @RequestParam(name = "xmid", required = false) String xmid,
                                HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(processInsId) && StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        List<BdcZsDO> bdcZsDOList;
        if (StringUtils.isNotBlank(xmid)) {
            bdcZsDOList = bdcZsInitFeignService.initBdcqz(xmid, true);
        } else {
            bdcZsDOList = bdcZsInitFeignService.initBdcqzs(processInsId, true);
        }
        List<BdcXmDO> bdcXmDOList = new ArrayList();
        if (StringUtils.isBlank(xmid)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        }
        // 生成一本证书，或者当前项目只有一条时，跳转到单证书页面
        if (StringUtils.isNotBlank(xmid) || CollectionUtils.isNotEmpty(bdcZsDOList) && CollectionUtils.size(bdcZsDOList) == 1 || CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.size(bdcXmDOList) == 1) {
            return ZS_JYXX_URL;
        }
        return ZS_JYXX_LIST_URL;
    }

}
