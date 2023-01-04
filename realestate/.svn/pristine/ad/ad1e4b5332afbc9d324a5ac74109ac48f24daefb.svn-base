package cn.gtmap.realestate.engine.service.impl.dataflow.sql;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjllyEnum;
import cn.gtmap.realestate.engine.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流SQL解析类：动参
 */
@Service
public class SqlExpDCResolver implements SqlExpression {
    /**
     * 数据处理
     */
    @Autowired
    private DataUtil dataUtil;

    /**
     * 注册动参处理类
     */
    @PostConstruct
    public void register(){
        SqlExpressionFactory.registerSqlExpReolver(BdcGzSjlCsEnum.DC.getCode(), this);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid 规则ID
     * @param sql  SQL内容
     * @param bdcGzSjlCsDO 数据流参数信息实体
     * @param dataResult 数据流执行结果（当前数据流有可能依赖上一个结果）
     * @description
     *       解析SQL内容，替换参数占位内容
     */
    @Override
    public String resolveSqlExpression(String gzid, String sql, BdcGzSjlCsDO bdcGzSjlCsDO, Map dataResult) {
        String val = dataUtil.getParamValue(dataResult, bdcGzSjlCsDO.getSjlcsmc(), BdcGzSjllyEnum.SQL.getCode());
        return sql.replaceFirst("\\#\\{" + bdcGzSjlCsDO.getSjlcsxh() + "\\}", val);
    }
}
