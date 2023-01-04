package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.etl.EtlDjtDjSlsqDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.mapper.DjtDJSlsqMapper;
import cn.gtmap.realestate.etl.service.DjtDJSlsqService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DjtDJSlsqServiceImpl implements DjtDJSlsqService {

    @Autowired
    private DjtDJSlsqMapper djtDJSlsqMapper;


    /**
     * @param ywh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过YWH查询受理申请表里的 BDCDYH
     */
    @Override
    public String getBdcdyhByYwh(String ywh) {
        // 如果只有YWH 需要 查询DJT_DJ_SLSQ表 获取BDCDYH
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("ywh",ywh);
        List<EtlDjtDjSlsqDO> slsqList = listDjtDjSlsq(paramMap);
        if(slsqList.size() == 1){
            return slsqList.get(0).getBdcdyh();
        }else{
            throw new AppException("业务号存在多条DJT_DJ_SLSQ记录");
        }
    }

    /**
     * @param map
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询受理申请
     */
    @Override
    public List<EtlDjtDjSlsqDO> listDjtDjSlsq(Map<String, Object> map) {
        if(MapUtils.isNotEmpty(map)){
            return djtDJSlsqMapper.listDjtDjSlsqDO(map);
        }
        return Collections.emptyList();
    }

    /**
     * @param
     * @return List<DjtDJSlSqDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询未处理的数据
     */
    @Override
    public List<EtlDjtDjSlsqDO> listUnsettledDjtDJSlSqDO(String dealflag) {
        Map map=new HashMap();
        map.put("dealflag",dealflag);
        return djtDJSlsqMapper.listDjtDjSlsqDO(map);
    }

    /**
     * @param
     * @return Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过map修改
     */
    @Override
    public Integer updateFlagByYwh(Map map) {
        return djtDJSlsqMapper.updateFlagByYwh(map);
    }
}
