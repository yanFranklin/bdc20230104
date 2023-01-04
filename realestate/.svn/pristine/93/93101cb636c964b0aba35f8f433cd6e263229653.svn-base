package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.realestate.building.core.mapper.ZrzMapper;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.service.ZrzService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.HFwLjzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-11
 * @description 自然幢服务
 */
@Service
public class ZrzServiceImpl implements ZrzService {

    @Autowired
    private Repo repo;

    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private ZrzMapper zrzMapper;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @param pageable
     * @param paramMap
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询 自然幢
     */
    @Override
    public Page<Map> listZrzByPage(Pageable pageable, Map<String, Object> paramMap) {
        Page<Map> result =  repo.selectPaging("listZrzByPageOrder", paramMap, pageable);
        if (CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                BuildingUtils.convertKeyToLowerCase(data);
            }
        }
        return result;
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据LSZD查询宗地下最大自然幢号（查询FW_LJZ FW_XMXX表）
     */
    @Override
    public int getMaxZrzhByDjh(String djh) {
        int maxZrzh = 0;
        // 查 FW_LJZ 表
        List<FwLjzDO> ljzList = fwLjzService.listLjzByDjhOrderByZrzh(djh);
        if(CollectionUtils.isNotEmpty(ljzList)){
            for(FwLjzDO fwLjzDO : ljzList){
                String zrzh = fwLjzDO.getZrzh();
                if(NumberUtils.isNumber(zrzh)){
                    maxZrzh = Integer.parseInt(zrzh);
                    break;
                }
            }
        }

        // 查 H_FW_LJZ 表
        Example example = new Example(HFwLjzDO.class);
        example.createCriteria().andEqualTo("lszd",djh)
                .andIsNotNull("zrzh");
        example.setOrderByClause("zrzh desc");
        List<HFwLjzDO> hFwLjzDOList = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(hFwLjzDOList)){
            for(HFwLjzDO hFwLjzDO : hFwLjzDOList){
                String zrzh = hFwLjzDO.getZrzh();
                if(NumberUtils.isNumber(zrzh) &&  Integer.parseInt(zrzh) > maxZrzh){
                    maxZrzh = Integer.parseInt(zrzh);
                    break;
                }
            }
        }
        return maxZrzh;
    }

    /**
     * @param djh
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public List<String> listZrzhByDjh(String djh) {
        if(StringUtils.isNotBlank(djh)){
            return zrzMapper.listZrzhByDjh(djh);
        }
        return Collections.emptyList();
    }
}
