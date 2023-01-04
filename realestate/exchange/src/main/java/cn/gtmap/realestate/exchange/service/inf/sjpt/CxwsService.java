package cn.gtmap.realestate.exchange.service.inf.sjpt;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-11
 * @description
 */
public interface CxwsService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param responseJson
     * @return void
     * @description 保存查询文书信息
     */
    void saveCxwsxx(JSONObject responseJson);
}
