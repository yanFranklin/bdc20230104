package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlYzSqlMapper;
import cn.gtmap.realestate.accept.service.BdcSlYzSqlService;
import cn.gtmap.realestate.common.core.ex.AppException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @注释 验证sql
 * @作者 gln
 * @创建日期 2019/5/27
 * @创建时间 16:56
 * @版本号 V 1.0
 */
@Service
public class BdcSlYzSqlServiceImpl implements BdcSlYzSqlService {
    @Autowired
    BdcSlYzSqlMapper bdcSlYzSqlMapper;

    /**
     * @param checkMap 验证的sql和参数
     * @return false/true
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据参数执行sql
     */
    @Override
    public boolean checkSql(Map<String, String> checkMap) {
        if (checkMap == null) {
            throw new AppException("验证项不可为空");
        }
        String sqls = checkMap.get("sqls");
        String cs = checkMap.get("cs");
        if (StringUtils.isBlank(sqls) || StringUtils.isBlank(cs)) {
            throw new AppException("验证的sql或者测试数据不能为空");
        }
        List<String> checkValueList = Lists.newArrayList(cs.split("[;；]"));
        List<String> sqlList = Lists.newArrayList(sqls.split("[;；]"));
        Map<String, String> params = Maps.newHashMap();
        checkValueList.forEach(check -> {
            String[] checks = check.split("=");
            params.put(checks[0], checks[1]);
        });
        if (CollectionUtils.isEmpty(sqlList) || MapUtils.isEmpty(params)) {
            throw new AppException("需要验证的配置sql不能为空！");
        }
        sqlList.forEach(sql -> {
            params.put("sql", sql);
            bdcSlYzSqlMapper.runConfigSql(params);
        });
        return true;
    }
}
