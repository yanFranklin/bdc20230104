package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlHsxxMxService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxMxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 核税信息明细
 */
@Service
public class BdcSlHsxxMxServiceImpl implements BdcSlHsxxMxService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    protected MessageProvider messageProvider;

    @Override
    public int delBdcSlHsxxMxByHsxxid(String hsxxid){
        int result = 0;
        if (StringUtils.isNotBlank(hsxxid)) {
            Example example = new Example(BdcSlHsxxMxDO.class);
            example.createCriteria().andEqualTo("hsxxid", hsxxid);
            result = entityMapper.deleteByExample(example);
        }
        return result;

    }

    @Override
    public List<BdcSlHsxxMxDO> listBdcSlHsxxMxByHsxxid(String hsxxid){
        List<BdcSlHsxxMxDO> bdcSlHsxxMxDOList = new ArrayList<>();
        if (StringUtils.isNotBlank(hsxxid)) {
            Example example = new Example(BdcSlHsxxMxDO.class);
            example.createCriteria().andEqualTo("hsxxid", hsxxid);
            bdcSlHsxxMxDOList = entityMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(bdcSlHsxxMxDOList)) {
            bdcSlHsxxMxDOList = Collections.emptyList();
        }
        return bdcSlHsxxMxDOList;

    }

    @Override
    public int updateBdcSlHsxxMx(BdcSlHsxxMxDO bdcSlHsxxMxDO){
        int result;
        if (bdcSlHsxxMxDO != null && StringUtils.isNotBlank(bdcSlHsxxMxDO.getHsxxmxid())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcSlHsxxMxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;

    }

    @Override
    public int batchUpdateBdcSlHsxxMx(String json){
        int count = 0;
        for(BdcSlHsxxMxDO obj : JSON.parseArray(json,BdcSlHsxxMxDO.class)){
            count +=updateBdcSlHsxxMx(obj);
        }
        return count;

    }
}
