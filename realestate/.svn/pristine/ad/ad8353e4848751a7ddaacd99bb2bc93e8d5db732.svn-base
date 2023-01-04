package cn.gtmap.realestate.certificate.core.support.spring;

import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzAsyncService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzRestTemplateService;
import cn.gtmap.realestate.certificate.core.support.async.AsyncTaskConfig;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 定时器
 */
@Component
public class ProvinceSchedule {

    private final Logger logger = LoggerFactory.getLogger(ProvinceSchedule.class);
    @Autowired
    private BdcDzzzRestTemplateService bdcDzzzRestTemplateService;
    @Autowired
    private BdcDzzzAsyncService bdcDzzzAsyncService;
    @Autowired
    private AsyncTaskConfig asyncTaskConfig;

    @Scheduled(cron = "${schedule_dzzz_syncdzzzinfo}")
    public void syncDzzzInfo(){
        logger.info("省市级数据定时同步开始, 市级同步单位代码 {}，同步数据量：{}", BdcDzzzPdfUtil.DZZZ_CITY_DWDM, BdcDzzzPdfUtil.DZZZ_CITY_SYNC_NUMBER);

        Map paramMap = new HashMap(3);
        paramMap.put("cityDwdm", BdcDzzzPdfUtil.DZZZ_CITY_DWDM);
        paramMap.put("number", BdcDzzzPdfUtil.DZZZ_CITY_SYNC_NUMBER);

        DzzzResponseModel dzzzResponseModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + "/rest/v2.0/zzgl/syncDzzzInfo"
                , JSON.toJSONString(paramMap));
        if (null != dzzzResponseModel && StringUtils.equals(ResponseEnum.SUCCESS.getCode(), dzzzResponseModel.getHead().getStatus())) {
            List<Map<String,Object>> resultList = (List<Map<String,Object>>)dzzzResponseModel.getData();
            if (CollectionUtils.isNotEmpty(resultList)) {
                logger.info("省市级数据定时同步开始, 数量 {}", resultList.size());

                int subCount = asyncTaskConfig.getExecutorPerformNumber();
                int length = resultList.size();
                // 计算可以分成多少组
                int num = (length + subCount - 1) / subCount;
                for (int i = 0; i < num; i ++) {
                    List<Map<String,Object>> newList = new ArrayList<>();
                    // 开始位置
                    int fromIndex = i * subCount;
                    // 结束位置
                    int toIndex = (i + 1) * subCount < length ? (i + 1) * subCount : length;
                    newList.addAll(resultList.subList(fromIndex, toIndex));
                    bdcDzzzAsyncService.syncDzzz(newList);
                }
            }
        }
    }
}
