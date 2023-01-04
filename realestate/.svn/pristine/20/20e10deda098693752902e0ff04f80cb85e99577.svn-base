package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.XmlEntityCommonConvertUtil;
import cn.gtmap.realestate.exchange.util.XmlUtils;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangjian
 * @Description
 * @create
 */

public class ServerConfig extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);

    @Autowired
    BdcSlSfxxFeignService slSfxxFeignService1;

    private Socket socket;

    public ServerConfig(Socket socket) {
        this.socket = socket;
    }

    // 获取spring容器管理的类，可以获取到sevrice的类
    private BdcSlSfxxFeignService slSfxxFeignService = SpringUtil.getBean(BdcSlSfxxFeignService.class);

    static final String fstoken = EnvironmentConfig.getEnvironment().getProperty("fs.token");
    static final String fssrc = EnvironmentConfig.getEnvironment().getProperty("fs.src");
    static final String fsdes = EnvironmentConfig.getEnvironment().getProperty("fs.des");
    static final String fsapp = EnvironmentConfig.getEnvironment().getProperty("fs.app");
    static final String fsver = EnvironmentConfig.getEnvironment().getProperty("fs.ver");


    private String handle(InputStream inputStream) throws IOException {

        byte[] bytes = new byte[4096];
        int len = inputStream.read(bytes);
        if (len != -1) {
            StringBuffer request = new StringBuffer();
            request.append(new String(bytes, 0, len, "GBK"));
            LOGGER.info("Socket接受的数据{}", request);
            LOGGER.info("from client ... " + request + "当前线程" + Thread.currentThread().getName());
            //截取消息头
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
            String mac = request.substring(request.length() - 64);
            String xml = request.substring(23, request.length() - 64);
            String jsonObject = XmlEntityCommonConvertUtil.xmlToJson(xml);
            LOGGER.info("非税推送交易确认报文返回值：{}", jsonObject);
            FsczResponseModel fsczResponseModel = new FsczResponseModel();
            FsczRequest fsczRequest = new FsczRequest();
            MSGModel msgModel = new MSGModel();
            String fsczResponseXml = "";
            String socketString = "";
            List<FsczResponse> fsczResponseList = new ArrayList<>();
            if (StringUtils.equals("5996", xxt.substring(19))) {
                JSONObject jsonObject1 = JSON.parseObject(jsonObject);
                JSONObject headJson = jsonObject1.getJSONObject("CFX").getJSONObject("HEAD");
                FsczHead head = JSONObject.parseObject(JSON.toJSONString(headJson), FsczHead.class);

                JSONObject jsonObject2 = jsonObject1.getJSONObject("CFX").getJSONObject("MSG");
                String jsonObject3 = jsonObject2.getJSONObject("MOF").getJSONObject("VoucherBody").getString("Voucher");
                //开始解密
                String base64Decode = new String(org.apache.commons.codec.binary.Base64.decodeBase64(jsonObject3), Charset.forName("GBK"));
                LOGGER.info("非税推送交易确认报文解密后czjyQrbwResponse：{}", base64Decode);
                String decodeString = XmlEntityCommonConvertUtil.xmlToJson(base64Decode);
                JSONObject decodeJson = JSON.parseObject(decodeString);
                CzjyQrbwResponse czjyQrbwResponse = JSONObject.parseObject(decodeJson.getString("Voucher"), CzjyQrbwResponse.class);
                LOGGER.info("返回值czjyQrbwResponse：{}", czjyQrbwResponse.toString());
                String jfsbm = czjyQrbwResponse.getPayCode();
                String date = czjyQrbwResponse.getCfmDate().substring(0, 4) + "-" + czjyQrbwResponse.getCfmDate().substring(4, 6) + "-" + czjyQrbwResponse.getCfmDate().substring(6, 8)
                        + " " + czjyQrbwResponse.getCfmTime().substring(0, 2) + ":" + czjyQrbwResponse.getCfmTime().substring(2, 4) + ":" + czjyQrbwResponse.getCfmTime().substring(4, 6);
                Date jfsj = DateUtils.formatDate(date, DateUtils.sdf_ymdhms);

                //查询收费信息表，更新状态和时间
                if (StringUtils.isNotBlank(jfsbm)) {
                    BdcSlSfxxDO slSfxxDO = new BdcSlSfxxDO();
                    slSfxxDO.setJfsbm(jfsbm);
                    List<BdcSlSfxxDO> bdcSlSfxxDOList = slSfxxFeignService.queryBdcSlSfxx(slSfxxDO);

                    if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                        LOGGER.info("查出收费信息有几条：{}", bdcSlSfxxDOList.size());

                        Boolean success = true;
                        for (BdcSlSfxxDO slSfxxDO1 : bdcSlSfxxDOList) {
                            slSfxxDO1.setSfzt(CommonConstantUtils.SFZT_YJF);
                            slSfxxDO1.setSfztczsj(jfsj);
                            LOGGER.info("流程实例id{}更新缴费时间为{}", slSfxxDO1.getGzlslid(), date);
                            slSfxxDO1.setJkrq(jfsj);
                            int count = slSfxxFeignService.updateBdcSlSfxxAndTsdjlc(slSfxxDO1);
                            if (count > 0) {
                                LOGGER.info("更新收费状态成功！收费xxid为：{}", slSfxxDO1.getSfxxid());
                            } else {
                                success = false;
                                LOGGER.info("更新收费状态失败！收费xxid为：{}", slSfxxDO1.getSfxxid());
                            }

                        }
                        FsczResponse fsczResponse = new FsczResponse();

                        //成功返回00000
                        if (success) {
                            fsczResponse.setResult("00000");
                            fsczResponse.setOriMsgNo(head.getMsgNo());
                            fsczResponseList.add(fsczResponse);
                        } else {
                            fsczResponse.setResult("99999");
                            fsczResponse.setOriMsgNo(head.getMsgNo());
                            fsczResponseList.add(fsczResponse);
                        }
                    } else {
                        LOGGER.info("此缴款码查不到收费信息！缴款码：{}", jfsbm);
                    }
                } else {
                    LOGGER.info("无缴款码！返回信息为：{}", request);
                }

                fsczResponseModel.setVoucherCount(1);
                fsczResponseModel.setVoucherBody(fsczResponseList);
                msgModel.setMof(fsczResponseModel);
                fsczRequest.setHead(head);
                fsczRequest.setMsg(msgModel);
                try {
                    fsczResponseXml = XmlUtils.getXmlStrByObjectGBK(fsczRequest, FsczRequest.class);
                    socketString = organizationRequest("8001", fsczResponseXml);
                    LOGGER.info("返回非税！返回信息为：{}", socketString);

                } catch (JAXBException e) {
                    LOGGER.info("返回转xml出错！");
                    e.printStackTrace();
                }
                return socketString;


            }

        } else {
            throw new AppException("数据处理异常");
        }
        return null;
    }

    @Override
    public void run() {
        BufferedWriter writer = null;
        try {
            // 设置连接超时9秒
            socket.setSoTimeout(10000);
            LOGGER.info("客户 - " + socket.getRemoteSocketAddress() + " -> 机连接成功");
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
                LOGGER.info("发生异常");
                try {
                    LOGGER.info("再次接受!");
                    result = handle(inputStream);
                    writer.write(result);
                    writer.newLine();
                    writer.flush();
                } catch (Exception ex) {
                    LOGGER.info("再次接受, 发生异常,连接关闭");
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
        LOGGER.info("更新状态后组装后整个报文为：{}", socketData);
        return socketData;
    }
}
