package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * *
 *
 * @author <a href="mailto:wutao2@gtmap.cn>wutao2</a>"
 * @version 1.0, 2021/10/29
 * @description 宗地图转发Controller
 */
@Controller
@RequestMapping("/rest/v1.0/bdcZdt")
public class BdcZdtForwardController {
    private static final String ZDT_LIST_URL = "/view/djbxx/bdcDjbZdtList.html";
    private static final String ZDT_URL = "/view/djbxx/bdcDjbZdt.html";

    @Autowired
    private BdcBdcdyFeignService bdcBdcdyFeignService;


    /**
     * @param processInsId 工作流实例ID
     * @return 页面路径
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 多条数据展示列表，单条数据直接展示宗地图
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
        int count=bdcXmDOPage.getContent().size();

        // 如果只有一条数据，直接展示宗地图页面
        if (count == 1) {
            BdcXmXmfbDTO bdcXmXmfbDTO=bdcXmDOPage.getContent().get(0);
            String qjgldm=bdcXmXmfbDTO.getQjgldm();
            String bdcdyh=bdcXmXmfbDTO.getBdcdyh();
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + ZDT_URL+"?bdcdyh="+bdcdyh+"&qjgldm="+qjgldm);
            return ZDT_URL;
        }
        return ZDT_LIST_URL;
    }
}
