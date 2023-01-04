package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.com.infosec.netsign.agent.NetSignAgent;
import cn.com.infosec.netsign.agent.NetSignResult;
import cn.com.infosec.netsign.agent.exception.NetSignAgentException;
import cn.com.infosec.netsign.agent.exception.ServerProcessException;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcFsPzDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.FailInfo;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcQuerySfztDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.*;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.InvoiceRequestDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFpxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcFsPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxmVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.config.FsConfig;
import cn.gtmap.realestate.exchange.core.dto.fs.MyQRCodeImage;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.EInvoice;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.fs.head.Header;
import cn.gtmap.realestate.exchange.util.Base64Util;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jp.sourceforge.qrcode.QRCodeDecoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/29
 * @description 南通收费
 */
@Service
public class NantongSfServiceImpl {

    // 编号 类型
    private static final String BHSERVICE_YWLX = "fsjkPayCode";
    // 编号 类型
    private static final String BHSERVICE_GASFHC_YWLX = "bjjkgasfhc";
    // 自增时间范围
    private static final String BHSERVICE_ZZSJFW = "YEAR";

    private static final String FS_SOCKET_HOST = "fs.socket.host";
    private static final String FS_SOCKET_PORT = "fs.socket.port";
    private static final String FS_SOCKET_TIMEOUT = "fs.socket.timeout";

    // 财政电子票据配置
    // 电子票据标签
    private static final String EINVOICE_TAG = "fs.eInvoiceTag";
    private static final String EINVOICE_NAME = "fs.eInvoiceName";
    private static final String PARTY_CODE = "fs.partyCode";
    private static final String PARTY_NAME = "fs.partyName";
    // 电子票据代码
    private static final String EINVOICE_CODE = "fs.eInvoiceCode";
    // 开票单位印章编号
    private static final String INVOICING_PARTY_SEALID = "fs.invoicingPartySealId";
    // 财政部门印章编号
    private static final String SUPERVISOR_PARTY_SEALID = "fs.supervisorPartySealId";
    // 电子票据模板代码
    private static final String EINVOICE_SPECIMEN_CODE = "fs.eInvoiceSpecimenCode";
    // 电子票据监管机构代码
    private static final String SUPERVISOR_AREA_CODE = "fs.supervisorAreaCode";

    // 是否读取数据库配置,默认：false (取yaml配置)，true（取BDC_FS_PZ配置），支持qxdm
    private static final String FS_DQSJKPZ = "fs.dqsjkpz";
    // 发起节点代码,财政编码规则是：非税系统使用非税节点编号；代理银行使用代理银行编码
    @Value("${fs.src:}")
    private String fssrc;
    // 接收方的节点代码，财政编码规则是：非税系统使用非税节点编号；代理银行使用代理银行编码
    @Value("${fs.des:}")
    private String fsdes;
    // 应用程序名称: CNNONTAX
    @Value("${fs.app:}")
    private String fsapp;
    // 报文的版本号，如1.0
    @Value("${fs.ver:}")
    private String fsver;
    // 行政区代码
    @Value("${fs.xzqdm:}")
    private String fsxzqdm;
    // 请求主机地址
    @Value("${fs.socket.host:}")
    private String socketHost;
    // 请求端口号
    @Value("${fs.socket.port:0}")
    private Integer socketPort;
    // 请求超时设置
    @Value("${fs.socket.timeout:30000}")
    private Integer socketTimeOut;
    // 请求token
    @Value("${fs.token:}")
    private String fstoken;
    // 电子票据标签,格式为“财政电子票据标识–监管机构,行政区划代码”。其中，财政电子票据标/识为“CZ-EI”；区划代码为 2 位数字
    @Value("${fs.eInvoiceTag:CZ-EI-00}")
    private String eInvoiceTag;
    @Value("${fs.eInvoiceName:}")
    private String eInvoiceName;
    @Value("${fs.partyCode:}")
    private String partyCode;
    @Value("${fs.partyName:}")
    private String partyName;
    @Value("${fs.eInvoiceCode:}")
    private String eInvoiceCode;
    /**
     * 第三方商品编码配置
     */
    private Map<String, String> itemCodeMap;
    // 开票单位印章编号
    @Value("${fs.invoicingPartySealId:}")
    private String invoicingPartySealId;
    // 财政部门印章编号
    @Value("${fs.supervisorPartySealId:}")
    private String supervisorPartySealId;
    // 电子票据模板代码
    @Value("${fs.eInvoiceSpecimenCode:}")
    private String eInvoiceSpecimenCode;
    // 电子票据监管机构代码
    @Value("${fs.supervisorAreaCode:}")
    private String supervisorAreaCode;

    /**
     * 是否自动换开电子票,默认：N (不换开电子票)，Y（换开电子票）
     */
    @Value("${fs.sfhkdzfp:N}")
    private String fsSfhkdzfp;

    /**
     * 非税推送收费信息时，数据对照的mapid默认为 tsjf_sfxx， 常州情况特殊使用 tsjf_sfxx_cz
     */
    @Value("${fs.ts.sfxx.mapid:tsjf_sfxx}")
    private String fsTsSfxxMapId;

    /**
     * 非税作废收费信息时，数据对照的mapid默认为 zfjf_sfxx
     */
    @Value("${fs.zf.sfxx.mapid:zfjf_sfxx}")
    private String fsZfSfxxMapId;

    /**
     * 是否读取数据库配置,默认：false (取yaml配置)，true（取BDC_FS_PZ配置），支持qxdm
     */
    @Value("${fs.dqsjkpz:false}")
    private String fsDqsjkpz;
    /**
     * 非税增强接口8.2.1： 电子缴款单信息发给财政
     */
    private static final String DZJKD_YWLX = "8994";
    /**
     * 非税增强接口8.2.3： 代理银行/单位换开电子票据之前获取电子票据号码
     */
    private static final String DZPJHM_YWLX = "8999";
    /**
     * 非税增强接口8.2.4： 换开电子票据
     */
    private static final String DZPJKP_YWLX = "8981";
    /**
     * 非税增强接口8.2.6： 电子票据下载
     */
    private static final String DZPJXZ_YWLX = "8982";
    /**
     * 非税增强接口：缴费状态查询接口
     */
    private static final String JFZTCX_YWLX = "8997";
    /**
     * 非税增强接口：应缴信息返回值业务类型
     */
    private static final String YJXX_RESP_YWLX = "5997";
    /**
     * 非税增强接口：通用错误返回业务类型
     */
    private static final String FS_ERROR_YWLX = "5001";
    /**
     * 收入平台：票据信息同步
     */
    private static final String SRPT_PJXXTB_YWLX = "4601";
    /**
     * 收入平台：换开电子发票
     */
    private static final String SRPT_HKDZP_YWLX = "4691";
    /**
     * 收入平台：电子发票下载
     */
    private static final String SRPT_DZFPXZ_YWLX = "4692";
    /**
     * 收入平台：通用应答业务类型
     */
    private static final String SRPT_TYYD_YWLX = "4001";
    /**
     * 票据信息作废返回值业务类型
     */
    private static final String SRPT_PJXXZF_YWLX = "4601-2";

    @Autowired
    private BdcBhFeignService bdcBhFeignService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    private BdcSlFpxxFeignService bdcSlFpxxFeignService;

    @Autowired
    private BdcFsPzFeignService fsPzFeignService;

    @Autowired
    BdcZdFeignService zdFeignService;

    @Autowired
    protected UserManagerUtils userManagerUtils;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NantongSfServiceImpl.class);

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    FsConfig fsConfig;

    //利用@PostConstruct将application中配置的值赋给本地的变量
    @PostConstruct
    public void getServelPort() {
        try {
            NetSignAgent.initialize();
        } catch (NetSignAgentException e) {
            LOGGER.error("初始化签章系统失败:", e);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织登记收费信息推送
     */
    public CommonResponse tsdjfxx(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse commonResponse = new CommonResponse();
        LOGGER.info("推送登记收费信息入参:{}", bdcSfxxDTO);
        String fsczRequestXml = null;
        if (bdcSfxxDTO != null) {
            if (Objects.isNull(bdcSfxxDTO.getDsfSfxxDTO()) || StringUtils.isBlank(bdcSfxxDTO.getDsfSfxxDTO().getQxdm())) {
                throw new MissingArgumentException("缺失第三方收费信息参数！");
            }
            String qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
            JSONObject requestJson = new JSONObject();
            dozerBeanMapper.map(bdcSfxxDTO, requestJson, fsTsSfxxMapId);
            //组织PayInfo信息
            if (CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())) {
                Map<String, BdcSlSfxmVO> sfxmMap = new HashMap<>();
                //ssje大于0 才相加收费项目，其他为0的不推送
                for (BdcSlSfxmVO slSfxmDO : bdcSfxxDTO.getBdcSlSfxmVOList()) {
                    BdcSlSfxmVO bdcSlSfxmDO = new BdcSlSfxmVO();

                    if (null != slSfxmDO.getSsje() && slSfxmDO.getSsje() > 0) {
                        if (sfxmMap.containsKey(slSfxmDO.getSfxmdm())) {
                            BdcSlSfxmVO bdcSlSfxmDO1 = sfxmMap.get(slSfxmDO.getSfxmdm());
                            bdcSlSfxmDO1.setSsje(NumberUtil.formatDigit(bdcSlSfxmDO1.getSsje() + slSfxmDO.getSsje(), 2));
                            bdcSlSfxmDO1.setSl(NumberUtil.formatDigit(bdcSlSfxmDO1.getSl() + slSfxmDO.getSl(), 1));
                        } else {
                            bdcSlSfxmDO.setSl(NumberUtil.formatDigit(slSfxmDO.getSl(), 1));
                            bdcSlSfxmDO.setSfxmmc(slSfxmDO.getSfxmmc());
                            bdcSlSfxmDO.setJedw(slSfxmDO.getJedw());
                            bdcSlSfxmDO.setSsje(NumberUtil.formatDigit(slSfxmDO.getSsje(), 2));
                            sfxmMap.put(slSfxmDO.getSfxmdm(), bdcSlSfxmDO);
                        }
                    }
                }
                String payInfo = "";
                for (String key : sfxmMap.keySet()) {
                    BdcSlSfxmVO slSfxmDO2 = sfxmMap.get(key);
                    String sl = slSfxmDO2.getSl().toString();
                    String slString = sl.substring(0, sl.indexOf('.'));
                    // 使用qxdm@key 组成的值进行对照
                    String sfxmdmDzxx = getDsfZdDzxx("BDC_CZ_SFXX","payinfo", key + "@" + qxdm );
                    if(StringUtils.isNotBlank(sfxmdmDzxx)){
                        key = sfxmdmDzxx;
                    }
                    payInfo += key + "|" + slSfxmDO2.getSfxmmc() + "|" + slSfxmDO2.getSsje() + "|" + "次" + "|" + slString + "|" + "不限制" + "|" + "0.0-0.0#";
                }
                requestJson.put("PayInfo", payInfo);

            }


            // 根据区县代码获取配置项
            changeConfigByQxdm(qxdm);

            //缴费书编码
            String jfsbm = this.payCodeLsh(fsxzqdm, CommonConstantUtils.YWLX_JFSBM);

            requestJson.put("PayCode", jfsbm);
            FsczRequest fsczRequest = new FsczRequest();
            FsczHead head = getFsczHead(DZJKD_YWLX);

            try {
                MSGModel msgModel = this.handlerTsDjfMsgXml(JSONObject.toJSONString(requestJson));

                String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
                LOGGER.info("组织msgXml为：{}", headxml);
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(head);

                fsczRequest.setHead(head);
                fsczRequest.setMsg(msgModel);

                fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
                LOGGER.info("组织fsczRequestXml为：{}", fsczRequestXml);
                String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
                String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf("<AdmDivCode>"), fsczRequestXml.indexOf("</Voucher>"));

                LOGGER.info("需要加密的字符串为：{}", sub);
                String base64Encode = "";
                String base64Decode = "";
                try {
                    base64Encode = Base64Util.str2Baes64StrByGBK(sub);
                    base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64Encode), Charset.forName("GBK"));
                    LOGGER.info("需要解密的字符串为：{}", base64Encode);
                    LOGGER.info("需要解密的字符串为：{}", base64Decode);

                } catch (Exception e) {
                    LOGGER.info("加密报错：{}", sub);
                    LOGGER.info("解密报错：{}", sub);
                    e.printStackTrace();
                }
                //加密后，替换字符串
                String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
                LOGGER.info("替换后：{}", replaceXml);
                String socketString = organizationRequest(DZJKD_YWLX, replaceXml);
                sendSocket(DZJKD_YWLX, socketString, commonResponse, jfsbm);

            } catch (JAXBException e) {
                LOGGER.info("非税转xml出错！", e);
                e.printStackTrace();
            }
