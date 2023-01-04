package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.core.qo.init.DyhGzQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/11/17
 * @description 外联产权限制权利处理日志记录
 */
@Service(value = LogConstans.LOG_TYPE_WLCQXZQLCL)
public class LogRecordWlcqXzqlClServiceImpl implements LogCustomRecordService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogRecordWlcqXzqlClServiceImpl.class);

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        LOGGER.info("外联产权限制权利处理日志记录:{}",logRecordDTO);
        Map<String, Object> paramMap = logRecordDTO.getParamMap();
        if(MapUtils.isNotEmpty(paramMap)){
            BdcCzrzDO bdcCzrzDO = LogCustomRecordService.resolveLogParamToBdcCzrzDO(paramMap, BdcCzrzLxEnum.CZRZ_CZLX_YWCZ.key());
            bdcCzrzDO.setCzzt(1);
            String args = (String) paramMap.get(LogKeyEnum.METHOD_ARGS.getKey());
            if(StringUtils.isNotBlank(args)){
                // 解析方法请求参数，获取请求参数中的工作流实例ID
                JSONArray methodArgs = JSONArray.parseObject(args, (Type) Object.class);
                if(methodArgs.size() > 0) {
                    List<DyhGzQO> dyhGzQOList = JSONObject.parseArray(JSON.toJSONString(methodArgs.get(0)), DyhGzQO.class);
                    if(CollectionUtils.isNotEmpty(dyhGzQOList)) {
                        for(DyhGzQO dyhGzQO:dyhGzQOList) {
                            //组织操作内容
                            String czyy = "插入历史关系以及单元号替换信息:"+JSONObject.toJSONString(dyhGzQO);
                            if (methodArgs.size() > 2) {
                                String gzlslid = (String) methodArgs.get(1);
                                String currentUserName = (String) methodArgs.get(2);
                                if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(currentUserName)) {
                                    bdcCzrzFeignService.initBdcCzrz(gzlslid, czyy, BdcCzrzLxEnum.CZRZ_CZLX_YWCZ.key(), currentUserName);
                                    return;
                                }
                            }
                            bdcCzrzDO.setCzyy(czyy);
                            // 记录数据库日志内容
                            this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
                        }
                    }
                }
            }
        }
    }
}
