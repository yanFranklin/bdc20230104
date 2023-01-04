package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.accept.BdcMjDTO;


/**
 * @program: realestate
 * @description: 受理计算面积服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-19 13:57
 **/
public interface BdcSlCountService {

    /**
     * @program: realestate
     * @description: 受理计算宗地宗海面积，定着物面积服务
     * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @create: 2019-06-19 13:57
     **/
    BdcMjDTO countMj(String gzlslid);
}
