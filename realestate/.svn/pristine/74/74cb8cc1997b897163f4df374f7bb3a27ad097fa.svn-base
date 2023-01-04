package cn.gtmap.realestate.common.util.configuration;

import cn.gtmap.gtc.clients.AuthorityManagerClient;
import cn.gtmap.gtc.sso.domain.dto.AuthorityDto;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.define.v1.StartUpSettingClient;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.dto.BdcJsPzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJsPzFeignService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/08/19
 * @description 按区划区分配置场景，获取对应区划配置处理类
 */
@Service
public class BdcConfigUtils {
    private static Logger logger = LoggerFactory.getLogger(BdcConfigUtils.class);

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  过滤方式：角色
      */
    private static final String GLFS_JSDZ = "jsdz";

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  过滤方式：组织机构对照
     */
    private static final String GLFS_ZZJGDZ = "zzjgdz";


    @Autowired
    private List<IBdcQjgldmQuery> qjgldmQueries;

    @Resource(name = "bdcQjgldmYhQuerier")
    private IBdcQjgldmQuery qjgldmYhQuerier;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    BdcJsPzFeignService bdcJsPzFeignService;

    @Autowired
    StartUpSettingClient startUpSettingClient;

    @Autowired
    AuthorityManagerClient authorityManagerClient;


    /**
     * 系统配置项是否区分权籍管理代码（如果需要的话需对应应用设置，现场Apollo设置全局公共配置）
     */
    @Value("${config.qfqjgldm:false}")
    private Boolean qfqjgldm;

    /**
     * 系统配置项是否进行区县代码过滤（如果需要的话需对应应用设置，现场Apollo设置全局公共配置）
     */
    @Value("${config.qxdmfilter:false}")
    private Boolean qxdmfilter;

    /**
     * 无需进行区域过滤的角色
     */
    @Value("#{'${qxdm.nofilter.code:}'.split(',')}")
    private List<String> qxdmNoFilterCode;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记部门代码来源 1：用户当前部门 2：用户部门所属父级
     */
    @Value("${config.djbmdmly:2}")
    private String djbmdmly;

    /**
     * 区县代码过滤对照，key为用户组织机构代码，value为需要查询的区县代码，多个用英文逗号隔开，不配对照默认组织机构代码即为区县代码
     * 配置示例：{'320400':'320401,320402,320411'}
     */
    @Value("#{${qxdmfilter.zzjgQxdm:null}}")
    private Map<String, String> zzjgQxdm;

    /**
     * 区县代码过滤，按照角色对照时,是否要区分页面（同一用户不同页面权限不同）
     */
    @Value("${config.jsdz.qfym:false}")
    private Boolean qfym;

    /**
     * 系统配置项是否进行权籍管理代码过滤
     */
    @Value("${config.qjgldmfilter:false}")
    private Boolean qjgldmfilter;

    /**
     * 无需进行区域过滤的角色
     */
    @Value("#{'${qjgldm.nofilter.code:}'.split(',')}")
    private List<String> qjgldmNoFilterCode;


    /**
     * 权籍管理代码过滤对照，key为用户组织机构代码，value为需要查询的权籍管理代码，多个用英文逗号隔开，不配对照默认组织机构代码即为权籍管理代码
     * 配置示例：{'320400':'320401,320402,320411'}
     */
    @Value("#{${qjgldmfilter.zzjgQjgldm:null}}")
    private Map<String, String> zzjgQjgldm;


