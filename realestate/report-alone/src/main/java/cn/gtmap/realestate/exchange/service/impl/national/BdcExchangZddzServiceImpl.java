package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.HeadModel;
import cn.gtmap.realestate.exchange.core.domain.zd.BdcExchangeZddz;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.national.BdcExchangeZddzService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xutao
 * @Date 2018-12-21
 * @Description
 */
@Service
public class BdcExchangZddzServiceImpl implements BdcExchangeZddzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcExchangZddzServiceImpl.class);
    @Autowired
    BdcdjMapper bdcdjMapper;

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 对所有的表中的字典对象 处理成 国标
     */
    @Override
    public DataModel handleZddz(DataModel dataModel, String xmid) {
        if (dataModel != null) {
            Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap = getBdcExchangeZddzMap();
            if (MapUtils.isNotEmpty(bdcExchangeZddzMap)) {
                List dataList = new ArrayList();
                try {
                    dataList = getDataList(dataModel);
                } catch (InvocationTargetException e) {
                    LOGGER.error("反射获取dataModel数据失败：{}", JSONObject.toJSONString(dataModel));
                } catch (IllegalAccessException e) {
                    LOGGER.error("反射获取dataModel数据失败：{}", JSONObject.toJSONString(dataModel));
                }
                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (Object data : dataList) {
                        doObj(data, bdcExchangeZddzMap, xmid);
                    }
                }
            }
        }
        return dataModel;
    }


    @Override
    public Map<String, List<BdcExchangeZddz>> getBdcExchangeZddzMap() {
        List<BdcExchangeZddz> zddzList = bdcdjMapper.getBdcExchangeZddzList();
        Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(zddzList)) {
            for (BdcExchangeZddz bdcExchangeZddz : zddzList) {
                String field = StringUtils.lowerCase(bdcExchangeZddz.getZdlx());
                if (bdcExchangeZddzMap.get(field) == null) {
                    bdcExchangeZddzMap.put(field, new ArrayList<BdcExchangeZddz>());
                }
                bdcExchangeZddzMap.get(field).add(bdcExchangeZddz);
            }
        }
        return bdcExchangeZddzMap;
    }

    private List getDataList(DataModel dataModel) throws InvocationTargetException, IllegalAccessException {
        List dataList = new ArrayList();
        Method[] methods = dataModel.getClass().getMethods();
        for (Method method : methods) {
            // 获取 GET方法
            if (method.getName().startsWith("get")) {
                Object obj = method.invoke(dataModel);
                if (obj != null && obj instanceof List) {
                    dataList.addAll((List) obj);
                }
            }
        }
        return dataList;
    }


    /**
     * @param headModel 数据头部model
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 对headModel中的字典对象 处理成 国标
     */
    @Override
    public HeadModel handleHeadZddz(HeadModel headModel) {
        if (headModel != null) {
            HashMap map = new HashMap();
            if (StringUtils.isNotBlank(headModel.getRightType())) {
                //权利类型
                map.put("bdcdjdm", headModel.getRightType());
                map.put("zdlx", "qllx");
                BdcExchangeZddz bdcExchangeZddz = bdcdjMapper.getBdcExchangeZddz(map);
                if (bdcExchangeZddz != null) {
                    headModel.setRightType(bdcExchangeZddz.getStddm());
                }
                map.clear();
            }
            if (StringUtils.isNotBlank(headModel.getRecFlowID()) && StringUtils.isNotBlank(headModel.getRegType())) {
                //申请类型
                map.put("bdcdjdm", headModel.getRegType());
                map.put("zdlx", "djlx");
                BdcExchangeZddz bdcExchangeZddz = bdcdjMapper.getBdcExchangeZddz(map);
                if (bdcExchangeZddz != null) {
                    headModel.setRegType(bdcExchangeZddz.getStddm());
                }
                map.clear();
            }
        }
        return headModel;
    }


    /**
     * @param obj     对象
     * @param zddzMap 对照表字典类型list
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 将对象里面的字典，存在对照表，就替换为国标字典
     */
    @Override
    public void doObj(Object obj, Map<String, List<BdcExchangeZddz>> zddzMap, String proid) {
        BdcExchangeZddz bdcExchangeZddz;
        HashMap<String, String> map = new HashMap();
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                //设置访问权限
                //djlx 和 djdl 单独方法获取对照
                if ((StringUtils.equals(field.getName(), "djlx")
                        || StringUtils.equals(field.getName(), "djdl"))) {
                    if (field.get(obj) != null) {
                        map.clear();
                        map.put("bdcdjdm", field.get(obj).toString());
                        map.put("zdlx", "sqlxDzDjlx");
                        bdcExchangeZddz = bdcdjMapper.getBdcExchangeZddz(map);
                        if (bdcExchangeZddz != null) {
                            field.set(obj, bdcExchangeZddz.getStddm());
                        }
                    }
                } else if (CollectionUtils.isNotEmpty(zddzMap.get(field.getName())) && field.get(obj) != null && StringUtils.isNotBlank(field.get(obj).toString())) {
                    for (BdcExchangeZddz bdcExchangeZddzTemp : zddzMap.get(field.getName())) {
                        if (field.get(obj) != null &&
                                bdcExchangeZddzTemp != null && StringUtils.equals(field.get(obj).toString(), bdcExchangeZddzTemp.getBdcdjdm())) {
                            field.set(obj, bdcExchangeZddzTemp.getStddm());
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("errorMsg:", e);
        }
    }


}
