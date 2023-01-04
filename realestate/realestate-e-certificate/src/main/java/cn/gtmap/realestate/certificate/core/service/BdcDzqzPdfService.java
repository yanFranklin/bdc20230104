package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

/**
 * @program: realestate
 * @description: 电子签章生成pdf 服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 10:03
 **/
public interface BdcDzqzPdfService {

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证电子签章信息
     * @date : 2022/3/10 10:04
     */
    DzzzResponseModel checkBdcDzqz(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增电子签章信息
     * @date : 2022/3/10 11:33
     */
    DzzzResponseModel insertBdcDzqz(BdcDzzzZzxx bdcDzzzZzxx);
}
