package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzLogDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzLogQO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.engine.core.service.BdcGzYzLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/5 15:12
 * @description 子规则实现类
 */
@Slf4j
@Service
public class BdcGzYzLogServiceImpl implements BdcGzYzLogService {

    @Autowired
    private Repo repo;

    @Override
    public Page<BdcGzYzLogDTO> listBdcGzYzLogGroupPage(Pageable pageable, BdcGzYzLogQO bdcGzYzLogQO) {
        return repo.selectPaging("listBdcGzYzLogGroupByPage", bdcGzYzLogQO, pageable);
    }

    @Override
    public Page<BdcGzYzLogDTO> listBdcGzYzLogPage(Pageable pageable, BdcGzYzLogQO bdcGzYzLogQO) {
        return repo.selectPaging("listBdcGzYzLogByPage", bdcGzYzLogQO, pageable);
    }
}
