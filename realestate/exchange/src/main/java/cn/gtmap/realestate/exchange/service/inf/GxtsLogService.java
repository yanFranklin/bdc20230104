package cn.gtmap.realestate.exchange.service.inf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2018-12-17
 * @description 共享推送
 */
public interface GxtsLogService {

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 分页查询共享推送日志
     */
    Page<Object> listGxtsLogByPages(Pageable pageable, Map map);
	
}
