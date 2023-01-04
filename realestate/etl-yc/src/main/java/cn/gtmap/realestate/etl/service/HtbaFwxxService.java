package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaFwxxDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 合同备案房屋信息service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-16 10:29
 **/
public interface HtbaFwxxService {

    /**
     * @param fwHsDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋户室信息到合同备案房屋信息
     * @date : 2020/12/16 10:30
     */
    int saveHtbaFwxxFromFwhs(FwHsDO fwHsDO, String baid);

    /**
     * @param fwids
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合同备案关联附属设施
     * @date : 2020/12/17 13:57
     */
    int glHtbaHsxx(List<String> fwids, String baid);


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据备案id查询房屋信息
     * @date : 2020/12/18 11:15
     */
    List<HtbaFwxxDO> listHtbaFwxxByBaid(String baid);

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋信息
     * @date : 2021/3/8 20:38
     */
    void saveHtbaFwxxDO(HtbaFwxxDO htbaFwxxDO);


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除合同备案房屋信息
     * @date : 2021/4/14 11:05
     */
    void deleteHtbaxx(String baid);
}
