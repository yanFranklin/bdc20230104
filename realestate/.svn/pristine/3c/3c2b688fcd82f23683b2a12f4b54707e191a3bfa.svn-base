package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSysxxDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/1/15
 * @description 受理核税信息
 */
public interface BdcSlHsxxMapper {

    /**
     * @param map 参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新完税状态
     */
    void updateBatchWszt(Map map);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人类别更新完税状态
     * @date : 2022/9/21 16:36
     */
    void updateWsztByqlrlb(Map map);

    /**
     * 批量更新不动产核税信息
     *
     * @param map (可更新的参数有：jypzh、ytsswzt、yhjkrkzt)
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void batchUpdateBdcSlHsxx(Map map);

    /**
     * 根据工作流实例ID获取核税信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     */
    List<BdcSlHsxxDO> listBdcSlHsxxByGzlslid(@Param("gzlslid") String gzlslid,@Param("slbh") String slbh);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    List<BdcSlSysxxDO> listBdcSlSysxx(@Param("hsxxid")String hsxxid,@Param("slbh") String slbh, @Param("gzlslid") String gzlslid);

    /**
     * 根据工作流实例ID获取核税信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param gzlslid 工作流实例ID
     */
    List<BdcSlHsxxDO> listBdcSlHsxxByGzlslidAndSqrlb(@Param("gzlslid") String gzlslid, @Param("sqrlb") String sqrlb);
}
