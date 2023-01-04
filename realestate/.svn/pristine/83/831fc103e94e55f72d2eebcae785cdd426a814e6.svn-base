package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.qo.etl.HtbaspfQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0  2022/11/22
 * @description 非登记流程信息注销查询合同备案数据
 */
public interface BdcHtbaService {

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 合同备案分页查询
     */
    Page<HtbaspfQO> listHtbaByPageJson(Pageable pageable, String paramJson);

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 注销合同，更新数据
     */
    Integer updateHtzt(String baid, String tfyy);

}
