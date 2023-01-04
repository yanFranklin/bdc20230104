package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.NumberUtil;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeDyaq;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeYg;
import cn.gtmap.realestate.exchange.core.domain.sjpt.PeCxDO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjgWithEmptyString;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ibm
 * Date: 2017/10/16.
 * Time: 20:23
 * To change this templatee File | Settings | File Templates.
 */
@Service
public class QueryCxjgYgServiceImpl extends QueryCxjgXzqlAbstractServiceImpl {
    /**
     * 金额单位是否转为万元（省级平台-查询结果-金额单位万元）
     * 原因：标准版数据库金额单位万元，盐城为元，导致省级查询报上去为元单位，但是上报时候是万元单位，两边不一致，因此需要处理下
     */
    @Value("${sjpt.cxjg.jedwwy:false}")
    private boolean jedwwy;


    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Override
    public SjptCxqlEnum getQllxEnum() {
        return SjptCxqlEnum.YG;
    }

    @Override
    public void setQlList(PeCommitCxsqjg peCommitCxsqjg, List qlList) {
        peCommitCxsqjg.setYgdj(qlList);

        if(jedwwy && CollectionUtils.isNotEmpty(peCommitCxsqjg.getYgdj())) {
            for(GxPeYg gxPeYg : peCommitCxsqjg.getYgdj()) {
                if(null == gxPeYg) {
                    continue;
                }

                if(null != gxPeYg.getQdjg()) {
                    gxPeYg.setQdjg(NumberUtil.divide(gxPeYg.getQdjg(), 10000.0, 4));
                }
            }
        }
    }

    @Override
    public void setQlListWithDefaultValueEmptyString(PeCommitCxsqjgWithEmptyString peCommitCxsqjg, List qlList) {
        List<JSONObject> jsonObjects = getDefaultEmptyStringJsonArray(qlList);
        peCommitCxsqjg.setYgdj(jsonObjects);
    }

    @Override
    public List getQlList(PeCommitCxsqjg peCommitCxsqjg) {
        return peCommitCxsqjg.getYgdj();
    }

    @Override
    public String getQlrlb() {
        return CommonConstantUtils.QLRLB_QLR;
    }

    /**
     * @param bdcdyhList 单元号集合
     * @return 批量查询单元号对应的现势限制权利
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    protected List<BdcQl> listBdcXzqlPlcx(List<String> bdcdyhList) {
        return commonService.listQlByBdcdyhs(bdcdyhList, new BdcYgDO());
    }

    /**
     * @param peCxDO         查询参数对象
     * @param peCommitCxsqjg 查询结果对象
     * @param type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return PeCommitCxsqjg
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 执行查询登记数据
     */
    @Override
    public PeCommitCxsqjg cxsj(PeCxDO peCxDO, PeCommitCxsqjg peCommitCxsqjg, String type) throws InstantiationException, IllegalAccessException {
        String className = getGxQllxClass().getSimpleName();
        LOGGER.info("省厅查询：预告权利查询开始{}， 查询申请单号：{}", className, peCxDO.getCxsqdh());
        // 根据 人信息 查权利
        List<BdcYgDO> ygQlList = bdcdjMapper.queryYgQlxxForSjpt(peCxDO);
        if (CollectionUtils.isNotEmpty(ygQlList)){
            List<BdcQl> bdcQlList = new ArrayList<>(ygQlList.size());
            ygQlList.forEach(bdcYgDO -> {
                bdcQlList.add(bdcYgDO);
            });
            //以人查询预告权力信息
            List gxQlList = fillCxsj(peCxDO, bdcQlList, type);
            if (CollectionUtils.isNotEmpty(gxQlList)) {
                this.setQlList(peCommitCxsqjg, gxQlList);
            }
            LOGGER.info("省厅查询：预告权利结束{}，查询申请单号：{}", className, peCxDO.getCxsqdh());
        }
        return peCommitCxsqjg;
    }
}
