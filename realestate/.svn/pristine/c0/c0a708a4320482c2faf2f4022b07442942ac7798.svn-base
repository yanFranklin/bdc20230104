package cn.gtmap.realestate.exchange.service.impl.national.upload;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.DataModelBdc;
import cn.gtmap.realestate.common.core.domain.exchange.uniformity.MessageModelBdc;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.core.enums.AccessWarningEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.dto.common.MessageModelOld;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.CityAccess;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.core.national.RespondModel;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.VailXmlErrorHandlerService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.util.*;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 上报日志
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/4/26.
 * @description
 */
@Component
public class AccessUploadLog {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessUploadLog.class);
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private DozerUtil dozerUtil;
    @Autowired
    private XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;
    @Autowired
    private VailXmlErrorHandlerService vailXmlErrorHandlerService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    AsyncDealUtils asyncDealUtils;

    @Autowired
    private AccessLogTypeService accessLogTypeService;
    @Value("${data.version:standard}")
    private String logversion;
    @Value("#{'${accessLog.sqqxdmList: 340100}'.split(',')}")
    private List<String> sqqxdmList;

    /**
     * 1 默认
     * 2 各区县登簿日志上报时则从bdc_jr_sjjl表取值组织报文、保存到bdc_jr_sjjl字段中
     */
    @Value("${access.data.part.field:2}")
    private Integer accessDataPartField;
    /**
     * xsd校验不通过是否能进行上报
     */
    @Value("${access.xsdSfjrXz: false}")
    private boolean xsdSfjrXz;

    /**
     * 保存上报日志
     *
     * @param headModel
     * @param xml
     * @param flag
     * @param bdcAccessLog
     */
    public BdcAccessLog saveAccessUploadLog(HeadModel headModel, String xml, Boolean flag, BdcAccessLog bdcAccessLog) {
        bdcAccessLog.setJrrq(new Date());
        bdcAccessLog.setUpdatetime(new Date());
        bdcAccessLog.setYwbwid(headModel.getBizMsgID());
        bdcAccessLog.setYwlsh(headModel.getRecFlowID());
        bdcAccessLog.setYwdm(headModel.getRecType());
        bdcAccessLog.setBdcdyh(headModel.getEstateNum());
        //不为空的话就是响应数据取到了成功标识
        if (bdcAccessLog.getCgbs() == null) {
            bdcAccessLog.setCgbs(flag ? JrRzCgbsEnum.WATING_RESP.getBs() : JrRzCgbsEnum.ACCESS_FAIL.getBs());
        }
        bdcAccessLog.setJrbw(xml);
        return bdcAccessLog;
    }


    /**
     * 保存上报日志
     *
     * @param messageModel
     * @param xml
     * @param flag
     * @param bdcAccessLog
     */
    public BdcAccessLog saveAccessUploadLog(MessageModel messageModel, String xml, boolean flag, BdcAccessLog bdcAccessLog) {
        LOGGER.info("判断市区代码：sqqsdm:{},报文qxdm:{}", sqqxdmList.toString(), messageModel.getHeadModel().getAreaCode());
        if (sqqxdmList.contains(messageModel.getHeadModel().getAreaCode())) {
            LOGGER.info("判断市区代码：sqqsdm:{},报文qxdm:{}", sqqxdmList.toString(), messageModel.getHeadModel().getAreaCode());
            //合肥市区，这三个字段，直接从项目表取值
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(messageModel.getHeadModel().getRecFlowID());
            if (null != bdcXmDO) {
                bdcAccessLog.setDjlx(null != bdcXmDO.getDjlx() ? String.valueOf(bdcXmDO.getDjlx()) : CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setQllx(null != bdcXmDO.getQllx() ? String.valueOf(bdcXmDO.getQllx()) : CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setBdcqzsh(StringUtils.isNotBlank(bdcXmDO.getBdcqzh()) ? bdcXmDO.getBdcqzh() : CommonConstantUtils.ZF_YW_XG);
            }
        } else {
            DataModel dataModel = messageModel.getDataModel();
            HeadModel headModel = messageModel.getHeadModel();
            // 1、	bdc_jr_sjjl表在抽取数据组织报文时增加qllx（权利类型）、djlx（登记类型）、bdcqzsh（证书证明号）字段，各区县数据抽取时在这三个字段插入值。

            // 各区县数据以上字段取值规则如下：djlx（从djt_dj_slsq.djlx）、
            if (CollectionUtils.isNotEmpty(dataModel.getDjtDjSlsqList())) {
                bdcAccessLog.setDjlx(null != dataModel.getDjtDjSlsqList().get(0).getDjlx() ? dataModel.getDjtDjSlsqList().get(0).getDjlx() : CommonConstantUtils.ZF_YW_XG);
            } else {
                bdcAccessLog.setDjlx(CommonConstantUtils.ZF_YW_XG);
            }
            // qllx（对应权利表的qllx字段，如QLT_FW_FDCQ_YZ.qllx）、
            if (StringUtils.isNotBlank(headModel.getRightType())) {
                bdcAccessLog.setQllx(null != headModel.getRightType() ? headModel.getRightType() : CommonConstantUtils.ZF_YW_XG);
            } else {
                bdcAccessLog.setQllx(CommonConstantUtils.ZF_YW_XG);
            }
            // bdcqzsh(ZTT_GY_QLR.bdcqzh),若多个权利人分别持多本证，该字段按照登簿日志格式英文逗号隔开拼接起来。
            if (CollectionUtils.isNotEmpty(dataModel.getZttGyQlrList())) {
                if (CollectionUtils.isNotEmpty(dataModel.getDjtDjSlsqList())) {
                    if ("1".equals(dataModel.getDjtDjSlsqList().get(0).getSqfbcz())) {
                        bdcAccessLog.setBdcqzsh(dataModel.getZttGyQlrList().stream().map(ZttGyQlrDO::getBdcqzh).collect(Collectors.joining(",")));
                    } else {
                        bdcAccessLog.setBdcqzsh(null != dataModel.getZttGyQlrList().get(0).getBdcqzh() ? dataModel.getZttGyQlrList().get(0).getBdcqzh() : "/");
                    }
                } else {
                    bdcAccessLog.setBdcqzsh(CommonConstantUtils.ZF_YW_XG);
                }
            } else {
                bdcAccessLog.setBdcqzsh(CommonConstantUtils.ZF_YW_XG);
            }
        }
        saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
        return bdcAccessLog;
    }

    /**
     * 保存上报日志一致性
     *
     * @param messageModel
     * @param xml
     * @param flag
     * @param bdcAccessLog
     */
    public BdcAccessLog saveAccessUploadLogYzx(MessageModelBdc messageModel, String xml, boolean flag, BdcAccessLog bdcAccessLog) {
        DataModelBdc dataModel = messageModel.getDataModel();
        // 1、	bdc_jr_sjjl表在抽取数据组织报文时增加qllx（权利类型）、djlx（登记类型）、bdcqzsh（证书证明号）字段，各区县数据抽取时在这三个字段插入值。

        // 各区县数据以上字段取值规则如下：djlx（从djt_dj_slsq.djlx）、
        if (CollectionUtils.isNotEmpty(dataModel.getDjtDjSlsqList())) {
            bdcAccessLog.setDjlx(dataModel.getDjtDjSlsqList().get(0).getDjlx());
        } else {
            bdcAccessLog.setDjlx("/");
        }
        // qllx（对应权利表的qllx字段，如QLT_FW_FDCQ_YZ.qllx）、
        if (CollectionUtils.isNotEmpty(dataModel.getZttGyQlrList())) {
            bdcAccessLog.setQllx(dataModel.getZttGyQlrList().get(0).getQllx());
        } else {
            bdcAccessLog.setQllx("/");
        }
        // bdcqzsh(ZTT_GY_QLR.bdcqzh),若多个权利人分别持多本证，该字段按照登簿日志格式英文逗号隔开拼接起来。
        if (CollectionUtils.isNotEmpty(dataModel.getZttGyQlrList())) {
            if (CollectionUtils.isNotEmpty(dataModel.getDjtDjSlsqList())) {
                if ("1".equals(dataModel.getDjtDjSlsqList().get(0).getSqfbcz())) {
                    bdcAccessLog.setBdcqzsh(dataModel.getZttGyQlrList().stream().map(ZttGyQlrDO::getBdcqzh).collect(Collectors.joining(",")));
                } else {
                    bdcAccessLog.setBdcqzsh(dataModel.getZttGyQlrList().get(0).getBdcqzh());
                }
            } else {
                bdcAccessLog.setBdcqzsh("/");
            }
        } else {
            bdcAccessLog.setBdcqzsh("/");
        }
        saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
        return bdcAccessLog;
    }


    /**
     * 存入响应部分数据
     */
    public BdcAccessLog saveAccessResponseData(RespondModel respondModel, BdcAccessLog bdcAccessLog) {
        bdcAccessLog.setYwbwid(respondModel.getBizMsgID());
        bdcAccessLog.setXybm(respondModel.getResponseCode());
        bdcAccessLog.setCgbs(respondModel.getSuccessFlag());
        bdcAccessLog.setXyxx(respondModel.getResponseInfo());
        bdcAccessLog.setFwewm(respondModel.getQRCode());
        bdcAccessLog.setFjxx(respondModel.getAdditionalData());
        bdcAccessLog.setFjxx1(respondModel.getAdditionalData2());
        bdcAccessLog.setUpdatetime(new Date());
        return bdcAccessLog;
    }

    /**
     * 日志存入数据库
     */
    @Transactional(transactionManager = "serverTransactionManager", rollbackFor = Exception.class)
    public void saveBdcAccessLog(BdcAccessLog bdcAccessLog) {
        //已生成汇交结构，之前以xmid为主键保存的信息需要删除，重新保存一条以BizMsgID 为主键的信息，后续均更新这条数据
        if (StringUtils.isNotBlank(bdcAccessLog.getYwlsh())) {
            LOGGER.info("删除之前xmid为主键值的记录！xmid:{}", bdcAccessLog.getYwlsh());
            Example example = new Example(bdcAccessLog.getClass());
            example.createCriteria().andEqualTo("ywbwid", bdcAccessLog.getYwlsh());
            int count = entityMapper.deleteByExampleNotNull(example);
            LOGGER.info("删除成功条数：{}", count);
        }
//        LOGGER.info("====================accessLog======================:{}", JSON.toJSONString(bdcAccessLog));
        //由于考虑到之前通过ywbwid去saveOrUpdate数据，实际应该按照ywlsh去saveOrUpdate数据，但是现在主键是ywbwid而存量数据会存在多条ywlsh一样的数据，通过ywlsh去saveOrUpdate数据会主键冲突，故先做删除操作，再更新
        if (StringUtils.isNotBlank(bdcAccessLog.getYwlsh())) {
            LOGGER.info("====================accessLog项目id{}，不动产单元号{},accessLog业务报文id{}======================", bdcAccessLog.getYwlsh(), bdcAccessLog.getBdcdyh(), bdcAccessLog.getYwbwid());
            Example example = new Example(bdcAccessLog.getClass());
            example.createCriteria().andEqualTo("ywlsh", bdcAccessLog.getYwlsh()).andEqualTo("bdcdyh", bdcAccessLog.getBdcdyh()).andNotEqualTo("ywbwid", bdcAccessLog.getYwbwid());
            List<BdcAccessLog> bdcAccessLogs = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcAccessLogs)) {
                ArrayList<String> xmids = bdcAccessLogs.stream().collect(ArrayList::new, (list, item) -> {
                    list.add(item.getYwlsh());
                }, ArrayList::addAll);
                ArrayList<String> bdcdyh = bdcAccessLogs.stream().collect(ArrayList::new, (list, item) -> {
                    list.add(item.getBdcdyh());
                }, ArrayList::addAll);
                LOGGER.info("存在重复数据并开始更新:{}", xmids);
                Map<String, Object> batchUpdateAccessLogMap = new HashMap<>();
                batchUpdateAccessLogMap.put("cgbs", JrRzCgbsEnum.HISTORY_DATA.getBs());
                batchUpdateAccessLogMap.put("xmids", xmids);
                batchUpdateAccessLogMap.put("bdcdyhs", bdcdyh);
                if (bdcAccessLog instanceof ProvinceAccess) {
                    bdcdjMapper.batchUpdateAccessLogCgbs(batchUpdateAccessLogMap);
                } else if(bdcAccessLog instanceof CityAccess){
                    bdcdjMapper.batchUpdateAccessLogCgbsCity(batchUpdateAccessLogMap);
                }else{
                    bdcdjMapper.batchUpdateAccessLogCgbsNational(batchUpdateAccessLogMap);
                }
            }
            //处理实际的接入时间。取xm表的djsj
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andEqualTo("xmid", bdcAccessLog.getYwlsh()).andEqualTo("bdcdyh", bdcAccessLog.getBdcdyh());
            List<BdcXmDO> xmxx = entityMapper.selectByExample(xmExample);
            if (CollectionUtils.isNotEmpty(xmxx)) {
                bdcAccessLog.setSjjrrq(xmxx.get(0).getDjsj());
            }
            LOGGER.info("====================accessLog业务bwid{}响应报文信息======================:{}", bdcAccessLog.getYwbwid(), bdcAccessLog.getXyxx());
            entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
        } else {
            LOGGER.info("====================accessLog.ywbwid======================:{}", bdcAccessLog.getYwbwid());
            //根据当前bwid 查询不等于9 的数据
            if (StringUtils.isNotBlank(bdcAccessLog.getYwbwid())) {
                Example example = new Example(bdcAccessLog.getClass());
                example.createCriteria().andEqualTo("ywbwid", bdcAccessLog.getYwbwid()).andNotEqualTo("cgbs", "9");
                List<BdcAccessLog> bdcAccessLogList = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcAccessLogList)) {
                    entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
                }
            }
        }

    }


    /**
     * 上报
     *
     * @param bizMsgId
     * @param xml
     * @return
     * @throws IOException
     */
    public boolean uploadFtpFile(FTPClient ftpClient, String localCharset, String bizMsgId, String xml, String path) throws IOException {
        LOGGER.debug("FTP上传报文开始............");
        StringBuilder bizSb = new StringBuilder();
        String uploadFileNameNoTmp = "Biz" + bizMsgId + ".xml";
        bizSb.append(StringUtils.defaultString(path, "")).append(bizMsgId, 0, 6).append("/").append("BizMsg/");
        FtpUtil.createDirs(ftpClient, new String(bizSb.toString().getBytes(localCharset), FTP.DEFAULT_CONTROL_ENCODING));
        boolean flag = ftpClient.storeFile(new String(uploadFileNameNoTmp.getBytes(localCharset), FTP
                .DEFAULT_CONTROL_ENCODING), new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        LOGGER.info("FTP上传报文结束............,上传状态：{}", flag ? "成功" : "失败");
        return flag;
    }

    /**
     * @param inputStream
     * @return java.io.ByteArrayOutputStream
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将输入流转换为字节码输出流 可多次使用
     */
    public static ByteArrayOutputStream revertToBaosFromInputStream(InputStream inputStream) {
        ByteArrayOutputStream baos = null;
        byte[] buffer = new byte[1024];
        int len;
        try {
            baos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
        } catch (IOException e) {
            LOGGER.error("输入流转字节码输出流异常", e);
        }
        return baos;
    }

    /**
     * 通过消息处理响应报文
     *
     * @param message
     */
    public CommonResponse dealWithResponseByMqMessage(String message, BdcAccessLog bdcAccessLog) {
        try {
            bdcAccessLog = bdcAccessLog.getClass().newInstance();
            bdcAccessLog.setXybw(message);
            RespondModel respondModel = (RespondModel) xmlEntityConvertUtil.xmlToEntity(RespondModel.class, new StringReader(message));
            if (respondModel != null) {
                saveAccessResponseData(respondModel, bdcAccessLog);
                LOGGER.info("rocketMQ接收响应报文获取成功，业务报文id{}............,响应内容:{}", bdcAccessLog.getYwbwid(), JSONObject.toJSONString(respondModel));
                Example example = new Example(bdcAccessLog.getClass());
                //cgbs 不等于9 的才可以更新
                example.createCriteria().andEqualTo("ywbwid", bdcAccessLog.getYwbwid()).andNotEqualTo("cgbs", "9");
                List<BdcAccessLog> bdcAccessLogs = entityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcAccessLogs)) {
                    entityMapper.saveOrUpdate(bdcAccessLog, bdcAccessLog.getYwbwid());
                } else {
                    return CommonResponse.fail("报文id=" + bdcAccessLog.getYwbwid() + "未查询到相关的接入日志或者当前数据接入标识为9");
                }
                return CommonResponse.ok();
            } else {
                LOGGER.info("respondModel null:{}", respondModel.getBizMsgID());
                return CommonResponse.fail("转换消息respondModel为空");
            }
        } catch (Exception e) {
            LOGGER.error("通过消息处理响应报文失败", e);
            return CommonResponse.fail("通过消息处理响应报文失败" + e.getMessage());
        }

    }

    /**
     * 响应报文
     *
     * @param bizMsgId
     */
    public BdcAccessLog downloadFtpFile(FTPClient ftpClient, String bizMsgId, BdcAccessLog bdcAccessLog, int times, String respath) {
        String loadFileName = "Rep" + bizMsgId + ".xml";//响应报文xml文件名
        String localCharset = "GBK";
        InputStream inputStream = null;
        StringBuilder repSb = new StringBuilder();
        if (!respath.startsWith("/")) {
            repSb.append("/");
        }
        repSb.append(respath);
        if (!respath.endsWith("/")) {
            repSb.append("/");
        }
        repSb.append(bizMsgId, 0, 6).append("/").append("RepMsg/");
        try {
            LOGGER.info("FTP开始获取响应报文............");
            inputStream = FtpUtil.downloadFtpFile(ftpClient, repSb.toString(), loadFileName);
            if (inputStream != null) {
                LOGGER.info("获取响应报文成功！！！");
                ByteArrayOutputStream baos = revertToBaosFromInputStream(inputStream);
                if (baos != null) {
                    // 保存响应报文
                    String xybw = CommonUtil.inputStreamToString(new ByteArrayInputStream(baos.toByteArray()));
                    bdcAccessLog.setXybw(xybw);
                    // 解析报文字段 保存日志
                    RespondModel respondModel = (RespondModel) xmlEntityConvertUtil.xmlToEntity(RespondModel.class, new ByteArrayInputStream(baos.toByteArray()));
                    if (respondModel != null) {
                        saveAccessResponseData(respondModel, bdcAccessLog);
                        //获取报文响应成功--上报成功
                        asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 6, "获取ftp返回报文信息成功，更新成功标识", bizMsgId, new Date(), "1");
                        LOGGER.info("响应报文获取成功............,响应内容:{}", JSONObject.toJSONString(respondModel));
                    } else {
                        asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 6, "解析ftp返回报文信息失败", bizMsgId, new Date(), "0");
                        LOGGER.info("respondModel null:{}", bizMsgId);
                    }
                }
            } else {
                //大于零的话重新获取
                if (times > 0) {
                    Thread.sleep(3000);
                    --times;
                    downloadFtpFile(ftpClient, bizMsgId, bdcAccessLog, times, respath);
                } else {
                    LOGGER.error("未获取到响应报文！" + bizMsgId);
                }
            }
        } catch (Exception e) {
            asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 6, "获取ftp返回报文信息异常" + e.getMessage(), bizMsgId, new Date(), "0");
            LOGGER.error("errorMsg:", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    ftpClient.completePendingCommand();
                } catch (IOException e) {
                    LOGGER.error("errorMsg:", e);
                }
            }
        }
        return bdcAccessLog;
    }


    /**
     * sftp 上报
     *
     * @param port
     * @param username
     * @param ip
     * @param password
     * @param downResponseRetryTimes
     * @param reEditFileEnable
     * @param connenttimes
     * @param messageModel
     * @param xsdpath
     * @param path
     * @param bdcAccessLog
     */
    public Boolean uploadSftp(String port, String username,
                              String ip, String password,
                              String downResponseRetryTimes,
                              Boolean reEditFileEnable,
                              Integer connenttimes,
                              MessageModel messageModel,
                              String xsdpath, String path, String respath,
                              BdcAccessLog bdcAccessLog) {
        if (StringUtils.isBlank(ip) || Objects.isNull(messageModel) || Objects.isNull(messageModel.getHeadModel())) {
            return null;
        }

        String ywh = "空";
        String recType = "";
        String bizMsgId = "";
        if (messageModel != null && messageModel.getHeadModel() != null) {
            ywh = messageModel.getHeadModel().getRecFlowID();
            recType = messageModel.getHeadModel().getRecType();
            bizMsgId = messageModel.getHeadModel().getBizMsgID();
        }
        bdcAccessLog.setYwbwid(bizMsgId);
        Boolean flag = false;
        boolean xsdflag = true;

        // 生成 xml
        String xml = accesssModelHandlerService.getAccessXML(messageModel);
        LOGGER.info("业务号 :{},请求上报报文：{}", ywh, xml);
        //开始保存数据，默认成功
        saveAccessUploadLog(messageModel.getHeadModel(), xml, true, bdcAccessLog);
        try {
            //存入上报日志数据
            LOGGER.info("xml生成成功,开始提前入库！");
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，前置保存数据失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            asyncDealUtils.saveJrCzrz(ywh, 3, "组织报文入库失败" + StringUtils.left(e.getMessage(), 3900), bizMsgId, new Date(), "0");
            throw new AppException("生成xml之后，入库失败！" + e.getMessage());
        }

        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath)) {
            StringBuffer xsdFilePath = new StringBuffer(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(recType);
            xsdFilePath.append(".xsd");
//            LOGGER.info("开始XSD验证,ywh :{}",ywh);
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult);
            LOGGER.warn("XSD验证结束并更新汇交日志,ywh :{},valresult :{}", ywh, valResult);
            //验证失败的日志
            if (!xsdflag) {
                bdcAccessLog.setXyxx(StringUtils.left(valResult, 1999));
//                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                bdcAccessLog.setCgbs(JrRzCgbsEnum.XSD_WSB.getBs());

                bdcAccessLog.setBdcqzsh(CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setQllx(CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setDjlx(CommonConstantUtils.ZF_YW_XG);

                saveAccessUploadLog(messageModel.getHeadModel(), xml, false, bdcAccessLog);
                //存入上报日志数据
                try {
                    LOGGER.info("xsd校验失败,开始更新数据库状态！");
                    saveBdcAccessLog(bdcAccessLog);
                } catch (Exception e) {
                    LOGGER.info("xsd校验失败,入库失败！报错：{}", e.getMessage());
                    throw new AppException("xsd校验失败后，入库失败！");
                } finally {
                    //发送通知，校验失败后，更新数据库状态失败
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_4.getYjlx());
                    //查询slbh
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                    if (null != bdcXmDO) {
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                    asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败" + valResult, bizMsgId, new Date(), "0");
                }
            } else {
                asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验成功，" + valResult + "下一步连接sftp", bizMsgId, new Date(), "1");
            }
        }
        //如果xsd校验不通过仍要上报，则根据配置判断，将不通过的xsdflag变为true,继续进行上报
        if (xsdSfjrXz && !xsdflag) {
            asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败，配置仍要上报，下一步连接sftp", bizMsgId, new Date(), "1");
            xsdflag = true;
        }
        if (xsdflag && StringUtils.isNotBlank(bizMsgId)) {
            String uploadFileName = null;
            if (reEditFileEnable) {
                uploadFileName = "Biz" + bizMsgId + ".xml.tmp";//上传汇交xml文件名,为了防止前置机把没有上传完的文件给提取走
            } else {
                uploadFileName = "Biz" + bizMsgId + ".xml";//上传汇交xml文件名
            }
            Session session = null;
            for (int i = 0; i < connenttimes; i++) {
                LOGGER.info("上传报文 - 获取前置机连接 ({}/{}) - - - - -", i + 1, connenttimes);
                session = getSession(port, username, ip, password);
                if (!ObjectUtils.isEmpty(session)) {
                    break;
                }
            }
            if (ObjectUtils.isEmpty(session)) {
                //前置机连接失败返回null
                flag = null;
                bdcAccessLog.setXyxx("尝试获取前置机连接失败！");
                LOGGER.info("尝试获取前置机连接失败,ywh :{},", ywh);
                LOGGER.info("ftp服务类连接失败！ywh为：{}", ywh);
                try {
                    LOGGER.info("ftp服务类连接失败！开始更新数据库状态：{}", ywh);
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.EXCEPTION_WSB.getBs());
                    bdcAccessLog.setUpdatetime(new Date());
                    saveBdcAccessLog(bdcAccessLog);
                } catch (Exception e) {
                    LOGGER.info("ftp服务类连接失败！更新数据库状态失败：{}", ywh);
                    throw new AppException("ftp服务类连接失败后，入库失败！");
                } finally {
                    //发送通知，ftp连接失败，更新数据库状态失败
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
                    //查询slbh
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                    if (null != bdcXmDO) {
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                }
                asyncDealUtils.saveJrCzrz(ywh, 5, "sftp连接前置机失败", bizMsgId, new Date(), "0");
            } else {
                flag = sftp(session, uploadFileName, xml, path);
                if (flag) {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.WATING_RESP.getBs());
                    asyncDealUtils.saveJrCzrz(ywh, 5, "sftp上传成功，结果标识为等待响应，下一步获取返回的报文信息", bizMsgId, new Date(), "1");
                } else {
                    asyncDealUtils.saveJrCzrz(ywh, 5, "sftp上传失败", bizMsgId, new Date(), "0");
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                }
                String loadFileName = "Rep" + bizMsgId + ".xml";
                int times = StringUtils.isNotBlank(downResponseRetryTimes) ? Integer.parseInt(downResponseRetryTimes) : 0;
                if (times >= 0) {
                    sftpRep(session, loadFileName, bdcAccessLog, times, respath);
                }
                LOGGER.info("SFTP处理结束,ywh :{},", ywh);
            }
            //存入上报日志数据
            saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
        }

        try {
            //保存处理  此处try是因为，出现过查询项目信息时报错，导致不入库的问题，这个时候发消息通知
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，最后保存失败，更新数据库状态失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_12.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }
        LOGGER.debug("保存汇交日志结束,ywh :{},", ywh);
        return flag;
    }

    public Boolean uploadSftpOld(String port, String username,
                                 String ip, String password,
                                 String downResponseRetryTimes,
                                 Boolean reEditFileEnable,
                                 Integer connenttimes,
                                 MessageModelOld messageModel,
                                 String xsdpath, String path, String respath,
                                 BdcAccessLog bdcAccessLog) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }

        String ywh = "空";
        String recType = "";
        String bizMsgId = "";
        if (messageModel != null && messageModel.getHeadModel() != null) {
            ywh = messageModel.getHeadModel().getRecFlowID();
            recType = messageModel.getHeadModel().getRecType();
            bizMsgId = messageModel.getHeadModel().getBizMsgID();
        }
        boolean flag = false;
        boolean xsdflag = true;
        // 生成 xml
        LOGGER.debug("开始生成XML报文,ywh :{}", ywh);
        String xml = accesssModelHandlerService.getAccessXMLOld(messageModel);
        LOGGER.info("请求上报报文：{}", xml);
        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath)) {
            StringBuffer xsdFilePath = new StringBuffer(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(recType);
            xsdFilePath.append(".xsd");
//            LOGGER.info("开始XSD验证,ywh :{}",ywh);
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult);
            LOGGER.debug("XSD验证结束并更新汇交日志,ywh :{},valresult :{}", ywh, valResult);
            //验证失败的日志
            if (!xsdflag) {
                bdcAccessLog.setXyxx(StringUtils.left(valResult, 1999));
                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                saveAccessUploadLog(messageModel.getHeadModel(), xml, false, bdcAccessLog);
            }
        }
        if (xsdflag && StringUtils.isNotBlank(bizMsgId)) {
            String uploadFileName = null;
            if (reEditFileEnable) {
                uploadFileName = "Biz" + bizMsgId + ".xml.tmp";//上传汇交xml文件名,为了防止前置机把没有上传完的文件给提取走
            } else {
                uploadFileName = "Biz" + bizMsgId + ".xml";//上传汇交xml文件名
            }
            Session session = null;
            for (int i = 0; i < connenttimes; i++) {
                LOGGER.info("上传报文 - 获取前置机连接 ({}/{}) - - - - -", i + 1, connenttimes);
                session = getSession(port, username, ip, password);
                if (!ObjectUtils.isEmpty(session)) {
                    break;
                }
            }
            if (ObjectUtils.isEmpty(session)) {
                bdcAccessLog.setXyxx("尝试获取前置机连接失败！");
                LOGGER.info("尝试获取前置机连接失败,ywh :{},", ywh);
            } else {
                flag = sftp(session, uploadFileName, xml, path);
                if (flag) {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.WATING_RESP.getBs());
                } else {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                }
                String loadFileName = "Rep" + bizMsgId + ".xml";
                int times = StringUtils.isNotBlank(downResponseRetryTimes) ? Integer.parseInt(downResponseRetryTimes) : 0;
                if (times >= 0) {
                    sftpRep(session, loadFileName, bdcAccessLog, times, respath);
                }
                LOGGER.info("SFTP处理结束,ywh :{},", ywh);
            }
            //存入上报日志数据
            saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
        }
        //保存处理
        saveBdcAccessLog(bdcAccessLog);
        LOGGER.debug("保存汇交日志结束,ywh :{},", ywh);
        return flag;
    }

    /**
     * sftp 上报-一致性
     *
     * @param port
     * @param username
     * @param ip
     * @param password
     * @param downResponseRetryTimes
     * @param reEditFileEnable
     * @param connenttimes
     * @param messageModel
     * @param xsdpath
     * @param path
     * @param bdcAccessLog
     */
    public Boolean uploadSftpYzx(String port, String username,
                                 String ip, String password,
                                 String downResponseRetryTimes,
                                 Boolean reEditFileEnable,
                                 Integer connenttimes,
                                 MessageModelBdc messageModel,
                                 String xsdpath, String path, String respath,
                                 BdcAccessLog bdcAccessLog) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }

        String ywh = "空";
        String recType = "";
        String bizMsgId = "";
        if (messageModel != null && messageModel.getHeadModel() != null) {
            ywh = messageModel.getHeadModel().getRecFlowID();
            recType = messageModel.getHeadModel().getRecType();
            bizMsgId = messageModel.getHeadModel().getBizMsgID();
        }
        boolean flag = false;
        boolean xsdflag = true;
        // 生成 xml
        LOGGER.debug("sftp开始生成XML报文,ywh :{}", ywh);
        String xml = accesssModelHandlerService.getAccessXMLFroYzx(messageModel);
        LOGGER.info("sftp请求上报报文：{}", xml);
        //开始保存数据，默认成功
        saveAccessUploadLog(messageModel.getHeadModel(), xml, true, bdcAccessLog);
        try {
            //存入上报日志数据
            LOGGER.info("xml生成成功,开始提前入库！");
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，前置保存数据失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            asyncDealUtils.saveJrCzrz(ywh, 3, "组织报文入库失败" + StringUtils.left(e.getMessage(), 3900), bizMsgId, new Date(), "0");
            throw new AppException("生成xml之后，入库失败！");
        }
        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath)) {
            StringBuffer xsdFilePath = new StringBuffer(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(recType);
            xsdFilePath.append(".xsd");
//            LOGGER.info("开始XSD验证,ywh :{}",ywh);
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult);
            LOGGER.debug("XSD验证结束并更新汇交日志,ywh :{},valresult :{}", ywh, valResult);
            //验证失败的日志
            if (!xsdflag) {
                bdcAccessLog.setXyxx(StringUtils.left(valResult, 1999));
//                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                bdcAccessLog.setCgbs(JrRzCgbsEnum.XSD_WSB.getBs());

                bdcAccessLog.setBdcqzsh(CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setQllx(CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setDjlx(CommonConstantUtils.ZF_YW_XG);

                saveAccessUploadLog(messageModel.getHeadModel(), xml, false, bdcAccessLog);
                //存入上报日志数据
                try {
                    LOGGER.info("xsd校验失败,开始更新数据库状态！");
                    saveBdcAccessLog(bdcAccessLog);
                } catch (Exception e) {
                    LOGGER.info("xsd校验失败,入库失败！报错：{}", e.getMessage());
                    throw new AppException("xsd校验失败后，入库失败！");
                } finally {
                    //发送通知，校验失败后，更新数据库状态失败
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_4.getYjlx());
                    //查询slbh
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                    if (null != bdcXmDO) {
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                    asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败" + valResult, bizMsgId, new Date(), "0");
                }

            } else {
                asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验成功，" + valResult + "下一步连接sftp", bizMsgId, new Date(), "1");

            }
        }
        if (xsdflag && StringUtils.isNotBlank(bizMsgId)) {
            asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败配置仍要上报，下一步连接sftp", bizMsgId, new Date(), "1");
            String uploadFileName = null;
            if (reEditFileEnable) {
                uploadFileName = "Biz" + bizMsgId + ".xml.tmp";//上传汇交xml文件名,为了防止前置机把没有上传完的文件给提取走
            } else {
                uploadFileName = "Biz" + bizMsgId + ".xml";//上传汇交xml文件名
            }
            Session session = null;
            for (int i = 0; i < connenttimes; i++) {
                LOGGER.info("sftp上传报文 - 获取前置机连接 ({}/{}) - - - - -", i + 1, connenttimes);
                session = getSession(port, username, ip, password);
                if (!ObjectUtils.isEmpty(session)) {
                    break;
                }
            }
            if (ObjectUtils.isEmpty(session)) {
                bdcAccessLog.setXyxx("sftp尝试获取前置机连接失败！");
                LOGGER.info("sftp尝试获取前置机连接失败,ywh :{},", ywh);
                LOGGER.info("ftp服务类连接失败！ywh为：{}", ywh);
                try {
                    LOGGER.info("ftp服务类连接失败！开始更新数据库状态：{}", ywh);
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.EXCEPTION_WSB.getBs());
                    bdcAccessLog.setUpdatetime(new Date());
                    saveBdcAccessLog(bdcAccessLog);
                } catch (Exception e) {
                    LOGGER.info("ftp服务类连接失败！更新数据库状态失败：{}", ywh);
                    throw new AppException("ftp服务类连接失败后，入库失败！");
                } finally {
                    //发送通知，ftp连接失败，更新数据库状态失败
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
                    //查询slbh
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                    if (null != bdcXmDO) {
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                }
                asyncDealUtils.saveJrCzrz(ywh, 5, "sftp连接前置机失败", bizMsgId, new Date(), "0");

            } else {
                flag = sftp(session, uploadFileName, xml, path);
                if (flag) {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.WATING_RESP.getBs());
                    asyncDealUtils.saveJrCzrz(ywh, 5, "sftp上传成功，结果标识为等待响应，下一步获取返回的报文信息", bizMsgId, new Date(), "1");

                } else {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                    asyncDealUtils.saveJrCzrz(ywh, 5, "sftp上传失败", bizMsgId, new Date(), "0");

                }
                String loadFileName = "Rep" + bizMsgId + ".xml";
                int times = StringUtils.isNotBlank(downResponseRetryTimes) ? Integer.parseInt(downResponseRetryTimes) : 0;
                if (times >= 0) {
                    sftpRep(session, loadFileName, bdcAccessLog, times, respath);
                }
                LOGGER.info("SFTP处理结束,ywh :{},", ywh);
            }
            //存入上报日志数据
            saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
        }
        try {
            //保存处理  此处try是因为，出现过查询项目信息时报错，导致不入库的问题，这个时候发消息通知
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，最后保存失败，更新数据库状态失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_12.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }
        LOGGER.debug("保存汇交日志结束,ywh :{},", ywh);
        return flag;
    }

    /**
     * ftp上报
     *
     * @param ip
     * @param username
     * @param password
     * @param port
     * @param downResponseRetryTimes
     * @param messageModel
     * @param xsdpath
     * @param path
     * @param respath
     * @param bdcAccessLog
     */
    public Boolean uploadFtp(String ip, String username,
                             String password, String port,
                             String downResponseRetryTimes,
                             MessageModel messageModel,
                             String xsdpath, String path,
                             String respath, BdcAccessLog bdcAccessLog) {


        if (StringUtils.isBlank(ip) || Objects.isNull(messageModel) || Objects.isNull(messageModel.getHeadModel())) {
            return null;
        }
        Boolean flag = false;
        boolean xsdflag = true;
        String ywh = "空";
        String recType = "";
        String bizMsgId = "";
        if (messageModel != null && messageModel.getHeadModel() != null) {
            ywh = messageModel.getHeadModel().getRecFlowID();
            recType = messageModel.getHeadModel().getRecType();
            bizMsgId = messageModel.getHeadModel().getBizMsgID();
        }
        // 生成 xml
        String xml = accesssModelHandlerService.getAccessXML(messageModel);
        LOGGER.warn("项目id{}请求上报报文：{}", ywh, xml);
        //开始保存数据，默认成功
        saveAccessUploadLog(messageModel.getHeadModel(), xml, true, bdcAccessLog);
        try {
            //存入上报日志数据
            LOGGER.info("项目id{}xml生成成功,开始提前入库！", ywh);
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，前置保存数据失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            asyncDealUtils.saveJrCzrz(ywh, 3, "组织报文入库失败" + StringUtils.left(e.getMessage(), 3900), bizMsgId, new Date(), "0");
            throw new AppException("生成xml之后，入库失败！xmid=" + ywh + "异常信息" + e.getMessage());
        }
        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath) && StringUtils.isNotBlank(recType)) {
            StringBuilder xsdFilePath = new StringBuilder(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(recType);
            xsdFilePath.append(".xsd");
//            LOGGER.info("开始XSD验证,ywh :{}",ywh);
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult);
            //验证失败的日志
            if (!xsdflag) {
                bdcAccessLog.setXyxx(StringUtils.left(valResult, 1999));
//                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                bdcAccessLog.setCgbs(JrRzCgbsEnum.XSD_WSB.getBs());
                bdcAccessLog.setBdcqzsh(CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setQllx(CommonConstantUtils.ZF_YW_XG);
                bdcAccessLog.setDjlx(CommonConstantUtils.ZF_YW_XG);
                saveAccessUploadLog(messageModel.getHeadModel(), xml, false, bdcAccessLog);
                //存入上报日志数据
                try {
                    LOGGER.info("xsd校验失败,开始更新数据库状态！");
                    saveBdcAccessLog(bdcAccessLog);
                } catch (Exception e) {
                    LOGGER.info("xsd校验失败,入库失败！报错：{}", e.getMessage());
                    throw new AppException("xsd校验失败后，入库失败！");
                } finally {
                    //发送通知，校验失败后，更新数据库状态失败
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_4.getYjlx());
                    //查询slbh
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                    if (null != bdcXmDO) {
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                    //异步保存接入操作日志
                    asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败" + valResult, bizMsgId, new Date(), "0");
                }
            } else {
                asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验成功，" + valResult + "下一步连接ftp", bizMsgId, new Date(), "1");
            }
            LOGGER.debug("XSD验证结束并更新汇交日志,ywh :{},valresult :{}", ywh, valResult);
        }
        //如果xsd校验不通过仍要上报，则根据配置判断，将不通过的xsdflag变为true,继续进行上报
        if (xsdSfjrXz) {
            asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败配置仍要上报，下一步连接ftp", bizMsgId, new Date(), "1");
            xsdflag = true;
        }
        if (xsdflag && StringUtils.isNotBlank(bizMsgId)) {
            String localCharset = StringToolUtils.ENCODING_GBK;
            try {
                FTPClient ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                    // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                    localCharset = StringToolUtils.ENCODING_UTF8;
                    asyncDealUtils.saveJrCzrz(ywh, 5, "ftp连接成功，下一步准备上传ftp文件", bizMsgId, new Date(), "1");
                    ftpClient.setControlEncoding(localCharset);
                    ftpClient.enterLocalPassiveMode();// 设置被动模式
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
                    //上报
                    flag = uploadFtpFile(ftpClient, localCharset, bizMsgId, xml, path);
                    if (flag) {
                        bdcAccessLog.setCgbs(JrRzCgbsEnum.WATING_RESP.getBs());
                        //等待ftp响应
                        asyncDealUtils.saveJrCzrz(ywh, 5, "ftp上传成功，结果标识为等待响应，下一步获取返回的报文信息", bizMsgId, new Date(), "1");
                    } else {
                        bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                        asyncDealUtils.saveJrCzrz(ywh, 5, "ftp上传失败", bizMsgId, new Date(), "0");
                    }
                    if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, logversion)) {
                        bdcAccessLog.setSjjrrq(messageModel.getDataModel().getDjtDjSlsqList().get(0).getJssj());
                    }
                    //获取上报响应报文
                    int times = StringUtils.isNotBlank(downResponseRetryTimes) ?
                            Integer.parseInt(downResponseRetryTimes) : 0;
                    if (times >= 0) {
                        downloadFtpFile(ftpClient, bizMsgId, bdcAccessLog, times, respath);
                    }
                    LOGGER.info("FTP处理结束,ywh :{},", ywh);
                } else {
                    //ftp连接失败返回null
                    flag = null;
                    LOGGER.info("ftp服务类连接失败！ywh为：{}", ywh);
                    try {
                        LOGGER.info("ftp服务类连接失败！开始更新数据库状态：{}", ywh);
                        bdcAccessLog.setCgbs(JrRzCgbsEnum.EXCEPTION_WSB.getBs());
                        bdcAccessLog.setUpdatetime(new Date());
                        saveBdcAccessLog(bdcAccessLog);
                    } catch (Exception e) {
                        LOGGER.info("ftp服务类连接失败！更新数据库状态失败：{}", ywh);
                        throw new AppException("ftp服务类连接失败后，入库失败！");
                    } finally {
                        //发送通知，ftp连接失败，更新数据库状态失败
                        MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                        msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
                        //查询slbh
                        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                        if (null != bdcXmDO) {
                            msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                        }
                        accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                        asyncDealUtils.saveJrCzrz(ywh, 5, "ftp接入失败", bizMsgId, new Date(), "0");
                    }
                }
            } catch (IOException e) {
                flag = null;
                LOGGER.error("errorMsg:", e);
                bdcAccessLog.setXyxx(StringToolUtils.left(e.getMessage(), 1999));
                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                asyncDealUtils.saveJrCzrz(ywh, 5, "ftp上传异常" + e.getMessage(), bizMsgId, new Date(), "0");
            } finally {
                //存入上报日志数据
                if (Objects.equals(accessDataPartField, CommonConstantUtils.ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL)) {
                    try {
                        saveAccessUploadLog(messageModel, xml, flag, bdcAccessLog);
                    } catch (Exception e) {
                        LOGGER.info("从messageModel中获取数据失败！ywlsh:{}", bdcAccessLog.getYwlsh());
                        saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);

                    }
                } else if (Objects.equals(accessDataPartField, CommonConstantUtils.ACCESS_DATA_PART_FIELD_DEFAULT)) {
                    saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
                }

            }
        } else {
            LOGGER.debug("上报报文验证失败：{}", bdcAccessLog.toString());
        }
        try {
            //保存处理  此处try是因为，出现过查询项目信息时报错，导致不入库的问题，这个时候发消息通知
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，最后保存失败，更新数据库状态失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_12.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }

        LOGGER.debug("保存汇交日志结束,ywh :{},", ywh);
        return flag;
    }

    public Boolean uploadFtpOld(String ip, String username,
                                String password, String port,
                                String downResponseRetryTimes,
                                MessageModelOld messageModel,
                                String xsdpath, String path,
                                String respath, BdcAccessLog bdcAccessLog) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        boolean flag = false;
        boolean xsdflag = true;
        String ywh = "空";
        String recType = "";
        String bizMsgId = "";
        if (messageModel != null && messageModel.getHeadModel() != null) {
            ywh = messageModel.getHeadModel().getRecFlowID();
            recType = messageModel.getHeadModel().getRecType();
            bizMsgId = messageModel.getHeadModel().getBizMsgID();
        }
        LOGGER.info("old开始生成XML报文,ywh :{}", ywh);
        // 生成 xml
        String xml = accesssModelHandlerService.getAccessXMLOld(messageModel);

        LOGGER.info("old请求上报报文：{}", xml);
        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath) && StringUtils.isNotBlank(recType)) {
            StringBuilder xsdFilePath = new StringBuilder(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(recType);
            xsdFilePath.append(".xsd");
//            LOGGER.info("开始XSD验证,ywh :{}",ywh);
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("old验证通过", valResult) || StringUtils.isBlank(valResult);
            //验证失败的日志
            if (!xsdflag) {
                bdcAccessLog.setXyxx(StringToolUtils.left(valResult, 1999));
                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                saveAccessUploadLog(messageModel.getHeadModel(), xml, false, bdcAccessLog);
            }
            LOGGER.debug("XSD验证结束并更新汇交日志,ywh :{},valresult :{}", ywh, valResult);
        }

        if (xsdflag && StringUtils.isNotBlank(bizMsgId)) {
            String localCharset = StringToolUtils.ENCODING_GBK;
            try {
                LOGGER.info("old请求上报前置机地址：ip:{},用户：{}，密码：{}", ip, username, password);
                FTPClient ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                    // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                    localCharset = StringToolUtils.ENCODING_UTF8;
                }
                ftpClient.setControlEncoding(localCharset);
                ftpClient.enterLocalPassiveMode();// 设置被动模式
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
                //上报
                flag = uploadFtpFile(ftpClient, localCharset, bizMsgId, xml, path);
                if (flag) {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.WATING_RESP.getBs());
                } else {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                }
                if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, logversion)) {
                    bdcAccessLog.setSjjrrq(messageModel.getDataModel().getDjtDjSlsqList().get(0).getJssj());
                }
                //获取上报响应报文
                int times = StringUtils.isNotBlank(downResponseRetryTimes) ?
                        Integer.parseInt(downResponseRetryTimes) : 0;
                if (times >= 0) {
                    downloadFtpFile(ftpClient, bizMsgId, bdcAccessLog, times, respath);
                }
                LOGGER.info("oldFTP处理结束,ywh :{},", ywh);
            } catch (IOException e) {
                LOGGER.error("errorMsg:", e);
                bdcAccessLog.setXyxx(StringUtils.left(e.getMessage(), 1999));
            } finally {
                //存入上报日志数据
                saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
            }
        } else {
            LOGGER.debug("old上报报文验证失败：{}", bdcAccessLog.toString());
        }
        //保存处理
        saveBdcAccessLog(bdcAccessLog);
        LOGGER.debug("old保存汇交日志结束,ywh :{},", ywh);
        return flag;
    }

    /**
     * 一致性ftp上报
     *
     * @param ip
     * @param username
     * @param password
     * @param port
     * @param downResponseRetryTimes
     * @param messageModel
     * @param xsdpath
     * @param path
     * @param respath
     * @param bdcAccessLog
     */
    public Boolean uploadFtpYzx(String ip, String username,
                                String password, String port,
                                String downResponseRetryTimes,
                                MessageModelBdc messageModel,
                                String xsdpath, String path,
                                String respath, BdcAccessLog bdcAccessLog) {


        if (StringUtils.isBlank(ip)) {
            return null;
        }
        boolean flag = false;
        boolean xsdflag = true;
        String ywh = "空";
        String recType = "";
        String bizMsgId = "";
        if (messageModel != null && messageModel.getHeadModel() != null) {
            ywh = messageModel.getHeadModel().getRecFlowID();
            recType = messageModel.getHeadModel().getRecType();
            bizMsgId = messageModel.getHeadModel().getBizMsgID();
        }
        LOGGER.info("开始生成XML报文,ywh :{}", ywh);
        // 生成 xml
        String xml = accesssModelHandlerService.getAccessXMLFroYzx(messageModel);
        LOGGER.info("请求上报报文：{}", xml);
        //开始保存数据，默认成功
        saveAccessUploadLog(messageModel.getHeadModel(), xml, true, bdcAccessLog);
        try {
            //存入上报日志数据
            LOGGER.info("xml生成成功,开始提前入库！");
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，前置保存数据失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
            asyncDealUtils.saveJrCzrz(ywh, 3, "组织报文入库失败" + StringUtils.left(e.getMessage(), 3900), bizMsgId, new Date(), "0");
            throw new AppException("生成xml之后，入库失败！");
        }
        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath) && StringUtils.isNotBlank(recType)) {
            StringBuilder xsdFilePath = new StringBuilder(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(recType);
            xsdFilePath.append(".xsd");
//            LOGGER.info("开始XSD验证,ywh :{}",ywh);
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult);
            //验证失败的日志
            if (!xsdflag) {
                bdcAccessLog.setXyxx(StringUtils.left(valResult, 1999));
//                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                bdcAccessLog.setCgbs(JrRzCgbsEnum.XSD_WSB.getBs());

                saveAccessUploadLog(messageModel.getHeadModel(), xml, false, bdcAccessLog);
                //存入上报日志数据
                try {
                    LOGGER.info("xsd校验失败,开始更新数据库状态！");
                    saveBdcAccessLog(bdcAccessLog);
                } catch (Exception e) {
                    LOGGER.info("xsd校验失败,入库失败！报错：{}", e.getMessage());
                    throw new AppException("xsd校验失败后，入库失败！");
                } finally {
                    //发送通知，校验失败后，更新数据库状态失败
                    MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                    msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_4.getYjlx());
                    //查询slbh
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                    if (null != bdcXmDO) {
                        msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                    }
                    accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                    //异步保存接入操作日志
                    asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败" + valResult, bizMsgId, new Date(), "0");
                }
            } else {
                asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验成功，" + valResult + "下一步连接ftp", bizMsgId, new Date(), "1");
            }
            LOGGER.debug("XSD验证结束并更新汇交日志,ywh :{},valresult :{}", ywh, valResult);
        }

        if (xsdflag && StringUtils.isNotBlank(bizMsgId)) {
            asyncDealUtils.saveJrCzrz(ywh, 4, "xsd校验失败配置仍要上报，下一步连接ftp", bizMsgId, new Date(), "1");
            String localCharset = StringToolUtils.ENCODING_GBK;
            try {
                FTPClient ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                    // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                    localCharset = StringToolUtils.ENCODING_UTF8;
                } else {
                    LOGGER.info("ftp服务类连接失败！ywh为：{}", ywh);
                    try {
                        LOGGER.info("ftp服务类连接失败！开始更新数据库状态：{}", ywh);
                        bdcAccessLog.setCgbs(JrRzCgbsEnum.EXCEPTION_WSB.getBs());
                        bdcAccessLog.setUpdatetime(new Date());
                        saveBdcAccessLog(bdcAccessLog);
                    } catch (Exception e) {
                        LOGGER.info("ftp服务类连接失败！更新数据库状态失败：{}", ywh);
                        throw new AppException("ftp服务类连接失败后，入库失败！");
                    } finally {
                        //发送通知，ftp连接失败，更新数据库状态失败
                        MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
                        msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_3.getYjlx());
                        //查询slbh
                        BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
                        if (null != bdcXmDO) {
                            msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
                        }
                        accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
                        asyncDealUtils.saveJrCzrz(ywh, 5, "ftp接入失败", bizMsgId, new Date(), "0");
                    }
                }
                ftpClient.setControlEncoding(localCharset);
                ftpClient.enterLocalPassiveMode();// 设置被动模式
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
                //上报
                flag = uploadFtpFile(ftpClient, localCharset, bizMsgId, xml, path);
                if (flag) {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.WATING_RESP.getBs());
                } else {
                    bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());
                }
                if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_HF, logversion)) {
                    bdcAccessLog.setSjjrrq(messageModel.getDataModel().getDjtDjSlsqList().get(0).getJssj());
                }
                //获取上报响应报文
                int times = StringUtils.isNotBlank(downResponseRetryTimes) ?
                        Integer.parseInt(downResponseRetryTimes) : 0;
                if (times >= 0) {
                    downloadFtpFile(ftpClient, bizMsgId, bdcAccessLog, times, respath);
                }
                LOGGER.info("FTP处理结束,ywh :{},", ywh);
            } catch (IOException e) {
                LOGGER.error("errorMsg:", e);
                bdcAccessLog.setXyxx(StringUtils.left(e.getMessage(), 1999));
                bdcAccessLog.setCgbs(JrRzCgbsEnum.ACCESS_FAIL.getBs());

            } finally {
                //存入上报日志数据
                if (Objects.equals(accessDataPartField, CommonConstantUtils.ACCESS_DATA_PART_FIELD_FROM_BDC_JR_SJJL)) {
                    try {
                        saveAccessUploadLogYzx(messageModel, xml, flag, bdcAccessLog);
                    } catch (Exception e) {
                        LOGGER.info("从messageModel中获取数据失败！ywlsh:{}", bdcAccessLog.getYwlsh());
                        saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);

                    }
                } else if (Objects.equals(accessDataPartField, CommonConstantUtils.ACCESS_DATA_PART_FIELD_DEFAULT)) {
                    saveAccessUploadLog(messageModel.getHeadModel(), xml, flag, bdcAccessLog);
                }

            }
        } else {
            LOGGER.debug("上报报文验证失败：{}", bdcAccessLog.toString());
        }
        try {
            //保存处理  此处try是因为，出现过查询项目信息时报错，导致不入库的问题，这个时候发消息通知
            saveBdcAccessLog(bdcAccessLog);
        } catch (Exception e) {
            //发送通知，最后保存失败，更新数据库状态失败
            MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
            msgNoticeDTO.setYjlx(AccessWarningEnum.STATUS_12.getYjlx());
            //查询slbh
            BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(ywh);
            if (null != bdcXmDO) {
                msgNoticeDTO.setSlbh(bdcXmDO.getSlbh());
            }
            accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
        }
        LOGGER.debug("保存汇交日志结束,ywh :{},", ywh);
        return flag;
    }


    /**
     * @param session
     * @param uploadFileName 上传文件名称
     * @param xml
     * @return
     * @description 上传报文到服务器
     */
    public boolean sftp(Session session, String uploadFileName, String xml, String bizMsg) {
        boolean flag = false;
        Channel channel = null;
        InputStream inputStream = null;
        LOGGER.info("前置机目录" + bizMsg + "............");
        try {
            channel = session.openChannel("sftp");
            channel.connect(30000);
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(bizMsg);
            OutputStream outputStream = channelSftp.put(uploadFileName);
            inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            byte[] b = new byte[1024];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                outputStream.write(b, 0, n);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            LOGGER.info("推送成功............");
            flag = true;
        } catch (Exception e) {
            LOGGER.error("推送失败:", e);
        } finally {
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("errorMsg:", e);
                }
            }
        }
        return flag;
    }

    /**
     * @param session
     * @param loadFileName 响应报文的文件名称
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取响应报文（linux平台的sftp服务器）
     */
    public void sftpRep(Session session, String loadFileName, BdcAccessLog bdcAccessLog, int times, String path) {
        Channel channel = null;
        String repMsg = path;
        InputStream inputStream = null;
        try {
            channel = session.openChannel("sftp");
            channel.connect(30000);
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(repMsg);
            inputStream = channelSftp.get(loadFileName);
            if (inputStream != null) {
                ByteArrayOutputStream baos = revertToBaosFromInputStream(inputStream);
                if (baos != null) {
                    // 保存响应报文
                    String xybw = CommonUtil.inputStreamToString(new ByteArrayInputStream(baos.toByteArray()));
                    bdcAccessLog.setXybw(xybw);
                    RespondModel respondModel = (RespondModel) xmlEntityConvertUtil.xmlToEntity(RespondModel.class, new ByteArrayInputStream(baos.toByteArray()));
                    saveAccessResponseData(respondModel, bdcAccessLog);
                    //获取报文响应成功--上报成功
                    asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 6, "获取sftp返回报文信息成功，更新成功标识", bdcAccessLog.getYwbwid(), new Date(), "1");
                } else {
                    //获取报文响应为空
                    asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 6, "获取sftp返回报文信息为空", bdcAccessLog.getYwbwid(), new Date(), "0");
                }
            } else {
                //大于零的话重新获取
                if (times > 0) {
                    Thread.sleep(3000);
                    --times;
                    sftpRep(session, loadFileName, bdcAccessLog, times, path);
                } else {
                    LOGGER.error("未获取到响应报文！");
                }
            }

        } catch (Exception e) {
            asyncDealUtils.saveJrCzrz(bdcAccessLog.getYwlsh(), 6, "获取sftp返回报文失败" + e.getMessage(), bdcAccessLog.getYwbwid(), new Date(), "0");
            LOGGER.error("获取响应失败：{}", loadFileName);
            LOGGER.error("获取响应失败：{}", e.getMessage());
        } finally {
            session.disconnect();
            if (channel != null && channel.isConnected()) {
                channel.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("errorMsg:", e);
                }
            }
        }
    }

    /**
     * 获取前置机连接
     *
     * @return
     */
    public Session getSession(String port, String username, String ip, String password) {
        Session session = null;
        try {
            JSch jSch = new JSch();
            if (Integer.parseInt(port) <= 0) {
                session = jSch.getSession(username, ip);
            } else {
                session = jSch.getSession(username, ip, Integer.parseInt(port));
            }
        } catch (JSchException e) {
            LOGGER.error("获取前置机连接失败 - ip:{}, port:{}, username:{}, password:{} - 无法连接！", ip, port, username, password, e);
        }
        if (session == null) {
            return session;
        }
        session.setPassword(password);
        Properties properties = new Properties();
        properties.setProperty("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        try {
            session.connect(30000);
        } catch (JSchException e) {
            session = null;
            LOGGER.error("获取前置机连接失败 - ip:{}, port:{}, username:{}, password:{} - 无法连接！", ip, port, username, password, e);
        }
        return session;
    }


    /**
     * 获取ftp的响应报文
     *
     * @param bizMsgIds
     * @param ip
     * @param username
     * @param password
     * @param port
     * @param respath
     * @param bdcAccessLog
     */
    public void getFtpReponse(String bizMsgIds, String ip, String username, String password, String port, String respath, BdcAccessLog bdcAccessLog) {
        if (StringUtils.isNoneBlank(bizMsgIds)) {
            String[] bizMsgId = bizMsgIds.split(",");
            String localCharset = "GBK";
            FTPClient ftpClient = null;
            try {
                ftpClient = FtpUtil.getFTPClient(ip, username, password, Integer.parseInt(port));
                if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
                    // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                    localCharset = "UTF-8";
                }
                ftpClient.setControlEncoding(localCharset);
                ftpClient.enterLocalPassiveMode();// 设置被动模式
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
                for (int i = 0; i < bizMsgId.length; i++) {
                    bdcAccessLog = bdcAccessLog.getClass().newInstance();
                    BdcAccessLog provinceAccess = downloadFtpFile(ftpClient, bizMsgId[i], bdcAccessLog, 0, respath);
                    if (provinceAccess != null && StringUtils.isNotBlank(provinceAccess.getYwbwid())) {
                        //保存处理
                        saveBdcAccessLog(provinceAccess);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("errorMsg:", e);
            } finally {
                if (ftpClient != null && ftpClient.isConnected()) {
                    try {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    } catch (IOException ioe) {
                        LOGGER.error("errorMsg:", ioe);
                    }
                }
            }
        }
    }


    /**
     * 获取sftp的响应报文
     *
     * @param bizMsgIds
     * @param ip
     * @param username
     * @param password
     * @param port
     * @param bdcAccessLog
     */
    public void getSftpReponse(String bizMsgIds, String ip, String username, String password, String port, String path, BdcAccessLog bdcAccessLog) {
        if (StringUtils.isNoneBlank(bizMsgIds) && bdcAccessLog != null) {
            String[] bizMsgId = bizMsgIds.split(",");
            for (int i = 0; i < bizMsgId.length; i++) {
                try {
                    Session session = getSession(port, username, ip, password);
                    bdcAccessLog = bdcAccessLog.getClass().newInstance();
                    sftpRep(session, "Rep" + bizMsgId[i] + ".xml", bdcAccessLog, 0, path);
                    if (bdcAccessLog != null && StringUtils.isNotBlank(bdcAccessLog.getYwbwid())) {
                        //保存处理
                        saveBdcAccessLog(bdcAccessLog);
                    }
                } catch (Exception e) {
                    LOGGER.error("errorMsg:", e);
                }
            }
        }
    }

    /**
     * 校验生成的xml数据
     */
    public Boolean checkMessageModel(MessageModel messageModel, String xsdpath) {
        boolean xsdflag = true;
        // 生成 xml
        String xml = accesssModelHandlerService.getAccessXML(messageModel);
        //xsd验证 配置的话会走
        if (StringUtils.isNotBlank(xsdpath)) {
            StringBuilder xsdFilePath = new StringBuilder(xsdpath);
            xsdFilePath.append("/");
            xsdFilePath.append(messageModel.getHeadModel().getRecType());
            xsdFilePath.append(".xsd");
            String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
            xsdflag = StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult);
        }
        return xsdflag;
    }
}
