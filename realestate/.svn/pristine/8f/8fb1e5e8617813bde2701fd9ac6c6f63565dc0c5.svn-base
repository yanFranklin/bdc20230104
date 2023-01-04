package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.AccessData;
import cn.gtmap.realestate.exchange.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.domain.exchange.QlfQlYgdjDO;
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
                for (QlfQlYgdjDO qlfQlYgdjDO : qlfQlYgdjList) {
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
                setData(dataModel, qlfQlYgdjList);
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
