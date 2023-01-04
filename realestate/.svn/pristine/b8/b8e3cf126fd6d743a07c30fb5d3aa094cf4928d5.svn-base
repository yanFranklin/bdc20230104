package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlWxjjxxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/22
 * @description 不动产受理维修基金数据服务实现类
 */
@Service
public class BdcSlWxjjxxServiceImpl implements BdcSlWxjjxxService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;

    @Override
    public List<BdcSlWxjjxxDO> queryBdcSlWxjjxx(BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        if(!CheckParameter.checkAnyParameter(bdcSlWxjjxxDO, "gzlslid","xmid","bdcdyh")){
            throw new MissingArgumentException("缺失必要参数");
        }
        Example example = new Example(BdcSlWxjjxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcSlWxjjxxDO.getWxjjxxid())) {
            criteria.andEqualTo("wxjjxxid", bdcSlWxjjxxDO.getWxjjxxid());
        }
        if (StringUtils.isNotBlank(bdcSlWxjjxxDO.getGzlslid())) {
            criteria.andEqualTo("gzlslid", bdcSlWxjjxxDO.getGzlslid());
        }
        if (StringUtils.isNotBlank(bdcSlWxjjxxDO.getXmid())) {
            criteria.andEqualTo("xmid", bdcSlWxjjxxDO.getXmid());
        }
        if(StringUtils.isNotBlank(bdcSlWxjjxxDO.getSfdm())){
            criteria.andEqualTo("sfdm", bdcSlWxjjxxDO.getSfdm());
        }
        if(StringUtils.isNotBlank(bdcSlWxjjxxDO.getQlrlb())){
            criteria.andEqualTo("qlrlb", bdcSlWxjjxxDO.getQlrlb());
        }
        if(StringUtils.isNotBlank(bdcSlWxjjxxDO.getBdcdyh())){
            criteria.andEqualTo("bdcdyh", bdcSlWxjjxxDO.getBdcdyh());
        }
        return entityMapper.selectByExampleNotNull(example);
    }

    @Override
    public BdcSlWxjjxxDO insertBdcSlWxjjxxDO(BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        if (Objects.nonNull(bdcSlWxjjxxDO)) {
            if (StringUtils.isBlank(bdcSlWxjjxxDO.getWxjjxxid())) {
                bdcSlWxjjxxDO.setWxjjxxid(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcSlWxjjxxDO);
        }
        return bdcSlWxjjxxDO;
    }

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDOList 不动产受理维修基金信息
     * @author <a href="mailto:zxy@gtmap.cn">yaoyi</a>
     * @return 维修基金信息
     */
    @Override
    public void insertBatchBdcSlWxjjxxDO(List<BdcSlWxjjxxDO>  bdcSlWxjjxxDOList) {
        if (CollectionUtils.isNotEmpty(bdcSlWxjjxxDOList)) {
            entityMapper.batchSaveSelective(bdcSlWxjjxxDOList);
        }
    }

    @Override
    public Integer deleteWxjjxxByWxjjxxid(String wxjjxxid) {
        int result = 0;
        if (StringUtils.isNotBlank(wxjjxxid)) {
            result = entityMapper.deleteByPrimaryKey(BdcSlWxjjxxDO.class, wxjjxxid);
        }
        return result;
    }

    @Override
    public Integer deleteWxjjxxByGzlslid(String gzlslid) {
        int result = 0;
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlWxjjxxDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    @Override
    public int updateBdcSlWxjjxx(BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        int result;
        if (bdcSlWxjjxxDO != null && StringUtils.isNotBlank(bdcSlWxjjxxDO.getWxjjxxid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlWxjjxxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

}
