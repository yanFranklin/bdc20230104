package cn.gtmap.realestate.common.util.configuration.service;

import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.configuration.IBdcQjgldmQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/08/19
 * @description 权籍管理代码登记库查询类
 */
@Order(1)
@Service
public class BdcQjgldmDjQuerier implements IBdcQjgldmQuery {
    private static Logger logger = LoggerFactory.getLogger(BdcQjgldmDjQuerier.class);

    @Autowired
    private BdcXmfbFeignService XmfbFeignService;


    /**
     * 从登记库查询权籍管理代码
     * @param configName 当前配置项内容
     * @param bdcQjgldmQO 业务数据
     * @return {String} 权籍管理代码
     */
    @Override
    public String queryBdcQjgldm(String configName, BdcQjgldmQO bdcQjgldmQO) {
        if(!CheckParameter.checkAnyParameter(bdcQjgldmQO)) {
            return null;
        }

        // 如果有业务相关关键字段数据，例如 bdcdyh、bdcqzh，则查询登记库权籍管理代码
        String qjgldm = XmfbFeignService.queryQjgldm(bdcQjgldmQO);
        logger.info("获取指定区划配置项{}值，从登记业务库查询数据[{}]对应权籍管理代码{}", configName, bdcQjgldmQO, qjgldm);
        return qjgldm;
    }
}
