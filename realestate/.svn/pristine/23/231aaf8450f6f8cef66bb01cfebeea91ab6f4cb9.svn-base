package cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.impl;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.AbstractSendHyxxService;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.hy.SendHyxxService;
import cn.gtmap.realestate.exchange.web.rest.NaturalResourcesRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendHyScxxServiceImpl extends AbstractSendHyxxService implements SendHyxxService {

    public static final String SEND_HY_YY_SERVICE_KEY = "SendHyScxxServiceImpl";


    @Autowired
    private NaturalResourcesRestController naturalResourcesRestController;

    /**
     * 获取相应的实现类
     * 2.1-2.6、2.11
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return SEND_HY_YY_SERVICE_KEY;
    }

    @Override
    public CommonResponse sendHyxx(String gzlslid) {
        CommonResponse seaZhjbxx = naturalResourcesRestController.seaZhjbxx(gzlslid);
        checkRep(seaZhjbxx);
        CommonResponse seaYhydzb = naturalResourcesRestController.seaYhydzb(gzlslid);
        checkRep(seaYhydzb);
        CommonResponse seaYhzk = naturalResourcesRestController.seaYhzk(gzlslid);
        checkRep(seaYhzk);
        CommonResponse seaZhbhqk = naturalResourcesRestController.seaZhbhqk(gzlslid);
        checkRep(seaZhbhqk);
        CommonResponse seaHysyq = naturalResourcesRestController.seaHysyq(gzlslid);
        checkRep(seaHysyq);
        CommonResponse seaQlr = naturalResourcesRestController.seaQlr(gzlslid);
        checkRep(seaQlr);
        CommonResponse seaSmjxx = naturalResourcesRestController.seaSmjxx(gzlslid);
        checkRep(seaSmjxx);
        return CommonResponse.ok();
    }


}
