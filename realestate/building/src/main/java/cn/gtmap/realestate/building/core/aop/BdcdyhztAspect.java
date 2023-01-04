package cn.gtmap.realestate.building.core.aop;

import cn.gtmap.realestate.building.service.bdcdyhzt.BdcdyhZtAbstractService;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-26
 * @description 更新BDCDYH ZT表的 拦截器
 */
@Aspect
@Component
public class BdcdyhztAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcdyhztAspect.class);

    private static final String PC_PREFIX = "execution(public * cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper.";

    private static final String PC_SUFFIX = "(..))";

    private static final String INSERT_SELECTIVE = PC_PREFIX + "insertSelective" + PC_SUFFIX;

    private static final String INSERT_BATCH_SELECTIVE = PC_PREFIX + "insertBatchSelective" + PC_SUFFIX;

    private static final String UPDATE_BYKEY_NULL = PC_PREFIX + "updateByPrimaryKeyNull" + PC_SUFFIX;

    private static final String UPDATE_BYKEY_SELECTIVE = PC_PREFIX + "updateByPrimaryKeySelective" + PC_SUFFIX;

    private static final String DELETE = PC_PREFIX + "delete" + PC_SUFFIX;

    private static final String DELETE_BYPK = PC_PREFIX + "deleteByPrimaryKey" + PC_SUFFIX;

    @Resource(name = "defaultBdcdyhZtServiceImpl")
    private BdcdyhZtAbstractService defaultService;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param joinPoint
     * @return java.lang.Object
     * @description 更新实体场景
     */
    @Around("execution(public * cn.gtmap.realestate.common.core.service.EntityService.updateByJsonEntity(..))")
    public Object updateByJson(ProceedingJoinPoint joinPoint) throws Throwable {
        SSjBdcdyhxsztDO oldXszt = null;
        SSjBdcdyhxsztDO newXszt = null;
        BdcdyhZtAbstractService service = null;
        Object entity = null;
        Object result = 0;
        try {
            Object[] args = joinPoint.getArgs();
            Object json = args[0];
            Class entityClass = (Class) args[1];
            if (json != null && entityClass != null) {
                entity = JSON.parseObject(json.toString(), entityClass);
                service = defaultService.getBean(entityClass.getName());
                if(service != null){
                    // 执行更新前 获取 原有XSZT表中的数据 和构造新的 XSZT实体
                    oldXszt = service.getOldXszt(entity);
                    newXszt = service.structureXszt(entity);
                }
            }
        }catch (Exception e){
            LOGGER.error("updateByJson处理BDCDYHZT异常：",e);
        }
        result = joinPoint.proceed();
        try{
            if(service != null){
                // 更新XSZT
                service.updateZt(entity,oldXszt,newXszt,true);
            }
        }catch (Exception e){
            LOGGER.error("updateByJson处理BDCDYHZT异常：",e);
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 单个新增
     */
    @AfterReturning(INSERT_SELECTIVE)
    public void insertSelective(JoinPoint joinPoint){
        try{
            Object[] args = joinPoint.getArgs();
            if(args[0] != null){
                BdcdyhZtAbstractService service = defaultService.getBean(args[0].getClass().getName());
                if(service != null){
                    service.insertZt(args[0]);
                }
            }
        }catch (Exception e){
            LOGGER.error("insertSelective处理BDCDYHZT异常：",e);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 批量新增
     */
    @AfterReturning(INSERT_BATCH_SELECTIVE)
    public void insertBatchSelective(JoinPoint joinPoint){
        try{
            Object[] args = joinPoint.getArgs();
            if(args[0] != null && args[0] instanceof List){
                List list = (List) args[0];
                if(CollectionUtils.isNotEmpty(list)){
                    BdcdyhZtAbstractService service = defaultService.getBean(list.get(0).getClass().getName());
                    if(service != null){
                        for(Object record : list){
                            service.insertZt(record);
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("insertBatchSelective处理BDCDYHZT异常：",e);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 更新 NULL
     */
    @Around(UPDATE_BYKEY_NULL)
    public Object updateByPrimaryKeyNull(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object record = args[0];
        Object result = 0;
        if(record != null){
            BdcdyhZtAbstractService service = null;
            SSjBdcdyhxsztDO oldXszt = null;
            SSjBdcdyhxsztDO newXszt = null;
            try{
                service = defaultService.getBean(record.getClass().getName());
                if(service != null){
                    // 执行更新前 获取 原有XSZT表中的数据 和构造新的 XSZT实体
                    oldXszt = service.getOldXszt(record);
                    newXszt = service.structureXszt(record);
                }
            }catch (Exception e){
                LOGGER.error("updateByPrimaryKeyNull处理BDCDYHZT异常：",e);
            }
            // 执行更新方法
            result = joinPoint.proceed();
            try{
                if(service != null){
                    service.updateZt(record,oldXszt,newXszt,true);
                }
            }catch (Exception e){
                LOGGER.error("updateByPrimaryKeyNull处理BDCDYHZT异常：",e);
            }
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 更新 不更新NULL
     */
    @Around(UPDATE_BYKEY_SELECTIVE)
    public Object updateByPrimaryKeySelective(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object record = args[0];
        Object result = 0;
        if(record != null){
            BdcdyhZtAbstractService service = null;
            SSjBdcdyhxsztDO oldXszt = null;
            SSjBdcdyhxsztDO newXszt = null;
            try{
                service = defaultService.getBean(record.getClass().getName());
                if(service != null){
                    // 执行更新前 获取 原有XSZT表中的数据 和构造新的 XSZT实体
                    oldXszt = service.getOldXszt(record);
                    newXszt = service.structureXszt(record);
                }
            }catch (Exception e){
                LOGGER.error("updateByPrimaryKeySelective处理BDCDYHZT异常：",e);
            }
            // 执行更新方法
            result = joinPoint.proceed();
            try{
                if(service != null){
                    // 更新XSZT
                    service.updateZt(record,oldXszt,newXszt,false);
                }
            }catch (Exception e){
                LOGGER.error("updateByPrimaryKeySelective处理BDCDYHZT异常：",e);
            }
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param joinPoint
     * @return void
     * @description 删除方法
     */
    @Around(DELETE)
    public Object delete(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object record = args[0];
        Object result = 0;
        if(record != null){
            BdcdyhZtAbstractService service = null;
            SSjBdcdyhxsztDO oldXszt = null;
            try{
                service = defaultService.getBean(record.getClass().getName());
                if(service != null){
                    // 执行更新前 获取 原有XSZT表中的数据
                    oldXszt = service.getOldXszt(record);
                }
            }catch (Exception e){
                LOGGER.error("delete处理BDCDYHZT异常：",e);
            }
            // 执行删除方法
            result = joinPoint.proceed();
            try{
                if(service != null){
                    if(oldXszt != null && StringUtils.isNotBlank(oldXszt.getBdcdyh())){
                        // 删除XSZT
                        service.deleteZt(record,oldXszt);
                    }
                }
            }catch (Exception e){
                LOGGER.error("delete处理BDCDYHZT异常：",e);
            }
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param joinPoint
     * @return void
     * @description 根据主键删除方法
     */
    @Around(DELETE_BYPK)
    public Object deleteByPrimaryKey(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Class entityClass = (Class) args[0];
        Object pk = args[1];
        Object result = 0;
        if(pk != null){
            BdcdyhZtAbstractService service = null;
            Object record = null;
            SSjBdcdyhxsztDO oldXszt = null;
            try{
                service = defaultService.getBean(entityClass.getName());
                record = entityMapper.selectByPrimaryKey(entityClass,pk);
                // 执行更新前 获取 原有XSZT表中的数据
                oldXszt = service.structureXszt(record);
            }catch (Exception e){
                LOGGER.error("deleteByPrimaryKey处理BDCDYHZT异常：",e);
            }
            // 执行删除方法
            result = joinPoint.proceed();
            try{
                if(service != null){
                    // 删除XSZT
                    service.deleteZt(record,oldXszt);
                }
            } catch (Exception e){
                LOGGER.error("deleteByPrimaryKey处理BDCDYHZT异常：",e);
            }
        }
        return result;
    }
}
