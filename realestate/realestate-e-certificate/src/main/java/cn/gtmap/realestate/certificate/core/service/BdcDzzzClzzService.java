package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzClzzDO;


/**
 * 存量电子证照信息管理
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
public interface BdcDzzzClzzService {

    // 插入或更新存量电子证照信息
    int saveOrUpdateClzz(BdcDzzzClzzDO bdcDzzzClzzDO);
}
