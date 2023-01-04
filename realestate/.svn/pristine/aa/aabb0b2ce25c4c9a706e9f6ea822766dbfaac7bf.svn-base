package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.exchange.service.inf.sjpt.SjptCommonService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/1/25
 * @description 省级平台公共服务
 */
@Service
public class SjptCommonServiceImpl implements SjptCommonService {

    /**
     * 区县代码
     */
    @Value("${sjpt.xzqdm:}")
    private String xzqdm;

    /**
     * @param requestObject 请求参数
     * @return 身份核查请求参数
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取身份核查请求参数
     */
    public JSONObject getSfhcRequestParam(JSONObject requestObject) {
        if(requestObject == null){
            return null;
        }

        List requestList  = requestObject.getJSONArray("cxywcs");
        if(CollectionUtils.isEmpty(requestList)){
            return null;
        }

        List<Map<String, Object>> conditions = new ArrayList<>();
        for(Object param : requestList){
            Map<String,String> paramMap = (Map<String, String>) param;
            Map<String, Object> condition = new HashMap<>();

            Map<String, Object> checkCondition = new HashMap<>();
            checkCondition.put("xm", paramMap.get("xm"));
            condition.put("CheckCondition", checkCondition);
            condition.put("QueryCondition", "gmsfhm='" + paramMap.get("zjh") + "'");
            conditions.add(condition);
        }


        requestObject.put("Conditions", conditions);
        return requestObject;
    }

    public String getCxqqdh(){
        return DateUtil.formatDate("yyyyMMdd") + xzqdm + "000000";
    }


}
