package cn.gtmap.realestate.exchange.service.inf.wwsq;

import cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response.WwsqQueryJtcyResponseData;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-20
 * @description 外网申请 家庭成员相关服务
 */
public interface JtcyService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jtcyList
     * @param sqrzjh
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response.WwsqQueryJtcyResponseData>
     * @description 为互联网+做家庭成员过滤
     */
    List<WwsqQueryJtcyResponseData> filterMainCy(List<WwsqQueryJtcyResponseData> jtcyList,String sqrzjh);


}
