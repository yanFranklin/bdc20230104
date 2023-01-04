package cn.gtmap.realestate.exchange.service.impl.inf.gx;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.gx.GxDataDbService;
import cn.gtmap.realestate.exchange.util.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description 根据YWH处理共享库结构
 * （针对YWH与实体为1对多关系场景，如ZttGyQlr）
 */
public abstract class GxDataDbByFkServiceImpl implements GxDataDbService {

    private static Logger LOGGER = LoggerFactory.getLogger(GxDataDbByFkServiceImpl.class);

    @Resource(name = "gxEntityMapper")
    private EntityMapper gxEntityMapper;
    @Value("${data.version:}")
    protected String dataVersion;
    @Value("${switch.datasource.flag:false}")
    protected boolean datasourceSwitch;
    //合肥不需要新版上报默认值
    @Value("${access.newDefault:true}")
    public boolean newDefault;
    //拼接字符替换
    @Value("${accessLog.replacezf:,}")
    private String replaceZf;
    @Value("${accessLog.turn-on-replacezf:false}")
    private boolean turnOnReplacezf;

    //从大云平台读取受理办结时间
    @Value("${access.slsjfromdy:false}")
    public boolean slsjFromWorkflow;

    //qxdm是否需要对照
    @Value("${qxdm.convert:false}")
    protected boolean qxdmConvert;

    @Autowired
    public CommonService commonService;
    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存数据到共享库
     */
    @Override
    public void saveData(DataModel dataModel) {
        List dataList = getData(dataModel);
        if (CollectionUtils.isNotEmpty(dataList)) {
            try {
                // 先删除
                for (Object data : dataList) {
                    List<Field> fkFieldList = AnnotationsUtils.getAnnotationField(data, RequiredFk.class);
                    if (CollectionUtils.isNotEmpty(fkFieldList)) {
                        Example example = new Example(data.getClass());
                        Example.Criteria criteria = example.createCriteria();
                        for (Field field : fkFieldList) {
                            Object fkValue = CheckEntityUtils.getFieldValue(field, data);
                            if (fkValue != null && StringUtils.isNotBlank(fkValue.toString())) {
                                criteria.andEqualTo(field.getName(), fkValue);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(criteria.getAllCriteria())) {
                            gxEntityMapper.deleteByExample(example);
                        }
                    }
                }
                for (Object data : dataList) {
                    Object temp = fillPk(data);
                    if (temp != null) {
                        BeanUtil.setEntityFieldValue(Date.class, "updatetime", temp, new Date());
                        gxEntityMapper.insert(temp);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("插入共享库ByFk报错,插入数据：{}", JSON.toJSONString(dataList), e);
            }
        }
    }

    @Override
    public void saveDataOld(DataModelOld dataModel) {
        List dataList = getDataOld(dataModel);
        if (CollectionUtils.isNotEmpty(dataList)) {
            try {
                // 先删除
                for (Object data : dataList) {
                    List<Field> fkFieldList = AnnotationsUtils.getAnnotationField(data, RequiredFk.class);
                    if (CollectionUtils.isNotEmpty(fkFieldList)) {
                        Example example = new Example(data.getClass());
                        Example.Criteria criteria = example.createCriteria();
                        for (Field field : fkFieldList) {
                            Object fkValue = CheckEntityUtils.getFieldValue(field, data);
                            if (fkValue != null && StringUtils.isNotBlank(fkValue.toString())) {
                                criteria.andEqualTo(field.getName(), fkValue);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(criteria.getAllCriteria())) {
                            gxEntityMapper.deleteByExample(example);
                        }
                    }
                }
                for (Object data : dataList) {
                    Object temp = fillPk(data);
                    if (temp != null) {
                        BeanUtil.setEntityFieldValue(Date.class, "updatetime", temp, new Date());
                        gxEntityMapper.insert(temp);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("插入共享库ByFk报错,插入数据：{}", JSON.toJSONString(dataList), e);
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param data
     * @return java.lang.Object
     * @description 填充主键
     */
    private static Object fillPk(Object data){
        try{
            List<Field> pkFieldedList = AnnotationsUtils.getAnnotationField(data, Id.class);
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(data));
            if(CollectionUtils.isNotEmpty(pkFieldedList)){
                for(Field field : pkFieldedList){
                    if(StringUtils.isBlank(jsonObject.getString(field.getName()))){
                        jsonObject.put(field.getName(), UUIDGenerator.generate());
                    }
                }
            }
            return jsonObject.toJavaObject(data.getClass());
        }catch (Exception e){
            LOGGER.error("保存共享数据填充主键异常:{}",JSONObject.toJSONString(data),e);
        }
        return null;
    }

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存数据到共享库
     */
    @Override
    public CommonResponse<String> saveDataAndReutrnResult(DataModel dataModel) {
        List dataList = getData(dataModel);
        String dealErrorStr = null;
        if(CollectionUtils.isNotEmpty(dataList)){
            try {
                // 先删除
                for (Object data : dataList) {
                    List<Field> fkFieldList = AnnotationsUtils.getAnnotationField(data, RequiredFk.class);
                    if (CollectionUtils.isNotEmpty(fkFieldList)) {
                        Example example = new Example(data.getClass());
                        Example.Criteria criteria = example.createCriteria();
                        for (Field field : fkFieldList) {
                            Object fkValue = CheckEntityUtils.getFieldValue(field, data);
                            if (fkValue != null && StringUtils.isNotBlank(fkValue.toString())) {
                                criteria.andEqualTo(field.getName(), fkValue);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(criteria.getAllCriteria())) {
                            gxEntityMapper.deleteByExample(example);
                        }
                    }
                }
                for (Object data : dataList) {
                    Object temp = fillPk(data);
                    if (temp != null) {
                        BeanUtil.setEntityFieldValue(Date.class, "updatetime", temp, new Date());
                        gxEntityMapper.insert(temp);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("插入共享库ByFk报错,插入数据：{}", JSON.toJSONString(dataList), e);
                dealErrorStr = "插入共享库ByFk报错: " + e.getMessage() + ",插入数据:" + JSON.toJSONString(dataList);
                if (dealErrorStr.length() > 1000){
                    dealErrorStr = "插入共享库ByFk报错: " + e.getMessage() + ",插入数据:" + dataList.get(0).getClass();
                }
            }
        }
        if (StringUtils.isNotBlank(dealErrorStr)){
            return CommonResponse.fail(dealErrorStr);
        }else {
            return CommonResponse.ok();
        }
    }
}
