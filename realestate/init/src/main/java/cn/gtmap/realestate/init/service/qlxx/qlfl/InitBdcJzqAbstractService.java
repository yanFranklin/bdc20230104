package cn.gtmap.realestate.init.service.qlxx.qlfl;

import cn.gtmap.realestate.common.core.domain.BdcJzqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * 初始化居住权信息
 *
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/1/11
 * @description
 */
public abstract class InitBdcJzqAbstractService extends InitBdcQlDataAbstractService {

    /**
     * 房屋所有权人处理
     *
     * @param qlrList
     * @param bdcJzqDO
     * @return
     */
    public BdcJzqDO dealFwsyqr(List<BdcQlrDO> qlrList, BdcJzqDO bdcJzqDO) {
        //为空时处理
        if (CollectionUtils.isNotEmpty(qlrList) && bdcJzqDO != null && StringUtils.isBlank(bdcJzqDO.getFwsyqr())) {
            String fwsyqr = StringToolUtils.resolveBeanToAppendStr(qlrList, "getQlrmc", " ");
            bdcJzqDO.setFwsyqr(fwsyqr);
        }
        return bdcJzqDO;
    }

    /**
     * 居住权数据处理
     *
     * @param initServiceQO
     * @param bdcJzqDO
     * @return
     */
    public BdcJzqDO dealJzq(InitServiceQO initServiceQO, BdcJzqDO bdcJzqDO) {
        dealFwsyqr(initServiceQO.getBdcYwrList(), bdcJzqDO);
        return bdcJzqDO;
    }



}
