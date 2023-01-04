package cn.gtmap.realestate.exchange.service;

import cn.gtmap.realestate.common.core.domain.exchange.BdcXtJrDO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcXtJrQO;

import java.util.List;

/**
 * @program: realestate
 * @description: 系统接入配置服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-07 09:30
 **/
public interface BdcXtJrService {

    /**
     * @param bdcXtJrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入配置
     * @date : 2022/7/7 9:31
     */
    List<BdcXtJrDO> listBdcXtJr(BdcXtJrQO bdcXtJrQO);

    /**
     * @param id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据id删除接入信息
     * @date : 2022/7/7 19:39
     */
    int deleteBdcXtJr(String id);

    /**
     * @param ywfwdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入业务代码
     * @date : 2022/7/8 11:25
     */
    String queryYwdm(String ywfwdm);
}
