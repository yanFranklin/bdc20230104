package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.dao.BdcConfigMapper;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.core.mapper.ydjbf.BdcEtlYdjbfConfigMapper;
import cn.gtmap.realestate.etl.core.mapper.ydjzh.BdcEtlYdjzhConfigMapper;
import cn.gtmap.realestate.etl.service.EtlConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/27
 * @description 配置服务
 */
@Service
public class EtlConfigServiceImpl implements EtlConfigService {

    @Autowired
    BdcEtlYdjbfConfigMapper bdcEtlYdjbfConfigMapper;

    @Autowired
    BdcEtlYdjzhConfigMapper bdcEtlYdjzhConfigMapper;

    @Override
    public List<Map> executeConfigSql(Map param){
        if(param.get("sjy") ==null){
            throw new AppException("执行配置SQL,未指定数据源");
        }
        String sjy =param.get("sjy").toString();
        if(StringUtils.equals(CommonConstantUtils.SJY_YDJBF,sjy)){
            return bdcEtlYdjbfConfigMapper.executeConfigSql(param);
        }
        return bdcEtlYdjzhConfigMapper.executeConfigSql(param);
    }
}
