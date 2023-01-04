package cn.gtmap.realestate.building.ui.core.bo;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description 拼凑参数中的WHERE 语句
 */
public class OmpParamWhereBO {

    private String bdcdyh;

    private String zrzh;

    private String lszd;

    private String xzqdm;

    public OmpParamWhereBO(String bdcdyh){
        this.bdcdyh = bdcdyh;
        if(StringUtils.isNotBlank(bdcdyh) && bdcdyh.length() == 28){
            String zrzhStr = bdcdyh.substring(20,24);
            this.zrzh = Integer.parseInt(zrzhStr)+"";
            this.lszd = bdcdyh.substring(0,19);
            this.xzqdm = bdcdyh.substring(0,6);
        }
    }

    public OmpParamWhereBO(){}

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }
}
