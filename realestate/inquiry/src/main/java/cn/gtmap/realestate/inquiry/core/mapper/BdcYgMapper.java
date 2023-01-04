package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn>zhongjinpeng</a>"
 * @version 1.0, 2020/6/22
 * @description 不动产查封信息
 */
public interface BdcYgMapper {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询查封信息
     */
    List<BdcYgDO> listBdcYgByBdcdyhs(@Param("list") List<String> bdcdyhList);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param djh 地籍号
     * @description 根据地籍号查询预告
     */
    List<BdcYgDO> listBdcYgByDjh(@Param("djh") String djh);
}