//            String socketString = organizationRequest(DZJKD_YWLX, replaceXml);
//            sendSocket(DZJKD_YWLX, socketString, commonResponse, jfsbm);
        } else {
            throw new MissingArgumentException("收费信息实体参数不能为空！");
        }
        return commonResponse;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 组织作废登记收费信息
     */
    public CommonResponse zfdjfxx(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse commonResponse = new CommonResponse();
        LOGGER.info("作废登记收费信息入参:{}", bdcSfxxDTO);
        String fsczRequestXml = null;
        if (bdcSfxxDTO != null) {
            if (Objects.isNull(bdcSfxxDTO.getDsfSfxxDTO()) || StringUtils.isBlank(bdcSfxxDTO.getDsfSfxxDTO().getQxdm())) {
                throw new MissingArgumentException("缺失第三方收费信息参数！");
            }
            String qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
            JSONObject requestJson = new JSONObject();
            dozerBeanMapper.map(bdcSfxxDTO, requestJson, fsZfSfxxMapId);
            //组织PayInfo信息
            if (CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())) {
                Map<String, BdcSlSfxmVO> sfxmMap = new HashMap<>();
                //ssje大于0 才相加收费项目，其他为0的不推送
                for (BdcSlSfxmVO slSfxmDO : bdcSfxxDTO.getBdcSlSfxmVOList()) {
                    BdcSlSfxmVO bdcSlSfxmDO = new BdcSlSfxmVO();

                    if (null != slSfxmDO.getSsje() && slSfxmDO.getSsje() > 0) {
                        if (sfxmMap.containsKey(slSfxmDO.getSfxmdm())) {
                            BdcSlSfxmVO bdcSlSfxmDO1 = sfxmMap.get(slSfxmDO.getSfxmdm());
                            bdcSlSfxmDO1.setSsje(NumberUtil.formatDigit(bdcSlSfxmDO1.getSsje() + slSfxmDO.getSsje(), 2));
                            bdcSlSfxmDO1.setSl(NumberUtil.formatDigit(bdcSlSfxmDO1.getSl() + slSfxmDO.getSl(), 1));
                        } else {
                            bdcSlSfxmDO.setSl(NumberUtil.formatDigit(slSfxmDO.getSl(), 1));
                            bdcSlSfxmDO.setSfxmmc(slSfxmDO.getSfxmmc());
                            bdcSlSfxmDO.setJedw(slSfxmDO.getJedw());
                            bdcSlSfxmDO.setSsje(NumberUtil.formatDigit(slSfxmDO.getSsje(), 2));
                            sfxmMap.put(slSfxmDO.getSfxmdm(), bdcSlSfxmDO);
                        }
                    }
                }
                String payInfo = "";
                for (String key : sfxmMap.keySet()) {
                    BdcSlSfxmVO slSfxmDO2 = sfxmMap.get(key);
                    String sl = slSfxmDO2.getSl().toString();
                    String slString = sl.substring(0, sl.indexOf('.'));
                    // 使用qxdm@key 组成的值进行对照
                    String sfxmdmDzxx = getDsfZdDzxx("BDC_CZ_SFXX","payinfo", key + "@" + qxdm );
                    if(StringUtils.isNotBlank(sfxmdmDzxx)){
                        key = sfxmdmDzxx;
                    }
                    payInfo += key + "|" + slSfxmDO2.getSfxmmc() + "|" + slSfxmDO2.getSsje() + "|" + "次" + "|" + slString + "|" + "不限制" + "|" + "0.0-0.0#";
                }
                requestJson.put("PayInfo", payInfo);

            }


            // 根据区县代码获取配置项
            changeConfigByQxdm(qxdm);

            //缴费书编码
            String jfsbm = bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm();

            requestJson.put("PayCode", jfsbm);
            FsczRequest fsczRequest = new FsczRequest();
            FsczHead head = getFsczHead(DZJKD_YWLX);

            try {
                MSGModel msgModel = this.handlerTsDjfMsgXml(JSONObject.toJSONString(requestJson));

                String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
                LOGGER.info("组织msgXml为：{}", headxml);
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(head);

                fsczRequest.setHead(head);
                fsczRequest.setMsg(msgModel);

                fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
                LOGGER.info("组织fsczRequestXml为：{}", fsczRequestXml);
                String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
                String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf("<AdmDivCode>"), fsczRequestXml.indexOf("</Voucher>"));

                LOGGER.info("需要加密的字符串为：{}", sub);
                String base64Encode = "";
                String base64Decode = "";
                try {
                    base64Encode = Base64Util.str2Baes64StrByGBK(sub);
                    base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64Encode), Charset.forName("GBK"));
                    LOGGER.info("需要解密的字符串为：{}", base64Encode);
                    LOGGER.info("需要解密的字符串为：{}", base64Decode);

                } catch (Exception e) {
                    LOGGER.info("加密报错：{}", sub);
                    LOGGER.info("解密报错：{}", sub);
                    e.printStackTrace();
                }
                //加密后，替换字符串
                String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
                LOGGER.info("替换后：{}", replaceXml);
                String socketString = organizationRequest(DZJKD_YWLX, replaceXml);
                sendSocket(DZJKD_YWLX, socketString, commonResponse, jfsbm);

            } catch (JAXBException e) {
                LOGGER.info("非税转xml出错！", e);
                e.printStackTrace();
            }
