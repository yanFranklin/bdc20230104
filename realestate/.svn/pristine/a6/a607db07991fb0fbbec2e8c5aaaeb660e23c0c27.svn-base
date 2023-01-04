package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
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
 * *
 *
 * @author <a href="mailto:liaoxiang@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2021/11/16
 * @description 电子签章
 */
@Controller
@RequestMapping("/rest/v1.0/dzqz")
public class BdcDzqzForwardController {
    private static final String DZQZ_LIST_URL = "/view/dzzz/priviewDzzzList.html";
    private static final String DZQZ_URL = "/view/dzzz/priviewDzzz.html";

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardDzqzHtml(@RequestParam(name = "processInsId") String processInsId)  {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isNotEmpty(bdcZsDOList) && CollectionUtils.size(bdcZsDOList) == 1){
            return DZQZ_URL;

        }
        return DZQZ_LIST_URL;
    }


}
