package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/12
 * @Description: 补录信息服务
 */
public interface BdcSlCdBlxxService {
    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 查询补录信息
     * @date : 2021/08/12
     */
    BdcCdBlxxDO queryBdcBlxx(BdcCdBlxxDO bdcCdBlxxDO);

    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 保存补录信息数据
     * @date : 2021/08/12
     */
    BdcCdBlxxDO saveBdcBlxx(BdcCdBlxxDO bdcCdBlxxDO);

    /**
     * @param blxxid
     * @param xmid
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 删除补录信息
     * @date : 2021/08/12
     */
    int deleteBdcBlxx(String blxxid,String xmid);
}
