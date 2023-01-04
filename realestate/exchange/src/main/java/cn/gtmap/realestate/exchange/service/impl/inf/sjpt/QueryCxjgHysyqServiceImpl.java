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
public class QueryCxjgHysyqServiceImpl extends QueryCxjgAbstractServiceImpl{


    @Override
    public SjptCxqlEnum getQllxEnum() {
        return SjptCxqlEnum.HYSYQ;
    }

    @Override
    public void setQlList(PeCommitCxsqjg peCommitCxsqjg, List qlList) {
        peCommitCxsqjg.setHysyq(qlList);
    }

    @Override
    public void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList) {
        List<JSONObject> jsonObjects = getDefaultEmptyStringJsonArray(qlList);
        peCommitCxsqjg.setHysyq(jsonObjects);
    }

    @Override
    public List getQlList(PeCommitCxsqjg peCommitCxsqjg) {
        return peCommitCxsqjg.getHysyq();
    }

    @Override
    public String getQlrlb() {
        return CommonConstantUtils.QLRLB_QLR;
    }
}
