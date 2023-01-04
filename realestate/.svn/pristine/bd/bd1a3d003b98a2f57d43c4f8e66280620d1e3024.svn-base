package cn.gtmap.realestate.building.ui.core.bo;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description
 */
public class OmpUrlParamBO {

    private String type;

    private OmpParamObject params;

    public OmpUrlParamBO(){}

    public OmpUrlParamBO(String layerAlias, String where){
        OmpParamObject object = new OmpParamObject();
        object.setLayerAlias(layerAlias);
        object.setWhere(where);
        object.setShowInfo(true);
        this.type = "layerLocation";
        this.params = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OmpParamObject getParams() {
        return params;
    }

    public void setParams(OmpParamObject params) {
        this.params = params;
    }
}
