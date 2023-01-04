package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.supervise.core.dto.BdcLfSfxxDTO;
import cn.gtmap.realestate.supervise.core.qo.LfSfxxQO;
import cn.gtmap.realestate.supervise.service.LfSfycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/27
 * @description 收费异常
 */
@Service
public class LfSfycServiceImpl implements LfSfycService {
    @Autowired
    private Repo repo;


    /**
     * 分页查询异常收费信息
     * @param pageable 分页参数
     * @param sfxxQO   查询参数
     * @return {Page} 异常收费信息
     */
    @Override
    public Page<BdcLfSfxxDTO> listSfycxxData(Pageable pageable, LfSfxxQO sfxxQO) {
        return repo.selectPaging("listYcsfxxByPageOrder", sfxxQO, pageable);
    }
}
