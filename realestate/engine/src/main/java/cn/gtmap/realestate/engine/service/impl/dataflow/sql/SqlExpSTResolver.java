package cn.gtmap.realestate.engine.service.impl.dataflow.sql;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流SQL解析类：实体
 */
@Service
public class SqlExpSTResolver implements SqlExpression {
    /**
     * 注册实体处理类
     */
    @PostConstruct
    public void register(){
        SqlExpressionFactory.registerSqlExpReolver(BdcGzSjlCsEnum.ST.getCode(), this);
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
        // SQL 不支持实体类型参数
        throw new AppException(ErrorCode.RULE_PARAM_SET_EX, "子规则（" + gzid + "）数据流SQL参数格式配置错误");
    }
}
