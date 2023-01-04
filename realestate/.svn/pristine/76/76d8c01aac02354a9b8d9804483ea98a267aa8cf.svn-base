package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlYgdjDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYgdjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlYgdjMapper;
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
 * 国家接入-预告登记信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataYgdjImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataYgdjImpl.class);

    @Autowired
    private BdcXmMapper bdcXmMapper;
    /**
     * 注销类djxl
     */
    @Value("${excludeDjxl.ygzx:}")
    private String excludeDjxl;

    @Autowired
    private QlfQlYgdjMapper qlfQlYgdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<QlfQlYgdjDO> qlfQlYgdjList = null;
            map.put("ywh", ywh);
            qlfQlYgdjList = qlfQlYgdjMapper.queryQlfQlYgdjList(map);
            if (CollectionUtils.isEmpty(qlfQlYgdjList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlYgdjList = qlfQlYgdjMapper.queryQlfQlYgdjList(map);
            }
            //查项目表djxl，判断是否是注销
            BdcXmDO xmDO = bdcXmMapper.queryBdcXm(ywh);
            if (CollectionUtils.isNotEmpty(qlfQlYgdjList)) {
                List<QlfQlYgdjDO> qlfQlYgdjDOList = Collections.singletonList(qlfQlYgdjList.get(0));
                if (CollectionUtils.size(qlfQlYgdjList) > 1) {
                    //查询到多条权利数据发出预警
                    sendMsg(ywh);
                }

                for (QlfQlYgdjDO qlfQlYgdjDO : qlfQlYgdjDOList) {
                    if (qxdmConvert) {
                        //需要根据qxdm进行对照
                        qlfQlYgdjDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfQlYgdjDO.getQxdm()));
                    }
                    if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlYgdjDO.getBdcdjzmh())) {
                        qlfQlYgdjDO.setBdcdjzmh(qlfQlYgdjDO.getBdcdjzmh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                    }
                    if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlYgdjDO.getYwr())) {
                        qlfQlYgdjDO.setYwr(qlfQlYgdjDO.getYwr().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                    }
                    if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlYgdjDO.getYwrzjh())) {
                        qlfQlYgdjDO.setYwrzjh(qlfQlYgdjDO.getYwrzjh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                    }
                    if (newDefault) {

                        ClassHandleUtil.setDefaultValueToNullField(qlfQlYgdjDO);
                    }
                    //djxl判断是否是注销业务
                    if (StringUtils.isNotBlank(excludeDjxl) && !Arrays.asList(excludeDjxl.split(",")).contains(xmDO.getDjxl())) {
                        LOGGER.info("预告注销业务！djxl{}", xmDO.getDjxl());
                        qlfQlYgdjDO.setZxsj(null);
                        qlfQlYgdjDO.setZxygywh(null);
                        qlfQlYgdjDO.setZxygyy(null);
                    }
                }
                setData(dataModel, qlfQlYgdjDOList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            List<QlfQlYgdjOldDO> qlfQlYgdjList = null;
            map.put("ywh", ywh);
            qlfQlYgdjList = qlfQlYgdjMapper.queryQlfQlYgdjListOld(map);
            if (CollectionUtils.isEmpty(qlfQlYgdjList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlYgdjList = qlfQlYgdjMapper.queryQlfQlYgdjListOld(map);
            }
            //查项目表djxl，判断是否是注销
            BdcXmDO xmDO = bdcXmMapper.queryBdcXm(ywh);
            if (CollectionUtils.isNotEmpty(qlfQlYgdjList)) {
                setDataOld(dataModel, qlfQlYgdjList);
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
        return "QLF_QL_YGDJ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlYgdjList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfQlYgdjList();
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
            dataModel.setQlfQlYgdjList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfQlYgdjList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlYgdjList())) {
            for (QlfQlYgdjDO qlfQlYgdjDO : dataModel.getQlfQlYgdjList()) {
                qlfQlYgdjDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
