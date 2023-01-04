package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.service.impl.inf.sjpt.*;
import cn.gtmap.realestate.exchange.service.inf.sjpt.QueryCxjgService;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/24
 * @description
 */
public class BeanList {
    private static List<QueryCxjgService> queryCxjgServiceList = new ArrayList<>();

    public static List<QueryCxjgService> getQueryCxjgServiceList() {
        if (CollectionUtils.isEmpty(queryCxjgServiceList)) {
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgFdcqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgGjzwsyqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgHysyqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgJsydsyqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgLqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgNydsyqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgTdsyqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgCfServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgDyaqServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgYgServiceImpl.class));
            queryCxjgServiceList.add((QueryCxjgService) Container.getBean(QueryCxjgYyServiceImpl.class));
        }
        return queryCxjgServiceList;
    }

}
