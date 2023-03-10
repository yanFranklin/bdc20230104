package cn.gtmap.realestate.inquiry.core.service.log.handler;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.LogActionConstans;
import cn.gtmap.realestate.inquiry.config.LogRecordConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 日志内容默认Es记录处理类
 */
@Component
public class LogRecordDefaultEsHandler extends LogRecordHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordDefaultEsHandler.class);

    @Autowired
    LogRecordConfig logRecordConfig;

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private UserManagerClient userManagerClient;


    @Override
    public void handleLogRecordRequest(LogRecordDTO logRecordDTO) {
        try{
            // 记录ES日志
            String logType = StringUtils.isEmpty(logRecordDTO.getLogAction())? LogActionConstans.OTHER : logRecordDTO.getLogAction();
            // 日志内容，判断是否精简内容
            if(!logRecordConfig.isCompactMode()){
                this.addLogInfo(logRecordDTO);
            }
            zipkinAuditEventRepository.add(new AuditEvent(logRecordDTO.getUserName(), logType, logRecordDTO.getParamMap()));
        }catch(Exception e){
            LOGGER.error("记录es日志出现异常，{}", e.getMessage());
        } finally {
            // 获取下一个日志记录处理者
            if (getNext() != null) {
                getNext().handleLogRecordRequest(logRecordDTO);
            }
        }
    }

    /**
     * 添加用户信息， 追加用户部门信息
     */
    private void addLogUserInfo(LogRecordDTO logRecordDTO){
        if(StringUtils.isNotBlank(logRecordDTO.getUserName())){
            UserDto userDto = userManagerClient.getUserDetailByUsername(logRecordDTO.getUserName());
            if(Objects.nonNull(userDto)){
                Map<String, Object> dataParam = logRecordDTO.getParamMap();
                if(CollectionUtils.isEmpty(userDto.getOrgRecordList())){
                    dataParam.put("orgName", userDto.getOrgRecordList().get(0).getName());
                }
            }
        }
    }

    /**
     * 丰富日志内容记录
     */
    private void addLogInfo(LogRecordDTO logRecordDTO){
        Map<String, Object> dataParam = logRecordDTO.getParamMap();
        if(MapUtils.isNotEmpty(dataParam)){
            {
                // 根据工作流实例ID参数，丰富日志内容
                String gzlslid = "";
                if(dataParam.containsKey("gzlslid")){
                    gzlslid = (String) dataParam.get("gzlslid");
                }
                if(dataParam.containsKey("processInstanceId")){
                    gzlslid = (String) dataParam.get("processInstanceId");
                }
                if(dataParam.containsKey("processInsId")){
                    gzlslid = (String) dataParam.get("processInsId");
                }
                if(StringUtils.isNotBlank(gzlslid)){
                    this.addLogParamByGzlslid(gzlslid, logRecordDTO);
                    return;
                }
            }
            {
                // 根据受理编号参数，丰富日志内容
                if(dataParam.containsKey("slbh")){
                   String slbh = (String) dataParam.get("slbh");
                    this.addLogParamBySlbh(slbh, logRecordDTO);
                    return;
                }
            }
            {
                // 根据项目ID参数，丰富日志内容
              if(dataParam.containsKey("xmid")){
                    String xmid = (String) dataParam.get("xmid");
                    this.addLogParamByXmid(xmid, logRecordDTO);
                    return;
                }
            }
        }

    }

    /**
     * 根据工作流实例ID，添加日志参数（不动产单元号、项目ID、受理编号、权利人、义务人、坐落、流程名称）
     */
    private void addLogParamByGzlslid(String gzlslid, LogRecordDTO logRecordDTO){
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                this.addLogDataParamFromBdcXm(bdcXmDO, logRecordDTO);
            }else{
                BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
                if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())){
                    List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                        this.addLogDataParamFromBdcSLXm(bdcSlXmDOList.get(0), bdcSlJbxxDO, logRecordDTO);
                    }
                }
            }
        }
    }

    /**
     * 根据受理编号，添加日志参数（不动产单元号、项目ID、受理编号、权利人、义务人、坐落、流程名称）
     */
    private void addLogParamBySlbh(String slbh, LogRecordDTO logRecordDTO){
        if(StringUtils.isNotBlank(slbh)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                this.addLogDataParamFromBdcXm(bdcXmDO, logRecordDTO);
            }else{
                BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");
                if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())){
                    List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                        this.addLogDataParamFromBdcSLXm(bdcSlXmDOList.get(0), bdcSlJbxxDO, logRecordDTO);
                    }
                }
            }
        }
    }

    /**
     * 根据项目ID，添加日志参数（不动产单元号、项目ID、受理编号、权利人、义务人、坐落、流程名称）
     */
    private void addLogParamByXmid(String xmid, LogRecordDTO logRecordDTO){
        if(StringUtils.isNotBlank(xmid)){
            BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(xmid);
            if(Objects.nonNull(bdcXmDO)){
                this.addLogDataParamFromBdcXm(bdcXmDO, logRecordDTO);
            }else{
                BdcSlXmDO bdcSlXmDO = this.bdcSlXmFeignService.queryBdcSlXmByXmid(xmid);
                if(Objects.nonNull(bdcSlXmDO)){
                    BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                    if(Objects.isNull(bdcSlJbxxDO)){
                        this.addLogDataParamFromBdcSLXm(bdcSlXmDO, bdcSlJbxxDO, logRecordDTO);
                    }
                }
            }
        }
    }

    /**
     * 添加不动产项目日志信息
     */
    private void addLogDataParamFromBdcXm(BdcXmDO bdcXmDO, LogRecordDTO logRecordDTO){
        if(Objects.nonNull(bdcXmDO)){
            Map<String, Object> dataParam = logRecordDTO.getParamMap();
            dataParam.put("bdcdyh", bdcXmDO.getBdcdyh());
            dataParam.put("xmid", bdcXmDO.getXmid());
            dataParam.put("slbh", bdcXmDO.getSlbh());
            dataParam.put("gzlslid", bdcXmDO.getGzlslid());
            dataParam.put("qlr", bdcXmDO.getQlr());
            dataParam.put("ywr", bdcXmDO.getYwr());
            dataParam.put("zl", bdcXmDO.getZl());
            dataParam.put("gzldymc", bdcXmDO.getGzldymc());
            dataParam.put("ycqzh", bdcXmDO.getYcqzh());
        }
    }

    /**
     * 添加不动产受理项目日志信息
     */
    private void addLogDataParamFromBdcSLXm(BdcSlXmDO bdcSlXmDO, BdcSlJbxxDO bdcSlJbxxDO, LogRecordDTO logRecordDTO){
        if(Objects.nonNull(bdcSlXmDO)){
            Map<String, Object> dataParam = logRecordDTO.getParamMap();
            dataParam.put("bdcdyh", bdcSlXmDO.getBdcdyh());
            dataParam.put("xmid", bdcSlXmDO.getXmid());
            dataParam.put("slbh", bdcSlJbxxDO.getSlbh());
            dataParam.put("gzlslid", bdcSlJbxxDO.getGzlslid());
            dataParam.put("qlr", bdcSlXmDO.getQlr());
            dataParam.put("zl", bdcSlXmDO.getZl());
            dataParam.put("gzldymc", bdcSlJbxxDO.getLcmc());
            dataParam.put("ycqzh", bdcSlXmDO.getYbdcqz());
        }
    }

}
