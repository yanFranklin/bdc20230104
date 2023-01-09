package cn.gtmap.realestate.inquiry.core.service.log;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-02
 * @description 日志自定义记录接口
 */
public interface LogCustomRecordService {

    /**
     * 日志记录接口
     */
    void recordLog(LogRecordDTO logRecordDTO);

    /**
     * 默认共用方法，解析日志记录的参数，组装成 BdcCzrzDO类
     * <p>解析以下参数内容：查询人、查询ip、查询参数、操作名称、操作时间、操作类型、操作状态</p>
     */
    static BdcCzrzDO resolveLogParamToBdcCzrzDO(Map<String, Object> paramMap, Integer czlx){
        BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
        if(MapUtils.isNotEmpty(paramMap)){
            // 解析LogNoteAspect中的记录的日志参数，转换为 BdcCzrzDO 对象
            String request = (String) paramMap.get(LogKeyEnum.METHOD_ARGS.getKey());
            // 查询参数
            bdcCzrzDO.setCzcs(CommonUtil.subLongStr(request));
            // 操作结果
            String response = (String) paramMap.get(LogKeyEnum.METHOD_RESPONSE.getKey());
            bdcCzrzDO.setCzjg(CommonUtil.subLongStr(response));
            // 查询人
            bdcCzrzDO.setCzr((String) paramMap.get(LogKeyEnum.ALIAS.getKey()));
            // 查询ip
            bdcCzrzDO.setIp((String) paramMap.get(CommonConstantUtils.IP));
            // 操作名称
            bdcCzrzDO.setCzmc((String) paramMap.get(LogKeyEnum.VIEW_TYPE_NAME.getKey()));
            // 操作时间
            Date czsj = new Date();
            if(paramMap.containsKey(LogKeyEnum.TIME.getKey())){
                czsj = new Date((long) paramMap.get(LogKeyEnum.TIME.getKey()));
            }
            bdcCzrzDO.setCzsj(czsj);
            // 操作类型
            bdcCzrzDO.setCzlx(czlx);
        }
        return bdcCzrzDO;
    }
}
