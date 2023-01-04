package cn.gtmap.realestate.exchange.service.national;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

import java.util.List;

/**
 * 处理汇交数据model
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/10/31
 * @description 市级上报实现
 */
public interface CityAccesssModelHandlerService {


    /**
     * 市级上报（根据工作流实例ID进行上报），工作流事件
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param processInsId 工作流主键
     */
    void cityAccessByProcessInsId(String processInsId);

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据项目id单独上报
     * @Date 2022/5/5
     * @Param bdcXmDO
     * @Param bdcXmDOList
     **/
    void singleAccess(BdcXmDO bdcXmDO, List<BdcXmDO> bdcXmDOList);

}
