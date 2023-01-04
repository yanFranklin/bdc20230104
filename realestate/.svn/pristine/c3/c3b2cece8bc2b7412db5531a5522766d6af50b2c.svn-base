package cn.gtmap.realestate.building.ui.util;

import cn.gtmap.realestate.building.ui.config.omp.*;
import cn.gtmap.realestate.building.ui.core.bo.OmpParamWhereBO;
import cn.gtmap.realestate.building.ui.core.bo.OmpUrlParamBO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description OMP 工具类
 */
@Component
public class OmpUtil {

    private static volatile List<OmpConfig> ompConfigs;

    private static volatile Map<String,OmpConfig> ompConfigMap = new HashMap<>();

    /**
     * 一张图定位请求地址
     */
    public static String OMP_URL = "";

    @Autowired
    private FwOmpConfig fwOmpConfig;

    @Autowired
    private ZdOmpConfig zdOmpConfig;

    @Autowired
    private LqOmpConfig lqOmpConfig;

    @Autowired
    private CyOmpConfig cyOmpConfig;

    @Autowired
    private HyOmpConfig hyOmpConfig;

    private synchronized void initOmpConfigList(){
        ompConfigs = new ArrayList<>();
        ompConfigs.add(fwOmpConfig);
        ompConfigs.add(zdOmpConfig);
        ompConfigs.add(lqOmpConfig);
        ompConfigs.add(cyOmpConfig);
        ompConfigs.add(hyOmpConfig);

        OMP_URL = EnvironmentConfig.getEnvironment().getProperty("omp.url");
        for(OmpConfig config : ompConfigs){
            if(StringUtils.isNotBlank(config.getBdclx())){
                String[] bdclxArr = config.getBdclx().split(",");
                for(String bdclx : bdclxArr){
                    ompConfigMap.put(bdclx,config);
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ompConfig, ompParamWhereBO
     * @return java.lang.String
     * @description 获取 带有参数的请求一张图定位页面的URL
     */
    public String getOmpUrlWithParam(OmpConfig ompConfig, OmpParamWhereBO ompParamWhereBO) throws UnsupportedEncodingException {
        String ompUrl = OMP_URL;
        StringBuilder stringBuilder = new StringBuilder(ompUrl);
        // 模板
        stringBuilder.append("/").append(ompConfig.getTpl(ompParamWhereBO.getBdcdyh()));
        // 固定参数
        stringBuilder.append("?hideLeftPanel=true&hideTopBar=true&action=location");
        // 获取参数JSON
        String paramJSON = ompConfig.getParamJson(ompParamWhereBO);
        // 转换参数JSON中的 双引号 和 单引号
        paramJSON = paramJsonToURL(paramJSON);
        stringBuilder.append("&params=").append(paramJsonToURL(paramJSON));
        return stringBuilder.toString();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param xmDOList
     * @return java.lang.String
     * @description 批量业务  拼接 OMP URL
     */
    public String getOmpUrlWithBdcXmList(List<BdcXmDO> xmDOList) throws UnsupportedEncodingException {
        List<OmpParamWhereBO> whereList = new ArrayList<>();
        OmpConfig ompConfig = getWhereBOAndOmpConfigByXmList(xmDOList,whereList);
        String ompUrl = OMP_URL;
        StringBuilder stringBuilder = new StringBuilder(ompUrl);
        // 模板
        stringBuilder.append("/").append(ompConfig.getTpl(""));
        // 固定参数
        stringBuilder.append("?hideLeftPanel=true&hideTopBar=true&action=location");
        // 获取参数JSON
        String paramJSON = ompConfig.getParamJson(whereList);
        // 转换参数JSON中的 双引号 和 单引号
        paramJSON = paramJsonToURL(paramJSON);
        stringBuilder.append("&params=").append(paramJsonToURL(paramJSON));
        return stringBuilder.toString();
    }

    /**
     * 替换param JSON中的 双引号 和 单引号
     * @param paramJson
     * @return
     */
    private String paramJsonToURL(String paramJson){
        if(StringUtils.isNotBlank(paramJson)){
            paramJson = paramJson.replace("\"","%22");
            paramJson = paramJson.replace("'","%27");
        }
        return paramJson;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdclx
     * @return cn.gtmap.realestate.building.ui.config.omp.OmpConfig
     * @description 根据BDCLX 查询OMPConfig
     */
    public OmpConfig getOmpConfigByBdclx(String bdclx){
        if(CollectionUtils.isEmpty(ompConfigs)){
            initOmpConfigList();
        }
        if(MapUtils.isNotEmpty(ompConfigMap) && StringUtils.isNotBlank(bdclx)){
            return ompConfigMap.get(bdclx);
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmDOList
     * @param whereList
     * @return cn.gtmap.realestate.building.ui.config.omp.OmpConfig
     * @description 根据项目列表  获取 拼接where条件的 实体列表  和 具体使用哪个图层配置
     */
    private OmpConfig getWhereBOAndOmpConfigByXmList(List<BdcXmDO> bdcXmDOList,List<OmpParamWhereBO> whereList){
        // 获取自然幢号集合  和 宗地集合
        Map<String,List<String>> zdMap = new HashMap<>();
        // 判断BDCLX 以最大的BDCLX 为主
        boolean fwLayer = false;
        for(BdcXmDO xmTemp : bdcXmDOList){
            if(StringUtils.isNotBlank(xmTemp.getBdcdyh())){
                OmpParamWhereBO tempBO = new OmpParamWhereBO(xmTemp.getBdcdyh());
                if(StringUtils.isNotBlank(tempBO.getLszd())){
                    if(zdMap.get(tempBO.getLszd()) == null){
                        zdMap.put(tempBO.getLszd(),new ArrayList<>());
                    }
                    if(StringUtils.isNotBlank(tempBO.getZrzh())
                            && Integer.parseInt(tempBO.getZrzh()) > 0){
                        fwLayer = true;
                        List<String> yList = zdMap.get(tempBO.getLszd());
                        if(!yList.contains(tempBO.getZrzh())){
                            zdMap.get(tempBO.getLszd()).add(tempBO.getZrzh());
                        }
                    }
                }
            }
        }

        // 确定是否是 房屋图层
        OmpConfig ompConfig = fwLayer? fwOmpConfig : zdOmpConfig;

        if(MapUtils.isNotEmpty(zdMap)){
            Iterator<Map.Entry<String,List<String>>> iterator
                    = zdMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String,List<String>> entry = iterator.next();
                String lszd = entry.getKey();
                OmpParamWhereBO ompParamWhereBO = new OmpParamWhereBO();
                ompParamWhereBO.setLszd(lszd);
                if(fwLayer){
                    List<String> zrzhList = entry.getValue();
                    ompParamWhereBO.setZrzh(pjzrzh(zrzhList));
                }
                whereList.add(ompParamWhereBO);
            }
        }
        return ompConfig;
    }

    private String pjzrzh(List<String> zrzhList){
        StringBuilder sb = new StringBuilder("");
        for(int i = 0 ;i < zrzhList.size() ; i++){
            sb.append(zrzhList.get(0));
            if(i != zrzhList.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
