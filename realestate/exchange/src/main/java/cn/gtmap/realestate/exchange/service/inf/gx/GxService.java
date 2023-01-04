package cn.gtmap.realestate.exchange.service.inf.gx;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.DataModel;
import cn.gtmap.realestate.exchange.core.dto.common.DataModelOld;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description 共享服务
 */
public interface GxService {

    /**
     * @param dataModel
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存共享结构数据
     */
    void saveGxDataModel(DataModel dataModel);

    void saveGxDataModelOld(DataModelOld dataModel);

    /**
     * @param ywh
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 构造 并保存 DJFDJYWXX实体
     */
    void saveDjfDjYwxxDO(String ywh);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param dataModel
     * @return void
     * @description 保存共享结构数据（并返回保存结果情况）
     */
    CommonResponse<List<String>> saveGxDataModelAndReturnResult(DataModel dataModel);
}
