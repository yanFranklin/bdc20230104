package cn.gtmap.realestate.exchange.service.impl.jrsb;

import cn.gtmap.realestate.common.core.domain.BdcBdcdjbZdjbxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.*;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.KttZdjbxxMapper;
import cn.gtmap.realestate.exchange.core.national.NationalAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.service.impl.national.AccessModelHandlerServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadFtpImpl;
import cn.gtmap.realestate.exchange.service.impl.national.upload.ProvinceAccessUploadSftpImpl;
import cn.gtmap.realestate.exchange.service.jrsb.BdcYwhjSbService;
import cn.gtmap.realestate.exchange.service.national.*;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.util.AsyncDealUtils;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * @program: bdcdj3.0
 * @description: 业务数据汇交上报实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-11-18 10:55
 **/
@Service
public class BdcYwsjSbServiceImpl implements BdcYwhjSbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessModelHandlerServiceImpl.class);

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数
            40,
            // 空闲超时一小时（调用频繁）
            3600, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("xsdjy-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    KttZdjbxxMapper kttZdjbxxMapper;

    @Autowired
    AsyncDealUtils asyncDealUtils;

    @Autowired
    AccesssModelHandlerService accesssModelHandlerService;

    @Autowired
    BdcExchangeZddzService bdcExchangeZddzService;

    @Autowired
    AccessDefaultValueService accessDefaultValueService;

    @Autowired
    NationalAccessHeadModelService nationalAccessHeadModelService;
    @Autowired
    VailXmlErrorHandlerService vailXmlErrorHandlerService;

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

    //虚拟单元号是否上报，默认不上报配置false
    @Value("${access.xnbdcdyh:false}")
    private boolean xndyhSfsb;

    @Autowired
    ProvinceAccessUploadFtpImpl provinceAccessUploadFtp;

    @Autowired
    ProvinceAccessUploadSftpImpl provinceAccessUploadSftp;

    @Value("${provinceAccess.type:}")
    private String accessType;

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 存量数据汇交上报
     * @date : 2022/11/18 10:56
     */
    @Override
    public void clsjhj(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
            //存量数据上报只有两个验证1. 虚拟单元号是否上报2. bdc_bdcdjb_zdjbxx 表是否有数据
            if (Objects.nonNull(bdcXmDO)) {

                //刚开始就记录数据到接入表中，cgbs为3，等待组织报文

                LOGGER.info("====================存量数据刚开始上报，存入数据库接入表一条======================:{}", bdcXmDO.getXmid());
                //数据接收，异步存入接入操作日志表，此时没有生成bwid
                asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 1, "服务接收到存量数据需要汇交的项目信息，下一步进行数据校验", "", new Date(), "1");
                if (nationalSwtich) {
                    accesssModelHandlerService.saveBdcAccess(new NationalAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "");
                }
                if (provinceSwtich) {
                    accesssModelHandlerService.saveBdcAccess(new ProvinceAccess(), JrRzCgbsEnum.BEGIN_WSB.getBs(), bdcXmDO, "");
                }
                boolean sfsb = true;
                if (!xndyhSfsb) {
                    //虚拟单元号不上报，且当前单元号是虚拟号，返回false 不上报
                    if (StringUtils.isNotBlank(bdcXmDO.getBdcdyh())
                            && bdcXmDO.getBdcdyh().length() == 28
                            && BdcdyhToolUtils.checkXnbdcdyh(bdcXmDO.getBdcdyh())) {
                        asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "当前存量数据单元号是虚拟号，配置了虚拟单元号不上报", "", new Date(), "0");
                        sfsb = false;
                    }
                }
                //验证bdc_bdcdjb_zdjbxx
                Map<String, Object> param = new HashMap(1);
                param.put("ywh", bdcXmDO.getXmid());
                List<KttZdjbxxDO> kttZdjbxxList = kttZdjbxxMapper.queryKttZdjbxxList(param);
                if (CollectionUtils.isEmpty(kttZdjbxxList)) {
                    asyncDealUtils.saveJrCzrz(bdcXmDO.getXmid(), 0, "bdc_bdcdjb_zdjbxx表未查询到相关数据，上报终止", "", new Date(), "0");
                    sfsb = false;
                }
                if (sfsb) {
                    accesssModelHandlerService.startAccess(bdcXmDO, Collections.singletonList(bdcXmDO));
                }
            }
        }

    }

    /**
     * @param qjsjjcDTOList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 权籍数据xsd校验，用于创建前校验，用受理项目的数据
     * @date : 2022/11/21 8:53
     */
    @Override
    public Object checkQjsjCjyz(List<QjsjjcDTO> qjsjjcDTOList) {
        List<String> msgList = new ArrayList<>(CollectionUtils.size(qjsjjcDTOList));
        //超过20条数据就启用多线程
        if (CollectionUtils.isNotEmpty(qjsjjcDTOList)) {
            if (CollectionUtils.size(qjsjjcDTOList) > 1) {
                List<CompletableFuture<List<String>>> xsdjyFuterList = new ArrayList<>(CollectionUtils.size(qjsjjcDTOList));
                for (QjsjjcDTO qjsjjcDTO : qjsjjcDTOList) {
                    CompletableFuture<List<String>> xsdjyFuture = CompletableFuture.supplyAsync(() -> {
                        msgList.add(xsdjy(qjsjjcDTO));
                        return msgList;
                    }, threadPoolExecutor);
                    xsdjyFuterList.add(xsdjyFuture);
                }
                CompletableFuture<Void> allFutures = CompletableFuture.allOf(xsdjyFuterList.toArray(new CompletableFuture[CollectionUtils.size(xsdjyFuterList)]));
                try {
                    allFutures.get();
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error("执行权籍数据xsd校验异常", e);
                }
            } else {
                for (QjsjjcDTO qjsjjcDTO : qjsjjcDTOList) {
                    msgList.add(xsdjy(qjsjjcDTO));
                }
            }
        }
        return msgList;
    }


    private MessageModel generateMessageModel(NationalAccessXmlService nationalAccessXmlService, QjsjjcDTO qjsjjcDTO) {
        if (Objects.isNull(nationalAccessXmlService)) {
            return null;
        }
        MessageModel messageModel = new MessageModel();
        //组织权籍数据
        QjsjDataModel qjsjDataModel = nationalAccessXmlService.getQjsjDataModel(qjsjjcDTO);
        DataModel dataModel = new DataModel();
        BeanUtils.copyProperties(qjsjDataModel, dataModel);
        //组织headModel
        HeadModel headModel = nationalAccessHeadModelService.generateHeadModel(qjsjjcDTO);
        if (Objects.nonNull(headModel)) {
            headModel.setRecType(nationalAccessXmlService.getRecType());
        }
        // 字典表和国标字典对照
        headModel = bdcExchangeZddzService.handleHeadZddz(headModel);
        dataModel = bdcExchangeZddzService.handleZddz(dataModel, "");

        messageModel.setDataModel(dataModel);
        messageModel.setHeadModel(headModel);
        // 读取默认值配置表  赋默认值
        accessDefaultValueService.setDefaultValueWithDefaultTable(nationalAccessXmlService.getNationalAccessDataServiceSet(), messageModel, true);
        return messageModel;
    }

    private NationalAccessXmlService getAccessService(Integer qllx, String djxl, Integer bdcdyfwlx, Integer bdclx) {
        NationalAccessXmlService nationalAccessXmlService = null;
        Map<String, Object> param = Maps.newHashMap();
        if (Objects.nonNull(qllx)) {
            param.put("bdcqllxdm", qllx);
        }
        if (StringUtils.isNotBlank(djxl)) {
            param.put("bdcsqlxdm", djxl);
        }
        if (Objects.nonNull(bdcdyfwlx)) {
            param.put("sfdz", bdcdyfwlx);
        }
        if (Objects.nonNull(bdclx)) {
            param.put("bdclx", bdclx);
        }
        Map<String, Object> bdcexchangeZdSqlxMap = bdcXmMapper.getBdcSubmitZdSqlx(param);
        if (ObjectUtils.isEmpty(bdcexchangeZdSqlxMap)) {
            LOGGER.error("未找到对应的服务{}", JSON.toJSONString(param));
        } else {
            if (MapUtils.isNotEmpty(bdcexchangeZdSqlxMap)) {
                nationalAccessXmlService = getNationNalServiceInstance(CommonUtil.formatEmptyValue(bdcexchangeZdSqlxMap.get("YWFWDM")));
            }
        }
        return nationalAccessXmlService;
    }

    public NationalAccessXmlService getNationNalServiceInstance(String instanceName) {
        Object object = null;
        try {
            object = Container.getBean(instanceName);
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.error("没有找到策略对象：{}", e.toString());
        }
        if (null != object) {
            return (NationalAccessXmlService) object;
        } else {
            return null;
        }
    }

    private String xsdjy(QjsjjcDTO qjsjjcDTO) {
        String msg = "";
        try {
            String tpl = "不动产单元号" + qjsjjcDTO.getBdcdyh();
            //1.验证虚拟号上报和bdc_bdcdjb_zdjbxx 是否有值
            if (StringUtils.isBlank(qjsjjcDTO.getBdcdyh())) {
                return "";
            }
            String bdcdyh = qjsjjcDTO.getBdcdyh();
            if (StringUtils.isNotBlank(bdcdyh)
                    && bdcdyh.length() == 28
                    && BdcdyhToolUtils.checkXnbdcdyh(bdcdyh)) {
                return "";
            }
            List<BdcBdcdjbZdjbxxDO> bdcBdcdjbZdjbxxDOList = bdcXmMapper.listBdcDjbZdjbxx(bdcdyh.substring(0, 19));
            if (CollectionUtils.isEmpty(bdcBdcdjbZdjbxxDOList)) {
                msg += tpl + "未查询到bdc_bdcdjb_zdjbxx相关数据";
            }
            //2.获取服务策略，入参
            //登记类型根据关系表查字典表
            NationalAccessXmlService nationalAccessXmlService = getAccessService(qjsjjcDTO.getQllx(), qjsjjcDTO.getDjxl(), qjsjjcDTO.getBdcdyfwlx(), qjsjjcDTO.getBdclx());
            if (Objects.isNull(nationalAccessXmlService)) {
                msg += tpl + "未找到对应的上报服务策略";
            }
            //3.组织messageModel
            MessageModel messageModel = generateMessageModel(nationalAccessXmlService, qjsjjcDTO);
            if (Objects.nonNull(messageModel) && Objects.nonNull(messageModel.getHeadModel())) {
                //4.xsd 校验
                String xml = accesssModelHandlerService.getAccessXML(messageModel);
                LOGGER.warn("当前单元号{}组织权籍数据xml{}", qjsjjcDTO.getBdcdyh(), xml);
                String xsdPath = "";
                if (StringUtils.equals("sftp", accessType)) {
                    xsdPath = provinceAccessUploadSftp.getXsdpath();
                } else {
                    xsdPath = provinceAccessUploadFtp.getXsdpath();
                }
                if (StringUtils.isNotBlank(xsdPath)) {
                    String xsdFilePath = xsdPath + "/" +
                            messageModel.getHeadModel().getRecType() + ".xsd";
                    LOGGER.info("xsd文件路径{}", xsdFilePath);
                    String valResult = vailXmlErrorHandlerService.xmlXsdjyHandler(xml, xsdFilePath);
                    msg += tpl + "xsd校验权籍数据异常提示:" + valResult;
                }
            }
            ////校验结果异步入库
            if (StringUtils.isNotBlank(msg) && StringUtils.isNotBlank(qjsjjcDTO.getXmid())) {
                asyncDealUtils.saveQjsjXsdJyjg(qjsjjcDTO.getXmid(), bdcdyh, msg, new Date());
            }
        } catch (Exception e) {
            LOGGER.error("不动产单元号xsd校验异常{}", qjsjjcDTO.getBdcdyh(), e);
        }
        return msg;
    }

}
