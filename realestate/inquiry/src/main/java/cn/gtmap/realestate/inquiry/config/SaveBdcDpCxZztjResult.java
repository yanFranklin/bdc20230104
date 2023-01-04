package cn.gtmap.realestate.inquiry.config;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpCxZzTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.service.BdcDpCxService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/16/11:35
 * @Description:
 */
@Component
public class SaveBdcDpCxZztjResult implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveBdcDpCxZztjResult.class);
    @Autowired
    BdcDpCxService bdcDpCxService;
    @Autowired
    private RedisUtils redisUtils;

    @Value("${saveZztjResult:false}")
    private boolean saveZztjResult;
    @Override
    public void run(String... args) throws Exception {
        String stringValue = redisUtils.getStringValue(CommonConstantUtils.REDIS_INQUIRY_ZZTJ);
        if(saveZztjResult && StringUtils.isBlank(stringValue)){
            BdcDpTjQO bdcDpTjQO = new BdcDpTjQO();
            BdcDpCxZzTjDTO zzTj = bdcDpCxService.getZzTj(bdcDpTjQO);
            redisUtils.addStringValue(CommonConstantUtils.REDIS_INQUIRY_ZZTJ, JSON.toJSONString(zzTj), 60*60);
        }
    }
}
