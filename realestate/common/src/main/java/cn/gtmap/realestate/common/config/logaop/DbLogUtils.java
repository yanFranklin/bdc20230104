package cn.gtmap.realestate.common.config.logaop;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.DbLogDTO;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/9/28
 * @description 手动登簿LOG日志
 */
@Component
public class DbLogUtils {

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DbLogUtils.class);

    /**
     * @param dbLogDTO 登簿参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存登簿验证日志
     */
    @Async
    public void saveDbLog(DbLogDTO dbLogDTO) {
        if (MapUtils.isNotEmpty(dbLogDTO.getPrivateAttrMap()) && null != dbLogDTO.getPrivateAttrMap().get("gzlslid")) {
            Map<String, String> xmxxMap = this.addBdcXmxx(dbLogDTO.getPrivateAttrMap().get("gzlslid").toString());
            if(MapUtils.isNotEmpty(xmxxMap)){
                dbLogDTO.getPrivateAttrMap().putAll(xmxxMap);
            }
        }
        Map<String, Object> data = new HashMap();
        String userName = userManagerUtils.getCurrentUserName();
        UserDto userDto = userManagerUtils.getUserByName(userName);
        if (userDto != null) {
            // 有的用户被删除，需要判空
            data.put(LogKeyEnum.ALIAS.getKey(), userDto.getAlias());
        }
        data.put(LogKeyEnum.EVENT_NAME.getKey(), "手动登簿操作日志");
        if (dbLogDTO.getPrivateAttrMap() != null) {
            data.putAll(dbLogDTO.getPrivateAttrMap());
        }

        AuditEvent auditEvent = new AuditEvent(userName, LogEventEnum.SDDBLOG.key(), data);
        try {
            zipkinAuditEventRepository.add(auditEvent);
        } catch (Exception e) {
            LOGGER.error("记录手动登簿操作日志，调用大云 zipkinAuditEventRepository 出错： {}", e.getMessage());
        }
    }

    /**
     * 根据工作流实例ID 查询不动产项目表补充手动登簿日志内容
     * @param gzlslid 工作流实例ID
     */
    private Map<String, String> addBdcXmxx(String gzlslid){
        Map<String, String> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                paramMap.put("gzlslid", bdcXmDO.getGzlslid());
                paramMap.put("slbh", bdcXmDO.getSlbh());
                paramMap.put("dbr", Optional.ofNullable(bdcXmDO.getDbr()).orElse(""));
                paramMap.put("qlr", bdcXmDO.getQlr());
                paramMap.put("ywr", bdcXmDO.getYwr());
                paramMap.put("gzldymc", bdcXmDO.getGzldymc());
                if(bdcXmDOList.size() > 1){
                    paramMap.put("bdcdyh", bdcXmDO.getBdcdyh() + "等");
                    paramMap.put("zl", bdcXmDO.getZl() + "等");
                }else{
                    paramMap.put("bdcdyh", bdcXmDO.getBdcdyh());
                    paramMap.put("zl", bdcXmDO.getZl());
                }
            }
        }
        return paramMap;
    }

    /**
     * @param routeKey 队列和交换机绑定的Key
     * @param exchange Exchange名称
     * @param obj      发送的数据
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description Mq发送日志记录
     */
    public void saveMqSendLog(String exchange, String routeKey, Object obj) {
        LOGGER.warn("Mq发送日志记录: saveMqSendLog, exchange:{}, routeKey:{}, obj{}", exchange, routeKey, obj);
        Map<String, Object> data = new HashMap();
        String userName = userManagerUtils.getCurrentUserName();
        UserDto userDto = userManagerUtils.getUserByName(userName);
        if (userDto != null) {
            // 有的用户被删除，需要判空
            data.put(LogKeyEnum.ALIAS.getKey(), userDto.getAlias());
        }
        data.put(LogKeyEnum.EVENT_NAME.getKey(), "Mq发送操作日志");
        data.put("exchange", exchange);
        data.put("routeKey", routeKey);
        data.put("sendData", obj);
        AuditEvent auditEvent = new AuditEvent(userName,  LogEventEnum.MQSENDLOG.key(), data);
        try {
            zipkinAuditEventRepository.add(auditEvent);
        } catch (Exception e) {
            LOGGER.error("Mq发送操作日志，调用大云 zipkinAuditEventRepository 出错： {}", e.getMessage());
        }
    }
}
