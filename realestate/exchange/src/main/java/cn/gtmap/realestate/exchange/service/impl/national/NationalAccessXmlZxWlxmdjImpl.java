package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlZxdjDO;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlZxdjMapper;
import cn.gtmap.realestate.exchange.service.impl.national.data.NationalAccessDataZxdjImpl;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.util.BeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 注销登记
 *
 * @author xhc
 * @version 1.0, 2015/11/23
 */
public class NationalAccessXmlZxWlxmdjImpl extends NationalAccessXmlAbstractService {
    @Autowired
    private AccessDefaultValueService accessDefaultValueService;

    private Set<NationalAccessDataService> nationalAccessDataZxdjServiceSet;
    @Autowired
    private QlfQlZxdjMapper qlfQlZxdjMapper;

    public void setNationalAccessDataZxdjServiceSet(Set<NationalAccessDataService> nationalAccessDataZxdjServiceSet) {
        this.nationalAccessDataZxdjServiceSet = nationalAccessDataZxdjServiceSet;
    }

    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public String getRecType() {
        return "4000101";
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public Set<NationalAccessDataService> getNationalAccessDataServiceSet() {
        return this.nationalAccessDataZxdjServiceSet;
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 只获取权籍数据的service集合
     * @date : 2022/11/22 8:37
     */
    @Override
    public Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet() {
        return null;
    }

    @Override
    public DataModel getNationalAccessDataModel(String xmid) {
        DataModel dataModel = new DataModel();
        if (StringUtils.contains(xmid, CommonConstantUtils.ZF_YW_DH) && StringUtils.split(xmid, CommonConstantUtils.ZF_YW_DH).length == 2) {
            String yxmid = StringUtils.split(xmid, CommonConstantUtils.ZF_YW_DH)[1];
            //业务号取值--项目历史关系的gxid
            String ywh = StringUtils.split(xmid, CommonConstantUtils.ZF_YW_DH)[0];
            Set<NationalAccessDataService> dataServiceSet = getNationalAccessDataServiceSet();
            if (CollectionUtils.isNotEmpty(dataServiceSet)) {
                for (NationalAccessDataService service : dataServiceSet) {
                    if (service instanceof NationalAccessDataZxdjImpl) {
                        HashMap<String, String> map = new HashMap();
                        map.put("ywh", yxmid);
                        List<QlfQlZxdjDO> qlfQlZxdjList = qlfQlZxdjMapper.queryWlqlQlfQlZxdjList(map);
                        if (CollectionUtils.isNotEmpty(qlfQlZxdjList)) {
                            for (QlfQlZxdjDO qlfQlZxdj : qlfQlZxdjList) {
                                if (StringUtils.isNotBlank(qlfQlZxdj.getZxywh())) {
                                    String zxywh = qlfQlZxdj.getZxywh();
                                    if (zxywh.length() > 20) {
                                        zxywh = zxywh.substring(zxywh.length() - 20);
                                    }
                                    qlfQlZxdj.setZxywh(zxywh);
                                }
                                qlfQlZxdj.setYwh(ywh);
                            }
                        }
                        dataModel.setQlfQlZxdjList(qlfQlZxdjList);
                        accessDefaultValueService.setDefaultValue(service.getData(dataModel));
                    } else {
                        dataModel = service.getAccessDataModel(yxmid, dataModel);
                        if (CollectionUtils.isNotEmpty(service.getData(dataModel))) {
                            for (Object obj : service.getData(dataModel)) {
                                BeanUtil.setEntityFieldValue(String.class, "ywh", obj, ywh);
                            }
                        }
                        // 赋默认值操作
                        accessDefaultValueService.setDefaultValue(service.getData(dataModel));
                    }
                }
            }
        } else {
            super.getNationalAccessDataModel(xmid);
        }

        return dataModel;
    }

    /**
     * @param qjsjjcDTO
     * @return
     */
    @Override
    public QjsjDataModel getQjsjDataModel(QjsjjcDTO qjsjjcDTO) {
        return null;
    }
}
