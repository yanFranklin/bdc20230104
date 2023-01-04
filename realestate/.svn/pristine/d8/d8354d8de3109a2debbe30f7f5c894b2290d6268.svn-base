package cn.gtmap.realestate.common.core.service.Impl;

import cn.gtmap.realestate.common.core.dao.BdcConfigMapper;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.enums.BdcDyBeanEnum;
import cn.gtmap.realestate.common.core.enums.BdcDySjlyEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcDyConfigService;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import com.google.common.collect.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/11
 * @description fr3 打印获取配置结果集业务实现类
 */
@Service
public class BdcDysjPzServiceImpl implements BdcDysjPzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDysjPzServiceImpl.class);

    private final Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]+\\}");

    @Autowired
    BeansResolveUtils beansResolveUtils;

    @Autowired
    RestRpcUtils restRpcUtils;


    /**
     * @param configParam
     * @return 查询data 数据
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取主表配置结果集
     */
    @Override
    public Map queryPrintDatasList(Map configParam, String configBeanName, List<BdcDysjPzDO> bdcDysjPzDOList) {
        if (StringUtils.isBlank(configBeanName)) {
            throw new AppException("执行配置sql的bean不能为空");
        }
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
            throw new AppException("打印主表配置不能为空");
        }
        List<Map> dataList = Lists.newArrayList();
        bdcDysjPzDOList.forEach(data -> {
            List<String> csList = Lists.newArrayList(data.getCs().split(","));
            csList.forEach(cs -> {
                if (configParam.get(cs) == null) {
                    throw new MissingArgumentException("代码缺失打印配置的参数:{}" + cs);
                }
            });

            if(BdcDySjlyEnum.FW.getCode().equals(data.getSjly())){
                // 服务方式 added by zhuyong 20200310
                this.getResultByGetReuqest(data.getQqyy(), data.getDysjy(), configParam, dataList);
            } else {
                // SQL方式
                /**
                 * 多条sql以英文分号相隔
                 */
                List<String> sqlList = Lists.newArrayList(data.getDysjy().split(CommonConstantUtils.ZF_YW_FH));
                sqlList.forEach(sql -> {
                    /**
                     * added by cyc 主表这边也要匹配${param}这种类型的参数
                     * 如果是这种类型的参数，则直接替换
                     * 用于解决 in （‘param1’,'param2'）
                     */
                    try{
                        Matcher matcher = pattern.matcher(sql);
                        String matchStr = null;
                        String condition = null;
                        Object value = null;
                        while (matcher.find()) {
                            matchStr = matcher.group();
                            condition = matchStr.substring(2, matchStr.length() - 1);
                            value = configParam.get(condition);
                            if (value == null) {
                                throw new MissingArgumentException(condition);
                            }
                            sql = sql.replaceAll("\\$\\{" + condition + "\\}", value.toString());
                        }
                    }catch (Exception e){
                        LOGGER.error("打印主表替换${param}类参数时发证异常，请联系研发！");
                    }

                    configParam.put("sql", sql);
                    List<Map> configList;
                    if (BdcDyBeanEnum.BdcDyConfigService.getBeanName().equals(configBeanName)) {
                        BdcDyConfigService bdcDyConfigService = beansResolveUtils.getBeanByName(configBeanName);
                        configList = bdcDyConfigService.executeConfigSql(configParam,data.getDbsource());
                    }else {
                        BdcConfigMapper bdcConfigMapper = beansResolveUtils.getBeanByName(configBeanName);
                        configList = bdcConfigMapper.executeConfigSql(configParam);
                    }
                    if (CollectionUtils.isNotEmpty(configList)) {
                        dataList.addAll(configList);
                    }
                });
            }
        });
        Map map = Maps.newHashMap();
        dataList.forEach(data -> {
            if (null != data) {
                map.putAll(data);
            }
        });
        return map;
    }

    /**
     * @param configParam
     * @return Multimap
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据配置sql查询detail数据
     */
    @Override
    public Multimap queryPrintDetailList(Map configParam, String configBeanName, List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        if (StringUtils.isBlank(configBeanName)) {
            throw new MissingArgumentException("执行配置sql的bean不能为空");
        }
        if (CollectionUtils.isEmpty(bdcDysjZbPzDOList)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("打印子表配置为空");
            }
            return ArrayListMultimap.create();
        }
        Multimap<String, List> multimap = ArrayListMultimap.create();
        bdcDysjZbPzDOList.forEach(detail -> {
            List<String> csList = Lists.newArrayList(detail.getCs().split(","));
            csList.forEach(cs -> {
                if (configParam.get(cs) == null) {
                    throw new MissingArgumentException("打印配置参数"+cs);
                }
            });

            List<Map> sqlMapList = Lists.newArrayList();

            if(BdcDySjlyEnum.FW.getCode().equals(detail.getSjly())){
                // 服务方式 added by zhuyong 20200310
                this.getResultByGetReuqest(detail.getQqyy(), detail.getDyzbsjy(), configParam, sqlMapList);
            } else {
                // SQL方式
                /**
                 * 多条sql以英文分号相隔
                 */
                List<String> sqlList = Lists.newArrayList(detail.getDyzbsjy().split(CommonConstantUtils.ZF_YW_FH));

                int index = 1;
                Map<String, Object> sqlResult = Maps.newHashMap();
                for (String sql : sqlList) {
                    if (StringUtils.isBlank(StringUtils.trim(sql))) {
                        continue;
                    }
                    Matcher matcher = pattern.matcher(sql);
                    String matchStr = null;
                    String condition = null;
                    Object value = null;
                    while (matcher.find()) {
                        matchStr = matcher.group();
                        condition = matchStr.substring(2, matchStr.length() - 1);
                        value = configParam.get(condition);
                        if (value == null) {
                            throw new MissingArgumentException(condition);
                        }
                        sql = sql.replaceAll("\\$\\{" + condition + "\\}", value.toString());
                    }
                    configParam.put("sql", sql);
                    List<Map> detailList;
                    if (BdcDyBeanEnum.BdcDyConfigService.getBeanName().equals(configBeanName)) {
                        BdcDyConfigService bdcDyConfigService = beansResolveUtils.getBeanByName(configBeanName);
                        detailList = bdcDyConfigService.executeConfigSql(configParam,detail.getDbsource());
                    }else {
                        BdcConfigMapper bdcConfigMapper = beansResolveUtils.getBeanByName(configBeanName);
                        detailList = bdcConfigMapper.executeConfigSql(configParam);
                    }
                    if (CollectionUtils.size(sqlList) > 1) {
                        sqlResult.put("sql" + index, Queues.newArrayDeque(detailList));
                        index++;
                    } else {
                        sqlMapList.addAll(detailList);
                    }
                }
                while (MapUtils.isNotEmpty(sqlResult)) {
                    Iterator<Map.Entry<String, Object>> iterator = sqlResult.entrySet().iterator();
                    Map<String, Object> detailMap = Maps.newHashMap();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        Queue queue = (Queue) entry.getValue();
                        Map<String, Object> map = (Map<String, Object>) queue.poll();
                        if (map == null) {
                            iterator.remove();
                            continue;
                        } else {
                            detailMap.putAll(map);
                        }
                    }
                    if (MapUtils.isNotEmpty(detailMap)) {
                        sqlMapList.add(detailMap);
                    }
                }
            }
            String detailid = detail.getDyzbid();
            multimap.put(detailid, sqlMapList);
        });
        return multimap;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param appName 请求应用或地址
     * @param url 具体请求服务路径
     * @param params 参数列表
     * @param resultDataList 返回结果保存集合
     * @description 服务方式获取打印数据内容
     *
     *  1、目前服务方式只支持GET方式，因为打印时候只传参字符串、数字这些类型，不传实体
     *  2、例如: /realestate-inquiry/rest/v1.0/print/zfxx/#{bdcdyh}/#{xmid}/xml?bdcdyh=#{bdcdyh}
     *  3、打印获取数据服务接口需要返回 List<Map> 或 List<实体> 结构数据
     */
    private void getResultByGetReuqest(String appName, String url, Map params, List<Map> resultDataList) {
        try {
            List<Map<String, Object>> result = (List<Map<String, Object>>) restRpcUtils.getRpcRequest(appName, url, params);

            // 为了匹配已有的处理逻辑，key转为大写
            if(CollectionUtils.isNotEmpty(result)){
                List<Map<String, Object>> newList = new ArrayList<>(result.size());
                result.stream().forEach(map -> {
                    Map<String, Object> newMap = new HashMap<>(map.size());
                    for(Map.Entry<String, Object> entry : map.entrySet()){
                        newMap.put(entry.getKey().toUpperCase(), entry.getValue());
                    }
                    newList.add(newMap);
                });

                resultDataList.addAll(newList);
            }
        } catch (Exception e){
            LOGGER.error("打印-服务方式获取数据内容发生异常：{}", e.getMessage());
            throw new AppException("打印-服务方式获取数据内容发生异常：" + e.getMessage());
        }
    }
}
