package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGrzfCxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGrzfQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/10/4 16:10
 * @description 个人住房查询
 */
public interface BdcGrzfService {

    /**
     * 个人住房查询
     *
     * @param grzfQO grzfQO
     * @return BdcGrzfCxDTO BdcGrzfCxDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<BdcGrzfCxDTO> listGrzfByPage(Pageable pageable, BdcGrzfQO grzfQO);
}
