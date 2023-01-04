package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlGzdjDO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlGzdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-更正登记信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataGzdjImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZxdjImpl.class);

    @Autowired
    private QlfQlGzdjMapper qlfQlGzdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);

            List<QlfQlGzdjDO> qlfQlGzdjDOList = qlfQlGzdjMapper.queryQlfQlGzdjList(map);

            if (CollectionUtils.isNotEmpty(qlfQlGzdjDOList)) {
                List<QlfQlGzdjDO> qlfQlGzdjDOS = Collections.singletonList(qlfQlGzdjDOList.get(0));
                if (CollectionUtils.size(qlfQlGzdjDOList) > 1) {
                    //查询到多条权利数据发出预警
                    sendMsg(ywh);
                }
                if (newDefault) {

                    for (QlfQlGzdjDO qlfQlGzdjDO : qlfQlGzdjDOS) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qlfQlGzdjDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfQlGzdjDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlGzdjDO);
                    }
                }
                setData(dataModel, qlfQlGzdjDOS);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {

        return dataModel;
    }

    @Override
    public List<AccessData> getAccessData(String ywh) {
        return Collections.emptyList();
    }

    @Override
    public String getAccessDataTagName() {
        return "QLF_QL_GZDJ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlZxdjList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfQlZxdjList();
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
            dataModel.setQlfQlGzdjDOList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
    }

}
