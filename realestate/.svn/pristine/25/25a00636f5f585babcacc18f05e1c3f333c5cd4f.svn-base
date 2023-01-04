package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/17
 * @description 受理项目历史关系
 */
@Repository
public interface BdcSlXmLsgxMapper {

    /**
     * @param map 参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据基本信息ID删除不动产受理项目历史关系
     */
    void deleteBdcSlXmLsgx(Map map);

    /**
     * @param map
     * @return 不动产受理项目上下手关系
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description  根据gzlslid获取不动产受理上下手关系数据
     */
    List<Map> sxxBdcXx(Map map);

    /**
     * @param bdcSlDeleteCsDTO 受理删除参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据参数批量删除受理抵押信息
     */
    void batchDeleteBdcSlXmLsgx(BdcSlDeleteCsDTO bdcSlDeleteCsDTO);

    /**
     * @param yxmidList 原项目ID集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据原项目ID删除项目历史关系
     */
    void deleteBdcSlXmlsgxByYxmid(@Param("yxmidList") List<String> yxmidList);

    /**
     * @param xmidList 项目ID集合 不能为空
     * @param sfwlzs 是否外联证书
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID集合获取受理项目历史关系
     */
    List<BdcSlXmLsgxDO> listSlXmLsgxPl(@Param("xmidList") List<String> xmidList,@Param("sfwlzs") Integer sfwlzs);
}
