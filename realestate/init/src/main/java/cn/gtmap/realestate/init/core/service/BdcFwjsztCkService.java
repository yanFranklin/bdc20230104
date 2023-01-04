package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFwjsztckDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 房屋建设状态查看Service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 14:00
 **/
public interface BdcFwjsztCkService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房屋建设状态查看信息
     * @date : 2021/7/20 14:03
     */
    List<BdcFwjsztckDO> queryBdcFwjszt(String gzlslid);

    /**
     * @param bdcFwjsztckDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增房屋建设状态查看信息
     * @date : 2021/7/20 14:09
     */
    BdcFwjsztckDO insertFwjsztCk(BdcFwjsztckDO bdcFwjsztckDO);
}
