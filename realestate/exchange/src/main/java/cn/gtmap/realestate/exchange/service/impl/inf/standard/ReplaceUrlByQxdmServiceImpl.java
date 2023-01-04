package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.domain.exchange.BdcYhUrlCsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.YanchengReplaceUrlServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/11  11:03
 * @description 根据区县代码获取地址
 */
@Service
public class ReplaceUrlByQxdmServiceImpl {
    private static final Logger logger =  LoggerFactory.getLogger(ReplaceUrlByQxdmServiceImpl.class);


    public String getNewUrl(Object requestBody,String replaceUrl){
        if (requestBody != null && StringUtils.isNotBlank(replaceUrl)){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestBody));
            if (StringUtils.isNotBlank(jsonObject.getString("qxdm"))){
                String qxdm = jsonObject.getString("qxdm");
                logger.info("根据区县代码获取请求地址url，配置项:{},区县代码:{}", replaceUrl,qxdm);
                String url = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(replaceUrl, "", qxdm);
                return url;
            }
        }
        return null;
    }

}
