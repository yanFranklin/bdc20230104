package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjFzDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjtDjSlsqDO;
import cn.gtmap.realestate.common.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.etl.core.mapper.DataModelMapper;
import cn.gtmap.realestate.etl.core.mapper.DjtDJSlsqMapper;
import cn.gtmap.realestate.etl.core.mapper.EtlQueryDataModelMapper;
import cn.gtmap.realestate.etl.service.EtlMessageModelService;
import cn.gtmap.realestate.etl.util.Constants;
import cn.gtmap.realestate.etl.util.EtlDataModelEnum;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class EtlMessageModelServiceImpl implements EtlMessageModelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtlMessageModelServiceImpl.class);

    @Autowired
    private DjtDJSlsqMapper djtDJSlsqMapper;
    @Autowired
    private DataModelMapper dataModelMapper;


    private static Integer count = 0;

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据业务号整理DataModel
     */
    @Override
    public DataModel initDataModel(Map paramMap) {
        DataModel dataModel = new DataModel();
        if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "ywh"))) {
            EtlDataModelEnum[] modelEnum = EtlDataModelEnum.values();
            for (int i = 0; i < modelEnum.length; i++) {
                try {
                    Map map = new HashMap();
                    map.put("ywh", MapUtils.getString(paramMap, "ywh"));
                    LOGGER.info("开始要查询的实体对象为：{}", modelEnum[i].getName(), modelEnum[i].getTableName());
                    if(!ArrayUtils.contains(Constants.NO_BDCDYH_CLASS, modelEnum[i].getModelClass())){
                        LOGGER.info("增加了bdcdyh查询参数！");
                        map.put("bdcdyh", MapUtils.getString(paramMap, "bdcdyh"));
                    }
                    Field field = dataModel.getClass().getDeclaredField(modelEnum[i].getName());
                    field.setAccessible(true);
                    field.set(dataModel, selectDataModelByMap(map, modelEnum[i]));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataModel;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据业务号整理HeadModel
     */
    @Override
    public HeadModel initHeadModel(Map paramMap, DjtDjSlsqDO djtDjSlsqDO) {
        HeadModel headModel = null;
        if (StringUtils.isNotBlank(MapUtils.getString(paramMap, "ywh"))) {
            String asId = Constants.ASID;
            String rightType = "";
            String regType = "";
            Date createDate = null;
            String ywh = MapUtils.getString(paramMap, "ywh");
            String parceId = "";
            String estateNum = "";
            String preEstateNum = "";
            String preCerId = "";
            Integer certCount = 0;
            Integer proofCount = 0;
            Map map = new HashMap();
            map.put("ywh", ywh);
            EtlDataModelEnum etlDataModelEnum = EtlDataModelEnum.valueOf("DJFDJFZDO");
            List<DjfDjFzDO> djfDjFzList = selectDataModelByMap(map, etlDataModelEnum);
            if (CollectionUtils.isNotEmpty(djfDjFzList)) {
                if (StringUtils.equals("不动产证明", djfDjFzList.get(0).getFzmc())) {
                    certCount = djfDjFzList.get(0).getFzsl();
                } else {
                    proofCount = djfDjFzList.get(0).getFzsl();
                }
            }
            // 处理参数
            headModel = new HeadModel();
            Map<String, String> djxlZdMap = dataModelMapper.getQllxByDjxl(djtDjSlsqDO.getDjxl());
            rightType = MapUtils.getString(djxlZdMap, "QLLX");
            String recType = MapUtils.getString(djxlZdMap, "YWDM");
            if (StringUtils.isNotBlank(djtDjSlsqDO.getDjlx())) {
                regType = djtDjSlsqDO.getDjlx();
            }
            if (!ObjectUtils.isEmpty(djtDjSlsqDO.getJssj())) {
                createDate = djtDjSlsqDO.getJssj();
            }
            estateNum = MapUtils.getString(paramMap, "bdcdyh");
            parceId = estateNum.substring(0, 19);
            headModel.setAreaCode(djtDjSlsqDO.getQxdm());
            headModel.setASID(org.apache.commons.lang.StringUtils.isNotBlank(asId) ? asId : "/");
            headModel.setRightType(org.apache.commons.lang.StringUtils.isNotBlank(rightType) ? rightType : "/");
            headModel.setRegType(org.apache.commons.lang.StringUtils.isNotBlank(regType) ? regType : "/");
            headModel.setRecType(StringUtils.isNotBlank(recType) ? recType : djtDjSlsqDO.getDjxl());
            headModel.setCreateDate(createDate);
            headModel.setRecFlowID(ywh);
            headModel.setRegOrgID(djtDjSlsqDO.getQxdm());
            headModel.setParcelID(org.apache.commons.lang.StringUtils.isNotBlank(parceId) ? parceId : "/");
            headModel.setEstateNum(org.apache.commons.lang.StringUtils.isNotBlank(estateNum) ? estateNum : "/");
            headModel.setPreEstateNum(org.apache.commons.lang.StringUtils.isNotBlank(preEstateNum) ? preEstateNum : "/");
            headModel.setPreCertID(org.apache.commons.lang.StringUtils.isNotBlank(preCerId) ? preCerId : "/");
            headModel.setCertCount(org.apache.commons.lang.StringUtils.isNotBlank(certCount.toString()) ? certCount.toString() : "/");
            headModel.setProofCount(org.apache.commons.lang.StringUtils.isNotBlank(proofCount.toString()) ? proofCount.toString() : "/");
        }
        return headModel;
    }

    /**
     * 获取 BizId
     *
     * @return
     */
    private synchronized Integer getBizId() {
        if (count == 0) {
            count = djtDJSlsqMapper.getExchangeBizMsgId();
            if (count == 0) {
                count = 1;
            }
        } else {
            count++;
        }
        return count;
    }

    /**
     * @param map 实体中的字段
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过Map 来查询相应的DataModel实体
     */
    @Override
    public <T> List<T> selectDataModelByMap(Map map, EtlDataModelEnum etlDataModelEnum) {
        map.put("tableName", etlDataModelEnum.getTableName());
        Class mapperClass = etlDataModelEnum.getMapperClass();
        EtlQueryDataModelMapper mapper = dataModelMapper;
        if(mapperClass != null){
            mapper = (EtlQueryDataModelMapper)Container.getBean(etlDataModelEnum.getMapperClass());
        }
        List<Map> mapList = mapper.selectDataModelByMap(map);
        List<T> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(mapList)) {
            for (Map ModelMap : mapList) {
                T t = JSONObject.parseObject(JSONObject.toJSONString(ModelMap), (Class<T>) etlDataModelEnum.getModelClass());
                list.add(t);
            }
        }
        return list;
    }

}
