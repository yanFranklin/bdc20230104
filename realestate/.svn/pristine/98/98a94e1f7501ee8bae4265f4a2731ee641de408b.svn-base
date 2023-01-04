package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QltQlLqDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QltQlLqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QltQlLqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 国家接入-林权
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataLqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataLqImpl.class);

    @Autowired
    private QltQlLqMapper qltQlLqMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<QltQlLqDO> qltQlLqList = qltQlLqMapper.queryQltQlLqList(map);
            if (CollectionUtils.isEmpty(qltQlLqList)) {
                map.clear();
                map.put("yywh", ywh);
                qltQlLqList = qltQlLqMapper.queryQltQlLqList(map);
            }
            if (CollectionUtils.isNotEmpty(qltQlLqList)) {
                List<QltQlLqDO> qltQlLqDOList = Collections.singletonList(qltQlLqList.get(0));
                if (CollectionUtils.size(qltQlLqList) > 1) {
                    //查询到多条权利数据发出预警
                    sendMsg(ywh);
                }
                if (newDefault) {

                    for (QltQlLqDO qltQlLqDO : qltQlLqDOList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qltQlLqDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qltQlLqDO.getQxdm()));
                        }
                        if (turnOnReplacezf && StringUtils.isNotBlank(qltQlLqDO.getBdcqzh())) {
                            qltQlLqDO.setBdcqzh(qltQlLqDO.getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qltQlLqDO);
                    }
                }
                setData(dataModel, qltQlLqDOList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = Maps.newHashMap();
            map.put("ywh", ywh);
            List<QltQlLqOldDO> qltQlLqList = qltQlLqMapper.queryQltQlLqListOld(map);
            if (CollectionUtils.isEmpty(qltQlLqList)) {
                map.clear();
                map.put("yywh", ywh);
                qltQlLqList = qltQlLqMapper.queryQltQlLqListOld(map);
            }
            if (CollectionUtils.isNotEmpty(qltQlLqList)) {

                setDataOld(dataModel, qltQlLqList);
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
        return "QLT_QL_LQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQltQlLqList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQltQlLqList();
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
            dataModel.setQltQlLqList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQltQlLqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQltQlLqList())) {
            for (QltQlLqDO qltQlLqDO : dataModel.getQltQlLqList()) {
                qltQlLqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
