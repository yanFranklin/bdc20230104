package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SendMsgService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 检查前一天的登簿日志是否上报
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-01 13:43
 **/
@Service
public class DbSfjrQuartzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbSfjrQuartzService.class);
    @Autowired
    AccessLogMapper accessLogMapper;
    @Autowired
    AccessLogTypeService accessLogTypeService;

    @Autowired
    BdcdjMapper bdcdjMapper;

    @Autowired
    AccessLogService accessLogService;

    @Autowired
    SendMsgService sendMsgService;

    @Value("#{'${dbrzbcsb.lxdh:}'.split(',')}")
    private List<String> lxdhList;

    @Value("${dbrzbcsb.msgtype:}")
    private String msgType;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 前一天登簿接入量比对
     * @date : 2022/7/1 13:59
     */
    @Scheduled(cron = "${lastday.dbsfsb.cron:0 0 04 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_DBSB, description = "登簿日志是否上报", waitTime = 10L, leaseTime = 600L)
    public void checkYesterDayDbjrl() {
        LOGGER.warn("开始检查前一天的登簿日志是否上报");
        String today = DateUtils.formateTime(new Date(), DateUtils.DATE_FORMAT_2);
        List<Map<String, String>> qxdmlist = bdcdjMapper.queryBdcZdQx();
        LOGGER.info("需要上报的区县有{}", qxdmlist);
        if (CollectionUtils.isNotEmpty(qxdmlist)) {
            BdcDbrzQO bdcDbrzQO = new BdcDbrzQO();
            //前一天时间
            bdcDbrzQO.setAccessDate(DateUtils.formatDate(DateUtils.getYesterDay(today)));
            //sjcgbs=1
            bdcDbrzQO.setCgbs(1);
            for (Map<String, String> map : qxdmlist) {
                String qxdm = String.valueOf(map.get("DM"));
                String qxdmMc = String.valueOf(map.get("MC"));
                //查询当前区县的前一天登簿日志是否成功上报
                if (StringUtils.isNotBlank(qxdm)) {
                    bdcDbrzQO.setQxdm(qxdm);
                    List<BdcJrDbrzjlDO> bdcJrDbrzjlDOList = accessLogMapper.listDbrzjl(bdcDbrzQO);
                    LOGGER.warn("检查前一天登簿日志是否成功上报查询入参{},查询结果{}条", JSON.toJSONString(bdcDbrzQO), CollectionUtils.size(bdcJrDbrzjlDOList));
                    if (CollectionUtils.isEmpty(bdcJrDbrzjlDOList)) {
                        LOGGER.error("当前区县{}昨天{}登簿日志没有sjcgbs=1 的数据，开始重新补报登簿日志", qxdmMc, DateUtils.formateTime(bdcDbrzQO.getAccessDate(), DateUtils.DATE_FORMAT_2));
                        accessLogService.accessLog(bdcDbrzQO.getAccessDate(), qxdm);
                        //发送短信提醒
                        if (CollectionUtils.isNotEmpty(lxdhList)) {
                            Map paramMap = new HashMap(3);
                            paramMap.put("qxdm", qxdmMc);
                            paramMap.put("date", DateUtils.formateTime(bdcDbrzQO.getAccessDate(), DateUtils.DATE_FORMAT_2));
                            for (String lxdh : lxdhList) {
                                LOGGER.warn("开始发送短信{}", lxdh);
                                paramMap.put("dh", lxdh);
                                try {
                                    if (sendMsgService.checkPhone(lxdh)) {
                                        sendMsgService.smsMsg(paramMap, lxdh, msgType);
                                        sendMsgService.saveMsgLog(null, String.valueOf(paramMap), lxdh, "1", "发送成功");
                                    } else {
                                        sendMsgService.saveMsgLog(null, String.valueOf(paramMap), lxdh, "0", "电话号格式不正确");
                                    }
                                } catch (Exception e) {
                                    LOGGER.error("联系电话{}发送短信失败", lxdh, e);
                                }
                            }

                        } else {
                            //发送通知
                            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_11.getYjlx());
                            msgNoticeDTO.setSlbh(DateUtils.formateTime(bdcDbrzQO.getAccessDate(), DateUtils.DATE_FORMAT_2) + "登簿日志未成功上报于" + DateUtils.formateTime(new Date(), DateUtils.DATE_FORMAT) + "进行补报");
                            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                        }
                    }
                }
            }
        }
    }
}
