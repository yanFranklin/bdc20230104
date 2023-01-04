package cn.gtmap.realestate.register.service.impl.dyConfig;


import cn.gtmap.realestate.common.core.enums.DbSourceEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcDyConfigService;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingConfigFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlPrintRestService;
import cn.gtmap.realestate.register.core.mapper.BdcRegisterConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/3/30
 * @description 打印配置实现类
 */
@Service("bdcDyConfigServiceImpl")
public class BdcDyConfigServiceImpl implements BdcDyConfigService {
    @Autowired
    private BdcRegisterConfigMapper bdcRegisterConfigMapper;

    @Autowired
    private BdcSlPrintRestService bdcSlPrintRestService;

    @Autowired
    private BuildingConfigFeignService buildingConfigFeignService;
    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param configParam 配置参数
     * @param dbsource 数据库源
     * @return List<Map> 执行结果
     * @description  根据数据库源执行执行打印
     */
    @Override
    public List<Map> executeConfigSql(Map configParam, String dbsource) {
        if (StringUtils.isBlank(dbsource)){
            throw new MissingArgumentException("缺失数据库配置信息！");
        }
        if (DbSourceEnum.Bdcdj.getName().equals(dbsource)){
            return bdcRegisterConfigMapper.executeConfigSql(configParam);
        }
        if (DbSourceEnum.Bdcsl.getName().equals(dbsource)){
            return bdcSlPrintRestService.executeConfigSql(configParam);
        }
        if (DbSourceEnum.Bdcqj.getName().equals(dbsource)){
            return buildingConfigFeignService.executeConfigSql(configParam);
        }
        return null;
    }
}
