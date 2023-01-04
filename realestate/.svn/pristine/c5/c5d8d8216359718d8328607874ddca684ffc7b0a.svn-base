package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZdytjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZdttjTbxxmxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZdytjQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcZdytjMapper;
import cn.gtmap.realestate.inquiry.service.BdcZdytjConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0, 2022/09/08
 * @description
 */
@Service
public class BdcZdytjConfigServiceImpl implements BdcZdytjConfigService {

    public static final Logger LOGGER = LoggerFactory.getLogger(BdcZdytjConfigServiceImpl.class);
    @Autowired
    BdcZdytjMapper bdcZdytjMapper;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    UserManagerUtils userManager;
    @Autowired
    Repo repo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAllDtcxCxxx(BdcZdytjDO bdcZdytjDO){
        entityMapper.saveOrUpdate(bdcZdytjDO,bdcZdytjDO.getCxid());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDtcxCx(String cxid){
        // 删除查询配置信息
        entityMapper.deleteByPrimaryKey(BdcZdytjDO.class,cxid);
    }

    @Override
    public Page<BdcZdytjDO> listZdytjPage(BdcZdytjQO qo, Pageable pageable){
        return repo.selectPaging("listBdctjByPage",qo,pageable);
    }

    @Override
    public BdcZdytjDO getConfigsByCxdh(String cxdh){

        BdcZdytjDO bdcZdytjDO;
        bdcZdytjDO = bdcZdytjMapper.getDtcxByCxdh(cxdh);
        return bdcZdytjDO;
    }

    @Override
    public Boolean checkSql(BdcZdytjDO bdcZdytjDO){
        if (null == bdcZdytjDO && StringUtils.isEmpty(bdcZdytjDO.getCxsql())){
            throw new MissingArgumentException("sql不能为空");
        }
        Boolean result =true;
        try {
            bdcZdytjMapper.getTbxx(bdcZdytjDO.getCxsql());
        }catch (Exception e){
            LOGGER.info(e.toString());
            result = false;
        }
        return result;
    }

    @Override
    public BdcZdttjTbxxDTO getTbxxByCxdh(String cxdh){
        if (StringUtils.isEmpty(cxdh)){
            throw new MissingArgumentException("cxdh不能为空");
        }
        BdcZdttjTbxxDTO bdcZdttjTbxxDTO = new BdcZdttjTbxxDTO();
        BdcZdytjDO bdcZdytjDO = bdcZdytjMapper.getDtcxByCxdh(cxdh);
        if(bdcZdytjDO != null && StringUtils.isNotBlank(bdcZdytjDO.getCxsql())){
            bdcZdttjTbxxDTO.setTitle(bdcZdytjDO.getCxmc());
            List<BdcZdttjTbxxmxDTO> bdcZdttjTbxxDTOList = bdcZdytjMapper.getTbxx(bdcZdytjDO.getCxsql());
            bdcZdttjTbxxDTO.setDetail(bdcZdttjTbxxDTOList);
        }
        return bdcZdttjTbxxDTO;
    }
}
