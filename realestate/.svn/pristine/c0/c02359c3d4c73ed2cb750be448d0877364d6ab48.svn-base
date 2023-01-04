package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.config.SdqConfig;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-12-28
 * @description 南通水气替换url
 */
@Service
public class NtsqReplaceUrlServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(NtsqReplaceUrlServiceImpl.class);

    @Autowired
    SdqConfig sdqConfig;

    /**
     * 通州水气的appkey
     */
    @Value("${sdq.tzsq.appkey:}")
    private String appkey;

    public String getNewUrl(Object requestBody, String replaceUrl) {
        if (requestBody != null && StringUtils.isNotBlank(replaceUrl)) {
            String url = "";
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestBody));
            if (StringUtils.isNotBlank(appkey)) {
                jsonObject = jsonObject.getJSONObject("body");
            }
            if (StringUtils.isNotBlank(jsonObject.getString("qxdm"))) {
                String qxdm = jsonObject.getString("qxdm");
                String ywlx = jsonObject.getString("ywlx");
                String dwdm = jsonObject.getString("dwdm");
                String jklx = jsonObject.getString("jklx");
                if (StringUtils.isBlank(qxdm) || StringUtils.isBlank(ywlx) || StringUtils.isBlank(dwdm)) {
                    return "";
                }
                logger.info("南通水电气获取请求地址url，配置项:{},区县代码:{},单位代码:{},业务类型：{}", replaceUrl, qxdm, dwdm, ywlx);
                SdqConfig.UnitInfo unitInfo = new SdqConfig.UnitInfo();
                if (Objects.equals(BdcSdqEnum.S.key().toString(), ywlx)) {
                    if (Objects.nonNull(sdqConfig.getGsdwxx())) {
                        unitInfo = sdqConfig.getGsdwxx().get(qxdm).get(dwdm);
                    }
                } else if (Objects.equals(BdcSdqEnum.Q.key().toString(), ywlx)) {
                    if (Objects.nonNull(sdqConfig.getGqdwxx())) {
                        unitInfo = sdqConfig.getGqdwxx().get(qxdm).get(dwdm);
                    }
                }
                if (Objects.nonNull(unitInfo)) {
                    // 核验请求地址
                    if ("hy".equals(jklx)) {
                        url = unitInfo.getCheckUrl();
                    }
                    // 过户请求地址
                    if ("gh".equals(jklx)) {
                        url = unitInfo.getGhUrl();
                    }
                }
                return url;
            }
        }
        return null;
    }

}
