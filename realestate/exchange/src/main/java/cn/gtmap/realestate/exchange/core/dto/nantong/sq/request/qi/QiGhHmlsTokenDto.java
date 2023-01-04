package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request.qi;

public class QiGhHmlsTokenDto {

    private String clientId;
    private String secret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "QiGhHmlsTokenDto{" +
                "clientId='" + clientId + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
