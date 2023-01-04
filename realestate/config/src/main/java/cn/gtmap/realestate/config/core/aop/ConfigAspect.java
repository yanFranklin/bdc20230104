package cn.gtmap.realestate.config.core.aop;

import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.enums.BdcZssyqkEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.config.service.BdcXtFphService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-10
 * @description
 */
@Component
@Aspect
public class ConfigAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigAspect.class);
    private static final String CLASS_NAME = ConfigAspect.class.getName();
    @Autowired
    BdcXtFphService bdcXtFphService;
    @Autowired
    EntityMapper entityMapper;
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Pointcut("execution(* cn.gtmap.realestate.config.service.BdcXtFphService.queryBdcFphDOList(..))")
    public void queryBdcFphDOList() {
    }

    /**
     * @param joinPoint
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 更新发票号使用状态
     */
    @AfterReturning("queryBdcFphDOList()")
    public void revertFph(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<BdcSlSfxxDO> bdcSlSfxxDOList = (List<BdcSlSfxxDO>) args[0];
        if (CollectionUtils.isEmpty(bdcSlSfxxDOList)) {
            LOGGER.debug("{}：更新发票号使用状态 ：缺少参数 bdcSlSfxmDOList或者slbh{}");
            throw new MissingArgumentException("bdcSlSfxmDOList，slbh");
        }
        LOGGER.debug("{}：更新发票号使用状态 ：{}", CLASS_NAME, JSONObject.toJSONString(bdcSlSfxxDOList));
        // 更新 bdc_fph ,设置 fphsymxid 为空，设置发票号状态为 未使用
        bdcSlSfxxDOList.forEach(bdcSlSfxxDO -> {
            String sfxxid = bdcSlSfxxDO.getSfxxid();
            if (StringUtils.isBlank(sfxxid) || StringUtils.isBlank(bdcSlSfxxDO.getFph())) {
                return;
            }
            Example example = new Example(BdcFphDO.class);
            Example.Criteria criteria = example.createCriteria().andEqualTo("sfxxid", sfxxid);
            criteria.andEqualTo("syqk", BdcZssyqkEnum.YSY.getCode());
            List<BdcFphDO> bdcFphDOList = entityMapper.selectByExampleNotNull(example);
            if(CollectionUtils.isEmpty(bdcFphDOList)){
                return;
            }
            bdcFphDOList.forEach(bdcFphDO -> {
                bdcFphDO.setFphsymxid(null);
                bdcFphDO.setSlbh(null);
                bdcFphDO.setSfxxid(null);
                bdcFphDO.setSyqk(BdcZssyqkEnum.YLY.getCode());
                bdcXtFphService.updateBdcFphSyzt(bdcFphDO);
            });
        });
    }
}
