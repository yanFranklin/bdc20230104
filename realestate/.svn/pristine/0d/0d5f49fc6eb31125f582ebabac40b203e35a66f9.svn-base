package cn.gtmap.realestate.engine.service.impl.dataflow.sql;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流SQL解析类：入参
 */
@Service
public class SqlExpRCResolver implements SqlExpression {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlExpRCResolver.class);

    /**
     * 规则ID参数名称
     */
    private static final String GZID = "gzid";

    /**
     * 注册入参处理类
     */
    @PostConstruct
    public void register(){
        SqlExpressionFactory.registerSqlExpReolver(BdcGzSjlCsEnum.RC.getCode(), this);
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
        String xh = bdcGzSjlCsDO.getSjlcsxh();
        Object csz = bdcGzSjlCsDO.getSjlcsz();


        // 参数为 gzid 程序直接替换设值
        if (StringUtils.equals(GZID, bdcGzSjlCsDO.getSjlcsmc()) && sql.contains("&{" + xh  + "}")) {
            return sql.replaceFirst("\\&\\{" + xh + "\\}", "'" + gzid + "'");
        }

        // 其它入参（参数值为空的时候设置默认值保证SQL的正常执行，规则例外时候需要）
        String value = CommonConstantUtils.BDC_GZ_SJL_RC_MRZ;
        if (null != csz && StringUtils.isNotBlank(String.valueOf(csz))) {
            value = String.valueOf(csz);
        }

        if(StringUtils.equals(CommonConstantUtils.BDC_GZ_SJL_RC_MRZ, value)) {
            LOGGER.debug("子规则：{} 数据流SQL：[{}] 第{}个参数验证时没有传值", gzid, sql, xh);
        }

        return sql.replaceFirst("\\#\\{" + xh + "\\}", "'" + value + "'");
    }
}
