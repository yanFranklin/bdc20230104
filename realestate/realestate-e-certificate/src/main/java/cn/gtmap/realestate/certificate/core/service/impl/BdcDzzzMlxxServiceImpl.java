package cn.gtmap.realestate.certificate.core.service.impl;



/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 不动产电子证照目录库业务
 */

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzMlxxMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzMlxxService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BdcDzzzMlxxServiceImpl implements BdcDzzzMlxxService {

    @Autowired
    private BdcDzzzMlxxMapper bdcDzzzMlxxMapper;

    @Override
    public int insertBdcDzzzMlxx(BdcDzzzMlxxDO bdcDzzzMlxxDO) {
        int result = 0;
        if (bdcDzzzMlxxDO != null) {
            result = bdcDzzzMlxxMapper.insertBdcDzzzMlxx(bdcDzzzMlxxDO);
        }
        return result;
    }

    @Override
    public List<BdcDzzzMlxxDO> queryBdcDzzzMlxxByMap(Map map) {
        List<BdcDzzzMlxxDO> bdcDzzzMlxxDOList = new ArrayList<>();
        if (MapUtils.isNotEmpty(map)) {
            bdcDzzzMlxxDOList = bdcDzzzMlxxMapper.queryBdcDzzzMlxxByMap(map);
        }

        return bdcDzzzMlxxDOList;
    }

    @Override
    public String countMlxxQuantitativeDistribution(Map map) {
        List<Map> resultList = bdcDzzzMlxxMapper.countMlxxQuantitativeDistribution(map);
        return countToJsonString(resultList);
    }

    @Override
    public String countToJsonString(List<Map> resultList) {
        JSONObject result = new JSONObject();
        if (CollectionUtils.isNotEmpty(resultList)) {
            JSONArray xAreaData = new JSONArray();
            JSONArray yZmData = new JSONArray();
            JSONArray yZsData = new JSONArray();
            JSONArray dataJSONArray = new JSONArray();
            for (int i = 0; i < resultList.size(); i++) {
                Map resultMap = resultList.get(i);
                Object mc = resultMap.get("MC");
                Object zms_count = resultMap.get("ZMS_COUNT");
                Object zs_count = resultMap.get("ZS_COUNT");
                xAreaData.add(mc);
                yZmData.add(zms_count);
                yZsData.add(zs_count);
                JSONObject dataObject = new JSONObject();
                dataObject.put("id", i + 1);
                dataObject.put("areaName", mc);
                dataObject.put("zs", zs_count);
                dataObject.put("zm", zms_count);
                dataJSONArray.add(dataObject);
            }

            result.put("xAreaData", xAreaData);
            result.put("yZmData", yZmData);
            result.put("yZsData", yZsData);
            result.put("data", dataJSONArray);
        }
        return JSON.toJSONString(result);
    }

    @Override
    public List<Map> countBdcDzzzMlxx(Map map) {
        return bdcDzzzMlxxMapper.countBdcDzzzMlxx(map);
    }
}