//            String socketString = organizationRequest(DZJKD_YWLX, replaceXml);
//            sendSocket(DZJKD_YWLX, socketString, commonResponse, jfsbm);
        } else {
            throw new MissingArgumentException("收费信息实体参数不能为空！");
        }
        return commonResponse;
    }


    private MSGModel handlerTsDjfMsgXml(String requestJson) throws JAXBException {
        MSGModel msgModel = new MSGModel();
        if(fsTsSfxxMapId.equals("tsjf_sfxx_cz")){
            DzjkdVoucherCz dzjkdVoucherCz = JSONObject.parseObject(requestJson, DzjkdVoucherCz.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dzjkdVoucherCz);

            String xml = XmlUtils.getXmlStrByObject(dzjkdVoucherCz, DzjkdVoucherCz.class);
            LOGGER.info("组织Voucher为：{}", xml);
            DzjkdMOFModelCz mofModelCz = new DzjkdMOFModelCz();
            mofModelCz.setVoucherCount(1);
            List<DzjkdVoucherCz> dzjkdMOFModels = new ArrayList<>();
            dzjkdMOFModels.add(dzjkdVoucherCz);
            mofModelCz.setVoucherBody(dzjkdMOFModels);
            String mofXml = XmlUtils.getXmlStrByObject(mofModelCz, DzjkdMOFModelCz.class);
            LOGGER.info("组织mofModel为：{}", mofXml);

            msgModel.setMof(mofModelCz);
            String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
            LOGGER.info("组织msgXml为：{}", msgXml);
        }else{
            DzjkdVoucher dzjkdVoucher = JSONObject.parseObject(requestJson, DzjkdVoucher.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dzjkdVoucher);

            String xml = XmlUtils.getXmlStrByObject(dzjkdVoucher, DzjkdVoucher.class);
            LOGGER.info("组织Voucher为：{}", xml);
            DzjkdMOFModel mofModel = new DzjkdMOFModel();
            mofModel.setVoucherCount(1);
            List<DzjkdVoucher> dzjkdMOFModels = new ArrayList<>();
            dzjkdMOFModels.add(dzjkdVoucher);
            mofModel.setVoucherBody(dzjkdMOFModels);
            String mofXml = XmlUtils.getXmlStrByObject(mofModel, DzjkdMOFModel.class);
            LOGGER.info("组织mofModel为：{}", mofXml);


            msgModel.setMof(mofModel);
            String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
            LOGGER.info("组织msgXml为：{}", msgXml);
        }
        return msgModel;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 换开电子票据
     */
    private CommonResponse dzpjkp(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse commonResponse = new CommonResponse();
        LOGGER.info("换开电子票据入参:{}", bdcSfxxDTO);
        String fsczRequestXml = null;
        if (bdcSfxxDTO != null) {
            //换开电子票据请求
            JSONObject requestJson = new JSONObject();
            dozerBeanMapper.map(bdcSfxxDTO, requestJson, "dzpjkp");
            // 读取bdc_fs_pz非税配置表，根据用户读取配置
            if ("true".equals(fsDqsjkpz)) {
                getFspzByUser(requestJson, bdcSfxxDTO.getDsfSfxxDTO().getUserName(),"kp", "换开电子票据");
            }
            try {
                Date currentDate = new Date();
                LOGGER.info("开始了财政信息操作：");
                EInvoice eInvoice = initEInvoiceInfo(requestJson, bdcSfxxDTO, currentDate);
                LOGGER.info("结束了财政信息操作：");

                String invoiceDataStr = XmlUtils.getXmlStrByObjectWithOutFormat(eInvoice, EInvoice.class);
                invoiceDataStr = dealXml(invoiceDataStr);
                requestJson.put("Invoice_Data", Base64Util.str2Baes64Str(invoiceDataStr));
                //签名信息
                LOGGER.info("开始了签名信息操作：");
                requestJson.put("sign_info", initSignInfo(currentDate, invoiceDataStr));
                LOGGER.info("结束了签名信息操作：");

            } catch (Exception e) {
//                throw new AppException("Invoice_Data组织失败或签章获取失败",e);
                LOGGER.info("签名错误：{}", e);
            }
            FsczRequest fsczRequest = new FsczRequest();
            FsczHead head = getFsczHead(DZPJKP_YWLX);
            MSGModel msgModel = new MSGModel();
            DzpjkpVoucher dzpjkpVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), DzpjkpVoucher.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dzpjkpVoucher);
            try {
                String xml = XmlUtils.getXmlStrByObject(dzpjkpVoucher, DzpjkpVoucher.class);
                LOGGER.info("换开电子票据组织Voucher为：{}", xml);
                DzpjkpMOFModel mofModel = new DzpjkpMOFModel();
                mofModel.setVoucherCount(1);
                List<DzpjkpVoucher> dzpjkpMOFModels = new ArrayList<>();
                dzpjkpMOFModels.add(dzpjkpVoucher);
                mofModel.setVoucherBody(dzpjkpMOFModels);
                String mofXml = XmlUtils.getXmlStrByObject(mofModel, DzpjkpMOFModel.class);
                LOGGER.info("换开电子票据组织mofModel为：{}", mofXml);
                msgModel.setMof(mofModel);
                String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
                LOGGER.info("换开电子票据组织msgXml为：{}", msgXml);
                String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
                LOGGER.info("换开电子票据组织msgXml为：{}", headxml);
                fsczRequest.setHead(head);
                fsczRequest.setMsg(msgModel);
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(head);

                fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
                LOGGER.info("换开电子票据组织fsczRequestXml为：{}", fsczRequestXml);
                String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
                String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf("<PayCode>"), fsczRequestXml.indexOf("</Voucher>"));

                LOGGER.info("需要加密的字符串为：{}", sub);
                String base64Encode = "";
                String base64Decode = "";
                try {
                    base64Encode = Base64Util.str2Baes64StrByGBK(sub);
                    base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64Encode), Charset.defaultCharset());
                    LOGGER.info("需要解密的字符串为：{}", base64Encode);
                    LOGGER.info("需要解密的字符串为：{}", base64Decode);

                } catch (Exception e) {
                    LOGGER.info("加密报错：{}", sub);
                    LOGGER.info("解密报错：{}", sub);
                    e.printStackTrace();
                }
                //加密后，替换字符串
                String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
                LOGGER.info("替换后：{}", replaceXml);
                String socketString = organizationRequest(DZPJKP_YWLX, replaceXml);
                sendSocket(DZPJKP_YWLX, socketString, commonResponse, "");
            } catch (JAXBException e) {
                LOGGER.info("换开电子票据非税转xml出错！", e);
                e.printStackTrace();
            }

        } else {
            throw new MissingArgumentException("收费信息实体参数不能为空！");
        }
        return commonResponse;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电子票据文件下载，如果没开票，就获取电子票据号码，换开发票，下载发票；如果已经开票，就下载发票
     */
    public Object dzpjxz(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse commonResponse = new CommonResponse();
        String fsczRequestXml = null;
        if (bdcSfxxDTO != null) {
            LOGGER.info("电子票据文件下载入参:{}", bdcSfxxDTO.toString());
            String qxdm = "";
            if (bdcSfxxDTO.getDsfSfxxDTO() != null) {
                qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
            }
            // 根据区县代码获取配置项
            changeConfigByQxdm(qxdm);
            changeDzpjConfigByQxdm(qxdm);
            // 是否开票，如果已开票，不在换开发票，只下载
            Integer sfkp = bdcSfxxDTO.getBdcSlSfxxDO().getSfkp() == null ? 0 : bdcSfxxDTO.getBdcSlSfxxDO().getSfkp();
            if (Objects.nonNull(sfkp) && sfkp.equals(1)) {
                LOGGER.info("已经开票，不再重复开票");
            } else {
                //请求获取电子票号
                String billno = bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm();
                CommonResponse dzhmResponse = dzpjhm(bdcSfxxDTO);
                if (dzhmResponse == null || dzhmResponse.getData() == null) {
                    throw new AppException("缴款码:" + billno + "未获取到对应的电子票号");
                }
                JSONObject dzhmResponseData = (JSONObject) dzhmResponse.getData();
                String fph = dzhmResponseData.getString("fph");
                if (StringUtils.isBlank(fph)) {
                    throw new AppException("缴款码:" + billno + "未获取到对应的电子票号");
                }
                //电子票据开票
                bdcSfxxDTO.getBdcSlSfxxDO().setFph(fph);
                CommonResponse kpResponse = dzpjkp(bdcSfxxDTO);
                LOGGER.info("开票返回：{}：", kpResponse.toString());
                if (kpResponse != null && kpResponse.isSuccess()) {
                    //换开成功，sfkp=1
                    BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
                    sfxx.setSfxxid(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid());
                    sfxx.setSfkp(CommonConstantUtils.SF_S_DM);
                    sfxx.setFph(fph);
                    UserDto userDto = userManagerUtils.getCurrentUser();
                    if (userDto != null) {
                        sfxx.setKprxm(userDto.getAlias());
                    }
                    bdcSlSfxxFeignService.updateBdcSlSfxx(sfxx);
                } else {
                    LOGGER.error("换开电子发票失败，失败信息：{}，发票号码：{}", kpResponse.getFail(), fph);
                    throw new AppException("换开发票失败！:失败码：{}" + kpResponse.getFail().toString());
                }
            }
            // 是否下载电子票据
            BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid());
            bdcSfxxDTO.setBdcSlSfxxDO(bdcSlSfxxDO);
            if (bdcSlSfxxDO.getSfkp() == 1) {
                //根据电子票号下载电子票据
                commonResponse = dzpjwjxz(bdcSfxxDTO);
                LOGGER.info("下载电子发票返回：{}：", commonResponse.toString());
            } else {
                throw new AppException("下载发票失败！:失败码：{}" + commonResponse.getFail().toString());
            }
        } else {
            throw new MissingArgumentException("收费信息实体参数不能为空！");
        }
        return commonResponse;
    }

    /**
     * 非税系统增强接口-电子票据文件下载请求报文(8982)
     *
     * @param bdcSfxxDTO 收费信息
     * @return
     */
    private CommonResponse dzpjwjxz(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse commonResponse = new CommonResponse();
        String fsczRequestXml;
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(bdcSfxxDTO, requestJson, "dzpjxz");
        // 读取bdc_fs_pz非税配置表，根据用户读取配置
        if ("true".equals(fsDqsjkpz)) {
            getFspzByUser(requestJson, bdcSfxxDTO.getDsfSfxxDTO().getUserName(),"xz", "电子票据下载");
        }
        FsczRequest fsczRequest = new FsczRequest();
        FsczHead head = getFsczHead(DZPJXZ_YWLX);
        MSGModel msgModel = new MSGModel();
        DzpjxzVoucher dzpjxzVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), DzpjxzVoucher.class);
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dzpjxzVoucher);

        try {
            String xml = XmlUtils.getXmlStrByObject(dzpjxzVoucher, DzpjxzVoucher.class);
            LOGGER.info("根据电子票号下载电子票据组织Voucher为：{}", xml);
            DzpjxzMOFModel mofModel = new DzpjxzMOFModel();
            mofModel.setVoucherCount(1);
            List<DzpjxzVoucher> dzpjxzMOFModels = new ArrayList<>();
            dzpjxzMOFModels.add(dzpjxzVoucher);
            mofModel.setVoucherBody(dzpjxzMOFModels);
            String mofXml = XmlUtils.getXmlStrByObject(mofModel, DzpjxzMOFModel.class);
            LOGGER.info("根据电子票号下载电子票据组织mofModel为：{}", mofXml);
            msgModel.setMof(mofModel);
            String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
            LOGGER.info("根据电子票号下载电子票据组织msgXml为：{}", msgXml);
            String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
            LOGGER.info("根据电子票号下载电子票据组织msgXml为：{}", headxml);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(head);

            fsczRequest.setHead(head);
            fsczRequest.setMsg(msgModel);
            fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
            LOGGER.info("根据电子票号下载电子票据组织fsczRequestXml为：{}", fsczRequestXml);
            String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
            String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf("<BillNo>"), fsczRequestXml.indexOf("</Voucher>"));

            LOGGER.info("需要加密的字符串为：{}", sub);
            String base64Encode = "";
            String base64Decode = "";
            try {
                base64Encode = Base64Util.str2Baes64StrByGBK(sub);
                base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64Encode), Charset.defaultCharset());
                LOGGER.info("需要解密的字符串为：{}", base64Encode);
                LOGGER.info("需要解密的字符串为：{}", base64Decode);

            } catch (Exception e) {
                LOGGER.info("加密报错：{}", sub);
                LOGGER.info("解密报错：{}", sub);
                e.printStackTrace();
            }
            //加密后，替换字符串
            String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
            LOGGER.info("替换后：{}", replaceXml);
            String socketString = organizationRequest(DZPJXZ_YWLX, replaceXml);
            sendSocket(DZPJXZ_YWLX, socketString, commonResponse, "");
        } catch (JAXBException e) {
            LOGGER.info("根据电子票号下载电子票据非税转xml出错！", e);
            e.printStackTrace();
        }
        return commonResponse;
    }

    private SignInfo initSignInfo(Date currentDate, String invoiceDataStr) throws NetSignAgentException, ServerProcessException {
        LOGGER.info("签章开始:{}", invoiceDataStr);
        //签名原文
        byte[] laintext = new byte[0];
        try {
            laintext = invoiceDataStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //签名证书DN，null表示用服务器默认证书进行签名（填证书序列号）
        String subject = null;
        //摘要算法，null表示用服务器默认的摘要算法（默认就是SM2）
        String digestAlg = null;
        //签名
        NetSignResult result = NetSignAgent.detachedSignature(laintext, subject, digestAlg, false);
        //获取base64编码后的签名结果
        String signedTextB64 = result.getStringResult(NetSignResult.SIGN_TEXT);
        LOGGER.info("签章结束:{}");
        //组织签名信息
        SignInfo signInfo = new SignInfo();
        signInfo.setFormat("DETACH");
        signInfo.setAlgorithm("SM2");
        signInfo.setValue(signedTextB64);
        SimpleDateFormat utcDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
        signInfo.setTime(utcDateTime.format(currentDate));
        return signInfo;

    }

    /**
     * 非税增强接口，查询缴费状态（8997）
     * paramJson: {"billNo": "xxx", "sfxxid": "xxx"}
     *
     * @param paramJson 请求参数
     * @return CommonResponse
     */
    public BdcQuerySfztDTO jfztcx(JSONObject paramJson) {
        LOGGER.info("查询缴费状态入参:{}", paramJson.toString());
        if (paramJson.isEmpty()) {
            throw new AppException(ErrorCode.MISSING_ARG, "查询缴费状态接口入参不能为空");
        }
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(paramJson, requestJson, "jfztcx");

        // 根据区县代码，获取对应区县的fs配置内容
        String qxdm = "";
        if (StringUtils.isNotBlank(paramJson.getString("qxdm"))) {
            qxdm = paramJson.getString("qxdm");
        }
        // 根据qxdm获取对应的非税请求参数配置
        changeConfigByQxdm(qxdm);

        CommonResponse<BdcQuerySfztDTO> commonResponse = new CommonResponse();
        try {
            MSGModel msgModel = new MSGModel();
            JfztCxVoucher jfztCxVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), JfztCxVoucher.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(jfztCxVoucher);

            String xml = XmlUtils.getXmlStrByObject(jfztCxVoucher, JfztCxVoucher.class);
            LOGGER.info("缴费状态查询组织JfztCxVoucher——XML信息为：{}", xml);

            // 组织报文体内容
            JfztCxMOFModel jfztCxMOFModel = new JfztCxMOFModel();
            jfztCxMOFModel.setVoucherCount(1);

            List<JfztCxVoucher> jfztCxVoucherList = new ArrayList<>(1);
            jfztCxVoucherList.add(jfztCxVoucher);
            jfztCxMOFModel.setVoucherBody(jfztCxVoucherList);

            String mofXml = XmlUtils.getXmlStrByObject(jfztCxMOFModel, JfztCxMOFModel.class);
            LOGGER.info("缴费状态查询组织报文体内容DzfpXzMOFModel为：{}", mofXml);
            msgModel.setMof(jfztCxMOFModel);

            // 生成xml请求参数
            String socketString = this.generateSocketString(JFZTCX_YWLX, msgModel, "<PayCode>");
            sendSocket(JFZTCX_YWLX, socketString, commonResponse, "");
        } catch (JAXBException e) {
            LOGGER.info("非税转xml出错！", e);
            e.printStackTrace();
        }

        if (commonResponse.isSuccess()) {
            return commonResponse.getData();
        } else {
            throw new AppException(ErrorCode.CUSTOM, commonResponse.getFail().getMessage());
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 电子票据号码获取
     */
    public CommonResponse dzpjhm(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse commonResponse = new CommonResponse();
        if (StringUtils.isBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm())) {
            throw new AppException("缴款书编码为空");
        }
        String fsczRequestXml = null;
        JSONObject requestJson = new JSONObject();
        //请求获取电子票号
        InvoiceRequestDTO invoiceRequestDTO = new InvoiceRequestDTO();
        invoiceRequestDTO.setBillno(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm());
        invoiceRequestDTO.setQxdm(bdcSfxxDTO.getDsfSfxxDTO().getQxdm());
        dozerBeanMapper.map(invoiceRequestDTO, requestJson, "dzpjhm");
        // 读取bdc_fs_pz非税配置表，根据用户读取配置
        if ("true".equals(fsDqsjkpz)) {
            getFspzByUser(requestJson, bdcSfxxDTO.getDsfSfxxDTO().getUserName(), "hq", "获取电子票据号码");
        }
        FsczRequest fsczRequest = new FsczRequest();
        FsczHead head = getFsczHead(DZPJHM_YWLX);
        MSGModel msgModel = new MSGModel();
        DzhmVoucher dzhmVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), DzhmVoucher.class);
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dzhmVoucher);

        try {
            String xml = XmlUtils.getXmlStrByObject(dzhmVoucher, DzhmVoucher.class);
            LOGGER.info("电子票据号码获取组织Voucher为：{}", xml);
            DzhmMOFModel mofModel = new DzhmMOFModel();
            mofModel.setVoucherCount(1);
            List<DzhmVoucher> dzpjhmMOFModels = new ArrayList<>();
            dzpjhmMOFModels.add(dzhmVoucher);
            mofModel.setVoucherBody(dzpjhmMOFModels);
            String mofXml = XmlUtils.getXmlStrByObject(mofModel, DzhmMOFModel.class);
            LOGGER.info("电子票据号码获取组织mofModel为：{}", mofXml);
            msgModel.setMof(mofModel);
            String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
            LOGGER.info("电子票据号码获取组织msgXml为：{}", msgXml);
            String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
            LOGGER.info("电子票据号码获取组织msgXml为：{}", headxml);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(head);

            fsczRequest.setHead(head);
            fsczRequest.setMsg(msgModel);
            fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
            LOGGER.info("电子票据号码获取组织fsczRequestXml为：{}", fsczRequestXml);
            String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
            String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf("<BillTypeCode>"), fsczRequestXml.indexOf("</Voucher>"));

            LOGGER.info("需要加密的字符串为：{}", sub);
            String base64Encode = "";
            String base64Decode = "";
            try {
                base64Encode = Base64Util.str2Baes64StrByGBK(sub);
                base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64Encode), Charset.defaultCharset());
                LOGGER.info("需要解密的字符串为：{}", base64Encode);
                LOGGER.info("需要解密的字符串为：{}", base64Decode);

            } catch (Exception e) {
                LOGGER.info("加密报错：{}", sub);
                LOGGER.info("解密报错：{}", sub);
                e.printStackTrace();
            }
            //加密后，替换字符串
            String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
            LOGGER.info("替换后：{}", replaceXml);
            String socketString = organizationRequest(DZPJHM_YWLX, replaceXml);
            sendSocket(DZPJHM_YWLX, socketString, commonResponse, "");

        } catch (JAXBException e) {
            LOGGER.info("非税转xml出错！", e);
            e.printStackTrace();
        }

        return commonResponse;
    }

    private EInvoice initEInvoiceInfo(JSONObject requestJson, BdcSfxxDTO bdcSfxxDTO, Date currentDate) {
        LOGGER.info("开始了财政信息操作initEInvoiceInfo：");
        Header header = Header.HeaderBuilder.aHeader().withEInvoiceID(new StringBuilder(bdcSfxxDTO.getBdcSlSfxxDO().getFph()).reverse().toString() + "-" + new StringBuilder(eInvoiceCode).reverse().toString())
                .withEInvoiceTag(eInvoiceTag)
                .withVersion("1.1.0").build();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

        List<Item> itemList = null;
        itemList = getItems(bdcSfxxDTO, itemList, requestJson);
        EInvoiceData eInvoiceData = EInvoiceData.EInvoiceDataBuilder.anEInvoiceData()
                .withMain(Main.MainBuilder.aMain()
                        .withEInvoiceName(eInvoiceName)
                        .withEInvoiceCode(eInvoiceCode)
                        .withEInvoiceNumber(bdcSfxxDTO.getBdcSlSfxxDO().getFph())
                        .withRandomNumber("1234")
                        .withEInvoiceSpecimenCode(eInvoiceSpecimenCode)
                        .withSupervisorAreaCode(supervisorAreaCode)
                        .withTotalAmount(bdcSfxxDTO.getBdcSlSfxxDO().getHj())
                        .withIssueDate(date.format(currentDate))
                        .withIssueTime(time.format(currentDate))
                        .withBizCode(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid())
                        .withHandlingPerson(bdcSfxxDTO.getDsfSfxxDTO().getSlrxm())
                        .withChecker(bdcSfxxDTO.getDsfSfxxDTO().getSlrxm())
                        .withRemark(bdcSfxxDTO.getBdcSlSfxxDO().getBz())
                        .withInvoicingParty(InvoicingParty.InvoicingPartyBuilder.anInvoicingParty()
                                .withInvoicingPartyCode(bdcSfxxDTO.getDsfSfxxDTO().getQxdm() + partyCode)
                                .withInvoicingPartyName(partyName)
                                .withRecName(bdcSfxxDTO.getBdcSlSfxxDO().getSfrxm())
                                .withRecAcct(bdcSfxxDTO.getBdcSlSfxxDO().getSfrzh())
                                .withRecOpBk(bdcSfxxDTO.getBdcSlSfxxDO().getSfrkhyh())
                                .build())
                        .withPayerParty(PayerParty.PayerPartyBuilder.aPayerParty()
                                .withPayerPartyName(bdcSfxxDTO.getBdcSlSfxxDO().getJfrxm())
                                .withPayerPartyCode(bdcSfxxDTO.getDsfSfxxDTO().getJfrzjh())
                                .withPayerPartyType("1")
                                .build())
                        .withMainExt(MainExt.MainExtBuilder.aMainExt()
                                .withPayCode(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm())
                                .build())
                        .withInvoicingPartySeal(InvoicingPartySeal.InvoicingPartySealBuilder.anInvoicingPartySeal()
                                .withSealId(invoicingPartySealId)
                                .build())
                        .withSupervisorPartySeal(SupervisorPartySeal.SupervisorPartySealBuilder.aSupervisorPartySeal()
                                .withSealId(supervisorPartySealId)
                                .build())
                        .build())
                .withDetails(Details.DetailsBuilder.aDetails()
                        .withItem(itemList)
                        .build()).build();
        EInvoice eInvoice = EInvoice.EInvoiceBuilder.anEInvoice()
                .withEInvoiceData(eInvoiceData)
                .withHeader(header).build();
        LOGGER.info("结束了财政信息操作initEInvoiceInfo：");

        return eInvoice;
    }

    private List<Item> getItems(BdcSfxxDTO bdcSfxxDTO, List<Item> itemList, JSONObject requestJson) {
        if (CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())) {
            List<Item> finalItemList = new ArrayList<>(bdcSfxxDTO.getBdcSlSfxmVOList().size());
            List<String> itemRemarkList = new ArrayList<>(bdcSfxxDTO.getBdcSlSfxmVOList().size());
            bdcSfxxDTO.getBdcSlSfxmVOList().forEach(bdcSlSfxmVO -> {
                if (null != bdcSlSfxmVO.getSsje() && bdcSlSfxmVO.getSsje() > 0) {
                    Item item = Item.ItemBuilder.anItem()
                            .withItemAmount(bdcSlSfxmVO.getSsje())
                            .withItemStd(bdcSlSfxmVO.getSfxmdj())
                            .withItemCode(bdcSlSfxmVO.getSfxmdm())
                            .withItemUnit(bdcSlSfxmVO.getJedw())
                            .withItemQuantity(null != bdcSlSfxmVO.getSl() ? bdcSlSfxmVO.getSl().intValue() : null)
                            .withItemName(bdcSlSfxmVO.getSfxmmc())
                            .build();
                    if (MapUtils.isNotEmpty(itemCodeMap) && itemCodeMap.containsKey(bdcSlSfxmVO.getSfxmmc())) {
                        item.setItemCode(itemCodeMap.get(bdcSlSfxmVO.getSfxmmc()));
                    }
                    // 新增备注, 设置为收费明细及坐落 （1.0*80.0=80.0,2.0*80.0=160.0 + 坐落）
                    if (null != bdcSlSfxmVO.getSl() && bdcSlSfxmVO.getSl() > 0) {
                        String itemRemark = bdcSlSfxmVO.getSl() + "*" + bdcSlSfxmVO.getSsje() + "=" + bdcSlSfxmVO.getSl()*bdcSlSfxmVO.getSsje();
                        itemRemarkList.add(itemRemark);
                    }
                    finalItemList.add(item);
                }

            });
            if (CollectionUtils.isNotEmpty(itemRemarkList)) {
                String itemRemarkAll = StringUtils.join(itemRemarkList, ",") + " " + bdcSfxxDTO.getDsfSfxxDTO().getZl();
                finalItemList.get(0).setItemRemark(itemRemarkAll);
            }
            itemList = finalItemList;
        }
        return itemList;
    }

    @NotNull
    private String dealXml(String invoiceDataStr) {
        if (invoiceDataStr.startsWith("<?xml ")) {
            invoiceDataStr = invoiceDataStr.substring(invoiceDataStr.indexOf("?>") + 2);
        }
        if (invoiceDataStr.contains("<EInvoice>")) {
            invoiceDataStr = invoiceDataStr.replaceAll("<EInvoice>", "");
            invoiceDataStr = invoiceDataStr.replaceAll("</EInvoice>", "");
        }
        if (invoiceDataStr.contains(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")) {
            invoiceDataStr = invoiceDataStr.replaceAll(" xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "");
        }
        if (invoiceDataStr.contains("<ItemExt/>")) {
            invoiceDataStr = invoiceDataStr.replaceAll("<ItemExt/>", "<ItemExt><ItemDetailName/></ItemExt>");
        }
        return invoiceDataStr;
    }

    /**
     * 交款单缴款码生成规则
     *
     * @param qxdm qxdm
     * @return
     * @Date 2021/11/30
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public String payCodeLsh(String qxdm, String ywlx) {
        String payCodeLsh = "";
        if (StringUtils.isNotBlank(qxdm)) {
            String xlhStr = "";
            Integer xlh = bdcBhFeignService.queryLsh(ywlx, BHSERVICE_ZZSJFW);
            if (xlh != null) {
                // 0补齐10位流水号
                xlhStr = String.format("%010d", xlh);
            }
            String yy = DateUtils.getCurrYearYear().substring(2, 4);
            payCodeLsh = qxdm + yy + "30" + xlhStr;
        } else {
            throw new MissingArgumentException("qxdm不能为空！");
        }
        LOGGER.info("非税缴款流水号为：{}", payCodeLsh);
        return payCodeLsh;
    }

    public FsczHead getFsczHead(String MsgNo) {
        String wybs = DateUtils.formateTimeYmdhms(new Date());
        FsczHead head = new FsczHead();
        head.setAPP(fsapp);
        head.setSRC(fssrc);
        head.setDES(fsdes);
        head.setMsgID(wybs);
        head.setMsgNo(MsgNo);
        head.setMsgRef(wybs);
        head.setWorkDate(DateUtils.getCurrDay());
        head.setVER(fsver);
        return head;
    }

    public String organizationRequest(String ywlx, String dataXml) {
        dataXml = dataXml.replace(" standalone=\"yes\"", "");
        String socketData = "";
        String mac = DigestUtils.sha256Hex(fstoken + dataXml);
        String data = fsapp + fsver + ywlx + dataXml + mac;
        String length = String.format("%-8d", 8 + data.length());
        socketData = length + data;
        LOGGER.info("组装后整个报文为：{}", socketData);
        return socketData;
    }

    /**
     * @param socketString socketString
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description socket请求
     */
    private void sendSocket(String ywlx, String socketString, CommonResponse commonResponse, String jfsbm) {
        Socket request = null;
        OutputStream requestOp = null;
        InputStream requestInp = null;
        try {
            request = new Socket(socketHost, socketPort);
            request.setSoTimeout(socketTimeOut); //设置读取超时时间10s
            requestOp = request.getOutputStream();
            requestInp = request.getInputStream();
            LOGGER.info("非税增强接口查询请求参数：{}", socketString);
            SocketUtil.writeStr2Stream(socketString, requestOp);
            // 3.读取返回消息
            String result = SocketUtil.readStrFromStream(requestInp);

//      String result = "609     CNNONTAX1.05001<?xml version=\"1.0\" encoding=\"GBK\"?><CFX><HEAD><APP>CNNONTAX</APP><VER>1.0</VER><SRC>F320682</SRC><DES>K3206820002</DES><MsgNo>5001</MsgNo><MsgID>1476124974174437376</MsgID><MsgRef>20211229174706</MsgRef><WorkDate>20211229</WorkDate><Reserve/></HEAD><MSG><MOF><VoucherCount>1</VoucherCount><VoucherBody><Voucher>PFZvdWNoZXI+PE9yaU1zZ05vPjg5OTQ8L09yaU1zZ05vPjxSZXN1bHQ+MTgwMTI8L1Jlc3VsdD48SW5mb3JtYXRpb24+w7vT0LjDtaXOu7XEtPrK1bfRyKjP3qO6MDEwNTIwNDwvSW5mb3JtYXRpb24+PC9Wb3VjaGVyPg==</Voucher></VoucherBody></MOF></MSG></CFX>C439DD3E8787029430A8FAF3D6428C8BFC8AFA87FD80F634D842705495127136\n";
//            String result = "645     CNNONTAX1.05001<?xml version=\"1.0\" encoding=\"GBK\"?><CFX><HEAD><APP>CNNONTAX</APP><VER>1.0</VER><SRC>F320682</SRC><DES>K3206820002</DES><MsgNo>5001</MsgNo><MsgID>1476470903570169856</MsgID><MsgRef>20211230164142</MsgRef><WorkDate>20211230</WorkDate><Reserve/></HEAD><MSG><MOF><VoucherCount>1</VoucherCount><VoucherBody><Voucher>PFZvdWNoZXI+PE9yaU1zZ05vPjg5OTQ8L09yaU1zZ05vPjxSZXN1bHQ+MDAwMDA8L1Jlc3VsdD48SW5mb3JtYXRpb24+aHR0cDovL3BheS5qc2N6dC5jbi9mbnRvcC9xcmNvZGU/YmlsbElkPTMyMDY4MjIxMzAwMDAwMDAwMDM2PC9JbmZvcm1hdGlvbj48L1ZvdWNoZXI+</Voucher></VoucherBody></MOF></MSG></CFX>813963044056F7F6D0BAE531FD4FC23ABC3694E0BDF39302BDCA7C365C4ED556";
//            String result = "581     CNNONTAX1.05001<?xml version=\"1.0\" encoding=\"GBK\"?><CFX><HEAD><APP>CNNONTAX</APP><VER>1.0</VER><SRC>F320682</SRC><DES>K3206820002</DES><MsgNo>5001</MsgNo><MsgID>1476496174352433152</MsgID><MsgRef>20211230182208</MsgRef><WorkDate>20211230</WorkDate><Reserve/></HEAD><MSG><MOF><VoucherCount>1</VoucherCount><VoucherBody><Voucher>PFZvdWNoZXI+PE9yaU1zZ05vPjg5OTk8L09yaU1zZ05vPjxSZXN1bHQ+MDAwMDA8L1Jlc3VsdD48SW5mb3JtYXRpb24+MDAxMzkzMzkyMjwvSW5mb3JtYXRpb24+PC9Wb3VjaGVyPg==</Voucher></VoucherBody></MOF></MSG></CFX>F2C31F86006D6E92771C23A94A9075B1551C94B82E393C50D6B3811470FAC9D6";
//            String result = "1665    CNNONTAX1.08994<?xml version=\"1.0\" encoding=\"GBK\"?><CFX><HEAD><APP>CNNONTAX</APP><VER>1.0</VER><SRC>K3206820002</SRC><DES>F320682</DES><MsgNo>8994</MsgNo><MsgID>20220105095231</MsgID><MsgRef>20220105095231</MsgRef><WorkDate>20220105</WorkDate><Reserve></Reserve></HEAD><MSG><MOF><VoucherCount>1</VoucherCount><VoucherBody><Voucher>PFZvdWNoZXI+PEFkbURpdkNvZGU+MzIwNjgyPC9BZG1EaXZDb2RlPjxQYXlDb2RlPjMyMDY4MjIyMzAwMDAwMDAwMDAxPC9QYXlDb2RlPjxCaWxsRGF0ZT4yMDIyMDEwNTwvQmlsbERhdGU+PENoZ0FnZW5Db2RlPjAxMDQ5MDE8L0NoZ0FnZW5Db2RlPjxDaGdBZ2VuTmFtZT7I57jeytDX1Mi718rUtLrNuea7rr7WPC9DaGdBZ2VuTmFtZT48UGF5ZXJOYW1lPtCk1b08L1BheWVyTmFtZT48UGF5ZXJBY2N0PjwvUGF5ZXJBY2N0PjxQYXllck9wQms+PC9QYXllck9wQms+PEFtdD44MC4wPC9BbXQ+PE9yaUFtdD44MC4wPC9PcmlBbXQ+PERlbGF5QW10PjAuMDA8L0RlbGF5QW10PjxQYXlTdGF0cz4wPC9QYXlTdGF0cz48UmVjQWNjdFR5cGU+MTwvUmVjQWNjdFR5cGU+PFJlY05hbWU+yOe43srQssbV/r7WPC9SZWNOYW1lPjxSZWNBY2N0PjMyMDAxNjQ3MjM2MDUxMTE3NjYwPC9SZWNBY2N0PjxSZWNBY2N0UmVhbD4zMjAwMTY0NzIzNjA1MTExNzY2MDwvUmVjQWNjdFJlYWw+PFJlY09wQms+1tC5+r2oyejS+NDQyOe43tan0NA8L1JlY09wQms+PFJlY0JhbmtObz4wMTA1PC9SZWNCYW5rTm8+PElzSW50ZXJCYW5rPjA8L0lzSW50ZXJCYW5rPjxQYXlJbmZvPjEwMzA0MzIxMXyyu7avsvq1x7zHt9F8ODAuMHy0znwxfLK7z97WxnwwLjAtMC4wIzwvUGF5SW5mbz48TWVtbz48L01lbW8+PENyY0NvZGU+MTIzNDwvQ3JjQ29kZT48TW9iaWxlUGhvbmU+MTgwNTEzODczNjY8L01vYmlsZVBob25lPjxQYXllckNyZWRpdENvZGU+MzIwNjgyMTk5MzEyMjkxODgzPC9QYXllckNyZWRpdENvZGU+PFJlY0NyZWRpdENvZGU+PC9SZWNDcmVkaXRDb2RlPjxPcGVyYXRvckNvZGU+PC9PcGVyYXRvckNvZGU+PE9wZXJhdG9yTmFtZT53emw8L09wZXJhdG9yTmFtZT48SG9sZDE+WTwvSG9sZDE+PEhvbGQyPjwvSG9sZDI+PC9Wb3VjaGVyPg==</Voucher></VoucherBody></MOF></MSG></CFX>75a96b58404e0f452b5f933268bd2252b93c61d66bf1442d7de6498e9a52e3bc";
            //返回缴费二维码的
//            String result = "645     CNNONTAX1.05001<?xml version=\"1.0\" encoding=\"GBK\"?><CFX><HEAD><APP>CNNONTAX</APP><VER>1.0</VER><SRC>F320682</SRC><DES>K3206820002</DES><MsgNo>5001</MsgNo><MsgID>1478637083944812544</MsgID><MsgRef>20220105160928</MsgRef><WorkDate>20220105</WorkDate><Reserve/></HEAD><MSG><MOF><VoucherCount>1</VoucherCount><VoucherBody><Voucher>PFZvdWNoZXI+PE9yaU1zZ05vPjg5OTQ8L09yaU1zZ05vPjxSZXN1bHQ+MDAwMDA8L1Jlc3VsdD48SW5mb3JtYXRpb24+aHR0cDovL3BheS5qc2N6dC5jbi9mbnRvcC9xcmNvZGU/YmlsbElkPTMyMDY4MjIyMzAwMDAwMDAwMDA4PC9JbmZvcm1hdGlvbj48L1ZvdWNoZXI+</Voucher></VoucherBody></MOF></MSG></CFX>A85B32DBF2388AB72ABBE53DF09BCCFDD6B91DE2136709D4AB5148EDD8477589";
            LOGGER.info("非税增强查询返回结果：{}", result);
            //根据ywlx返回不同，分开处理
            if (StringUtils.isBlank(result)) {
                throw new AppException("非税增强查询无返回结果");
            }
            // 返回值业务编码
            String responseCode = result.substring(19, 23);
            result = result.substring(23, result.length() - 64);
            LOGGER.info("非税增强查询返回结果1：{}", result);

            // 截取返回值中<MSG></MSG> 部分转为json对象
            String msgResult = result.substring(result.indexOf("<MSG>"), result.indexOf("</CFX>"));
            LOGGER.info("返回值：{}", msgResult);
            String jsonObject = XmlEntityCommonConvertUtil.xmlToJson(msgResult);
            JSONObject jsonObject2 = JSON.parseObject(jsonObject);
            String jsonObject3 = jsonObject2.getJSONObject("MSG").getJSONObject("MOF").getJSONObject("VoucherBody").getString("Voucher");

            //开始解密
            String base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(jsonObject3), Charset.forName("GBK"));
            LOGGER.info("解密后fsczResponse：{}", base64Decode);
            String decodeString = XmlEntityCommonConvertUtil.xmlToJson(base64Decode);
            JSONObject decodeJson = JSON.parseObject(decodeString);

            if (StringUtils.equals(DZJKD_YWLX, ywlx)) {
                FsczResponse fsczResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), FsczResponse.class);

                if (null != fsczResponse) {
                    LOGGER.info("返回值fsczResponse：{}", fsczResponse.toString());

                    if (StringUtils.equals("00000", fsczResponse.getResult())) {
                        LOGGER.info("8994返回正确！");
                        commonResponse.setSuccess(true);
                        JSONObject jf = new JSONObject();
                        jf.put("jfsbm", jfsbm);
                        jf.put("jfsewmurl", fsczResponse.getInformation());
                        commonResponse.setData(jf);
                        LOGGER.info("8994返回正确！！，{}", commonResponse.toString());
                    } else {
                        LOGGER.info("8994返回错误！");
                        commonResponse.setSuccess(false);
                        FailInfo failInfo = new FailInfo();
                        failInfo.setCode(fsczResponse.getResult());
                        failInfo.setMessage(fsczResponse.getInformation());
                        commonResponse.setFail(failInfo);
                        LOGGER.info("8994返回错误！,{}", commonResponse.toString());

                    }
                }
            }
            //电子票据号码获取  返回值处理
            if (StringUtils.equals(DZPJHM_YWLX, ywlx)) {
                FsczResponse fsczResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), FsczResponse.class);

                if (null != fsczResponse) {
                    LOGGER.info("返回值fsczResponse：{}", fsczResponse.toString());
                    if (StringUtils.equals("00000", fsczResponse.getResult())) {
                        commonResponse.setSuccess(true);
                        JSONObject jf = new JSONObject();
                        jf.put("fph", fsczResponse.getInformation());
                        commonResponse.setData(jf);
                    } else {
                        commonResponse.setSuccess(false);
                        FailInfo failInfo = new FailInfo();
                        failInfo.setCode(fsczResponse.getResult());
                        failInfo.setMessage(fsczResponse.getInformation());
                        commonResponse.setFail(failInfo);
                    }
                }
            }
            //电子票据换开获取  返回值处理
            if (StringUtils.equals(DZPJKP_YWLX, ywlx)) {
                HkdzpjRersponse hkdzpjRersponse = JSONObject.parseObject(decodeJson.getString("Voucher"), HkdzpjRersponse.class);
                if (null != hkdzpjRersponse) {
                    LOGGER.info("返回值hkdzpjRersponse：{}", hkdzpjRersponse.toString());
                }
                if (null != hkdzpjRersponse) {
                    if (StringUtils.equals("00000", hkdzpjRersponse.getResult())) {
                        commonResponse.setSuccess(true);
                        JSONObject jf = new JSONObject();
                        jf.put("url", hkdzpjRersponse.getInvoice_Url());
                        commonResponse.setData(jf);
                    } else {
                        commonResponse.setSuccess(false);
                        FailInfo failInfo = new FailInfo();
                        failInfo.setCode(hkdzpjRersponse.getResult());
                        failInfo.setMessage(hkdzpjRersponse.getMsg());
                        commonResponse.setFail(failInfo);
                    }

                }
            }
            //电子票据下载  返回值处理
            if (StringUtils.equals(DZPJXZ_YWLX, ywlx)) {
                XzdzpjRersponse xzdzpjRersponse = JSONObject.parseObject(decodeJson.getString("Voucher"), XzdzpjRersponse.class);
                if (null != xzdzpjRersponse) {
                    LOGGER.info("返回值xzdzpjRersponse：{}", xzdzpjRersponse.toString());
                }
                if (null != xzdzpjRersponse) {
                    if (StringUtils.equals("00000", xzdzpjRersponse.getResult())) {
                        commonResponse.setSuccess(true);
                        JSONObject jf = new JSONObject();
                        jf.put("base64", xzdzpjRersponse.getInvoicefiledata());
                        commonResponse.setData(jf);
                    } else {
                        commonResponse.setSuccess(false);
                        FailInfo failInfo = new FailInfo();
                        failInfo.setCode(xzdzpjRersponse.getResult());
                        failInfo.setMessage(xzdzpjRersponse.getMsg());
                        commonResponse.setFail(failInfo);
                    }

                }
            }

            // 查询缴款状态，返回值处理
            if (StringUtils.equals(JFZTCX_YWLX, ywlx)) {
                if (responseCode.equals(YJXX_RESP_YWLX)) {
                    JfztCxResponse jfztCxResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), JfztCxResponse.class);
                    LOGGER.info("查询缴款状态返回值信息：{}", JSON.toJSONString(jfztCxResponse));
                    if (Objects.nonNull(jfztCxResponse)) {
                        BdcQuerySfztDTO bdcQuerySfztDTO = new BdcQuerySfztDTO();
                        dozerBeanMapper.map(jfztCxResponse, bdcQuerySfztDTO, "fs_jfztcx_resp");
                        commonResponse.setSuccess(true);
                        commonResponse.setData(bdcQuerySfztDTO);
                    } else {
                        commonResponse.setSuccess(false);
                        FailInfo failInfo = new FailInfo();
                        failInfo.setCode("99999");
                        failInfo.setMessage("未获取到查询缴款状态返回值内容");
                        commonResponse.setFail(failInfo);
                    }
                } else {
                    FsczResponse fsczResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), FsczResponse.class);
                    commonResponse.setSuccess(false);
                    FailInfo failInfo = new FailInfo();
                    failInfo.setCode(fsczResponse.getResult());
                    failInfo.setMessage(fsczResponse.getInformation());
                    commonResponse.setFail(failInfo);
                }
            }

            // 收入平台，单位票据信息同步
            if (StringUtils.equals(SRPT_PJXXTB_YWLX, ywlx)) {
                SrptPjxxResponse srptPjxxResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), SrptPjxxResponse.class);
                LOGGER.info("单位票据信息同步返回值：{}", srptPjxxResponse.toString());

                // 判断返回值是否正常
                boolean responseResult = Objects.nonNull(srptPjxxResponse) && (StringUtils.isBlank(srptPjxxResponse.getResult()) ||
                        (StringUtils.isNotBlank(srptPjxxResponse.getResult()) && srptPjxxResponse.getResult().equals("00000")));

                if (responseResult) {
                    LOGGER.info("4601，接口返回正确！");
                    commonResponse.setSuccess(true);
                    String jfsewmurl = this.convertEwmImageToUrl(srptPjxxResponse.getHold1());
                    LOGGER.info("jfsewmurl为：{}", jfsewmurl);
                    JSONObject jf = new JSONObject();
                    jf.put("jfsbm", srptPjxxResponse.getPayCode());
                    jf.put("jfsewmurl", jfsewmurl);
                    commonResponse.setData(jf);
                    LOGGER.info("4601，同步接口返回正确！，{}", commonResponse.toString());
                    //返回3601返回内容通用应答
                    try {
                        String reponseSocketString = this.generateCommonReponse();
                        sendSocketNoResp(reponseSocketString);
                    } catch (Exception e) {
                        LOGGER.error("通用应答接口异常：", e);
                    }
                } else {
                    commonResponse.setSuccess(false);
                    FailInfo failInfo = new FailInfo();
                    failInfo.setCode("");
                    failInfo.setMessage("调用收入平台单位票据信息同步接口出错：" + srptPjxxResponse.getInformation());
                    commonResponse.setFail(failInfo);
                }
            }

            // 收入平台，单位票据信息作废
            if (StringUtils.equals(SRPT_PJXXZF_YWLX, ywlx)) {
                SrptPjxxResponse srptPjxxResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), SrptPjxxResponse.class);
                LOGGER.info("单位票据信息作废返回值：{}", srptPjxxResponse.toString());
                // 判断返回值是否正常
                boolean responseResult = Objects.nonNull(srptPjxxResponse) && (StringUtils.isBlank(srptPjxxResponse.getResult()) ||
                        (StringUtils.isNotBlank(srptPjxxResponse.getResult()) && srptPjxxResponse.getResult().equals("00000")));
                if (responseResult) {
                    commonResponse.setSuccess(true);
                    LOGGER.info("4601，作废接口返回正确！，{}", commonResponse.toString());
                    //返回3601返回内容通用应答
                    try {
                        String reponseSocketString = this.generateCommonReponse();
                        sendSocketNoResp(reponseSocketString);
                    } catch (Exception e) {
                        LOGGER.error("通用应答接口异常：", e);
                    }
                } else {
                    commonResponse.setSuccess(false);
                    FailInfo failInfo = new FailInfo();
                    failInfo.setCode("");
                    failInfo.setMessage("调用收入平台单位票据信息同步接口出错：" + srptPjxxResponse.getInformation());
                    commonResponse.setFail(failInfo);
                }
            }

            // 收入平台，换开电子发票信息
            if (StringUtils.equals(SRPT_HKDZP_YWLX, ywlx)) {
                SrptPjxxResponse srptPjxxResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), SrptPjxxResponse.class);
                LOGGER.info("换开电子发票返回值：{}", srptPjxxResponse.toString());

                // 判断返回值是否正常
                boolean responseResult = Objects.nonNull(srptPjxxResponse) && (StringUtils.isBlank(srptPjxxResponse.getResult()) ||
                        (StringUtils.isNotBlank(srptPjxxResponse.getResult()) && srptPjxxResponse.getResult().equals("00000")));

                if (responseResult) {
                    commonResponse.setSuccess(true);
                    commonResponse.setData(srptPjxxResponse);
                    LOGGER.info("4691，接口返回正确！，{}", commonResponse.toString());
                } else {
                    commonResponse.setSuccess(false);
                    FailInfo failInfo = new FailInfo();
                    failInfo.setCode(srptPjxxResponse.getResult());
                    if (StringUtils.isNotBlank(srptPjxxResponse.getInformation())) {
                        failInfo.setMessage("调用收入平台单位换开电子发票信息出错，返回值信息为空");
                    } else {
                        failInfo.setMessage(srptPjxxResponse.getInformation());
                    }
                    commonResponse.setFail(failInfo);
                    LOGGER.info("4691接口返回错误！,{}", commonResponse.toString());
                }
            }

            //电子票据下载  返回值处理
            if (StringUtils.equals(SRPT_DZFPXZ_YWLX, ywlx)) {
                SrptPjxxResponse srptPjxxResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), SrptPjxxResponse.class);
                LOGGER.info("电子发票下载返回值：{}", srptPjxxResponse.toString());

                // 判断返回值是否正常
                boolean responseResult = Objects.nonNull(srptPjxxResponse) && (StringUtils.isBlank(srptPjxxResponse.getResult()) ||
                        (StringUtils.isNotBlank(srptPjxxResponse.getResult()) && srptPjxxResponse.getResult().equals("00000")));

                if (responseResult) {
                    commonResponse.setSuccess(true);
                    JSONObject jf = new JSONObject();
                    jf.put("base64", srptPjxxResponse.getInformation());
                    commonResponse.setData(jf);
                    LOGGER.info("4692，接口返回正确！，{}", commonResponse.toString());
                } else {
                    commonResponse.setSuccess(false);
                    FailInfo failInfo = new FailInfo();
                    failInfo.setCode("");
                    failInfo.setMessage("调用收入平台单位下载电子发票信息出错，" + srptPjxxResponse.getInformation());
                    commonResponse.setFail(failInfo);
                    LOGGER.info("4692接口返回错误！,{}", commonResponse.toString());
                }
            }
            LOGGER.info("最终返回参数commonResponse：{}", commonResponse.toString());
        } catch (SocketException e) {
            LOGGER.error("非税增强查询接口异常", e);
            throw new AppException("查询接口异常");
        } catch (UnknownHostException e) {
            LOGGER.error("非税增强接口连接失败", e);
            throw new AppException("非税增强接口连接失败");
        } catch (IOException e) {
            LOGGER.error("非税增强查询接口异常", e);
            throw new AppException("非税增强查询接口异常");
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
            if (requestOp != null) {
                try {
                    requestOp.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
            if (requestInp != null) {
                try {
                    requestInp.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
        }
    }

    /**
     * 发送无返回值的socket请求
     */
    private void sendSocketNoResp(String socketString) {
        Socket request = null;
        OutputStream requestOp = null;
        InputStream requestInp = null;
        try {
            request = new Socket(socketHost, socketPort);
            request.setSoTimeout(socketTimeOut); //设置读取超时时间10s
            requestOp = request.getOutputStream();
            requestInp = request.getInputStream();
            LOGGER.info("非税socket无返回值请求，请求参数：{}", socketString);
            SocketUtil.writeStr2Stream(socketString, requestOp);
            // 3.读取返回消息
            String result = SocketUtil.readStrFromStream(requestInp);
        } catch (SocketException e) {
            LOGGER.error("非税socket无返回值接口异常", e);
            throw new AppException("查询接口异常");
        } catch (UnknownHostException e) {
            LOGGER.error("非税socket无返回值接口连接失败", e);
            throw new AppException("非税socket无返回值接口连接失败");
        } catch (IOException e) {
            LOGGER.error("非税socket无返回值接口异常", e);
            throw new AppException("非税socket无返回值接口异常");
        } finally {
            if (request != null) {
                try {
                    request.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
            if (requestOp != null) {
                try {
                    requestOp.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
            if (requestInp != null) {
                try {
                    requestInp.close();
                } catch (IOException e) {
                    LOGGER.error("流关闭异常");
                }
            }
        }
    }

    /**
     * 根据区县代码获取非税配置项
     *
     * @param qxdm 区县代码
     */
    private void changeConfigByQxdm(String qxdm) {
        fsapp = (String) CommonUtil.getConfigValueByQxdm(CommonConstantUtils.FS_APP, fsapp, qxdm);
        fssrc = (String) CommonUtil.getConfigValueByQxdm(CommonConstantUtils.FS_SRC, fssrc, qxdm);
        fsdes = (String) CommonUtil.getConfigValueByQxdm(CommonConstantUtils.FS_DES, fsdes, qxdm);
        fsver = (String) CommonUtil.getConfigValueByQxdm(CommonConstantUtils.FS_VER, fsver, qxdm);
        fstoken = (String) CommonUtil.getConfigValueByQxdm(CommonConstantUtils.FS_TOKEN, fstoken, qxdm);
        fsxzqdm = (String) CommonUtil.getConfigValueByQxdm(CommonConstantUtils.FS_XZQDM, fsxzqdm, qxdm);
        fsDqsjkpz = (String) CommonUtil.getConfigValueByQxdm(FS_DQSJKPZ, fsDqsjkpz, qxdm);

        socketHost = (String) CommonUtil.getConfigValueByQxdm(FS_SOCKET_HOST, socketHost, qxdm);
        socketPort = (Integer) CommonUtil.getConfigValueByQxdm(FS_SOCKET_PORT, socketPort, qxdm);
        socketTimeOut = (Integer) CommonUtil.getConfigValueByQxdm(FS_SOCKET_TIMEOUT, socketTimeOut, qxdm);

        LOGGER.info("非税配置项：fsapp:{}, fssrc:{}, fsdes:{}, fsver:{}, fstoken:{}, fsxzqxdm:{}, fsDqsjkpz:{}", fsapp, fssrc, fsdes, fsver, fstoken, fsxzqdm, fsDqsjkpz);
        LOGGER.info("socket配置项：socketHost:{}, socketPort:{}, socketTimeOut:{}", socketHost, socketPort, socketTimeOut);
    }

    /**
     * 根据区县代码获取财政电子票据配置项
     *
     * @param qxdm 区县代码
     */
    private void changeDzpjConfigByQxdm(String qxdm) {
        eInvoiceCode = (String) CommonUtil.getConfigValueByQxdm(EINVOICE_CODE, "", qxdm);
        eInvoiceTag = (String) CommonUtil.getConfigValueByQxdm(EINVOICE_TAG, "", qxdm);
        eInvoiceName = (String) CommonUtil.getConfigValueByQxdm(EINVOICE_NAME, "", qxdm);
        partyCode = (String) CommonUtil.getConfigValueByQxdm(PARTY_CODE, "", qxdm);
        partyName = (String) CommonUtil.getConfigValueByQxdm(PARTY_NAME, "", qxdm);
        eInvoiceSpecimenCode = (String) CommonUtil.getConfigValueByQxdm(EINVOICE_SPECIMEN_CODE, "", qxdm);
        invoicingPartySealId = (String) CommonUtil.getConfigValueByQxdm(INVOICING_PARTY_SEALID, "", qxdm);
        supervisorAreaCode = (String) CommonUtil.getConfigValueByQxdm(SUPERVISOR_AREA_CODE, "", qxdm);
        supervisorPartySealId = (String) CommonUtil.getConfigValueByQxdm(SUPERVISOR_PARTY_SEALID, "", qxdm);
        Map<String, String> itemCodeTemp = fsConfig.getItemcode().get(qxdm);
        if (itemCodeTemp != null) {
            itemCodeMap = itemCodeTemp;
        } else {
            itemCodeMap = fsConfig.getItemcode().get("default");
        }
        LOGGER.info("区县代码为：{}，财政电子票据配置项：eInvoiceCode:{},eInvoiceTag:{},eInvoiceName:{},partyCode:{},partyName:{},eInvoiceSpecimenCode:{},invoicingPartySealId:{},supervisorAreaCode:{},supervisorPartySealId:{},itemCodeMap:{}",
                qxdm, eInvoiceCode, eInvoiceTag, eInvoiceName, partyCode, partyName, eInvoiceSpecimenCode, invoicingPartySealId, supervisorAreaCode, supervisorPartySealId, itemCodeMap);
    }

    /**
     * 请求报文根据当前用户获取相关非税配置
     * 开票员账号kpyzh， 票据版本代码billvercode, 票据版本名称billvername 通过BDC_FS_PZ表，根据收件人读取
     * @param json 请求报文
     * @param userName 用户名称
     * @param jklx 接口类型
     * @param msg  请求信息
     */
    private void getFspzByUser(JSONObject json, String userName, String jklx, String msg) {
        UserDto userDto = null;
        if(StringUtils.isNotBlank(userName)){
            userDto = userManagerUtils.getUserByName(userName);
        }
        if(null == userDto){
            userDto = userManagerUtils.getCurrentUser();
        }
        if(null != userDto){
            BdcFsPzDO bdcFsPzDO = fsPzFeignService.queryFspz("", userDto.getId());
            if (null != bdcFsPzDO) {
                LOGGER.info("{}，当前用户{}的非税配置为：{}", msg, userDto.getAlias(), bdcFsPzDO.toString());
                json.put("BillVerCode", bdcFsPzDO.getBillvercode());
                json.put("BillVerName", bdcFsPzDO.getBillvername());
                if ("hq".equals(jklx) || "kp".equals(jklx)) {
                    json.put("Hold1", bdcFsPzDO.getKpyzh());
                }
            }
        } else {
            LOGGER.error("{},获取当前用户失败", msg);
        }
    }


    /**
     * 收入平台统一接口推送登记费信息
     *
     * @param bdcSfxxDTO 不动产收费信息DTO
     * @return CommonResponse
     */
    public CommonResponse srptTsdjfxx(BdcSfxxDTO bdcSfxxDTO) {
        LOGGER.info("推送登记收费信息入参:{}", bdcSfxxDTO);
        if (Objects.isNull(bdcSfxxDTO)) {
            throw new MissingArgumentException("收费信息实体参数不能为空！");
        }
        CommonResponse commonResponse = new CommonResponse();
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(bdcSfxxDTO, requestJson, "srpt_tsjf_sfxx");
        // 组织是否换开电子发票信息
        requestJson.put("IsAutoMake", fsSfhkdzfp);

        // 根据区县代码，获取对应区县的fs配置内容
        String qxdm = "";
        if (bdcSfxxDTO.getDsfSfxxDTO() != null) {
            qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
        }
        changeConfigByQxdm(qxdm);

        String jfsbm = this.payCodeLsh(bdcSfxxDTO.getDsfSfxxDTO().getQxdm(), CommonConstantUtils.YWLX_JFSBM);
        String socketString = this.generateXml(bdcSfxxDTO, requestJson, jfsbm);
        if (StringUtils.isBlank(socketString)) {
            throw new MissingArgumentException("生成请求的xml数据为空。");
        }
        sendSocket(SRPT_PJXXTB_YWLX, socketString, commonResponse, jfsbm);
        return commonResponse;
    }

    /**
     * 生成收入平台数据同步socket请求参数
     */
    private String generateXml(BdcSfxxDTO bdcSfxxDTO, JSONObject requestJson, String jfsbm) {
        // 组织PayInfo信息
        if (CollectionUtils.isNotEmpty(bdcSfxxDTO.getBdcSlSfxmVOList())) {
            Map<String, BdcSlSfxmVO> sfxmMap = new HashMap<>();
            //ssje大于0 才相加收费项目，其他为0的不推送
            for (BdcSlSfxmVO slSfxmDO : bdcSfxxDTO.getBdcSlSfxmVOList()) {
                BdcSlSfxmVO bdcSlSfxmDO = new BdcSlSfxmVO();

                if (null != slSfxmDO.getSsje() && slSfxmDO.getSsje() > 0) {
                    if (sfxmMap.containsKey(slSfxmDO.getSfxmdm())) {
                        BdcSlSfxmVO bdcSlSfxmDO1 = sfxmMap.get(slSfxmDO.getSfxmdm());
                        bdcSlSfxmDO1.setSsje(NumberUtil.formatDigit(bdcSlSfxmDO1.getSsje() + slSfxmDO.getSsje(), 2));
                        bdcSlSfxmDO1.setSl(NumberUtil.formatDigit(bdcSlSfxmDO1.getSl() + slSfxmDO.getSl(), 1));
                    } else {
                        bdcSlSfxmDO.setSl(NumberUtil.formatDigit(slSfxmDO.getSl(), 1));
                        bdcSlSfxmDO.setSfxmmc(slSfxmDO.getSfxmmc());
                        bdcSlSfxmDO.setJedw(slSfxmDO.getJedw());
                        bdcSlSfxmDO.setSsje(NumberUtil.formatDigit(slSfxmDO.getSsje(), 2));
                        sfxmMap.put(slSfxmDO.getSfxmdm(), bdcSlSfxmDO);
                    }
                }
            }
            // 全国统一项目识别码+项目名称+金额+计量单位+数量+标准类型+收缴标准，信息以分隔符进行分割，如：全国统一项目识别码1|项目名称1|2.0|次|2|无限制|0.0-0.0#全国统一项目识别码2|项目名称2|3.0|个|3|金额上下限|1.0-10.0#,也可为财政定义的其他格式
            String payInfo = "";
            for (String key : sfxmMap.keySet()) {
                BdcSlSfxmVO slSfxmDO2 = sfxmMap.get(key);
                String sl = slSfxmDO2.getSl().toString();
                String slString = sl.substring(0, sl.indexOf('.'));
                payInfo += key + "|" + slSfxmDO2.getSfxmmc() + "|" + slSfxmDO2.getSsje() + "|" + "次" + "|" + slString + "|" + "不限制" + "|" + "0.0-0.0#";
            }
            requestJson.put("PayInfo", payInfo);
        }

        // 组织缴款码信息
        if (null == bdcSfxxDTO.getDsfSfxxDTO()) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失第三方收费信息参数");
        }
        requestJson.put("PayCode", jfsbm);

        try {
            // 组织报文体Voucher信息
            DwpjxxtbVoucher dwpjxxtbVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), DwpjxxtbVoucher.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dwpjxxtbVoucher);

            // 组织报文体 MOF 内容
            DwpjxxtbMOFModel mofModel = new DwpjxxtbMOFModel();
            mofModel.setVoucherCount(1);

            List<DwpjxxtbVoucher> dwpjxxtbVouchers = new ArrayList<>();
            dwpjxxtbVouchers.add(dwpjxxtbVoucher);
            mofModel.setVoucherBody(dwpjxxtbVouchers);

            // 组织请求头信息
            MSGModel msgModel = new MSGModel();
            msgModel.setMof(mofModel);

            String socketString = this.generateSocketString(SRPT_PJXXTB_YWLX, msgModel, null);
            return socketString;
        } catch (Exception e) {
            LOGGER.info("非税转xml出错！", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 收入平台统一接口作废登记费信息
     *
     * @param bdcSfxxDTO 不动产收费信息DTO
     * @return CommonResponse
     */
    public CommonResponse srptZfdjfxx(BdcSfxxDTO bdcSfxxDTO) {
        LOGGER.info("作废登记收费信息入参:{}", bdcSfxxDTO);
        if (Objects.isNull(bdcSfxxDTO)) {
            throw new MissingArgumentException("收费信息实体参数不能为空！");
        }
        CommonResponse commonResponse = new CommonResponse();
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(bdcSfxxDTO, requestJson, "srpt_zfjf_sfxx");

        // 根据区县代码，获取对应区县的fs配置内容
        String qxdm = "";
        if (bdcSfxxDTO.getDsfSfxxDTO() != null) {
            qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
        }
        changeConfigByQxdm(qxdm);

        String socketString = this.generateXml(bdcSfxxDTO, requestJson, bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm());
        sendSocket(SRPT_PJXXZF_YWLX, socketString, commonResponse, bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm());
        return commonResponse;
    }

    /**
     * 生成非税接口需要结构的Xml参数
     *
     * @param ywlx       业务类型编码
     * @param msgModel   消息体内容
     * @param contentTag 消息体xml标签名
     * @return String xml数据
     */
    private String generateSocketString(String ywlx, MSGModel msgModel, String contentTag) {
        String socketString = "";
        try {
            // 组织请求内容体
            String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
            LOGGER.info("组织请求体<MOF>节点为：{}", msgXml);

            // 组织请求头信息
            FsczHead head = getFsczHead(ywlx);
            String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
            LOGGER.info("组织请求头<head>节点为：{}", headxml);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(head);

            FsczRequest fsczRequest = new FsczRequest();
            fsczRequest.setHead(head);
            fsczRequest.setMsg(msgModel);
            String fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
            LOGGER.info("组织<CFX>节点为：{}", fsczRequestXml);

            // 对<Voucher></Voucher>中间的内容进行加密处理
            String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
            if (StringUtils.isBlank(contentTag)) {
                contentTag = "<AdmDivCode>";
            }
            String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf(contentTag), fsczRequestXml.indexOf("</Voucher>"));
            LOGGER.info("需要加密的字符串为：{}", sub);
            String base64Encode = "";
            String base64Decode = "";
            try {
                base64Encode = Base64Util.str2Baes64StrByGBK(sub);
                base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(base64Encode), Charset.defaultCharset());
                LOGGER.info("需要解密的字符串为：{}", base64Encode);
                LOGGER.info("需要解密的字符串为：{}", base64Decode);
            } catch (Exception e) {
                LOGGER.info("加密报错：{}", sub);
                LOGGER.info("解密报错：{}", sub);
                e.printStackTrace();
            }
            //加密后，替换字符串
            String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
            LOGGER.info("替换后：{}", replaceXml);
            socketString = organizationRequest(ywlx, replaceXml);
        } catch (JAXBException e) {
            LOGGER.info("转换xml出错，{}", e);
            e.printStackTrace();
        }
        return socketString;
    }

    /**
     * 收入平台统一接口电子发票下载
     *
     * @param
     * @return CommonResponse
     */
    public Object dzfpxz(BdcSfxxDTO bdcSfxxDTO) {
        LOGGER.info("电子发票下载入参:{}", JSON.toJSONString(bdcSfxxDTO));
        if (Objects.isNull(bdcSfxxDTO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "收费信息实体参数不能为空！");
        }
        String qxdm = "";
        if (bdcSfxxDTO.getDsfSfxxDTO() != null) {
            qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
        }
        // 根据区县代码获取配置项
        changeConfigByQxdm(qxdm);

        if (StringUtils.isBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm())) {
            throw new AppException(ErrorCode.CUSTOM, "缴款书编码为空");
        }

        // 请求信息字段对照
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(bdcSfxxDTO, requestJson, "srpt_dzfpxz");

        CommonResponse<String> commonResponse = new CommonResponse();
        try {
            MSGModel msgModel = new MSGModel();
            DzfpXzVoucher dzfpXzVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), DzfpXzVoucher.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(dzfpXzVoucher);

            String xml = XmlUtils.getXmlStrByObject(dzfpXzVoucher, DzfpXzVoucher.class);
            LOGGER.info("电子发票下载组织DzfpXzVoucher——XML信息为：{}", xml);

            // 组织报文体内容
            DzfpXzMOFModel dzfpXzMOFModel = new DzfpXzMOFModel();
            dzfpXzMOFModel.setVoucherCount(1);

            List<DzfpXzVoucher> dzfpXzVoucherList = new ArrayList<>(1);
            dzfpXzVoucherList.add(dzfpXzVoucher);
            dzfpXzMOFModel.setVoucherBody(dzfpXzVoucherList);

            String mofXml = XmlUtils.getXmlStrByObject(dzfpXzMOFModel, DzfpXzMOFModel.class);
            LOGGER.info("电子发票下载组织报文体内容DzfpXzMOFModel为：{}", mofXml);
            msgModel.setMof(dzfpXzMOFModel);

            // 生成xml请求参数
            String socketString = this.generateSocketString(SRPT_DZFPXZ_YWLX, msgModel, null);
            sendSocket(SRPT_DZFPXZ_YWLX, socketString, commonResponse, "");
        } catch (JAXBException e) {
            LOGGER.info("非税转xml出错！", e);
            e.printStackTrace();
        }

        LOGGER.info("电子发票下载开票返回信息：{}：", commonResponse.toString());
        if (Objects.isNull(commonResponse) || !commonResponse.isSuccess()) {
            LOGGER.error("电子发票下载失败，失败信息：{}", commonResponse.getFail());
            throw new AppException("电子发票下载失败, 失败码：{}" + commonResponse.getFail().toString());
        }
        return commonResponse;
    }

    /**
     * 收入平台统一接口换开电子发票
     *
     * @param
     * @return CommonResponse
     */
    public Object hkdzfp(BdcSfxxDTO bdcSfxxDTO) {
        CommonResponse<String> result = new CommonResponse();
        LOGGER.info("换开电子发票入参:{}", JSON.toJSONString(bdcSfxxDTO));
        if (Objects.isNull(bdcSfxxDTO)) {
            throw new AppException(ErrorCode.MISSING_ARG, "收费信息实体参数不能为空！");
        }
        String qxdm = "";
        if (bdcSfxxDTO.getDsfSfxxDTO() != null) {
            qxdm = bdcSfxxDTO.getDsfSfxxDTO().getQxdm();
        }
        // 根据区县代码获取配置项
        changeConfigByQxdm(qxdm);

        // 是否开票，如果已开票，进行提示
        Integer sfkp = bdcSfxxDTO.getBdcSlSfxxDO().getSfkp() == null ? 0 : bdcSfxxDTO.getBdcSlSfxxDO().getSfkp();
        if (CommonConstantUtils.SF_S_DM.equals(sfkp)) {
            throw new AppException(ErrorCode.CUSTOM, "已开票，不在重复开票");
        }
        if (StringUtils.isBlank(bdcSfxxDTO.getBdcSlSfxxDO().getJfsbm())) {
            throw new AppException(ErrorCode.CUSTOM, "缴款书编码为空");
        }

        // 请求信息字段对照
        JSONObject requestJson = new JSONObject();
        dozerBeanMapper.map(bdcSfxxDTO, requestJson, "srpt_hkdzfp");

        CommonResponse<SrptPjxxResponse> commonResponse = new CommonResponse();
        try {

            HkDzfpVoucher hkDzfpVoucher = JSONObject.parseObject(JSONObject.toJSONString(requestJson), HkDzfpVoucher.class);
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(hkDzfpVoucher);

            String xml = XmlUtils.getXmlStrByObject(hkDzfpVoucher, HkDzfpVoucher.class);
            LOGGER.info("换开电子发票组织HkDzfpVoucher——XML信息为：{}", xml);

            List<HkDzfpVoucher> hkDzfpVoucherList = new ArrayList<>();
            hkDzfpVoucherList.add(hkDzfpVoucher);

            // 组织报文体内容
            HkDzfpMOFModel hkDzfpMOFModel = new HkDzfpMOFModel();
            hkDzfpMOFModel.setVoucherCount(1);
            hkDzfpMOFModel.setVoucherBody(hkDzfpVoucherList);

            MSGModel msgModel = new MSGModel();
            msgModel.setMof(hkDzfpMOFModel);

            String socketString = this.generateSocketString(SRPT_HKDZP_YWLX, msgModel, null);
            sendSocket(SRPT_HKDZP_YWLX, socketString, commonResponse, "");
        } catch (JAXBException e) {
            LOGGER.info("非税转xml出错！", e);
            e.printStackTrace();
        }

        LOGGER.info("换开电子发票开票返回信息：{}：", commonResponse.toString());
        if (Objects.isNull(commonResponse) || !commonResponse.isSuccess()) {
            LOGGER.error("换开电子发票失败，失败信息：{}", JSON.toJSONString(commonResponse.getFail()));
            throw new AppException("换开电子发票失败, 失败码：{}" + commonResponse.getFail().getCode());
        }

        SrptPjxxResponse srptPjxxResponse = commonResponse.getData();
        LOGGER.info("获取收入平台换开电子发票 commonResponse 返回data:{}！", JSON.toJSONString(srptPjxxResponse));

        if (Objects.isNull(srptPjxxResponse)) {
            result.setSuccess(false);
            result.setFail(commonResponse.getFail());
        } else {
            result.setSuccess(true);
            result.setData(srptPjxxResponse.getInformation());
            // 更新收费信息是否开票，更新发票信息表发票链接
            this.modifySfxxAndFpxx(bdcSfxxDTO.getBdcSlSfxxDO().getSfxxid(), bdcSfxxDTO.getBdcSlSfxxDO().getXmid(), srptPjxxResponse.getInformation());
        }
        return result;
    }

    /**
     * 1、更新收费信息是否开票
     * 2、保存发票信息
     */
    private void modifySfxxAndFpxx(String sfxxid, String xmid, String fpUrl) {
        BdcSlSfxxDO sfxx = new BdcSlSfxxDO();
        sfxx.setSfxxid(sfxxid);
        sfxx.setSfkp(CommonConstantUtils.SF_S_DM);
        String userame = "";
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            userame = userDto.getAlias();
        }
        sfxx.setKprxm(userame);
        bdcSlSfxxFeignService.updateBdcSlSfxx(sfxx);

        // 保存发票信息内容
        List<BdcSlFpxxDO> bdcSlFpxxDOList = this.bdcSlFpxxFeignService.queryBdcSlFpxxBySfxxid(sfxxid);
        if (CollectionUtils.isNotEmpty(bdcSlFpxxDOList)) {
            bdcSlFpxxDOList = bdcSlFpxxDOList.stream().filter(t -> !t.getFpzt().equals(CommonConstantUtils.FPZT_CH)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(bdcSlFpxxDOList)) {
                for (BdcSlFpxxDO bdcSlFpxxDO : bdcSlFpxxDOList) {
                    bdcSlFpxxDO.setKprq(new Date());
                    bdcSlFpxxDO.setFpurl(fpUrl);
                    bdcSlFpxxDO.setFpzt(1);
                    this.bdcSlFpxxFeignService.saveBdcSlFpxx(bdcSlFpxxDO);
                }
            } else {
                BdcSlFpxxDO bdcSlFpxxDO = new BdcSlFpxxDO();
                bdcSlFpxxDO.setSfxxid(sfxxid);
                bdcSlFpxxDO.setXmid(xmid);
                bdcSlFpxxDO.setKprq(new Date());
                bdcSlFpxxDO.setFpurl(fpUrl);
                bdcSlFpxxDO.setFpzt(1);
                this.bdcSlFpxxFeignService.saveBdcSlFpxx(bdcSlFpxxDO);
            }
        }
    }

    /**
     * 将二维码base64字符串转换为url链接
     */
    public String convertEwmImageToUrl(String imageBase64) {
        if (StringUtils.isNotBlank(imageBase64)) {
            ByteArrayInputStream bis = null;
            try {
                String[] baseStrs = imageBase64.split(",");
                byte[] bytes = null;
                //有前缀名只取后面的base码
                if (baseStrs.length > 1) {
                    bytes = Base64Utils.decodeBase64StrToByte(baseStrs[1]);
                } else {
                    bytes = Base64Utils.decodeBase64StrToByte(imageBase64);
                }
                bis = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(bis);
                QRCodeDecoder decoder = new QRCodeDecoder();
                String url = new String(decoder.decode(new MyQRCodeImage(image)), "gb2312");
                return url;
            } catch (IOException e) {
                LOGGER.error("图像转换异常！", e);
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        LOGGER.error("关闭流异常", e);
                    }
                }
            }
        }
        return "";
    }

    /**
     * 3601返回消息后，生成通用的消息接口后应答请求
     */
    public String generateCommonReponse() {
        String socketString = "";
        try {
            MSGModel msgModel = new MSGModel();
            ResponseVoucher responseVoucher = new ResponseVoucher();
            responseVoucher.setOriMsgNo("3601");
            responseVoucher.setResult("00000");
            ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(responseVoucher);

            String xml = XmlUtils.getXmlStrByObject(responseVoucher, ResponseVoucher.class);
            LOGGER.info("通用应答Voucher——XML信息为：{}", xml);

            // 组织报文体内容
            ResponseMOFModel responseMOFModel = new ResponseMOFModel();
            responseMOFModel.setVoucherCount(1);

            List<ResponseVoucher> responseVoucherList = new ArrayList<>(1);
            responseVoucherList.add(responseVoucher);
            responseMOFModel.setVoucherBody(responseVoucherList);

            String mofXml = XmlUtils.getXmlStrByObject(responseMOFModel, ResponseMOFModel.class);
            LOGGER.info("通用应答ResponseMOFModel为：{}", mofXml);
            msgModel.setMof(responseMOFModel);

            // 生成xml请求参数
            socketString = this.generateSocketString(SRPT_TYYD_YWLX, msgModel, "<OriMsgNo>");
        } catch (JAXBException e) {
            LOGGER.info("非税转xml出错！", e);
            e.printStackTrace();
        }
        return socketString;
    }

    // 获取第三方对照信息
    public String getDsfZdDzxx(String zdbs, String dsfzdbs, String bdczdz) {
        //数据归属地区进行对照
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfzdbs);
        BdcZdDsfzdgxDO result = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.info("---第三方字典返回值:{},查询参数:{}", result, bdcZdDsfzdgxDO);
        if (null != result && StringUtils.isNotBlank(result.getDsfzdz())) {
            return result.getDsfzdz();
        }
        return "";
    }

}
