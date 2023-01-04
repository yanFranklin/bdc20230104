package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGrzfCxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/10/4 11:09
 * @description 个人住房查询
 */
public interface BdcGrzfRestService {

    /**
     * 个人住房信息查询
     *
     * @param grzfJson 权利人名称
     * @return BdcGrzfCxDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/grzf/page")
    Page<BdcGrzfCxDTO> listGrzfByPage(Pageable pageable,
                                      @RequestParam(name = "grzfJson", required = false) String grzfJson);
}
