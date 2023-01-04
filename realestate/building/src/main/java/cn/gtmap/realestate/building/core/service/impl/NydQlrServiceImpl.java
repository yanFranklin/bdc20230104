package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.mapper.NydQlrMapper;
import cn.gtmap.realestate.building.core.service.NydQlrService;
import cn.gtmap.realestate.building.core.service.NydService;
import cn.gtmap.realestate.building.service.BdcdyService;
import cn.gtmap.realestate.common.core.domain.building.HNydQlrDO;
import cn.gtmap.realestate.common.core.domain.building.NydDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.NydQlrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-14
 * @description
 */
@Service
public class NydQlrServiceImpl implements NydQlrService {

    @Autowired
    private NydService nydService;

    @Autowired
    private BdcdyService bdcdyService;

    @Autowired
    private NydQlrMapper nydQlrMapper;
    @Autowired
    private EntityMapper entityMapper;
    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询农用地权利人信息
     */
    @Override
    public List<NydQlrDO> listNydQlrByBdcdyh(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            NydDjdcbDO nydDjdcbDO = nydService.queryNydDjdcbByBdcdyh(bdcdyh);
            if (nydDjdcbDO != null && StringUtils.isNotBlank(nydDjdcbDO.getNydDjdcbIndex())) {
                return bdcdyService.listQlrByDjDcbIndex(nydDjdcbDO.getNydDjdcbIndex(), NydQlrDO.class);
            }
        }
        return new ArrayList<>(0);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @description 根据DJH查询林权相关权利人（包括林地使用权人、林木使用权人、林木所有权人）
     */
    @Override
    public List<NydQlrDO> listLqQlrByDjh(String djh){
        if(StringUtils.isNotBlank(djh)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("djh",djh);
            return nydQlrMapper.listLqNydQlr(paramMap);
        }
        return new ArrayList<>(0);
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 查询农用地权利人信息
     */
    @Override
    public List<NydQlrDO> listNydQlrByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            Example example = new Example(NydQlrDO.class);
            example.createCriteria().andEqualTo("djh", djh);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 用外键查询农用地权利人信息
     */
    @Override
    public List<NydQlrDO> listNydQlrByDjdcbIndex(String djdcbIndex) {
        if(StringUtils.isNotBlank(djdcbIndex)){
            Example example = new Example(NydQlrDO.class);
            example.createCriteria().andEqualTo("djdcbIndex", djdcbIndex);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param djh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.HNydQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询备份农用地权利人
     */
    @Override
    public List<HNydQlrDO> listHNydQlrByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            Example example = new Example(HNydQlrDO.class);
            example.createCriteria().andEqualTo("djh", djh);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }

    /**
     * @param dcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.HNydQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外键查询权利人
     */
    @Override
    public List<HNydQlrDO> listHNydQlrByDcbIndex(String dcbIndex) {
        if(StringUtils.isNotBlank(dcbIndex)){
            Example example = new Example(HNydQlrDO.class);
            example.createCriteria().andEqualTo("djdcbIndex", dcbIndex);
            return entityMapper.selectByExample(example);
        }
        return Collections.emptyList();
    }
}
