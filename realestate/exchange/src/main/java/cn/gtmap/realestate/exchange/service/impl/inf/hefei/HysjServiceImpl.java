package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.hefei.hydjxxxc.request.HydjxxxcRequestBody;
import cn.gtmap.realestate.exchange.core.dto.hefei.hydjxxxc.request.HydjxxxcRequestCondition;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/10/12 9:45
 * @description 省民政厅婚姻数据相关
 */
@Service(value = "hysjServiceImpl")
public class HysjServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(HysjServiceImpl.class);

    private static final String REQUIRED_ITEMS = "POXM,POGMSFHM,DJRQ";

    /*
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [filterResponse, jhsjcxResponse, lhsjcxResponse]
     * @return com.alibaba.fastjson.JSONObject
     * @description 处理部级婚姻，省级结婚，省级离婚三个接口返回的数据，比较登记日期取日期最新的接口数据
     */
    public JSONObject hysjFilter(JSONObject filterResponse, JSONObject jhsjcxResponse, JSONObject lhsjcxResponse) {
        JSONObject jsonObject = new JSONObject();
        Date bjjkDate = null;
        Date sjjhDate = null;
        Date sjlhDate = null;
        List<Date> dateList = new ArrayList<>();
        if (jhsjcxResponse != null && StringUtils.isNotBlank(jhsjcxResponse.getString("djrq"))) {
            sjjhDate = DateUtils.formatDate(jhsjcxResponse.getString("djrq"), DateUtils.sdf_ymd);
            if (sjjhDate != null) {
                dateList.add(sjjhDate);
            }
        }

        if (lhsjcxResponse != null && StringUtils.isNotBlank(lhsjcxResponse.getString("djrq"))) {
            sjlhDate = DateUtils.formatDate(lhsjcxResponse.getString("djrq"), DateUtils.sdf_ymd);
            if (sjlhDate != null) {
                dateList.add(sjlhDate);
            }
        }

        if (filterResponse != null && StringUtils.isNotBlank(filterResponse.getString("opDate"))) {
            bjjkDate = DateUtils.formatDate(filterResponse.getString("opDate"), DateUtils.sdf_ymdhms);
            if (bjjkDate != null) {
                dateList.add(bjjkDate);
            }
        }
        if (CollectionUtils.isNotEmpty(dateList)) {
            // 日期按自然顺序（从小到大）排列，取最后一个（最新日期）
            Collections.sort(dateList);
            Date date = dateList.get(dateList.size() - 1);

            if (Objects.equals(date, sjjhDate)) {
                // 返回省级结婚接口的数据
                jsonObject.put("hyzt", "已婚");
                BdcSlJtcyDO jtcy = new BdcSlJtcyDO();
                jtcy.setJtcymc(jhsjcxResponse.getString("poxm"));
                jtcy.setZjh(jhsjcxResponse.getString("posfzh"));
                jsonObject.put("jtcy", jtcy);
            } else if (Objects.equals(date, sjlhDate)) {
                // 返回省级离婚接口的数据
                jsonObject.put("hyzt", "离婚");
            } else {
                // 返回部级接口数据，根据opType确定婚姻状态
                String opType = filterResponse.getString("opType");
                if (StringUtils.isNotBlank(opType)) {
                    if (org.apache.commons.lang3.StringUtils.equals("IA", opType)
                            || org.apache.commons.lang3.StringUtils.equals("IC", opType)
                            || org.apache.commons.lang3.StringUtils.equals("ICA", opType)) {

                        jsonObject.put("hyzt", "已婚");
                        BdcSlJtcyDO jtcy = new BdcSlJtcyDO();
                        jtcy.setJtcymc(filterResponse.getString("xm"));
                        jtcy.setZjh(filterResponse.getString("zjh"));
                        jsonObject.put("jtcy", jtcy);

                    } else if (org.apache.commons.lang3.StringUtils.equals("IB", opType) || org.apache.commons.lang3.StringUtils.equals("ICB", opType)) {
                        jsonObject.put("hyzt", "离婚");
                    } else {
                        // 婚姻类型只返回明确的结婚，离婚，其他为无效数据
                        jsonObject.put("hyzt", "");
                    }
                }

            }

        }
        return jsonObject;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [reqJsonObject]
     * @return com.alibaba.fastjson.JSONObject
     * @description 构造省厅婚姻接口查询参数xml
     */
    public JSONObject buildHydjxxxcRequestXml(JSONObject reqJsonObject) {
        if(reqJsonObject == null) {
            LOGGER.warn("省厅婚姻接口请求入参为空！");
            return null;
        }
        if(StringUtils.isBlank(reqJsonObject.getString("qlrmc")) || StringUtils.isBlank(reqJsonObject.getString("qlrzjh"))) {
            LOGGER.warn("省厅婚姻接口请求入参缺失！");
        }

        HydjxxxcRequestCondition requestCondition = new HydjxxxcRequestCondition();
        requestCondition.setXM(reqJsonObject.getString("qlrmc"));
        requestCondition.setGMSFHM(reqJsonObject.getString("qlrzjh"));

        HydjxxxcRequestBody requestBody = new HydjxxxcRequestBody();
        requestBody.setCondition(requestCondition);
        requestBody.setRequiredItems(REQUIRED_ITEMS);

        JSONObject xmlJsonObject = new JSONObject();
        xmlJsonObject.put("requestBody", JSONObject.toJSONString(requestBody));
        return xmlJsonObject;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [resJsonObject]
     * @return com.alibaba.fastjson.JSONObject
     * @description 处理省厅婚姻接口返回值
     */
    public JSONObject dealHydjxxxcResponse(JSONObject resJsonObject) {
      /*  if(resJsonObject != null && StringUtils.equals(resJsonObject.getString("resultCode"), "00")
                && CollectionUtils.isNotEmpty(resJsonObject.getJSONArray("resultData"))){
            return (JSONObject) resJsonObject.getJSONArray("resultData").get(0);
        }*/
        if (resJsonObject != null && resJsonObject.getBoolean("IsSuccess")
                && CollectionUtils.isNotEmpty(resJsonObject.getJSONArray("DataList"))) {
            return (JSONObject) resJsonObject.getJSONArray("DataList").get(0);
        }
        return null;
    }
}
