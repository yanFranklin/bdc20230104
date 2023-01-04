package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.exchange.core.domain.sjpt.*;
import cn.gtmap.realestate.exchange.core.dto.sjpt.cxjg.request.PeCommitCxsqjg;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.enums.SjptCxqlEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/24
 * @description
 */
public abstract class QueryCxjgXzqlAbstractServiceImpl extends QueryCxjgAbstractServiceImpl {

    @Autowired
    protected CommonService commonService;

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyhList 单元号集合
     * @return 批量查询单元号对应的现势限制权利
     */
    protected abstract List<BdcQl> listBdcXzqlPlcx(List<String> bdcdyhList);

    /**
     * @param peCxDO         查询参数对象
     * @param peCommitCxsqjg 查询结果对象
     * @param type 区分是实时查询还是批量查询（sscx 实时查询  plcx 批量查询）
     * @return PeCommitCxsqjg
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 执行查询登记数据
     */
    @Override
    public PeCommitCxsqjg cxsj(PeCxDO peCxDO, PeCommitCxsqjg peCommitCxsqjg, String type) throws InstantiationException, IllegalAccessException {
        String className = getGxQllxClass().getSimpleName();
        // 整理 bdcdyh
        List<String> bdcdyhList = getBdcdyhList(peCommitCxsqjg);

        // 根据 人信息 查权利
        List<BdcQl> bdcQlList = new ArrayList<>();
        LOGGER.info("省厅查询：限制产权开始{}，查询申请单号{}", className, peCxDO.getCxsqdh());

        if(CollectionUtils.size(bdcdyhList) > 100 && sjplcl) {
            LOGGER.info("省厅查询：限制产权查询采用批量处理方案，对应查询申请单号：{}", peCxDO.getCxsqdh());
            bdcQlList = listBdcXzqlPlcx(bdcdyhList);
        } else {
            LOGGER.info("省厅查询：限制产权查询采用循环单个处理方案，对应查询申请单号：{}", peCxDO.getCxsqdh());
            for (String bdcdyh : bdcdyhList) {
                List<BdcQl> bdcQlTemp = commonService.listXsQlByBdcdyh(bdcdyh, getQllxDm()[0]);
                if (CollectionUtils.isNotEmpty(bdcQlTemp)) {
                    bdcQlList.addAll(bdcQlTemp);
                }
            }
        }

        List gxQlList = fillCxsj(peCxDO, bdcQlList, type);
        if (CollectionUtils.isNotEmpty(gxQlList)) {
            this.setQlList(peCommitCxsqjg, gxQlList);
        }
        LOGGER.info("省厅查询：限制产权结束{}，查询数量{}，查询申请单号{}", className, CollectionUtils.size(gxQlList), peCxDO.getCxsqdh());
        return peCommitCxsqjg;
    }

    @Override
    public SjptCxqlEnum getQllxEnum() {
        return null;
    }

    @Override
    public void setQlList(PeCommitCxsqjg peCommitCxsqjg, List qlList) {

    }

    @Override
    public List getQlList(PeCommitCxsqjg peCommitCxsqjg) {
        return new ArrayList();
    }

    @Override
    public String getQlrlb() {
        return "";
    }

    private List<String> getBdcdyhList(PeCommitCxsqjg peCommitCxsqjg) {
        Set<String> bdcdyhSet = new HashSet<>();
        List<String> bdcdyhList = new ArrayList<>();
        // 土地使用权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getTdsyq())) {
            List<GxPeTdsyq> gxPeTdsyqList = peCommitCxsqjg.getTdsyq();
            for (GxPeTdsyq gxPeTdsyq : gxPeTdsyqList) {
                if (StringUtils.isNotBlank(gxPeTdsyq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeTdsyq.getBdcdyh());
                }
            }
        }
        // 建设用地使用权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getJsydsyq())) {
            List<GxPeJsydsyq> gxPeJsydsyqList = peCommitCxsqjg.getJsydsyq();
            for (GxPeJsydsyq gxPeJsydsyq : gxPeJsydsyqList) {
                if (StringUtils.isNotBlank(gxPeJsydsyq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeJsydsyq.getBdcdyh());
                }
            }
        }
        // 房地产权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getFdcq())) {
            List<GxPeFdcq> gxPeFdcqList = peCommitCxsqjg.getFdcq();
            for (GxPeFdcq gxPeFdcq : gxPeFdcqList) {
                if (StringUtils.isNotBlank(gxPeFdcq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeFdcq.getBdcdyh());
                }
            }
        }
        // 海域使用权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getHysyq())) {
            List<GxPeHysyq> gxPeHysyqList = peCommitCxsqjg.getHysyq();
            for (GxPeHysyq gxPeHysyq : gxPeHysyqList) {
                if (StringUtils.isNotBlank(gxPeHysyq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeHysyq.getBdcdyh());
                }
            }
        }
        // 构建筑物所有权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getGjzwsyq())) {
            List<GxPeGjzwsyq> gxPeGjzwsyqList = peCommitCxsqjg.getGjzwsyq();
            for (GxPeGjzwsyq gxPeGjzwsyq : gxPeGjzwsyqList) {
                if (StringUtils.isNotBlank(gxPeGjzwsyq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeGjzwsyq.getBdcdyh());
                }
            }
        }
        // 林权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getLq())) {
            List<GxPeLq> gxPeLqList = peCommitCxsqjg.getLq();
            for (GxPeLq gxPeLq : gxPeLqList) {
                if (StringUtils.isNotBlank(gxPeLq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeLq.getBdcdyh());
                }
            }
        }
        // 农用地使用权
        if (CollectionUtils.isNotEmpty(peCommitCxsqjg.getNydsyq())) {
            List<GxPeNydsyq> gxPeNydsyqList = peCommitCxsqjg.getNydsyq();
            for (GxPeNydsyq gxPeNydsyq : gxPeNydsyqList) {
                if (StringUtils.isNotBlank(gxPeNydsyq.getBdcdyh())) {
                    bdcdyhSet.add(gxPeNydsyq.getBdcdyh());
                }
            }
        }

        bdcdyhList.addAll(bdcdyhSet);
        return bdcdyhList;
    }
}
