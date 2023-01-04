package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.service.inf.CommonResponseService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-20
 * @description 常用的响应处理服务
 */
@Service
public class CommonResponseServiceImpl implements CommonResponseService{
    /**
     * @param result
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断返回是否是空 空返回false 非空返回true
     */
    @Override
    public boolean checkResultIsEmpty(Object result) {
        if(result instanceof List){
            return CollectionUtils.isNotEmpty((List)result);
        }else if(result instanceof Map){
            return MapUtils.isNotEmpty((Map)result);
        }else{
            return result != null && CheckParameter.checkAnyParameter(result);
        }
    }

    /**
     * @param result
     * @return boolean
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 判断返回是否是空 空返回false 非空返回true
     */
    @Override
    public boolean checkResultTokenError(Object result) {
        boolean isTokenError=false;
        if(result != null){
            JSONObject resultObj = null;
            try{
                resultObj = JSONObject.parseObject(result.toString());
            }catch (Exception e){
                try{
                    JSONArray responseArr = JSONArray.parseArray(result.toString());
                    if(CollectionUtils.isNotEmpty(responseArr)){
                        resultObj = responseArr.getJSONObject(0);
                    }
                }catch (Exception e1){
                }
            }
            if (resultObj != null && resultObj.get("head") instanceof Map) {
                Map dataObj = (Map)resultObj.get("head");
                if (MapUtils.isNotEmpty(dataObj)) {
                    if(dataObj.get("code")!=null && StringUtils.equals(dataObj.get("code").toString(),"2002")){
                        isTokenError=true;
                    }
                }
            }
        }
        return isTokenError;
    }

}
