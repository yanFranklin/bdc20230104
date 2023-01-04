package cn.gtmap.realestate.exchange.config.xsd;

import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.service.national.VailXmlErrorHandlerService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-14
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExchangeApp.class)
@WebAppConfiguration
public class XsdCheck {

    @Autowired
    private VailXmlErrorHandlerService vailXmlErrorHandlerService;


    @Test
    public void test(){
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Message>\n" +
                "    <Head>\n" +
                "        <ASID>AS100</ASID>\n" +
                "        <AreaCode>340121</AreaCode>\n" +
                "        <BizMsgID>340121190614014862</BizMsgID>\n" +
                "<DigitalSign>37b84ad2c3ced1cc751a7e3dc0b8110e8d817f264a0d97e4dcd3d56ca0874901f03ba45fc6b1ef5c25e4db71647c5b0f257d98043dd6aac8154320689fa830933f6d030d5cbc318693756d14f04e2d72bf6b9a0055fb36f84d6cce75af86df8a7d8b13bad4779d4a7b8559f13fff267cbb3fd8ac0994d3d0159f9bd7a8bf3353</DigitalSign>\n" +
                "        <CertCount>0</CertCount>\n" +
                "        <CreateDate>2019-06-14T10:30:18</CreateDate>\n" +
                "        <EstateNum>340121261006GB00014F00220045</EstateNum>\n" +
                "        <ParcelID>340121261006GB00014</ParcelID>\n" +
                "        <PreCertID></PreCertID>\n" +
                "        <PreEstateNum></PreEstateNum>\n" +
                "        <ProofCount>0</ProofCount>\n" +
                "        <RecFlowID>20190612-0027835_127</RecFlowID>\n" +
                "        <RecType>7000101</RecType>\n" +
                "        <RegOrgID>340121</RegOrgID>\n" +
                "        <RegType>700</RegType>\n" +
                "        <RightType>99</RightType>\n" +
                "    </Head>\n" +
                "    <Data>\n" +
                "        <ZTT_GY_QLR BDCDYH=\"340121261006GB00014F00220045\" BDCQZH=\"皖(2019)长丰县不动产证明第0010484号\" DH=\"13966373126\" GJ=\"142\" GYFS=\"0\" HJSZSS=\"330000\" QLRLX=\"2\" QLRMC=\"中国工商银行股份有限公司合肥四牌楼支行\" QXDM=\"340121\" SFCZR=\"1\" SXH=\"1\" XB=\"3\" YSDM=\"6003000000\" ZJH=\"340100000117617(1-1)\" ZJZL=\"7\"/>\n" +
                "        <ZTT_GY_QLR BDCDYH=\"340121261006GB00014F00220045\" BDCQZH=\"皖(2019)长丰县不动产证明第0010483号\" DH=\"17705655107\" GJ=\"142\" GYFS=\"0\" HJSZSS=\"330000\" QLRLX=\"1\" QLRMC=\"刘念\" QXDM=\"340121\" SFCZR=\"1\" SXH=\"1\" XB=\"3\" YSDM=\"6003000000\" ZJH=\"220125197706133223\" ZJZL=\"1\"/>\n" +
                "        <QLF_QL_YGDJ BDCDJZMH=\"皖(2019)长丰县不动产证明第0010484号\" BDCDYH=\"340121261006GB00014F00220045\" BDCZL=\"长丰县双墩镇凤湖西路与百善路交口新慧翠湖天地B26幢1401室\" DBR=\"胡明东\" DJJG=\"不动产登记中心\" DJLX=\"700\" DJSJ=\"2019-06-14T10:30:18+08:00\" DJYY=\"未知\" FWJG=\"3\" FWXZ=\"0\" GHYT=\"11\" JZMJ=\"101.17\" QDJG=\"61\" QSZT=\"1\" QXDM=\"340121\" SCYWH=\"0\" SZC=\"14\" TDSYQR=\"刘念\" YGDJZL=\"4\" YSDM=\"6002040100\" YWH=\"20190612-0027835_127\" YWR=\"刘念\" YWRZJH=\"220125197706133223\" YWRZJZL=\"1\" ZCS=\"18\"/>\n" +
                "        <QLF_QL_YGDJ BDCDJZMH=\"皖(2019)长丰县不动产证明第0010483号\" BDCDYH=\"340121261006GB00014F00220045\" BDCZL=\"长丰县双墩镇凤湖西路与百善路交口新慧翠湖天地B26幢1401室\" DBR=\"胡明东\" DJJG=\"不动产登记中心\" DJLX=\"700\" DJSJ=\"2019-06-14T10:30:18+08:00\" DJYY=\"未知\" FWJG=\"3\" FWXZ=\"0\" GHYT=\"11\" JZMJ=\"101.17\" QDJG=\"0\" QSZT=\"1\" QXDM=\"340121\" SCYWH=\"0\" SZC=\"14\" TDSYQR=\"刘念\" YGDJZL=\"2\" YSDM=\"6002040100\" YWH=\"20190612-0027835_127\" YWR=\"合肥新慧房地产开发有限公司\" YWRZJH=\"91340100781081199K（1-1）\" YWRZJZL=\"7\" ZCS=\"18\"/>\n" +
                "        <DJT_DJ_SLSQ AJZT=\"2\" DJDL=\"700\" DJXL=\"7000101\" JSSJ=\"2019-06-14T10:30:18+08:00\" QXDM=\"340121\" SFWTAJ=\"0\" SLRY=\"董军1\" SLSJ=\"2019-06-12T15:40:39+08:00\" SQFBCZ=\"0\" SQZSBS=\"0\" TZFS=\"1\" YSDM=\"6004010000\" YWH=\"20190612-0027835_127\" ZL=\"长丰县双墩镇凤湖西路与百善路交口新慧翠湖天地B26幢1401室\"/>\n" +
                "        <DJT_DJ_SLSQ AJZT=\"2\" DJDL=\"700\" DJXL=\"7000101\" JSSJ=\"2019-06-14T10:30:18+08:00\" QXDM=\"340121\" SFWTAJ=\"0\" SLRY=\"董军1\" SLSJ=\"2019-06-12T15:40:39+08:00\" SQFBCZ=\"0\" SQZSBS=\"0\" TZFS=\"1\" YSDM=\"6004010000\" YWH=\"20190612-0027835_127\" ZL=\"长丰县双墩镇凤湖西路与百善路交口新慧翠湖天地B26幢1401室\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"复审\" QXDM=\"340121\" SHJSSJ=\"2019-06-14T00:00:00+08:00\" SHKSSJ=\"2019-06-13T00:00:00+08:00\" SHRYXM=\"胡明东\" SHYJ=\"  拟同意初审意见，上报审批。\" SXH=\"2\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"核定\" QXDM=\"340121\" SHJSSJ=\"2019-06-14T00:00:00+08:00\" SHKSSJ=\"2019-06-14T00:00:00+08:00\" SHRYXM=\"胡明东\" SHYJ=\"  准予预告抵押权登记。\" SXH=\"3\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"初审\" QXDM=\"340121\" SHJSSJ=\"2019-06-13T00:00:00+08:00\" SHKSSJ=\"2019-06-12T00:00:00+08:00\" SHRYXM=\"董军\" SHYJ=\"    材料齐全，经调查，四至清晰，权属明晰，无争议，拟登记。\" SXH=\"1\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"核定\" QXDM=\"340121\" SHJSSJ=\"2019-06-14T00:00:00+08:00\" SHKSSJ=\"2019-06-14T00:00:00+08:00\" SHRYXM=\"胡明东\" SHYJ=\"   准予预告登记。\" SXH=\"3\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"复审\" QXDM=\"340121\" SHJSSJ=\"2019-06-14T00:00:00+08:00\" SHKSSJ=\"2019-06-13T00:00:00+08:00\" SHRYXM=\"胡明东\" SHYJ=\"  拟同意初审意见，上报审批。\" SXH=\"2\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"核定\" QXDM=\"340121\" SHJSSJ=\"2019-06-14T00:00:00+08:00\" SHKSSJ=\"2019-06-14T00:00:00+08:00\" SHRYXM=\"胡明东\" SHYJ=\"  准予预告抵押权登记。\" SXH=\"3\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"初审\" QXDM=\"340121\" SHJSSJ=\"2019-06-13T00:00:00+08:00\" SHKSSJ=\"2019-06-12T00:00:00+08:00\" SHRYXM=\"董军\" SHYJ=\"    材料齐全，经调查，四至清晰，权属明晰，无争议，拟登记。\" SXH=\"1\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SH CZJG=\"1\" JDMC=\"核定\" QXDM=\"340121\" SHJSSJ=\"2019-06-14T00:00:00+08:00\" SHKSSJ=\"2019-06-14T00:00:00+08:00\" SHRYXM=\"胡明东\" SHYJ=\"   准予预告登记。\" SXH=\"3\" YSDM=\"6004050000\" YWH=\"20190612-0027835_127\"/>\n" +
                "        <DJF_DJ_SQR QLRMC=\"中国工商银行股份有限公司合肥四牌楼支行\" QLRZJH=\"340100000117617(1-1)\" QLRZJZL=\"7\" QXDM=\"340121\" YSDM=\"6004020000\" YWH=\"20190612-0027835_127\" YWRMC=\"刘念\" YWRZJH=\"220125197706133223\" YWRZJZL=\"1\"/>\n" +
                "        <DJF_DJ_SQR QLRMC=\"刘念\" QLRZJH=\"220125197706133223\" QLRZJZL=\"1\" QXDM=\"340121\" YSDM=\"6004020000\" YWH=\"20190612-0027835_127\" YWRMC=\"合肥新慧房地产开发有限公司\" YWRZJH=\"91340100781081199K（1-1）\" YWRZJZL=\"7\"/>\n" +
                "        <DJF_DJ_SQR QLRMC=\"中国工商银行股份有限公司合肥四牌楼支行\" QLRZJH=\"340100000117617(1-1)\" QLRZJZL=\"7\" QXDM=\"340121\" YSDM=\"6004020000\" YWH=\"20190612-0027835_127\" YWRMC=\"刘念\" YWRZJH=\"220125197706133223\" YWRZJZL=\"1\"/>\n" +
                "        <DJF_DJ_SQR QLRMC=\"刘念\" QLRZJH=\"220125197706133223\" QLRZJZL=\"1\" QXDM=\"340121\" YSDM=\"6004020000\" YWH=\"20190612-0027835_127\" YWRMC=\"合肥新慧房地产开发有限公司\" YWRZJH=\"91340100781081199K（1-1）\" YWRZJZL=\"7\"/>\n" +
                "    </Data>\n" +
                "</Message>";
        StringBuffer xsdFilePath = new StringBuffer("E:\\功能\\各种文档\\学习文件资料\\学习文件资料\\schema汇交验证\\schema\\7000101.xsd");
        String valResult = vailXmlErrorHandlerService.vailXmlErrorHandlerService(xml, xsdFilePath.toString());
        System.out.println(StringUtils.equals("验证通过", valResult) || StringUtils.isBlank(valResult));
    }
}
