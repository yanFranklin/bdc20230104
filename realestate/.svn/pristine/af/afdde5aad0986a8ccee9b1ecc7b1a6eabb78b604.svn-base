package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO;
import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.exchange.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.util.AnnotationsUtils;
import cn.gtmap.realestate.exchange.util.CheckEntityUtils;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-19
 * @description 上报默认必填赋值
 */
@Service
public class AccessDefaultValueServiceImpl implements AccessDefaultValueService {

    @Resource(name = "accessDefaultValueDozerMapper")
    private DozerBeanMapper accessDefaultValueDozerMapper;

    @Autowired
    private EntityMapper entityMapper;


    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 默认值表数据集合
     */
    private volatile static List<BdcExchangeDefaultValueDO> bdcExchangeDefaultValueDOList;

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDefaultValueServiceImpl.class);

    /**
     * @param dataList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 上报实体 赋默认值
     */
    @Override
    public void setDefaultValue(List dataList) {
        try {
            if (CollectionUtils.isNotEmpty(dataList)) {
                for (Object obj : dataList) {
                    accessDefaultValueDozerMapper.map(obj, obj);
//                    fillEmptyStrToObject(obj);
                }
            }
        } catch (Exception e) {
            LOGGER.error("上报实体赋默认值异常", e);
        }
    }

    /**
     * @param messageModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 上报实体  读取 默认值配置表 处理默认值
     */
    @Override
    public void setDefaultValueWithDefaultTable(Set<NationalAccessDataService> accessServiceSet,
                                                MessageModel messageModel, boolean useGzldyidFilter) {
        List<BdcExchangeDefaultValueDO> defaultValueDOList = getBdcExchangeDefaultValueDOList();
        if (messageModel.getHeadModel() != null && CollectionUtils.isNotEmpty(defaultValueDOList)) {
            String xmid = messageModel.getHeadModel().getRecFlowID();
            String gzldyid = null;
            String djxl = null;
            if (StringUtils.isNotBlank(xmid) && useGzldyidFilter) {
                BdcXmDO bdcXmDO = bdcXmMapper.queryBdcXm(xmid);
                if (bdcXmDO != null) {
                    gzldyid = bdcXmDO.getGzldyid();
                    djxl = bdcXmDO.getDjxl();
                }
            }
            if (CollectionUtils.isNotEmpty(accessServiceSet)) {
                for (NationalAccessDataService service : accessServiceSet) {
                    List objList = service.getData(messageModel.getDataModel());
                    // 赋默认值操作
                    if (CollectionUtils.isNotEmpty(objList)) {
                        for (Object obj : objList) {
                            setObjDefaulValue(obj, defaultValueDOList, gzldyid, djxl);
                        }
                    }
                }
            }
        }
    }


    /**
     * @param obj
     * @param defaultValueList
     * @param gzldyid
     * @param djxl
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 给实体赋默认值
     */
    @Override
    public void setObjDefaulValue(Object obj, List<BdcExchangeDefaultValueDO> defaultValueList, String gzldyid, String djxl) {
        if (obj != null && CollectionUtils.isNotEmpty(defaultValueList)) {
            String tableName = CommonUtil.getTableName(obj);
            if (StringUtils.isNotBlank(tableName)) {
                for (BdcExchangeDefaultValueDO defaultValue : defaultValueList) {
                    try {
                        if (StringUtils.equalsIgnoreCase(tableName, defaultValue.getTablename()) && StringUtils.isNoneBlank(defaultValue.getFieldname(), defaultValue.getDefaultvalue())) {
                            if (StringUtils.isNotBlank(gzldyid)
                                    && StringUtils.isNotBlank(defaultValue.getGzldyid())
                                    && !StringUtils.equals(gzldyid, defaultValue.getGzldyid())) {
                                continue;
                            }
                            if (StringUtils.isNotBlank(djxl)
                                    && StringUtils.isNotBlank(defaultValue.getDjxl())
                                    && !StringUtils.equals(djxl, defaultValue.getDjxl())) {
                                continue;
                            }
                            Field field = obj.getClass().getDeclaredField(StringUtils.lowerCase(defaultValue.getFieldname()));
                            Object fieldValue = CheckEntityUtils.getFieldValue(field, obj);
                            if (fieldValue == null || StringUtils.isBlank(fieldValue.toString())) {
                                setEntityFieldValue(field, obj, defaultValue.getDefaultvalue());
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
            }
        }
    }


    /**
     * sly 通过反射赋值实体类某个字段的值
     *
     * @param entityField 实体类中的一个字段
     * @param entity      实体类
     * @param value       要赋的值
     */
    private static void setEntityFieldValue(Field entityField, Object entity, String value) {
        if (entityField != null && entity != null) {
            try {
                Object obj = null;
                if (StringUtils.equals("java.lang.Integer", entityField.getType().getName())) {
                    if (StringUtils.isNotBlank(value)) {
                        obj = Integer.parseInt(value);
                    }
                } else if (StringUtils.equals("java.lang.Double", entityField.getType().getName())) {
                    if (StringUtils.isNotBlank(value)) {
                        obj = Double.valueOf(value);
                    }
                } else if (StringUtils.equals("java.util.Date", entityField.getType().getName())) {
                    if (StringUtils.isNotBlank(value)) {
                        obj = DateUtils.formatDate(value);
                    }
                } else if (StringUtils.equals("java.lang.Float", entityField.getType().getName())) {
                    if (StringUtils.isNotBlank(value)) {
                        obj = Float.valueOf(value);
                    }
                } else {
                    obj = value;
                }
                Class[] parameterTypes = new Class[1];
                parameterTypes[0] = entityField.getType();
                StringBuilder sb = new StringBuilder();
                sb.append("set");
                sb.append(entityField.getName().substring(0, 1).toUpperCase());
                sb.append(entityField.getName().substring(1));
                Method method = entity.getClass().getMethod(sb.toString(), parameterTypes);
                method.invoke(entity, obj);
            } catch (Exception e) {
                LOGGER.error("赋值时报错", e);
            }
        }
    }


    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取默认值表数据集
     */
    @Override
    public List<BdcExchangeDefaultValueDO> getBdcExchangeDefaultValueDOList() {
        if (CollectionUtils.isNotEmpty(bdcExchangeDefaultValueDOList)) {
            return bdcExchangeDefaultValueDOList;
        } else {
            return refreshbdcExchangeDefaultValueDOList();
        }
    }

    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取默认值表数据集
     */
    @Override
    public synchronized List<BdcExchangeDefaultValueDO> refreshbdcExchangeDefaultValueDOList() {
        bdcExchangeDefaultValueDOList = entityMapper.selectByExample(new Example(BdcExchangeDefaultValueDO.class));
        return bdcExchangeDefaultValueDOList;
    }

    /**
     * @param object
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description null字段赋值空字符串 数值类型赋值0
     */
    private static void fillEmptyStrToObject(Object object) throws IllegalAccessException {
        List<Field> fieldList = new ArrayList<>();
        AnnotationsUtils.getClassFields(object.getClass(), fieldList);
        if (CollectionUtils.isNotEmpty(fieldList)) {
            for (Field field : fieldList) {
                field.setAccessible(true);
                if (field.getType() == String.class && field.get(object) == null) {
                    field.set(object, "");
                }
                if (field.getType() == Double.class && field.get(object) == null) {
                    field.set(object, new Double(0));
                }
            }
        }
    }
}
