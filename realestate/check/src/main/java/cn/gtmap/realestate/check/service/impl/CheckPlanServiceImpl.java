package cn.gtmap.realestate.check.service.impl;

import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.check.service.CheckPlanService;
import cn.gtmap.realestate.common.core.domain.check.CheckPlanDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/7/30.
 * @description
 */

@Service
public class CheckPlanServiceImpl implements CheckPlanService{
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    EntityMapper entityMapper;
    @Override
    public Map<String, Object> getPlanDatas(Map<String, Object> params) {
        Map<String, Object> result = new HashMap();
        int num = bdcXmMapper.countBdcXms(params);
        Integer dcjcts = MapUtils.getInteger(params,"dcjcts");
        if(num >0 &&  dcjcts!= null) {
            int jccs = num % dcjcts == 0 ? (num / dcjcts) : (num / dcjcts) + 1;
            result.put("zsjl", num);
            result.put("jccs", jccs);
        }
        int version = bdcXmMapper.getCheckPlanVersion() +1;
        result.put("version", version);
        return result;
    }

    @Override
    public void saveOrUpdatePlan(CheckPlanDO checkPlanDO) {
        if(StringUtils.isBlank(checkPlanDO.getId())){
            checkPlanDO.setId(UUIDGenerator.generate16());
            checkPlanDO.setCreatetime(new Date());
            entityMapper.insert(checkPlanDO);
        }else{
            entityMapper.updateByPrimaryKeySelective(checkPlanDO);
        }
    }

    @Override
    public List<CheckPlanDO> queryCheckPlan(CheckPlanDO checkPlanDO) {
        return bdcXmMapper.queryCheckPlan(checkPlanDO);
    }
}
