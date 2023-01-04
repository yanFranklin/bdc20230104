package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxsqsDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxsqsQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/22
 * @description 查询申请书服务
 */
public interface BdcCxsqsRestService {
    @PostMapping("/realestate-inquiry/rest/v1.0/cxsqs")
    BdcCxsqsDTO saveOrUpdateBdcCxsqs(@RequestBody BdcCxsqsDTO bdcCxsqsDTO);

    @PostMapping("/realestate-inquiry/rest/v1.0/cxsqs/list")
    List<BdcCxsqsDTO> queryBdcCxSqs(@RequestBody BdcCxsqsQO bdcCxsqsQO);

    @GetMapping("/realestate-inquiry/rest/v1.0/cxsqs/page")
    Page<BdcCxsqsDO> listBdcCxsqsPage(Pageable pageable, @RequestParam(name = "cxsqsParamJson", required = false) String cxsqsParamJson);
}
