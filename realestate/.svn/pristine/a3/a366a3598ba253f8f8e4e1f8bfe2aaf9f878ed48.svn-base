package cn.gtmap.realestate.common.core.support.log;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.BdcZhcxLogDO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcZhcxFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 公共日志保存服务
 */
@RestController
@RequestMapping(value = "/rest/v1.0/log")
public class BdcLogInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcLogInfoController.class);

    /**
     * 用户服务
     */
    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * 日志服务
     */
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    ProcessDefinitionClient processDefinitionClient;

    @Autowired
    BdcZhcxFeignService bdcZhcxFeignService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param logInfoMap 日志参数信息
     * @description  保存日志信息
     *
     *  1、为了灵活直接用 Map 参数，常用 KEY 说明：
     *  >>>>>>>>>>>>>>>>>>>>>>>>>>>
     *   userAlias       用户名称
     *   userName        用户账号
     *   viewTypeName    页面名称
     *   logType         日志类型
     *   response        日志详细内容
     *   cxtj            查询条件
     *   bdcdyh          不动产单元号
     *   xmid            项目ID
     *   zl              坐落
     *   ipaddress       IP地址
     *   slbh            受理编号
     *   ywr             义务人
     *   qlr             权利人
     *  >>>>>>>>>>>>>>>>>>>>>>>>>>>
     *
     *  2、必要 KEY 值项： logType
     */
    @PostMapping(value = "/info")
    public void saveLogInfo(@RequestBody Map<String, Object> logInfoMap) {
        if(MapUtils.isEmpty(logInfoMap) || StringUtils.isBlank(MapUtils.getString(logInfoMap, "logType"))){
            LOGGER.error("保存日志信息中止，因为日志参数信息为空");
            return;
        }

        // 获取用户名称、账号
        String userAlias = MapUtils.getString(logInfoMap, "userAlias");
        String userName = MapUtils.getString(logInfoMap, "userName");


        if(StringUtils.isBlank(userAlias) || StringUtils.isBlank(userName)){
            UserDto userDto = userManagerUtils.getCurrentUser();

            userAlias = userDto == null ? "" : userDto.getAlias();
            logInfoMap.put("alias", userAlias);

            userName = userDto == null ? "" : userDto.getUsername();
            logInfoMap.put("userName", userName);

            if(StringUtils.isNotBlank(userName)){
                List<OrganizationDto> organizationDtoList = userManagerUtils.getOrgListByUserName(userName);
                logInfoMap.put("orgId", organizationDtoList.get(0).getId());
                logInfoMap.put("orgName", organizationDtoList.get(0).getName());
            }
        }

        //流程规则忽略拼接流程信息
        String gzldyid = MapUtils.getString(logInfoMap, "gzldyid");
        String hlnr = MapUtils.getString(logInfoMap, "HLNR");
        if(StringUtils.isNotBlank(gzldyid) &&StringUtils.isNotBlank(hlnr)){
            ProcessDefData processDefData = processDefinitionClient.getProcessDefByProcessDefKey(gzldyid);
            if(processDefData != null && StringUtils.isNotBlank(processDefData.getName())) {
                hlnr = "流程名称:" + processDefData.getName() + " "+hlnr;
                logInfoMap.put("HLNR", hlnr);
            }
        }

        // 处理logmap里面的条件
        if(logInfoMap.containsKey("field")){
            Map<String,String> map = (Map)logInfoMap.get("field") ;
            if(MapUtils.isNotEmpty(map)){
                for(Map.Entry<String, String> entry : map.entrySet()){
                    if(StringUtils.isNotBlank(entry.getValue())){
                        logInfoMap.put(entry.getKey(),entry.getValue());
                    }
                }
            }

        }
        try {
            String logType = MapUtils.getString(logInfoMap, "logType");
            zipkinAuditEventRepository.add(new AuditEvent(userName, logType, logInfoMap));
        }catch(Exception e){
            LOGGER.error("保存日志信息出错：{}", e.getMessage());
        }
    }

    /**
     * 通用数据库日志内容记录（用于记录综合查询操作、打印操作日志等内容）
     * 保存综合查询台账查询和打印操作日志
     * @param bdcZhcxLogDO 日志信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/print")
    public void saveBdcZhcxLog(@RequestBody BdcZhcxLogDO bdcZhcxLogDO) {
        bdcZhcxFeignService.insertZhcxLog(bdcZhcxLogDO);
    }

}