    /**
     * 根据权籍管理代码获取指定区划配置
     * @param configName 当前配置项内容（例如 config.qfqjgldm）
     * @param defaultValue 配置项默认配置值 （例如 config.qfqjgldm = true，则当前参数传 true）
     * @param qjgldm 权籍管理代码（如果调用端能直接获取到权籍管理代码，建议直接传递）
     * @param bdcQjgldmQO 业务数据（业务场景没有权籍管理代码，需要根据办理的件等信息判断权籍管理代码）
     * @return {Object} 对应区划配置项值，例如 （config.qfqjgldm.340124 = false，返回fasle）
     */
    public Object getConfigValueByQjgldm(String configName, Object defaultValue, String qjgldm, BdcQjgldmQO bdcQjgldmQO) {
        if(!qfqjgldm || StringUtils.isBlank(configName)) {
            logger.info("获取指定区划配置项{}值，返回默认设值{}，因为：系统无需按区划配置、未声明配置项", configName, defaultValue);
            return defaultValue;
        }

        try {
            // 1、获取权籍管理代码
            if (StringUtils.isNotBlank(qjgldm)) {
                logger.info("获取指定区划配置项{}值，调用端已传参权籍管理代码{}", configName, qjgldm);
            } else {
                qjgldm = this.getQjgldm(configName, bdcQjgldmQO);
                if (StringUtils.isBlank(qjgldm)) {
                    logger.info("获取指定区划配置项{}值，返回默认设值{}，因为：未传值权籍管理代码且未从登记库、权籍库、用户信息判断出权籍管理代码", configName, defaultValue);
                    return defaultValue;
                }
            }

            // 2、根据权籍管理代码获取对应地区配置
            Object configValue = this.getConfigValue(configName, defaultValue, qjgldm);
            return null == configValue ? defaultValue : configValue;
        } catch (Exception e) {
            logger.error("获取指定区划配置项{}值，发生异常，返回默认设值{}", configName, defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * 判断当前场景所属权籍管理代码
     * @param configName 配置项内容
     * @param bdcQjgldmQO 业务数据
     * @return {String} 权籍管理代码
     */
    private String getQjgldm(String configName, BdcQjgldmQO bdcQjgldmQO) {
        if(!CheckParameter.checkAnyParameter(bdcQjgldmQO)) {
            // 如果没有业务数据，直接根据当前用户判断
            return qjgldmYhQuerier.queryBdcQjgldm(configName, bdcQjgldmQO);
        }

        // 依次根据登记、权籍、用户判断当前权籍管理代码
        for(IBdcQjgldmQuery querier : qjgldmQueries) {
            String qjgldm = querier.queryBdcQjgldm(configName, bdcQjgldmQO);
            if(StringUtils.isNotBlank(qjgldm)) {
                return qjgldm;
            }
        }
        return null;
    }

    /**
     * 获取指定权籍管理代码配置值
     * @param configName 当前配置项内容
     * @param defaultValue 配置项默认配置值
     * @param qjgldm 权籍管理代码
     * @return {Object} 配置项值
     */
    private Object getConfigValue(String configName, Object defaultValue, String qjgldm) {
        Object configValue;

        // 原配置例如 person.name = 小明 ，区划子配置 person.name.340124 = 小华
        if(null == defaultValue) {
            configValue = EnvironmentConfig.getEnvironment().getProperty(configName + "." + qjgldm);
        } else {
            configValue = EnvironmentConfig.getEnvironment().getProperty(configName + "." + qjgldm, defaultValue.getClass());
        }

        logger.info("获取指定区划配置项{}值，区划代码{}对应配置项{}值{}", configName, qjgldm, configName + "." + qjgldm, configValue);
        return configValue;
    }

    /**
     * 获取配置项值，defaultValue为null返回String类型，不为null，返回defaultValue对应类型
     * @param configName 当前配置项内容
     * @param defaultValue 配置项默认配置值
     * @return {Object} 配置项值
     */
    private Object getConfigValue2(String configName, Object defaultValue) {
        Object configValue;

        if(null == defaultValue) {
            configValue = EnvironmentConfig.getEnvironment().getProperty(configName);
        } else {
            configValue = EnvironmentConfig.getEnvironment().getProperty(configName, defaultValue.getClass());
        }
        return configValue;
    }

    /**
     * 根据qxdmfilter.fs.cxlx配置项判断按角色区县代码配置还是按照用户组合机构代码对照获取区县代码过滤
     * @param cxlx 查询类型
     * @param gzldyid config.jsdz.qfym配置true且过滤方式为角色对照时,gzldyid有值,获取流程启动角色和当前用户的交集进行角色过滤
     * @param moduleCode config.jsdz.qfym配置true且过滤方式为角色对照时,gzldyid为空且moduleCode有值,获取模块访问角色和当前用户的交集进行角色过滤
     * @return 区县代码集合,查看全部返回空集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 区县代码过滤
     */
    public List<String> qxdmFilter(String cxlx,String gzldyid,String moduleCode){
        List<String> qxdmFilterList =new ArrayList<>();
        if(Boolean.FALSE.equals(qxdmfilter)){
            return qxdmFilterList;
        }

        UserDto currentUser = userManagerUtils.getCurrentUser();
        if(currentUser ==null){
            //用户信息为空
            logger.error("区县代码过滤,当前用户不存在,不进行过滤,cxlx:{}",cxlx);
            return qxdmFilterList;
        }
        // 管理员不做限制处理
        if (currentUser.getAdmin() == 1) {
            return qxdmFilterList;
        }
        //判断当前用户角色是否需要过滤
        List<String> roleRecordList =null;

        if(CollectionUtils.isNotEmpty(currentUser.getRoleRecordList())){
            roleRecordList = currentUser.getRoleRecordList().stream()
                    .map(RoleDto::getName).collect(Collectors.toList());
            for (String role : qxdmNoFilterCode) {
                if (StringUtils.isNotBlank(role) &&roleRecordList.contains(role)) {
                    //存在查看全部权限，无需进行区县过滤
                    logger.info("用户：{}存在查看全部权限:{},无需区县过滤",currentUser.getUsername(),role);
                    return qxdmFilterList;
                }
            }
        }

        //获取区县代码过滤方式
        String glfs =getQxdmGlfs(cxlx);
        if(StringUtils.equals(GLFS_JSDZ,glfs)){
            //角色对照
            if(CollectionUtils.isEmpty(roleRecordList)){
                logger.error("区县代码过滤,当前用户没有关联角色:{}",currentUser);
                throw new AppException("当前用户没有角色,无法通过角色查询数据");
            }
     
            if(Boolean.TRUE.equals(qfym)){
                roleRecordList =glJs(gzldyid,moduleCode,currentUser.getUsername(),roleRecordList);
            }
            qxdmFilterList =bdcJsPzFeignService.listQxdmByRoleCodeList(roleRecordList);

        }else if(StringUtils.equals(GLFS_ZZJGDZ,glfs)){
            //普通用户只允许查看所属区域数据
            //部门所属父级
            List<OrganizationDto> organizationDtos =null;
            if (StringUtils.equals("2", djbmdmly)) {
                organizationDtos = userManagerUtils.listUserParentOrgs(currentUser.getUsername());
            }
            if(CollectionUtils.isNotEmpty(organizationDtos)){
                qxdmFilterList =getOrganizationQxdmFilter(organizationDtos,true);
            }else if (CollectionUtils.isNotEmpty(currentUser.getOrgRecordList())) {
                qxdmFilterList =getOrganizationQxdmFilter(currentUser.getOrgRecordList(),true);
            }
        }
        if(CollectionUtils.isEmpty(qxdmFilterList)){
            //没有部门
            logger.error("区县代码过滤,未获取到可查询的区县代码集合，当前用户没有关联部门或组织编码为空:{} 过滤方式:{}",currentUser,glfs);
            throw new AppException("当前用户"+currentUser.getUsername()+"未获取到可查询的区县代码集合,无法查询数据,过滤方式:"+glfs);
        }
        logger.info("当前用户:{}过滤方式：{}区县代码过滤：{}",currentUser.getUsername(),glfs,qxdmFilterList);
        return qxdmFilterList;

    }

    /**
     * @return 权籍管理代码集合,查看全部返回空集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 权籍管理代码过滤
     */
    public List<String> qjgldmFilter(String cxlx){
        List<String> qjgldmFilterList =new ArrayList<>();
        if(Boolean.FALSE.equals(qjgldmfilter)){
            return qjgldmFilterList;
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if(currentUser ==null){
            //用户信息为空
            logger.error("权籍管理代码过滤,当前用户不存在");
            throw new AppException("当前用户不存在,无法查询数据");
        }
//        // 管理员不做限制处理
        if (currentUser.getAdmin() == 1) {
            return qjgldmFilterList;
        }
        //判断当前用户角色是否需要过滤
        if(CollectionUtils.isNotEmpty(currentUser.getRoleRecordList())){
            List<String> roleRecordList = currentUser.getRoleRecordList().stream()
                    .map(RoleDto::getName).collect(Collectors.toList());
            for (String role : qjgldmNoFilterCode) {
                if (StringUtils.isNotBlank(role) &&roleRecordList.contains(role)) {
                    //存在查看全部权限，无需进行区县过滤
                    return qjgldmFilterList;
                }
            }
        }
        //获取区县代码过滤方式
        String glfs =getQjgldmGlfs(cxlx);
        if(StringUtils.equals(GLFS_JSDZ,glfs)){

            BdcJsPzDTO bdcJsPzDTO =bdcJsPzFeignService.queryJsPzDTOByUserId(currentUser);
            if(bdcJsPzDTO != null){
                List<BdcZdQjgldmDO> bdcZdQjgldmDOS=bdcJsPzDTO.getQjgldmList();
                if(CollectionUtils.isNotEmpty(bdcZdQjgldmDOS)){
                    for(BdcZdQjgldmDO zdQjgldmDO:bdcZdQjgldmDOS){
                        qjgldmFilterList.add(zdQjgldmDO.getDm());
                    }
                }
            }
        }else if(StringUtils.equals(GLFS_ZZJGDZ,glfs)){
            //普通用户只允许查看所属区域数据
            //部门所属父级
            List<OrganizationDto> organizationDtos =null;
            if (StringUtils.equals("2", djbmdmly)) {
                organizationDtos = userManagerUtils.listUserParentOrgs(currentUser.getUsername());
            }
            if(CollectionUtils.isNotEmpty(organizationDtos)){
                qjgldmFilterList =getOrganizationQjgldmFilter(organizationDtos);

            }else if (CollectionUtils.isNotEmpty(currentUser.getOrgRecordList())) {
                qjgldmFilterList =getOrganizationQjgldmFilter(currentUser.getOrgRecordList());
            }
        }
        if(CollectionUtils.isEmpty(qjgldmFilterList)){
            //没有部门
            logger.error("权籍管理代码过滤,当前用户没有关联部门或组织编码为空:{}",currentUser);
            throw new AppException("当前用户没有关联部门或组织编码,无法查询数据");
        }
        logger.info("当前用户:{}权籍管理代码过滤：{}",currentUser.getUsername(),qjgldmFilterList);
        return qjgldmFilterList;

    }

    /**
     * @param qxdmdz 是否需要对照
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取组织区县代码过滤
     */
    private List<String> getOrganizationQxdmFilter(List<OrganizationDto> organizationDtos,Boolean qxdmdz){
        List<String> qxdmFilterList =new ArrayList<>();
        for(OrganizationDto organizationDto:organizationDtos) {
            if(StringUtils.isNotBlank(organizationDto.getRegionCode())) {
                if(Boolean.TRUE.equals(qxdmdz) &&MapUtils.isNotEmpty(zzjgQxdm) &&zzjgQxdm.containsKey(organizationDto.getRegionCode())){
                    String qxdms =zzjgQxdm.get(organizationDto.getRegionCode());
                    if(StringUtils.isNotBlank(qxdms)){
                        for(String qxdm:qxdms.split(CommonConstantUtils.ZF_YW_DH)){
                            if(StringUtils.isNotBlank(qxdm)){
                                qxdmFilterList.add(qxdm);
                            }
                        }
                    }
                }else {
                    qxdmFilterList.add(organizationDto.getRegionCode());
                }
            }
        }
        return qxdmFilterList;
    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取组织权籍管理代码过滤
     */
    private List<String> getOrganizationQjgldmFilter(List<OrganizationDto> organizationDtos){
        List<String> qjgldmFilterList =new ArrayList<>();
        for(OrganizationDto organizationDto:organizationDtos) {
            if(StringUtils.isNotBlank(organizationDto.getRegionCode())) {
                if(MapUtils.isNotEmpty(zzjgQjgldm) &&zzjgQjgldm.containsKey(organizationDto.getRegionCode())){
                    String qjgldms =zzjgQjgldm.get(organizationDto.getRegionCode());
                    if(StringUtils.isNotBlank(qjgldms)){
                        for(String qjgldm:qjgldms.split(CommonConstantUtils.ZF_YW_DH)){
                            if(StringUtils.isNotBlank(qjgldm)){
                                qjgldmFilterList.add(qjgldm);
                            }
                        }
                    }
                }else {
                    qjgldmFilterList.add(organizationDto.getRegionCode());
                }
            }
        }
        return qjgldmFilterList;
    }

    /**
     * @return 权籍管理代码：当角色配置获取不到通过用户组织判断权籍库集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 区县代码过滤
     */
    public List<String> qxdmFilterWithUser(UserDto currentUser){
        List<String> qjgldmList =new ArrayList<>();
        if(Boolean.FALSE.equals(qxdmfilter)){
            return qjgldmList;
        }
        if(currentUser ==null){
            //用户信息为空
            logger.error("根据区县代码配置权籍管理代码过滤,当前用户不存在");
            throw new AppException("根据区县代码配置权籍管理代码过滤，当前用户不存在,无法查询数据");
        }
        // 管理员不做限制处理
        if (currentUser.getAdmin() == 1) {
            return qjgldmList;
        }
        //判断当前用户角色是否需要过滤
        if(CollectionUtils.isNotEmpty(currentUser.getRoleRecordList())){
            List<String> roleRecordList = currentUser.getRoleRecordList().stream()
                    .map(RoleDto::getName).collect(Collectors.toList());
            for (String role : qxdmNoFilterCode) {
                if (StringUtils.isNotBlank(role) &&roleRecordList.contains(role)) {
                    //存在查看全部权限，无需进行区县过滤
                    return qjgldmList;
                }
            }
        }

        //普通用户只允许查看所属区域数据
        //部门所属父级
        List<OrganizationDto> organizationDtos = userManagerUtils.listUserParentOrgs(currentUser.getUsername());
        if(CollectionUtils.isNotEmpty(organizationDtos)){
            qjgldmList =getOrganizationQxdmFilter(organizationDtos,false);

        }else if (CollectionUtils.isNotEmpty(currentUser.getOrgRecordList())) {
            qjgldmList =getOrganizationQxdmFilter(currentUser.getOrgRecordList(),false);
        }
        if(CollectionUtils.isEmpty(qjgldmList)){
            //没有部门
            logger.error("根据区县代码配置权籍管理代码过滤,当前用户没有关联部门或组织编码为空:{}",currentUser);
            throw new AppException("根据区县代码配置权籍管理代码过滤，当前用户没有关联部门或组织编码,无法查询数据");
        }
        logger.info("当前用户:{}根据区县代码配置权籍管理代码过滤：{}",currentUser.getUsername(),qjgldmList);
        return qjgldmList;

    }

    /**
     * 根据区县代码获取指定区划配置
     * @param configName 当前配置项内容（例如 fs.config.beanName）
     * @param defaultValue 配置项默认配置值 （例如 fs.config.beanName = test，则当前参数传 test）
     * @param qxdm 区县代码
     * @return {Object} 对应区划配置项值，例如 （fs.config.beanName.340124 = test2，返回test2）
     */
    public Object getConfigValueByQxdm(String configName, Object defaultValue, String qxdm) {
        if(StringUtils.isBlank(configName) ||StringUtils.isBlank(qxdm)) {
            logger.info("获取指定区划配置项{}值，返回默认设值{}，因为：区县代码为空、未声明配置项", configName, defaultValue);
            return defaultValue;
        }

        try {
            // 2、根据区县代码获取对应地区配置
            Object configValue = this.getConfigValue(configName, defaultValue, qxdm);
            return null == configValue ? defaultValue : configValue;
        } catch (Exception e) {
            logger.error("获取区县代码指定区划配置项{}值，发生异常，返回默认设值{}", configName, defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * 根据区县代码获取指定区划配置
     * @param configName 当前配置项内容（例如 fs.config.beanName）
     * @param defaultValue 配置项默认配置值 （例如 fs.config.beanName = test，则当前参数传 test）
     * @param qxdm 区县代码
     * @return {Object} 对应区划配置项值，例如 （fs.config.beanName.340124 = test2，fs.config.beanName = test1，如果区县代码为340124，返回test2；区县代码为空，返回test1；区县代码不等于340124，返回test1）
     */
    public Object getConfigValueByQxdm2(String configName, Object defaultValue, String qxdm) {
        if(StringUtils.isBlank(configName)) {
            logger.info("获取配置项{}值，返回默认设值{}，因为：未声明配置项", configName, defaultValue);
            return defaultValue;
        }

        try {
            Object configValue;
            if (StringUtils.isNotBlank(qxdm)){
                configValue = this.getConfigValue2(configName+"."+qxdm, defaultValue);
                if (configValue == null){
                    configValue = this.getConfigValue2(configName, defaultValue);
                }
            }else {
                configValue = this.getConfigValue2(configName, defaultValue);
            }
            return null == configValue ? defaultValue : configValue;
        } catch (Exception e) {
            logger.error("获取区县代码指定区划配置项{}值，发生异常，返回默认设值{}", configName, defaultValue, e);
            return defaultValue;
        }
    }

    /**
     * @param qxdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据指定区县代码获取配置得区县代码（常州地区）
     * @date : 2022/1/10 18:11
     */
    public String getQxdm(String qxdm) {
        if (StringUtils.isNotBlank(qxdm) && MapUtils.isNotEmpty(zzjgQxdm) && zzjgQxdm.containsKey(qxdm)) {
            String qxdms = zzjgQxdm.get(qxdm);
            if (StringUtils.isNotBlank(qxdms)) {
                return qxdms;
            }
        }
        return qxdm;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  获取区县代码过滤方式
      */
    private String getQxdmGlfs(String cxlx){
        //优先根据查询类型配置，如果没有配置默认按照组织机构过滤
        String glfs =EnvironmentConfig.getEnvironment().getProperty("qxdmfilter.fs."+cxlx);
        if(StringUtils.isBlank(glfs)){
            glfs =GLFS_ZZJGDZ;
        }
        return glfs;

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取权籍管理代码过滤方式
     */
    private String getQjgldmGlfs(String cxlx){
        //优先根据查询类型配置，如果没有配置默认按照组织机构过滤
        String glfs =EnvironmentConfig.getEnvironment().getProperty("qjgldmfilter.fs."+cxlx);
        if(StringUtils.isBlank(glfs)){
            glfs =GLFS_ZZJGDZ;
        }
        return glfs;
    }

    /**
     * @param gzldyid
     * @param moduleCode
     * @param username
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据流程定义ID或者模块编码过滤角色
     */
    private List<String> glJs(String gzldyid,String moduleCode,String username,List<String> roleRecordList){
        //gzldyid有值,获取流程启动角色和当前用户的交集进行角色过滤
        //gzldyid为空且moduleCode有值,获取模块访问角色和当前用户的交集进行角色过滤
        if(StringUtils.isNotBlank(gzldyid)){
            List<RoleDto> roleDtoList =startUpSettingClient.getProcessStartRoleInfoFroUser(gzldyid,username);
            if(CollectionUtils.isNotEmpty(roleDtoList)){
                roleRecordList =roleDtoList.stream().filter(t -> StringUtils.isNotBlank(t.getName())).map(RoleDto::getName).collect(Collectors.toList());
            }else{
                logger.error("根据流程定义ID或者模块编码过滤角色后角色集合为空,gzldyid:{},moduleCode:{},过滤前角色结合:{}", gzldyid, moduleCode, roleRecordList);
                throw new AppException("当前用户没有符合条件的角色,无法通过角色查询数据");
            }
        }else if(StringUtils.isNotBlank(moduleCode)){
            List<RoleDto> roleDtoList =authorityManagerClient.hasAuthRoles(username,moduleCode);
            if(CollectionUtils.isNotEmpty(roleDtoList)){
                roleRecordList =roleDtoList.stream().filter(t -> StringUtils.isNotBlank(t.getName())).map(RoleDto::getName).collect(Collectors.toList());
            }else{
                logger.error("根据流程定义ID或者模块编码过滤角色后角色集合为空,gzldyid:{},moduleCode:{},过滤前角色结合:{}", gzldyid, moduleCode, roleRecordList);
                throw new AppException("当前用户没有符合条件的角色,无法通过角色查询数据");
            }
        }
        logger.info("用户：{}根据流程定义ID或者模块编码过滤角色后角色集合：{}",username,roleRecordList);
        return roleRecordList;
    }
}
