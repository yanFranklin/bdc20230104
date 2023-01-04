package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
public interface BdcXmFbMapper {
    /**
     * 批量更新不动产项目附表信息
     *
     * @param map
     * @return
     */
    int updateBatchBdcXmFb(Map map);

    /**
     * 查询不动产项目附表
     *
     * @param bdcXmFbQO
     * @return
     */
    List<BdcXmFbDO> listBdcXmFb(BdcXmFbQO bdcXmFbQO);

    /**
     * 通过不动产单元查询现势的sfszfgf状态
     *
     * @param map
     * @return
     */
    List<BdcXmDO> listBdcFgfXmByBdcdyh(Map map);

    /**
     * 批量更新，不存在则插入
     * @param bdcXmFbDOS
     */
    void batchInsertMerge(List<BdcXmFbDO> bdcXmFbDOS);

    /**
     * 根据业务信息查询关联权籍管理代码
     * @param bdcQjgldmQO 登记业务数据（例如单元号、证号等）
     * @return 权籍管理代码
     */
    String queryQjgldm(BdcQjgldmQO bdcQjgldmQO);
}
