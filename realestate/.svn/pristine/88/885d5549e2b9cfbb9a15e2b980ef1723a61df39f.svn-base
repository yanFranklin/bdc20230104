package cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.impl;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.AbstractSendHyxxService;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.SendHyxxService;
import cn.gtmap.realestate.exchange.web.rest.NaturalResourcesRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendHyDyaxxServiceImpl extends AbstractSendHyxxService implements SendHyxxService {

    private static final Logger logger = LoggerFactory.getLogger(SendHyDyaxxServiceImpl.class);

    public static final String SEND_HY_DY_SERVICE_KEY = "SendHyDyaxxServiceImpl";


    @Autowired
    private NaturalResourcesRestController naturalResourcesRestController;

    /**
     * 获取相应的实现类
     * 2.6、2.8
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return SEND_HY_DY_SERVICE_KEY;
    }

    @Override
    public CommonResponse sendHyxx(String gzlslid) {
        CommonResponse seaQlr = naturalResourcesRestController.seaQlr(gzlslid);
        checkRep(seaQlr);
        CommonResponse seaDyaq = naturalResourcesRestController.seaDyaq(gzlslid);
        checkRep(seaDyaq);
        return CommonResponse.ok();
    }


}
