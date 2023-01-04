package cn.gtmap.realestate.exchange.service.inf.standard;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wangyinghao
 * @version 2021-06-23 11:05:54
 * @description 共享查询
 */
public interface BdcGxcxService {

    /**
     * 宣城-sdk查询常住人口
     *
     * @param request
     * @return
     */
    public JSONArray SkdCzrkcx(JSONObject request);

}
