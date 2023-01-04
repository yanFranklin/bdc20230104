package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlNydsyqDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlNydsyqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlNydsyqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-农用地使用权(非林地)
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataNydsyqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataNydsyqImpl.class);

    @Autowired
    private QlfQlNydsyqMapper qlfQlNydsyqMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlNydsyqDO> qlfQlNydsyqList = qlfQlNydsyqMapper.queryQlfQlNydsyqList(map);
            if (CollectionUtils.isEmpty(qlfQlNydsyqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlNydsyqList = qlfQlNydsyqMapper.queryQlfQlNydsyqList(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlNydsyqList)) {
                List<QlfQlNydsyqDO> qlfQlNydsyqDOList = Collections.singletonList(qlfQlNydsyqList.get(0));
                if (CollectionUtils.size(qlfQlNydsyqList) > 1) {
                    //查询到多条权利数据发出预警
                    sendMsg(ywh);
                }
                if (newDefault) {

                    for (QlfQlNydsyqDO qlfQlNydsyqDO : qlfQlNydsyqDOList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qlfQlNydsyqDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfQlNydsyqDO.getQxdm()));
                        }
                        if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlNydsyqDO.getBdcqzh())) {
                            qlfQlNydsyqDO.setBdcqzh(qlfQlNydsyqDO.getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlNydsyqDO);
                    }
                }
                setData(dataModel, qlfQlNydsyqDOList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlNydsyqOldDO> qlfQlNydsyqList = qlfQlNydsyqMapper.queryQlfQlNydsyqListOld(map);
            if (CollectionUtils.isEmpty(qlfQlNydsyqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlNydsyqList = qlfQlNydsyqMapper.queryQlfQlNydsyqListOld(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlNydsyqList)) {
                setDataOld(dataModel, qlfQlNydsyqList);
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
        return "QLF_QL_NYDSYQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlNydsyqList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfQlNydsyqList();
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
            dataModel.setQlfQlNydsyqList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfQlNydsyqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlNydsyqList())) {
            for (QlfQlNydsyqDO qlfQlNydsyqDO : dataModel.getQlfQlNydsyqList()) {
                qlfQlNydsyqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
