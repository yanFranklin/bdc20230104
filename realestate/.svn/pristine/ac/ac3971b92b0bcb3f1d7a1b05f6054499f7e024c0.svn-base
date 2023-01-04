package cn.gtmap.realestate.exchange.quartz;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.domain.BdcDbrzjlXqDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 检查接入登簿量是否一致
 * 不一致，接入日志量大于项目表，短信异常提醒
 * 接入日志量小于项目表，则对缺失上报日志的那部分数据参照接入逻辑进行上报操作
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-23 14:15
 **/
@Component
@EnableScheduling
public class CheckJrDblQuartzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckJrDblQuartzService.class);

    @Autowired
    AccessLogMapper accessLogMapper;

    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;

    @Autowired
    AccessLogTypeService accessLogTypeService;

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Scheduled(cron = "${access.jrdbldb.cron:0 0 19 * * ?}")
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_DBJRL, description = "登簿接入量对比", waitTime = 10L, leaseTime = 600L)
    public void CheckJrDbl() {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setCurrentDate(new Date());
        List<BdcXmDO> bdcXmDOList = bdcXmMapper.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //有登簿数据进行比对
            //List 转map key值为xmid 日志表的ywlsh
            //1. 查询当天的数据接入量排除-2 和9 的数据
            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrSjjl(new Date());
            Map<String, BdcAccessLog> bdcAccessLogMap = bdcAccessLogList.stream().collect(Collectors.toMap(BdcAccessLog::getYwlsh, bdcAccessLog -> bdcAccessLog, (k1, k2) -> k2));
            //2. 查询项目表当天的登簿数据
            Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOList.stream().collect(Collectors.toMap(BdcXmDO::getXmid, bdcXmDO -> bdcXmDO));
            //比较数据是否一致
            Set<String> moreTplList = new HashSet<>(1);
            Set<String> lessTplList = new HashSet<>(1);
            LOGGER.warn("当天接入量{}项目登簿量{}", CollectionUtils.size(bdcAccessLogList), CollectionUtils.size(bdcXmDOList));
            if (CollectionUtils.size(bdcAccessLogList) > CollectionUtils.size(bdcXmDOList)) {
                for (BdcAccessLog bdcAccessLog : bdcAccessLogList) {
                    if (!bdcXmDOMap.containsKey(bdcAccessLog.getYwlsh())) {
                        //不在当天登簿的接入数据进行短信发送
                        BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(bdcAccessLog.getYwlsh());
                        if (Objects.nonNull(bdcXmDO)) {
                            moreTplList.add(bdcXmDO.getSlbh());
                        }
                    }
                }
            } else {
                if (CollectionUtils.size(bdcAccessLogList) < CollectionUtils.size(bdcXmDOList)) {
                    //少的那部分数据重新上报
                    for (BdcXmDO bdcXmDO : bdcXmDOList) {
                        //判断是否需要上报
                        boolean sfsb = accesssModelHandlerService.sfsb(bdcXmDO, "", false);
                        //判断是否允许补报
                        BdcDbrzQO bdcDbrzQO = new BdcDbrzQO();
                        bdcDbrzQO.setXmid(bdcXmDO.getXmid());
                        bdcDbrzQO.setCgbs(1);
                        List<BdcDbrzjlXqDO> bdcDbrzjlXqDOList = accessLogMapper.listBdcDbrzjlXq(bdcDbrzQO);
                        //如果是需要上报的，且判断登簿日志详情不存在数据允许补报
                        if (!bdcAccessLogMap.containsKey(bdcXmDO.getXmid()) && sfsb && CollectionUtils.isEmpty(bdcDbrzjlXqDOList)) {
                            LOGGER.warn("数据量接入少于登簿量,且判断是需要上报的,登簿日志详情不存在记录cgbs=1,当前项目受理编号{}xmid{}流程实例id{}，进行重新上报", bdcXmDO.getSlbh(), bdcXmDO.getXmid(), bdcXmDO.getGzlslid());
                            //重新上报一次不在接入日志的
                            lessTplList.add(bdcXmDO.getSlbh());
                            accesssModelHandlerService.autoAccessByXmid(bdcXmDO.getXmid(), false);
                        }
                    }
                }
            }
            String msg = "";
            if (CollectionUtils.isNotEmpty(moreTplList)) {
                LOGGER.warn("当天接入量大于登簿量的单元号数据{}", moreTplList);
                msg = moreTplList.toString();
                //发送通知
                MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_10.getYjlx());
                msgNoticeDTO.setSlbh(msg);
                accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            }
            if (CollectionUtils.isNotEmpty(lessTplList)) {
                LOGGER.warn("当天接入量小于登簿量的受理编号数据{}", lessTplList);
                msg = lessTplList.toString();
                //发送通知
                MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_9.getYjlx());
                msgNoticeDTO.setSlbh(msg);
                accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            }
        }
    }

}
