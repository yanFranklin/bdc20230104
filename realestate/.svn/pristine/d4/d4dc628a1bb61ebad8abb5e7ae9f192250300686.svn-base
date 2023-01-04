package cn.gtmap.realestate.init.core.mapper;


import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcDyaqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQlxxRequestDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 权利批量服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/13.
 * @description
 */
public interface BdcQlMapper {
    /**
     * 删除权利
     * @param tableName 权利表名
     * @param gzlslid 工作流实例ID
     * @return
     */
    void deleteQl(@Param("tableName")String tableName,@Param("gzlslid")String gzlslid);

    /**
     * 批量更新不动产权利
     * @param map
     * @return  更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新不动产权利
     */
    int updateBatchBdcQl(Map map);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    void updateCfbhPl(@Param("gzlslid") String gzlslid, @Param("cfbh") String cfbh);

    Map queryFdcqByZl(Map map);

    /**
     * 查询权利人项目信息
     * @param qlxx 查询参数
     * @return List<BdcQl> 权利信息
     */
    List<BdcXmDO> listBdcXmxxByQlr(BdcQlxxRequestDTO qlxx);
}
