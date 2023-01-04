package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.vo.accept.ui.BdcBgxxDbVO;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/21.
 * @description 变更信息对比接口服务
 */
public interface BdcBgxxDbService {

    /**
     * 根据工作流实例ID,获取业务信息并存至ES中
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void addBdcYwxxToEs(String gzlslid);

    /**
     * 根据xmid获取不动产业务信息，包含项目、权利、权利人信息
     * @param xmid 项目ID
     * @param gzlslid 工作流实例ID
     * @return 变更信息VO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcBgxxDbVO> getBdcYwxx(String xmid, String gzlslid);

    /**
     * 比对变更前后的业务信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 比对结果信息
     */
    Object compareYwxx(String gzlslid);

}
