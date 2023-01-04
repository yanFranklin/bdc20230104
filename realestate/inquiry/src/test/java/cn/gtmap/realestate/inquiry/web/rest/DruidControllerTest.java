package cn.gtmap.realestate.inquiry.web.rest;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;


/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/5
 * @description 用于获取密码 加密解密密码
 */
public class DruidControllerTest {
    @Test
    public void getPasswordAndKey() {
        try {
            String password = "gtis";
            String[] arr = ConfigTools.genKeyPair(512);

            System.out.println("password:" + password);
            System.out.println("privateKey:" + arr[0]);
            System.out.println("publicKey:" + arr[1]);
            System.out.println("password:" + ConfigTools.encrypt(arr[0], password));
        } catch (Exception ex) {
        }
    }
    @Test
    public void decrypt() {
        String password = "e/lWYHJj7d9rXmgsItIxLw2oWP4XasfqoDXA0AKNYHgeSJQK8IKgU8/+ia1ax4mr9ki4QZbrQrCW9WMkHDOPAA==";
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAK8ev+xi3qANIZpQGhndeKtc2EOlsPd48VIhphnl3OoOKqynJuQo7qZrJekk8iqXHgvze3bnFrFyTtf3vxGIamsCAwEAAQ==";
        String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEArx6/7GLeoA0hmlAaGd14q1zYQ6Ww93jxUiGmGeXc6g4qrKcm5Cjupmsl6STyKpceC/N7ducWsXJO1/e/EYhqawIDAQABAkEAqZ+nV1kNOwp9GfOs1JDpaVMtR5keqRbE6UEemsdEDJDAJrPh1R8dbKA5uDh93cOfaUctwEwue7LYd/l9gPrgwQIhAOm0nJca/C//t8CONCCIpyb/RZqHnaKnLwUhT2zBLG79AiEAv9NoUDFxMdgagynksi8cRbeT0JR2E6QuG3AwadKyX4cCIGjrSrmTxdAOr4Hk1YYdk10wkSD07VIOWE+nSq76cmZJAiAVzxhN/cEL1LD29p1csMR7HKr+jNJ72y+uyOuILH9BIwIhAN3Y/kAyp7IT18DvRGSsYQUoqWIpkLdq070sgBMTRwOX";
        try {
            String pwd = ConfigTools.decrypt(publicKey, password);
            System.out.println("pwd:" + pwd);
        } catch (Exception ex) {
        }
    }

    @Test
    public void encrypt() {
        String password = "gtis";
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAK8ev+xi3qANIZpQGhndeKtc2EOlsPd48VIhphnl3OoOKqynJuQo7qZrJekk8iqXHgvze3bnFrFyTtf3vxGIamsCAwEAAQ==";
        String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEArx6/7GLeoA0hmlAaGd14q1zYQ6Ww93jxUiGmGeXc6g4qrKcm5Cjupmsl6STyKpceC/N7ducWsXJO1/e/EYhqawIDAQABAkEAqZ+nV1kNOwp9GfOs1JDpaVMtR5keqRbE6UEemsdEDJDAJrPh1R8dbKA5uDh93cOfaUctwEwue7LYd/l9gPrgwQIhAOm0nJca/C//t8CONCCIpyb/RZqHnaKnLwUhT2zBLG79AiEAv9NoUDFxMdgagynksi8cRbeT0JR2E6QuG3AwadKyX4cCIGjrSrmTxdAOr4Hk1YYdk10wkSD07VIOWE+nSq76cmZJAiAVzxhN/cEL1LD29p1csMR7HKr+jNJ72y+uyOuILH9BIwIhAN3Y/kAyp7IT18DvRGSsYQUoqWIpkLdq070sgBMTRwOX";
        try {
            String pwd = ConfigTools.encrypt(privateKey, password);
            System.out.println("pwd:" + pwd);
        } catch (Exception ex) {
        }
    }
}