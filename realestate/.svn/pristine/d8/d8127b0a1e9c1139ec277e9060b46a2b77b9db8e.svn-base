package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.NumberUtil;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeDyaq;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjgWithEmptyString;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
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
public class QueryCxjgDyaqServiceImpl extends QueryCxjgXzqlAbstractServiceImpl {
    /**
     * 金额单位是否转为万元（省级平台-查询结果-金额单位万元）
     * 原因：标准版数据库金额单位万元，盐城为元，导致省级查询报上去为元单位，但是上报时候是万元单位，两边不一致，因此需要处理下
     */
    @Value("${sjpt.cxjg.jedwwy:false}")
    private boolean jedwwy;

    /**
     * @param bdcdyhList 单元号集合
     * @return 批量查询单元号对应的现势限制权利
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    protected List<BdcQl> listBdcXzqlPlcx(List<String> bdcdyhList) {
        return commonService.listQlByBdcdyhs(bdcdyhList, new BdcDyaqDO());
    }

    @Override
    public SjptCxqlEnum getQllxEnum() {
        return SjptCxqlEnum.DYAQ;
    }

    @Override
    public void setQlList(PeCommitCxsqjg peCommitCxsqjg, List qlList) {
        peCommitCxsqjg.setDyaq(qlList);

        if(jedwwy && CollectionUtils.isNotEmpty(peCommitCxsqjg.getDyaq())) {
            for(GxPeDyaq gxPeDyaq : peCommitCxsqjg.getDyaq()) {
                if(null == gxPeDyaq) {
                    continue;
                }

                if(null != gxPeDyaq.getBdbzzqse()) {
                    gxPeDyaq.setBdbzzqse(NumberUtil.divide(gxPeDyaq.getBdbzzqse(), 10000.0, 4));
                }

                if(null != gxPeDyaq.getZgzqse()) {
                    gxPeDyaq.setZgzqse(NumberUtil.divide(gxPeDyaq.getZgzqse(), 10000.0, 4));
                }
            }
        }
    }

    @Override
    public void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList) {
        List<JSONObject> jsonObjects = getDefaultEmptyStringJsonArray(qlList);
        peCommitCxsqjg.setDyaq(jsonObjects);
    }

    @Override
    public List getQlList(PeCommitCxsqjg peCommitCxsqjg) {
        return peCommitCxsqjg.getDyaq();
    }

    @Override
    public String getQlrlb() {
        return CommonConstantUtils.QLRLB_YWR;
    }
}
