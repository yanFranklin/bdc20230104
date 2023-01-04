package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaFwxxDO;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.service.HtbaFwxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 合同备案房屋信息实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-16 10:29
 **/
@Service
public class HtbaFwxxServiceImpl implements HtbaFwxxService {
    @Qualifier("bdcEntityMapper")
    @Autowired(required = false)
    EntityMapper entityMapper;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    /**
     * @param fwHsDO,baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋户室信息到合同备案房屋信息
     * @date : 2020/12/16 10:30
     */
    @Override
    public int saveHtbaFwxxFromFwhs(FwHsDO fwHsDO,String baid) {
        int cou = 0;
        if(StringUtils.isNotBlank(baid)) {
            HtbaFwxxDO htbaFwxxDO = new HtbaFwxxDO();
            htbaFwxxDO.setBaid(baid);
            htbaFwxxDO.setFwid(fwHsDO.getFwHsIndex());
            htbaFwxxDO.setFwxxid(UUIDGenerator.generate16());
            htbaFwxxDO.setBdcdyh(fwHsDO.getBdcdyh());
            htbaFwxxDO.setFwbm(fwHsDO.getFwbm());
            htbaFwxxDO.setZl(fwHsDO.getZl());
            cou= entityMapper.insertSelective(htbaFwxxDO);
        }
        return cou;
    }

    /**
     * @param fwids
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合同备案关联附属设施
     * @date : 2020/12/17 13:57
     */
    @Override
    public int glHtbaHsxx(List<String> fwids, String baid) {
        int cou = 0;
        if(CollectionUtils.isNotEmpty(fwids)) {
            for(String fwid : fwids) {
                FwHsDO fwHsDO = fwHsFeignService.queryFwHsByFwHsIndex(fwid,"");
                if(Objects.isNull(fwHsDO)) {
                    fwHsDO = new FwHsDO();
                    FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByFwHsIndex(fwid,"");
                    if(Objects.nonNull(fwYchsDO)) {
                        BeanUtils.copyProperties(fwYchsDO,fwHsDO);
                    }
                }
                cou += this.saveHtbaFwxxFromFwhs(fwHsDO,baid);
            }
        }
        return 0;
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据备案id查询房屋信息
     * @date : 2020/12/18 11:15
     */
    @Override
    public List<HtbaFwxxDO> listHtbaFwxxByBaid(String baid) {
        if (StringUtils.isNotBlank(baid)) {
            Example example = new Example(HtbaFwxxDO.class);
            example.createCriteria().andEqualTo("baid", baid);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>();
    }

    /**
     * @param htbaFwxxDO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存房屋信息
     * @date : 2021/3/8 20:38
     */
    @Override
    public void saveHtbaFwxxDO(HtbaFwxxDO htbaFwxxDO) {
        if (StringUtils.isBlank(htbaFwxxDO.getFwxxid())) {
            htbaFwxxDO.setFwxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(htbaFwxxDO);
        } else {
            entityMapper.updateByPrimaryKey(htbaFwxxDO);
        }
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案房屋信息
     * @date : 2021/4/14 11:05
     */
    @Override
    public void deleteHtbaxx(String baid) {
        if (StringUtils.isNotBlank(baid)) {
            Example example = new Example(HtbaFwxxDO.class);
            example.createCriteria().andEqualTo("baid", baid);
            entityMapper.deleteByExample(example);
        }
    }
}
