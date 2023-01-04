package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.register.core.mapper.BdcRegisterConfigMapper;
import cn.gtmap.realestate.register.service.BdcRegisterConfigService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/9
 * @description 执行sql业务类
 */
@Service
public class BdcRegisterConfigServiceImpl implements BdcRegisterConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcRegisterConfigServiceImpl.class);
    @Autowired
    BdcRegisterConfigMapper bdcRegisterConfigMapper;

    /**
     * @param param@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 执行 配置 sql
     */
    @Override
    public List<Map> executeConfigSql(Map param) {
        if (MapUtils.isEmpty(param)) {
            return Lists.newArrayList();
        }
        List<Map> configResult = null;
        /**
         * 表单名称
         */
        String formName=String.valueOf(param.get("formName"));
        String sql=String.valueOf(param.get("sql"));
        try {
            configResult = bdcRegisterConfigMapper.executeConfigSql(param);
        } catch (Exception e) {
            LOGGER.error("{}：审核登簿子系统——执行配置SQL发生异常：{}", "表单名称："+formName+" sql："+sql, e.getMessage());
            throw new AppException("审核登簿子系统——执行配置SQL发生异常！表单名称："+formName +" sql："+sql);
        }
        return configResult;
    }
}
