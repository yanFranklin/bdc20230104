package cn.gtmap.realestate.exchange.service.impl.inf.bengbu;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-10-7
 * @description 蚌埠婚姻户籍接口相关服务
 */
@Service
public class BbHyhjxxServiceImpl {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 特殊处理蚌埠婚姻户籍信息数据 response.body.resultList.result 单条信息处理为 array，避免 dozer 转换报错
     */
    public Object handleAcceptHyxxJtcyxxResult(Object respBodySource) {
        if(respBodySource != null) {
            JSONObject jsonObject = JSON.parseObject(respBodySource.toString());
            JSONObject response = jsonObject.getJSONObject("response");
            if (response == null) {
                return respBodySource;
            }
            JSONObject body = response.getJSONObject("body");
            if (body == null) {
                return respBodySource;
            }
            JSONObject resultList = body.getJSONObject("resultList");
            if (resultList == null) {
                return respBodySource;
            }
            Object result = resultList.get("result");
            if (result instanceof JSONObject) {
                JSONArray array = new JSONArray();
                array.add(result);
                resultList.put("result", array);
                body.put("resultList", resultList);
                response.put("body", body);
                jsonObject.put("response", response);
                return jsonObject;
            }
        }

        return respBodySource;
    }

}
