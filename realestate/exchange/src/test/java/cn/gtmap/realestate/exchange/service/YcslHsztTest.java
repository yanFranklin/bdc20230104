package cn.gtmap.realestate.exchange.service;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.ExchangeApp;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.Sm4Util;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = ExchangeApp.class)
//@WebAppConfiguration
public class YcslHsztTest {

    @Test
    public void test() {
        String beanName = "ycsl_hsztbyxmid";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("xmid","f16952d5236b402880af695197d30113");
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap,getServiceList());
        requestBuilder.invoke();
        // 比对请求
        Object requestBody = requestBuilder.getRequestBody();

    }

    @Test
    public void test2() {
        String beanName = "ycsl_tjqq";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("xmid","f16952d5236b402880af695197d30113");
        paramMap.put("bdcdyh","bdcdyh");
        paramMap.put("czbz", "1");
        paramMap.put("yj", "yj");
        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,paramMap);
        requestBuilder.invoke();
        // 比对请求
        Object requestBody = requestBuilder.getRequestBody();

    }

    private List<BuildRequestService> getServiceList(){
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        return serviceList;
    }

    @Test
    public void base64encode() {
        String s = null;
        try {
            String s1 = "<?xml version=\"1.0\" encoding=\"GB2312\"?> \n" +
                    "<ROOT>\n" +
                    "<CODE>-1</CODE>" +
                    "<MSG>调用成功！</MSG>" +
                    "<TIMESTAMP>20200701121200</TIMESTAMP>" +
                    "<DATA>" +
                    "<BUSINESS_ID>100</BUSINESS_ID>" +
                    "<BUSINESSNUMBER>32040020000000002527</BUSINESSNUMBER>" +
                    "<URL>https://jscz.govpay.ccb.com/online/fsjf?PyF_BillNo=32040020000000002527&Verf_CD=blank&Admn_Rgon_Cd=320400</URL>" +
                    "</DATA>" +
                    "</ROOT>";
            String gbk1 = Base64.encodeBase64String(s1.getBytes());
//            System.out.println(gbk1);
//            String gbk2 = new String(Base64.decodeBase64(gbk1), "gbk");
//            String s2 = new String(Base64.decodeBase64(gbk1),StandardCharsets.UTF_8);
            System.out.println(gbk1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void base64encode1() {
        String s = null;
        try {
            s = "<?xml version=\"1.0\" encoding=\"GB2312\"?>" +
                    "<ROOT>" +
                    "<CODE>-1</CODE>" +
                    "<MSG>调用成功！</MSG>" +
                    "<TIMESTAMP>20200701121200</TIMESTAMP>" +
                    "<DATA>" +
                    "<BUSINESS_ID>100</BUSINESS_ID>" +
                    "<BUSINESSNUMBER>32040020000000002527</BUSINESSNUMBER>" +
                    "<PAY_DATE></PAY_DATE>" +
                    "<IS_SUCCESS>1</IS_SUCCESS>" +
                    "<STATUS_MC></STATUS_MC>" +
                    "</DATA>" +
                    "</ROOT>";
            String s1 = Base64.encodeBase64String(s.getBytes());
            System.out.println(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void base64encode2() {
        String s = null;
        try {
            s = "<?xml version=\"1.0\" encoding=\"GB2312\"?>" +
                    "<ROOT>" +
                    "<CODE>-1</CODE>" +
                    "<MSG>调用成功！</MSG>" +
                    "<TIMESTAMP>20200701121200</TIMESTAMP>" +
                    "<DATA/>" +
                    "</ROOT>";
            String s1 = Base64.encodeBase64String(s.getBytes());
            System.out.println(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void base64encode23() {
        String s = null;
        try {
            s = "<?xml version=\"1.0\" encoding=\"GB2312\"?> " +
                    "<ROOT>" +
                    "<CODE>-1</CODE>" +
                    "<MSG>调用成功！</MSG>" +
                    "<TIMESTAMP>20200701121200</TIMESTAMP>" +
                    "<DATA>" +
                    "<BUSINESS_ID>100</BUSINESS_ID>" +
                    "<BUSINESSNUMBER>32040020000000002527</BUSINESSNUMBER>" +
                    "<URL>http://financz.cn/fs-gateway/base/common/downloadFileFromFile?businessnumber=32040020000000002527</URL>" +
                    "</DATA>" +
                    "</ROOT>";
            String s1 = Base64.encodeBase64String(s.getBytes());
            System.out.println(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void decode() {
        String s = null;
        try {
            String s1 = "<?xml version=\"1.0\" encoding=\"GB2312\"?> \n" +
                    "<ROOT>\n" +
                    "\t<CODE>-1</CODE><!--返回业务码-->\n" +
                    "\t<MSG>调用成功！</MSG><!--返回码描述-->\n" +
                    "\t<TIMESTAMP>20200701121200</TIMESTAMP><!--响应请求的时间，格式YYYYMMDDHHMMSS-->\n" +
                    "<DATA>\n" +
                    "<BUSINESS_ID>100</BUSINESS_ID><!--业务唯一码-->\n" +
                    "<BUSINESSNUMBER>32040020000000002527</BUSINESSNUMBER><!--缴款码-->\n" +
                    "<PAY_DATE></PAY_DATE><!--缴款日期-->\n" +
                    "<IS_SUCCESS>1</IS_SUCCESS><!--缴费成功标志-->\n" +
                    "<STATUS_MC></STATUS_MC><!--作废说明-->\n" +
                    "\t</DATA>\n" +
                    "</ROOT>\n";
//            Charset gbk = Charset.forName("gbk");
//            String gb2312 = new String(s1.getBytes(StandardCharsets.UTF_8),"gbk");
            //System.out.println(gb2312);

            String gbk1 = StringUtils.newString(Base64.encodeBase64(s1.getBytes()), "gbk");
            String gbk2 = new String(Base64.decodeBase64(gbk1), "gbk");
            String s2 = new String(gbk2.getBytes("gbk"),StandardCharsets.UTF_8);
            System.out.println(s2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void sm4encode() {
        String s = null;
        try {
            String s1 = "{\"ywh\":\"2022081302212\"}";
           // String gbk1 = StringUtils.newString(Base64.encodeBase64(s1.getBytes()),StandardCharsets.UTF_8.toString());
            //String s2 = new String( Sm4Util.encrypt(s1.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
            String s3 = Base64.encodeBase64String(Sm4Util.encrypt(s1.getBytes(StandardCharsets.UTF_8),null));
            System.out.println(s3);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void sm4decode() {
        String s = null;
        try {
            String s1 = "BBRmFWi+4W5/xKNmHKzKYn0e/zMcLRGFFRPuxytaKdpn93PgIDVbCsZXSQ/ydB\n" +
                    "XwYGFopySIF8I0WiCF6Wi0Goy5AP3NqTYv0azulSjgmzULV3PVA4pokLMV2kKC2+AIZGKLLq1Z/F\n" +
                    "xzlpFuwuaaYPtF0qvHs0bcbZic9x1hh3+mW8x7h0mOLxuDh/FEfsptyp6bqkhnjP14/ifq8erfSL\n" +
                    "vvHACAy+Yc3Su/lhkPIXP4HMVRsGNOVyHOfuwmBSbzSx7dh7DAEebgGiwKEF2SAtkg2/TnZP1GGn\n" +
                    "+9GIM/Nln+sHEdnvzGqNWd6KtmWz9k8gJPiRT/2dqXbfin61UBgnPjRMbTcRweyyXLQ7166zL8nS\n" +
                    "/prG3aRBlVR85XAu4RmTon5YbxL6GtIugg6ZFhkQKHdsF5Z/wmp/hgWB2AgD7RHfeAGc/6VovgQS\n" +
                    "+4Yjfnd8q3rcJZ4YU+mVvnkwxf6V3O1AQKMrVh8vDNzBrB2sANkmNZJAq5AOwAGXgTAnv0bNeqAd\n" +
                    "0jIDvzByj+IFMNKEm3cl9U3tD3b8VwEYNum0DAllTSOHGxKuwLGYKg2fI28nS3KCbk7HJK9NGhcQ\n" +
                    "DRffkQC3uoOgJiLYb8MYQfK1SD/2p76/AEehvfER9FwTZLuwHKAGEySsbyH+sAE1DtG6sGwCf/Uc\n" +
                    "u+xLS2f+O/EqKC8ptipaZNQ7zgTyBT7MD4dckACCg3YKIF+bMbE2uL3D8ytmySyGXh4BLF/3nvxx\n" +
                    "AU9qK5HquIBdmxvghuKIE5WOoicUES7zFvIhZ8N/qVLio3WEq3dqBelEwVSTSEDxyypcQ2BwJWqu\n" +
                    "SBQGk7g+9jfDU7XwZSAiyfOdLvgHK9d4eL/3BhzWxseny6gp7RqUP51lGaMn8pqOqHwhBlSTWar4\n" +
                    "yAb4eV4XvD9L8GQ9FJHag5Gly3k1alH4qriDaYhB50A1D1OV6viuisYHXbNyXVtALC+8UKn+JJmo\n" +
                    "YmTgtWMinL2jxOCuXCm4HvDuGMCkPoizii/ExakZpwIxzEPS19Wmgwl6jU8TAm+tjWw5FprwhIXq\n" +
                    "JPfZeZmY11P5OWBwtbOjKHSuSCWmQV8QJaDKrTu8Lxjx7pDnNeo5H7RANvkG/zqnmCkaNru3mr02\n" +
                    "J/QngKthD7J9MINQ0p8DOpsIlcWlmftVJSc4/6nfafMdbMvBl5DdZd+KwABxEUrM+f2BQVziUSbj\n" +
                    "CV/L8RVPGIkT1+PymMojjNWbTWL5BK8XmKnl38gUuM4c6bDNBIRMv0hMiojq6j8Qjyz9gFrm9ECn\n" +
                    "wulHfAfDxEpfhq4LdRK46ZSOpXhEzGAVuIdNO3tcRtsQdihtdI5UYcuzlQx/MafFHLBebbFsZlCO\n" +
                    "1QeE2sB9xNS8os/aPvQtRepASQxFE0O4ed19mNfCXf/yrOsCnWd0tmX0h8BOiGc+QR7avUDSJazQ\n" +
                    "==";
            String gbk1 = StringUtils.newString(Base64.decodeBase64(s1.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8.toString());
            String s2 = new String( Sm4Util.decrypt(Base64.decodeBase64(s1.getBytes(StandardCharsets.UTF_8)),null),StandardCharsets.UTF_8);
            System.out.println(s2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test10(){
        try {
            //String gbk = new String("闀囨睙浜烘皯娉曢櫌".getBytes("GBK"), "UTF-8");
            //String gbk = new String("镇江人民法院".getBytes("GBK"), "UTF-8");
            String gbk = new String("镇江人民法院".getBytes(StandardCharsets.UTF_8), "GBK");
            System.out.println(gbk);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
