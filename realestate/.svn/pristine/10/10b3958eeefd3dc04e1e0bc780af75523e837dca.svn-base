package cn.gtmap.realestate.exchange.service.inf.sjpt;

import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeCxqqXm;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsq.response.SjptCxsqResponseData;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxsqFk.request.SjptCxsqFkRequestData;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-26
 * @description 省级平台查询申请服务
 */
public interface CxsqService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqList
     * @return SjptCxsqFkRequestData
     * @description 保存查询申请
     */
    SjptCxsqFkRequestData saveCxsq(JSONArray cxsqList) throws Exception;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxPeCxqqList
     * @return void
     * @description 批量保存 查询申请
     */
    void batchInsertGxPeCxqq(List<GxPeCxqq> gxPeCxqqList);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param cxsqList,cxsqdhList
     * @return void
     * @description 批量保存 查询业务数据
     */
    void batchInsertGxPeCxqqXm(List cxsqList,List<String> cxsqdhList);

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">liyinqiao</a>
     * @param cxqq,gxPeCxqqXmList
     * @return void
     * @description 插入每条查询申请信息
     */
    void saveCxqqxx(GxPeCxqq cxqq,List<GxPeCxqqXm> gxPeCxqqXmList);
}
