package cn.gtmap.realestate.exchange.core.bo.reqProp;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-27
 * @description
 */
public class BbGspApiPropBO extends ReqProBO {

    // 终端地址
    private String url;

    // 应用码 3403-XXXX
    private String yybm;

    // 授权码 XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    private String sqm;

    // 接口编码 3403-XXXX-1-XXXXXXXX
    private String jkbm;

    // 超时时间
    private Long timeout;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 服务编码 340300000000_01_XXXXXXXXXX
    private String serviceCode;

    // 客户端信息 zzwk_sgtzyj
    private String clientInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYybm() {
        return yybm;
    }

    public void setYybm(String yybm) {
        this.yybm = yybm;
    }

    public String getSqm() {
        return sqm;
    }

    public void setSqm(String sqm) {
        this.sqm = sqm;
    }

    public String getJkbm() {
        return jkbm;
    }

    public void setJkbm(String jkbm) {
        this.jkbm = jkbm;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
