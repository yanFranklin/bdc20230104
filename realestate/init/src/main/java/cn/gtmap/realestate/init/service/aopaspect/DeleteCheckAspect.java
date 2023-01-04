package cn.gtmap.realestate.init.service.aopaspect;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/3.
 * @description
 */
@Component
@Aspect
public class DeleteCheckAspect {
    @Autowired
    BdcQllxService bdcQllxService;

   /* @Before("execution(* cn.gtmap.realestate.init.service.xmxx.InitBdcXmLsgxAbstractService.delete(..))")*/
    public void checkSfdb(JoinPoint point) {
        List<BdcXmDO> bdcXmDOList = (List<BdcXmDO>) point.getArgs()[0];
        if(CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.isNotBlank(bdcXmDOList.get(0).getXmid())){
            BdcQl bdcQl = bdcQllxService.queryQllxDO(bdcXmDOList.get(0).getXmid());
            if(bdcQl!=null && CommonConstantUtils.QSZT_VALID.equals(bdcQl.getQszt())){
                throw new AppException("当前业务已登簿，不允许删除，xmid:"+bdcXmDOList.get(0).getXmid());
            }
        }
    }
}
