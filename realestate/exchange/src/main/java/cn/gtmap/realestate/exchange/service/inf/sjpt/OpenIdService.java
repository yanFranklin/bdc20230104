package cn.gtmap.realestate.exchange.service.inf.sjpt;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2019/1/10
 * @description
 */

import cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.request.SjptSscxRequestHead;

public interface OpenIdService {

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @param head head
     * @return
     * @description 验证openID
     */
    Boolean valOpenID(SjptSscxRequestHead head);
}
