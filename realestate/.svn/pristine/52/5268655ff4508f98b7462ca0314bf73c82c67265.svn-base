package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.FwJtcyMapper;
import cn.gtmap.realestate.building.core.service.FwJtcyService;
import cn.gtmap.realestate.common.core.domain.building.FwJtcyDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/11/30/17:01
 * @Description:
 */
@Service
public class FwJtcyServiceImpl implements FwJtcyService {
    @Autowired
    FwJtcyMapper fwJtcyMapper;

    /**
     * @param qlrIndex 权利人主键
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据权利人主键查询房屋家庭成员
     */
    @Override
    public List<FwJtcyDO> listFwJtcy(String qlrIndex) {
        if(StringUtils.isNotBlank(qlrIndex)){
            Map<String,Object> paramMap = new HashMap();
            paramMap.put("qlrIndex",qlrIndex);
            return fwJtcyMapper.listFwJtcy(paramMap);
        }
        return new ArrayList<>();
    }
}
