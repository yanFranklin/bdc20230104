package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcWtsjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.BdcWtsjService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/11
 * @description 问题数据服务
 */
@Service
public class BdcWtsjServiceImpl implements BdcWtsjService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Override
    public List<BdcWtsjDO> queryBdcWtsj(BdcWtsjDO bdcWtsjDO){
        if (bdcWtsjDO == null || StringUtils.isBlank(bdcWtsjDO.getWtsj())) {
            throw new AppException("获取问题数据表的问题数据不能为空！");
        }
        return entityMapper.select(bdcWtsjDO);

    }

    @Override
    public int addWtBdcdy(List<BdcWtsjDO> bdcWtsjDOList){
        if (CollectionUtils.isEmpty(bdcWtsjDOList)) {
            throw new AppException("添加问题数据的参数不能为空");
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        for (BdcWtsjDO bdcWtsjDO : bdcWtsjDOList) {
            bdcWtsjDO.setWtsjid(UUIDGenerator.generate16());
            bdcWtsjDO.setWtsjzt(CommonConstantUtils.WTSJZT_CZWT);
            if (userDto != null) {
                bdcWtsjDO.setCjr(userDto.getAlias());
                bdcWtsjDO.setCjrid(userDto.getId());
            }
            bdcWtsjDO.setCjsj(new Date());

        }
        return entityMapper.insertBatchSelective(bdcWtsjDOList);

    }

    @Override
    public int jjBdcWtsj(List<BdcWtsjDO> bdcWtsjDOList, String jjfa){
        if (CollectionUtils.isEmpty(bdcWtsjDOList)) {
            throw new AppException("解决问题数据的参数不能为空");
        }
        int count = 0;
        UserDto userDto = userManagerUtils.getCurrentUser();
        for (BdcWtsjDO bdcWtsjDO : bdcWtsjDOList) {
            bdcWtsjDO.setWtsjzt(CommonConstantUtils.WTSJZT_YJJ);
            if (userDto != null) {
                bdcWtsjDO.setJjr(userDto.getAlias());
                bdcWtsjDO.setJjrid(userDto.getId());
            }
            bdcWtsjDO.setJjsj(new Date());
            bdcWtsjDO.setJjfa(jjfa);

            count += entityMapper.updateByPrimaryKeySelective(bdcWtsjDO);
        }
        return count;

    }

    @Override
    public int updateWtsj(BdcWtsjDO bdcWtsjDO){
        if(StringUtils.isBlank(bdcWtsjDO.getWtsjid())){
            throw new AppException("更新问题数据缺失主键");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcWtsjDO);

    }
}
