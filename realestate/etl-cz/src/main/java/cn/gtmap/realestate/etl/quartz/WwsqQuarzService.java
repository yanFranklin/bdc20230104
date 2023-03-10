package cn.gtmap.realestate.etl.quartz;


import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.OpinionDto;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.HlwSlztEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.ProcessTaskRestClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxmDo;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.server.utils.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021-11-02
 * @description ???????????? ??????????????????
 */
@Service
@ConditionalOnProperty("gx.wwsqcj.enable")
public class WwsqQuarzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WwsqQuarzService.class);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????
     */
    private static final Integer WWSQCJ_MAX = 1000;

    private static final String WW_UPDATE_SLZT = "wwsqupdateslzt";

    @Autowired
    private HlwYwxxDataService hlwYwxxDataService;

    @Autowired
    private HlwYwxxService hlwYwxxService;

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    ProcessInstanceClientMatcher processInstanceClientMatcher;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    /**
     * ?????????????????????????????????????????????????????????????????????
     */
    @Value("${gx.wwsqcj.enable:false}")
    private Boolean openWwsqListener;

    /**
     * ??????????????????
     */
    @Value("${gx.wwsqcjcxcj.enable:false}")
    private Boolean openWwsqcjcxcj;

    /**
     * ????????????????????????
     */
    @Value("${gx.wwsqcjzttx.enable:false}")
    private Boolean openWwsqcjzttx;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ??????????????????????????????
     */
    @Value("${gx.wwsqcj.cjsl:100}")
    private Integer cjsl;

    @Scheduled(cron = "${gx.wwsqcj.cron:0 0/30 * * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_WWSQCJ_JOB_NAME, description = "??????????????????????????????", waitTime = 30L, leaseTime = 600L)
    public void listenWwsqcj() {
        try {
            if (!Boolean.TRUE.equals(openWwsqListener)) {
                LOGGER.info("??????????????????????????????,???????????????????????????????????????");
                return;
            }
            //???????????????????????????
            Integer cjNumMax = cjsl < WWSQCJ_MAX ? cjsl : WWSQCJ_MAX;
            List<GxWwSqxmDo> sqxmDoList = hlwYwxxDataService.listWcjYhWwsqXmidList();
            Integer count = CollectionUtils.isNotEmpty(sqxmDoList) ? sqxmDoList.size() : 0;
            LOGGER.info("??????????????????????????????????????????,?????????????????????????????????{}???,???????????????????????????????????????{}", count, cjNumMax);
            cjBdcXm(cjNumMax, sqxmDoList, false);
        } catch (Exception e) {
            LOGGER.error("?????????????????????????????????????????????{}", e.getMessage(), e);
        }
    }

    private void cjBdcXm(Integer cjNumMax, List<GxWwSqxmDo> sqxmDoList, Boolean cxcj) {
        Integer cjNum = 0;
        Integer successNum = 0;
        Integer failNum = 0;
        if (CollectionUtils.isNotEmpty(sqxmDoList)) {
            for (GxWwSqxmDo gxWwSqxmDo : sqxmDoList) {
                Map<String, Object> resultMap = new HashMap<>();
                try {
                    resultMap = hlwYwxxService.cjBdcXm(gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), "", cxcj);
                } catch (Exception e) {
                    LOGGER.error("????????????ID:{} ???????????????{}???????????????????????????:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), e);
                    try {
                        JSONObject hxObject = new JSONObject();
                        hxObject.put("xmid", gxWwSqxmDo.getXmid());
                        hxObject.put("byslyy", "????????????????????????");
                        hxObject.put("shzt", Constants.SHZT_ABANDON);
                        hxObject.put("czrmc", "????????????");
                        hlwYwxxService.hxDjxx(hxObject);
                    } catch (Exception error) {
                        LOGGER.error("????????????ID:{} ???????????????{}?????????????????????,????????????:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(),error);
                    }
                }
                cjNum++;
                if (StringUtils.isNotBlank(MapUtils.getString(resultMap, "gzlslid"))) {
                    successNum++;
                    LOGGER.info("????????????ID:{}???????????????{}??????????????????????????????????????????ID:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), MapUtils.getString(resultMap, "gzlslid"));
                } else {
                    failNum++;
                    String msg = MapUtils.getString(resultMap, "msg");
                    LOGGER.info("????????????ID:{}???????????????{}???????????????????????????:{}", gxWwSqxmDo.getXmid(), gxWwSqxmDo.getSqslbh(), StringUtils.isNotBlank(msg) ? msg : "????????????,????????????????????????");
                }
                if (cjNum.equals(cjNumMax)) {
                    break;
                }
            }
        }
        LOGGER.info("??????????????????????????????????????????,?????????????????????{}???,???????????????{}???", successNum, failNum);
    }


    @Scheduled(cron = "${gx.wwsqcjcxcj.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_CXWWSQCJ_JOB_NAME, description = "????????????????????????????????????", waitTime = 30L, leaseTime = 600L)
    public void reWwsqcj() {
        if (!Boolean.TRUE.equals(openWwsqcjcxcj)) {
            LOGGER.info("??????????????????????????????,?????????????????????????????????????????????");
            return;
        }
        try {
            //???????????????????????????xmid
            Date dayBeginTime = DateUtil.getDayBeginTime(new Date());
            Date dayEndTime = DateUtil.getDayEndTime(new Date());
            List<String> sbXmids = hlwYwxxDataService.listFailedWwsqXmidList(dayBeginTime, dayEndTime);
            if (CollectionUtils.isEmpty(sbXmids)) {
                LOGGER.info("??????????????????????????????????????????????????????,?????????????????????");
                return;
            }
            LOGGER.info("??????????????????????????????????????????????????????{}???", sbXmids.size());
            //????????????
            List<List<String>> partition = Lists.partition(sbXmids, 100);
            for (List<String> xmids : partition) {
                //????????????????????????
                List<GxWwSqxmDo> sqxmDoList = hlwYwxxDataService.listWwsqListByXmids(xmids);
                if (CollectionUtils.isNotEmpty(sqxmDoList)) {
                    cjBdcXm(sqxmDoList.size() + 1, sqxmDoList, true);
                }
            }
        } catch (Exception e) {
            LOGGER.error("???????????????????????????????????????????????????{}", e.getMessage(), e);
        }
    }


    @Scheduled(cron = "${gx.wwsqcjzttx.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.QUARTZ_WWSQZTTZ_JOB_NAME, description = "????????????????????????????????????", waitTime = 30L, leaseTime = 600L)
    public void updateSlzt() {
        if (!Boolean.TRUE.equals(openWwsqcjzttx)) {
            LOGGER.info("??????????????????????????????,?????????????????????????????????????????????");
            return;
        }
        try {
            //?????????????????????????????????xmid
            Date dayBeginTime = DateUtil.getDayBeginTime(new Date());
            Date dayEndTime = DateUtil.getDayEndTime(new Date());
            List<String> gzlslids = hlwYwxxDataService.listSuccessWwsqXmidList(dayBeginTime, dayEndTime);
            if (CollectionUtils.isEmpty(gzlslids)) {
                LOGGER.info("????????????????????????,???????????????");
                return;
            }
            LOGGER.info("??????????????????????????????{}???", gzlslids.size());
            for (String gzlslid : gzlslids) {
                try {
                    //??????xm??????
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setGzlslid(gzlslid);
                    List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isEmpty(bdcXmDOS)) {
                        continue;
                    }
                    BdcXmDO bdcXmDO = bdcXmDOS.get(0);
                    LOGGER.info("??????????????????????????????gzlslid{}???????????????{}", gzlslid,bdcXmDO.getSpxtywh());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("processInsId", gzlslid);
                    //??????????????????????????????????????????
                    if (bdcXmDO.getAjzt().equals(CommonConstantUtils.AJZT_YHCH_DM)) {
                        jsonObject.put("isDelete", HlwSlztEnum.ABANDON.getSlzt());
                        //??????????????????
                        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
                        bdcCzrzDO.setGzlslid(gzlslid);
                        bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_WWTJ.key());
                        List<BdcCzrzDO> bdcCzrzDOS = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
                        if(CollectionUtils.isNotEmpty(bdcCzrzDOS) && StringUtils.isNotBlank(bdcCzrzDOS.get(0).getCzyy())){
                            jsonObject.put("opinion", bdcCzrzDOS.get(0).getCzyy());
                        }
                    } else if (bdcXmDO.getAjzt().equals(CommonConstantUtils.AJZT_YB_DM)) {
                        jsonObject.put("isDelete", HlwSlztEnum.SZDB.getSlzt());
                    } else {
                        continue;
                    }
                    LOGGER.info("???????????????????????????hlwslh:{},????????????:{}", bdcXmDO.getSpxtywh(), jsonObject);
                    exchangeInterfaceFeignService.requestInterface(WW_UPDATE_SLZT, jsonObject);
                } catch (Exception error) {
                    LOGGER.error("????????????ID:{} ?????????????????????,????????????:{}", gzlslid, error);
                }
            }
        } catch (Exception e) {
            LOGGER.error("???????????????????????????????????????????????????{}", e.getMessage(), e);
        }
    }
}
