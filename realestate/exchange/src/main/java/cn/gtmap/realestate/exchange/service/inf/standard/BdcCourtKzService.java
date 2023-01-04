package cn.gtmap.realestate.exchange.service.inf.standard;

import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzBdcQQItem;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;

/**
 * 人民法院网络执行查控系统
 * @author wangyinghao
 */
public interface BdcCourtKzService {


    /**
     * 调用该接口返回法院请求的司法控制信息
     */
    void CourtKzBdcQL();


    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @return
     */
    void feedCourtKzCfBdcQL(CourtKzBdcQQItem courtKzBdcQQItem);

    /**
     * 调用该接口获取请求单位各控制申请涉及的相关文书信息
     *
     * @return
     */
    FjclDTOForZhlc courtKzwsInfo(String kzqqdh);

    /**
     * 调用该接口获取请求单位各控制申请涉及的法官证件信息
     *
     * @return
     */
    FjclDTOForZhlc courtKzZjInfo(String kzqqdh);
}
