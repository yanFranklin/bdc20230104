package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.request.WjwCszmRequestBody;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.request.WjwCszmRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.request.WjwCszmRequestData;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwcszm.request.WjwCszmRequestInfo;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request.WjwSwzmRequestBody;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request.WjwSwzmRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request.WjwSwzmRequestData;
import cn.gtmap.realestate.exchange.core.dto.hefei.wjwswzm.request.WjwSwzmRequestInfo;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 卫计委 相关服务
 */
@Service
public class WjwServiceImpl {

    @Value("${openapi.wjw.orgcode:}")
    private String orgCode;

    @Value("${openapi.wjw.orgname:}")
    private String orgName;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonObject
     * @return java.util.Map
     * @description 拼凑出生证明的接口  层级多  用dozer 实现比较复杂 所以写代码拼凑
     */
    public Map getCszmRequestParam(JSONObject jsonObject){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("api_method","getBirthInfo");
        WjwCszmRequestDTO requestDTO = new WjwCszmRequestDTO();
        requestDTO.setBody(new ArrayList<>());
        requestDTO.setRequestinfo(new ArrayList<>());

        WjwCszmRequestInfo requestInfo = new WjwCszmRequestInfo();
        requestInfo.setRequestOrgName(orgName);
        requestInfo.setRequestOrgCode(orgCode);
        requestInfo.setRequestDate(DateUtil.formateTime(new Date()));

        WjwCszmRequestData requestData = new WjwCszmRequestData();
        requestData.setMomName(jsonObject.getString("mqmc"));
        requestData.setMomIdCode(jsonObject.getString("mqzjh"));
        requestData.setBirthCode(jsonObject.getString("cszmh"));

        WjwCszmRequestBody body = new WjwCszmRequestBody();
        body.setData(new ArrayList<>());
        body.getData().add(requestData);
        requestDTO.getBody().add(body);

        requestDTO.getRequestinfo().add(requestInfo);
        requestMap.put("xml", XmlEntityConvertUtil.xstreamToXml(requestDTO));
        return requestMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonObject
     * @return java.util.Map
     * @description 拼凑死亡证明的接口  层级多  用dozer 实现比较复杂 所以写代码拼凑
     */
    public Map getSwzmRequestParam(JSONObject jsonObject){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("api_method","wjwswyxzmcx");
        WjwSwzmRequestDTO requestDTO = new WjwSwzmRequestDTO();
        requestDTO.setBody(new ArrayList<>());
        requestDTO.setRequestinfo(new ArrayList<>());

        WjwSwzmRequestInfo requestInfo = new WjwSwzmRequestInfo();
        requestInfo.setRequestOrgName(orgName);
        requestInfo.setRequestOrgCode(orgCode);
        requestInfo.setRequestDate(DateUtil.formateTime(new Date()));
        requestDTO.getRequestinfo().add(requestInfo);

        WjwSwzmRequestBody body = new WjwSwzmRequestBody();
        body.setData(new ArrayList<>());

        WjwSwzmRequestData data = new WjwSwzmRequestData();
        data.setGmsfzh(jsonObject.getString("zjh"));
        data.setXm(jsonObject.getString("qlrmc"));
        body.getData().add(data);
        requestDTO.getBody().add(body);

        requestDTO.getRequestinfo().add(requestInfo);
        requestMap.put("xml", XmlEntityConvertUtil.xstreamToXml(requestDTO));
        return requestMap;
    }
}
