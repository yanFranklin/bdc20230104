package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @description 国家接入xml生成服务策略模式管理器
 */
public class NationalAccessXmlContext {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessXmlContext.class);

    @Autowired
    private BdcXmMapper bdcXmMapper;

    public NationalAccessXmlService getNationalAccessXmlServiceByDbConfig(BdcXmDO bdcXmDO) {
        NationalAccessXmlService nationalAccessXmlService = null;
        Map<String, Object> param = Maps.newHashMap();
        if (!ObjectUtils.isEmpty(bdcXmDO.getQllx())) {
            param.put("bdcqllxdm", bdcXmDO.getQllx());
        }
        if (!ObjectUtils.isEmpty(bdcXmDO.getDjxl())) {
            param.put("bdcsqlxdm", bdcXmDO.getDjxl());
        }
        if (!ObjectUtils.isEmpty(bdcXmDO.getBdcdyfwlx())) {
            param.put("sfdz", bdcXmDO.getBdcdyfwlx());
        }
        if (!ObjectUtils.isEmpty(bdcXmDO.getBdclx())) {
            param.put("bdclx", bdcXmDO.getBdclx());
        }
        Map<String, Object> bdcexchangeZdSqlxMap = bdcXmMapper.getBdcSubmitZdSqlx(param);
        if (ObjectUtils.isEmpty(bdcexchangeZdSqlxMap)) {
            LOGGER.error("未找到对应的服务");
        } else {
            if (MapUtils.isNotEmpty(bdcexchangeZdSqlxMap)) {
                nationalAccessXmlService = getNationNalServiceInstance(CommonUtil.formatEmptyValue(bdcexchangeZdSqlxMap.get("YWFWDM")));
            }
        }
        return nationalAccessXmlService;
    }


    /**
     * @description 实例化工厂
     */
    public NationalAccessXmlService getNationNalServiceInstance(String instanceName) {
        Object object = null;
        try {
            object = Container.getBean(instanceName);
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.error("没有找到策略对象：{}", e.toString());
        }
        if (null != object) {
            return (NationalAccessXmlService) object;
        } else {
            return null;
        }
    }
}
