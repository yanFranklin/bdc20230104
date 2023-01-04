package cn.gtmap.realestate.common.core.dto.accept;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 获取token后的二维码和待认证token
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-20 11:13
 **/
public class DzzzTokenDTO implements Serializable {
    private static final long serialVersionUID = 3437168998443405728L;

    private String qrCode;

    private String token;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "DzzzTokenDTO{" +
                "qrCode='" + qrCode + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
