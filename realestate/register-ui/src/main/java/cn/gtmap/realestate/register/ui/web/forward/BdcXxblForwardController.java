package cn.gtmap.realestate.register.ui.web.forward;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXzxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/07/01
 * @description 发证记录页面转发Controller
 */
@Controller
@RequestMapping("/rest/v1.0/xxbl")
public class BdcXxblForwardController extends BaseController {
    private static final String XXBL_ADD = "/view/xxbl/xxblSelectbdcdy.html";
    private static final String XXBL_MODIFY = "/view/xxbl/xxblUpdateinfo.html?type=update";


    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlXzxxFeignService bdcSlXzxxFeignService;


    /**
     * @param processInsId 工作流实例ID
     * @return 页面路径
     * @author <a href="mailto:chneyucheng@gtmap.cn">chneyucheng</a>
     * @description 信息补录跳转页面地址 分为 新增补录和修改补录
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String forwardXxblHtml(@RequestParam(name = "processInsId") String processInsId,HttpServletRequest request) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例ID不能为空！");
        }
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setGzlslid(processInsId);
        BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
        if(bdcSlXzxxDO != null){
            if(StringUtils.isNotBlank(bdcSlXzxxDO.getYxmid())){
                String xmid = bdcSlXzxxDO.getYxmid();
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                    String gzlslid = bdcXmDOList.get(0).getGzlslid();
                    if(StringUtils.isNotBlank(gzlslid)){
                        return XXBL_MODIFY ;
                    }else{
                        LOGGER.info("项目数据不包含gzlslid,补录方式为新增。xmid:{}",xmid);
                    }
                }
            }
        }
        return XXBL_ADD ;
    }
}
