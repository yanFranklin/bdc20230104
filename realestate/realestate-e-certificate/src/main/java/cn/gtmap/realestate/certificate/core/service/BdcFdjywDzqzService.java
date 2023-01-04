package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

/**
 * @program: realestate
 * @description: 非登记业务无证书信息，只生成pdf 数据推送签章
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-24 09:52
 **/
public interface BdcFdjywDzqzService {

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据推送的数据 可能只有pdf 相关信息，无证书信息，生成签章信息
     * @date : 2022/8/24 9:54
     */
    DzzzResponseModel createFdjDzqz(Object o);
}
