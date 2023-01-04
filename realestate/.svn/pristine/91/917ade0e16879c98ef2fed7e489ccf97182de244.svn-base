package cn.gtmap.realestate.inquiry.core.aop;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZszmDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.RegisterWorkflowRestService;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @param
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @return
 * @description
 */
@Component
@Aspect
public class InquiryAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(InquiryAspect.class);
    private static final String CLASS_NAME = InquiryAspect.class.getName();
    @Autowired
    RegisterWorkflowRestService registerWorkflowRestService;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    
    
    @Pointcut("execution(* cn.gtmap.realestate.inquiry.service.BdcSdService.sdBdcdy(..))")
    public void sdBdcdy() {
    }
    
    @Pointcut("execution(* cn.gtmap.realestate.inquiry.service.BdcSdService.jsBdcdy(..))")
    public void jsBdcdy() {
    }
    
    @Pointcut("execution(* cn.gtmap.realestate.inquiry.service.BdcSdService.sdBdczs(..))")
    public void sdBdczs() {
    }
    
    @Pointcut("execution(* cn.gtmap.realestate.inquiry.service.BdcSdService.jsBdczs(..))")
    public void jsBdczs() {
    }

    /**
     * @param joinPoint
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 锁定单元号执行后，同步权籍单元号锁定信息
     */
    @AfterReturning("sdBdcdy()")
    public void synBdcdySdztSd(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<BdcDysdDO> bdcDysdDOList = (List<BdcDysdDO>) args[0];
        if (CollectionUtils.isEmpty(bdcDysdDOList)) {
            LOGGER.debug("{}：同步权籍锁定状态信息 ：缺少参数bdcDysdDO", CLASS_NAME);
            throw new MissingArgumentException("bdcDysdDO");
        }
        List<String> bdcdyList = new ArrayList();
        for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
            LOGGER.debug("{}：同步权籍锁定状态信息 ：{}", CLASS_NAME, bdcDysdDO.toString());

            bdcdyList.add(bdcDysdDO.getBdcdyh());
        }
        // 权籍同步锁定状态
        bdcDbxxFeignService.synQjBdcdyztSd(bdcdyList, CommonConstantUtils.SDZT_SD);
    }
    
    /**
     * @param joinPoint
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 解锁单元号执行后，同步权籍单元号锁定信息
     */
    @AfterReturning("jsBdcdy()")
    public void synBdcdySdztJs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<BdcDysdDO> bdcDysdDOList = (List<BdcDysdDO>) args[0];
        if (CollectionUtils.isEmpty(bdcDysdDOList)) {
            LOGGER.debug("{}：同步权籍锁定状态信息 ：缺少参数bdcDysdDO为空", CLASS_NAME);
            throw new MissingArgumentException("bdcDysdDO");
        }
    
        List<String> bdcdyList = new ArrayList();
        for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
            LOGGER.debug("{}：同步权籍锁定状态信息 ：{}", CLASS_NAME, bdcDysdDO.toString());
            
            bdcdyList.add(bdcDysdDO.getBdcdyh());
        }
        // 权籍同步锁定状态
        bdcDbxxFeignService.synQjBdcdyztSd(bdcdyList, CommonConstantUtils.SDZT_JS);
    }
    
    /**
     * @param joinPoint
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 锁定证书后，同步权籍单元号锁定信息
     */
    @AfterReturning("sdBdczs()")
    public void synBdczsSdztSd(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<BdcZssdDO> bdcZssdDOList = (List<BdcZssdDO>) args[0];
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            LOGGER.debug("{}：同步权籍锁定状态信息 ：缺少参数bdcZssdDO，为空", CLASS_NAME);
            throw new MissingArgumentException("BdcZssdDO");
        }
        LOGGER.debug("{}：同步权籍锁定状态信息 ：{}", CLASS_NAME, bdcZssdDOList.toString());
        
        List<String> bdcdyList = new ArrayList();
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            if (StringUtils.isBlank(bdcZssdDO.getZsid())) {
                throw new MissingArgumentException("bdcZssdDO.getZsid()");
            }
            // 获取相关的项目信息
            List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZssdDO.getZsid());
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new MissingArgumentException("证书ID：" + bdcZssdDO.getZsid() + "未查询到对应的项目信息!");
            }
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
                    bdcdyList.add(bdcXmDO.getBdcdyh());
                }
            }
        }
        // 当解锁的数据都不需要同步权藉时 不进行操作
        if (CollectionUtils.isEmpty(bdcdyList)){
            return;
        }
        // 权籍同步证书锁定状态
        bdcDbxxFeignService.synQjBdcdyztSd(bdcdyList, CommonConstantUtils.SDZT_SD);
    }
    
    /**
     * @param joinPoint
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 解锁证书后，同步权籍单元号锁定信息
     */
    @AfterReturning("jsBdczs()")
    public void synBdczsSdztJs(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<BdcZssdDO> bdcZssdDOList = (List<BdcZssdDO>) args[0];
        if (CollectionUtils.isEmpty(bdcZssdDOList)) {
            LOGGER.debug("{}：同步权籍锁定状态信息 ：缺少参数bdcZssdDO，为空", CLASS_NAME);
            throw new MissingArgumentException("BdcZssdDO");
        }
        LOGGER.debug("{}：同步权籍锁定状态信息 ：{}", CLASS_NAME, bdcZssdDOList.toString());
        
        List<String> bdcdyList = new ArrayList();
        for (BdcZssdDO bdcZssdDO : bdcZssdDOList) {
            if (StringUtils.isBlank(bdcZssdDO.getZsid())) {
                throw new MissingArgumentException("bdcZssdDO.getZsid()");
            }
            // 获取相关的项目信息
            List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZssdDO.getZsid());
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new MissingArgumentException("证书ID：" + bdcZssdDO.getZsid() + "未查询到对应的项目信息!");
            }
            
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
                    bdcdyList.add(bdcXmDO.getBdcdyh());
                }
            }
        }
        // 权籍同步证书锁定状态
        bdcDbxxFeignService.synQjBdcdyztSd(bdcdyList, CommonConstantUtils.SDZT_JS);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 保存综合查询的结果日志信息
     */
    @AfterReturning(value = "execution(* cn.gtmap.realestate.inquiry.service.BdcZszmCxService.listBdcZszm(..))", returning = "result")
    public void saveZhcxLog(JoinPoint joinPoint, Object result){
        List<Object> args = Arrays.asList(joinPoint.getArgs());

        Map<String, Object> map = new HashMap<>(4);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, "综合查询日志");

        // 获取用户名称
        UserDto userDto = userManagerUtils.getCurrentUser();
        String userAlias = userDto == null ? "" : userDto.getAlias();
        String userName = userDto == null ? "" : userDto.getUsername();
        // 用户名称
        map.put(CommonConstantUtils.ALIAS, userAlias);
        // 用户部门
        map.put(CommonConstantUtils.ORGANIZATION, userManagerUtils.getYhBmmc());
        try {
            if(CollectionUtils.isNotEmpty(args) && null != args.get(1)){
                BdcZszmQO bdcZszmQO = (BdcZszmQO) args.get(1);
                // 查询条件
                String cxtj = BeansResolveUtils.toNotNullPropertyJSON(bdcZszmQO);
                map.put(CommonConstantUtils.CXTJ, cxtj);
                map.put("ZHCXTJ", cxtj);
                // IP地址
                map.put(CommonConstantUtils.IPADDRESS, bdcZszmQO.getIpaddress());
                map.put(CommonConstantUtils.SLBH, bdcZszmQO.getSlbh());
                map.put(CommonConstantUtils.ZL, bdcZszmQO.getZl());
                map.put(CommonConstantUtils.BDCDYH, bdcZszmQO.getBdcdyh());
                map.put(CommonConstantUtils.QLR, bdcZszmQO.getQlrmc());
                map.put(CommonConstantUtils.YWR, bdcZszmQO.getYwrmc());
            }

            // 查询结果
            String res = BeansResolveUtils.toNotNullPropertyListJSON(((Page<BdcZszmDTO>) result).getContent());
            map.put(CommonConstantUtils.CXJG, res);
            map.put("response", res);

            AuditEvent auditEvent = new AuditEvent(userName, CommonConstantUtils.LOG_EVENT_ZHCX, map);
            zipkinAuditEventRepository.newSpanTag(auditEvent, CommonConstantUtils.LOG_EVENT_ZHCX);
        }catch(Exception e){
            LOGGER.error("保存综合查询日志出错：{}", e.getMessage());
        }
    }

}
