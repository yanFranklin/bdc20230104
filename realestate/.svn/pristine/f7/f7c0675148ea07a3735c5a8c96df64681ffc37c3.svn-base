package cn.gtmap.realestate.init.service.tshz.impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzAbstractService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 初始化特殊服务实现
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/07/30.
 * @description
 */
@Service
public class InitBdcTsHzServiceImpl extends InitBdcTsHzAbstractService {

    @Override
    public String getVal() {
        return Constants.DEFAULT;
    }
    @Autowired
    private InitBeanFactory initBeanFactory;

    @Override
    public InitServiceDTO init(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //不为空对已初始化数据做特殊处理
        if (initServiceDTO != null && CollectionUtils.isNotEmpty(initBeanFactory.getTshzServices())) {
            for(String beanName:initBeanFactory.getTshzServices()){
                try {
                    InitBdcTsHzService initBdcTsHzService= (InitBdcTsHzService) Container.getBean(beanName);
                    initBdcTsHzService.tshz(initServiceQO,initServiceDTO);
                }catch (NoSuchBeanDefinitionException e){
                    throw  new AppException("特殊服务中不存在此配置："+beanName);
                }
            }
        }
        return initServiceDTO;
    }
}
