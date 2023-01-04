package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;


import cn.gtmap.realestate.common.core.dto.accept.BdcQuerySfztDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfxxCzQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSfxxFeignService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/rest/v1.0/sfxxcx")
public class BdcSfxxCxController extends BaseController {

    @Autowired
    private BdcSfxxFeignService bdcSfxxFeignService;

    /**
     * @param bdcSfxxCzQO 不动产受理收费信息常州QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询财政收费状态
     */
    @ResponseBody
    @GetMapping("/querySfzt")
    public BdcQuerySfztDTO querySfzt(BdcSfxxCzQO bdcSfxxCzQO) {
        return bdcSfxxFeignService.querySfzt(bdcSfxxCzQO);
    }


    /**
     * @param jftbsj 日期
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步未缴费信息
     */
    @ResponseBody
    @GetMapping("/wjftb")
    public void wjftb(@RequestParam(value = "jftbsj", required = true) String jftbsj) {
       // Date date = DateUtils.formatDate(jftbsj, DateUtils.sdf);
        bdcSfxxFeignService.wjftb(jftbsj);
    }
}
