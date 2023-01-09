package cn.gtmap.realestate.inquiry.core.service.log.handler;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 日志内容默认Db记录处理类
 */
@Component
public class LogRecordDefaultDbHandler extends LogRecordHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordDefaultDbHandler.class);

    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;


    @Override
    public void handleLogRecordRequest(LogRecordDTO logRecordDTO) {
        try{
            if(!logRecordDTO.isDbRecord()){
                return;
            }
            Map<String, Object> paramMap = logRecordDTO.getParamMap();
            if(MapUtils.isNotEmpty(paramMap)){
                BdcCzrzDO bdcCzrzDO = LogCustomRecordService.resolveLogParamToBdcCzrzDO(paramMap, BdcCzrzLxEnum.CZRZ_CZLX_YWCZ.key());
                // 添加日志内容
                this.addLogInfo(logRecordDTO, bdcCzrzDO);
                // 记录数据库日志内容
                this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
            }

        }catch(Exception e){
            LOGGER.error("记录默认数据库日志出现异常，{}", e.getMessage());
        }finally {
            // 获取下一个日志记录处理者
            if (getNext() != null) {
                getNext().handleLogRecordRequest(logRecordDTO);
            }
        }
    }

    /**
     * 丰富日志内容记录
     */
    private void addLogInfo(LogRecordDTO logRecordDTO, BdcCzrzDO bdcCzrzDO){
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
                    this.addLogParamByGzlslid(gzlslid, bdcCzrzDO);
                    return;
                }
            }
            {
                // 根据受理编号参数，丰富日志内容
                if(dataParam.containsKey("slbh")){
                    String slbh = (String) dataParam.get("slbh");
                    this.addLogParamBySlbh(slbh, bdcCzrzDO);
                    return;
                }
            }
            {
                // 根据项目ID参数，丰富日志内容
                if(dataParam.containsKey("xmid")){
                    String xmid = (String) dataParam.get("xmid");
                    this.addLogParamByXmid(xmid, bdcCzrzDO);
                    return;
                }
            }
        }
    }


    /**
     * 根据工作流实例ID，添加日志参数（不动产单元号、项目ID、受理编号、权利人、义务人、坐落、流程名称）
     */
    private void addLogParamByGzlslid(String gzlslid, BdcCzrzDO bdcCzrzDO){
        if(StringUtils.isNotBlank(gzlslid)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(gzlslid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                this.addLogDataParamFromBdcXm(bdcXmDO, bdcCzrzDO);
            }else{
                BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
                if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())){
                    List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                        this.addLogDataParamFromBdcSLXm(bdcSlXmDOList.get(0), bdcSlJbxxDO, bdcCzrzDO);
                    }
                }
            }
        }
    }

    /**
     * 根据受理编号，添加日志参数（不动产单元号、项目ID、受理编号、权利人、义务人、坐落、流程名称）
     */
    private void addLogParamBySlbh(String slbh, BdcCzrzDO bdcCzrzDO){
        if(StringUtils.isNotBlank(slbh)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh(slbh);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                this.addLogDataParamFromBdcXm(bdcXmDO, bdcCzrzDO);
            }else{
                BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");
                if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())){
                    List<BdcSlXmDO> bdcSlXmDOList = this.bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                    if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
                        this.addLogDataParamFromBdcSLXm(bdcSlXmDOList.get(0), bdcSlJbxxDO, bdcCzrzDO);
                    }
                }
            }
        }
    }

    /**
     * 根据项目ID，添加日志参数（不动产单元号、项目ID、受理编号、权利人、义务人、坐落、流程名称）
     */
    private void addLogParamByXmid(String xmid, BdcCzrzDO bdcCzrzDO){
        if(StringUtils.isNotBlank(xmid)){
            BdcXmDO bdcXmDO = this.bdcXmFeignService.queryBdcXmByXmid(xmid);
            if(Objects.nonNull(bdcXmDO)){
                this.addLogDataParamFromBdcXm(bdcXmDO, bdcCzrzDO);
            }else{
                BdcSlXmDO bdcSlXmDO = this.bdcSlXmFeignService.queryBdcSlXmByXmid(xmid);
                if(Objects.nonNull(bdcSlXmDO)){
                    BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(bdcSlXmDO.getJbxxid());
                    if(Objects.isNull(bdcSlJbxxDO)){
                        this.addLogDataParamFromBdcSLXm(bdcSlXmDO, bdcSlJbxxDO, bdcCzrzDO);
                    }
                }
            }
        }
    }

    /**
     * 添加不动产项目日志信息
     */
    private void addLogDataParamFromBdcXm(BdcXmDO bdcXmDO, BdcCzrzDO bdcCzrzDO){
        if(Objects.nonNull(bdcXmDO)){
            bdcCzrzDO.setSlbh(bdcXmDO.getSlbh());
            bdcCzrzDO.setSpxtywh(bdcXmDO.getSpxtywh());
            bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
            bdcCzrzDO.setSlsj(bdcXmDO.getSlsj());
            bdcCzrzDO.setSlr(bdcXmDO.getSlr());
            bdcCzrzDO.setGzlslid(bdcXmDO.getGzlslid());
            bdcCzrzDO.setXmid(bdcXmDO.getXmid());
        }
    }

    /**
     * 添加不动产受理项目日志信息
     */
    private void addLogDataParamFromBdcSLXm(BdcSlXmDO bdcSlXmDO, BdcSlJbxxDO bdcSlJbxxDO, BdcCzrzDO bdcCzrzDO){
        if(Objects.nonNull(bdcSlXmDO)){
            bdcCzrzDO.setSlbh(bdcSlJbxxDO.getSlbh());
            bdcCzrzDO.setSpxtywh(bdcSlXmDO.getSpxtywh());
            bdcCzrzDO.setLcmc(bdcSlJbxxDO.getLcmc());
            bdcCzrzDO.setSlsj(bdcSlJbxxDO.getSlsj());
            bdcCzrzDO.setSlr(bdcSlJbxxDO.getSlr());
            bdcCzrzDO.setGzlslid(bdcSlJbxxDO.getGzlslid());
            bdcCzrzDO.setXmid(bdcSlXmDO.getXmid());
        }
    }

}
