package cn.gtmap.realestate.accept.core.mapper;


import cn.gtmap.realestate.common.core.domain.accept.BdcLcTsjfGxDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/12/2
 * @description 不动产流程推送缴费关系信息
 */
public interface BdcLcTsjfGxMapper {

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例id查询一条流程与推送缴费的关系
     */
    BdcLcTsjfGxDO queryOneLcTsjfGx(@Param("gzlslid")String gzlslid);

    /**
     * 清除推送ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void clearTsid(@Param("gzlslid")String gzlslid);

}
