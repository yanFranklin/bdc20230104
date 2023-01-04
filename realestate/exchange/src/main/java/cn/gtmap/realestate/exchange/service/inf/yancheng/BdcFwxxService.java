package cn.gtmap.realestate.exchange.service.inf.yancheng;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/12/10
 * @description 给互联网+银行提供查询房屋户室信息
 */
public interface BdcFwxxService {

    /**
     * 根据index查询户室信息
     *
     * @param param param
     * @return YcHsZtResDTO
     * @Date 2020/12/10
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Map queryHsZt(JSONObject param);
}
