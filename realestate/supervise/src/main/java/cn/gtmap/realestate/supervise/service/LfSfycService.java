package cn.gtmap.realestate.supervise.service;

import cn.gtmap.realestate.supervise.core.dto.BdcLfSfxxDTO;
import cn.gtmap.realestate.supervise.core.qo.LfSfxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/27
 * @description 收费异常
 */
public interface LfSfycService {
    /**
     * 分页查询异常收费信息
     * @param pageable 分页参数
     * @param sfxxQO 查询参数
     * @return {Page} 异常收费信息
     */
    Page<BdcLfSfxxDTO> listSfycxxData(Pageable pageable, LfSfxxQO sfxxQO);
}
