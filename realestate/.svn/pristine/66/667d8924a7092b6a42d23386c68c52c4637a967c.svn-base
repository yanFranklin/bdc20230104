package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.dto.portal.BdcZfhyzDTO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.portal.ui.core.dto.ForwardYzDTO;
import cn.gtmap.realestate.portal.ui.core.dto.WorkFlowDTO;

import java.util.List;
import java.util.concurrent.Future;

public interface BdcForwardService {
    /**
     * 转发验证 <br/>
     * 规则验证和必填项验证
     *
     * @param workFlowDTO 转发数据
     * @param autoSign    是否自动签名
     * @return 验证结果
     */
    List<Future<ForwardYzDTO>> zfyz(WorkFlowDTO workFlowDTO, boolean autoSign);

    /**
     * 未办结流程补偿事件
     *
     * @param bdcZsXmDTOS 不动产项目 DTO
     * @param szrid       缮证人 id
     */
    void makeUpWbjyw(List<BdcZsXmDTO> bdcZsXmDTOS, String szrid);

    /**
     * 转发后验证 <br/>
     * 规则验证
     *
     * @return 验证结果
     */
    List<BdcGzyzVO> zfhyz(BdcZfhyzDTO bdcZfhyzDTO);

    /**
     * @param workFlowDTO,autoSign
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发验证必填项
     * @date : 2021/8/26 16:00
     */
    List<Future<ForwardYzDTO>> zfyzBtx(WorkFlowDTO workFlowDTO, boolean autoSign);

    /**
     * @param workFlowDTO,autoSign
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 转发验证规则
     * @date : 2021/8/26 16:05
     */
    List<Future<ForwardYzDTO>> zfyzZhgz(WorkFlowDTO workFlowDTO, boolean autoSign);
}
