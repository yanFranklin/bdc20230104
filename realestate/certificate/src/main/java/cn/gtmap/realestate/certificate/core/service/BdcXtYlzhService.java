package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXtYlzhDO;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 预留证号操作Service接口
 */
public interface BdcXtYlzhService {
    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   zsid 证书ID
     * @return  {BdcXtYlzhDO} 预留证号
     * @description  获取证书对应的预留证号
     */
    BdcXtYlzhDO queryBdcXtYlzh(String zsid);

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcXtYlzhDO 预留证号信息实体
     * @return  {int} 更新记录条数
     * @description 更新预留证号使用情况
     */
    int updateBdcXtYlzhSyqk(BdcXtYlzhDO bdcXtYlzhDO);
}
