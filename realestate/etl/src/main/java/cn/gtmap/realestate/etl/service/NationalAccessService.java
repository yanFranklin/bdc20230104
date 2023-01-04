package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.etl.EtlBatchNationalAccessRequestDTO;

import java.util.Map;

public interface NationalAccessService {
    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过业务号处理
     */
    boolean disposeByYwh(String ywh,String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return void
     * @description 批量上报
     */
    void plAccess(EtlBatchNationalAccessRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param map YWH  和 BDCDYH
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @description 获取信息实体
     */
    MessageModel getMessageModel(Map map);

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过业务号维护flag字段
     */
    void updateFlagByYwh(String ywh,String bdcdyh, boolean success);
}
