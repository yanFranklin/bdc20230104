package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import cn.gtmap.realestate.inquiry.core.service.log.handler.LogRecordDefaultEsHandler;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-07
 * @description 匹配自定义日志记录实现类
 */
@Service(value = LogConstans.LOG_TYPE_PP)
public class LogRecordPptzServiceImpl implements LogCustomRecordService {

    @Autowired
    LogRecordDefaultEsHandler logRecordDefaultEsHandler;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Value("${log.pptz.enable:true}")
    private boolean logPptzEnable;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 根据配置判断是否记录选择台账日志
        if(!logPptzEnable){
            return;
        }
        // 执行日志记录方法
        try {
            Map<String, Object> data = logRecordDTO.getParamMap();
            //日志填充，尝试去查找坐落，不动产单元号，产权证号，如果没有操作内容则将请求参数传入
            if (data.containsKey("bdcdyh")) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setBdcdyh((String) data.get("bdcdyh"));
                List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(list)) {
                    BdcXmDO bdcXm = list.get(0);
                    if (!data.containsKey("zl")) {
                        data.put("zl", bdcXm.getZl());
                    }
                    if (!data.containsKey("bdcqzh")) {
                        data.put("bdcqzh", bdcXm.getBdcqzh());
                    }
                    if (!data.containsKey("paramCha")) {
                        data.put("paramCha", JSON.toJSONString(data));
                    }
                }
            }
            if (data.containsKey("fcxmid")) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid((String) data.get("fcxmid"));
                List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(list)) {
                    BdcXmDO bdcXm = list.get(0);
                    if (!data.containsKey("zl")) {
                        data.put("zl", bdcXm.getZl());
                    }
                    if (!data.containsKey("bdcdyh")) {
                        data.put("bdcdyh", bdcXm.getBdcdyh());
                    }
                    if (!data.containsKey("bdcqzh")) {
                        data.put("bdcqzh", bdcXm.getBdcqzh());
                    }
                    if (!data.containsKey("paramCha")) {
                        data.put("paramCha", JSON.toJSONString(data));
                    }
                }
            }
            logRecordDTO.setParamMap(data);
        }catch (Exception e){

        }
        logRecordDefaultEsHandler.handleLogRecordRequest(logRecordDTO);
    }
}
