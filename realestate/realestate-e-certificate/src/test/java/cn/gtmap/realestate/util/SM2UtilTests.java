package cn.gtmap.realestate.util;

import cn.gtmap.realestate.certificate.CertificateApp;
import cn.gtmap.realestate.certificate.core.model.encrypt.SM2KeyPair;
import cn.gtmap.realestate.certificate.util.Base64Util;
import cn.gtmap.realestate.certificate.util.SM2Util;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/6/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CertificateApp.class)
public class SM2UtilTests {

    @Test
    public void sm2() throws IOException {

        /*SM2 sm02 = new SM2();
        SM2KeyPair aKeyPair = sm02.generateKeyPair();
        ECPoint publicKey = aKeyPair.getPublicKey();
        BigInteger privateKey = aKeyPair.getPrivateKey();
        String pubk = DataUtil.encodeHexString(publicKey.getEncoded(false));
        String prik = DataUtil.encodeHexString(privateKey.toByteArray());
        System.out.print("\n公钥字符串:" + pubk);
        System.out.print("\n私钥字符串:" + prik);*/

        System.out.println("\n-----------------公钥加密与解密-----------------");
        //公钥
        SM2KeyPair keyPair = SM2Util.getKeyPair();
        //String publicKeyStr = Base64Util.encodeByteToBase64Str(keyPair.getPublicKey().getEncoded(false));
        //String privateKeyStr = Base64Util.encodeByteToBase64Str(keyPair.getPrivateKey().toByteArray());
        //String publicKeyStr = "BHJXz4SQVSuFSe2zWQKcfoaTvh/Mh7OaKM7Sooib+7MB/HqeGPkwnLYRrU2ktl3HPVLFrbUB9pJrjaihJL+hrLM=";
        String privateKeyStr = "fE7ZcH+RZifRIIBeCOmE8qFJh8qQB0JST9REkBt2u3g=";
        //String eR = SM2Util.encrypt("\"bdcdyh\": \"611002004011GB00018F00030013\"", publicKeyStr);
        String eR ="BHJXOkkkzxHissTy4nQvibzW+beLyuiFi2yFSbg0l3D9s5AG5hpQeIzWwfPKIQtPlgrnT0SjtJeK7uEQa2WztD4xqACKB9ZEmdxUuON5bsg8AIjEkuacEqvUonzZ27un/8E2fyk9EcKIwL0MlKvFM3Pjy0PGuvvVwQEQlLsQmYcwCC8lkhrXPcDcGcvSGy8BF78OeWGp3ha35nfJ5x0zt7nSAOKGe2TXzPAjKfX5GvmKyZOd93CFrlGtdM7wZ6wwNTu9ag6s8EU1fTBdC6mB0Rp364ul0BUVCHP7KVfhtHJJQ2BdS+hEru8XotpuvvHP3TJ5vGRg+XypTxiexhFGL0Edme7WxhbjhOhgyCn2lqB0/4sTRUyEK4AQsECMUhwIOytKzGRRYHNy53ojHx12f1XN7pESClP7dUrHU2IODhZCTDGgb7CrYilPwrIIVe7zBzEBDVRYDolBQ0J1RGlMjCgbORstmtZWXapuhpkVWhOFJdGJR5579lFzOsZpRTUG81c9ZFTsarzUY//cZvwFnxF8M1FgsQi0grLw2AYOUK08jE4I9Jd5/cmQTyiY4YFuBM7IuR5L5GMLBxGMYY5RJfF1Zayp9LMcufdM4jkWwjudlU9p/rcbQ7UNEAqLFXNmXSMKWVpEhf2vzXvqOkbUzK29pLQw+J8c0IAEvno3urVnW9UE60o0DnyCFux3IYTHlNdkqQXAjwbNWeAmlgitgtslOlnQCmhrMs/8W1InDloArAC2jUYwVvSXKSwlP4jnMjZcqBYliouxk5lfotD2SF/GNwqb9pPls5KwpWwvPstXuCcQ69fjN+KwoNL1PMQNfegQA1VeZyCwvrqlY1VjiRZRQ9lkszMlSlvZIS4bgHN/7hQvIBcUDdVu8GrYIzmccQO2iDLWKnlQGdN3Ee/ScLyIbPXfldwS8HY9U9y7WrpYIxG7tTw+XJkpyANNZc1wSuCGkM5KvzPkNb9qvIC8skT73kHwBG2YjIQGDnzUBCPweHKqzvWxc0nhTHShJzem0807j+3BDcLv8MMpWCIIPC2SOGT9LqMNGGbvQ/kmzIHmSHOwPjsBJwXKQ9tVk5e4EnbSnn8vupfBDNisHhxoosnexY2nziGWOITCEhDRwVQxERl0QdZ14elg4ZIS0JL2IDUaUBWEEAffuqVS78gQIrxrlq4n4xQ3dR37LOsLpiBbmHCsA+a3XlujP6H8S+KyffK00BRn6rptmD6A2bqSxDXH+qRB8+v9MbPYXguRUlW9go5rnAmuVVFWYNVQOBHS/dkzWpJiuzi2YDzeQX5ZYSzbnpp/MgoLsZVQPk1bOaph9C3XaBHIEv2iaMki8NTjMqb+FTakRX8YOvJosDN2CC+GCFOhWeSp6bNz2jgRBTVcpirHXCk/z9MT1zNI+bw9/p/GrBQGPa+D6ykKv+wwkljbCpGhZ95MfjFPUaopCunI/0TTExDbC7lxNDGFjct7qrOfW6n+PnGV3R3EVmNOI4GHrZsLTMYd4IPYlKUh00geIE9vSalCVNf/JKWc8joGSGEpRJQGX6xkj0XQsNVEKO0mG3Xd2q1FO+ig/reqX5poum/+mUSRkAHGp6QTvQGMXyjw7zdCYDyPL1c+aaQCqq+BifS+MlRYFigNnmiXqxsg+GrULAUUBLkg6OYmIjOCEToULsG2YCaCEaXxOIODfPJwgKJaaoVJcJCBAXdyG6Yfjg32ZDwfigntyyrbZmnPcd46GkPkKLXzP3iyKAlA2rZTzIWSjieBU4jZEI96bHLWzuYN46zm";
        System.out.print("\n密文:" + eR);
        System.out.println("\n解密后明文:" + SM2Util.decrypt(eR, privateKeyStr));
        /*SM2 sm02 = new SM2(true);
        ECPoint publicKey = sm02.importPublicKey("C:\\temp\\publickey.pem");
        BigInteger privateKey = sm02.importPrivateKey("C:\\temp\\privatekey.pem");
        String eR = SM2Util.encrypt("测试加密:aaaaa服从:aaaaaa{123aabb}", publicKey);
        System.out.print("\n密文:");
        System.out.print(eR);
        System.out.println("\n解密后明文:" + SM2Util.decrypt(eR, privateKey));*/
        /*SM2 sm02 = new SM2(true);
        ECPoint publicKey = sm02.importPublicKey("C:\\temp\\publickey.pem");
        BigInteger privateKey = sm02.importPrivateKey("C:\\temp\\privatekey.pem");
        byte[] eR = sm02.encrypt("测试加密:aaaaa服从:aaaaaa{123aabb}", publicKey);
        System.out.print("\n密文:");
        System.out.print(DataUtil.encodeHexString(eR));
        System.out.println("\n解密后明文:" + sm02.decrypt(eR, privateKey));*/

        //System.out.println("-----------------签名与验签-----------------");
        //SM2 sm02 = new SM2(true);
        // SM2KeyPair aKeyPair = sm02.generateKeyPair();
        // ECPoint publicKey = aKeyPair.getPublicKey();
        // BigInteger privateKey = aKeyPair.getPrivateKey();
        /*String M = "{\"ywh\": \"111111111111111\",\"zzlxdm\": \"2\",\"zzbh\": \"DZZM11000111\",\"zzbfjg\": \"不动产兴安盟登记机构\",\"zzbfjgdm\": \"1500001\",\"zzbfrq\": 1548086400000,\"zzzt\": 1,\"bdcqzh\": \"蒙(2016)乌兰浩特市不动产证明第0003309号\",\"ewmnr\": \"201903050587$320311001021GB00233F00010013$苏(2019)徐州市不动产权第0020128号$B458F2500F1FDDF5A61F47179D2D5712\",\"qlqtzk\": \"\",\"dwdm\": \"152201\",\"sqsjc\": \"蒙\",\"szsxqc\": \"XXXX市\",\"qt\": \"房屋结构:混合结构专有建筑面积:237.66㎡，分摊建筑面积:18.21㎡房屋总层数:11层，所在层数:第1层\",\"fj\": \"土地用途为:批发零售用地,使用期限:2008年09月01日起2048年09月01日止;土地用途为:批发零售用地,使用期限:2008年09月01日起2048年09月01日止;\",\"bdcdyh\": \"152201009009GB00054F03031101\",\"zl\": \"XXX市胜利街江南公寓综合楼1B-商业-4号商业[4-24-99-82]\",\"zmqlsx\": \"抵押权\",\"gyqk\": \"共同共有\",\"qllx\": \"国有建设用地使用权/房屋所有权\",\"qlxz\": \"出让/商品房\",\"yt\": \" 批发零售用地 批发零售用地/商业服务\",\"mj\": \"共用宗地面积2520.38㎡/房屋建筑面积255.87㎡\",\"syqx\": \"详见附记栏\",\"nf\": \"2019\",\"zhlsh\": \" 0002102 \",\"qlr\": \"张三  侯雁冰\",\"ywr\": \"王伯生\" ,\"qlrxx\": [{\"czzt\": \"张三\",\t\"czztdm\": \"152827199011110909\",\"czztdmlx\": \"公民身份号码\",\"czztdmlxdm\": \"111\"}]}";
        String signature = SM2Util.SM2Sign(M, "zhangyu_token");
        System.out.println("用户标识:" + "zhangyu_token");
        System.out.println("签名信息:" + M);
        System.out.println("数字签名:" + signature);
        System.out.println("验证签名:" + SM2Util.SM2Verify(M, signature, "zhangyu_token", SM2Util.SM2_PUBLIC_KEY));*/

        /*System.out.println("-----------------密钥协商-----------------");
        SM2 sm02 = new SM2(true);
        String aID = "AAAAAAAAAAAAA";
        SM2KeyPair aKeyPair = sm02.generateKeyPair();
        SM2.KeyExchange aKeyExchange = new SM2.KeyExchange(aID,aKeyPair);

        String bID = "BBBBBBBBBBBBB";
        SM2KeyPair bKeyPair = sm02.generateKeyPair();
        SM2.KeyExchange bKeyExchange = new SM2.KeyExchange(bID,bKeyPair);
        SM2.TransportEntity entity1 = aKeyExchange.keyExchange_1();
        SM2.TransportEntity entity2 = bKeyExchange.keyExchange_2(entity1);
        SM2.TransportEntity entity3 = aKeyExchange.keyExchange_3(entity2);
        bKeyExchange.keyExchange_4(entity3);*/
    }

