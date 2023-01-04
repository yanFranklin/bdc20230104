package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcLzxxDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/2/13
 * @description
 */
public interface BdcLzrMapper {
    /**
     * @param zsid 证书ID
     * @return List<BdcLzrDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书相关的所有项目在受理保存的领证人信息
     */
    List<BdcLzrDO> getAllZsXmLzrByZsid(String zsid);


    /**
     * 批量更新不动产领证人
     *
     * @param map
     * @return 更新数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量更新不动产领证人
     */
    int updateBatchBdcLzr(Map map);

    /**
     * 删除权利人
     *
     * @param paramMap
     * @return
     */
    void deleteLzr(Map paramMap);

    /**
     * 删除权利人ID非空的领证人
     *
     * @param paramMap
     * @return
     */
    int deleteQlrLzr(Map paramMap);

    /**
     * 查询lzfs
     *
     * @param paramMap
     * @return
     */
    Integer getLzfsByGzlslid(Map paramMap);

    /**
     * 查询lzfs
     *
     * @param gzlslid
     * @return
     */
    List<BdcLzxxDTO> getAllLzfsByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * 查询lzfs
     *
     * @param zsid
     * @return
     */
    BdcLzxxDTO getLzfsByZsid(@Param("zsid") String zsid);

    List<BdcLzrDO> getBdcLzrDOByXm(@Param("bdcXmDOList") List<BdcXmDO> bdcXmDOList);

    List<BdcLzrDO> getBdcLzrDOByGzlslid(@Param("gzlslid") String gzlslid);
}
