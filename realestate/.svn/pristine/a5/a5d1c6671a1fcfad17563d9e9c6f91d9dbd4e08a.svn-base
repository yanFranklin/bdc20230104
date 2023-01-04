package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/1/7
 * @description 受理家庭成员
 */
public interface BdcSlJtcyMapper {

    /**
     * @param bdcSlJtcyQO 受理家庭成员查询QO对象
     * @return 家庭成员信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询家庭成员信息
     */
    List<BdcSlJtcyDO> listBdcSlJtcy(BdcSlJtcyQO bdcSlJtcyQO);

    /**
     * @param zjh 证件号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据申请人ID集合和证件号批量删除不动产受理家庭成员
     */
    void deleteBatchBdcSlJtcy(@Param("sqridList")List<String> sqridList, @Param("zjh")String zjh);

    /**
     * @param map 参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除申请人
     */
    void delBatchJtcyxx(Map map);


}
