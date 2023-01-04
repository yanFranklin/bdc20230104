package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlYwlzDO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;

import java.util.List;

/**
 * @program: realestate
 * @description: 业务流转service服务层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 10:39
 **/
public interface BdcSlYwlzService extends InterfaceCode {

    /**
     * @param bdcSlYwlzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务流转信息
     * @date : 2021/9/22 11:10
     */
    List<BdcSlYwlzDO> listBdcSlYwlz(BdcSlYwlzDO bdcSlYwlzDO);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除业务流转信息
     * @date : 2021/9/22 11:11
     */
    int delteBdcSlYwlz(String gzlslid);
}
