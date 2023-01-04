package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlFpxxXgjlMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlFpXgjlService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxXgjlDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class BdcSlFpXgjlServiceImpl implements BdcSlFpXgjlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlFpXgjlServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    BdcSlFpxxXgjlMapper bdcSlFpxxXgjlMapper;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;


    @Override
    public Page<BdcSlFpxxXgjlDO> listBdcSlFpXgjlByPage(Pageable pageable, String json) {
        BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO = JSON.parseObject(json, BdcSlFpxxXgjlDO.class);
        return repo.selectPaging("listBdcSlFpxxXgjlByPage", bdcSlFpxxXgjlDO, pageable);
    }

    @Override
    public List<BdcSlFpxxXgjlDO> listBdcSlFpXgjl(BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO) {
        return this.bdcSlFpxxXgjlMapper.listBdcSlFpxxXgjlByPage(bdcSlFpxxXgjlDO);
    }

    @Override
    public void saveBdcSlFpxxXgjl(BdcSlFpxxXgjlDO bdcSlFpxxXgjlDO) {
        if(Objects.isNull(bdcSlFpxxXgjlDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理发票信息修改记录");
        }
        if(StringUtils.isBlank(bdcSlFpxxXgjlDO.getXgjlid())){
            bdcSlFpxxXgjlDO.setXgjlid(UUIDGenerator.generate16());
            this.entityMapper.insertSelective(bdcSlFpxxXgjlDO);
        }else{
            this.entityMapper.updateByPrimaryKeySelective(bdcSlFpxxXgjlDO);
        }
    }
}
