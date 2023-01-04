package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/07/01
 * @description 发证记录页面转发Controller
 */
@Controller
@RequestMapping("/rest/v1.0/bdcFzjl")
public class BdcFzjlForwardController {
    private static final String FZJL_LIST_URL = "/view/fzjl/bdcFzjlList.html";
    private static final String FZJL_URL = "/view/fzjl/fzjl.html";

    @Value("${html.version:standard}")
    private String htmlVersion;

    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZsInitFeignService bdcZsInitFeignService;


    /**
     * @param processInsId 工作流实例ID
     * @return 页面路径
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 标准发证记录跳转方法（一个项目，或生成一本证书，或多个项目同一个权利人只打印一个发证记录，
     * 其他情况走台账逻辑，一本证书打印一份发证记录）
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardFzjlHtml(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "type", required = false) String type) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        Integer zsNum = bdcZsFeignService.countBdcZs(bdcZsQO);

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        // 盐城分别持证的情况，跳转发证记录列表页面
        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(htmlVersion)
                && CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.size(bdcXmDOList) == 1 && zsNum >1){
            return FZJL_LIST_URL;
        }
        // 证书数量为1或者项目数据量为1时，都跳转到发证记录页面
        //盐城发证记录根据证书数量展示，显示发证记录列表，url地址配置type=1
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.size(bdcXmDOList) == 1 || zsNum == 1) {
            return FZJL_URL;
        }

        if (CollectionUtils.isNotEmpty(bdcXmDOList) && CollectionUtils.size(bdcXmDOList) > 1) {
            Set<String> qlrSet = new HashSet();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (StringUtils.isNotBlank(bdcXmDO.getQlr())) {
                    qlrSet.add(bdcXmDO.getQlr());
                }
            }
            if (CollectionUtils.size(qlrSet) == 1 && !StringUtils.equals("1", type)) {
                // 权利人一致，默认领证人信息也一致，发证记录合并展示
                return FZJL_URL;
            }
        }
        return FZJL_LIST_URL;
    }
}
