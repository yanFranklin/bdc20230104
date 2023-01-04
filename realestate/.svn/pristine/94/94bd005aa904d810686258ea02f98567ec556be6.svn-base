package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.common.core.domain.exchange.QjsjDataModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessQjsjService;
import cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-19
 * @description 处理上报数据的服务的抽象服务
 */
public abstract class NationalAccessXmlAbstractService implements NationalAccessXmlService {

    @Autowired
    private AccessDefaultValueService accessDefaultValueService;


    /**
     * 当前业务编码
     *
     * @return
     */
    @Override
    public abstract String getRecType();

    /**
     * @param xmid
     * @rerutn
     * @description 根据业务号和不动产单元号获取国家级接入平台Model数据
     */
    @Override
    public DataModel getNationalAccessDataModel(String xmid){
        DataModel dataModel = new DataModel();
        Set<NationalAccessDataService> dataServiceSet = getNationalAccessDataServiceSet();
        if (CollectionUtils.isNotEmpty(dataServiceSet)) {
            for (NationalAccessDataService service : dataServiceSet) {
                dataModel = service.getAccessDataModel(xmid, dataModel);
                // 赋默认值操作
                accessDefaultValueService.setDefaultValue(service.getData(dataModel));
            }
        }
        return dataModel;
    }

    @Override
    public QjsjDataModel getQjsjDataModel(QjsjjcDTO qjsjjcDTO) {
        QjsjDataModel dataModel = new QjsjDataModel();
        Set<NationalAccessQjsjService> qjsjServiceSet = getNationalAccessDataQjsjSet();
        if (CollectionUtils.isNotEmpty(qjsjServiceSet)) {
            for (NationalAccessQjsjService service : qjsjServiceSet) {
                dataModel = service.getAccessQjsjModel(qjsjjcDTO, dataModel);
                // 赋默认值操作
                accessDefaultValueService.setDefaultValue(service.getQjData(dataModel));
            }
        }
        return dataModel;
    }

    @Override
    public DataModelOld getNationalAccessDataModelOld(String xmid) {
        DataModelOld dataModel = new DataModelOld();
        Set<NationalAccessDataService> dataServiceSet = getNationalAccessDataServiceSet();
        if (CollectionUtils.isNotEmpty(dataServiceSet)) {
            for (NationalAccessDataService service : dataServiceSet) {
                dataModel = service.getAccessDataModelOld(xmid, dataModel);
                // 赋默认值操作
                accessDefaultValueService.setDefaultValue(service.getDataOld(dataModel));
            }
        }
        return dataModel;
    }

    /**
     * @return java.util.Set<cn.gtmap.realestate.exchange.service.national.NationalAccessDataService>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取 DATA service
     */
    @Override
    public abstract Set<NationalAccessDataService> getNationalAccessDataServiceSet();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 只获取权籍数据的service集合
     * @date : 2022/11/22 8:37
     */
    @Override
    public abstract Set<NationalAccessQjsjService> getNationalAccessDataQjsjSet();
}
