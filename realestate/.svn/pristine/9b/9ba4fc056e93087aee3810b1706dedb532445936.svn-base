package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmJsydlhxxGxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.mapper.BdcXmJsydlhxxGxMapper;
import cn.gtmap.realestate.init.core.service.BdcXmJsydlhxxGxService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/17
 * @description 项目建设用地量化信息关系服务
 */
@Service
public class BdcXmJsydlhxxGxServiceImpl implements BdcXmJsydlhxxGxService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXmJsydlhxxGxMapper bdcXmJsydlhxxGxMapper;

    @Override
    public List<BdcXmJsydlhxxGxDO> listBdcXmJsydlhxxGxByGzlslid(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcXmJsydlhxxGxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.selectByExampleNotNull(example);
        }
        return Collections.emptyList();

    }

    @Override
    public void deleteBdcXmJsydlhxxGx(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcXmJsydlhxxGxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            entityMapper.deleteByExample(example);
        }

    }

}
