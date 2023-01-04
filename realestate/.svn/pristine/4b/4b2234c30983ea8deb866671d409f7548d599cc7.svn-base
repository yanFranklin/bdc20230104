package cn.gtmap.realestate.exchange.service.inf.standard.court;

import cn.gtmap.realestate.common.core.dto.exchange.court.*;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.CourtCxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.CourtCxjgDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.CourtFeedCxBdcQLDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzDTO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;

/**
 * 人民法院网络执行查控系统
 *
 * @author wangyinghao
 */
public interface BdcCourtKzJhService extends InterfaceCode {


    /**
     * 调用该接口返回法院请求的司法控制信息
     */
    CourtKzDTO kzBdcQL(CourtKzUserDTO usermarker);

    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @return
     */
    CourtKzjgDTO feedkzBdcQL(CourtKzUserDTO usermarker, CourtFeedKzBdcQLDTO feedbackinfo);

    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @return
     */
    CourtKzjgDTO feedkzhzBdcQL(CourtKzUserDTO usermarker, CourtFeedKzBdcQLDTO feedbackinfo);

    /**
     * 调用该接口获取请求单位各控制申请涉及的相关文书信息
     *
     * @return
     */
    CourtKzBdcWsInfoDTO kzwsInfo(CourtKzUserDTO usermarker, CourtKzdhDTO courtKzdhDTO);

    /**
     * 调用该接口获取请求单位各控制申请涉及的法官证件信息
     *
     * @return
     */
    CourtKzBdcZjInfoDTO zjInfo(CourtKzUserDTO usermarker, CourtKzdhDTO courtKzdhDTO);


    /**
     *
     * @param usermarker
     * @return
     */
    CourtCxDTO cxBdcQL(CourtKzUserDTO usermarker);

    /**
     *
     * @param usermarker
     * @param feedbackinfo
     * @return
     */
    CourtCxjgDTO feedCxBdcQL(CourtKzUserDTO usermarker, CourtFeedCxBdcQLDTO feedbackinfo);
}
