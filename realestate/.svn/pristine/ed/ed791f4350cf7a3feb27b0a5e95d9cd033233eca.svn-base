package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxbmcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcJwcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGxbmcxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJwcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description
 */
public interface BdcGxbmcxService {
    /**
     * 分共享部门委查询
     *
     * @param pageable
     * @param bdcGxbmcxQO
     * @return Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    Page<BdcGxbmcxDTO> listBdcGxbmcxByPage(@RequestBody Pageable pageable, BdcGxbmcxQO bdcGxbmcxQO);

}
