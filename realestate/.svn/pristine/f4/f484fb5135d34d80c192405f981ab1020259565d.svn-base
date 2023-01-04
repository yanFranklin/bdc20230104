package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
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

/**
 * @program: realestate
 * @description: 需求流转信息跳转controller
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-03-02 08:33
 **/
@Controller
@RequestMapping("rest/v1.0/xqxx")
public class SlymXqxxForwardController extends BaseController {
    private static final String SLYMXQXX_URL = "/yancheng/xqxx/xqxx.html";


    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardXqxxHtml(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        LOGGER.info("gzlslid获取项目类型，gzlslid为：{}",processInsId);
        List<BdcSlXmDO> bdcSlXmDTOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
        if(CollectionUtils.isNotEmpty(bdcSlXmDTOList)) {
            return SLYMXQXX_URL;
        } else {
            throw new AppException("未找到项目信息，工作流实例id为："+processInsId);
        }
    }
}
