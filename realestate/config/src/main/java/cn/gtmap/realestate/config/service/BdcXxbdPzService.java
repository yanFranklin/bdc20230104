package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcXxbdPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXxbdSjPzDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/28
 * @description 信息比对配置服务
 */
public interface BdcXxbdPzService {

    /**
     * @param bdlx 比对类型
     * @return 信息比对配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据比对类型获取信息比对配置
     */
    BdcXxbdPzDO queryBdcXxbdPzByBdlx(String bdlx);

    /**
     * @param bdlx
     * @return 信息比对数据配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据比对类型获取信息比对数据配置
     */
    List<BdcXxbdSjPzDO> listBdcXxbdPzByBdlx(String bdlx);
}
