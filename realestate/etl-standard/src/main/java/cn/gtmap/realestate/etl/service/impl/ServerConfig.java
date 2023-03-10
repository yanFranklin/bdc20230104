package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.ResponseMOFModel;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.ResponseVoucher;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr.SrptPjxxResponse;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFpxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.etl.util.Base64Util;
import cn.gtmap.realestate.etl.util.XmlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.bind.JAXBException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author huangjian
 * @Description
 * @create
 */

public class ServerConfig extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);

    private final BdcSlSfxxFeignService slSfxxFeignService;
    private final BdcSlFpxxFeignService bdcSlFpxxFeignService;
    private final BdcXmFeignService bdcXmFeignService;
    private final BdcUploadFileUtils bdcUploadFileUtils;
    private final ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    private EntityMapper entityMapper;
    private Socket socket;

    public ServerConfig(Socket socket, EntityMapper entityMapper, BdcSlSfxxFeignService slSfxxFeignService,
                        BdcSlFpxxFeignService bdcSlFpxxFeignService, BdcXmFeignService bdcXmFeignService,
                        BdcUploadFileUtils bdcUploadFileUtils,ExchangeInterfaceFeignService exchangeInterfaceFeignService) {
        this.socket = socket;
        this.entityMapper = entityMapper;
        this.slSfxxFeignService = slSfxxFeignService;
        this.bdcSlFpxxFeignService = bdcSlFpxxFeignService;
        this.bdcXmFeignService = bdcXmFeignService;
        this.bdcUploadFileUtils = bdcUploadFileUtils;
        this.exchangeInterfaceFeignService = exchangeInterfaceFeignService;
    }

    static String fstoken = EnvironmentConfig.getEnvironment().getProperty("fs.token");
    static String fssrc = EnvironmentConfig.getEnvironment().getProperty("fs.src");
    static String fsdes = EnvironmentConfig.getEnvironment().getProperty("fs.des");
    static String fsapp = EnvironmentConfig.getEnvironment().getProperty("fs.app");
    static String fsver = EnvironmentConfig.getEnvironment().getProperty("fs.ver");
    static String sfzdhk = EnvironmentConfig.getEnvironment().getProperty("fs.zdhk", String.class, "false");
    static String dzfpmlmc = EnvironmentConfig.getEnvironment().getProperty("fs.dzfp.mlmc", String.class, "????????????");
    static String dzfpwjmc = EnvironmentConfig.getEnvironment().getProperty("fs.dzfp.wjmc", String.class, "");

    private String handle(InputStream inputStream) throws IOException {

        byte[] bytes = new byte[4096];
        int len = inputStream.read(bytes);
        if (len != -1) {
            StringBuffer request = new StringBuffer();
            request.append(new String(bytes, 0, len, "GBK"));
            LOGGER.info("Socket???????????????{}", request);
            LOGGER.info("from client ... " + request + "????????????" + Thread.currentThread().getName());
            //???????????????
           /* String request1 = "1546    CNNONTAX1.05996<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "\t<CFX>\n" +
                    "\t\t<HEAD>\n" +
                    "\t\t\t<VER>1.0</VER>\n" +
                    "\t\t\t<SRC>K3206000001</SRC>\n" +
                    "\t\t\t<DES>F320600</DES>\n" +
                    "\t\t\t<APP>CNNONTAX</APP>\n" +
                    "\t\t\t<MsgNo>8994</MsgNo>\n" +
                    "\t\t\t<MsgID>f9244c94b39cf78a38f4</MsgID>\n" +
                    "\t\t\t<MsgRef>f9244c94b39cf78a38f4</MsgRef>\n" +
                    "\t\t\t<WorkDate>20211129</WorkDate>\n" +
                    "\t\t\t<Reserve>String</Reserve>\n" +
                    "\t\t</HEAD>\n" +
                    "\t\t<MSG>\n" +
                    "\t\t\t<MOF>\n" +
                    "\t\t\t\t<VoucherCount>1</VoucherCount>\n" +
                    "\t\t\t\t<VoucherBody AdmDivCode=\"100000\" StYear=\"2021\" VtCode=\"8994\" VoucherNo=\"f9244c94b39cf78a38f4\">\n" +
                    "\t\t\t\t\t<Voucher>\n" +
                    "\t\t\t\t\t\t<CfmDate>20211211</CfmDate>\n" +
                    "\t\t\t\t\t\t<PayCode>105000086510366202003200012954</PayCode>\n" +
                    "\t\t\t\t\t\t<CfmTime>121212</CfmTime>\n" +
                    "\t\t\t\t\t\t<Amt>22.09</Amt>\n" +
                    "\t\t\t\t\t\t<BankOutlet>12</BankOutlet>\n" +
                    "\t\t\t\t\t\t<Operator>12</Operator>\n" +
                    "\t\t\t\t\t\t<PayMode>12</PayMode>\n" +
                    "\t\t\t\t\t\t<TraNoBus>12</TraNoBus>\n" +
                    "\t\t\t\t\t\t<PayerName>12</PayerName>\n" +
                    "\t\t\t\t\t\t<PayerAcct>12</PayerAcct>\n" +
                    "\t\t\t\t\t\t<PayerOpBk>12</PayerOpBk>\n" +
                    "\t\t\t\t\t\t<PayerOpBkNo>12</PayerOpBkNo>\n" +
                    "\t\t\t\t\t\t<RecAcctType>12</RecAcctType>\n" +
                    "\t\t\t\t\t\t<RecAcct>12</RecAcct>\n" +
                    "\t\t\t\t\t</Voucher>\n" +
                    "\t\t\t\t</VoucherBody>\n" +
                    "\t\t\t</MOF>\n" +
                    "\t\t</MSG>\n" +
                    "\t</CFX>35fbb7735880e07746167124735b51943859237a955eb3e3448c2e15c3f8ab11";
            */
//            String request ="986     CNNONTAX1.05996<?xml version=\"1.0\" encoding=\"GBK\"?><CFX><HEAD><APP>CNNONTAX</APP><VER>1.0</VER><SRC>F320682</SRC><DES>K3206820002</DES><MsgNo>5996</MsgNo><MsgID>1478622255595388928</MsgID><MsgRef>1478622255595388928</MsgRef><WorkDate>20220105</WorkDate><Reserve/></HEAD><MSG><MOF><VoucherCount>1</VoucherCount><VoucherBody><Voucher>PFZvdWNoZXI+PFBheUNvZGU+MzIwNjgyMjIzMDAwMDAwMDAwMDc8L1BheUNvZGU+PENmbURhdGU+MjAyMjAxMDU8L0NmbURhdGU+PENmbVRpbWU+MTUwMzA1PC9DZm1UaW1lPjxBbXQ+MC4wMTwvQW10PjxCYW5rT3V0bGV0PjAwMDAwPC9CYW5rT3V0bGV0PjxPcGVyYXRvcj5HMDAwMDAxPC9PcGVyYXRvcj48UGF5TW9kZT41MTwvUGF5TW9kZT48VHJhTm9CdXM+MjAyMjAxMDUyMjAwMTQ4NTA2MTQ1Nzk1NzMxODwvVHJhTm9CdXM+PFBheWVyTmFtZT7V1MD207E8L1BheWVyTmFtZT48UGF5ZXJBY2N0Lz48UGF5ZXJPcEJrLz48UGF5ZXJPcEJrTm8vPjxSZWNBY2N0VHlwZT4xPC9SZWNBY2N0VHlwZT48UmVjQWNjdD4zMjAwMTY0NzIzNjA1MTExNzY2MDwvUmVjQWNjdD48SG9sZDEvPjxIb2xkMi8+PC9Wb3VjaGVyPg==</Voucher></VoucherBody></MOF></MSG></CFX>52472EEF2C238A53CFBC7D77E56EA327A750F54AF500E4B0A6E806DD445056E2";
            String xxt = request.substring(0, 23);
            String xml = request.substring(23, request.length() - 64);
            String jsonObject = XmlEntityCommonConvertUtil.xmlToJson(xml);
            LOGGER.info("??????????????????????????????????????????{}", jsonObject);
            if (StringUtils.equals("5996", xxt.substring(19))) {
                JSONObject jsonObject1 = JSON.parseObject(jsonObject);
                JSONObject headJson = jsonObject1.getJSONObject("CFX").getJSONObject("HEAD");
                FsczHead head = JSONObject.parseObject(JSON.toJSONString(headJson), FsczHead.class);

                JSONObject jsonObject2 = jsonObject1.getJSONObject("CFX").getJSONObject("MSG");
                String jsonObject3 = jsonObject2.getJSONObject("MOF").getJSONObject("VoucherBody").getString("Voucher");
                //????????????
                String base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(jsonObject3), Charset.forName("GBK"));
                LOGGER.info("???????????????????????????????????????czjyQrbwResponse???{}", base64Decode);
                String decodeString = XmlEntityCommonConvertUtil.xmlToJson(base64Decode);
                JSONObject decodeJson = JSON.parseObject(decodeString);
                CzjyQrbwResponse czjyQrbwResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), CzjyQrbwResponse.class);
                LOGGER.info("?????????czjyQrbwResponse???{}", czjyQrbwResponse.toString());
                String jfsbm = czjyQrbwResponse.getPayCode();
                String date = czjyQrbwResponse.getCfmDate().substring(0, 4) + "-" + czjyQrbwResponse.getCfmDate().substring(4, 6) + "-" + czjyQrbwResponse.getCfmDate().substring(6, 8)
                        + " " + czjyQrbwResponse.getCfmTime().substring(0, 2) + ":" + czjyQrbwResponse.getCfmTime().substring(2, 4) + ":" + czjyQrbwResponse.getCfmTime().substring(4, 6);
                Date jfsj = DateUtils.formatDate(date, DateUtils.sdf_ymdhms);

                Boolean success = true;
                //?????????????????????????????????????????????
                if (StringUtils.isNotBlank(jfsbm)) {
                    BdcSlSfxxDO slSfxxDO = new BdcSlSfxxDO();
                    slSfxxDO.setJfsbm(jfsbm);
                    List<BdcSlSfxxDO> bdcSlSfxxDOList = slSfxxFeignService.queryBdcSlSfxx(slSfxxDO);
                    String qxdm = "";
                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                        LOGGER.info("??????????????????????????????{}", bdcSlSfxxDOList.size());
                        if(StringUtils.isNotBlank(bdcSlSfxxDOList.get(0).getXmid())){
                            BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(bdcSlSfxxDOList.get(0).getXmid());
                            qxdm = bdcXmDO.getQxdm();
                        }

                        changeConfigByQxdm(qxdm);
                        for (BdcSlSfxxDO slSfxxDO1 : bdcSlSfxxDOList) {
                            slSfxxDO1.setSfzt(CommonConstantUtils.SFZT_YJF);
                            slSfxxDO1.setSfztczsj(jfsj);
                            slSfxxDO1.setSfsj(jfsj);
                            slSfxxDO1.setSfxsjf(CommonConstantUtils.SF_S_DM);
                            LOGGER.info("????????????id{}?????????????????????{}", slSfxxDO1.getGzlslid(), date);
                            slSfxxDO1.setJkrq(jfsj);
                            int count = slSfxxFeignService.updateBdcSlSfxx(slSfxxDO1);
                            if (count > 0) {
                                LOGGER.info("?????????????????????????????????xxid??????{}", slSfxxDO1.getSfxxid());
                                // ???????????????????????????
                                this.tzHlwSfzt(slSfxxDO1.getXmid(), CommonConstantUtils.SFZT_YJF);
                                if(sfzdhk.equals("true")){
                                    this.zdHkDzfpAndXzdzpj(slSfxxDO1);
                                }
                            } else {
                                success = false;
                                LOGGER.info("?????????????????????????????????xxid??????{}", slSfxxDO1.getSfxxid());
                            }
                        }
                    } else {
                        LOGGER.info("????????????????????????????????????????????????{}", jfsbm);
                    }
                } else {
                    LOGGER.info("?????????????????????????????????{}", request);
                }
                return this.generateCommonReponse(head.getMsgNo(),"8001", success);
            }

            if(StringUtils.equals("3601", xxt.substring(19))){
                JSONObject jsonObject1 = JSON.parseObject(jsonObject);
                JSONObject headJson = jsonObject1.getJSONObject("CFX").getJSONObject("HEAD");
                FsczHead head = JSONObject.parseObject(JSON.toJSONString(headJson), FsczHead.class);

                JSONObject msgJson = jsonObject1.getJSONObject("CFX").getJSONObject("MSG");
                String voucherJson = msgJson.getJSONObject("MOF").getJSONObject("VoucherBody").getString("Voucher");
                LOGGER.info("???????????????????????????????????????Voucher????????????{}", voucherJson);
                //????????????
                String base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(voucherJson), Charset.forName("GBK"));
                LOGGER.info("???????????????????????????????????????Voucher????????????????????????{}", base64Decode);
                String decodeString = XmlEntityCommonConvertUtil.xmlToJson(base64Decode);
                JSONObject decodeJson = JSON.parseObject(decodeString);
                SrptPjxxResponse srptPjxxResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), SrptPjxxResponse.class);
                LOGGER.info("?????????srptPjxxResponse???{}", JSON.toJSONString(srptPjxxResponse));

                String sfxxid = srptPjxxResponse.getPayNo();
                LOGGER.info("?????????????????????????????????sfxxid??????{}", sfxxid);
                Boolean success = true;
                if (StringUtils.isNotBlank(sfxxid)) {
                    BdcSlSfxxDO bdcSlSfxxDO = slSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
                    String qxdm = "";
                    if (Objects.nonNull(bdcSlSfxxDO)) {
                        if(StringUtils.isNotBlank(bdcSlSfxxDO.getXmid())){
                            BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(bdcSlSfxxDO.getXmid());
                            qxdm = bdcXmDO.getQxdm();
                        }

                        // ????????????????????????????????????
                        changeConfigByQxdm(qxdm);

                        Integer sfzt = null;
                        if (srptPjxxResponse.getPayStats().equals("0")) {
                            sfzt = BdcSfztEnum.WJF.key();
                        }
                        if (srptPjxxResponse.getPayStats().equals("1")) {
                            sfzt = BdcSfztEnum.YJF.key();
                        }
                        if (srptPjxxResponse.getPayStats().equals("2")) {
                            sfzt = BdcSfztEnum.YZF.key();
                        }
                        bdcSlSfxxDO.setSfzt(sfzt);
                        // ??????????????????
                        String date = srptPjxxResponse.getConfirmDate() + " " + srptPjxxResponse.getConfirmTime();
                        Date jfsj = DateUtils.formatDate(date, "yyyyMMdd HHmmss");
                        bdcSlSfxxDO.setSfztczsj(jfsj);
                        bdcSlSfxxDO.setJkrq(jfsj);
                        if (StringUtils.isNotBlank(srptPjxxResponse.getHold2())) {
                            bdcSlSfxxDO.setSfkp(CommonConstantUtils.SF_S_DM);
                        }
                        int count = slSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
                        if (count > 0) {
                            this.tzHlwSfzt(bdcSlSfxxDO.getXmid(), sfzt);
                            LOGGER.info("?????????????????????????????????xxid??????{}", bdcSlSfxxDO.getSfxxid());
                        } else {
                            success = false;
                            LOGGER.info("?????????????????????????????????xxid??????{}", bdcSlSfxxDO.getSfxxid());
                        }
                        // ????????????????????????
                        this.modifySfxxAndFpxx(bdcSlSfxxDO.getSfxxid(), bdcSlSfxxDO.getXmid(), srptPjxxResponse);
                    } else {
                        LOGGER.info("???sfxxid???????????????????????????????????????ID???{}", sfxxid);
                    }
                }
                return this.generateCommonReponse(head.getMsgNo(),"4001", success);
            }
        }
        return null;
    }

    /**
     * 3601????????????????????????????????????????????????????????????
     */
    public String generateCommonReponse(String ybwbh, String headYwlx, boolean result) {
        String socketString = "";
        try {
            MSGModel msgModel = new MSGModel();
            ResponseVoucher responseVoucher = new ResponseVoucher();
            responseVoucher.setOriMsgNo(ybwbh);
            if(result){
                responseVoucher.setResult("00000");
            }else{
                responseVoucher.setResult("99999");
            }

            String xml = XmlUtils.getXmlStrByObject(responseVoucher, ResponseVoucher.class);
            LOGGER.info("????????????Voucher??????XML????????????{}", xml);

            // ?????????????????????
            ResponseMOFModel responseMOFModel = new ResponseMOFModel();
            responseMOFModel.setVoucherCount(1);

            List<ResponseVoucher> responseVoucherList = new ArrayList<>(1);
            responseVoucherList.add(responseVoucher);
            responseMOFModel.setVoucherBody(responseVoucherList);

            String mofXml = XmlUtils.getXmlStrByObject(responseMOFModel, ResponseMOFModel.class);
            LOGGER.info("????????????ResponseMOFModel??????{}", mofXml);
            msgModel.setMof(responseMOFModel);

            // ??????xml????????????
            socketString = this.generateSocketString(headYwlx, msgModel, "<OriMsgNo>");
        } catch (JAXBException e) {
            LOGGER.info("?????????xml?????????", e);
            e.printStackTrace();
        }
        return socketString;
    }

    /**
     * ?????????????????????????????????Xml??????
     *
     * @param ywlx       ??????????????????
     * @param msgModel   ???????????????
     * @param contentTag ?????????xml?????????
     * @return String xml??????
     */
    private String generateSocketString(String ywlx, MSGModel msgModel, String contentTag) {
        String socketString = "";
        try {
            // ?????????????????????
            String msgXml = XmlUtils.getXmlStrByObject(msgModel, MSGModel.class);
            LOGGER.info("???????????????<MOF>????????????{}", msgXml);

            // ?????????????????????
            FsczHead head = getFsczHead(ywlx);
            String headxml = XmlUtils.getXmlStrByObject(head, FsczHead.class);
            LOGGER.info("???????????????<head>????????????{}", headxml);
            FsczRequest fsczRequest = new FsczRequest();
            fsczRequest.setHead(head);
            fsczRequest.setMsg(msgModel);
            String fsczRequestXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
            LOGGER.info("??????<CFX>????????????{}", fsczRequestXml);

            // ???<Voucher></Voucher>?????????????????????????????????
            String sub = fsczRequestXml.substring(fsczRequestXml.indexOf("<Voucher>"), fsczRequestXml.indexOf("</VoucherBody>"));
            if (StringUtils.isBlank(contentTag)) {
                contentTag = "<AdmDivCode>";
            }
            String subReplace = fsczRequestXml.substring(fsczRequestXml.indexOf(contentTag), fsczRequestXml.indexOf("</Voucher>"));
            LOGGER.info("??????????????????????????????{}", sub);
            String base64Encode = "";
            try {
                base64Encode = Base64Util.str2Baes64StrByGBK(sub);
            } catch (Exception e) {
                LOGGER.info("???????????????{}", sub);
                e.printStackTrace();
            }
            //???????????????????????????
            String replaceXml = fsczRequestXml.replace(subReplace, base64Encode);
            LOGGER.info("????????????{}", replaceXml);
            socketString = organizationRequest(ywlx, replaceXml);
        } catch (JAXBException e) {
            LOGGER.info("??????xml?????????{}", e);
            e.printStackTrace();
        }
        return socketString;
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

    /**
     * ????????????????????????
     */
    private void modifySfxxAndFpxx(String sfxxid, String xmid, SrptPjxxResponse srptPjxxResponse){
        // ????????????????????????
        if(StringUtils.isNotBlank(srptPjxxResponse.getHold2())){
            List<BdcSlFpxxDO> bdcSlFpxxDOList = this.bdcSlFpxxFeignService.queryBdcSlFpxxBySfxxid(sfxxid);
            BdcSlFpxxDO bdcSlFpxxDO = new BdcSlFpxxDO();
            bdcSlFpxxDO.setSfxxid(sfxxid);
            bdcSlFpxxDO.setXmid(xmid);
            bdcSlFpxxDO.setKprq(new Date());
            bdcSlFpxxDO.setFpurl(srptPjxxResponse.getHold2());
            bdcSlFpxxDO.setFpzt(1);
            if(CollectionUtils.isNotEmpty(bdcSlFpxxDOList)){
                bdcSlFpxxDO.setFpxxid(bdcSlFpxxDOList.get(0).getFpxxid());
            }
            this.bdcSlFpxxFeignService.saveBdcSlFpxx(bdcSlFpxxDO);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    private void zdHkDzfpAndXzdzpj(BdcSlSfxxDO sfxx){
        if(Objects.nonNull(sfxx) && StringUtils.isNotBlank(sfxx.getSfxxid())){
            try{
                Map<String, String> response= (Map<String, String>) this.slSfxxFeignService.getDzfpxx(sfxx.getSfxxid());
                if(MapUtils.isNotEmpty(response) && response.containsKey("base64")){
                    String fpmc = StringUtils.isNotBlank(dzfpwjmc) ? dzfpwjmc: sfxx.getSfxxid();
                    String base64str = CommonConstantUtils.BASE64_QZ_PDF + response.get("base64");
                    this.bdcUploadFileUtils.uploadBase64File(base64str, sfxx.getGzlslid(), dzfpmlmc, fpmc, ".pdf");
                }
            }catch(Exception e){
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * ???????????????????????????
     */
    private void tzHlwSfzt(String xmid, int jfzt){
        BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(xmid);
        if(null != bdcXmDO){
            try{
                Map<String, String> param = new HashMap<>(3);
                param.put("wwslbh", bdcXmDO.getSpxtywh());
                param.put("czsj", DateUtils.formateDateToString(new Date(), DateUtils.DATE_FORMAT));
                param.put("jfzt", String.valueOf(jfzt));
                this.exchangeInterfaceFeignService.requestInterface("wwsq_tsJfzt", param);
            }catch(Exception e){
                e.printStackTrace();
                LOGGER.error("??????????????????????????????????????????????????????" + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        BufferedWriter writer = null;
        try {
            // ??????????????????9???
            socket.setSoTimeout(10000);
            LOGGER.info("?????? - " + socket.getRemoteSocketAddress() + " -> ???????????????");
            InputStream inputStream = socket.getInputStream();
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String result = null;
            try {
                result = handle(inputStream);
                writer.write(result);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                writer.write("error");
                writer.newLine();
                writer.flush();
                LOGGER.info("????????????");
                try {
                    LOGGER.info("????????????!");
                    result = handle(inputStream);
                    writer.write(result);
                    writer.newLine();
                    writer.flush();
                } catch (Exception ex) {
                    LOGGER.info("????????????, ????????????,????????????");
                }
            }
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            try {
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String organizationRequest(String ywlx, String dataXml) {
        String socketData = "";
        String mac = DigestUtils.sha256Hex(fstoken + dataXml);
        String data = fsapp + fsver + ywlx + dataXml + mac;
        String length = String.format("%-8d", 8 + data.length());
        socketData = length + data;
        LOGGER.info("???????????????????????????{}", socketData);
        return socketData;
    }

    private void changeConfigByQxdm(String qxdm) {
        fsapp = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(CommonConstantUtils.FS_APP, fsapp, qxdm);
        fssrc = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(CommonConstantUtils.FS_SRC, fssrc, qxdm);
        fsdes = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(CommonConstantUtils.FS_DES, fsdes, qxdm);
        fsver = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(CommonConstantUtils.FS_VER, fsver, qxdm);
        fstoken = (String) Container.getBean(BdcConfigUtils.class).getConfigValueByQxdm(CommonConstantUtils.FS_TOKEN, fstoken, qxdm);
    }
}
