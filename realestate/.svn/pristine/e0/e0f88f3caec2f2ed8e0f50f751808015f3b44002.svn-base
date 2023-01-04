package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjgWithEmptyString;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/10/16.
 * Time: 20:23
 * To change this templatee File | Settings | File Templates.
 */
@Service
public class QueryCxjgTdsyqServiceImpl extends QueryCxjgAbstractServiceImpl {

    @Override
    public SjptCxqlEnum getQllxEnum() {
        return SjptCxqlEnum.TDSYQ;
    }

    @Override
    public void setQlList(PeCommitCxsqjg peCommitCxsqjg, List qlList) {
        peCommitCxsqjg.setTdsyq(qlList);
    }

    @Override
    public void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList) {
        List<JSONObject> jsonObjects = getDefaultEmptyStringJsonArray(qlList);
        peCommitCxsqjg.setTdsyq(jsonObjects);
    }

    @Override
    public List getQlList(PeCommitCxsqjg peCommitCxsqjg) {
        return peCommitCxsqjg.getTdsyq();
    }

    @Override
    public String getQlrlb() {
        return CommonConstantUtils.QLRLB_QLR;
    }
}
