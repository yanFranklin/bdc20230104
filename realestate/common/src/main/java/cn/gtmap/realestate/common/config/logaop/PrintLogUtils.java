package cn.gtmap.realestate.common.config.logaop;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPrintLogDTO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/6/3
 * @description
 */
@Component
public class PrintLogUtils {

    @Autowired
    private  UserManagerUtils userManagerUtils;

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintLogUtils.class);


    /**
     * 保存打印信息
     * @param bdcPrintLogDTO
     */
    public void savePrintLog(BdcPrintLogDTO bdcPrintLogDTO){

        Map<String, Object> data = new HashMap();
        String userName = userManagerUtils.getCurrentUserName();
        UserDto userDto = userManagerUtils.getUserByName(userName);
        if (userDto != null) {// 有的用户被删除，需要判空
            data.put(CommonConstantUtils.ALIAS, userDto.getAlias());
            data.put(CommonConstantUtils.ORGANIZATION, userManagerUtils.getOrganizationByUserName(userName));
        }
        data.put(CommonConstantUtils.MODELURS, bdcPrintLogDTO.getModelUrl());
        data.put(CommonConstantUtils.DATAURL, bdcPrintLogDTO.getDataUrl());
        data.put(CommonConstantUtils.XMLSTR, bdcPrintLogDTO.getXmlStr());

        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "打印操作记录");
        if(bdcPrintLogDTO.getPrivateAttrMap() != null){
            data.putAll(bdcPrintLogDTO.getPrivateAttrMap());
        }
        AuditEvent auditEvent = new AuditEvent(userName, CommonConstantUtils.LOG_EVENT_PRINTLOG, data);
        try {
            zipkinAuditEventRepository.add(auditEvent);
        }catch(Exception e){
            LOGGER.error("-------日志记录接口出错-------"+e.getMessage());
        }
    }

}

