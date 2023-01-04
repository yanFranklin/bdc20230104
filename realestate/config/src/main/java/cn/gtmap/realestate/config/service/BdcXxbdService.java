package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.dto.BdcXxbdDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcXxbdQO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 信息比对服务
 */
public interface BdcXxbdService {

    /**
     * @param bdcXxbdQO 信息比对查询参数
     * @return 信息比对数据结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成信息比对结果
     */
    BdcXxbdDTO generateXxbdDTO(BdcXxbdQO bdcXxbdQO);
}
