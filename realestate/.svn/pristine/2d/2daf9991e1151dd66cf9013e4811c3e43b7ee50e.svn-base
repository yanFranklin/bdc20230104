package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcJwcxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description 纪委查询
 */
public interface BdcJwcxRestService {
    /**
     * 分页纪委查询
     *
     * @param pageable
     * @param bdcJwcxQOJson
     * @return Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/jwcx/page")
    Page<BdcJwcxDTO> listBdcJwcxByPage(Pageable pageable,
                                       @RequestParam(name = "bdcJwcxQOJson", required = false) String bdcJwcxQOJson);


}
