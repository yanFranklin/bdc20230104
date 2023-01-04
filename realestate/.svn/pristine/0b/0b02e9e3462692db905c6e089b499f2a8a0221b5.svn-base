package cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.impl;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.AbstractSendHyxxService;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.SendHyxxService;
import cn.gtmap.realestate.exchange.web.rest.NaturalResourcesRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendHyCfxxServiceImpl extends AbstractSendHyxxService implements SendHyxxService {

    public static final String SEND_HY_CF_SERVICE_KEY = "SendHyCfxxServiceImpl";

    @Autowired
    private NaturalResourcesRestController naturalResourcesRestController;

    /**
     * 获取相应的实现类
     * 2.6、2.9
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return SEND_HY_CF_SERVICE_KEY;
    }

    @Override
    public CommonResponse sendHyxx(String gzlslid) {
        CommonResponse seaQlr = naturalResourcesRestController.seaQlr(gzlslid);
        checkRep(seaQlr);
        CommonResponse seaCfdj = naturalResourcesRestController.seaCfdj(gzlslid);
        checkRep(seaCfdj);
        return CommonResponse.ok();
    }
}
