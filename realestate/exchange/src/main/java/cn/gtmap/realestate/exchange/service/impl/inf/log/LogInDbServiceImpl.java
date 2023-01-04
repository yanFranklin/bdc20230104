package cn.gtmap.realestate.exchange.service.impl.inf.log;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDsfRzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.inf.log.LogService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-24
 * @description 向DB中记录日志
 */
@Service(value = "logInDb")
public class LogInDbServiceImpl implements LogService {

    @Autowired
    private DozerBeanMapper defaultDozerMapper;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    // 请求参数 和响应结果 最大长度
    private static final int MAX_LENGTH = 3000;

    /**
     * @param auditEventBO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存日志
     */
    @Override
    public void saveAuditLog(AuditEventBO auditEventBO) {
        if(auditEventBO != null && StringUtils.isNotBlank(auditEventBO.getViewTypeName()) ){
            BdcDsfRzDO bdcDsfRzDO = new BdcDsfRzDO();
            defaultDozerMapper.map(auditEventBO,bdcDsfRzDO);
            bdcDsfRzDO.setRzid(UUIDGenerator.generate16());
            // 获取操作人的登录名 如果请求入口在页面，则可以获取到操作用户登录名
            String userName = userManagerUtils.getCurrentUserName();
            if (StringUtils.isNotBlank(userName)) {
                bdcDsfRzDO.setCzr(userName);
            } else {
                bdcDsfRzDO.setCzr(CommonUtil.aopLogGetPrincipal());
            }
            if (StringUtils.isNotBlank(auditEventBO.getUsername())) {
                bdcDsfRzDO.setCzr(auditEventBO.getUsername());
            }
            UserDto currentUser = userManagerUtils.getCurrentUser();
            if(Objects.nonNull(currentUser) && StringUtils.isNotBlank(currentUser.getAlias())){
                bdcDsfRzDO.setAlias(currentUser.getAlias());
            }
            if(StringUtils.isNotBlank(bdcDsfRzDO.getQqcs()) && bdcDsfRzDO.getQqcs().length() > MAX_LENGTH){
                bdcDsfRzDO.setQqcs("字符串过长，已省略");
            }
            if(StringUtils.isNotBlank(bdcDsfRzDO.getXyjg()) && bdcDsfRzDO.getXyjg().length() > MAX_LENGTH){
                bdcDsfRzDO.setXyjg("字符串过长，已省略");
            }
            // 获取第三方子系统提供地址
            setBdcdz(auditEventBO,bdcDsfRzDO);
            bdcDsfRzDO.setQqsj(new Date());
            bdcDsfRzDO.setQxdm(auditEventBO.getQxdm());
            entityMapper.insertSelective(bdcDsfRzDO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param auditEventBO
     * @param bdcDsfRzDO
     * @return void
     * @description 获取第三方子系统提供的接口地址
     */
    private void setBdcdz(AuditEventBO auditEventBO,BdcDsfRzDO bdcDsfRzDO){
        // 先获取配置
        if(auditEventBO.getLogBO() != null){
            LogBO logBO = auditEventBO.getLogBO();
            if(StringUtils.isNotBlank(logBO.getBdcdz())){
                bdcDsfRzDO.setBdcdz(logBO.getBdcdz());
            }
        }
    }
}
