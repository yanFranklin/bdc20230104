package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.init.BdcQlPageResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @program: realestate
 * @description: 原权利信息查看十多个资源还是列表展现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-10-10 15:44
 **/
@Controller
@RequestMapping("rest/v1.0/yqlxx")
public class BdcYqlxxForwardController extends BaseController {

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    private static final String YQLIST_URL = "/view/slym/yqllist.html";

    private static final String YQL_URL = "/view/slym/yql.html";


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardQlxxHtml(@LayuiPageable Pageable pageable, @RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("查询原权利缺失参数gzlslid");
        }
        BdcQlQO bdcQlQO = new BdcQlQO();
        bdcQlQO.setGzlslid(processInsId);
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        Page<BdcQlPageResponseDTO> bdcQlPageResponseDTOPage = bdcXmFeignService.listBdcYqlByPage(pageable, bdcQlQoStr);
        if (bdcQlPageResponseDTOPage != null) {
            List<BdcQlPageResponseDTO> bdcQlPageResponseDTOList = bdcQlPageResponseDTOPage.getContent();
            if (CollectionUtils.isNotEmpty(bdcQlPageResponseDTOList) && CollectionUtils.size(bdcQlPageResponseDTOList) == 1) {
                return YQL_URL;
            } else {
                return YQLIST_URL;
            }
        } else {
            return YQLIST_URL;
        }
    }

}
