package cn.gtmap.realestate.building.ui.config.omp;

import cn.gtmap.realestate.building.ui.core.bo.OmpParamWhereBO;
import cn.gtmap.realestate.building.ui.core.bo.OmpUrlParamBO;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description
 */
@Configuration
@ConfigurationProperties(prefix = "bdc-zddw")
public class ZdOmpConfig extends OmpConfig{
    /**
     * @param ompParamWhereBO
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 不同图层  拼凑不同的 WHERE 语句
     */
    @Override
    public String getParamJson(OmpParamWhereBO ompParamWhereBO) throws UnsupportedEncodingException {
        String where = getSingleWhereParam(ompParamWhereBO);
        OmpUrlParamBO ompUrlParamBO = new OmpUrlParamBO(URLEncoder.encode(this.getLayerAlias(), "UTF-8"),where);
        return JSONObject.toJSONString(ompUrlParamBO);
    }

    /**
     * 拼接一个宗地  和自然幢上的 查询SQL
     * @param ompParamWhereBO
     * @return
     */
    private String getSingleWhereParam(OmpParamWhereBO ompParamWhereBO){
        StringBuilder whereSb = new StringBuilder("djh='");
        if(StringUtils.isNotBlank(ompParamWhereBO.getLszd())){
            whereSb.append(ompParamWhereBO.getLszd());
        }
        whereSb.append("'");
        return whereSb.toString();
    }

    /**
     * @param ompParamWhereBOList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 一个宗地对应一个WhereBO
     */
    @Override
    public String getParamJson(List<OmpParamWhereBO> ompParamWhereBOList) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder("");
        if(CollectionUtils.isNotEmpty(ompParamWhereBOList)){
            for(int i = 0 ; i < ompParamWhereBOList.size() ; i++){
                stringBuilder.append("(");
                stringBuilder.append(getSingleWhereParam(ompParamWhereBOList.get(i)));
                stringBuilder.append(")");
                if(i != ompParamWhereBOList.size()){
                    stringBuilder.append("%20or%20");
                }
            }
        }
        OmpUrlParamBO ompUrlParamBO = new OmpUrlParamBO(URLEncoder.encode(this.getLayerAlias(), "UTF-8"),stringBuilder.toString());
        return JSONObject.toJSONString(ompUrlParamBO);
    }
}
