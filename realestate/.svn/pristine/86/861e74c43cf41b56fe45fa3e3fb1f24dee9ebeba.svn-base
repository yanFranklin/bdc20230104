package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.LogKeyEnum;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-06
 * @description 流程转发自定义日志记录实现类
 */
@Service(value = LogConstans.LOG_TYPE_LCZF)
public class LogRecordLcZfServiceImpl implements LogCustomRecordService {
    /**
     * 流程转发方法请求入参中工作流实例ID的名称
     */
    private static final String[] GZLSLID_PARAM_NAME = {"processInsId", "processInstanceId"};

    @Value("${log.lczf.enable:false}")
    private boolean logLczfEnable;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcCzrzFeignService bdcCzrzFeignService;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 根据配置判断是否记录流程转发日志
        if(!logLczfEnable){
            return;
        }
        Map<String, Object> paramMap = logRecordDTO.getParamMap();
        if(MapUtils.isNotEmpty(paramMap)){
            BdcCzrzDO bdcCzrzDO = LogCustomRecordService.resolveLogParamToBdcCzrzDO(paramMap, BdcCzrzLxEnum.CZRZ_CZLX_LCZF.key());
            /**
             * 处理转发请求参数，批量转发请求参数为数组， 转发请求参数为对象
             * 记录 xmid 为查询结果
             */
            String args = (String) paramMap.get(LogKeyEnum.METHOD_ARGS.getKey());
            List<String> xmids = new ArrayList<>(10);
            if(StringUtils.isNotBlank(args)){
                // 解析方法请求参数，获取请求参数中的工作流实例ID，在通过工作流实例ID获取xmid
                JSONArray methodArgs = JSONArray.parseObject(args, (Type) Object.class);
                if(methodArgs.size() > 0){
                    if(methodArgs.get(0) instanceof List){
                        JSONArray params = JSONArray.parseArray(JSON.toJSONString(methodArgs.get(0)));
                        for(int i=0; i< params.size(); i++){
                            List<String> xmidList = this.getXmidListByParamGzlslid((JSONObject) params.get(i));
                            if(CollectionUtils.isNotEmpty(xmidList)){
                                xmids.addAll(xmidList);
                            }
                        }
                    }else if(methodArgs.get(0) instanceof JSONObject){
                        List<String> xmidList = this.getXmidListByParamGzlslid((JSONObject) JSON.parse(JSON.toJSONString(methodArgs.get(0))));
                        if(CollectionUtils.isNotEmpty(xmidList)){
                            xmids.addAll(xmidList);
                        }
                    }
                }
            }
            Map<String, Object> czjgMap = new HashMap<>();
            czjgMap.put("xmid", xmids);
            bdcCzrzDO.setCzjg(CommonUtil.subLongStr(JSON.toJSONString(czjgMap)));
            // 记录数据库日志内容
            this.bdcCzrzFeignService.addBdcCzrz(bdcCzrzDO);
        }
    }

    /**
     * 获取方法中的参数工作流实例ID，在根据工作流实例ID查询项目ID信息
     */
    private List<String> getXmidListByParamGzlslid(JSONObject argsJson){
        if (null != argsJson && !argsJson.isEmpty()) {
            String gzlslid = "";
            for(String paramName : GZLSLID_PARAM_NAME){
                if(argsJson.containsKey(paramName)){
                    gzlslid = (String) argsJson.get(paramName);
                    break;
                }
            }
            if(StringUtils.isNotBlank(gzlslid)){
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                if(CollectionUtils.isNotEmpty(bdcXmDTOList)){
                    return bdcXmDTOList.stream().map(t-> t.getXmid()).collect(Collectors.toList());
                }
            }
        }
        return null;
    }
}
