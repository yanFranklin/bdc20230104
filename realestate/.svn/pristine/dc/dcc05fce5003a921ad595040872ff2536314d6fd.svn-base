package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.TdlpbMapper;
import cn.gtmap.realestate.building.service.TdlpbService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/04
 * @Description:
 */
@Service
public class TdlpbServiceImpl implements TdlpbService {

    @Autowired
    TdlpbMapper tdlpbMapper;
    @Override
    public List<Map<String,Object>> getTdlpbxx(String djh) {
        if(StringUtils.isNotBlank(djh)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("djh",djh);
            return tdlpbMapper.getTdlpbxxList(paramMap);
        }
        return Collections.emptyList();
    }
}
