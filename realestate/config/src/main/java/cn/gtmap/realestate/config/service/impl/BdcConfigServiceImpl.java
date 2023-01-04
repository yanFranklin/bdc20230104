package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.config.core.mapper.BdcSqlMapper;
import cn.gtmap.realestate.config.service.BdcConfigService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/20
 * @description
 */
@Service
public class BdcConfigServiceImpl implements BdcConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcConfigServiceImpl.class);

    @Autowired
    BdcSqlMapper bdcSqlMapper;

    @Override
    public List<Map> executeConfigSql(Map param){
        if (MapUtils.isEmpty(param)) {
            return Lists.newArrayList();
        }
        List<Map> configResult = null;

        String sql=String.valueOf(param.get("sql"));
        try {
            configResult = bdcSqlMapper.executeConfigSql(param);
        } catch (Exception e) {
            LOGGER.error("{}：配置子系统——执行配置SQL发生异常：{},sql：{}", e.getMessage(),sql,e.getMessage());
            throw new AppException("配置子系统——执行配置SQL发生异常！sql："+sql);
        }
        return configResult;

    }
}
