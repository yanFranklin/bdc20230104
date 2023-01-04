package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import groovy.util.Node;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/19
 * @description 归档配置xml业务类
 */
public interface BdcGdsjPzService {
    /**
     * @param  gdpz
     * @return 结果Map
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据配置表获取 归档信息
     */
    Map queryBdcGdxx(Map gdpz);

    /**
     * @param gzlslid 工作流实例id
     * @return 归档 xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档不动产信息
     */
    String gdBdcXx(String gzlslid,String xmid);
    /**
     * @param path xml地址
     * @return Node
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取xml
     */
    Node getBdcGdNode(String path);
    /**
     * @param bdcXmDO
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取原文归档xml
     */
    List<Map> queryBdcGdYw(BdcXmDO bdcXmDO);
}
