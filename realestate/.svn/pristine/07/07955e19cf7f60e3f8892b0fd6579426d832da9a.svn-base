package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QltQlGjzwsyqDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QltQlGjzwsyqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 国家接入-构(建)筑物所有权
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataGjzwsyqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataGjzwsyqImpl.class);

    @Autowired
    private QltQlGjzwsyqMapper qltQlGjzwsyqMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<Map> mapList = qltQlGjzwsyqMapper.queryQltQlGjzwsyqList(map);
            if(CollectionUtils.isEmpty(mapList)){
                map.clear();
                map.put("yywh", ywh);
                mapList = qltQlGjzwsyqMapper.queryQltQlGjzwsyqList(map);
            }
            if(CollectionUtils.isNotEmpty(mapList)){
                List<QltQlGjzwsyqDO> qltQlGjzwsyqList = new ArrayList<>();
                for(Map xmMap : mapList){
                    String jgsj = MapUtils.getString(xmMap,"JGSJ");
                    Date jgsjDate = DateUtils.formatDate(jgsj);
                    xmMap.remove("JGSJ");
                    QltQlGjzwsyqDO gjzwsyqDO = JSONObject.parseObject(JSONObject.toJSONString(xmMap),QltQlGjzwsyqDO.class);
                    gjzwsyqDO.setJgsj(jgsjDate);
                    qltQlGjzwsyqList.add(gjzwsyqDO);
                }
                if (CollectionUtils.isNotEmpty(qltQlGjzwsyqList)) {
                    if (newDefault) {

                        for (QltQlGjzwsyqDO qltQlGjzwsyqDO : qltQlGjzwsyqList) {
                            ClassHandleUtil.setDefaultValueToNullField(qltQlGjzwsyqDO);
                        }
                    }
                    setData(dataModel, qltQlGjzwsyqList);
                }
            }
        }
        return dataModel;
    }

    @Override
    public List<AccessData> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "QLT_QL_GJZWSYQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQltQlGjzwsyqList();
    }

    /**
     * @param dataModel
     * @param dataList
     * @return java.util.List
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public void setData(DataModel dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQltQlGjzwsyqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQltQlGjzwsyqList())) {
            for (QltQlGjzwsyqDO qltQlGjzwsyqDO : dataModel.getQltQlGjzwsyqList()) {
                qltQlGjzwsyqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
