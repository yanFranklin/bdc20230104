package cn.gtmap.realestate.init.config;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.init.service.InitService;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlflAbstractService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzMbService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzQlqtzkFjMbService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化工厂
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/1.
 * @description
 */
@Component
@ConfigurationProperties(prefix = "init.services")
public class InitBeanFactory implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitBeanFactory.class);

    @Autowired
    private BdcDzgxConfig bdcDzgxConfig;
    @Autowired
    private MessageProvider messageProvider;
    @Value("${init.dozerVersion:standard}")
    private String version;
    /**
     * 存储服务集合
     */
    private List<Class> list;
    /**
     * 初始化所需服务集合
     */
    private List<Class> initServices;
    /**
     * 删除所需服务集合
     */
    private List<Class> deleteServices;

    /**
     * 初始化时是否生成权利附记
     */
    private boolean initFj;
    /**
     * 是否将证书附记回写到权利fj里(只有在initFj为false的时候才生效)
     */
    private boolean hxFj;
    /**
     * 模板附记位置 (top,bottom,空的话不追加)
     */
    private String fjWz;
    /**
     * 是否读取项目表的权利其他状况
     */
    private boolean dqXmQlqtzk;
    /**
     * 默认是否覆盖登记簿
     */
    private boolean coverDjb;

    /**
     * 抽取楼盘表数据时是否覆盖登记簿
     */
    private boolean lpbsjCoverDjb;
    /**
     * 分别持证是否显示持证人
     */
    private boolean fbczSfxsczr;

    /**
     * 证书特殊服务的bean
     */
    private List<String> zsServices;

    /**
     * 特殊服务的bean
     */
    private List<String> tshzServices;

    /**
     * bean工厂集合
     */
    private  Map<String,Map<String, InitService>> beanMap;

    /**
     * 证书模板服务的bean
     */
    private Map<Integer,InitBdcqzMbService> zsMbServices;

    /**
     * 初始化后服务的bean
     */
    private List<InitBdcJwService> bdcJwServices;

    /**
     * 权利其他状况和附记
     */
    private List<InitBdcqzQlqtzkFjMbService> zsQlqtzkfjMbServices;


    /**
     * 工厂类加载服务相关bean
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param applicationContext
     *@return
     *@description
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if(CollectionUtils.isNotEmpty(list)){
            beanMap =new HashMap<>();
            //循环要加载到工厂内的bean
            for(Class initClass:list){
                Map<String, InitService> classMap=new HashMap<>();
                //根据bean类去分组
                beanMap.put(initClass.toString(),classMap);
                Map<String, InitService> map = applicationContext.getBeansOfType(initClass);
                if(MapUtils.isNotEmpty(map)){
                    for(InitService initService:map.values()){
                        if(StringUtils.isNotBlank(initService.getVal())){
                            //版本空的就是标准版，并且集合内没有这个标准版才能存入  或者特殊版本与当前系统配置的版本号相同直接存入
                            if((StringUtils.isBlank(initService.getVersion()) && !classMap.containsKey(initService.getVal().toUpperCase()))  || StringUtils.equals(initService.getVersion(),version)){
                                classMap.put(initService.getVal().toUpperCase(), initService);
                                if(!classMap.containsKey(Constants.DEFAULT)){
                                    classMap.put(Constants.DEFAULT,initService);
                                }
                            }
                        }
                    }
                }
            }
        }
        //整理证书模板服务
        Map<String, InitBdcqzMbService> map = applicationContext.getBeansOfType(InitBdcqzMbService.class);
        if(MapUtils.isNotEmpty(map)){
            zsMbServices=new HashMap<>();
            for(InitBdcqzMbService initBdcqzMbService:map.values()){
                if(initBdcqzMbService.getQllx()!=null && initBdcqzMbService.getQllx().length>0){
                    //特殊版本与当前系统配置的版本号相同直接存入
                    if(StringUtils.equals(initBdcqzMbService.getVersion(),version)){
                        for(Integer qllx:initBdcqzMbService.getQllx()){
                            zsMbServices.put(qllx,initBdcqzMbService);
                        }
                    }else if(StringUtils.isBlank(initBdcqzMbService.getVersion())){
                        for(Integer qllx:initBdcqzMbService.getQllx()){
                            //不包含这个权利类型 存入标准版
                            if(!zsMbServices.containsKey(qllx)){
                                zsMbServices.put(qllx,initBdcqzMbService);
                            }
                        }
                    }
                }
            }
        }
        //整理初始化后结尾服务
        Map<String, InitBdcJwService> mapJwService = applicationContext.getBeansOfType(InitBdcJwService.class);
        if(MapUtils.isNotEmpty(mapJwService)){
            bdcJwServices=new ArrayList<>();
            for(InitBdcJwService initBdcJwService:mapJwService.values()){
                //特殊版本与当前系统配置的版本号相同或者标准版直接存入
                if(CollectionUtils.isEmpty(initBdcJwService.getVersion()) ||initBdcJwService.getVersion().contains(version)){
                    bdcJwServices.add(initBdcJwService);
                }
            }
        }

        //整理权利其他状况和附记服务
        Map<String, InitBdcqzQlqtzkFjMbService> mapQlqtzkfjervice = applicationContext.getBeansOfType(InitBdcqzQlqtzkFjMbService.class);
        if(MapUtils.isNotEmpty(mapQlqtzkfjervice)){
            zsQlqtzkfjMbServices=new ArrayList<>();
            for(InitBdcqzQlqtzkFjMbService initBdcqzQlqtzkFjMbService:mapQlqtzkfjervice.values()){
                //特殊版本与当前系统配置的版本号相同或者标准版直接存入
                if(StringUtils.equals(initBdcqzQlqtzkFjMbService.getVersion(),version) || StringUtils.isBlank(initBdcqzQlqtzkFjMbService.getVersion())){
                    zsQlqtzkfjMbServices.add(initBdcqzQlqtzkFjMbService);
                }
            }
        }
    }


    /**
     * 根据服务类型和开关表去获取对应的服务实现
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param  bdcCshFwkgSlDO
     *@param  clz
     *@return
     *@description
     */
    public  <T extends InitService> List<T> getTrafficMode(BdcCshFwkgSlDO bdcCshFwkgSlDO, Class clz) throws IllegalAccessException {
        List<T> listService =new ArrayList<>();
        Map<String, InitService> map=beanMap.get(clz.toString());
        if(MapUtils.isNotEmpty(map)){
            //无开关的实现  直接返回
            InitService initService= map.get(Constants.DEFAULT);
            if(map.size()==1 && map.containsKey(Constants.DEFAULT)){
                listService.add((T) initService);
                return listService;
            }

            Class c=BdcCshFwkgSlDO.class;
            Field field=null;
            if(StringUtils.isNotBlank(initService.getCode())){
                for (; c != Object.class; c = c.getSuperclass()) {
                    try {
                        field = c.getDeclaredField(initService.getCode());
                    } catch (NoSuchFieldException e) {
                        LOGGER.debug(messageProvider.getMessage("message.nofield"),e);
                    }
                    //取到了就停止
                    if(field!=null){
                        break;
                    }
                }
            }
            if(field!=null){
                field.setAccessible(true);
                String val=ObjectUtils.toString(field.get(bdcCshFwkgSlDO));
                if(StringUtils.isNotBlank(val)){
                    val=val.toUpperCase();
                    //权利分类特殊处理
                    if(StringUtils.equals(clz.getName(),InitBdcQlflAbstractService.class.getName())){
                        Map qllxMap = bdcDzgxConfig.getQllxMap();
                        val=qllxMap.get(val)!=null ? qllxMap.get(val).toString() : "";
                    }
                    String[] codes=val.split(",");
                    if(ArrayUtils.isNotEmpty(codes)){
                        for(String code:codes){
                            if(map.get(code)!=null){
                                listService.add((T)map.get(code));
                            }
                        }
                    }
                }
            }
        }
        return listService;
    }

    public List<String> getZsServices() {
        return zsServices;
    }

    public void setZsServices(List<String> zsServices) {
        this.zsServices = zsServices;
    }

    public List<String> getTshzServices() {
        return tshzServices;
    }

    public void setTshzServices(List<String> tshzServices) {
        this.tshzServices = tshzServices;
    }

    public List<Class> getList() {
        return list;
    }

    public void setList(List<Class> list) {
        this.list = list;
    }

    public List<Class> getInitServices() {
        return initServices;
    }

    public void setInitServices(List<Class> initServices) {
        this.initServices = initServices;
    }

    public List<Class> getDeleteServices() {
        return deleteServices;
    }

    public void setDeleteServices(List<Class> deleteServices) {
        this.deleteServices = deleteServices;
    }

    public boolean isCoverDjb() {
        return coverDjb;
    }

    public void setCoverDjb(boolean coverDjb) {
        this.coverDjb = coverDjb;
    }

    public boolean isLpbsjCoverDjb() {
        return lpbsjCoverDjb;
    }

    public void setLpbsjCoverDjb(boolean lpbsjCoverDjb) {
        this.lpbsjCoverDjb = lpbsjCoverDjb;
    }

    public boolean isInitFj() {
        return initFj;
    }

    public void setInitFj(boolean initFj) {
        this.initFj = initFj;
    }

    public String getFjWz() {
        return fjWz;
    }

    public void setFjWz(String fjWz) {
        this.fjWz = fjWz;
    }

    public boolean isHxFj() {
        return hxFj;
    }

    public void setHxFj(boolean hxFj) {
        this.hxFj = hxFj;
    }

    public boolean isDqXmQlqtzk() {
        return dqXmQlqtzk;
    }

    public void setDqXmQlqtzk(boolean dqXmQlqtzk) {
        this.dqXmQlqtzk = dqXmQlqtzk;
    }

    public Map<Integer, InitBdcqzMbService> getZsMbServices() {
        return zsMbServices;
    }

    public List<InitBdcJwService> getBdcJwServices() {
        return bdcJwServices;
    }

    public List<InitBdcqzQlqtzkFjMbService> getZsQlqtzkfjMbServices() {
        return zsQlqtzkfjMbServices;
    }

    public boolean isFbczSfxsczr() {
        return fbczSfxsczr;
    }

    public void setFbczSfxsczr(boolean fbczSfxsczr) {
        this.fbczSfxsczr = fbczSfxsczr;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}


