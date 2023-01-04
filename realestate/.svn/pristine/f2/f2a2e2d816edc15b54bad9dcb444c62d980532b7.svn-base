package cn.gtmap.realestate.exchange.service.national;

import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessRequestDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlNationalAccessResponseDTO;
import cn.gtmap.realestate.exchange.core.vo.WsxNationalAccessVO;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-24
 * @description 外市县汇交相关服务
 */
public interface WsxNationalAccessService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qxdm
     * @param ywh
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.exchange.MessageModel
     * @description 获取外市县的上报结构
     */
    MessageModel getWsxMessageModel(String qxdm, String ywh, String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param wsxNationalAccessVO
     * @return void
     * @description 批量执行外市县上报
     */
    void wsxPlNationalAccess(WsxNationalAccessVO wsxNationalAccessVO);

}
