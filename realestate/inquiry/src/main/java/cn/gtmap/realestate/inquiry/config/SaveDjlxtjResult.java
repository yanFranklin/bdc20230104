package cn.gtmap.realestate.inquiry.config;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpCxTjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDpCxZzTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
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

import java.util.Date;
import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/21/9:37
 * @Description:
 */
@Component
public class SaveDjlxtjResult implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaveDjlxtjResult.class);
    @Autowired
    BdcDpCxService bdcDpCxService;
    @Autowired
    private RedisUtils redisUtils;

    @Value("${saveDjlxtjResult:false}")
    private boolean saveDjlxtjResult;

    @Override
    public void run(String... args) throws Exception {
        if(saveDjlxtjResult){
            BdcDpTjQO bdcDpTjQO = new BdcDpTjQO();
            List<BdcDpCxTjDTO> bdcDpCxTjDTOS = bdcDpCxService.listJrdjlx(bdcDpTjQO);
            redisUtils.addStringValue(CommonConstantUtils.REDIS_INQUIRY_JRDJLX, JSON.toJSONString(bdcDpCxTjDTOS), 60*60);
        }
    }
}
