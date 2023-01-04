package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcSlBysldjService;
import cn.gtmap.realestate.common.core.domain.accept.BdcByslDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.Impl.EntityServiceImpl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BdcSlbysldjServiceImpl implements BdcSlBysldjService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private EntityServiceImpl entityService;

    /**
     * @param gzlslid 工作流实例ID
     * @return 不予受理/不予登记信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例id获取不予受理，不予登记信息
     */
    @Override
    public List<BdcByslDO> queryBdcByslDOBygzlslid(String gzlslid) {
        Example example = new Example(BdcByslDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }
        criteria.andEqualTo("gzlslid", gzlslid);
        return entityMapper.selectByExample(example);
    }

    /**
     * @return 不予受理/不予登记信息
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例id获取不予受理，不予登记信息
     */
    @Override
    public BdcByslDO insertBdcByslDO(BdcByslDO bdcByslDO) {
        if (bdcByslDO != null) {
            if (StringUtils.isBlank(bdcByslDO.getByslid())) {
                bdcByslDO.setByslid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcByslDO);
        }
        return bdcByslDO;
    }

    /**
     * @param byslid 不予受理ID
     * @return 删除数量
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据不予受理ID删除不予受理，不予登记信息
     */
    @Override
    public Integer deleteBdcByslDOByByslid(String byslid) {
        if (StringUtils.isBlank(byslid)) {
            return 0;
        }
        return entityMapper.deleteByPrimaryKey(BdcByslDO.class, byslid);

    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 删除数量
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID删除不予受理，不予登记信息
     */
    @Override
    public Integer deleteBdcByslDOBygzlslid(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return 0;
        }
        Example example = new Example(BdcByslDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", gzlslid);
        return entityMapper.deleteByExampleNotNull(example);
    }

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 依据工作流实例ID修改不予受理，不予登记信息
     */
    @Override
    public Integer saveBdcByslDOBygzlslid(String json) {
        JSONObject jsonObject = getJsonObject(json);
        BdcByslDO bdcByslDO = getEntity(json, BdcByslDO.class);
        return entityMapper.updateByJsonEntity(jsonObject, bdcByslDO);
    }

    /**
     * @param json 需要更新的实体部分属性json字符串
     * @return JSONObject 实体参数 jsonObject 格式
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取实体内容
     */
    private JSONObject getJsonObject(String json) {
        if (StringUtils.isBlank(json)) {
            throw new NullPointerException("更新实体数据不存在或未指定要更新的实体类型！");
        }
        JSONObject jsonObject = JSON.parseObject(json);
        if (MapUtils.isEmpty(jsonObject)) {
            throw new NullPointerException("更新实体属性内容为空！");
        }
        return jsonObject;
    }

    /**
     * @param json        需要更新的实体部分属性json字符串
     * @param entityClass 要保存的对应实体Class
     * @return <T> 实体类
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取实体类
     */
    private <T> T getEntity(String json, Class<T> entityClass) {
        if (StringUtils.isBlank(json) || null == entityClass) {
            throw new NullPointerException("更新实体数据不存在或未指定要更新的实体类型！");
        }
        T entity = JSON.parseObject(json, entityClass);
        return entity;
    }
}
