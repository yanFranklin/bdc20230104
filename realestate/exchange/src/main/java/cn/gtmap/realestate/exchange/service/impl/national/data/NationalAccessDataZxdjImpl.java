package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlZxdjDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlZxdjOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlZxdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-注销登记
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataZxdjImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataZxdjImpl.class);

    @Autowired
    private QlfQlZxdjMapper qlfQlZxdjMapper;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);

            List<QlfQlZxdjDO> qlfQlZxdjList = qlfQlZxdjMapper.queryQlfQlZxdjList(map);

            /**
             * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
             * @description 根据国家上报标准zxywh大于20截取前20位
             */
            if (CollectionUtils.isNotEmpty(qlfQlZxdjList)) {
                for (QlfQlZxdjDO qlfQlZxdj : qlfQlZxdjList) {
                    if (StringUtils.isNotBlank(qlfQlZxdj.getZxywh())) {
                        String zxywh = qlfQlZxdj.getZxywh();
                        if (zxywh.length() > 20) {
                            zxywh = zxywh.substring(zxywh.length() - 20);
                        }
                        qlfQlZxdj.setZxywh(zxywh);
                    }
                    if (turnOnReplacezf && StringUtils.isNotBlank(qlfQlZxdj.getBdcqzh())) {
                        qlfQlZxdj.setBdcqzh(qlfQlZxdj.getBdcqzh().replaceAll(CommonConstantUtils.ZF_YW_DH, replaceZf));
                    }
                }
            }

            /**
             * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
             * @description 根据bdcdyh为空和xmly值来判断项目来源于过渡并且不匹配不动产单元
             */
            if (CollectionUtils.isNotEmpty(qlfQlZxdjList)) {
                if (newDefault) {

                    for (QlfQlZxdjDO qlfQlZxdjDO : qlfQlZxdjList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            qlfQlZxdjDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", qlfQlZxdjDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(qlfQlZxdjDO);
                    }
                }
                setData(dataModel, qlfQlZxdjList);
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);

            List<QlfQlZxdjOldDO> qlfQlZxdjList = qlfQlZxdjMapper.queryQlfQlZxdjListOld(map);

            /**
             * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
             * @description 根据国家上报标准zxywh大于20截取前20位
             */
            if (CollectionUtils.isNotEmpty(qlfQlZxdjList)) {
                for (QlfQlZxdjOldDO qlfQlZxdj : qlfQlZxdjList) {
                    if (StringUtils.isNotBlank(qlfQlZxdj.getZxywh())) {
                        String zxywh = qlfQlZxdj.getZxywh();
                        if (zxywh.length() > 20) {
                            zxywh = zxywh.substring(zxywh.length() - 20);
                        }
                        qlfQlZxdj.setZxywh(zxywh);
                    }
                }
            }

            /**
             * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
             * @description 根据bdcdyh为空和xmly值来判断项目来源于过渡并且不匹配不动产单元
             */
            if (CollectionUtils.isNotEmpty(qlfQlZxdjList)) {
                setDataOld(dataModel, qlfQlZxdjList);
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
        return "QLF_QL_ZXDJ";
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
            dataModel.setQlfQlZxdjList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setQlfQlZxdjList(dataList);
        }
    }
}
