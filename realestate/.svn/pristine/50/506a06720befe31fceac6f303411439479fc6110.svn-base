package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.domain.register.BdcBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.building.*;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcCflxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcHdhsfeDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcdySumDTO;
import cn.gtmap.realestate.common.core.dto.register.XxblDbDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.building.BdcTddysfxxQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwLjzFeginService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDyafeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDzzzCzRestService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.matcher.FlowableNodeClientMatcher;
import cn.gtmap.realestate.common.matcher.TaskHandleClientMatcher;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.register.core.dto.BdcWlzxcqXzqlDTO;
import cn.gtmap.realestate.register.core.mapper.BdcCfMapper;
import cn.gtmap.realestate.register.core.mapper.BdcRegisterConfigMapper;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.core.qo.BdcWlzxcqXzqlQO;
import cn.gtmap.realestate.register.core.qo.BdcXmGxQO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.*;
import cn.gtmap.realestate.register.core.service.impl.BdcYgServiceImpl;
import cn.gtmap.realestate.register.core.thread.BdcdyxxThread;
import cn.gtmap.realestate.register.rabbitMq.RegisterAccessCheckMqSender;
import cn.gtmap.realestate.register.rabbitMq.SynQjBdcdyztMqService;
import cn.gtmap.realestate.register.rabbitMq.SyncHdhsfeMqService;
import cn.gtmap.realestate.register.service.*;
import cn.gtmap.realestate.register.util.Constants;
import cn.gtmap.realestate.register.util.ConvertUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/2
 * @description 登簿信息服务实现类
 */
