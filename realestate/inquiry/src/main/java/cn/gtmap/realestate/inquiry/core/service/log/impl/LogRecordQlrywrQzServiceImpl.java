package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcQzxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;

@Service(value = LogConstans.LOG_TYPE_QLRYWRQZ)
public class LogRecordQlrywrQzServiceImpl implements LogCustomRecordService {
    /**
     * 记录操作权利人义务人签名日志内容开关，默认为：true
     */
    @Value("${log.QlrywrQz.enable:true}")
    private boolean logQlrywrQzEnable;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 根据配置判断是否记录选择台账日志
        if(!logQlrywrQzEnable){
            return;
        }
        Map<String, Object> paramMap = logRecordDTO.getParamMap();
        if(MapUtils.isNotEmpty(paramMap)){
            BdcCzrzDO bdcCzrzDO = LogCustomRecordService.resolveLogParamToBdcCzrzDO(paramMap, BdcCzrzLxEnum.CZRZ_CZLX_YWCZ.key());
            bdcCzrzDO.setCzyy("受理人员操作权利人义务人签字");
            bdcCzrzDO.setCzzt(1);

            String args = (String) paramMap.get(LogKeyEnum.METHOD_ARGS.getKey());
            if(StringUtils.isNotBlank(args)){
                // 解析方法请求参数，获取请求参数中的工作流实例ID，在通过工作流实例ID获取xmid
                JSONArray methodArgs = JSONArray.parseObject(args, (Type) Object.class);
                if(methodArgs.size() > 0){
                    BdcQzxxDTO bdcQzxxDTO = JSONObject.parseObject(JSON.toJSONString(methodArgs.get(0)), BdcQzxxDTO.class);
                    if (bdcQzxxDTO != null && StringUtils.isNotBlank(bdcQzxxDTO.getXmid())){
                        BdcXmDO bdcXmDO = bdcXmFeignService.queryBdcXmByXmid(bdcQzxxDTO.getXmid());
                        bdcCzrzDO.setXmid(bdcXmDO.getXmid());
                        bdcCzrzDO.setLcmc(bdcXmDO.getGzldymc());
                        bdcCzrzDO.setSpxtywh(bdcXmDO.getSpxtywh());
                        bdcCzrzDO.setSlr(bdcXmDO.getSlr());
                        bdcCzrzDO.setSlsj(bdcXmDO.getSlsj());
                        bdcCzrzDO.setSlbh(bdcXmDO.getSlbh());
                        bdcCzrzDO.setGzlslid(bdcXmDO.getGzlslid());
                        if (bdcQzxxDTO.getQzrlb() == 1) {
                            bdcCzrzDO.setCzyy("受理人员操作权利人签字");
                            bdcCzrzDO.setCzmc("记录受理人员操作权利人签字");
                        } else if (bdcQzxxDTO.getQzrlb() == 3) {
                            bdcCzrzDO.setCzyy("受理人员操作义务人签字");
                            bdcCzrzDO.setCzmc("记录受理人员操作义务人签字");
                        }
                    }
                }
            }
            // 记录数据库日志内容
            this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }

}
