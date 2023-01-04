package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.etl.EtlBatchNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.exchange.NationalAccessFeignService;
import cn.gtmap.realestate.etl.service.DjtDJSlsqService;
import cn.gtmap.realestate.etl.service.EtlMessageModelService;
import cn.gtmap.realestate.etl.service.NationalAccessService;
import cn.gtmap.realestate.etl.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class NationalAccessServiceImpl implements NationalAccessService {
    @Autowired
    private EtlMessageModelService etlMessageModelService;
    @Autowired
    private DjtDJSlsqService djtDJSlsqService;
    @Autowired
    private NationalAccessFeignService nationalAccessFeignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessServiceImpl.class);

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过业务号处理
     */
    @Override
    @Transactional
    public boolean disposeByYwh(String ywh,String bdcdyh) {
        boolean result = false;
        if(StringUtils.isBlank(ywh)){
            throw new IllegalArgumentException("YWH");
        }

        // 如果只有YWH 需要 查询DJT_DJ_SLSQ表 获取BDCDYH
        Map<String,Object> map = new HashMap<>();
        map.put("ywh",ywh);
        if(StringUtils.isBlank(bdcdyh)){
            bdcdyh = djtDJSlsqService.getBdcdyhByYwh(ywh);
        }
        // 如果BDCDYH 都为空 则不处理
        if(StringUtils.isBlank(bdcdyh)){
            throw new IllegalArgumentException("BDCDYH");
        }
        map.put("bdcdyh",bdcdyh);

        //调用exchange接口
        try {
            LOGGER.info("构造MessageModel，ywh:{}",ywh);
            MessageModel messageModel = getMessageModel(map);
            LOGGER.info("请求上报，ywh:{},messageModel: {} ",ywh,JSONObject.toJSONString(messageModel));
            result = nationalAccessFeignService.autoAccessByMessageModel(messageModel);
            LOGGER.info("请求上报结束，ywh:{},返回结果:{}",ywh,result);
        } catch (Exception e) {
            LOGGER.error("上报失败,param:{} ",JSONObject.toJSONString(map), e);
        }
        //修改djtDJSlsq状态
        if(!result){
            LOGGER.info("上报国家失败，ywh:{},bdcdyh:{}", ywh, bdcdyh);
        }
        updateFlagByYwh(MapUtils.getString(map, "ywh"),MapUtils.getString(map, "bdcdyh"), result);
        LOGGER.info("回写处理状态结束，ywh:{},bdcdyh:{},result:{}",ywh,bdcdyh,result);
        return result;
    }

    /**
     * @param requestDTO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量上报
     */
    @Override
    public void plAccess(EtlBatchNationalAccessRequestDTO requestDTO) {
        if(requestDTO != null
                && CollectionUtils.isNotEmpty(requestDTO.getRequestDTOList())){
            for(EtlNationalAccessRequestDTO dto : requestDTO.getRequestDTOList()){
                try{
                    disposeByYwh(dto.getYwh(),dto.getBdcdyh());
                } catch (Exception e){
                    LOGGER.error("批量上报异常，dto:{}",JSONObject.toJSONString(dto),e);
                }
            }
        }
    }

    /**
     * @param map YWH  和 BDCDYH
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取信息实体
     */
    @Override
    public MessageModel getMessageModel(Map map) {
        MessageModel messageModel = new MessageModel();
        DataModel dataModel = etlMessageModelService.initDataModel(map);
        if(CollectionUtils.isNotEmpty(dataModel.getDjtDjSlsqList())){
            HeadModel headModel = etlMessageModelService.initHeadModel(map, dataModel.getDjtDjSlsqList().get(0));
            messageModel.setDataModel(dataModel);
            messageModel.setHeadModel(headModel);
        }
        return messageModel;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过业务号维护flag字段
     */
    public void updateFlagByYwh(String ywh,String bdcdyh, boolean success) {
        if (StringUtils.isNotBlank(ywh)) {
            Map<String, Object> map = new HashMap<>();
            map.put("ywh", ywh);
            if(StringUtils.isNotBlank(bdcdyh)){
                map.put("bdcdyh", bdcdyh);
            }
            if (success) {
                map.put("dealflag", Constants.DJTDJSLSQ_FLAG_ONE);
            } else {
                map.put("dealflag", Constants.DJTDJSLSQ_FLAG_TWO);
            }
            djtDJSlsqService.updateFlagByYwh(map);
        }
    }
}
