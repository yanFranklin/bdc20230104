package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxbmcxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description 共享部门查询
 */
public interface BdcGxbmcxRestService {
    /**
     * 分页共享部门查询
     *
     * @param pageable
     * @param bdcGxbmcxQOJson
     * @return Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/gxbmcx/page")
    Page<BdcGxbmcxDTO> listBdcGxbmcxByPage(Pageable pageable,
                                         @RequestParam(name = "bdcGxbmcxQOJson", required = false) String bdcGxbmcxQOJson);


}
