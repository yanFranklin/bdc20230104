package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlYydjDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYydjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlYydjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.QlfQlQsztService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 国家接入-异议登记信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataYydjImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataYydjImpl.class);

    @Autowired
    private QlfQlYydjMapper qlfQlYydjMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    /**
     * 注销类djxl
     */
    @Value("${excludeDjxl.yyzx:}")
    private String excludeDjxl;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlYydjDO> qlfQlYydjList = qlfQlYydjMapper.queryQlfQlYydjList(map);
            if (CollectionUtils.isEmpty(qlfQlYydjList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlYydjList = qlfQlYydjMapper.queryQlfQlYydjList(map);
            }
            //查项目表djxl，判断是否是注销
            BdcXmDO xmDO = bdcXmMapper.queryBdcXm(ywh);
            if (CollectionUtils.isNotEmpty(qlfQlYydjList)) {
                List<QlfQlYydjDO> qlfQlYydjDOList = Collections.singletonList(qlfQlYydjList.get(0));
                if (CollectionUtils.size(qlfQlYydjList) > 1) {
                    //查询到多条权利数据发出预警
                    sendMsg(ywh);
                }
                for (QlfQlYydjDO qlfQlYydjDO : qlfQlYydjDOList) {
                    if (qxdmConvert) {
                        //需要根据qxdm进行对照
                        qlfQlYydjDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfQlYydjDO.getQxdm()));
                    }
                    if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlYydjDO.getBdcqzh())) {
                        qlfQlYydjDO.setBdcqzh(qlfQlYydjDO.getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                    }
                    if (newDefault) {

                        ClassHandleUtil.setDefaultValueToNullField(qlfQlYydjDO);
                    }
                    //djxl判断是否是注销业务
                    if (StringUtils.isNotBlank(excludeDjxl) && !Arrays.asList(excludeDjxl.split(",")).contains(xmDO.getDjxl())) {
                        LOGGER.info("异议注销业务！djxl{}", xmDO.getDjxl());
                        qlfQlYydjDO.setZxyydbr(null);
                        qlfQlYydjDO.setZxyydjsj(null);
                        qlfQlYydjDO.setZxyyywh(null);
                        qlfQlYydjDO.setZxyyyy(null);
                    }
                }
                setData(dataModel, qlfQlYydjDOList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            Map<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlYydjOldDO> qlfQlYydjList = qlfQlYydjMapper.queryQlfQlYydjListOld(map);
            if (CollectionUtils.isEmpty(qlfQlYydjList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlYydjList = qlfQlYydjMapper.queryQlfQlYydjListOld(map);
            }
            //查项目表djxl，判断是否是注销
            BdcXmDO xmDO = bdcXmMapper.queryBdcXm(ywh);
            if (CollectionUtils.isNotEmpty(qlfQlYydjList)) {
                setDataOld(dataModel, qlfQlYydjList);
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
        return "QLF_QL_YYDJ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlYydjList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfQlYydjList();
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
            dataModel.setQlfQlYydjList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfQlYydjList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlYydjList())) {
            for (QlfQlYydjDO qlfQlYydjDO : dataModel.getQlfQlYydjList()) {
                qlfQlYydjDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
