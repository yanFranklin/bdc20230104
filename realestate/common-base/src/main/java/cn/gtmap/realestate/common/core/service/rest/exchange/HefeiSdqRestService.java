package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request.HefeiDianSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.request.DianYhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.response.HefeiDianYhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.request.HefeiRanqiGhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.HefeiWnRanqiGhJgRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.RanqiSqghCommonRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiWnRanqiGhJgResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.RanqiSqghCommonResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.request.HefeiShuifeicxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.request.HefeiShuiSqghRequestTransferPerson;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-20
 * @description 合肥 水、 电、 气 相关接口
 */
public interface HefeiSdqRestService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response.HefeiRanqiGhcxResponseDTO
     * @description 燃气 过户查询
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/ranqi/ghcx")
    RanqiSqghCommonResponseDTO ranqiGhcx(@RequestBody HefeiRanqiGhcxRequestDTO requestDTO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiRanqiSqghResponseDTO
     * @description  燃气申请过户
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/ranqi/sqgh")
    RanqiSqghCommonResponseDTO ranqiSqgh(@RequestBody RanqiSqghCommonRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiWnRanqiSqghResponseDTO
     * @description  皖能燃气申请过户结果更新
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/ranqi/wn/ghjg")
    HefeiWnRanqiGhJgResponseDTO wnRanqiGhJg(@RequestBody HefeiWnRanqiGhJgRequestDTO requestDTO);



    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.response.HefeiDianYhcxResponseData
     * @description 电 过户查询
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/dian/ghcx")
    HefeiDianYhcxResponseDTO dianGhcx(@RequestBody DianYhcxRequestDTO requestDTO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO
     * @description 电 申请过户
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/dian/sqgh")
    HefeiDianSqghResponseDTO dianSqgh(@RequestBody HefeiDianSqghRequestDTO requestDTO);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO
     * @description 水 申请过户
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/shui/sqgh")
    HefeiShuiSqghResponseDTO shuiSqgh(@RequestBody HefeiShuiSqghRequestTransferPerson requestDTO);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO
     * @description 新 水 申请过户
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/new/shui/sqgh")
    HefeiShuiSqghResponseDTO newShuiSqgh(@RequestBody HefeiShuiSqghRequestTransferPerson requestDTO);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO
     * @description 水费查询
     */
    @PostMapping("/realestate-exchange/rest/v1.0/hefeisdq/shui/shuifeicx")
    HefeiShuifeicxResponseDTO shuifeiCx(@RequestBody HefeiShuifeicxRequestDTO requestDTO);
}
