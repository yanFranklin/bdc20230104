package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
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
public class QueryCxjgCfServiceImpl extends QueryCxjgXzqlAbstractServiceImpl {

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return 批量查询单元号对应的现势限制权利
     */
    @Override
    protected List<BdcQl> listBdcXzqlPlcx(List<String> bdcdyhList) {
        return commonService.listQlByBdcdyhs(bdcdyhList, new BdcCfDO());
    }

    @Override
    public SjptCxqlEnum getQllxEnum() {
        return SjptCxqlEnum.CF;
    }

    @Override
    public void setQlList(PeCommitCxsqjg peCommitCxsqjg, List qlList) {
        peCommitCxsqjg.setCfdj(qlList);
    }

    @Override
    public void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList) {
        List<JSONObject> jsonObjects = getDefaultEmptyStringJsonArray(qlList);
        peCommitCxsqjg.setCfdj(jsonObjects);
    }

    @Override
    public List getQlList(PeCommitCxsqjg peCommitCxsqjg) {
        return peCommitCxsqjg.getCfdj();
    }

    @Override
    public String getQlrlb() {
        return CommonConstantUtils.QLRLB_YWR;
    }

}
