package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.HfCustomMapper;
import cn.gtmap.realestate.building.service.HfCustomService;
import cn.gtmap.realestate.building.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-30
 * @description 合肥版本的 特殊需求 服务
 */
@Service
public class HfCustomServiceImpl implements HfCustomService{


    @Autowired
    private HfCustomMapper hfCustomMapper;

    /**
     * @param bdcdyh
     * @param hslx sc yc
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH 查询行政区
     */
    @Override
    public String queryXzqByBdcdyh(String bdcdyh, String hslx) {
        if(StringUtils.isBlank(bdcdyh)){
            return null;
        }
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("bdcdyh",bdcdyh);
        List<Map> xzqList = null;
        if(StringUtils.equals(Constants.FW_YCHS,hslx)){
            xzqList = hfCustomMapper.queryFwYchsXzq(paramMap);
        }
        if(StringUtils.equals(Constants.FW_SCHS,hslx)){
            xzqList = hfCustomMapper.queryFwhsXzq(paramMap);
        }
        if(StringUtils.isBlank(hslx)){
            xzqList = hfCustomMapper.queryFwhsXzq(paramMap);
            if(CollectionUtils.isEmpty(xzqList)){
                xzqList = hfCustomMapper.queryFwYchsXzq(paramMap);
            }
        }
        if(CollectionUtils.isNotEmpty(xzqList)){
            Map temp = xzqList.get(0);
            return MapUtils.getString(temp,"XZQ");
        }
        return null;
    }

}