    @Test
    public void httpZzpdf() {
        //验签地址
        String url = "http://ip:port/realestate-e-certificate/rest/v1.0/sign/verifySign";
        //请求完整json
        String postParam = "{\"head\": {\"sign\": \"密文\"},\"data\": {\"ywh\": \"111111111111111\",\"zzlxdm\": \"11100000MB03271699001\",\"zzbh\": \"DZZM11000111\",\"zzbfjg\": \"不动产兴安盟登记机构\",\"zzbfjgdm\": \"1500001\",\"zzbfrq\": 1548086400000,\"bdcqzh\": \"蒙(2016)乌兰浩特市不动产证明第0003309号\",\"ewmnr\": \"201903050587$320311001021GB00233F00010013$苏(2019)徐州市不动产权第0020128号$B458F2500F1FDDF5A61F47179D2D5712\",\"qlqtzk\": \"\",\"dwdm\": \"152201\",\"sqsjc\": \"蒙\",\"szsxqc\": \"XXXX市\",\"qt\": \"房屋结构:混合结构专有建筑面积:237.66㎡，分摊建筑面积:18.21㎡房屋总层数:11层，所在层数:第1层\",\"fj\": \"土地用途为:批发零售用地,使用期限:2008年09月01日起2048年09月01日止;土地用途为:批发零售用地,使用期限:2008年09月01日起2048年09月01日止;\",\"bdcdyh\": \"152201009009GB00054F03031101\",\"zl\": \"XXX市胜利街江南公寓综合楼1B-商业-4号商业[4-24-99-82]\",\"zmqlsx\": \"抵押权\",\"gyqk\": \"共同共有\",\"qllx\": \"国有建设用地使用权/房屋所有权\",\"qlxz\": \"出让/商品房\",\"yt\": \" 批发零售用地 批发零售用地/商业服务\",\"mj\": \"共用宗地面积2520.38㎡/房屋建筑面积255.87㎡\",\"syqx\": \"详见附记栏\",\"nf\": \"2019\",\"zhlsh\": \" 0002102 \",\"qlr\": \"张三  侯雁冰\",\"ywr\": \"王伯生\" ,\"qlrxx\": [{\"czzt\": \"张三\",\"czztdm\": \"152827199011110909\",\"czztdmlx\": \"公民身份号码\",\"czztdmlxdm\": \"111\"}]}}";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            //必须设置编码格式为UTF-8
            StringEntity stringEntity = new StringEntity(postParam, "UTF-8");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response != null && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                System.out.println(new String(result, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
