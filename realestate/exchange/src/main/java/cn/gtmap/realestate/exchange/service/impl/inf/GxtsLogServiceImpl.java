package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.exchange.service.inf.GxtsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GxtsLogServiceImpl implements GxtsLogService {

    @Autowired
    private Repo repository;

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 分页查询共享推送日志
     */
    @Override
    public Page<Object> listGxtsLogByPages(Pageable pageable, Map map) {
        return repository.selectPaging("listGxtsLogByPageOrder", map, pageable);
    }
}
