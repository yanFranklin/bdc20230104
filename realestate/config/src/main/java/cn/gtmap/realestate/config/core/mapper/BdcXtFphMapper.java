package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.dto.config.BdcFphSymxDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcFphQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description 发票号
 */
@Repository
public interface BdcXtFphMapper {
    /**
     * @param bdcFphQO 发票号模板
     * @return {int} 发票号数量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询指定区间发票号数量
     */
    int countYzh(BdcFphQO bdcFphQO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取发票号
     */
    List<BdcFphDO> listBdcXtFph(BdcFphQO bdcFphQO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改发票号状态
     */
    int updateBdcFphSyzt(BdcFphDO bdcFphDO);

    /**
     * @param fphid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取发票号使用明细
     * @date : 2020/11/26 18:32
     */
    List<BdcFphSymxDTO> listFphSymx(String fphid);
}
