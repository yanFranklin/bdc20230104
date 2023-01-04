package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcPjdxDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/6/25 14:32
 * @description
 */
public interface BdcPjdxMapper{

    List<BdcPjdxDO> queryPjdxMsg(@Param("processInsId") String processInsId);
}
