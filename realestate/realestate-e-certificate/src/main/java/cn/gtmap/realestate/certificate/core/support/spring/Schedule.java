package cn.gtmap.realestate.certificate.core.support.spring;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzAsyncService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzHttpService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxService;
import cn.gtmap.realestate.certificate.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 定时器
 */
@Component
public class Schedule {

    private final Logger logger = LoggerFactory.getLogger(Schedule.class);
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzHttpService bdcDzzzHttpService;
    @Autowired
    private BdcDzzzAsyncService bdcDzzzAsyncService;
    //private String controller = "Schedule/zzxxIncrementData";

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 每天定时检查电子证照未生成PDF数据
     */
    @Scheduled(cron = "${schedule_dzzz_pdf_not_produced}")
   public void zzxxIncrementData(){
        logger.info("定时器检测昨天未生成PDF数据开始：开始时间：{}", DateUtil.now());

        Map map = new HashMap<>();
        Date cjsjqssj = DateUtil.getYesterdayStart();
        Date cjsjjssj = DateUtil.getYesterdayEnd();
        map.put("cjsjqssj", cjsjqssj);
        map.put("cjsjjssj", cjsjjssj);

        List<BdcDzzzZzxxDO> listBdzDzzzZzxx = bdcDzzzZzxxService.listBdcDzzzZzxx(map);

        if (CollectionUtils.isNotEmpty(listBdzDzzzZzxx)) {
            logger.info("定时器检测到昨天未生成PDF的数据量：{}", listBdzDzzzZzxx.size() + "条。");
            for (BdcDzzzZzxxDO bdcDzzzZzxxDO : listBdzDzzzZzxx) {
                if (null != bdcDzzzZzxxDO && StringUtils.isBlank(bdcDzzzZzxxDO.getZzqzlj())) {
                    bdcDzzzAsyncService.zzxxIncrement(bdcDzzzZzxxDO);
                }
            }
        }
   }

    /*@Async
    public void zzxxIncrement(BdcDzzzZzxxDO bdcDzzzZzxxDO){
        String flag = UUIDGenerator.generate();
        BdcDzzzLogDO bdcDzzzLogDOReq = bdcDzzzLogService.getRequestData(controller, flag, "zzxxIncrement", JSON.toJSONString(bdcDzzzZzxxDO), controller);
        bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDOReq);

        DzzzResponseModel resultModel = bdcDzzzZzxxService.createPdfBdcDzzzByZzid(bdcDzzzZzxxDO.getZzid());

        BdcDzzzLogDO bdcDzzzLogDORes = bdcDzzzLogService.getResponseData(controller, flag, JSON.toJSONString(resultModel));
        bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDORes);
    }*/

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 每天定时检查不动产登记数据与证照库数据是否同步
     */
    /*public void bdcdjIncrementData(){
        StringBuilder paramString = new StringBuilder();
        Date qssj = DateUtil.getYesterdayStart();
        Date jssj = DateUtil.getYesterdayEnd();
        paramString.append("{\"fzrqSta\":").append(qssj.getTime()).append(",");
        paramString.append("\"fzrqEnd\":").append(jssj.getTime()).append("}");

        String url = BdcDzzzPdfUtil.BDCDJ_URL + "/bdcDzzz/checkUnsynchronizedData";
        try {
            byte[] result = bdcDzzzHttpService.doPostWithString(url,paramString.toString());
            if (null != result) {
                logger.info("不动产登记数据与证照库数据同步检测开始：检查时间：{}", DateUtil.now());
            }
        } catch (IOException e) {
            logger.error("Schedule:bdcdjIncrementData", e);
        }
    }*/
}
