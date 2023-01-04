package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCjxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.service.BdcCjxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/08/11
 * @description 不动产持件信息实现类
 */
@Service
public class BdcCjxxServiceImpl implements BdcCjxxService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public BdcCjxxDO saveBdcCjxx(BdcCjxxDO bdcCjxxDO) {
        if(Objects.isNull(bdcCjxxDO)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产持件信息");
        }

        if(StringUtils.isNotBlank(bdcCjxxDO.getCjxxid())){
            this.entityMapper.updateByPrimaryKeySelective(bdcCjxxDO);
        }else{
            bdcCjxxDO.setCjxxid(UUIDGenerator.generate16());
            this.entityMapper.insertSelective(bdcCjxxDO);
        }
        return bdcCjxxDO;
    }

    @Override
    public void plDeleteBdcCjxx(List<String> ids) {
        if(CollectionUtils.isEmpty(ids)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到需要删除的持件信息ID");
        }
        for(String id: ids){
            this.entityMapper.deleteByPrimaryKey(BdcCjxxDO.class, id);
        }
    }

}
