package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.service.CbzdService;
import cn.gtmap.realestate.building.core.service.NydQlrService;
import cn.gtmap.realestate.building.core.service.NydService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
@Service
public class NydServiceImpl implements NydService {
    @Autowired
    private BdcdyService bdcdyService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private CbzdService cbzdService;
    @Autowired
    private Repo repo;
    @Autowired
    private NydQlrService nydQlrService;
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydJtcyDo>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地家庭成员
     */
    @Override
    public List<NydJtcyDO> listNydJtcyByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            CbzdDcbDO cbzdDcbDO = cbzdService.queryCdzdDcbByBdcdyh(bdcdyh);
            if (cbzdDcbDO != null && StringUtils.isNotBlank(cbzdDcbDO.getJtIndex())) {
                Example example = new Example(NydJtcyDO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("jtIndex", cbzdDcbDO.getJtIndex());
                return entityMapper.selectByExample(example);
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地宗地
     */
    @Override
    public NydDjdcbDO queryNydDjdcbByBdcdyh(String bdcdyh) {
        return bdcdyService.queryDjxxByBdcdyh(bdcdyh, NydDjdcbDO.class);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH 查询NYD
     */
    @Override
    public NydDjdcbDO queryNydDjdcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjh(djh,NydDjdcbDO.class);
    }
    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.NydDjdcbResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询农用地信息
     */
    @Override
    public Page<Map> listNydByPage(Pageable pageable, Map map) {
        Page<Map> result = repo.selectPaging("listNydByPageOrder", map, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                // 保存QLR
                setQlr(data);
                // key转换小写
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return result;
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询备份表
     */
    @Override
    public HNydDjdcbDO queryHNydDjdcbByDjh(String djh) {
        return bdcdyService.queryDjxxByDjhWithOrder(djh,HNydDjdcbDO.class,"gxrq desc");
    }


    /**
     * @param dataMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 保存宗地相关权利人
     */
    private void setQlr(Map dataMap) {
        List<String> nydQlrList = new ArrayList<>();
        if (dataMap != null && StringUtils.isNotBlank(MapUtils.getString(dataMap,"DJH"))) {
            List<NydQlrDO> qlrList = nydQlrService.listNydQlrByDjh(MapUtils.getString(dataMap,"DJH"));
            if (CollectionUtils.isNotEmpty(qlrList)) {
                for (NydQlrDO qlr : qlrList) {
                    nydQlrList.add(qlr.getQlrmc());
                }
            }
            dataMap.put("QLR",BuildingUtils.wmQlrMcWithList(nydQlrList));
        }
    }
}