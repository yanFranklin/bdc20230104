package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * *
 *
 * @author <a href="mailto:caolu@gtmap.cn>caolu</a>"
 * @version 1.0, 2022/4/21
 * @description 常州户室图转发Controller
 */
@Controller
@RequestMapping("/rest/v1.0/bdcHst")
public class BdcHstForwardController {
    private static final String HST_LIST_URL = "/view/djbxx/bdcDjbHstList.html";
    private static final String HST_URL = "/view/djbxx/bdcDjbHst.html";

    /**
     * 户室图请求url
     */
    @Value("${hst.httpurl:}")
    protected String hstHttpUrl;

    @Autowired
    private BdcBdcdyFeignService bdcBdcdyFeignService;

    /**
     * @param processInsId 工作流实例ID
     * @return 页面路径
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 多条数据展示列表，单条数据直接展示户室图
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardFzjlHtml(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam(name = "processInsId") String processInsId) throws IOException {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        Page<BdcXmXmfbDTO> bdcXmDOPage = bdcBdcdyFeignService.getXmOrYxmDistinctByDyhPage(0, 10, null, bdcXmQO);
        int count = bdcXmDOPage.getContent().size();

        // 如果只有一条数据，直接展示户室图页面
        if (count == 1) {
            BdcXmXmfbDTO bdcXmXmfbDTO = bdcXmDOPage.getContent().get(0);
            String bdcdywybh = bdcXmXmfbDTO.getBdcdywybh();
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + HST_URL+ "?bdcdywybh="+ bdcdywybh);
            return HST_URL;
        }
        return HST_LIST_URL;
    }
}
