package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmQO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/08
 * @description 房产证明Mapper处理
 */
public interface BdcFczmMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @return {List} 下一手项目信息
     * @description 获取指定项目的下一手项目信息
     */
    List<BdcXmDO> getBdcXmNextXmDO(String xmid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param list 选择单元记录
     * @return {List} 匹配相关单元新想
     * @description 查询打印权属证明时勾选单元关联的匹配房产或土地单元信息（如果存在匹配）
     */
    List<BdcQszmQO> queryPpdyxx(List<BdcQszmQO> list);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {String} 建筑面积
     * @description 查询单元现势产权建筑面积
     */
    String queryBdcdyJzmj(@Param("bdcdyh")String bdcdyh);



    /**
     * @param xmidList
     * @param bxsghytList
     * @return xmid-ghyt
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmidList查询项目表的规划用途，如果规划用途是bxsghytList,查询房地产权表的用途名称
     */
    List<Map<String,String>> listXmidGhytMap(@Param("xmidList") List<String> xmidList, @Param("bxsghytList") List<String> bxsghytList);


}
