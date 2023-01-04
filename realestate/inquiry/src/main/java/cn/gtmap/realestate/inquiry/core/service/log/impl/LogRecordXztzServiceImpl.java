package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-02
 * @description 选择台账自定义日志记录实现类
 */
@Service(value = LogConstans.LOG_TYPE_XZTZ)
public class LogRecordXztzServiceImpl implements LogCustomRecordService {

    /**
     * 记录选择台账日志内容开关，默认为：false
     */
    @Value("${log.xztz.enable:false}")
    private boolean logXztzEnable;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 根据配置判断是否记录选择台账日志
        if(!logXztzEnable){
            return;
        }
        Map<String, Object> paramMap = logRecordDTO.getParamMap();
        if(MapUtils.isNotEmpty(paramMap)){
            BdcCzrzDO bdcCzrzDO = LogCustomRecordService.resolveLogParamToBdcCzrzDO(paramMap, BdcCzrzLxEnum.CZRZ_CZLX_XZTZ.key());
            String count = this.resovleRespPagingCount(paramMap);
            if(StringUtils.isNotBlank(count)){
                // 查询结果条数
                bdcCzrzDO.setCzjg(count);
            }else{
                bdcCzrzDO.setCzjg("0");
            }
            // 记录数据库日志内容
            this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }

    /**
     * 解析选择台账分页查询结果的数量
     */
    private String resovleRespPagingCount(Map<String, Object> paramMap){
        if(paramMap.containsKey(LogKeyEnum.METHOD_RESPONSE.getKey())){
            String responseStr = (String) paramMap.get(LogKeyEnum.METHOD_RESPONSE.getKey());
            Map<String,Object> responseMap = JSON.parseObject(responseStr, Map.class);
            if(MapUtils.isNotEmpty(responseMap)&& responseMap.containsKey("totalElements")){
                Integer totalElements = (Integer) responseMap.get("totalElements");
                return String.valueOf(totalElements);
            }
        }
        return null;
    }

}
