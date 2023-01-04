package cn.gtmap.realestate.inquiry.web.main;

import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description
 */
public class BaseController {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * 分页查询方法Repo
     *
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询方法Repo
     */
    @Autowired
    protected Repo repo;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;

    /**
     * @param pageable
     * @param map
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理排序信息，处理pageable中sort传入的参数，
     * 将排序信息和查询信息整理成一个map作为查询条件
     */

    public static Map pageableSort(Pageable pageable, Map map) {
        if (pageable.getSort() != null && !"UNSORTED".equals(String.valueOf(pageable.getSort()))) {
            String sort = String.valueOf(pageable.getSort());
            String sortParameter = sort.replace(":", "");
            map.put("sortParameter", sortParameter);
        }
        return map;
    }
}
