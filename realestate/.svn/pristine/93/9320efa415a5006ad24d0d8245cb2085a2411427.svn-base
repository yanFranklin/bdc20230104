package cn.gtmap.realestate.engine.service.impl.dataflow;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjllyEnum;
import cn.gtmap.realestate.engine.core.mapper.BdcGzSjlMapper;
import cn.gtmap.realestate.engine.service.BdcGzSjlService;
import cn.gtmap.realestate.engine.service.impl.dataflow.sql.SqlExpressionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/5
 * @description 不动产规则子规则 SQL数据流 处理逻辑
 */
@Service
public class BdcGzSjlSqlResolver implements BdcGzSjlService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzSjlSqlResolver.class);

    /**
     * 数据流处理Mapper
     */
    @Autowired
    private BdcGzSjlMapper bdcGzSjlMapper;

    /**
     * SQL处理工厂
     */
    @Autowired
    private SqlExpressionFactory sqlExpressionFactory;

    /**
     * 注册实例
     */
    @PostConstruct
    public void register() {
        BdcGzSjlResolverFactory.registerResolver(BdcGzSjllyEnum.SQL.getCode(), this);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO  数据流
     * @param sjlResultMap 数据流执行结果集
     * @return {Map} 数据流执行结果
     * @description 获取子规则数据流执行结果
     */
    @Override
    public <T> T getSjlExecuteResult(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap){
        if (null == bdcGzSjlDTO || StringUtils.isBlank(bdcGzSjlDTO.getSjnr())) {
            throw new AppException(ErrorCode.RULE_NULL_ERROR, "数据流SQL执行异常：数据流对象或者SQL内容为空");
        }

        String sqlExpression = this.getSqlExpression(bdcGzSjlDTO, sjlResultMap);
        return (T) bdcGzSjlMapper.executeSql(sqlExpression);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO  数据流
     * @param sjlResultMap 数据流执行结果集
     * @return {String} SQL语句
     * @description 获取待执行SQL语句内容
     *
     *   如果有参数需要有序遍历，依次替换SQL中占位变量
     *   例如 SELECT BDCDYH FROM BDC_XM WHERE XMID = #{1} AND ZL = #{2}
     */
    private <T> String getSqlExpression(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap) {
        String sqlExpression = bdcGzSjlDTO.getSjnr();

        // 参数列表为空则SQL没有参数
        if (CollectionUtils.isEmpty(bdcGzSjlDTO.getBdcGzSjlCsDOList())) {
            return sqlExpression;
        }

        // 需要依次替换所有的参数
        for (BdcGzSjlCsDO bdcGzSjlCsDO : bdcGzSjlDTO.getBdcGzSjlCsDOList()) {
            sqlExpression = sqlExpressionFactory.resolveSqlExpression(bdcGzSjlDTO.getGzid(), sqlExpression, bdcGzSjlCsDO, sjlResultMap);
        }

        LOGGER.debug("规则子系统数据流SQL执行内容：{}", sqlExpression);
        return sqlExpression;
    }
}
