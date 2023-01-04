package cn.gtmap.realestate.etl.core.service;

import cn.gtmap.realestate.etl.core.domian.ClfHtbaDo;
import cn.gtmap.realestate.etl.core.domian.SpfHtbaDo;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020/05/12
 * @description 房产交易合同信息数据服务
 */
public interface FcjyHtxxService {
    /**
     * @param htbh
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 通过规则创建不动产单元号
     */
    ClfHtbaDo queryClfHtbaDoByHtbh(String htbh,String yjybh,String fwbm);

    /**
     * @param htbh
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过逻辑幢主键创建房屋不动产单元号
     */
    SpfHtbaDo querySpfHtbaDoByHtbh(String htbh,String fwbm);
}
