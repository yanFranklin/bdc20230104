package cn.gtmap.realestate.common.util.configuration.service;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJsPzFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.IBdcQjgldmQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/08/19
 * @description 权籍管理代码用户查询类
 */
@Order(3)
@Service
public class BdcQjgldmYhQuerier implements IBdcQjgldmQuery {
    private static Logger logger = LoggerFactory.getLogger(BdcQjgldmYhQuerier.class);

    @Autowired
    private UserManagerUtils userManager;
    @Autowired
    private BdcJsPzFeignService jsPzFeignService;

    /**
     * 根据当前用户判断权籍管理代码
     * @param configName 当前配置项内容
     * @param bdcQjgldmQO 业务数据
     * @return {String} 权籍管理代码
     */
    @Override
    public String queryBdcQjgldm(String configName, BdcQjgldmQO bdcQjgldmQO) {
        String qjgldm = null;

        UserDto userDto = userManager.getCurrentUser();
        if(null != userDto && StringUtils.isNotBlank(userDto.getId())) {
            qjgldm = jsPzFeignService.listMostQjgldmByUserId(userDto.getId());
        }

        logger.info("获取指定区划配置项{}值，根据当前登录用户[{}]查询到对应权籍管理代码{}", configName,userDto, qjgldm);
        return qjgldm;
    }
}
