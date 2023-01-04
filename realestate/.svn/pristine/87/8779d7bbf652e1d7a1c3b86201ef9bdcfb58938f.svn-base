package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.bjxx.SbdjResquestBjxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.slxx.SbdjResquestSlxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj.SbdjResquestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk.SbdjfkResquestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0  2020-12-14
 * @description 合肥政务
 */
public interface HefeiZwRestService {

    /**
     * @param sbdjfkResquestDTO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 4.2.4申报登记反馈
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefei/zw/sbdjfk")
    SbdjfkResponseDTO sbdjfk(@RequestBody SbdjfkResquestDTO sbdjfkResquestDTO);

    /**
     * 4.3.1 申报登记
     *
     * @param resquestSbjbxxDTO
     * @return
     * @Date 2022/7/26
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/zw/sbdj")
    SbdjfkResponseDTO sbdj(@RequestBody SbdjResquestDTO resquestSbjbxxDTO);

    /**
     * 4.3.3 申报登记_受理或删除信息推送
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/zw/sbdj/slxx")
    SbdjfkResponseDTO sbdjSlxx(@RequestBody SbdjResquestSlxxDTO slxxDTO);

    /**
     * 4.3.8	办结
     *
     * @param processInsId
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/zw/sbdj/bjxx")
    SbdjfkResponseDTO sbdjBjxx(@RequestBody SbdjResquestBjxxDTO bjxxDTO);
}
