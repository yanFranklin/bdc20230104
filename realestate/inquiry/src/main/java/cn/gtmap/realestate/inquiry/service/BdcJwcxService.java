package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcJwcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJwcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description
 */
public interface BdcJwcxService {
    /**
     * 分页纪委查询
     *
     * @param pageable
     * @param bdcJwcxQO
     * @return Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    Page<BdcJwcxDTO> listBdcJwcxByPage(@RequestBody Pageable pageable, BdcJwcxQO bdcJwcxQO);

}
