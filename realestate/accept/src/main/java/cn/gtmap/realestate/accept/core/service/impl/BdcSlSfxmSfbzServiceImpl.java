package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlSfxmMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmSfbzService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/22
 * @description 不动产收费项目收费标准数据库服务
 */
@Service
public class BdcSlSfxmSfbzServiceImpl implements BdcSlSfxmSfbzService {
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcSlSfxmMapper bdcSlSfxmMapper;

    @Override
    public List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDO(String sfxmdm) {
        List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzDOArrayList = new ArrayList<>();
        if (StringUtils.isNotBlank(sfxmdm)) {
            Example example = new Example(BdcSlSfxmSfbzDO.class);
            example.createCriteria().andEqualTo("sfxmdm", sfxmdm);
            bdcSlSfxmSfbzDOArrayList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlSfxmSfbzDOArrayList)) {
            bdcSlSfxmSfbzDOArrayList = Collections.emptyList();
        }
        return bdcSlSfxmSfbzDOArrayList;
    }

    @Override
    public List<BdcSlSfxmSfbzDO> listBdcSlSfxmSfbzDOAll() {
        return bdcSlSfxmMapper.listBdcSlSfxmSfbzDOAll();
    }
}
