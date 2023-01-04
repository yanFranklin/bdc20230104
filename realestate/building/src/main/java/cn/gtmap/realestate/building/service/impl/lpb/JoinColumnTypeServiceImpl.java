package cn.gtmap.realestate.building.service.impl.lpb;

import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.mapper.LpbJoinQueryMapper;
import cn.gtmap.realestate.building.core.resource.InfoResource;
import cn.gtmap.realestate.building.service.lpb.LpbConfigInfoService;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.LpbInfoTypeEnum;
import cn.gtmap.realestate.building.util.LpbUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-03
 * @description
 */
@Service
public class JoinColumnTypeServiceImpl extends LpbConfigInfoService {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinColumnTypeServiceImpl.class);

    @Autowired
    private LpbJoinQueryMapper lpbJoinQueryMapper;

    @Override
    protected LpbInfoTypeEnum getTypeEnum() {
        return LpbInfoTypeEnum.JOINCOLUMN;
    }

    /**
     * @param infoResource
     * @param configList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理服务
     */
    @Override
    public Map<String, Object> abstractDealInfo(InfoResource infoResource, List<ColumnBO> configList) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Map<String, Object> infoMap = LpbUtils.parseObjectToMap(infoResource.paramObject, null);
        for(ColumnBO config : configList){
            if(StringUtils.isNotBlank(config.getValue())){
                Map<String,Object> temp = new HashMap<>();
                temp.put("desc",config.getDesc());
                temp.put("tabType",config.getTabType());
                temp.put("type",config.getType());
                temp.put("value",executeQuery(config,infoMap));
                resultMap.put(config.getValue(),temp);
            }
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param config
     * @return java.lang.String
     * @description 
     */
    private String executeQuery(ColumnBO config,Map<String,Object> infoMap){
        if(StringUtils.isNotBlank(config.getJoinSql())){
            String joinSql = config.getJoinSql();
            // 判断 有WHERE关键字 才可以执行
            if(StringUtils.upperCase(joinSql).contains("WHERE")){
                String executeSql = LpbUtils.revertPlaceholder(joinSql,infoMap);
                if(StringUtils.isNotBlank(executeSql)){
                    List<String> result = lpbJoinQueryMapper.queryJoinSql(executeSql);
                    if(CollectionUtils.isNotEmpty(result)){
                        if(result.size() <= 10){
                            return LpbUtils.wmStr(Constants.DEFAULT_SEPARATOR,result);
                        }else{
                            LOGGER.error("配置关联SQL查询结果过多（{}条），不予处理",result.size());
                        }
                    }
                }
            }else{
                LOGGER.error("配置关联SQL中不包含WHERE条件语句，不予执行");
            }
        }
        return "";
    }



}
