package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlDyaqDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlDyaqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlDyaqMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 国家接入-抵押权
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
@Service
public class NationalAccessDataDyaqImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDyaqImpl.class);

    @Autowired
    private QlfQlDyaqMapper qlfQlDyaqMapper;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 抵押注销类djxl
     */
    @Value("${excludeDjxl.dyzx:}")
    private String excludeDjxl;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlDyaqDO> qlfQlDyaqList = qlfQlDyaqMapper.queryQlfQlDyaqList(map);
            if (CollectionUtils.isEmpty(qlfQlDyaqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlDyaqList = qlfQlDyaqMapper.queryQlfQlDyaqList(map);
            }
            //查项目表djxl，判断是否是注销
            BdcXmDO xmDO = bdcXmMapper.queryBdcXm(ywh);
            if (null != xmDO) {
                if (CollectionUtils.isNotEmpty(qlfQlDyaqList)) {
                    List<QlfQlDyaqDO> qlfQlDyaqDOList = Collections.singletonList(qlfQlDyaqList.get(0));
                    if (CollectionUtils.size(qlfQlDyaqList) > 1) {
                        //查询到多条权利数据发出预警
                        sendMsg(ywh);
                    }
                    for (QlfQlDyaqDO dyaqDO : qlfQlDyaqDOList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            dyaqDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", dyaqDO.getQxdm()));
                        }
                        if (turnOnReplacezf && StringUtils.isNotBlank(dyaqDO.getBdcdjzmh())) {
                            dyaqDO.setBdcdjzmh(dyaqDO.getBdcdjzmh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                        }
                        if (turnOnReplacezf && StringUtils.isNotBlank(dyaqDO.getDyr())) {
                            dyaqDO.setDyr(dyaqDO.getDyr().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                        }
                        if (newDefault) {
                            ClassHandleUtil.setDefaultValueToNullField(dyaqDO);
                        }
                        //djxl判断是否是注销业务
                        if (StringUtils.isNotBlank(excludeDjxl) && !Arrays.asList(excludeDjxl.split(",")).contains(xmDO.getDjxl())) {
                            LOGGER.info("非抵押注销业务！djxl{}", xmDO.getDjxl());
                            dyaqDO.setZxdbr(null);
                            dyaqDO.setZxdyywh(null);
                            dyaqDO.setZxsj(null);
                            dyaqDO.setZxdyyy(null);
                        }
                    }
                    setData(dataModel, qlfQlDyaqDOList);
                }
            }

        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlDyaqOldDO> qlfQlDyaqList = qlfQlDyaqMapper.queryQlfQlDyaqListOld(map);
            if (CollectionUtils.isEmpty(qlfQlDyaqList)) {
                map.clear();
                map.put("yywh", ywh);
                qlfQlDyaqList = qlfQlDyaqMapper.queryQlfQlDyaqListOld(map);
            }
            if (CollectionUtils.isNotEmpty(qlfQlDyaqList)) {
                setDataOld(dataModel, qlfQlDyaqList);
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
        return "QLF_QL_DYAQ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlDyaqList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {
        return dataModel.getQlfQlDyaqList();
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
            dataModel.setQlfQlDyaqList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfQlDyaqList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlDyaqList())) {
            for (QlfQlDyaqDO qlfQlDyaqDO : dataModel.getQlfQlDyaqList()) {
                qlfQlDyaqDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
