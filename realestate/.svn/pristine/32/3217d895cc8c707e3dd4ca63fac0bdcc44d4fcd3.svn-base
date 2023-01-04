package cn.gtmap.realestate.exchange.service.rabbitmq;

import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.domain.BdcDbWjrDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.accessLog.AccessLogMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.CityAccess;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: realestate
 * @description: 登簿接入mq消费信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-28 20:52
 **/
@Service
public class RegisterAccessCheckMqConsumer extends MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RegisterAccessCheckMqConsumer.class);
    private final ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(50);
    @Value("${access.pldy.single:false}")
    private boolean accessPldy;
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    AccessLogMapper accessLogMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;
    @Autowired
    AccessLogTypeService accessLogTypeService;

    /**
     * 国家上报
     */
    @Value("${nationalAccess.switch:false}")
    public boolean nationalSwtich;

    /**
     * 省级上报
     */
    @Value("${provinceAccess.switch:true}")
    public boolean provinceSwtich;

    /**
     * 市级上报
     */
    @Value("${cityAccess.switch:false}")
    public boolean citySwtich;


    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "register-access-queue")
    @RabbitHandler
    public void checkDbjr(String msg, Channel channel, Message message) throws IOException {
        logger.warn("登簿接入对比mq接受到消息{}", msg);
        Map<String, Object> logData = new HashMap<>();
        logData.put("msg", msg);
        String logName = RabbitMqEnum.QueueName.BDCDBHJSBQUEUE.getCode();
        AuditEvent event = new AuditEvent(logName, logName, logData);
        zipkinAuditEventRepository.newSpanTag(event, logName);
        consumer(msg, channel, message);
    }


    /**
     * 消费者处理消息
     *
     * @param msg
     */
    @Override
    public void processMsg(String msg) {

        /*
         * 修改为异步消费，定时半小时后操作
         *
         * */
        mScheduledExecutorService.schedule(() -> {
            // 逻辑处理
            dealMsg(msg);
        }, 30 * 60L, TimeUnit.SECONDS);
        logger.warn("消费完成{}", msg);

    }

    private BdcDbWjrDO dealWjrxx(BdcXmDO bdcXmDO) {
        BdcDbWjrDO bdcDbWjrDO = new BdcDbWjrDO();
        bdcDbWjrDO.setId(UUIDGenerator.generate16());
        bdcDbWjrDO.setSlbh(bdcXmDO.getSlbh());
        bdcDbWjrDO.setGzlslid(bdcXmDO.getGzlslid());
        bdcDbWjrDO.setXmid(bdcXmDO.getXmid());
        bdcDbWjrDO.setQllx(bdcXmDO.getQllx());
        bdcDbWjrDO.setClzt(0);
        bdcDbWjrDO.setGxsj(new Date());
        return bdcDbWjrDO;
    }

    public void dealMsg(String msg) {
        //对比当前已经登簿的数据是否已经上报，不管是否需要上报，只要接入表不存在数据就插入数据
        //当前传参为工作流实例id，判断上报逻辑
        //1. 判断流程类型
        if (StringUtils.isNotBlank(msg)) {
            List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(msg);
            if (CollectionUtils.isEmpty(bdcXmList)) {
                LOGGER.warn("当前流程实例id{}未查询到项目信息", msg);
                return;
            }
            int lclx = bdcXmFeignService.makeSureBdcXmLx(msg);
            //判断是否是批量抵押只上报一条
            switch (lclx) {
                case 1:
                case 2:
                    //流程实例id查项目表循环项目查询
                    for (BdcXmDO bdcXmDO : bdcXmList) {
                        LOGGER.warn("当前流程实例id{}流程类型{}当前项目id{},查询接入表数据", msg, lclx, bdcXmDO.getXmid());
                        List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrsj(bdcXmDO.getXmid());
                        if (CollectionUtils.isEmpty(bdcAccessLogList)) {
                            //判断是否是需要上报的接入的
                            boolean sfxysb = accesssModelHandlerService.sfsb(bdcXmDO, "", false);
                            if (sfxysb) {
                                saveDbWjrJrrz(bdcXmDO, "登簿后半小时检查发现未生成接入数据");
                            }
                        }
                    }
                    break;
                case 3:
                    if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmList.get(0).getQllx()) && accessPldy) {
                        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmList.get(0).getXmid());
                        if (bdcCshFwkgSlDO != null && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                            //由于上报的时候只有一条，所以接入表只存了一个xmid，循环数据只要查到一条就返回，否则全部插入
                            boolean sfsb = false;
                            LOGGER.warn("当前流程实例id{}流程类型{}当前项目id{},受理编号{},当前是抵押权,且设置了批量抵押上报,循环查询接入表数据，有一条就表示接入了", msg, lclx, bdcXmList.get(0).getXmid(), bdcXmList.get(0).getSlbh());
                            for (BdcXmDO bdcXmDO : bdcXmList) {
                                List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrsj(bdcXmDO.getXmid());
                                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                                    //只要查到存在数据结束循环
                                    sfsb = true;
                                    break;
                                }
                            }
                            //不存在数据，插入记录表
                            if (!sfsb) {
                                boolean sfxysb = accesssModelHandlerService.sfsb(bdcXmList.get(0), "", false);
                                if (sfxysb) {
                                    saveDbWjrJrrz(bdcXmList.get(0), "登簿后半小时检查发现未生成接入数据");
                                }
                            }
                        }
                    } else {
                        // 循环项目查询
                        LOGGER.warn("当前流程实例id{}流程类型{},当前是抵押权,未批量抵押上报,根据xmid查询接入表数据", msg, lclx);
                        for (BdcXmDO bdcXmDO : bdcXmList) {
                            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrsj(bdcXmDO.getXmid());
                            if (CollectionUtils.isEmpty(bdcAccessLogList)) {
                                boolean sfxysb = accesssModelHandlerService.sfsb(bdcXmList.get(0), "", false);
                                if (sfxysb) {
                                    saveDbWjrJrrz(bdcXmList.get(0), "登簿后半小时检查发现未生成接入数据");
                                }
                            }
                        }
                    }
                    break;
                case 4:
                    //批量组合，产权的循环项目，抵押的判断是否只上报一条
                    // 非抵押的不动产项目
                    List<BdcXmDO> cqBdcXmList = bdcXmList.stream()
                            .filter(item -> !CommonConstantUtils.QLLX_DYAQ_DM.equals(item.getQllx()))
                            .collect(Collectors.toList());
                    // 抵押的不动产项目
                    List<BdcXmDO> dyaqXmList = bdcXmList.stream()
                            .filter(item -> CommonConstantUtils.QLLX_DYAQ_DM.equals(item.getQllx()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(cqBdcXmList)) {
                        LOGGER.warn("当前流程实例id{},批量组合流程,非抵押权类项目数量{},根据xmid查接入表", msg, CollectionUtils.size(cqBdcXmList));
                        for (BdcXmDO bdcXmDO : cqBdcXmList) {
                            List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrsj(bdcXmDO.getXmid());
                            if (CollectionUtils.isEmpty(bdcAccessLogList)) {
                                boolean sfxysb = accesssModelHandlerService.sfsb(bdcXmDO, "", false);
                                if (sfxysb) {
                                    saveDbWjrJrrz(bdcXmDO, "登簿后半小时检查发现未生成接入数据");
                                }
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(dyaqXmList)) {
                        //此处只需要判断这个配置就行了，肯定是抵押权，且肯定是生成权利的
                        if (accessPldy) {
                            //由于上报的时候只有一条所以接入表只存了一个xmid，循环数据只要查到一条就返回，否则全部插入
                            boolean sfsb = false;
                            for (BdcXmDO bdcXmDO : dyaqXmList) {
                                List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrsj(bdcXmDO.getXmid());
                                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                                    //只要查到存在数据结束循环
                                    sfsb = true;
                                    break;
                                }
                            }
                            //不存在数据，插入记录表
                            if (!sfsb) {
                                boolean sfxysb = accesssModelHandlerService.sfsb(dyaqXmList.get(0), "", false);
                                if (sfxysb) {
                                    saveDbWjrJrrz(dyaqXmList.get(0), "登簿后半小时检查发现未生成接入数据");
                                }
                            }
                        } else {
                            //循环查询
                            for (BdcXmDO bdcXmDO : dyaqXmList) {
                                List<BdcAccessLog> bdcAccessLogList = accessLogMapper.listBdcJrsj(bdcXmDO.getXmid());
                                if (CollectionUtils.isEmpty(bdcAccessLogList)) {
                                    boolean sfxysb = accesssModelHandlerService.sfsb(dyaqXmList.get(0), "", false);
                                    if (sfxysb) {
                                        saveDbWjrJrrz(dyaqXmList.get(0), "登簿后半小时检查发现未生成接入数据");
                                    }
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理异常记录消息
     *
     * @param message
     */
    @Override
    public void saveErrorMsg(Message message) {
        logger.error("处理登簿未接入消息处理失败：{}", JSON.toJSONString(message));
    }

    public void saveDbWjrJrrz(BdcXmDO bdcXmDO, String xyxx) {
        if (nationalSwtich) {
            accesssModelHandlerService.saveBdcAccess(new NationalAccess(), JrRzCgbsEnum.DB_WJR.getBs(), bdcXmDO, xyxx);
        }
        if (provinceSwtich) {
            accesssModelHandlerService.saveBdcAccess(new ProvinceAccess(), JrRzCgbsEnum.DB_WJR.getBs(), bdcXmDO, xyxx);
        }
        if (citySwtich) {
            accesssModelHandlerService.saveBdcAccess(new CityAccess(), JrRzCgbsEnum.DB_WJR.getBs(), bdcXmDO, xyxx);
        }
        //发送短信提醒
        //发送通知
        MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
        msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_0.getYjlx());
        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
        accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
    }
}
