package cn.gtmap.realestate.common.core.service.Impl;

import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/18
 * @description 实体CRUD操作逻辑Service
 */
@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityMapper entityMapper;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   json 需要保存的实体属性json字符串
     * @param   entityClass 要保存的对应实体Class
     * @return  {int} 更新数据记录数量
     * @description
     *     <p>
     *         更新实体对象部分属性值  <br>
     *         1、json字符串为要更新的实体部分属性、值序列化JSON <br>
     *         2、json中属性值为 null值的会更新为null值
     *     </p>
     */
    @Override
    public <T> int updateByJsonEntity(String json, Class<T> entityClass){
        JSONObject jsonObject=getJsonObject(json);
        T entity=getEntity( json, entityClass);
        return entityMapper.updateByJsonEntity(jsonObject, entity);
    }

    /**
     * @param json        需要更新的实体部分属性json字符串
     * @param entityClass 要保存的对应实体Class
     * @return {int} 更新数据记录数量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 插入实体对象部分属性
     */
    @Override
    public <T> int insertJsonEntity(String json, Class<T> entityClass) {
        T entity=getEntity( json, entityClass);
        return entityMapper.insertSelective(entity);
    }

    /**
     * @param json        需要更新的实体部分属性json字符串
     * @param entityClass 要保存的对应实体Class
     * @return <T> 实体类
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取实体类
     */
    private <T> T getEntity(String json, Class<T> entityClass){
        if(StringUtils.isBlank(json) || null == entityClass){
            throw new NullPointerException("更新实体数据不存在或未指定要更新的实体类型！");
        }
        T entity = JSON.parseObject(json, entityClass);
        return entity;
    }

    /**
     * @param json        需要更新的实体部分属性json字符串
     * @return JSONObject 实体参数 jsonObject 格式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取实体内容
     */
    private JSONObject getJsonObject(String json){
        if(StringUtils.isBlank(json)){
            throw new NullPointerException("更新实体数据不存在或未指定要更新的实体类型！");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        if(MapUtils.isEmpty(jsonObject)){
            throw new NullPointerException("更新实体属性内容为空！");
        }
        return jsonObject;
    }
}
