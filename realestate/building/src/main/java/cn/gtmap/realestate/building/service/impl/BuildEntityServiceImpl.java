package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.service.BuildEntityService;
import cn.gtmap.realestate.building.web.rest.EntityRestController;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.EntityService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 更新服务
 */
@Service
public class BuildEntityServiceImpl implements BuildEntityService {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityRestController.class);


    @Autowired
    private EntityService entityService;

    /**
     * @param json
     * @param className
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description JSON结构更新实体
     */
    @Override
    public void updateEntityByJson(String json, String className) {
        if(StringUtils.isBlank(json) || StringUtils.isBlank(className)){
            throw new NullPointerException("空参数异常！");
        }
        try {
            entityService.updateByJsonEntity(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("更新实体数据异常！");
        }
    }

    /**
     * @param jsonArray
     * @param className
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description JSON结构更新实体 批量
     */
    @Override
    public void batchUpdateEntityByJson(String jsonArray, String className) {
        if(StringUtils.isBlank(jsonArray) || StringUtils.isBlank(className)){
            throw new NullPointerException("空参数异常！");
        }
        JSONArray array = JSONObject.parseArray(jsonArray);
        if(CollectionUtils.isNotEmpty(array)){
            for(int i = 0 ; i < array.size() ; i++){
                JSONObject jsonObject = array.getJSONObject(i);
                try {
                    entityService.updateByJsonEntity(jsonObject.toJSONString(), Class.forName(className));
                } catch (ClassNotFoundException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new AppException("更新实体数据异常！");
                }
            }
        }
    }
}
