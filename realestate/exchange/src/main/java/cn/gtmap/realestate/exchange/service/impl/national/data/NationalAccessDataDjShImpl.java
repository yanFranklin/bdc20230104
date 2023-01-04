package cn.gtmap.realestate.exchange.service.impl.national.data;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjShDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjShOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjShMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.gx.GxDataDbByPkServiceImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 国家接入-登记审核信息
 *
 * @author lst
 * @version 1.0, 2015/11/20
 */
public class NationalAccessDataDjShImpl extends GxDataDbByPkServiceImpl implements NationalAccessDataService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(NationalAccessDataDjShImpl.class);

    @Autowired
    private DjfDjShMapper djfDjShMapper;

    @Autowired
    private BdcXmFeignService xmFeignService;

    /*  @Value("${dbxx.jdmc}")
      private String jdmc;*/
    @Value("${shxx.one: false}")
    private boolean shxxOne;

    @Override
    public DataModel getAccessDataModel(String ywh, DataModel dataModel) {
        List<DjfDjShDO> djfDjShList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            if (shxxOne) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(ywh);
                List<BdcXmDO> bdcXmDOList = xmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    DjfDjShDO djfDjShDO = new DjfDjShDO();
                    djfDjShDO.setShryxm(bdcXmDOList.get(0).getDbr());
                    djfDjShDO.setShjssj(bdcXmDOList.get(0).getDjsj());
                    djfDjShDO.setYwh(ywh);
                    djfDjShDO.setQxdm(bdcXmDOList.get(0).getBdcdyh().substring(0, 6));
                    djfDjShDO.setSxh("1");
                    djfDjShDO.setCzjg("1");
                    djfDjShDO.setJdmc("审核");
                    djfDjShDO.setShyj("审核通过");
                    djfDjShDO.setShkssj(bdcXmDOList.get(0).getDjsj());
                    djfDjShList.add(djfDjShDO);
                }
            } else {
                HashMap<String, String> map = new HashMap();
                map.put("ywh", ywh);
                djfDjShList = djfDjShMapper.queryDjfDjShList(map);

            }
            if (CollectionUtils.isNotEmpty(djfDjShList)) {
                if (newDefault) {
                    for (DjfDjShDO djfDjShDO : djfDjShList) {
                        if (qxdmConvert) {
                            //需要根据qxdm进行对照
                            djfDjShDO.setQxdm(commonService.bdcDmToDsfZdNvl("ACCESS_QXDM", "ACCESS", djfDjShDO.getQxdm()));
                        }
                        ClassHandleUtil.setDefaultValueToNullField(djfDjShDO);
                    }
                }

            }
            setData(dataModel, djfDjShList);

        }
        return dataModel;
    }

    @Override
    public DataModelOld getAccessDataModelOld(String ywh, DataModelOld dataModel) {
        if (!ObjectUtils.isEmpty(dataModel) && StringUtils.isNotBlank(ywh)) {
            HashMap<String, String> map = new HashMap();
            map.put("ywh", ywh);
            List<DjfDjShOldDO> djfDjShList = djfDjShMapper.queryDjfDjShListOld(map);
            if (CollectionUtils.isNotEmpty(djfDjShList)) {
                setDataOld(dataModel, djfDjShList);
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
        return "DJF_DJ_SH";
    }

    /**
     * @param dataModel
     * @return java.util.List<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 从 dataModel 中获取单表数据
     */
    @Override
    public List getData(DataModel dataModel) {
        return dataModel.getDjfDjShList();
    }

    @Override
    public List getDataOld(DataModelOld dataModel) {

        return dataModel.getDjfDjShList();
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
            dataModel.setDjfDjShList(dataList);
        }
    }

    @Override
    public void setDataOld(DataModelOld dataModel, List dataList) {
        if (dataModel != null) {
            dataModel.setDjfDjShList(dataList);
        }
    }
}
