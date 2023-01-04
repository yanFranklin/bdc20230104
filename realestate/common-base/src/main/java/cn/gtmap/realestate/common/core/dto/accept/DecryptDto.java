package cn.gtmap.realestate.common.core.dto.accept;

/**
 * 解密内容
 */
public class DecryptDto {

    String decryptkey;

    String encryptkey;

    public String getDecryptkey() {
        return decryptkey;
    }

    public void setDecryptkey(String decryptkey) {
        this.decryptkey = decryptkey;
    }

    public String getEncryptkey() {
        return encryptkey;
    }

    public void setEncryptkey(String encryptkey) {
        this.encryptkey = encryptkey;
    }
}
