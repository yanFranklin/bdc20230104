package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlCfdjDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlCfdjMapper;
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
 * 国家接入-查封登记信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataCfdjImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService, QlfQlQsztService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataCfdjImpl.class);

    @Autowired
    private QlfQlCfdjMapper qlfQlCfdjMapper;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 注销类djxl
     */
    @Value("${excludeDjxl.cfjf:}")
    private String excludeDjxl;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<QlfQlCfdjDO> qlCfdjList = qlfQlCfdjMapper.queryQlfQlCfdjList(map);
            if (CollectionUtils.isEmpty(qlCfdjList)) {
                map.clear();
                map.put("yywh", ywh);
                qlCfdjList = qlfQlCfdjMapper.queryQlfQlCfdjList(map);
            }
            //查项目表djxl，判断是否是注销
            BdcXmDO xmDO = bdcXmMapper.queryBdcXm(ywh);
            if (CollectionUtils.isNotEmpty(qlCfdjList)) {
                for (QlfQlCfdjDO qlfQlCfdjDO : qlCfdjList) {
                    if (newDefault) {
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlCfdjDO);
                    }
                    //djxl判断是否是注销业务
                    if (StringUtils.isNotBlank(excludeDjxl) && !Arrays.asList(excludeDjxl.split(",")).contains(xmDO.getDjxl())) {
                        LOGGER.info("解封业务！djxl{}", xmDO.getDjxl());
                        qlfQlCfdjDO.setJfdbr(null);
                        qlfQlCfdjDO.setJfdjsj(null);
                        qlfQlCfdjDO.setJfjg(null);
                        qlfQlCfdjDO.setJfwh(null);
                        qlfQlCfdjDO.setJfwj(null);
                        qlfQlCfdjDO.setJfywh(null);
                    }
                }
                setData(dataModel, qlCfdjList);
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
        return "QLF_QL_CFDJ";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getQlfQlCfdjList();
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
            dataModel.setQlfQlCfdjList(dataList);
        }
    }

    @Override
    public void updateQsztByYwh(String ywh) {
        DataModel dataModel = new DataModel();
        dataModel = getAccessDataModel(ywh, dataModel);
        if (dataModel != null && CollectionUtils.isNotEmpty(dataModel.getQlfQlCfdjList())) {
            for (QlfQlCfdjDO qlfQlCfdjDO : dataModel.getQlfQlCfdjList()) {
                qlfQlCfdjDO.setUpdatetime(new Date());
            }
            saveData(dataModel);
        }
    }
}
