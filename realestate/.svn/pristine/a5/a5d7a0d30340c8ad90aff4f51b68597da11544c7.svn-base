package cn.gtmap.realestate.exchange.service.inf.qzview;


import cn.gtmap.realestate.exchange.core.domain.DvBzcxDo;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt.DvHlwResponseDto;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;

import java.util.List;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-01
 * @description 常州 前置共享视图数据查询
 */
public interface QzViewDataService {

    /**
     * 根据受理编号查询办理状态
     *
     * @param slbh
     * @return
     */
    DvBzcxDo dvBzcxDoByHlwslbh(String hlwslbh);

    /**
     * 根据受理编号查询办理状态
     *
     * @param slbh
     * @return
     */
    DvBzcxDo dvBzcxDoByNwslbh(String nwslbh);

    /**
     * 根据参数查询业务办理数据，参数：roomid，权利人，证件号，坐落等
     *
     * @param param
     * @return
     */
    List<DvHlwResponseDto> dvHlwListByParamMap(GrdacxRequestBody param);

}
