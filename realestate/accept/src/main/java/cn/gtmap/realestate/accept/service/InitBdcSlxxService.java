package cn.gtmap.realestate.accept.service;



import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;



/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/5/30
 * @description
 */
public interface InitBdcSlxxService {

    /**
     * @param initSlxxQO 受理信息
     * @return 受理信息结果集
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化受理信息
     */
    BdcSlxxInitDTO initBdcSlxx(InitSlxxQO initSlxxQO);

}
