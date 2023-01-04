package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.formclient.common.dto.FormElementConfigDTO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.portal.BdcBtxyzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcSjyPzFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.service.BdcCheckValidSqlConditionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-10-15
 * @description 必填项验证
 */
public abstract class BdcBtxYzAbstractService implements BdcCheckValidSqlConditionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBtxYzAbstractService.class);
    private final Pattern pattern = Pattern.compile("\\#\\{[a-zA-Z]+\\}");
    @Autowired
    BdcSjyPzFeignService bdcSjyPzFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * @param resultList requiredElements
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 查询结果为空的字段
     */
    @Override
    public Set<String> addNullColumn(List<Map<String, Object>> resultList, List<FormElementConfigDTO> requiredElements, Set<String> columnSet) {
        /**
         * 查询结果为空，全部字段
         */
        if (CollectionUtils.isEmpty(resultList)) {
            return columnSet;
        }
        return forEachResultAddNullColumn(resultList, columnSet);
    }

    /**
     * @param resultList
     * @param requiredElements
     * @param columnSet
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 批量查询结果为空的字段
     */
    @Override
    public Set<String> addNullColumnPl(List<Map<String, Object>> resultList, List<FormElementConfigDTO> requiredElements, Set<String> columnSet) {
        /**
         * 查询结果为空
         */
        if (CollectionUtils.isEmpty(resultList)) {
            return Sets.newHashSet();
        }
        return forEachResultAddNullColumn(resultList, columnSet);
    }

    /**
     * @param resultList
     * @param columnSet
     * @return Set<String>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询每个结果map的空值
     */
    private Set<String> forEachResultAddNullColumn(List<Map<String, Object>> resultList, Set<String> columnSet) {
        Set<String> result = Sets.newHashSet();
        for (Map<String, Object> map : resultList) {
            if (MapUtils.isNotEmpty(map)) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == null) {
                        result.add(StringUtils.lowerCase(key));
                    }
                }
            } else {
                result.addAll(columnSet);
            }
        }
        return result;
    }

    /**
     * @param sql columnNameSet
     * @return Set<String>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sql 中的查询字段名称
     */
    @Override
    public Set<String> getColumnName(String sql, Set<String> columnNameSet) {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        String newSql = sql;
        try {
            Matcher matcher = pattern.matcher(sql);
            String matchStr = null;
            String condition = null;
            while (matcher.find()) {
                matchStr = matcher.group();
                condition = matchStr.substring(2, matchStr.length() - 1);
                newSql = sql.replaceAll("\\#\\{" + condition + "\\}", "a ");
            }
            LOGGER.info("验证的sql:{}", newSql);
            Select select = (Select) pm.parse(new StringReader(newSql));
            PlainSelect selectBody = (PlainSelect) select.getSelectBody();
            List<SelectItem> selectItemList = selectBody.getSelectItems();
            for (SelectItem item : selectItemList) {
                Expression expression = ((SelectExpressionItem) item).getExpression();
                Alias alias = ((SelectExpressionItem) item).getAlias();
                String aliasName = null;
                if (alias != null && StringUtils.isNotBlank(aliasName = alias.getName())) {
                    columnNameSet.add(StringUtils.lowerCase(aliasName.replace("\"", "")));
                }
                if (StringUtils.isBlank(aliasName) && expression instanceof Column) {
                    String columnName = ((Column) expression).getColumnName();
                    columnNameSet.add(StringUtils.lowerCase(columnName.replace("\"", "")));
                }
            }
        } catch (JSQLParserException e) {
            LOGGER.error(e.getMessage());
        }
        return columnNameSet;

    }

    /**
     * @param bdcBtxyzDTO sql
     * @return  List<Map<String, Object>>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询 配置sql结果
     */
    public List<Map<String, Object>> querySqlPl(BdcBtxyzDTO bdcBtxyzDTO, String sql) {
        Map param = Maps.newHashMap();
        param.put("sql", sql);
        param.put("gzlslid", bdcBtxyzDTO.getGzlslid());
        param.put("taskId", bdcBtxyzDTO.getTaskId());
        List<Map<String, Object>> resultList = bdcSjyPzFeignService.yxPzSjy(param);
        if (CollectionUtils.size(resultList) > 100000) {
            resultList.clear();
            throw new AppException("yxPzSjy方法查询结果集过多，可能导致内存溢出！");
        }
        return resultList;
    }

    /**
     * @param bdcBtxyzDTO
     * @return List<BdcXmDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取项目信息
     */
    public List<BdcXmDO> queryBdcXmList(BdcBtxyzDTO bdcBtxyzDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcBtxyzDTO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return Lists.newArrayList();
        }
        boolean shxx = StringUtils.startsWith(bdcBtxyzDTO.getFormName(), CommonConstantUtils.FORM_NAME_SH);
        if (shxx) {
            bdcXmDOList = bdcXmDOList.subList(0, 1);
        }
        return bdcXmDOList;
    }
}
