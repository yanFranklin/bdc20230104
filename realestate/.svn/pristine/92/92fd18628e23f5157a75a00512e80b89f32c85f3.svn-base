package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/1/12
 * @description
 */
public interface BdcQlrMapper {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询权利人
      */
    List<BdcQlrDO> queryQlrList(Map paramMap);

    /**
     * 根据权利人名称获取权利人类型
     * @param qlrmc 权利人名称
     * @return List<BdcQlrDO>
     */
    Integer queryQlrlbByQlrmc(@Param("qlrmc") String qlrmc);
}
