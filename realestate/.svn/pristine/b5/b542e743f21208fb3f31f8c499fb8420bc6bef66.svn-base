package cn.gtmap.realestate.exchange.service.impl.inf.log;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDsfRzDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.inf.log.LogService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.ParseJsonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-24
 * @description 记录在ES中的日志 newspan 日志类型为 "DSFJK"
 */
@Service(value="logInEs")
public class LogInEsServiceImpl implements LogService {

    protected static Logger LOGGER = LoggerFactory.getLogger(LogInEsServiceImpl.class);

    @Autowired
    private DozerBeanMapper defaultDozerMapper;

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private UserManagerUtils userManagerUtils;
    // 请求参数 和响应结果 最大长度
    private static final int MAX_LENGTH = 5000;
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param auditEventBO
     * @return void
     * @description 保存请求日志
     */
    @Override
    public void saveAuditLog(AuditEventBO auditEventBO) {
        if (StringUtil.isBlank(auditEventBO.getViewTypeName())) {
            return;
        }
        String userName = userManagerUtils.getCurrentUserName();
        String principal = StringUtils.isNotBlank(userName) ? CommonUtil.aopLogGetPrincipal() : userName;
        BdcDsfRzDO bdcDsfRzDO = new BdcDsfRzDO();
        defaultDozerMapper.map(auditEventBO, bdcDsfRzDO);
        LOGGER.debug("当前接口共享部门标识:{}",bdcDsfRzDO.getGxbmbz());
        // 获取操作人的登录名 如果请求入口在页面，则可以获取到操作用户登录名
        bdcDsfRzDO.setCzr(principal);
        bdcDsfRzDO.setQxdm(auditEventBO.getQxdm());
        if (StringUtils.isNotBlank(auditEventBO.getUsername())) {
            bdcDsfRzDO.setCzr(auditEventBO.getUsername());
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if(Objects.nonNull(currentUser) && StringUtils.isNotBlank(currentUser.getAlias())){
            bdcDsfRzDO.setAlias(currentUser.getAlias());
        }
//        bdcDsfRzDO.setAlias("currentUser.getAlias()");
        // 获取第三方子系统提供地址
        bdcDsfRzDO.setQqsj(new Date());
        Map<String, Object> cusomMap = new HashMap<>();
        if (auditEventBO.getLogBO() != null) {
            bdcDsfRzDO.setBdcdz(auditEventBO.getLogBO().getBdcdz());
            // 处理 自定义日志
            cusomMap = getCustomMap(auditEventBO);
        }
        if (StringUtils.isNotBlank(bdcDsfRzDO.getQqcs()) && bdcDsfRzDO.getQqcs().length() > MAX_LENGTH && !auditEventBO.isBigDataFlag()) {
            bdcDsfRzDO.setQqcs("字符串过长，已省略。开头：" + StringUtils.left(bdcDsfRzDO.getQqcs(), 1000));
        }
        if (StringUtils.isNotBlank(bdcDsfRzDO.getXyjg()) && bdcDsfRzDO.getXyjg().length() > MAX_LENGTH && !auditEventBO.isBigDataFlag()) {
            bdcDsfRzDO.setXyjg("字符串过长，已省略。开头：" + StringUtils.left(bdcDsfRzDO.getXyjg(), 1000));
        }
        Map<String, Object> logData = JSONObject.parseObject(JSONObject.toJSONString(bdcDsfRzDO), Map.class);
        if (MapUtils.isNotEmpty(cusomMap)) {
            logData.putAll(cusomMap);
        }
        AuditEvent event;
        if (StringUtils.isNotBlank(auditEventBO.getRzlx()) && !Constants.EXCHANGE_ES_RZLX.equals(auditEventBO.getRzlx())){
            event = new AuditEvent(principal, auditEventBO.getRzlx(), logData);
        }else {
            event = new AuditEvent(principal, Constants.EXCHANGE_ES_RZLX, logData);
        }
        try {
            zipkinAuditEventRepository.add(event);
        } catch (Exception ex) {
            LOGGER.error("向国图大云写日志异常，日志名：{}", auditEventBO.getViewTypeName(), ex);
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param auditEventBO
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 获取自定义参数的日志MAP
     */
    private Map<String,Object> getCustomMap(AuditEventBO auditEventBO){
        LogBO logBO = auditEventBO.getLogBO();
        Map<String,Object> customMap = new HashMap<>();
        if(StringUtils.isNotBlank(logBO.getReqCustom())){
            setCustomMap(customMap,logBO.getReqCustom(),auditEventBO.getRequest());
        }
        if(StringUtils.isNotBlank(logBO.getRespCustom())){
            setCustomMap(customMap,logBO.getRespCustom(),auditEventBO.getResponse());
        }
        return customMap;
    }

    private void setCustomMap(Map<String,Object> customMap,String custom,String param){
        JSONObject paramJson = null;
        try{
            paramJson = JSONObject.parseObject(param);
        }catch (Exception e){}
        if(paramJson != null){
            Map<String,String> reqCustomMap = (Map) ParseJsonUtil.parseJson(custom);
            Iterator<Map.Entry<String,String>> iter = reqCustomMap.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry<String,String> temp = iter.next();
                Object value = CommonUtil.getKeyFromJsonObject(paramJson,temp.getValue());
                if(value != null){
                    customMap.put(temp.getKey(),value);
                }
            }
        }
    }

}
