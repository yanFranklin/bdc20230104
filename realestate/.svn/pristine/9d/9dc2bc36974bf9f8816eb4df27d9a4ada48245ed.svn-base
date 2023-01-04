package cn.gtmap.realestate.exchange.service.impl.inf.gd;

import cn.gtmap.realestate.common.core.dto.accept.ListFileDTO;
import cn.gtmap.realestate.exchange.service.inf.gd.GdService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @date 2021/06/01 15:23
 */
@Service
public class GdServiceImpl implements GdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GdService.class);

    @Override
    public List dealWithFjxxResponseStr2List(Object object) {
        if (object instanceof String){
            String jsonStr = (String)object;
            if (StringUtils.isNotBlank(jsonStr)){
                LOGGER.info("gdfjxx返回参数:{}",jsonStr);
                return JSON.parseArray(jsonStr, ListFileDTO.class);
            }
        }
        if (object instanceof JSONArray){
            JSONArray jsonArray = (JSONArray) object;
            LOGGER.info("gdfjxx返回参数:{}",jsonArray.toJSONString());
            return JSON.parseArray(jsonArray.toJSONString(), ListFileDTO.class);
        }
        return null;
    }

}
