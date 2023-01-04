package cn.gtmap.realestate.building.config.lpb;


import cn.gtmap.realestate.building.core.bo.ColumnBO;
import cn.gtmap.realestate.building.core.bo.LinkBO;
import cn.gtmap.realestate.building.core.bo.LpbConfigBO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-02
 * @description 楼盘表配置
 */
@Service
public class LpbConfig {

    /**
     * 链接标志  户室
     */
    public static final String FLAG_HS = "hs";

    /**
     * 链接标志  逻辑幢
     */
    public static final String FLAG_LJZ = "ljz";

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LpbConfig.class);

    /**
     * 默认配置 code
     */
    private static final String DEFAULT_CONFIG_CODE = "default";

    /**
     * code 与 配置 Map关系
     */
    private static Map<String, LpbConfigBO> configMap = new ConcurrentHashMap<>();

    /**
     * code 与 statusList Map关系
     */
    private static Map<String, List<String>> statusMap = new HashMap<>();

    /**
     * 私有常量
     */
    private static final String ERROR_MSG = "初始化楼盘表配置异常";

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.Map<java.lang.String,cn.gtmap.realestate.building.core.bo.LpbConfigBO>
     * @description 获取 全部配置
     */
    public static Map<String, LpbConfigBO> getConfigMap(){
        return configMap;
    }

    /**
     * @param code
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取配置的状态List
     */
    public static List<String> getConfigStatus(String code) {
        if(StringUtils.isBlank(code)){
            code = DEFAULT_CONFIG_CODE;
        }
        List<String> statusList = statusMap.get(code);
        if (CollectionUtils.isEmpty(statusList)) {
            statusList = initStatusList(code);
        }
        return statusList;
    }

    /**
     * @param code
     * @return java.util.List<cn.gtmap.realestate.building.core.bo.ColumnBO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取配置中的字段列表
     */
    public  List<ColumnBO> getColumnList(String code,String infoCode) {
        List<ColumnBO> list=null;
        if(StringUtils.equals(FLAG_LJZ,infoCode)){
            list=getLjzColumnList(code);
        }else{
            list=getHsColumnList(code);
        }
        return list;
    }

    public static List<ColumnBO> getLjzColumnList(String code){
        LpbConfigBO config = getLpbConfig(code);
        List<ColumnBO> columnBOList = null;
        if (config != null
                && config.getInfo() != null) {
            columnBOList = config.getInfo().getColumn();
        }
        return columnBOList;
    }

    public static List<ColumnBO> getHsColumnList(String code){
        LpbConfigBO config = getLpbConfig(code);
        List<ColumnBO> columnBOList = null;
        if (config != null
                && config.getFwhs() != null
                && config.getFwhs().getInfo() != null) {
            columnBOList = config.getFwhs().getInfo().getColumn();
        }
        return columnBOList;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param code 配置文件标志
     * @param linkFlag 链接配置标志
     * @return java.util.List<cn.gtmap.realestate.building.core.bo.LinkBO>
     * @description 默认是获取户室链接配置
     */
    public static List<LinkBO> getLinkList(String code,String linkFlag){
        if(StringUtils.equals(FLAG_LJZ,linkFlag)){
            return getLjzLinkList(code);
        }
        return getHsLinkList(code);
    }

    /**
     * @param code
     * @return java.util.List<cn.gtmap.realestate.building.core.bo.LinkBO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取实体内的链接配置
     */
    public static List<LinkBO> getHsLinkList(String code) {
        LpbConfigBO config = getLpbConfig(code);
        List<LinkBO> linkBOList = null;
        if (config != null && config.getFwhs() != null) {
            linkBOList = config.getFwhs().getLinks();
        }
        return linkBOList;
    }

    /**
     * @param code
     * @return java.util.List<cn.gtmap.realestate.building.core.bo.LinkBO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取实体外层的链接配置
     */
    public static List<LinkBO> getLjzLinkList(String code) {
        LpbConfigBO config = getLpbConfig(code);
        List<LinkBO> linkBOList = null;
        if (config != null) {
            linkBOList = config.getLinks();
        }
        return linkBOList;
    }

    /**
     * @param code
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化状态List
     */
    public static synchronized List<String> initStatusList(String code) {
        if(StringUtils.isBlank(code)){
            code = DEFAULT_CONFIG_CODE;
        }
        LpbConfigBO config = getLpbConfig(code);
        List<String> statusList = new ArrayList<>();
        if (config != null && config.getFwhs() != null) {
            String status = config.getFwhs().getStatus();
            if (StringUtils.isNotBlank(status)) {
                // 全部转成 大写
                status = StringUtils.upperCase(status);
                String[] arr = status.split(",");
                for (String arrtemp : arr) {
                    statusList.add(arrtemp);
                }
                if (CollectionUtils.isNotEmpty(statusList)) {
                    statusMap.put(code, statusList);
                }
            }
        }
        return statusList;
    }

    /**
     * @param code
     * @param status
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 移除配置中的错误状态
     */
    public static synchronized void removeErrorStatus(String code, String status) {
        if(StringUtils.isNotBlank(code)){
            List<String> statusList = getConfigStatus(code);
            if (StringUtils.isNotBlank(status) && CollectionUtils.isNotEmpty(statusList)) {
                List<String> temList = new ArrayList<>();
                for (String temp : statusList) {
                    if (!StringUtils.equals(temp, status)) {
                        temList.add(temp);
                    }
                }
                if (CollectionUtils.isNotEmpty(temList)) {
                    statusMap.put(code, temList);
                }
            }
        }
    }

    /**
     * @param code
     * @return cn.gtmap.realestate.building.core.bo.LpbConfigBO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取配置实体
     */
    public static LpbConfigBO getLpbConfig(String code) {
        if(StringUtils.isBlank(code)){
            code = DEFAULT_CONFIG_CODE;
        }
        LpbConfigBO configBO = configMap.get(code);
        if (configBO == null) {
            configBO = initLpbConfigByCode(code, "");
        }
        if (configBO == null) {
            configBO = configMap.get(DEFAULT_CONFIG_CODE);
        }
        return configBO;
    }

    /**
     * @param code
     * @return LpbConfigBO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化楼盘表查询配置
     */
    public static LpbConfigBO initLpbConfigByCode(String code, String path) {
        if(StringUtils.isBlank(code)){
            code = DEFAULT_CONFIG_CODE;
        }
        LpbConfigBO lpbConfigBO = null;
        ClassPathResource res = null;
        if (StringUtils.isNotBlank(path)) {
            res = new ClassPathResource(path);
        } else if (StringUtils.equals(code, DEFAULT_CONFIG_CODE)) {
            res = new ClassPathResource(getDefaultConfigPath());
        } else if (StringUtils.isNotBlank(code)) {
            res = new ClassPathResource(MessageFormat.format(getConfigPathFormat(), code));
        }
        if (res != null && res.exists()) {
            lpbConfigBO = readConfigFromResource(res);
            if(lpbConfigBO != null && lpbConfigBO.getInfo() != null){
                List<ColumnBO> list = lpbConfigBO.getInfo().getColumn();
                //lst 常量做读取redis里的颜色配置
                BdcRedisFeignService bdcRedisFeignService =(BdcRedisFeignService) Container.getBean("cn.gtmap.realestate.common.core.service.feign.inquiry.BdcRedisFeignService");
                String lpbColor = bdcRedisFeignService.getStringValue("lpbColor");
                if(StringUtils.isNotBlank(lpbColor)){
                    List<ColumnBO> listColum = JSONObject.parseArray(lpbColor,ColumnBO.class);
                    if(CollectionUtils.isNotEmpty(listColum)){
                        if(CollectionUtils.isEmpty(list)){
                            list = new ArrayList<>();
                        }
                        list.addAll(listColum);
                    }
                }
            }


            // 存放 静态变量 MAP 并清空 statusMap
            configMap.put(code, lpbConfigBO);
            statusMap.remove(code);
        } else {
            LOGGER.error("没有找到配置文件 code:{}，已使用默认配置", code);
        }
        // 如果 资源不存在 判断 default 是否已加载，如果未加载 加载 default
        if (lpbConfigBO == null && configMap.get(DEFAULT_CONFIG_CODE) == null && !StringUtils.equals(DEFAULT_CONFIG_CODE,code)) {
            lpbConfigBO = initLpbConfigByCode(DEFAULT_CONFIG_CODE, getDefaultConfigPath());
        }
        return lpbConfigBO;
    }

    private static LpbConfigBO readConfigFromResource(ClassPathResource res) {
        LpbConfigBO lpbConfigBO = null;
        XStream xStream = new XStream();
        xStream.alias("fwhsList", LpbConfigBO.class);
        try {
            Reader reader = new InputStreamReader(res.getInputStream(), "UTF-8");
            xStream.autodetectAnnotations(true);
            Object object = xStream.fromXML(reader);
            if (object instanceof LpbConfigBO) {
                lpbConfigBO = (LpbConfigBO) object;
            }
            reader.close();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(ERROR_MSG + ":UnsupportedEncodingException", e);
        } catch (FileNotFoundException e) {
            LOGGER.error(ERROR_MSG + ":FileNotFoundException", e);
        } catch (IOException e) {
            LOGGER.error(ERROR_MSG + ":IOException", e);
        }
        return lpbConfigBO;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description 获取配置文件滴啊会
     */
    private static String getDefaultConfigPath(){
        String sysVersion = EnvironmentConfig.getEnvironment().getProperty("sysversion","standard");
        return "/conf/lpb/"+sysVersion+"/lpb.xml";
    }

    private static String getConfigPathFormat(){
        String sysVersion = EnvironmentConfig.getEnvironment().getProperty("sysversion","standard");
        return "/conf/lpb/"+sysVersion+"/lpb_{0}.xml";
    }
}
