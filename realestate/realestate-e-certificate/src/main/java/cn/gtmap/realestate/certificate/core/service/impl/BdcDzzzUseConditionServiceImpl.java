package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzUseConditionMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzTokenDo;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzUseConditionDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzTokenService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzUseConditionService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0 2019/1/22
 * @description 请求记录
 */
@Service
public class BdcDzzzUseConditionServiceImpl implements BdcDzzzUseConditionService {
    @Resource
    private BdcDzzzUseConditionMapper dzzzUseConditionMapper;
    @Autowired
    private BdcDzzzTokenService bdcDzzzTokenService;


    @Override
    public void insertDzzzUseCondition(BdcDzzzZzxx bdcDzzzZzxx, String yymc) {
        if (StringUtils.isNotBlank(yymc)) {
            BdcDzzzTokenDo bdcDzzzTokenDo = bdcDzzzTokenService.queryTokenByTokenName(yymc);
            if (null != bdcDzzzTokenDo && null != bdcDzzzZzxx) {
                BdcDzzzUseConditionDo conditionDo = new BdcDzzzUseConditionDo();
                conditionDo.setId(UUIDGenerator.generate());
                conditionDo.setZzbs(bdcDzzzZzxx.getZzbs());
                conditionDo.setBdcqzh(bdcDzzzZzxx.getBdcqzh());
                conditionDo.setYymc(bdcDzzzTokenDo.getYymc());
                conditionDo.setYybm(bdcDzzzTokenDo.getYybm());
                conditionDo.setCjsj(DateUtil.now());
                conditionDo.setBdcdyh(bdcDzzzZzxx.getBdcdyh());
                dzzzUseConditionMapper.insertDzzzUseCondition(conditionDo);
            }
        }
    }

    @Override
    public String countUseConditionYybm(String bdcqzh) {
        JSONObject obj = new JSONObject();
        if (StringUtils.isNotBlank(bdcqzh)) {
            Map paramMap = new HashMap<>(2);
            if (StringUtils.isNotBlank(bdcqzh)) {
                paramMap.put("bdcqzh", bdcqzh);
            }
            List<Map> mapList = dzzzUseConditionMapper.countUseConditionYybm(paramMap);
            List<Map> conditionDoList = dzzzUseConditionMapper.getUseConditionByBdcqzh(bdcqzh);
            if (CollectionUtils.isNotEmpty(mapList)) {
                JSONArray zzsyXdata = new JSONArray();
                JSONArray zzsySeriesXdata = new JSONArray();
                for (Map result : mapList) {
                    zzsyXdata.add(result.get("MC"));
                    zzsySeriesXdata.add(result.get("USE_COUNT"));
                }
                obj.put("zzsyXdata", zzsyXdata);
                obj.put("zzsySeriesXdata", zzsySeriesXdata);
            }
            if (CollectionUtils.isNotEmpty(conditionDoList)) {
                obj.put("zzsyTableData",conditionDoList);
            }
        }
        return JSON.toJSONString(obj);
    }
}
