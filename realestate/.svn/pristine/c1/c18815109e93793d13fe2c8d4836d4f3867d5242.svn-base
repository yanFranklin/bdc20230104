package cn.gtmap.realestate.inquiry.web.rest.rugao;

import cn.gtmap.realestate.common.core.dto.inquiry.rugao.BdcCqxxDTO;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.rugao.BdcCqCxFeignService;
import cn.gtmap.realestate.inquiry.service.rugao.BdcCqCxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/01/19
 * @description
 */
@RestController
@RequestMapping(value = "/realestate-inquiry/rest/v1.0/cqcx")
public class BdcCqCxRestController implements BdcCqCxFeignService {

    @Autowired
    private BdcCqCxService bdcCqCxService;

    @Override
    @ResponseBody
    @RequestMapping("/dycf/page")
    public Page<BdcCqxxDTO> dycfcqCx(Pageable pageable, @RequestParam(name = "bdcDyCfCqcxQOStr", required = false) String bdcDyCfCqcxQOStr) {
        return bdcCqCxService.dycfcqCx(pageable,bdcDyCfCqcxQOStr);
    }

    @ResponseBody
    @RequestMapping("/dycf/search")
    @Override
    public List<BdcCqxxDTO> listCqxxByBdchyhList(@RequestBody BaseQO baseQO) {
        return bdcCqCxService.listCqxxByBdchyhList(baseQO.getIds());
    }

}