@Service
public class BdcDbxxServiceImpl implements BdcDbxxService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDbxxServiceImpl.class);
    /**
     * 登簿时时间差 默认 60s
     */
    private static final Long INTERVAL = 60000L;

    /**
     * 注销预查封，生成正式查封
     */
    private static final String FUNCTION_INITCF = "注销预查封，生成正式查封功能";

    /**
     * 权利人
     */
    @Value("#{'${dbEvent.noPushQj-tdfw-table.fw_fcqlr:}'.split(',')}")
    private Integer[] BDC_QLR_DJLX_ARR;
    /**
     * 房地产权-户室
     */
    @Value("#{'${dbEvent.noPushQj-tdfw-table.fw_hs:}'.split(',')}")
    private Integer[] BDC_FDCQ_HS_DJLX_ARR;
    /**
     * 房地产权-逻辑幢
     */
    @Value("#{'${dbEvent.noPushQj-tdfw-table.fw_ljz:}'.split(',')}")
    private Integer[] BDC_FDCQ_LJZ_DJLX_ARR;
    /**
     * 房地产权-房地产权项目信息
     */
    @Value("#{'${dbEvent.noPushQj-tdfw-table.fw_xmxx:}'.split(',')}")
    private Integer[] BDC_FDCQ_FDCQXM_DJLX_ARR;
    /**
     * 房地产权-房地产权项目信息
     */
    @Value("#{'${dbEvent.noPushQj-tdfw-table.fw_ychs:}'.split(',')}")
    private Integer[] BDC_YG_DJLX_ARR;

    /**
     * 注销电子证照服务版本
     */
    @Value("${zxdzzz.version:}")
    private String zxdzzzFwVersion;

    /*
     * 登簿是否注销原权利电子证照
     * */
    @Value("${dbzxyql.zxdzzz:false}")
    private boolean dbzxYqlDzzz;

    /**
     * 撤销登记
     */
    @Value("#{'${cxdj.gzldyid:}'.split(',')}")
    private List<String> cxdjDyids;

    /**
     * 信息补录项目来源 默认为3 其他
     */
    @Value("${xxbl.xmly:3}")
    private String XXBL_XMLY;

    /**
     * 房地产权首次登记更新外联证书的权利附记追加字符串模板
     */
    @Value("${spfscdj.wlzsfjzj:该土地上已办理 xmmc小区商品房及业务共有部分首次登记证：dbsj  xmmc小区dh幢。}")
    private String fjStrTemp;

    @Value("${zjgcdy.updatetdztemp:}")
    private String updatetdztemp;

    /**
     * 外联产权注销,带入产权上的抵押时，当前配置的抵押流程无需带入
     */
    @Value("#{'${wlcqzx.dyaq.pcgzldyid:}'.split(',')}")
    private List<String> pcDyaqDyids;

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcQlxxService bdcQlxxService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcDjbxxService bdcDjbxxService;
    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;
    @Autowired
    BdcFdcqFdcqXmService bdcFdcqFdcqXmService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    BdcYgServiceImpl bdcYgService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    SynQjBdcdyztMqService synQjBdcdyztMqService;
    @Autowired
    FlowableNodeClientMatcher flowableNodeClient;
    @Autowired
    TaskHandleClientMatcher taskHandleClient;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcdyZtService bdcdyZtService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    Set<BdcQlService> bdcQlServiceSet;
    @Autowired
    ECertificateFeignService eCertificateFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkin;
    @Autowired
    BdcRegisterConfigMapper bdcRegisterConfigMapper;
    @Autowired
    BdcDzzzCzRestService bdcDzzzCzRestService;
    @Autowired
    private EntityService entityService;
    @Autowired
    BdcdySdService bdcdySdService;
    @Autowired
    BdcBdcdyService bdcBdcdyService;
    @Autowired
    BdcFdcqService bdcFdcqService;
    @Autowired
    SyncHdhsfeMqService syncHdhsfeMqService;
    @Autowired
    RegisterAccessCheckMqSender registerAccessCheckMqSender;
    @Autowired
    BdcCfMapper bdcCfMapper;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwLjzFeginService fwLjzFeginService;
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    BdcDyafeignService bdcDyafeignService;

    @Autowired
    BdcTddysfDyhService bdcTddysfDyhService;

    @Autowired
    BdcDyhGzFeignService bdcDyhGzFeignService;

    @Autowired
    private BdcInitFeignService bdcInitFeignService;

    @Value("${xxbl.hfql.yzbdc:true}")
    private boolean xxblYzbdcqzh;
    @Value("${xxbl.hfql.hfdyq:false}")
    private boolean xxblHfdyq;
    /**
     * 登簿时候异步注销证照线程池
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 600, TimeUnit.SECONDS,
            // 同时进行登簿操作的项目数量一般不会超过上千，这里设置的队列容量满足同时进行注销证照的任务量
            new ArrayBlockingQueue<>(1000),
            new BasicThreadFactory.Builder().namingPattern("hefei-db-zxdzzz").build(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * @param xxblDbDTO 信息补录登簿对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 信息补录登簿
     */
    @Override
    public void updateBdcDbxxAndQszt(XxblDbDTO xxblDbDTO) {
        if (!CheckParameter.checkAllParameter(xxblDbDTO)) {
            throw new MissingArgumentException("XxblDbDTO 有字段为空！");
        }

        DbxxQO dbxxQO = new DbxxQO();
        dbxxQO.setGzlslid(xxblDbDTO.getGzlslid());
        dbxxQO.setDbr(xxblDbDTO.getDbr());
        dbxxQO.setDjjg(xxblDbDTO.getDjjg());
        dbxxQO.setDjsj(xxblDbDTO.getDjsj());

        dbxxQO.setQszt(CommonConstantUtils.QSZT_VALID);
        dbxxQO.setZxQszt(CommonConstantUtils.QSZT_HISTORY);

        this.updateBdcDbxxAndQsztSyncQj(dbxxQO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 撤销流程，修改权属状态和案件状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelProcessQsztAndAjzt(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失参数gzlslid！");
        }
        // 更新项目的权属状态和案件状态
        int result = bdcXmxxService.updateBdcXmQsztAndAjztPl(gzlslid, CommonConstantUtils.QSZT_END, CommonConstantUtils.AJZT_YHCH_DM);
        LOGGER.error("工作流实例ID:{}, 更新项目的权属状态为{}，更新案件状态为 {}。更新数量{}", gzlslid, CommonConstantUtils.QSZT_END, CommonConstantUtils.AJZT_YHCH_DM, result);
        // 更新当前流程生成的权利的权属状态
        bdcQlxxService.updateBdcQlQsztPl(gzlslid, CommonConstantUtils.QSZT_END);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    public void updateBdcDbxxAndQsztSyncQj(DbxxQO dbxxQO) {
        String gzlslid = dbxxQO.getGzlslid();
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少工作流实例 ID!");
        }

        Date startTime = new Date();
        LOGGER.info("登簿开始：流程{}项目登簿更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态", gzlslid);

        //获取当前流程生成的权利的权利类型(返回的qllxList应该是按照业务逻辑根据项目生成的先后顺序排序的，预告应在预抵押前面)
        List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(dbxxQO.getGzlslid());
        LOGGER.info("{}登簿获取当前流程生成权利的权利类型：{}", gzlslid, JSON.toJSONString(qllxList));

        /**
         * 更新不动产项目和权利信息
         */
        bdcXmxxService.updateBdcXmAndQlXx(dbxxQO, qllxList, true);
        /**
         * 同步权籍基本信息和单元状态
         */
        bdcdyZtService.synQjJbxxAndBdcdyzt(gzlslid);

        /*
         * 登簿后发送消息，判断当前已登簿数据是否有接入数据
         * */
        LOGGER.warn("当前流程实例id{}发送MQ登簿消息检查是否有接入数据", gzlslid);
        registerAccessCheckMqSender.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDJDBHJQUNEUE.getCode(), gzlslid);
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.warn("已完成流程{}项目登簿更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态，所耗时间：{}", gzlslid, time);
    }


    /**
     * @param gzlslid         工作流实例ID
     * @param currentUserName 当前用户信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    public void updateBdcDbxxAndQsztSyncQj(String gzlslid, String currentUserName) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少工作流实例 ID!");
        }
        //TODO  增加蚌埠版本判断，后期多地市的话，做成开关
        if (CommonConstantUtils.DZZZ_HEFEI.equals(zxdzzzFwVersion) || CommonConstantUtils.DZZZ_BENGBU.equals(zxdzzzFwVersion)) {
            // 改变原项目的qszt时，开启线程注销原项目的电子证照
            try {
                LOGGER.info("开始注销原项目的电子证照，gzlslid:{}", gzlslid);
                executor.execute(() -> eCertificateFeignService.zxHefeiDzzz(gzlslid, ""));
            } catch (Exception e) {
                LOGGER.error("注销电子证照异常：{}，请检查日志", e.getMessage());
            }
        } else if (dbzxYqlDzzz) {
            // 改变原项目的qszt时，开启线程注销原项目的电子证照
            try {
                LOGGER.info("开始注销原项目的电子证照，gzlslid:{}", gzlslid);
                executor.execute(() -> eCertificateFeignService.cancelYxmECertificate(gzlslid, currentUserName));
            } catch (Exception e) {
                LOGGER.error("注销电子证照异常：{}，请检查日志", e.getMessage());
            }
        }

        DbxxQO dbxxQO = new DbxxQO();
        try {
            // 流程转发中没有用户认证信息，所以用此方法获取
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            if (null != userDto) {
                dbxxQO.setDbr(userDto.getAlias());
                String djjg = bdcSlJbxxFeignService.queryDjjgByUser(userDto);
                if (StringUtils.isNotBlank(djjg)) {
                    dbxxQO.setDjjg(djjg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}:审核登簿子系统——更新登簿信息以及权属状态报错：调用大云用户信息失败", gzlslid, e);
        }

        dbxxQO.setDjsj(new Date());
        dbxxQO.setGzlslid(gzlslid);

        dbxxQO.setQszt(CommonConstantUtils.QSZT_VALID);
        dbxxQO.setZxQszt(CommonConstantUtils.QSZT_HISTORY);
        LOGGER.info("{} ：登簿信息：{}", gzlslid, dbxxQO);
        this.updateBdcDbxxAndQsztSyncQj(dbxxQO);
    }

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description AOP规则验证后，验证通过则登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    public List<BdcGzyzVO> updateDbxxQsztGzyzAOP(String processInsId, String currentUserName) {
        this.updateBdcDbxxAndQsztSyncQj(processInsId, currentUserName);
        return Collections.emptyList();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param syncQj
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿退回登簿信息和权属状态(还原当前权利为临时 ， 原权利为现势 ， 清空当前登簿信息 ， 清空注销权利的注销登簿信息)
     */
    @Override
    public void revertBdcDbxxAndQsztSyncQj(String gzlslid, boolean syncQj) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少工作流实例 id");
        }
        DbxxQO dbxxQO = new DbxxQO();
        // 当前权利更新为临时
        dbxxQO.setQszt(CommonConstantUtils.QSZT_TEMPORY);
        // 退回原权利信息为现势
        dbxxQO.setZxQszt(CommonConstantUtils.QSZT_VALID);
        dbxxQO.setGzlslid(gzlslid);
        //获取当前流程生成的权利的权利类型(返回的qllxList应该是按照业务逻辑根据项目生成的先后顺序排序的，预告应在预抵押前面)
        List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(dbxxQO.getGzlslid());
        /**
         * 更新不动产项目和权利信息
         */
        bdcXmxxService.updateBdcXmAndQlXx(dbxxQO, qllxList, false);

        /**
         * 同步权籍基本信息和单元状态
         */
        if (syncQj) {
            LOGGER.warn("更新权籍基本信息和单元状态！gzlslid={}", gzlslid);
            bdcdyZtService.synQjJbxxAndBdcdyzt(gzlslid);
        }

        LOGGER.info("已完成流程{}登簿退回登簿信息和权属状态", gzlslid);
    }


    /**
     * @param gzlslid 工作流实例ID
     * @param qszt    权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原注销权利的登簿信息和权属状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateYzxqlDbxxAndQszt(String gzlslid, Integer qszt) {
        if (StringUtils.isBlank(gzlslid) || null == qszt) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        // 需要还原的原注销权利信息
        List<BdcQl> bdcYzxQlList = bdcQllxFeignService.listHyYzxQlxxByProcessInsId(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcYzxQlList)) {
            for (BdcQl bdcQl : bdcYzxQlList) {
                // 更新权利状态
                updateBdcQlQszt(bdcQl, qszt);
                // 更新项目的权属状态
                bdcXmxxService.updateBdcXmQszt(bdcQl.getXmid(), qszt);

                // 仅在原注销权利还原为现势时，清空注销信息
                if (CommonConstantUtils.QSZT_VALID.equals(qszt)) {
                    BdcZxQO bdcZxQO = new BdcZxQO();
                    bdcZxQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    bdcQlxxService.updateBdcQlZxxx(bdcQl, bdcZxQO);
                }
            }
        }
        LOGGER.info("已完成流程{}更新原注销权利的登簿信息和权属状态", gzlslid);
    }

    /**
     * @param bdcQl 不动产权利
     * @param qszt  权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权利和项目的权属状态
     */
    public void updateBdcQlQszt(BdcQl bdcQl, Integer qszt) {
        if (null == bdcQl || StringUtils.isBlank(bdcQl.getQlid())) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        bdcQl.setQszt(qszt);
        entityMapper.updateByPrimaryKeySelective(bdcQl);
        LOGGER.info("已完成权利{}更新权利和项目的权属状态", bdcQl.getQlid());
    }


    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新项目案件状态为2已完成状态，并更新项目结束时间
     */
    @Override
    public void changeAjzt(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return;
        }

        BdcXmDO bdcXmDO = new BdcXmDO();
        bdcXmDO.setXmid(xmid);
        bdcXmDO.setAjzt(CommonConstantUtils.AJZT_YB_DM);
        /**
         * 登簿时会延迟 60s，导致查封等登簿办结同时执行的流程 jssj 比 djsj 早，办结统一延迟 60s 确保 jssj 在 djsj 之后
         */
        bdcXmDO.setJssj(new Date(System.currentTimeMillis() + INTERVAL));

        entityMapper.updateByPrimaryKeySelective(bdcXmDO);
        LOGGER.info("更新项目{}的案件状态为已完成", xmid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产单元状态集合的对象
     */
    @Override
    public BatchBdcdyhztRequestDTO getBatchBdcdyhztRequestDTO(String gzlslid, String bs) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //获取当前流程新生成的权利
            List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByProcessInsId(gzlslid);
            //获取当前流程需要注销的原权利
            List<BdcQl> bdcZxQlList = bdcQllxFeignService.listZxQlxxByProcessInsId(gzlslid);

            List bdcdyhZtRequestDTOList = new ArrayList();

            //获取不动产单元登记状态
            bdcdyDjztList(bdcdyhZtRequestDTOList, bdcQlList, bdcZxQlList, gzlslid, bs);

            if (StringUtils.equals(bs, CommonConstantUtils.TBQJ_DB)) {

                bdcdyZtList(bdcdyhZtRequestDTOList, bdcQlList, CommonConstantUtils.BDCDYZT_XS);
                bdcdyZtList(bdcdyhZtRequestDTOList, bdcZxQlList, CommonConstantUtils.BDCDYZT_LS);
            } else if (StringUtils.equals(bs, CommonConstantUtils.TBQJ_TH)) {
                bdcdyZtList(bdcdyhZtRequestDTOList, bdcQlList, CommonConstantUtils.BDCDYZT_LS);
                bdcdyZtList(bdcdyhZtRequestDTOList, bdcZxQlList, CommonConstantUtils.BDCDYZT_XS);
            }

            BatchBdcdyhztRequestDTO batchBdcdyhztRequestDTO = new BatchBdcdyhztRequestDTO();
            batchBdcdyhztRequestDTO.setBdcdyhZtList(bdcdyhZtRequestDTOList);

            return batchBdcdyhztRequestDTO;
        }
        return null;
    }

    /**
     * @param bdcdyhZtRequestDTOList
     * @param bdcQlList
     * @param bdcZxQlList
     * @param gzlslid
     * @param bs
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产单元的登记状态（转发登簿，退回）
     */
    public void bdcdyDjztList(List bdcdyhZtRequestDTOList, List bdcQlList, List bdcZxQlList, String gzlslid, String bs) {
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (ArrayUtils.contains(CommonConstantUtils.BDC_ZS_QLLX, bdcXmDO.getQllx())) {

                    BdcdyhZtRequestDTO bdcdyhZtRequestDTO = new BdcdyhZtRequestDTO();
                    bdcdyhZtRequestDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                    if (StringUtils.equals(bs, CommonConstantUtils.TBQJ_DB)) {
                        if (CollectionUtils.isEmpty(bdcQlList)) {
                            bdcdyhZtRequestDTO.setDjzt(CommonConstantUtils.BDCDYDJZT_YZX);
                        } else {
                            bdcdyhZtRequestDTO.setDjzt(CommonConstantUtils.BDCDYDJZT_YDJ);
                        }
                    }
                    if (StringUtils.equals(bs, CommonConstantUtils.TBQJ_TH)) {
                        if (CollectionUtils.isEmpty(bdcZxQlList)) {
                            bdcdyhZtRequestDTO.setDjzt(CommonConstantUtils.BDCDYDJZT_WDJ);
                        }
                        if (CollectionUtils.isEmpty(bdcQlList)) {
                            bdcdyhZtRequestDTO.setDjzt(CommonConstantUtils.BDCDYDJZT_YDJ);
                        }
                    }
                    if (StringUtils.isNotBlank(bdcdyhZtRequestDTO.getBdcdyh())) {
                        bdcdyhZtRequestDTOList.add(bdcdyhZtRequestDTO);
                    }
                }
            }
        }
    }

    /**
     * @param bdcdyhList
     * @param sdzt
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产单元状态集合的对象(锁定)
     */
    @Override
    public BatchBdcdyhSyncZtRequestDTO getBdcdySdztRequestDTO(List<String> bdcdyhList, Integer sdzt) {
        if (CollectionUtils.isEmpty(bdcdyhList)) {
            throw new MissingArgumentException("缺失bdcdyhList信息！");
        }
        //根据不动产单元号查询证书锁定关联出额外的单号号（一证多房的情况）
        List<String> extraBdcdyhList = bdcdySdService.queryBdcZssdBdcdyh(bdcdyhList, sdzt);
        if (CollectionUtils.isNotEmpty(extraBdcdyhList)) {
            bdcdyhList.addAll(extraBdcdyhList);
            bdcdyhList = bdcdyhList.stream().distinct().collect(Collectors.toList());
        }
        //计算每个单元号的锁定情况
        List<BdcSyncZtRequestDTO> bdcSyncZtRequestDTOList = new ArrayList();
        Integer xssdcs = 0;
        for (String bdcdyh : bdcdyhList) {
            BdcSyncZtRequestDTO bdcSyncZtRequestDTO = new BdcSyncZtRequestDTO();
            xssdcs = bdcdySdService.queryBdcXssdcs(bdcdyh);
            bdcSyncZtRequestDTO.setXssdcs(xssdcs);
            bdcSyncZtRequestDTO.setBdcdyh(bdcdyh);

            bdcSyncZtRequestDTOList.add(bdcSyncZtRequestDTO);
        }
        BatchBdcdyhSyncZtRequestDTO batchBdcdyhSyncZtRequestDTO = new BatchBdcdyhSyncZtRequestDTO();
        batchBdcdyhSyncZtRequestDTO.setBdcdyhZtList(bdcSyncZtRequestDTOList);
        //尝试查找权籍管理代码
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setBdcdyhList(bdcdyhList);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        LOGGER.warn("同步不动产单元信息,查找权籍管理代码！{}", bdcXmDOS);
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            //取得任意一个工作流
            List<String> gzlslids = bdcXmDOS.stream().map(BdcXmDO::getGzlslid).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            LOGGER.warn("同步不动产单元信息,查找权籍管理代码，工作流实例！{}", gzlslids);
            if (CollectionUtils.isNotEmpty(gzlslids)) {
                BdcXmXmfbDTO bdcXmXmfbDTO = bdcXmxxService.getOnlyOneXm(gzlslids.get(0), "");
                LOGGER.warn("同步不动产单元信息,查找权籍管理代码，项目附表！{}", bdcXmXmfbDTO);
                if (Objects.nonNull(bdcXmXmfbDTO) && StringUtils.isNotBlank(bdcXmXmfbDTO.getQjgldm())) {
                    batchBdcdyhSyncZtRequestDTO.setQjgldm(bdcXmXmfbDTO.getQjgldm());
                }
            }
        }
        return batchBdcdyhSyncZtRequestDTO;
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 同步权籍基本信息
     */
    @Override
    public BdcdyxxPlRequestDTO getBdcdyxxPlRequestDTO(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
            return getBdcdyxxPlRequestDTO(bdcXmDOList);
        }
        return null;
    }

    /**
     * @param bdcXmDOList 项目集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取不动产单元信息集合的对象
     */
    @Override
    public BdcdyxxPlRequestDTO getBdcdyxxPlRequestDTO(List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //循环线程
            List<CommonThread> listThread = new ArrayList();
            List bdcdyxxRequestList = new CopyOnWriteArrayList();
            //判断是否走多线程计数限制
            boolean sfbjs = (bdcXmDOList.size() <= 1);
            //线程对象
            Map<String, Object> taskMap = new ConcurrentHashMap<>();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                BdcdyxxThread bdcdyxxThread = new BdcdyxxThread(bdcdyxxRequestList, bdcXmDO, this);
                bdcdyxxThread.setSfbjs(sfbjs);
                bdcdyxxThread.setTaskMap(taskMap);
                listThread.add(bdcdyxxThread);
            }
            //多线程处理操作
            threadEngine.excuteThread(listThread, true, taskMap);
            if (taskMap.containsKey("msg")) {
                throw new AppException(taskMap.get("msg") != null ? taskMap.get("msg").toString() : "回写权籍基本信息失败!");
            }
            BdcdyxxPlRequestDTO bdcdyxxPlRequestDTO = new BdcdyxxPlRequestDTO();
            bdcdyxxPlRequestDTO.setBdcdyxxRequestDTOList(bdcdyxxRequestList);
            if (StringUtils.isNotBlank(bdcXmDOList.get(0).getGzlslid())) {
                BdcXmXmfbDTO bdcXmXmfbDTO = bdcXmxxService.getOnlyOneXm(bdcXmDOList.get(0).getGzlslid(), "");
                if (bdcXmXmfbDTO != null) {
                    bdcdyxxPlRequestDTO.setQjgldm(bdcXmXmfbDTO.getQjgldm());
                }
            }
            return bdcdyxxPlRequestDTO;
        }
        return null;
    }

    /**
     * @param bdcXmDO 项目集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取不动产单元信息的对象
     */
    @Override
    public BdcdyxxRequestDTO getBdcdyxxRequestDTO(BdcXmDO bdcXmDO) {
        if (StringUtils.isNotBlank(bdcXmDO.getXmid()) && StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
            BdcdyxxRequestDTO bdcdyxxRequestDTO = new BdcdyxxRequestDTO();
            //封装权利信息
            boolean isCq = bdcdyQlxx(bdcdyxxRequestDTO, bdcXmDO);
            if (!isCq) {
                // 只有产权需要同步权籍信息
                return null;
            }

            String bdcdyh = bdcXmDO.getBdcdyh();
            bdcdyxxRequestDTO.setBdcdyh(bdcdyh);

            // 封装权利人信息
            if (!(StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)
                    && ArrayUtils.contains(BDC_QLR_DJLX_ARR, bdcXmDO.getDjlx()))) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDO.getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                bdcdyxxRequestDTO.setBdcQlrDOList(bdcQlrFeignService.listBdcQlr(bdcQlrQO));
            }

            //封装宗地或宗海基本信息
            if (ConvertUtils.ifZh(bdcdyh)) {
                bdcdyxxRequestDTO.setBdcBdcdjbZhjbxxDO(bdcDjbxxService.queryBdcBdcdjbZhjbxx(ConvertUtils.convertFToW(bdcdyh)));
            } else {
                bdcdyxxRequestDTO.setBdcBdcdjbZdjbxxDO(bdcDjbxxService.queryBdcBdcdjbZdjbxx(ConvertUtils.convertFToW(bdcdyh)));
            }
            return bdcdyxxRequestDTO;
        }
        return null;
    }

    /**
     * @param listQllx 生成的权利类型集合
     * @param dbxxQO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新当前项目和当前权利的权属状态和登簿信息
     */
    @Override
    public void updateBdcXmDbxx(DbxxQO dbxxQO, List<String> listQllx) {
        // 先更新生成权利的项目
        if (CollectionUtils.isNotEmpty(listQllx)) {
            for (String qllx : listQllx) {
                LOGGER.info("{}登簿开始更新生成权利的项目状态信息，当前权利类型{}", dbxxQO.getGzlslid(), qllx);
                // 避免转移抵押这些业务登簿时间发生错乱，人为设置先后时间顺序
                if (null != dbxxQO.getDjsj()) {
                    dbxxQO.setDjsj(new Date(dbxxQO.getDjsj().getTime() + INTERVAL));
                }
                if (StringUtils.equals(CommonConstantUtils.YGDJ_YDY, qllx)) {
                    bdcXmxxService.updateYdyaXmDbxxAndQsztPl(dbxxQO);
                    bdcYgService.updateYdyaQlDbxxAndQsztPl(dbxxQO);
                    LOGGER.info("{}修改预抵押项目和权利登记时间完成！", dbxxQO.getGzlslid());
                    continue;
                }
                // 批量更新权利，权利是同一种类型
                //判断是否为撤销登记,撤销登记只需更新权属状态,不替换登簿信息
                BdcXmXmfbDTO bdcXmXmfbDTO = bdcXmxxService.getOnlyOneXm(dbxxQO.getGzlslid(), "");
                boolean isCxdj = CollectionUtils.isNotEmpty(cxdjDyids) && bdcXmXmfbDTO != null && StringUtils.isNotBlank(bdcXmXmfbDTO.getGzldyid()) && cxdjDyids.contains(bdcXmXmfbDTO.getGzldyid());
                //判断是否为信息补录数据
                boolean isXxbl = bdcXmXmfbDTO != null && bdcXmXmfbDTO.getXmly() != null && XXBL_XMLY.equals(bdcXmXmfbDTO.getXmly().toString());
                LOGGER.info("{}登簿更新权利，当前权利类型{}，是否为撤销登记{}，是否为信息补录数据{}", dbxxQO.getGzlslid(), qllx, isCxdj, isXxbl);
                if (isCxdj || isXxbl) {
                    bdcQlxxService.updateBdcQlQsztPl(dbxxQO.getGzlslid(), dbxxQO.getQszt());
                    bdcXmxxService.updateBdcXmQsztAndAjztPl(dbxxQO.getGzlslid(), dbxxQO.getQszt(), null);

                } else {
                    bdcQlxxService.udpateBdcQlDbxxAndQsztPl(dbxxQO, qllx);
                    // 批量更新权利对应项目的登簿信息和权属状态
                    bdcXmxxService.updateBdcQlXmDbxxAndQsztPl(dbxxQO, qllx);
                }
            }
            // (宗地宗海等修改了单元的信息需要更新)更新登记簿信息
            bdcDjbxxService.updateDjbxx(dbxxQO);
        }

        // 不生成权利的项目，也要更新项目的登簿信息（登簿后为历史(此时当前项目的qszt为现势)，退回后还原为临时）
        if (CommonConstantUtils.QSZT_VALID.equals(dbxxQO.getQszt())) {
            dbxxQO.setQszt(CommonConstantUtils.QSZT_HISTORY);
        }
        // 该参数是为了过滤掉生成权利信息的数据，只对不生产权利的项目进行操作（上面的代码已经对当前流程的所有代码做了更新操作）
        dbxxQO.setSfscql(CommonConstantUtils.SF_F_DM);
        bdcXmxxService.updateBdcXmDbxxAndQsztByGzlslid(dbxxQO);

        LOGGER.info("已完成流程{}：更新当前项目和当前权利的权属状态和登簿信息", dbxxQO.getGzlslid());
    }

    /**
     * @param dbxxQO
     * @param listQllx 生成的权利类型集合
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 更新当前流程生成的历史权利和项目的登薄时间 减少60秒
     */
    @Override
    public void updateBdcLsQlXmDjsj(DbxxQO dbxxQO, List<String> listQllx) {
        if (CollectionUtils.isNotEmpty(listQllx)) {
            for (String qllx : listQllx) {
                //更新当前流程生成的历史权利的登薄时间 减少60秒
                if (null != dbxxQO.getDjsj() && !StringUtils.equals(CommonConstantUtils.YGDJ_YDY, qllx)) {
                    String tableName = getTableName(bdcQllxFeignService.makeSureQllx(qllx));
                    if (StringUtils.isNotBlank(tableName)) {
                        int lsQlSl = bdcXmMapper.updateBdcLsQlDjsjPl(new Date(dbxxQO.getDjsj().getTime() - INTERVAL), dbxxQO.getGzlslid(), tableName);
                        LOGGER.info("更新当前{}历史权利{},共{}条！", dbxxQO.getGzlslid(), qllx, lsQlSl);
                    }
                    int lsQlSl = bdcXmMapper.updateBdcLsQlXmDjsjPl(new Date(dbxxQO.getDjsj().getTime() - INTERVAL), dbxxQO.getGzlslid(), qllx != null ? Integer.parseInt(qllx) : null);
                    LOGGER.info("更新当前{}历史权利{},共{}条！", dbxxQO.getGzlslid(), qllx, lsQlSl);
                }
            }

            LOGGER.info("已完成流程{}：更新当前流程生成的历史权利和项目的登薄时间", dbxxQO.getGzlslid());
        }
    }

    /**
     * @param dbxxQO 登簿信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新原项目和原权利的权属状态和注销登簿信息
     */
    @Override
    public void updateYxmDbxx(DbxxQO dbxxQO) {
        // 获取要注销权利的权利类型
        List<String> yqllxList = bdcQllxFeignService.listZxQllxByProcessInsId(dbxxQO.getGzlslid());
        LOGGER.info("{}登簿更新状态需要更新注销权利的权利类型：{}", dbxxQO.getGzlslid(), JSON.toJSONString(yqllxList));
        if (CollectionUtils.isNotEmpty(yqllxList)) {
            //登记时间不为空才获取slbh,空的时候为还原逻辑
            if (dbxxQO.getDjsj() != null) {
                // 获取受理编号，作为注销业务号
                String slbh = bdcXmFeignService.querySlbh(dbxxQO.getGzlslid());
                if (StringUtils.isNotBlank(slbh)) {
                    dbxxQO.setSlbh(slbh);
                }
            }
            // 更新原项目的权属状态
            bdcXmxxService.updateYxmQsztByGzlslid(dbxxQO);
            for (String qllx : yqllxList) {
                //登记时间不为空才获取djyy,空的时候为还原逻辑
                if (dbxxQO.getDjsj() != null) {
                    String djyy = "";
                    //预抵押
                    if (StringUtils.equals(CommonConstantUtils.YGDJ_YDY, qllx)) {
                        djyy = bdcXmFeignService.queryZxYgYdyDjyy(dbxxQO.getGzlslid(), Arrays.asList(CommonConstantUtils.YG_YGDJZL_YDY_ARR));
                    } else if (StringUtils.equals(CommonConstantUtils.QLLX_YG_DM.toString(), qllx)) {
                        djyy = bdcXmFeignService.queryZxYgYdyDjyy(dbxxQO.getGzlslid(), Arrays.asList(CommonConstantUtils.YG_YGDJZL_ARR));
                    } else {
                        djyy = bdcXmFeignService.queryDjyy(dbxxQO.getGzlslid(), Integer.parseInt(qllx));
                    }
                    dbxxQO.setDjyy(djyy);
                }
                if (StringUtils.equals(CommonConstantUtils.YGDJ_YDY, qllx)) {
                    dbxxQO.setYgdjzl(Arrays.asList(CommonConstantUtils.YG_YGDJZL_YDY_ARR));
                    bdcYgService.udpateYgYdyYqlZxDbxxAndQsztPl(dbxxQO);
                } else if (StringUtils.equals(CommonConstantUtils.QLLX_YG_DM.toString(), qllx)) {
                    dbxxQO.setYgdjzl(Arrays.asList(CommonConstantUtils.YG_YGDJZL_ARR));
                    bdcYgService.udpateYgYdyYqlZxDbxxAndQsztPl(dbxxQO);
                } else {
                    // 更新原权利的注销登簿信息和权属状态
                    bdcQlxxService.updateYqlZxDbxxAndQsztPl(dbxxQO, qllx);
                }
            }
        }

        LOGGER.info("已完成流程{}：更新原项目和原权利的权属状态和注销登簿信息", dbxxQO.getGzlslid());
    }

    @Override
    public Object beforeDbGzyz(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数gzlslid!");
        }

        UserDto yzr = userManagerUtils.getCurrentUser();//验证人；
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = null;
        List<BdcXmDO> listXm = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);

        if (CollectionUtils.isNotEmpty(listXm)) {
            // 先从项目中取定义ID
            String processKey = listXm.get(0).getGzldyid();
            // 没有值从大云数据取
            if (StringUtils.isBlank(processKey)) {
                List<TaskData> processRunningTasks = processTaskClient.processRunningTasks(gzlslid);
                if (CollectionUtils.isEmpty(processRunningTasks)) {
                    throw new MissingArgumentException("未查询到流程信息！");
                }
                String taskId = processRunningTasks.get(0).getTaskId();
                processKey = workFlowUtils.getTaskById(taskId).getProcessKey();
            }

            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            // 设置登簿验证组合规则processKey_DBYZ
            bdcGzYzQO.setZhbs(StringUtils.replace("processKey_DBYZ", "processKey", processKey));
            List<Map<String, Object>> paramList = new ArrayList<>();
            bdcGzYzQO.setParamList(paramList);
            bdcGzYzQO.setYzrid(yzrid);
            bdcGzYzQO.setYzrzh(yzrzh);
            //单元号去重
            Set<String> setDyh = new HashSet<>();
            //循环生成条件
            for (BdcXmDO bdcXmDO : listXm) {
                if (!setDyh.contains(bdcXmDO.getBdcdyh())) {
                    Map<String, Object> param = new HashMap<>(4);
                    param.put("bdcdyh", bdcXmDO.getBdcdyh());
                    param.put("xmid", bdcXmDO.getXmid());
                    param.put("slbh", bdcXmDO.getSlbh());
                    param.put("gzlslid", bdcXmDO.getGzlslid());
                    paramList.add(param);
                    setDyh.add(bdcXmDO.getBdcdyh());
                }
            }
            listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
        }
        return BdcGzyzTsxxUtils.checkTsxx(listBdcGzYzTsxx);
    }

    /**
     * 更新项目和权利的权属状态
     *
     * @param bdcHfQO 恢复信息
     * @return {code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateXmAndQlHfxx(BdcHfQO bdcHfQO) {
        if (null == bdcHfQO || (CollectionUtils.isEmpty(bdcHfQO.getXmidList()) && StringUtils.isBlank(bdcHfQO.getXmid()))
                || null == bdcHfQO.getQszt()) {
            return -1;
        }
        // 只有允许恢复为现势
        if (!CommonConstantUtils.QSZT_VALID.equals(bdcHfQO.getQszt())) {
            return -1;
        }

        List<String> xmidList = bdcHfQO.getXmidList();
        if (CollectionUtils.isEmpty(xmidList)) {
            xmidList = new ArrayList<>();
            xmidList.add(bdcHfQO.getXmid());
        }
        bdcHfQO.setXmidList(xmidList);
        if (CollectionUtils.isEmpty(xmidList)) {
            LOGGER.info("{}缺失xmidList！", bdcHfQO);
            return -1;
        }

        int result = 0;
        for (String xmid : xmidList) {
            String yzxx = yzHfQlxx(xmid, bdcHfQO);
            if (!StringUtils.equals(yzxx, "success")) {
                LOGGER.info("{} 不能恢复权利信息: {}", xmid, yzxx);
                continue;
            }
            LOGGER.info("更新项目表{}：恢复权利信息 {}", xmid, JSON.toJSONString(bdcHfQO));
            // 更新项目权属状态
            result += bdcXmxxService.updateBdcXmQszt(xmid, bdcHfQO.getQszt());
            BdcZxQO bdcZxQO = new BdcZxQO();
            // 通过注销接口更新权利的权属状态，只传 qszt 将注销相关信息全部还原
            bdcZxQO.setXmid(xmid);
            bdcZxQO.setQszt(bdcHfQO.getQszt());
            result += bdcQlxxService.updateBdcQlZxxxByXmid(bdcZxQO);
            LOGGER.info("已完成项目{}：恢复权利信息 {}", xmid, JSON.toJSONString(bdcZxQO));
        }
        return result;
    }

    /**
     * @param bdcZxQO 权属状态
     * @return {code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目和权利的权属状态
     * qszt{@code 1}清空注销人和注销时间，对登簿人和登记时间不做修改；{@code 2}同时更新注销人和注销时间
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateXmAndQlZxxx(BdcZxQO bdcZxQO) {
        if (null == bdcZxQO
                || (CollectionUtils.isEmpty(bdcZxQO.getXmidList()) && StringUtils.isBlank(bdcZxQO.getXmid()))
                || null == bdcZxQO.getQszt()) {
            return -1;
        }
        // 判断权属状态是否证确
        final Integer[] qsztDbArr = {CommonConstantUtils.QSZT_VALID, CommonConstantUtils.QSZT_HISTORY};
        if (!ArrayUtils.contains(qsztDbArr, bdcZxQO.getQszt())) {
            return -1;
        }

        // 更新权利注销信息
        List<String> xmidList = bdcZxQO.getXmidList();
        if (CollectionUtils.isEmpty(xmidList)) {
            xmidList = new ArrayList<>();
            xmidList.add(bdcZxQO.getXmid());
        }
        if (CollectionUtils.isEmpty(xmidList)) {
            LOGGER.info("{}缺失xmidList！", bdcZxQO);
            return -1;
        }
        int result = 0;
        for (String xmid : xmidList) {
            // 更新项目权属状态
            result += bdcXmxxService.updateBdcXmQszt(xmid, bdcZxQO.getQszt());

            // 更新权利的注销信息
            bdcZxQO.setXmid(xmid);
            result += bdcQlxxService.updateBdcQlZxxxByXmid(bdcZxQO);

            LOGGER.info("已完成项目{}：注销权利信息,bdcZxQO:{}", xmid,JSON.toJSONString(bdcZxQO));
        }
        return result;
    }


    /**
     * @param bdcdyhZtRequestDTOList
     * @param qlList
     * @param zt
     * @return List
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 判断权利类型，组织对应的同步权籍不动产单元状态dto集合
     */
    public void bdcdyZtList(List bdcdyhZtRequestDTOList, List<BdcQl> qlList, Integer zt) {
        if (CollectionUtils.isNotEmpty(qlList)) {
            for (BdcQl bdcQl : qlList) {
                if (null != bdcQl) {
                    BdcdyhZtRequestDTO bdcdyhZtRequestDTO = new BdcdyhZtRequestDTO();

                    if (bdcQl instanceof BdcCfDO) {
                        bdcdyhZtRequestDTO.setBdcdyh(((BdcCfDO) bdcQl).getBdcdyh());
                        if (ArrayUtils.contains(CommonConstantUtils.CFLX_YCF_ARR, ((BdcCfDO) bdcQl).getCflx())) {
                            bdcdyhZtRequestDTO.setYcf(zt);
                        } else {
                            bdcdyhZtRequestDTO.setCf(zt);
                        }
                    }
                    if (bdcQl instanceof BdcDyaqDO) {
                        bdcdyhZtRequestDTO.setBdcdyh(((BdcDyaqDO) bdcQl).getBdcdyh());
                        if (Objects.equals(CommonConstantUtils.DYBDCLX_ZJJZW, ((BdcDyaqDO) bdcQl).getDybdclx())) {
                            bdcdyhZtRequestDTO.setZjgcdy(zt);
                        } else {
                            bdcdyhZtRequestDTO.setDya(zt);
                        }
                    }
                    if (bdcQl instanceof BdcDyiqDO) {
                        bdcdyhZtRequestDTO.setBdcdyh(((BdcDyiqDO) bdcQl).getGydbdcdyh());
                        bdcdyhZtRequestDTO.setDyi(zt);
                    }
                    if (bdcQl instanceof BdcYyDO) {
                        bdcdyhZtRequestDTO.setBdcdyh(((BdcYyDO) bdcQl).getBdcdyh());
                        bdcdyhZtRequestDTO.setYy(zt);
                    }
                    if (bdcQl instanceof BdcYgDO) {
                        bdcdyhZtRequestDTO.setBdcdyh(((BdcYgDO) bdcQl).getBdcdyh());
                        if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_ARR, ((BdcYgDO) bdcQl).getYgdjzl())) {
                            bdcdyhZtRequestDTO.setYg(zt);
                        } else {
                            bdcdyhZtRequestDTO.setYdya(zt);
                        }
                    }
                    if (StringUtils.isNotBlank(bdcdyhZtRequestDTO.getBdcdyh())) {
                        bdcdyhZtRequestDTOList.add(bdcdyhZtRequestDTO);
                    }
                }
            }
        }
    }



    /**
     * @param bdcdyxxRequestDTO
     * @param bdcXmDO
     * @return boolean 判断是不是产权
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 根据当前项目id获取对应权利信息封装至同步权籍信息的对象中
     */
    public boolean bdcdyQlxx(BdcdyxxRequestDTO bdcdyxxRequestDTO, BdcXmDO bdcXmDO) {
        String xmid = bdcXmDO.getXmid();
        Integer djlx = bdcXmDO.getDjlx();
        Integer bdcdyfwlx = bdcXmDO.getBdcdyfwlx();
        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
        if (bdcQl != null) {
            if (bdcQl instanceof BdcTdsyqDO) {
                bdcdyxxRequestDTO.setBdcTdsyqDO((BdcTdsyqDO) bdcQl);
                return true;
            }
            if (bdcQl instanceof BdcJsydsyqDO) {
                bdcdyxxRequestDTO.setBdcJsydsyqDO((BdcJsydsyqDO) bdcQl);
                return true;
            }
            // 只想更新新逻辑幢的流程，排除掉房屋户室的信息，避免更新了fw_hs表
            if (bdcQl instanceof BdcFdcqDO) {
                if (!ArrayUtils.contains(BDC_FDCQ_HS_DJLX_ARR, djlx) || (!ArrayUtils.contains(BDC_FDCQ_LJZ_DJLX_ARR, djlx) && !CommonConstantUtils.BDCDYFWLX_HS.equals(bdcdyfwlx))) {
                    bdcdyxxRequestDTO.setBdcFdcqDO((BdcFdcqDO) bdcQl);
                }
                if (StringUtils.isNotBlank(bdcQl.getQlid()) && !ArrayUtils.contains(BDC_FDCQ_FDCQXM_DJLX_ARR, djlx)) {
                    bdcdyxxRequestDTO.setFdcqFdcqxmDOList(bdcFdcqFdcqXmService.listFdcqxm(bdcQl.getQlid()));
                }
                return true;
            }
            if (bdcQl instanceof BdcFdcq3DO) {
                bdcdyxxRequestDTO.setBdcFdcq3DO((BdcFdcq3DO) bdcQl);
                bdcdyxxRequestDTO.setBdcFdcq3GyxxDOList(bdcFdcq3GyxxService.queryListBdcQlByXmid(xmid));
                return true;
            }
            if (bdcQl instanceof BdcGjzwsyqDO) {
                bdcdyxxRequestDTO.setBdcGjzwsyqDO((BdcGjzwsyqDO) bdcQl);
                return true;
            }
            if (bdcQl instanceof BdcHysyqDO) {
                bdcdyxxRequestDTO.setBdcHysyqDO((BdcHysyqDO) bdcQl);
                return true;
            }
            if (bdcQl instanceof BdcTdcbnydsyqDO) {
                bdcdyxxRequestDTO.setBdcTdcbnydsyqDO((BdcTdcbnydsyqDO) bdcQl);
                return true;
            }
            if (bdcQl instanceof BdcQtxgqlDO) {
                bdcdyxxRequestDTO.setBdcQtxgqlDO((BdcQtxgqlDO) bdcQl);
                return true;
            }
            if (bdcQl instanceof BdcLqDO) {
                bdcdyxxRequestDTO.setBdcLqDO((BdcLqDO) bdcQl);
                return true;
            }
            //非预抵押的回写
            if (bdcQl instanceof BdcYgDO && !ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, ((BdcYgDO) bdcQl).getYgdjzl())
                    && !ArrayUtils.contains(BDC_YG_DJLX_ARR, djlx)) {
                bdcdyxxRequestDTO.setBdcYgDO((BdcYgDO) bdcQl);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取权利对象对应的表名
     *
     * @param bdcQl
     * @return
     */
    private String getTableName(BdcQl bdcQl) {
        String name = null;
        if (bdcQl != null) {
            Table annotation = bdcQl.getClass().getAnnotation(Table.class);
            if (annotation != null) {
                name = annotation.name();
            }
        }
        return name;
    }


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押注销流程增加回传功能
     */
    @Override
    public void zsyhDyzxHcfw(String processInsId) {
        String slbh = bdcXmFeignService.querySlbh(processInsId);
        if (isZsyhLc(slbh)) {
//            BdcZsQO bdcZsQO = new BdcZsQO();
//            bdcZsQO.setGzlslid(processInsId);
//            List<BdcZsDO> listZs = bdcZsFeignService.listBdcZs(bdcZsQO);
            List<BdcZsDO> listZs = bdcZsFeignService.queryYxmBdcqzhByGzlslid(processInsId, null);
            if (CollectionUtils.isNotEmpty(listZs)) {
                for (BdcZsDO bdcZsDO : listZs) {
                    String bdcqzh = bdcZsDO.getBdcqzh();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("slbh", slbh);
                    jsonObject.put("bdcqzh", bdcqzh);
                    jsonObject.put("djsj", new Date());
                    LOGGER.info("招商银行抵押注销流程回写服务入参：{}", jsonObject);
                    Object response = exchangeInterfaceFeignService.requestInterface("nt_zsyhdyzxxx", jsonObject);
                    if (null != response) {
                        LOGGER.info("招商银行抵押注销流程回写服务返回值：{}", response);
                    }
                }
            }
        }
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押流程增加回传功能
     */
    @Override
    public void zsyhDyHcfw(String processInsId) {
        String slbh = bdcXmFeignService.querySlbh(processInsId);
        if (isZsyhLc(slbh)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(processInsId);
            List<BdcZsDO> listZs = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(listZs)) {
                for (BdcZsDO bdcZsDO : listZs) {
                    String bdcqzh = bdcZsDO.getBdcqzh();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("slbh", slbh);
                    jsonObject.put("bdcqzh", bdcqzh);
                    jsonObject.put("djsj", new Date());
                    LOGGER.info("招商银行抵押流程回写服务入参：{}", jsonObject);
                    Object response = exchangeInterfaceFeignService.requestInterface("nt_zsyhdyxx", jsonObject);
                    if (null != response) {
                        LOGGER.info("招商银行抵押流程回写服务返回值：{}", response);
                    }
                }
            }
        }
    }

    @Override
    public void updateZxqlfj(String processInsId) {
        // 获取当前流程所注销的所有权利信息
        List<BdcQl> bdcZxQlList = bdcQllxFeignService.listZxQlxxByProcessInsId(processInsId);
        if (CollectionUtils.isEmpty(bdcZxQlList)) {
            LOGGER.info("当前流程未查询到要注销的权利！工作流实例id：{}", processInsId);
            return;
        }
        for (BdcQl bdcQl : bdcZxQlList) {
            generateAndUpdateZxqlFj(bdcQl);
        }
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcZxQl]
     * @description 更新权利注销附记
     */
    @Override
    public void generateAndUpdateZxqlFj(BdcQl bdcZxQl) {
        if (bdcZxQl != null && StringUtils.isNotBlank(bdcZxQl.getXmid())) {
            // 查询原权利对应的注销附记配置
            BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO = getBdcXtQlqtzkFjPz(bdcZxQl.getXmid());
            if (bdcXtQlqtzkFjPzDO == null) {
                LOGGER.error("未查询到权利其他状况附记配置，项目id：{}", bdcZxQl.getXmid());
                throw new AppException("未查询到权利其他状况附记配置,无法更新注销权利附记！");
            }
            if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getZxqlfjmb())) {
                // 模板转为小写
                String mb = bdcXtQlqtzkFjPzDO.getZxqlfjmb().toLowerCase();
                //LOGGER.info("注销权利附记模板：{}", mb);

                if (StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getZxqlfjsjy())) {
                    mb = replaceMbWithSjy(mb, bdcXtQlqtzkFjPzDO.getZxqlfjsjy(), bdcZxQl.getXmid());
                }
                // 更新原权利附记
                //LOGGER.info("更新前权利附记：{}", bdcZxQl.getFj());
                if (StringUtils.isNotBlank(bdcZxQl.getFj())) {
                    String fj = bdcZxQl.getFj() + "\n" + mb;
                    bdcZxQl.setFj(fj);
                } else {
                    bdcZxQl.setFj(mb);
                }
                //LOGGER.info("最终权利附记：{}", bdcZxQl.getFj());
                entityMapper.updateByPrimaryKeySelective(bdcZxQl);
            }
        }
    }

    /**
     * 变更登记（带抵押）流程登簿时候追加抵押证明附记内容、重新生成抵押证明电子证照
     *
     * @param processInsId    工作流实例id
     * @param currentUserName 当前用户
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public void appendFjAndRebuildDzzz(String processInsId, String currentUserName) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("变更登记（带抵押）流程登簿更新信息异常，原因：未传工作流实例ID参数");
        }

        // 查询产权对应的抵押信息
        List<BdcDyaqDO> ydyaqList = bdcXmMapper.queryYdyaq(processInsId);
        if (CollectionUtils.isEmpty(ydyaqList)) {
            throw new AppException("变更登记（带抵押）流程" + processInsId + "登簿更新信息异常，原因：未查询到关联抵押信息");
        }

        for (BdcDyaqDO ydyaqDO : ydyaqList) {
            // 查询抵押证明后抵押证明附记追加新产权证号信息
            List<BdcZsDO> dyaZmList = this.zjdyzmfj(processInsId, currentUserName, ydyaqDO);
            if (CollectionUtils.isEmpty(dyaZmList)) {
                continue;
            }

            // 作废抵押证明证照
            List<String> ydyaqXmid = Collections.singletonList(ydyaqDO.getXmid());
            this.zfdzzz(processInsId, ydyaqXmid);

            // 再重新生成抵押证明证照
            this.scdzzz(processInsId, dyaZmList, ydyaqXmid);
        }
    }

    /**
     * 冻结/解冻时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    public void updateQsztSyncQjForDj(String gzlslid) {
        Date startTime = new Date();
        LOGGER.info("冻结/解冻流程{} ：登簿更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态", gzlslid);

        DbxxQO dbxxQO = new DbxxQO();
        try {
            // 流程转发中没有用户认证信息，所以用此方法获取
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (null != userDto) {
                dbxxQO.setDbr(userDto.getAlias());
                String djjg = bdcSlJbxxFeignService.queryDjjgByUser(userDto);
                if (StringUtils.isNotBlank(djjg)) {
                    dbxxQO.setDjjg(djjg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}:审核登簿子系统——更新登簿信息以及权属状态报错：调用大云用户信息失败", gzlslid, e);
        }
        dbxxQO.setDjsj(new Date());
        dbxxQO.setGzlslid(gzlslid);
        dbxxQO.setQszt(CommonConstantUtils.QSZT_VALID);
        dbxxQO.setZxQszt(CommonConstantUtils.QSZT_HISTORY);

        /**
         * 更新不动产项目和权利信息
         */
        bdcXmxxService.updateBdcXmDbxxAndQsztByGzlslid(dbxxQO);

        /**
         * 更新原项目的权属状态
         */
        bdcXmxxService.updateYxmQsztByGzlslid(dbxxQO);

        // 获取要注销权利的权利类型
        List<String> yqllxList = bdcQllxFeignService.listZxQllxByProcessInsId(dbxxQO.getGzlslid());
        if (CollectionUtils.isNotEmpty(yqllxList)) {
            //登记时间不为空才获取slbh,空的时候为还原逻辑
            if (dbxxQO.getDjsj() != null) {
                // 获取受理编号，作为注销业务号
                String slbh = bdcXmFeignService.querySlbh(dbxxQO.getGzlslid());
                if (StringUtils.isNotBlank(slbh)) {
                    dbxxQO.setSlbh(slbh);
                }
            }
            for (String qllx : yqllxList) {
                //登记时间不为空才获取djyy,空的时候为还原逻辑
                if (dbxxQO.getDjsj() != null) {
                    String djyy = "";
                    //预抵押
                    if (StringUtils.equals(CommonConstantUtils.YGDJ_YDY, qllx)) {
                        djyy = bdcXmFeignService.queryZxYgYdyDjyy(dbxxQO.getGzlslid(), Arrays.asList(CommonConstantUtils.YG_YGDJZL_YDY_ARR));
                    } else if (StringUtils.equals(CommonConstantUtils.QLLX_YG_DM.toString(), qllx)) {
                        djyy = bdcXmFeignService.queryZxYgYdyDjyy(dbxxQO.getGzlslid(), Arrays.asList(CommonConstantUtils.YG_YGDJZL_ARR));
                    } else {
                        djyy = bdcXmFeignService.queryDjyy(dbxxQO.getGzlslid(), Integer.parseInt(qllx));
                    }
                    dbxxQO.setDjyy(djyy);
                }
                if (StringUtils.equals(CommonConstantUtils.YGDJ_YDY, qllx)) {
                    dbxxQO.setYgdjzl(Arrays.asList(CommonConstantUtils.YG_YGDJZL_YDY_ARR));
                    bdcYgService.udpateYgYdyYqlZxDbxxAndQsztPl(dbxxQO);
                } else if (StringUtils.equals(CommonConstantUtils.QLLX_YG_DM.toString(), qllx)) {
                    dbxxQO.setYgdjzl(Arrays.asList(CommonConstantUtils.YG_YGDJZL_ARR));
                    bdcYgService.udpateYgYdyYqlZxDbxxAndQsztPl(dbxxQO);
                } else {
                    // 更新原权利的注销登簿信息和权属状态
                    bdcQlxxService.updateYqlZxDbxxAndQsztPl(dbxxQO, qllx);
                }
            }
        }

        /**
         * 同步权籍基本信息和单元状态
         */
        bdcdyZtService.synQjJbxxAndBdcdyzt(gzlslid);

        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.warn("已完成流程{}项目登簿更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态，所耗时间：{}", gzlslid, time);
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [mb, sjy, yxmid]
     * @description 根据数据源结果替换模板参数
     */
    private String replaceMbWithSjy(String mb, String sjy, String yxmid) {
        String[] sqls = sjy.split("；|;");
        for (String sql : sqls) {
            Map param = new HashMap();
            param.put("sql", sql);
            param.put("yxmid", yxmid);
            List<Map> result = bdcRegisterConfigMapper.executeConfigSql(param);
            // 若查询数据不为空
            if (CollectionUtils.isNotEmpty(result)) {
                for (Map map : result) {
                    // 对模板中参数进行替换
                    mb = MbConvertUtils.mbParamReplace(mb, map);
                    LOGGER.info("替换参数后模板为：{}", mb);
                }
            }
        }

        // 将模板未赋值行删除
        mb = MbConvertUtils.mbUnsetRowReplace(mb);
        // 去除最后一个换行符
        if (StringUtils.isNotBlank(mb) && StringUtils.lastIndexOf(mb, CommonConstantUtils.ZF_HH_CHAR) == StringUtils.length(mb) - 1) {
            mb = StringUtils.substring(mb, 0, StringUtils.lastIndexOf(mb, CommonConstantUtils.ZF_HH_CHAR));
        }
        LOGGER.info("根据模板+数据源生成的附记：{}", mb);
        return mb;
    }

    /**
     * @return cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid]
     * @description 查询权利其他状况附记配置
     */
    private BdcXtQlqtzkFjPzDO getBdcXtQlqtzkFjPz(String xmid) {
        BdcXtQlqtzkFjPzDO qlqtzkFjPz = null;
        List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzList;
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXm = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (bdcXm != null && StringUtils.isNotBlank(bdcXm.getDjxl())) {
                Example example = new Example(BdcXtQlqtzkFjPzDO.class);
                Example.Criteria criteria = example.createCriteria().andEqualTo("djxl", bdcXm.getDjxl());
                // 判断权利类型是否为空
                if (bdcXm.getQllx() != null) {
                    criteria.andEqualTo("qllx", bdcXm.getQllx());
                } else {
                    criteria.andIsNull("qllx");
                }
                // 查询权利其他状况与附记配置
                bdcXtQlqtzkFjPzList = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcXtQlqtzkFjPzList)) {
                    qlqtzkFjPz = bdcXtQlqtzkFjPzList.get(0);
                }
            }
        }
        return qlqtzkFjPz;
    }

    /**
     * 根据受理编号判断是否是招商银行
     *
     * @param slbh
     * @return
     */
    private boolean isZsyhLc(String slbh) {
        if (StringUtils.isBlank(slbh)) {
            return false;
        }
        return slbh.indexOf(CommonConstantUtils.ZSYH) > -1;
    }

    private String yzHfQlxx(String xmid, BdcHfQO bdcHfQO) {
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        if (!(CommonConstantUtils.XMLY_CLGD_DM.equals(bdcXmDO.getXmly()) || CommonConstantUtils.XMLY_CLBDC_DM.equals(bdcXmDO.getXmly()))) {
            return "不支持恢复非存量数据！";
        }
        if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
            return "仅支持恢复权属状态为历史的数据！";
        }
        //权利类型验证
        boolean qllxyz;
        if(xxblHfdyq){
            qllxyz = bdcXmDO.getQllx() != null && ( CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx()) || !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx()));
        }else{
            qllxyz = bdcXmDO.getQllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx());
        }
        if (qllxyz) {
            // 不动产数据(非存量过渡数据) 产权证号标识
            if (StringUtils.isNotBlank(bdcXmDO.getBdcqzh())) {
                //根据配置判断是否验证包含不动产
                if (xxblYzbdcqzh && bdcXmDO.getBdcqzh().contains("不动产")) {
                    return "不支持修改证号包含不动产字段的数据！";
                }
                // 根据 bdcdyh 查询是否已经有现实产权
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                if (CommonConstantUtils.BDCLX_TD_DM.equals(bdcXmDO.getBdclx().toString())) {
                    bdcXmQO.setQllxs(Arrays.asList(1, 2, 3, 5, 7));
                } else {
                    bdcXmQO.setQllxs(Arrays.asList(4, 6, 8));
                }
                bdcXmQO.setBdcdyh(bdcXmDO.getBdcdyh());
                List<BdcXmDO> results = bdcXmFeignService.listBdcXm(bdcXmQO);
                if(bdcXmDO.getQllx().equals(CommonConstantUtils.QLLX_DYAQ_DM)){
                    //抵押权判断上一手是否为现势
                    BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                    bdcXmLsgxQO.setXmid(xmid);
                    List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
                    if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                        BdcXmLsgxDO bdcXmLsgxDO = bdcXmLsgxDOList.get(0);
                        BdcXmDO yBdcXmDO = bdcXmFeignService.queryBdcXmByXmid(bdcXmLsgxDO.getYxmid());
                        if(yBdcXmDO != null){
                            Integer yQszt = yBdcXmDO.getQszt();
                            if(!CommonConstantUtils.QSZT_VALID.equals(yQszt)){
                                return "不动产单元号：" + bdcXmDO.getBdcdyh() + "无现势产权，不能恢复抵押权！";
                            }
                        }else{
                            return "不动产单元号：" + bdcXmDO.getBdcdyh() + "无现势产权，不能恢复抵押权！";
                        }
                    }
                }else{
                    if (CollectionUtils.isNotEmpty(results)) {
                        return "不动产单元号：" + bdcXmDO.getBdcdyh() + "已经存在现势产权";
                    }
                }
                UserDto userDto = userManagerUtils.getCurrentUser();
                Map<String, Object> map = getLogInfoMap(bdcHfQO, bdcXmDO, userDto);
                AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.XXBL, map);
                zipkin.add(auditEvent);
                LOGGER.info("{}: 恢复权属状态为：{} 恢复人：{} 恢复原因：{}", bdcXmDO.getSlbh(),
                        bdcHfQO.getQszt(), userManagerUtils.getCurrentUserName(), bdcHfQO.getHfyy());
                return "success";
            } else {
                return "不支持修改证号为空的数据！";
            }
        } else {
            if(xxblHfdyq){
                return "仅支持产权的恢复和抵押权的恢复";
            }
            return "仅支持产权的恢复";
        }
    }

    /**
     * 组织恢复信息日志记录的参数
     *
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private Map<String, Object> getLogInfoMap(BdcHfQO bdcHfQO, BdcXmDO bdcXmDO, UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, "信息补录恢复权利信息");
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put("response", bdcXmDO.getSlbh() + "恢复权属状态为：" + bdcHfQO.getQszt()
                + "恢复原因：" + bdcHfQO.getHfyy());
        map.put("eventName", "UPDATE");
        map.put(CommonConstantUtils.XXBL_PARAMCH, bdcXmDO.getXmid());
        return map;
    }

    /**
     * 追加抵押证明附记（追加转移后新产权证号）
     *
     * @param processInsId    工作流实例ID
     * @param currentUserName 当前用户
     * @param ydyaqDO         抵押
     * @return {List} 抵押证明
     */
    private List<BdcZsDO> zjdyzmfj(String processInsId, String currentUserName, BdcDyaqDO ydyaqDO) {
        // 查询抵押证明
        BdcZsQO zsQO = new BdcZsQO();
        zsQO.setXmid(ydyaqDO.getXmid());
        List<BdcZsDO> dyaZmList = bdcZsFeignService.listBdcZs(zsQO);
        if (CollectionUtils.isEmpty(dyaZmList)) {
            LOGGER.warn("变更登记（带抵押）流程{}登簿更新抵押证明附记异常，原因：未查询到抵押证明信息", processInsId);
            return Collections.emptyList();
        }

        // 查询当前新生成的产权证号信息
        String appendInfo = this.getNewBdcqzh(processInsId, ydyaqDO.getBdcdyh());

        // 追加抵押证明附记
        for (BdcZsDO dyaZmDO : dyaZmList) {
            BdcZsDO bdcZsDO = new BdcZsDO();
            bdcZsDO.setZsid(dyaZmDO.getZsid());
            bdcZsDO.setFj(StringUtils.isBlank(dyaZmDO.getFj()) ? appendInfo : dyaZmDO.getFj() + "\n" + appendInfo);
            int num = entityMapper.updateByPrimaryKeySelective(bdcZsDO);
            LOGGER.info("变更登记（带抵押）流程{}登簿更新抵押证明{}附记{}条，变更后内容：{}，操作用户：{}",
                    processInsId, dyaZmDO.getZsid(), num, bdcZsDO.getFj(), currentUserName);
        }
        return dyaZmList;
    }

    /**
     * 查询当前项目新产权证号信息
     *
     * @param processInsId 工作流实例ID
     * @param bdcdyh       不动产单元号
     * @return {String} 需要追加到抵押附记信息
     */
    private String getNewBdcqzh(String processInsId, String bdcdyh) {
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        bdcZsQO.setBdcdyh(bdcdyh);
        List<BdcZsDO> zsDOList = bdcZsFeignService.listBdcZs(bdcZsQO);
        String bdcqzh = StringToolUtils.resolveBeanToAppendStr(zsDOList, "getBdcqzh", ",");
        return String.format("于%s所有权证号变更为：%s", DateUtils.formateYmdZw(new Date()), bdcqzh);
    }

    /**
     * 作废抵押证明电子证照
     *
     * @param processInsId 工作流实例ID
     * @param ydyaqXmid    抵押证明项目ID
     */
    private void zfdzzz(String processInsId, List<String> ydyaqXmid) {
        // 先作废
        try {
            bdcDzzzCzRestService.zfDzzz(ydyaqXmid);
            LOGGER.info("变更登记（带抵押）流程{}登簿已作废关联抵押项目{}证明电子证照", processInsId, ydyaqXmid);
        } catch (Exception e) {
            LOGGER.error("变更登记（带抵押）流程{}登簿作废关联抵押项目{}证明电子证照失败", processInsId, ydyaqXmid, e);
        }
    }

    /**
     * 新生成抵押证明电子证照
     *
     * @param processInsId 工作流实例ID
     * @param dyaZmList    抵押证明
     * @param ydyaqXmid    抵押证明项目ID
     */
    private void scdzzz(String processInsId, List<BdcZsDO> dyaZmList, List<String> ydyaqXmid) {
        try {
            // 先清空证照标识，避免不执行重新生成
            for (BdcZsDO dyaZm : dyaZmList) {
                Map<String, String> param = new HashMap<>();
                param.put("zsid", dyaZm.getZsid());
                param.put("zzbs", "");
                entityService.updateByJsonEntity(JSON.toJSONString(param), BdcZsDO.class);
            }

            bdcDzzzCzRestService.createDzzz(ydyaqXmid);
            LOGGER.info("变更登记（带抵押）流程{}登簿已重新生成关联抵押项目{}证明电子证照", processInsId, ydyaqXmid);
        } catch (Exception e) {
            LOGGER.error("变更登记（带抵押）流程{}登簿重新生成关联抵押项目{}证明电子证照失败", processInsId, ydyaqXmid, e);
        }
    }

    @Override
    public void syncHdhsfe(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return;
        }
        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDTOList) && StringUtils.isNotBlank(bdcXmDTOList.get(0).getBdcdyh())) {
            String djh = StringUtils.substring(bdcXmDTOList.get(0).getBdcdyh(), 0, 19);
            // 获取当前地籍号上的所有现势产权的户室
            // 计算所有现势产权的数据，流程事件需要放到登簿更改权属状态事件之后
            // 1、计算所有建筑面积 2、获取所有的项目ID 3、生成消息数据往队列中添加数据 4、消费者消费数据，通过xmid获取fdcq数据，更新项目附表 土地使用权份额
            BdcdySumDTO bdcdySumDTO = this.bdcBdcdyService.computeFdcqMjByDjh(djh);
            List<BdcFdcqDO> bdcFdcqDOList = this.bdcFdcqService.listBdcFdcqByDjh(djh);
            if (CollectionUtils.isNotEmpty(bdcFdcqDOList)) {
                for (BdcFdcqDO bdcFdcqDO : bdcFdcqDOList) {
                    BdcHdhsfeDTO bdcHdhsfeDTO = new BdcHdhsfeDTO(bdcdySumDTO.getJzmj(), bdcFdcqDO.getXmid(), bdcFdcqDO.getJzmj());
                    syncHdhsfeMqService.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.SYNCHDHSFEQUEUE.getCode(), JSONObject.toJSONString(bdcHdhsfeDTO));
                }
            }
        }
    }

    @Override
    public void changeYcfToCf(String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            return;
        }
        LOGGER.info("gzlslid:{}执行预查封转现势查封",gzlslid);
        //预查封变为查封，轮候预查封变为轮候查封
        int count =0;
        BdcCflxDTO bdcCflxDTO =new BdcCflxDTO();
        bdcCflxDTO.setGzlslid(gzlslid);
        bdcCflxDTO.setCflxList(Arrays.asList(CommonConstantUtils.CFLX_YCF));
        bdcCflxDTO.setCflx(CommonConstantUtils.CFLX_CF);
        count =bdcCfMapper.updateCflxPl(bdcCflxDTO);
        bdcCflxDTO.setCflxList(Arrays.asList(CommonConstantUtils.CFLX_LHYCF));
        bdcCflxDTO.setCflx(CommonConstantUtils.CFLX_LHCF);
        count +=bdcCfMapper.updateCflxPl(bdcCflxDTO);
        LOGGER.info("gzlslid:{}执行预查封转现势查封成功，更新数量：{}条",gzlslid,count);
    }

    @Override
    public void initCfFromYcf(String gzlslid){
        if (StringUtils.isBlank(gzlslid)) {
            return;
        }
        LOGGER.info("gzlslid:{},{}",gzlslid,FUNCTION_INITCF);
        //存储预查封xmid与产权xmid的对照
        Map<String,String> cqxmidDz =new HashMap<>();
        List<BdcXmDO> bdcXmDOList= bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.info("gzlslid:{},:{},未获取到项目信息",gzlslid,FUNCTION_INITCF);
            return ;
        }
        //查询预查封数据,记录产权项目与预查封的对照关系
        List<BdcCfDO> bdcCfDOList =listXsYcfByDyh(bdcXmDOList,gzlslid,cqxmidDz);
        if(CollectionUtils.isEmpty(bdcCfDOList)){
            LOGGER.info("gzlslid:{},:{},未获取到现势预查封信息",gzlslid,FUNCTION_INITCF);
            return;
        }
        //复制预查封信息
        List<BdcCopyReplaceYwxxDTO> bdcCopyReplaceYwxxDTOList =new ArrayList<>(bdcCfDOList.size());
        //存储复制后xmid与复制前xmid的对照
        Map<String,String> xmidDz =new HashMap<>(bdcCfDOList.size());
        for(BdcCfDO bdcCfDO:bdcCfDOList){
            BdcCopyReplaceYwxxDTO bdcCopyReplaceYwxxDTO =new BdcCopyReplaceYwxxDTO();
            bdcCopyReplaceYwxxDTO.setQueryXmid(bdcCfDO.getXmid());
            //gzlslid替换为当前流程gzlslid
            bdcCopyReplaceYwxxDTO.setGzlslid(gzlslid);
            bdcCopyReplaceYwxxDTO.setSlbh(bdcXmDOList.get(0).getSlbh());
            String newxmid =UUIDGenerator.generate16();
            bdcCopyReplaceYwxxDTO.setXmid(newxmid);
            xmidDz.put(newxmid,bdcCfDO.getXmid());
            bdcCopyReplaceYwxxDTOList.add(bdcCopyReplaceYwxxDTO);
        }
        List<BdcYwxxDTO> bdcYwxxDTOList =bdcInitFeignService.copyAndReplaceYwxx(bdcCopyReplaceYwxxDTOList);
        //数据处理
        if(CollectionUtils.isNotEmpty(bdcYwxxDTOList)){
            for(BdcYwxxDTO bdcYwxxDTO:bdcYwxxDTOList){
                //修改查封类型,预查封转正式查封
                if(bdcYwxxDTO.getBdcQl() instanceof BdcCfDO){
                    if(CommonConstantUtils.CFLX_YCF.equals(((BdcCfDO) bdcYwxxDTO.getBdcQl()).getCflx())) {
                        ((BdcCfDO) bdcYwxxDTO.getBdcQl()).setCflx(CommonConstantUtils.CFLX_CF);
                    }else{
                        ((BdcCfDO) bdcYwxxDTO.getBdcQl()).setCflx(CommonConstantUtils.CFLX_LHCF);
                    }
                }
                //历史关系处理
                bdcYwxxDTO.setBdcXmLsgxList(initYcfToCfLsgx(bdcYwxxDTO.getBdcXm().getXmid(),xmidDz,cqxmidDz));

            }
            bdcInitFeignService.insertYwxxList(bdcYwxxDTOList);
        }


    }

    /**
     * 商品房首次登记更新外联证书的权利附记
     *
     * @param processInsId 工作流实例ID
     */
    @Override
    public void updateSpfscdjWlzsQlfj(String processInsId) throws Exception {
        if (StringUtils.isBlank(processInsId)) {
            return;
        }
        LOGGER.info("商品房首次登记更新外联证书的权利附记,gzlslid:{}", processInsId);
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
            BdcXmDO bdcXmDO = bdcXmDOS.get(0);
            String xmid = bdcXmDO.getXmid();
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setXmid(xmid);
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_S_DM);
            bdcXmLsgxQO.setZxyql(CommonConstantUtils.SF_F_DM);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                //获取不动产项目的fwLjzList
                List<FwLjzDO> fwLjzDOList = acceptBdcdyFeignService.listFwLjzByGzlslid(processInsId, null);
                if (CollectionUtils.isNotEmpty(fwLjzDOList)) {
                    BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmLsgxDOList.get(0).getYxmid());
                    LOGGER.info("商品房首次登记更新外联证书的权利附记,gzlslid:{},关联证书的权力信息:{}",processInsId,JSON.toJSONString(bdcQl));
                    if (Objects.nonNull(bdcQl)) {
                        String fj = StringUtils.isBlank(bdcQl.getFj()) ? StringUtils.EMPTY : bdcQl.getFj();
                        StringBuilder fjStringBuilder = new StringBuilder(fj);
                        //房屋逻辑幢根据小区名称和幢号去重
                        fwLjzDOList = fwLjzDOList.stream()
                                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getXmmc() + o.getDh()))), ArrayList::new));
                        LOGGER.info("商品房首次登记更新外联证书的权利附记,gzlslid:{},配置字符串模板:{}",processInsId,fjStrTemp);
                        for (FwLjzDO fwLjzDO : fwLjzDOList) {
                            String fjStr = StringUtils.EMPTY;
                            String xmmc = StringUtils.isNotBlank(fwLjzDO.getXmmc()) ? fwLjzDO.getXmmc() : StringUtils.EMPTY;
                            String dh = StringUtils.isNotBlank(fwLjzDO.getDh()) ? fwLjzDO.getDh() : StringUtils.EMPTY;
                            String dbsj = bdcXmDOS.get(0).getDjsj() != null ? DateUtils.formateYmdZw(bdcXmDOS.get(0).getDjsj()) : StringUtils.EMPTY;
                            if (StringUtils.isNotBlank(fjStrTemp)) {
                                //相同的小区和幢号只展示一遍
                                if(!fj.contains(xmmc + "小区" + dh + "幢。")){
                                    List<String> strs = Arrays.asList(fjStrTemp.split("dbsj"));
                                    fjStr = StringUtils.replace(fjStrTemp, "xmmc", xmmc).replace("dh", dh).replace("dbsj", dbsj);
                                    String fjContsStr = StringUtils.replace(strs.get(0), "xmmc", xmmc);
                                    if (StringUtils.isNotBlank(fj) && fj.contains(fjContsStr)) {
                                        fjStr = dbsj + StringUtils.SPACE + xmmc + "小区" + dh + "幢。";
                                    }
                                }
                            }
                            fjStringBuilder.append(fjStr);
                        }
                        JSONObject qlObj = new JSONObject();
                        qlObj.put("fj", fjStringBuilder);
                        BdcDjxxUpdateQO bdcQlxxUpdateQO = new BdcDjxxUpdateQO();
                        bdcQlxxUpdateQO.setJsonStr(JSON.toJSONString(qlObj));
                        Map qlxxWhereMap = new HashMap<>();
                        qlxxWhereMap.put("xmids", Collections.singletonList(bdcXmLsgxDOList.get(0).getYxmid()));
                        bdcQlxxUpdateQO.setClassName(bdcQl.getClass().getName());
                        bdcQlxxUpdateQO.setWhereMap(qlxxWhereMap);
                        bdcQllxFeignService.updateBatchBdcQl(bdcQlxxUpdateQO);
                    }

                }

            }
        }


    }

    @Override
    public void updateWwsqDbr(String processInsId,String currentUserName){
        if(StringUtils.isNotBlank(processInsId)){
            //获取互联网创建接口传的登簿人
            String dbr =redisUtils.getHashValue(CommonConstantUtils.ZDZF_SLR+"DBR",processInsId);
            if(StringUtils.isNotBlank(dbr)){
                BdcXmXmfbDTO bdcXmXmfbDTO =bdcXmxxService.getOnlyOneXm(processInsId,"");
                if(bdcXmXmfbDTO ==null){
                    LOGGER.info("gzlslid:{}外网申请登簿人在登记中获取项目为空",processInsId);
                    throw new AppException("gzlslid:"+processInsId+"外网申请登簿人在登记中获取项目为空");
                }
                //登簿人可能挂接多个组织,需要根据qxdm过滤
                UserDto user = userManagerUtils.getUserByNameAndXzqdm(dbr,bdcXmXmfbDTO.getQxdm());
                if(user !=null){
                    dbr =user.getAlias();
                }else{
                    LOGGER.info("gzlslid:{}外网申请登簿人在登记中获取到对应用户,用户名：{}",processInsId,dbr);
                    throw new AppException("gzlslid:"+processInsId+"外网申请登簿人在登记中获取到对应用户,用户名:"+dbr);
                }
                if(CollectionUtils.isEmpty(user.getOrgRecordList())){
                    LOGGER.info("gzlslid:{}外网申请登簿人未匹配到对应组织,用户名：{},qxdm:{}",processInsId,dbr,bdcXmXmfbDTO.getQxdm());
                    throw new AppException("gzlslid:"+processInsId+"外网申请登簿人未匹配到对应组织,用户名:"+dbr);
                }
                DbxxQO dbxxQO =new DbxxQO();
                dbxxQO.setGzlslid(processInsId);
                dbxxQO.setDbr(dbr);
                dbxxQO.setDjjg(user.getOrgRecordList().get(0).getName());
                LOGGER.info("gzlslid:{}获取外网申请登簿人：{}，更新权利表和项目表登簿信息字段",processInsId,dbxxQO);
                //登簿人有值，以互联网传的作为登簿人
                List<String> qllxList = bdcQllxFeignService.listQllxByProcessInsId(processInsId);
                if(CollectionUtils.isNotEmpty(qllxList)){
                    for(String qllx:qllxList){
                        //更新权利登簿人
                        bdcQlxxService.updateBdcQlDbxxPl(dbxxQO, qllx);
                    }
                }
                //更新项目登簿人
                bdcXmxxService.updateBdcXmxxDbxxPl(dbxxQO);
                List<String> yqllxList = bdcQllxFeignService.listZxQllxByProcessInsId(processInsId);
                if(CollectionUtils.isNotEmpty(yqllxList)){
                    for(String yqllx:yqllxList){
                        //更新注销权利登簿人
                        bdcQlxxService.updateZxQlDbrPl(dbxxQO, yqllx);
                    }

                }
            }

        }

    }

    @Override
    public void updateZjjzTdzfj(String processInsId) throws Exception {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            Map<String, List<BdcXmDO>> map = new HashMap<>(8);
            List<String> djhList = bdcXmDOS.stream().map(bdcXmDO -> bdcXmDO.getBdcdyh().substring(0,19)).collect(Collectors.toList());
            djhList = djhList.stream().distinct().collect(Collectors.toList());
            for (String djh : djhList) {
                //根据不动产单元号前19位查询dybdclx为4的现势在建建筑物抵押
                List<BdcDyaqDO> dyaqList = bdcDyafeignService.listDyaByDjhAndQszt(djh,CommonConstantUtils.QSZT_VALID,CommonConstantUtils.DYBDCLX_ZJJZW);
                List<String> xmidList = dyaqList.stream().map(bdcDyaqDO -> bdcDyaqDO.getXmid()).collect(Collectors.toList());
                BdcXmQO xmQO = new BdcXmQO();
                xmQO.setXmidList(xmidList);
                List<BdcXmDO> dyaqXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                map.put(djh,dyaqXmList);
            }

            for (String key : map.keySet()) {
                //获取项目的土地证项目ID
                List<String> qllxs = new ArrayList<>();
                qllxs.add(CommonConstantUtils.QLXX_QLLX_JSYDSYQ);
                qllxs.add(CommonConstantUtils.QLXX_QLLX_ZJDSYQ);
                qllxs.add(CommonConstantUtils.QLXX_QLLX_JTJSYDSYQ);
                BdcXmQO xmQO = new BdcXmQO();
                xmQO.setBdcdyh(key+"W00000000");
                xmQO.setQllxs(qllxs);
                xmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                List<BdcXmDO> bdcXmList = bdcXmFeignService.listBdcXm(xmQO);
                if(CollectionUtils.isNotEmpty(bdcXmList)){
                    //土地证项目ID
                    String tdzXmid = bdcXmList.get(0).getXmid();
                    //查询土地证的权利附记
                    BdcQl tdzQl = bdcQllxFeignService.queryQlxx(tdzXmid);
                    if(Objects.nonNull(tdzQl)){
                        String fj = StringUtils.isBlank(tdzQl.getFj())?StringUtils.EMPTY:tdzQl.getFj();
                        fj.replace("/r/n","");
                        LOGGER.info("gzlslid:{}，根据不动产单元号前19位：{}，获取的土地证xmid：{},获取的土地证附记：{}，",processInsId,key,tdzXmid,fj);
                        List<BdcXmDO> bdcXmDOList = map.get(key);
                        int begin = fj.indexOf(CommonConstantUtils.ZF_ZW_Z_ZKH);
                        int end = fj.indexOf(CommonConstantUtils.ZF_ZW_Y_ZKH);
                        Map<String, List<BdcXmDO>> groupByBdcqzhMap = bdcXmDOList.stream().filter(bdcXmDO -> StringUtils.isNotBlank(bdcXmDO.getBdcqzh())).collect(Collectors.groupingBy(bdcXmDO -> bdcXmDO.getBdcqzh()));
                        LOGGER.info("gzlslid:{}，宗地：{}，根据bdcqzh分组后的数据：{}",processInsId,key,groupByBdcqzhMap);
                        List<String> newFjStr = getBdcqzhFj(groupByBdcqzhMap);
                        LOGGER.info("gzlslid:{}，宗地：{}，土地证要更新的附记集合：{}",processInsId,key,newFjStr);
                        String tdDyqFjStr = StringUtils.join(newFjStr, CommonConstantUtils.ZF_ZW_FH+"\r\n");
                        StringBuilder fjStrBuilder = new StringBuilder(fj);
                        String newFj = "";
                        StringBuilder newFjBuilder = new StringBuilder(fj);

                        if(begin == -1 && end == -1){
                            newFj = fjStrBuilder.append(CommonConstantUtils.ZF_ZW_Z_ZKH).append(tdDyqFjStr).append(CommonConstantUtils.ZF_ZW_Y_ZKH).toString();
                            if(StringUtils.isNotBlank(newFj) && newFj.length()>2000){
                                Set<String> keySet = groupByBdcqzhMap.keySet();
                                String bdcqzhJoinStr = "该土地存在在建工程抵押，抵押证明号：" + StringUtils.join(keySet, CommonConstantUtils.ZF_ZW_DH);
                                newFj = newFjBuilder.append(CommonConstantUtils.ZF_ZW_Z_ZKH).append(bdcqzhJoinStr).append(CommonConstantUtils.ZF_ZW_Y_ZKH).toString();
                            }
                        }else{
                            newFj = fjStrBuilder.replace(begin + 1,end,tdDyqFjStr).toString();
                            if(StringUtils.isNotBlank(newFj) && newFj.length()>2000){
                                Set<String> keySet = groupByBdcqzhMap.keySet();
                                String bdcqzhJoinStr = "该土地存在在建工程抵押，抵押证明号：" +  StringUtils.join(keySet, CommonConstantUtils.ZF_ZW_DH);
                                newFj =  newFjBuilder.replace(begin + 1,end,bdcqzhJoinStr).toString();
                            }
                        }
                        JSONObject qlObj = new JSONObject();
                        LOGGER.info("gzlslid:{}，宗地：{}，最终附记内容：{}",processInsId,key,newFj);
                        qlObj.put("fj", new StringBuilder(newFj));
                        BdcDjxxUpdateQO bdcQlxxUpdateQO = new BdcDjxxUpdateQO();
                        bdcQlxxUpdateQO.setJsonStr(JSON.toJSONString(qlObj));
                        Map qlxxWhereMap = new HashMap<>();
                        qlxxWhereMap.put("xmids", Collections.singletonList(tdzQl.getXmid()));
                        bdcQlxxUpdateQO.setClassName(tdzQl.getClass().getName());
                        bdcQlxxUpdateQO.setWhereMap(qlxxWhereMap);
                        bdcQllxFeignService.updateBatchBdcQl(bdcQlxxUpdateQO);

                    }
                }
            }

        }
    }

    @Override
    public void updateTddysfxx(String processInsId, String currentUserName){
        if(StringUtils.isBlank(processInsId)){
            LOGGER.error("更新土地抵押释放信息异常,工作流实例ID为空");
            return;
        }
        //根据关联关系获取关联单元号
        List<BdcTddysfDyhDO> bdcTddysfDyhDOList = bdcTddysfDyhService.listTddysfBdcdyhByGzlslid(processInsId);
        List<String> bdcdyhList =new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcTddysfDyhDOList)){
            bdcdyhList= bdcTddysfDyhDOList.stream().map(BdcTddysfDyhDO::getBdcdyh).collect(Collectors.toList());
        }
        if(CollectionUtils.isNotEmpty(bdcdyhList)) {
            //土地抵押释放人优先获取登簿人,为空获取当前登录用户
            BdcXmXmfbDTO bdcXmXmfbDTO = bdcXmxxService.getOnlyOneXm(processInsId, "");
            String dbr = "";
            if (bdcXmXmfbDTO != null && StringUtils.isNotBlank(bdcXmXmfbDTO.getDbr())) {
                dbr = bdcXmXmfbDTO.getDbr();
            } else {
                UserDto userDto = userManagerUtils.getUserByName(currentUserName);
                if (userDto != null) {
                    dbr = userDto.getAlias();
                }
            }
            if (StringUtils.isBlank(dbr)) {
                LOGGER.error("更新土地抵押释放信息异常,未获取到登簿人,工作流实例ID:{},currentUserName:{}", processInsId, currentUserName);
                throw new AppException(processInsId + "更新土地抵押释放信息异常,未获取到登簿人");
            }
            //更新户室表土地抵押释放信息
            BdcTddysfxxQO bdcTddysfxxQO = new BdcTddysfxxQO();
            bdcTddysfxxQO.setTddysfsf(CommonConstantUtils.SF_ZW_S);
            bdcTddysfxxQO.setTddysfczr(dbr);
            bdcTddysfxxQO.setTddysfsj(new Date());
            bdcTddysfxxQO.setQjgldm(bdcXmXmfbDTO.getQjgldm());
            bdcTddysfxxQO.setBdcdyhList(bdcdyhList);
            fwHsFeignService.updateFwhsTddysfxx(bdcTddysfxxQO);
            LOGGER.info("工作流实例ID:{}更新土地抵押释放信息结束,土地抵押释放更新对象：{}",processInsId,bdcTddysfxxQO);
        }
    }

    /**
     * 登记系统房屋发生转移类登记后，将合同的状态修改为注销
     *
     * @param processInsId
     * @param currentUserName
     */
    @Override
    public void updateHtbazt(String processInsId, String currentUserName) {
        if(StringUtils.isBlank(processInsId)){
            LOGGER.error("更新合同状态异常,工作流实例ID为空");
            return;
        }
        //根据关联关系获取关联单元号
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)) {
            List<Object> bdcdywybhList = bdcXmDOS
                    .stream()
                    .map(BdcXmDO::getBdcdywybh)
                    .filter(StringUtils::isNotBlank).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcdywybhList)) {
                LOGGER.error("更新合同状态异常,未获取到不动产单元唯一标识,工作流实例ID:{},currentUserName:{}", processInsId, currentUserName);
                throw new AppException(processInsId + "更新合同状态异常,未获取到不动产单元唯一标识");
            }
            //更新htba_sfp状态为已注销
            List<List<Object>> partition = Lists.partition(bdcdywybhList, 500);
            for (List<Object> bdcdywybhPartition : partition) {
                Example example = new Example(HtbaSpfDO.class);
                example.createCriteria().andIn("fwbm",bdcdywybhPartition);
                HtbaSpfDO htbaSpfDO = new HtbaSpfDO();
                htbaSpfDO.setBazt(0);
                entityMapper.updateByExampleSelective(htbaSpfDO,example);
            }
            LOGGER.info("工作流实例ID:{}更新合同状态信息结束",processInsId);
        }
    }

    @Override
    public void tzggxtBjzt(String processInsId,String spjg,String spjd,String reason){
        if(StringUtils.isBlank(processInsId)){
            LOGGER.error("通知工改系统办件状态异常,工作流实例ID为空");
            return;
        }
        BdcXmXmfbDTO bdcXmXmfbDTO =bdcXmxxService.getOnlyOneXm(processInsId,"");
        if(bdcXmXmfbDTO ==null){
            LOGGER.error("通知工改系统办件状态异常,工作流实例ID:{}未查询到登记项目",processInsId);
            return;
        }
        if (!CommonConstantUtils.SPLY_GGXT.equals(bdcXmXmfbDTO.getSply())) {
            LOGGER.info("工作流实例ID:{}非工改系统业务,无需推送办件状态", processInsId);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spxtywh", bdcXmXmfbDTO.getSpxtywh());
        jsonObject.put("spjd", spjd);
        jsonObject.put("spjg", StringUtils.isBlank(spjg) ? 1 : spjg);
        jsonObject.put("spyj", reason);
        String spsj = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        try {
            jsonObject.put("spsj", URLEncoder.encode(spsj, CharEncoding.UTF_8));
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("spsj转码失败！：{}", jsonObject.toString());
            e.printStackTrace();
        }
        LOGGER.info("工作流实例ID:{}通知工改系统办件状态服务入参：{}", processInsId, jsonObject);
        Object response = exchangeInterfaceFeignService.requestInterface("gcsp_receive", jsonObject);
        if (null != response) {
            LOGGER.info("工作流实例ID:{}通知工改系统办件状态服务返回值：{}", processInsId, response);
        }

    }

    @Override
    public void tsggztDzzzxx(String processInsId){
        if(StringUtils.isBlank(processInsId)){
            LOGGER.error("推送工改系统电子证照信息异常,工作流实例ID为空");
            return;
        }
        BdcXmXmfbDTO bdcXmXmfbDTO =bdcXmxxService.getOnlyOneXm(processInsId,"");
        if(bdcXmXmfbDTO ==null){
            LOGGER.error("推送工改系统电子证照信息异常,工作流实例ID:{}未查询到登记项目",processInsId);
            return;
        }
        if(!CommonConstantUtils.SPLY_GGXT.equals(bdcXmXmfbDTO.getSply())){
            LOGGER.info("工作流实例ID:{}非工改系统业务,无需推送电子证照信息",processInsId);
            return;
        }
        BdcZsQO bdcZsQO =new BdcZsQO();
        bdcZsQO.setGzlslid(processInsId);
        List<BdcZsDO> bdcZsDOList =bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isEmpty(bdcZsDOList)){
            LOGGER.info("工作流实例ID:{}未获取到证书记录,无需推送电子证照信息",processInsId);
            return;
        }
        List<String> fileUrlList =new ArrayList<>();
        for(BdcZsDO bdcZsDO:bdcZsDOList){
            if(StringUtils.isNotBlank(bdcZsDO.getZzdz()) &&bdcZsDO.getZzdz().indexOf("//") >-1){
                //去除IP端口部分，由工改系统自行拼接
                String url =bdcZsDO.getZzdz();
                int index=url.indexOf('/', url.indexOf("//") + 2);
                if(url.length() >index) {
                    url =url.replace(url.substring(0,index),"");
                    fileUrlList.add(url);
                }
            }
        }
        if (CollectionUtils.isEmpty(fileUrlList)) {
            LOGGER.info("工作流实例ID:{}未获取到证照地址,无需推送电子证照信息", processInsId);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("spxtywh", bdcXmXmfbDTO.getSpxtywh());
        try {
            jsonObject.put("fileUrl", URLEncoder.encode(String.join(CommonConstantUtils.ZF_YW_DH, fileUrlList), CharEncoding.UTF_8));
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("fileUrl转码失败！：{}", jsonObject.toString());
            e.printStackTrace();
        }
        LOGGER.info("工作流实例ID:{}推送工改系统电子证照信息服务入参：{}", processInsId, jsonObject);
        Object response = exchangeInterfaceFeignService.requestInterface("gcsp_tsdzzz", jsonObject);
        if (null != response) {
            LOGGER.info("工作流实例ID:{}推送工改系统电子证照信息服务返回值：{}", processInsId, response);
        }
    }

    @Override
    public void wlcqzx(String processInsId,String currentUserName) throws Exception{
        if(StringUtils.isBlank(processInsId)){
            LOGGER.error("登簿时处理外联产权对应的限制权利异常,工作流实例ID为空");
            return;
        }
        //查询需要处理的数据
        List<BdcWlzxcqXzqlDTO> bdcWlzxcqXzqlDTOList =listWlcqzxXzql(processInsId);
        if(CollectionUtils.isEmpty(bdcWlzxcqXzqlDTOList)){
            LOGGER.error("工作流实例ID:{}登簿时处理外联产权对应的限制权利结束,未查询到需要处理的数据",processInsId);
            return;
        }
        LOGGER.error("工作流实例ID:{}登簿时处理外联产权对应的限制权利,查询到需要处理限制权利：{}",processInsId,JSONObject.toJSONString(bdcWlzxcqXzqlDTOList));
        //替换单元号，建立历史关系
        dealWlcqXzqlxx(bdcWlzxcqXzqlDTOList,processInsId,currentUserName);


    }

    @Override
    public void updateLqlzzt(String processInsId){
        if(StringUtils.isBlank(processInsId)){
            LOGGER.error("登簿时更新林权流转状态异常,工作流实例ID为空");
            return;
        }
        List<BdcXmDO> bdcXmDOList =bdcXmxxService.getListBdcXmByGzlslid(processInsId);
        if(CollectionUtils.isNotEmpty(bdcXmDOList) &&CommonConstantUtils.DJLX_SCDJ_DM.equals(bdcXmDOList.get(0).getDjlx()) &&Arrays.asList(CommonConstantUtils.BDC_LQ_JYD_QLLX).contains(bdcXmDOList.get(0).getQllx())){
            //林地经营权首次
            List<String> bdcdyhList =new ArrayList<>();
            BdcXmGxQO bdcXmGxQO = new BdcXmGxQO();
            bdcXmGxQO.setGzlslid(processInsId);
            bdcXmGxQO.setWlxm(CommonConstantUtils.SF_S_DM);
            List<BdcXmDO> bdcYxmDOList = bdcXmxxService.getListYbdcXm(bdcXmGxQO);
            if(CollectionUtils.isNotEmpty(bdcYxmDOList)){
                for(BdcXmDO bdcXmDO:bdcXmDOList) {
                    if(Arrays.asList(CommonConstantUtils.BDC_LQ_CBYJQ_QLLX).contains(bdcXmDO.getQllx()) ||Arrays.asList(CommonConstantUtils.BDC_LQ_JYD_QLLX).contains(bdcXmDO.getQllx())){
                        if(StringUtils.isNotBlank(bdcXmDO.getBdcdyh())) {
                            bdcdyhList.add(bdcXmDO.getBdcdyh());
                        }
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(bdcdyhList)) {
                LOGGER.info("工作流实例ID:{}登簿时更新林权流转状态,单元号集合为{}", processInsId,bdcdyhList);
                List<List> sublist =ListUtils.subList(bdcdyhList,500);
                for(List list:sublist) {
                    BdcBdcdyhxsztDO bdcBdcdyhxsztDO = new BdcBdcdyhxsztDO();
                    bdcBdcdyhxsztDO.setLqlzzt(CommonConstantUtils.SF_S_DM);
                    Example example = new Example(BdcBdcdyhxsztDO.class);
                    example.createCriteria().andInWithListString("bdcdyh", list);
                    entityMapper.updateByExampleSelectiveNotNull(bdcBdcdyhxsztDO, example);
                }
            }

        }
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   查询当前流程产权项目对应外联选择注销的产权上的现势限制权利
     */
    private List<BdcWlzxcqXzqlDTO> listWlcqzxXzql(String processInsId){
        BdcWlzxcqXzqlQO bdcWlzxcqXzqlQO =new BdcWlzxcqXzqlQO();
        bdcWlzxcqXzqlQO.setGzlslid(processInsId);
        bdcWlzxcqXzqlQO.setXzqlqllx(Arrays.asList(CommonConstantUtils.BDC_QLLX_XZQL));
        bdcWlzxcqXzqlQO.setPcgzldyid(pcDyaqDyids);
        if(CollectionUtils.isNotEmpty(pcDyaqDyids) &&pcDyaqDyids.size() ==1 &&StringUtils.isBlank(pcDyaqDyids.get(0))){
            bdcWlzxcqXzqlQO.setPcgzldyid(null);
        }
        return bdcXmMapper.listWlcqzxXzql(bdcWlzxcqXzqlQO);
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 处理外联产权的限制权利信息
     */
    private void dealWlcqXzqlxx(List<BdcWlzxcqXzqlDTO> bdcWlzxcqXzqlDTOList,String processInsId,String currentUserName) throws Exception{
        //根据单元号分组
        Map<String, List<BdcWlzxcqXzqlDTO>> dyhMap = bdcWlzxcqXzqlDTOList.stream().collect(Collectors.groupingBy(BdcWlzxcqXzqlDTO::getBdcdyh));
        if (MapUtils.isNotEmpty(dyhMap)){
            List<DyhGzQO> dyhGzQOList =new ArrayList<>(dyhMap.size());
            for (Map.Entry<String, List<BdcWlzxcqXzqlDTO>> entry : dyhMap.entrySet()) {
                DyhGzQO dyhGzQO =new DyhGzQO();
                List<BdcXmLsgxDO> bdcXmLsgxDOList =new ArrayList<>();
                String bdcdyh =entry.getKey();
                dyhGzQO.setBdcdyh(bdcdyh);
                List<String> xmidList =new ArrayList<>();
                List<BdcDysdDO> bdcDysdDOList =new ArrayList<>();
                for(BdcWlzxcqXzqlDTO bdcWlzxcqXzqlDTO:entry.getValue()){
                    //单元号不一致的更新单元号
                    if(!StringUtils.equals(bdcdyh,bdcWlzxcqXzqlDTO.getXzqldyh())){
                        if(StringUtils.isNotBlank(bdcWlzxcqXzqlDTO.getXzqlxmid())){
                            //限制权利与新产权建立历史关系
                            BdcXmLsgxDO bdcXmLsgxDO =new BdcXmLsgxDO();
                            bdcXmLsgxDO.setXmid(bdcWlzxcqXzqlDTO.getXzqlxmid());
                            bdcXmLsgxDO.setYxmid(bdcWlzxcqXzqlDTO.getXmid());
                            bdcXmLsgxDO.setWlxm(CommonConstantUtils.SF_F_DM);
                            bdcXmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                            bdcXmLsgxDOList.add(bdcXmLsgxDO);
                            xmidList.add(bdcWlzxcqXzqlDTO.getXzqlxmid());
                        }else if(StringUtils.isNotBlank(bdcWlzxcqXzqlDTO.getDysdid())){
                            BdcDysdDO bdcDysdDO =new BdcDysdDO();
                            bdcDysdDO.setDysdid(bdcWlzxcqXzqlDTO.getDysdid());
                            bdcDysdDO.setBdcdyh(bdcWlzxcqXzqlDTO.getXzqldyh());
                            bdcDysdDOList.add(bdcDysdDO);
                        }
                    }
                }

                //限制权利
                if(CollectionUtils.isNotEmpty(xmidList)) {
                    //限制权利数量不会过多
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXmByXmids(xmidList);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        dyhGzQO.setBdcXmDOList(bdcXmDOList);
                        BdcXmXmfbDTO bdcXmXmfbDTO = bdcXmxxService.getOnlyOneXm(null, bdcXmDOList.get(0).getXmid());
                        if(bdcXmXmfbDTO != null){
                            dyhGzQO.setQjgldm(bdcXmXmfbDTO.getQjgldm());
                        }
                    }
                }
                //单元锁定
                if(CollectionUtils.isNotEmpty(bdcDysdDOList)){
                    dyhGzQO.setBdcDysdDOList(bdcDysdDOList);
                }
                if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                    dyhGzQO.setBdcXmLsgxDOList(bdcXmLsgxDOList);
                }
                dyhGzQOList.add(dyhGzQO);
            }
            //数据入库，日志记录
            bdcDyhGzFeignService.updateWlcqXzql(dyhGzQOList,processInsId,currentUserName);
        }

    }




    /**
    * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
    * @param
    * @return 
    * @description 通过不动产权证号查到现势的所有项目，更新附记内容
    **/
    private List<String> getBdcqzhFj(Map<String,List<BdcXmDO>> groupByBdcqzhMap){
        DecimalFormat nf = new DecimalFormat("#");
        List<String> fjStrList = new ArrayList<>(8);
        if(MapUtils.isNotEmpty(groupByBdcqzhMap)){
            for (Map.Entry<String, List<BdcXmDO>> entry : groupByBdcqzhMap.entrySet()) {
                List<BdcXmDO> bdcXmDOS = entry.getValue();
                Map<String,String> dhMap = new HashMap<>();
                String fjh = "";
                String dh = "";
                String bdbzzqse = "";
                String zwlxqssj = "";
                String zwlxjssj = "";
                String djsj = "";
                String bdcqzh = entry.getKey();
                String qlr = "";
                String dhfjh = "";
                if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                    for (BdcXmDO bdcXmDO : bdcXmDOS) {
                        qlr = bdcXmDO.getQlr();
                        djsj = DateUtils.formateDateToString(bdcXmDO.getDjsj(),DateUtils.DATE_FORMAT_2);
                        //获取权利信息
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcXmDO.getXmid());
                        if(bdcQl != null && bdcQl instanceof BdcDyaqDO){
                            BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                            bdbzzqse = nf.format(bdcDyaqDO.getBdbzzqse())+StringUtils.EMPTY;
                            zwlxqssj = DateUtils.formateDateToString(bdcDyaqDO.getZwlxqssj(),DateUtils.DATE_FORMAT_2);
                            zwlxjssj = DateUtils.formateDateToString(bdcDyaqDO.getZwlxjssj(),DateUtils.DATE_FORMAT_2);
                            FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcXmDO.getBdcdyh(),null);
                            if(Objects.nonNull(fwYchsDO)){
                                FwLjzDO fwLjzDO = fwLjzFeginService.queryLjzByFwDcbIndex(fwYchsDO.getFwDcbIndex(), null);
                                fjh = fwYchsDO.getFjh();
                                if(Objects.nonNull(fwLjzDO)){
                                    dh = fwLjzDO.getDh();
                                }
                            }
                            if(StringUtils.isNotBlank(dhMap.get(dh))){
                                dhMap.put(dh,dhMap.get(dh)+CommonConstantUtils.ZF_YW_DH+fjh);
                            }else{
                                dhMap.put(dh,dh+"幢"+fjh);
                            }
                        }
                    }
                    for (Map.Entry<String,String> dhEntry:dhMap.entrySet()) {
                        if(StringUtils.isNotBlank(dhfjh)){
                            dhfjh = dhfjh +CommonConstantUtils.ZF_YW_DH+dhEntry.getValue();
                        }else{
                            dhfjh = dhfjh +dhEntry.getValue();
                        }
                    }
                    if(StringUtils.isNotBlank(updatetdztemp)) {
                        String newStr = updatetdztemp.replace("dhfjh",dhfjh).replace("bdcqzh",bdcqzh).replace("qlr",qlr)
                                .replace("bdbzzqse",bdbzzqse).replace("zwlxqssj",zwlxqssj).replace("zwlxjssj",zwlxjssj).replace("djsj",djsj);
                        fjStrList.add(newStr);
                    }
                }

            }
            return fjStrList;
        }
        return null;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  撤销登记,还原上一手产权项目对应外联历史关系中注销的原限制权利的状态
     * 产权A外联限制证书C生成产权B并注销C,选择产权B办理撤销，还原C状态
     */
    @Override
    public void updateYxmWlzxqlDbxxAndQsztForCxdj(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("撤销登记还原状态缺失参数gzlslid");
        }
        BdcYxmQO bdcYxmQO = new BdcYxmQO();
        bdcYxmQO.setGzlslid(gzlslid);
        bdcYxmQO.setZxyql(CommonConstantUtils.SF_S_DM);
        List<BdcXmDO> yzxXmList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
        if(CollectionUtils.isNotEmpty(yzxXmList)){
            //查询产权项目
            yzxXmList=yzxXmList.stream().filter(t-> !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, t.getQllx())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(yzxXmList)) {
                List<String> yxmidList = yzxXmList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                bdcYxmQO = new BdcYxmQO();
                bdcYxmQO.setXmidList(yxmidList);
                bdcYxmQO.setWlxm(CommonConstantUtils.SF_S_DM);
                bdcYxmQO.setZxyql(CommonConstantUtils.SF_S_DM);
                List<BdcXmDO> hyzxXmList = bdcXmFeignService.listYxmByYxmQO(bdcYxmQO);
                if (CollectionUtils.isNotEmpty(hyzxXmList)) {
                    //撤销登记,只还原产权项目外联的限制权利的状态
                    hyzxXmList = hyzxXmList.stream().filter(t -> ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, t.getQllx())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(hyzxXmList)) {
                        LOGGER.info("撤销登记{},还原上一手项目对应外联历史关系中注销的原权利的状态:{}", gzlslid, hyzxXmList);
                        for (BdcXmDO hyzxXm : hyzxXmList) {
                            // 更新权利状态
                            BdcZxQO bdcZxQO = new BdcZxQO();
                            bdcZxQO.setXmid(hyzxXm.getXmid());
                            bdcZxQO.setQszt(CommonConstantUtils.QSZT_VALID);
                            bdcQlxxService.updateBdcQlZxxxByXmid(bdcZxQO);
                            // 更新项目的权属状态
                            bdcXmxxService.updateBdcXmQszt(hyzxXm.getXmid(), CommonConstantUtils.QSZT_VALID);
                        }
                    }
                }
            }

        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param cqxmidDz 记录预查封xmid与产权xmid的对照
     * @return 现势预查封数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取当前流程对应单元号的现势预查封数据
     */
    private List<BdcCfDO> listXsYcfByDyh(List<BdcXmDO> bdcXmDOList,String gzlslid,Map<String,String> cqxmidDz){
        Map<String,String> dyhMap =new HashMap<>();
        List<String> bdcdyhList =new ArrayList<>();
        for(BdcXmDO bdcXmDO:bdcXmDOList){
            if(!ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())){
                dyhMap.put(bdcXmDO.getBdcdyh(),bdcXmDO.getXmid());
                bdcdyhList.add(bdcXmDO.getBdcdyh());
            }
        }
        if(CollectionUtils.isEmpty(bdcdyhList)){
            LOGGER.info("gzlslid:{},:{},未获取到项目单元号信息",gzlslid,FUNCTION_INITCF);
            return Collections.emptyList();
        }
        List<BdcCfDO> bdcCfDOList =new ArrayList<>();
        List<List> partDyhList =ListUtils.subList(bdcdyhList,500);
        for(List list:partDyhList) {
            BdcCfQO bdcCfQO = new BdcCfQO();
            bdcCfQO.setCflxList(Arrays.asList(CommonConstantUtils.CFLX_YCF_ARR));
            bdcCfQO.setBdcdyh(list);
            List<BdcCfDO> cfList = bdcCfMapper.listBdcCfxx(bdcCfQO);
            if(CollectionUtils.isNotEmpty(cfList)){
                cfList = cfList.stream().filter(cf -> CommonConstantUtils.QSZT_VALID.equals(cf.getQszt())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(cfList)) {
                    for(BdcCfDO cf:cfList){
                        cqxmidDz.put(cf.getXmid(),dyhMap.get(cf.getBdcdyh()));
                    }
                    bdcCfDOList.addAll(cfList);
                }
            }
        }
        return bdcCfDOList;
    }

    /**
     * @param xmid 新查封项目ID
     * @param xmidDz 新查封与预查封的对照关系
     * @param cqxmidDz 预查封与产权项目的对照关系
     * @return 项目历史关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成新查封项目的历史关系
     */
    private List<BdcXmLsgxDO> initYcfToCfLsgx(String xmid,Map<String,String> xmidDz,Map<String,String> cqxmidDz){
        //查封与预查封建立外联注销历史关系
        List<BdcXmLsgxDO> bdcXmLsgxDOList =new ArrayList<>();
        BdcXmLsgxDO bdcXmLsgxDO =new BdcXmLsgxDO();
        bdcXmLsgxDO.setGxid(UUIDGenerator.generate16());
        bdcXmLsgxDO.setXmid(xmid);
        String ycfxmid =xmidDz.get(xmid);
        bdcXmLsgxDO.setYxmid(ycfxmid);
        bdcXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
        bdcXmLsgxDO.setWlxm(CommonConstantUtils.SF_S_DM);
        bdcXmLsgxDOList.add(bdcXmLsgxDO);
        //查封与当前产权建立主历史关系
        BdcXmLsgxDO xmLsgxDO =new BdcXmLsgxDO();
        xmLsgxDO.setGxid(UUIDGenerator.generate16());
        xmLsgxDO.setXmid(xmid);
        xmLsgxDO.setYxmid(cqxmidDz.get(ycfxmid));
        xmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
        xmLsgxDO.setWlxm(CommonConstantUtils.SF_F_DM);
        bdcXmLsgxDOList.add(xmLsgxDO);
        return bdcXmLsgxDOList;

    }

}
