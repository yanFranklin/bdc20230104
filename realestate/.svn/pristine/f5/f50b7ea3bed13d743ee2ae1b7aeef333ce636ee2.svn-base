package cn.gtmap.realestate.common.core.service.rest.inquiry.rugao;

import cn.gtmap.realestate.common.core.qo.BaseQO;
import org.springframework.data.domain.Page;
import cn.gtmap.realestate.common.core.dto.inquiry.rugao.BdcCqxxDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/01/19
 * @description
 */
public interface BdcCqCxRestService {

    /**
     *
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcDyCfCqcxQOStr
     * @return BdcPlDycfcxDTO
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/cqcx/dycf/page")
    Page<BdcCqxxDTO> dycfcqCx(Pageable pageable, @RequestParam(name = "bdcDyCfCqcxQOStr", required = false) String bdcDyCfCqcxQOStr);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param baseQO
     * @return
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/cqcx/dycf/search")
    List<BdcCqxxDTO> listCqxxByBdchyhList(@RequestBody BaseQO baseQO);

}
