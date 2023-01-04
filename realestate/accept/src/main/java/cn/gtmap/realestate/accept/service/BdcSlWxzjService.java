package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/25.
 * @description 不动产受理维修资金处理服务
 */
public interface BdcSlWxzjService {

    /**
     * 生成维修资金项目信息
     *
     * @param bdcSlWxjjxxDO 不动产维修基金信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void generateWxzjXmxx(BdcSlWxjjxxDO bdcSlWxjjxxDO);

    /**
     * 通知维修资金
     *
     * @param gzlslid 工作流实例ID
     * @return object
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Object tzwxzj(String gzlslid, String xmid);
}
