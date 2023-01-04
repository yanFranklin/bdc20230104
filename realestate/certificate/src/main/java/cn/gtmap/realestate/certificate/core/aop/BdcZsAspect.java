package cn.gtmap.realestate.certificate.core.aop;


import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.annotations.ZsDyZt;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @description 不动产证书操作相关切面
 */
@Component
@Aspect
public class BdcZsAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZsAspect.class);
    @Autowired
    private BdcZsService bdcZsService;


    @Pointcut(value = "@annotation(zsDyZt)")
    public void pointCut(ZsDyZt zsDyZt) {
    }

    @AfterReturning(value = "pointCut(zsDyZt)")
    public void updateDyztYdy(JoinPoint joinPoint, ZsDyZt zsDyZt) {
        try {
            Object[] args = joinPoint.getArgs();
            BdcPrintDTO bdcPrintDTO = (BdcPrintDTO) args[0];
            String paramName = zsDyZt.paramName();
            List<String> zsidList = bdcPrintDTO.getListZsid();
            if (CollectionUtils.isEmpty(zsidList)) {
                zsidList = new ArrayList();
            }
            if (StringUtils.equals(Constants.ZSID, paramName)) {
                zsidList.clear();
                zsidList.add(bdcPrintDTO.getZsid());
            }
            int result = bdcZsService.updateBdcZsDyzt(zsidList, CommonConstantUtils.SF_S_DM);
            LOGGER.warn("更新证书打印状态，参数：{}。更新数据量：{}", bdcPrintDTO.toString(), result);
        } catch (Exception e) {
            LOGGER.error("updateDyztYdy更新异常，异常信息：", e);
        }
    }
}
