package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = LogConstans.LOG_TYPE_TDDR)
public class LogRecordTdDrServiceImpl  implements LogCustomRecordService {

    /**
     * 记录土地数据导入时日志
     */
    @Value("${log.tddr.enable:false}")
    private boolean logTddrEnable;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 根据配置判断是否记录导入土地日志
        if(!logTddrEnable){
            return;
        }
        Map<String, Object> paramMap = logRecordDTO.getParamMap();
        if(MapUtils.isNotEmpty(paramMap)){
            BdcCzrzDO bdcCzrzDO = LogCustomRecordService.resolveLogParamToBdcCzrzDO(paramMap, BdcCzrzLxEnum.CZRZ_CZLX_TDDR.key());
            // 记录数据库日志内容
            this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }
}
