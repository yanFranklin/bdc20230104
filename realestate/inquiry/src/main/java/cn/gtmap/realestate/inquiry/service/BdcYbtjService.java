package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;

import java.util.List;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/12/28
 * @description 月报统计查询服务
 */
public interface BdcYbtjService {

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取月报统计信息
     */
    List bdcywtj(BdcYbtjQO bdcYbtjQO);

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 获取月报统计信息
     */
    List bdcyzbwtj(BdcYbtjQO bdcYbtjQO);

}
