package cn.gtmap.realestate.exchange.service.impl.hefei;

import cn.gtmap.realestate.common.core.domain.exchange.BdcYhUrlCsDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplaceQighUrlServiceImpl {

    private static final Logger logger =  LoggerFactory.getLogger(ReplaceQighUrlServiceImpl.class);

    private static final String VERSION = "hefei";

    @Autowired
    private EntityMapper entityMapper;

    public String getNewUrl(Object requestBody,String handlerType){
        if (requestBody != null && StringUtils.isNotBlank(handlerType)){
            logger.info("当前获取银行url参数:{}",JSON.toJSONString(requestBody));
            //查询需要替换的新地址
            JSONObject paramDTO = (JSONObject)requestBody;
            if (StringUtils.isNotBlank(paramDTO.getString("qjgldm"))){
                Example example = new Example(BdcYhUrlCsDO.class);
                example.createCriteria()
                        .andEqualTo("yhmc",paramDTO.getString("qjgldm"))
                        .andEqualTo("handlerType",handlerType)
                        .andEqualTo("version",VERSION);
                List<BdcYhUrlCsDO> bdcYhUrlCsDOList = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcYhUrlCsDOList)){
                    return bdcYhUrlCsDOList.get(0).getYhurl();
                }
            }
        }
        return null;
    }

}
