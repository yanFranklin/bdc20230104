package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.common.DtcxConstants;
import cn.gtmap.realestate.inquiry.core.mapper.BdcDtcxMapper;
import cn.gtmap.realestate.inquiry.service.DtcxJkConfigService;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/3/19
 * @description
 */
@Service
public class DtcxJkConfigServiceImpl implements DtcxJkConfigService {

    @Autowired
    BdcDtcxMapper dtcxMapper;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    UserManagerUtils userManager;
    @Autowired
    Repo repo;

    @Override
    public void deleteDtcxCx(String cxid) {
        // 删除查询配置信息
        entityMapper.deleteByPrimaryKey(DtcxDO.class, cxid);

        // 删除查询条件配置
        Example example = new Example(DtcxCxtjDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID, cxid);
        entityMapper.deleteByExample(example);

        //删除查询结果配置
        example = new Example(DtcxCxjgDO.class);
        example.createCriteria().andEqualTo(DtcxConstants.CXID, cxid);
        entityMapper.deleteByExample(example);
        example.clear();
    }

    @Override
    public List<DtcxCxjgDO> getCxjgList(String cxid) {
        if (StringUtils.isNotEmpty(cxid)) {
            Example example = new Example(DtcxCxjgDO.class);
            example.createCriteria().andEqualTo("cxid", cxid);
            example.setOrderByClause("priority asc");

            return entityMapper.selectByExample(example);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public BdcDtcxDTO getConfigsByCxdh(String cxdh) {

        BdcDtcxDTO bdcDtcxDTO;
        bdcDtcxDTO = dtcxMapper.getDtcxByCxdh(cxdh);
        if (bdcDtcxDTO != null) {
            bdcDtcxDTO.setCxtjDOList(dtcxMapper.getCxtjConfig(cxdh));
            bdcDtcxDTO.setCxjgDOList(dtcxMapper.getCxjgConfig(cxdh));
        }
        return bdcDtcxDTO;
    }
}
