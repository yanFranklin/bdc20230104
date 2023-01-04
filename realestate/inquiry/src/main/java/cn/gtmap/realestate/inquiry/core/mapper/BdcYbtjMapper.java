package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXdryxxDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYbtjDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXdryQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYbtjQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn>wutao</a>"
 * @version 1.0, 2020/6/22
 * @description 不动产月报统计信息
 */
public interface BdcYbtjMapper {

    /**
     * @param
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 不动产业务统计
     * @date : 2021/8/3 13:43
     */
    List<BdcYbtjDTO> listBdcywtj(BdcYbtjQO bdcYbtjQO);

    /**
     * @param
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 不动产业务统计
     * @date : 2021/8/3 13:43
     */
    Integer countBdcZs(BdcYbtjQO bdcYbtjQO);

    /**
     * @param
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 不动产业务统计
     * @date : 2021/8/3 13:43
     */
    Integer countBdcDzzz(BdcYbtjQO bdcYbtjQO);

    /**
     * @param
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 业务占比统计：首次登记
     * @date : 2021/8/3 13:43
     */
    Integer countYwzbScdj(BdcYbtjQO bdcYbtjQO);

    /**
     * @param
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 业务占比统计
     * @date : 2021/8/3 13:43
     */
    Integer countYwzb(BdcYbtjQO bdcYbtjQO);
}
