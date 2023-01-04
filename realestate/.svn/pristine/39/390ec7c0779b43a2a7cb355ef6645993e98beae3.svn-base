package cn.gtmap.realestate.building.ui.config.omp;

import cn.gtmap.realestate.building.ui.core.bo.OmpParamWhereBO;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description
 */
public abstract class OmpConfig {

    private static final String PLACE_HOLDER = "xzqdm";

    private String tpl;

    private String layerAlias;

    private String bdclx;

    public void setTpl(String tpl) {
        this.tpl = tpl;
    }

    public String getLayerAlias() {
        return layerAlias;
    }

    public void setLayerAlias(String layerAlias) {
        this.layerAlias = layerAlias;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ompParamWhereBO
     * @return java.lang.String
     * @description 不同图层  拼凑不同的 WHERE 语句
     */
    public abstract String getParamJson(OmpParamWhereBO ompParamWhereBO) throws UnsupportedEncodingException;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ompParamWhereBOList
     * @return java.lang.String
     * @description 一个宗地对应一个WhereBO
     */
    public abstract String getParamJson(List<OmpParamWhereBO> ompParamWhereBOList) throws UnsupportedEncodingException;
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 根据BDCDYH 获取 模板名称
     */
    public String getTpl(String bdcdyh){
        if(StringUtils.isNotBlank(this.tpl)
            && this.tpl.contains(PLACE_HOLDER)
                && StringUtils.isNotBlank(bdcdyh)
                && bdcdyh.length() == 28){
            String xzqdm = bdcdyh.substring(0,19);
            return this.tpl.replace(PLACE_HOLDER,xzqdm);
        }
        return this.tpl;
    }
}
