package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.LqService;
import cn.gtmap.realestate.building.core.service.NydQlrService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.common.core.domain.building.HLqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.LqDcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 林权相关服务
 */
@Service
public class LqServiceImpl implements LqService {

    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private Repo repo;

    @Autowired
    private NydQlrService nydQlrService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询林权
     */
    @Override
    public LqDcbDO queryLqDcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, LqDcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询林权
     */
    @Override
    public LqDcbDO queryLqDcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjh(djh,LqDcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.LqDcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询林权
     */
    @Override
    public HLqDcbDO queryHLqDcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjhWithOrder(djh,HLqDcbDO.class,"gxrq desc");
    }

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.LqDcbPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询林权调查表信息
     */
    @Override
    public Page<Map> listLqDcbByPageJson(Pageable pageable, Map<String, Object> paramMap) {
        // 分页查询 没有聚合权利人
        Page<Map> result = repo.selectPaging("listLqDcbByPageOrder", paramMap, pageable);
        // 循环处理权利人信息
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                setQlr(data);
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return result;
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木所有权人
     */
    @Override
    public List<NydQlrDO> listLmsyqrByDcbIndex(String dcbIndex) {
        return listLqQlrByDcbIndex(dcbIndex, Constants.LQQLRLX_LMSYQR);
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林木使用权人
     */
    @Override
    public List<NydQlrDO> listLmsuqrByDcbIndex(String dcbIndex) {
        return listLqQlrByDcbIndex(dcbIndex, Constants.LQQLRLX_LMSUQR);
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过dcbIndex查询林地所有权人
     */
    @Override
    public List<NydQlrDO> listLdsyqrByDcbIndex(String dcbIndex) {
        return listLqQlrByDcbIndex(dcbIndex, Constants.LQQLRLX_LDSYQR);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木所有权人
     */
    @Override
    public List<NydQlrDO> listLmsyqrByDjh(String djh) {
        return listLqQlrByDjh(djh, Constants.LQQLRLX_LMSYQR);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林木使用权人
     */
    @Override
    public List<NydQlrDO> listLmsuqrByDjh(String djh) {
        return listLqQlrByDjh(djh, Constants.LQQLRLX_LMSUQR);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过djh查询林地所有权人
     */
    @Override
    public List<NydQlrDO> listLdsyqrByDjh(String djh) {
        return listLqQlrByDjh(djh, Constants.LQQLRLX_LDSYQR);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据调查簿主键和类型查询使用权人信息
     */
    private List<NydQlrDO> listLqQlrByDjh(String djh, String qlrlx) {
        List<NydQlrDO> nydQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(djh)) {
            List<NydQlrDO> list = nydQlrService.listLqQlrByDjh(djh);
            if (CollectionUtils.isNotEmpty(list)) {
                nydQlrDOList = listQlrByLx(list, qlrlx);
            }
        }
        return nydQlrDOList;
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据调查簿主键和类型查询使用权人信息
     */
    private List<NydQlrDO> listLqQlrByDcbIndex(String dcbIndex, String qlrlx) {
        List<NydQlrDO> nydQlrDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(dcbIndex)) {
            List<NydQlrDO> list =bdcdyService.listQlrByDjDcbIndex(dcbIndex, NydQlrDO.class);
            if (CollectionUtils.isNotEmpty(list)) {
                nydQlrDOList = listQlrByLx(list, qlrlx);
            }
        }
        return nydQlrDOList;
    }

    /**
     * @param list
     * @param qlrlx
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过类型获取权利人
     */
    @Override
    public List<NydQlrDO> listQlrByLx(List<NydQlrDO> list, String qlrlx) {
        List<NydQlrDO> nydQlrDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(qlrlx)) {
            for (NydQlrDO nydQlrDO : list) {
                String num = "0";
                if (StringUtils.equals(Constants.LQQLRLX_LMSUQR, qlrlx)) {
                    if (StringUtils.isNotBlank(nydQlrDO.getSflmsuqr()) && (!num.equals(nydQlrDO.getSflmsuqr()))) {
                        nydQlrDOList.add(nydQlrDO);
                    }
                } else if (StringUtils.equals(Constants.LQQLRLX_LMSYQR, qlrlx)) {
                    if (StringUtils.isNotBlank(nydQlrDO.getSflmsyqr()) && (!num.equals(nydQlrDO.getSflmsyqr()))) {
                        nydQlrDOList.add(nydQlrDO);
                    }
                } else if (StringUtils.equals(Constants.LQQLRLX_LDSYQR, qlrlx)
                        && StringUtils.isNotBlank(nydQlrDO.getSfldsyqr()) && (!num.equals(nydQlrDO.getSfldsyqr()))) {
                    nydQlrDOList.add(nydQlrDO);
                }
            }
        }
        return nydQlrDOList;
    }

    /**
     * 获取区县下林地总面积和宗地数量
     *
     * @param qxdmList
     * @return
     */
    @Override
    public List<Map> getLdmjAndZd(List<String> qxdmList) {
        Map paramMap = new HashMap();
        paramMap.put("qxdmList", qxdmList);
        return repo.selectList("listLdmjAndZd", paramMap);
    }

    /**
     * @param map
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存林权相关权利人
     */
    private void setQlr(Map map) {
        // 林地使用权人
        List<String> ldsyqrList = new ArrayList<>();
        // 林木使用权人
        List<String> lmsyqrList = new ArrayList<>();
        // 林木所有权人
        List<String> lmsuqrList = new ArrayList<>();
        String djh = MapUtils.getString(map,"DJH");

        if (StringUtils.isNotBlank(djh)) {
            List<NydQlrDO> qlrList = nydQlrService.listLqQlrByDjh(djh);
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (NydQlrDO qlr : qlrList) {
                    if (StringUtils.equals(Constants.LQ_QLRBZ, qlr.getSfldsyqr())) {
                        ldsyqrList.add(qlr.getQlrmc());
                    }
                    if (StringUtils.equals(Constants.LQ_QLRBZ, qlr.getSflmsyqr())) {
                        lmsyqrList.add(qlr.getQlrmc());
                    }
                    if (StringUtils.equals(Constants.LQ_QLRBZ, qlr.getSflmsuqr())) {
                        lmsuqrList.add(qlr.getQlrmc());
                    }
                }
            }
            map.put("QLR",BuildingUtils.wmQlrMcWithList(ldsyqrList));
            map.put("LMSYQR",BuildingUtils.wmQlrMcWithList(lmsyqrList));
            map.put("LMSUQR",BuildingUtils.wmQlrMcWithList(lmsuqrList));
        }
    }

}