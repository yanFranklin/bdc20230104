package cn.gtmap.realestate.exchange.service.inf.gx;


import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;
import cn.gtmap.realestate.exchange.service.national.NationalAccessDataService;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description 共享库数据 数据库处理服务
 */
public interface GxDataDbService extends NationalAccessDataService {

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存数据到共享库
     */
    void saveData(DataModel dataModel);

    void saveDataOld(DataModelOld dataModel);

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 保存数据到共享库(并返回成功情况)
     */
    CommonResponse<String> saveDataAndReutrnResult(DataModel dataModel);
}
