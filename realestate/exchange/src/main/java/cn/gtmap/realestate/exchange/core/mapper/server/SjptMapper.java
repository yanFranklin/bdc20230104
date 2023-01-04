package cn.gtmap.realestate.exchange.core.mapper.server;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-26
 * @description 省级平台接口需要的业务系统数据库查询
 */
public interface SjptMapper {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<java.lang.String>
     * @description 根据QLR 获取权利
     */
    List<String> getQlidByQlr(Map<String,Object> paramMap);
}
