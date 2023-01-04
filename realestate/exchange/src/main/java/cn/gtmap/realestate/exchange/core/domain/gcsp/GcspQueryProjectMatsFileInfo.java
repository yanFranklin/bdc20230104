package cn.gtmap.realestate.exchange.core.domain.gcsp;

import java.io.Serializable;

public class GcspQueryProjectMatsFileInfo implements Serializable {

    private static final long serialVersionUID = 2911452065426461943L;

    private String matinstName;
    private String url;

    public String getMatinstName() {
        return matinstName;
    }

    public void setMatinstName(String matinstName) {
        this.matinstName = matinstName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
