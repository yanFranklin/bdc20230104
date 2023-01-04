package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcQlrService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/30
 * @description 不动产权利人查询服务实现类
 */
@Service
public class BdcQlrServiceImpl implements BdcQlrService {
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param xmid  项目ID
     * @param qlrlb 权利人类别
     * @return List<BdcQlrDO> 权利人列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询项目的权利人信息
     */
    @Override
    public List<BdcQlrDO> queryListBdcQlr(String xmid, Integer qlrlb) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID!");
        }
        Example example = new Example(BdcQlrDO.class);
        example.setOrderByClause("sxh asc");
        example.createCriteria().andEqualTo("xmid", xmid).andEqualTo("qlrlb", qlrlb);
        return entityMapper.selectByExampleNotNull(example);
    }
}
