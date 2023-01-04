package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcCdxxDO;

/**
 * @program: realestate
 * @description: 查档信息查询服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 17:20
 **/
public interface BdcCdxxService {

    /**
     * @param bdcCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查档信息
     * @date : 2020/9/23 17:21
     */
    BdcCdxxDO queryBdcCdxx(BdcCdxxDO bdcCdxxDO);

    /**
     * @param bdcCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存查档信息数据
     * @date : 2020/9/23 17:29
     */
    BdcCdxxDO saveBdcCdxx(BdcCdxxDO bdcCdxxDO);

    /**
     * @param cdxxid,xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除查档信息
     * @date : 2020/9/23 17:37
     */
    int deleteBdcCdxx(String cdxxid,String xmid);
}
