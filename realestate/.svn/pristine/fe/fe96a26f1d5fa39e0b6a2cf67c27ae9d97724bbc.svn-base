package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcHmdDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.init.core.service.BdcHmdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2021/10/25
 * @description 不动产黑名单实现类
 */
@Service
public class BdcHmdServiceImpl implements BdcHmdService {

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    @Override
    public List<BdcHmdDO> queryBdcHmd(BdcHmdDO bdcHmdDO) {
        if (bdcHmdDO == null) {
            throw new AppException("未获取到不动产黑名单查询参数。");
        }
        return entityMapper.select(bdcHmdDO);
    }

    @Override
    public void updateBdcHmd(BdcHmdDO bdcHmdDO) {
        if (bdcHmdDO != null && StringUtils.isNotBlank(bdcHmdDO.getHmdid())) {
            entityMapper.updateByPrimaryKeySelective(bdcHmdDO);
        } else {
            throw new AppException(ErrorCode.MISSING_ARG , "缺失参数黑名单ID");
        }
    }

    @Override
    public BdcHmdDO saveBdcHmd(BdcHmdDO bdcHmdDO) {
        if (bdcHmdDO != null) {
            if (StringUtils.isBlank(bdcHmdDO.getHmdid())) {
                bdcHmdDO.setHmdid(UUIDGenerator.generate16());
                entityMapper.insertSelective(bdcHmdDO);
            }else{
                entityMapper.updateByPrimaryKeySelective(bdcHmdDO);
            }
        }
        return bdcHmdDO;
    }

    @Override
    public void jsBljl(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("未获取到工作流实例ID。");
        }
        List<BdcZsDO> bdcZsDOList = this.bdcZsFeignService.queryYxmBdcqzhByGzlslid(processInsId, null);
        if(CollectionUtils.isNotEmpty(bdcZsDOList)){
            List<BdcHmdDO> bdcHmdDOList = new ArrayList<>();
            for(BdcZsDO bdcZsDO : bdcZsDOList){
                if(StringUtils.isNotBlank(bdcZsDO.getBdcqzh())){
                    BdcHmdDO queryparam = new BdcHmdDO();
                    queryparam.setBdcqzh(bdcZsDO.getBdcqzh());
                    queryparam.setHmdztlb(CommonConstantUtils.HMD_ZT_ZS);
                    queryparam.setHmdzt(CommonConstantUtils.HMD_STATUS_VALID);
                    List<BdcHmdDO> hmdxx = this.queryBdcHmd(queryparam);
                    if(CollectionUtils.isNotEmpty(hmdxx)){
                        bdcHmdDOList.addAll(hmdxx);
                    }
                }
            }

            // 解锁黑名单操作
            if(CollectionUtils.isNotEmpty(bdcHmdDOList)){
                String jsyy = "业务审批通过，不动产证书自动解锁。";
                UserDto userDto = userManagerUtils.getCurrentUser();
                for(BdcHmdDO bdcHmdDO : bdcHmdDOList){
                    bdcHmdDO.setHmdzt(CommonConstantUtils.HMD_STATUS_HISTORY);
                    bdcHmdDO.setJssj(new Date());
                    bdcHmdDO.setJsyy(jsyy);
                    if(Objects.nonNull(userDto)){
                        bdcHmdDO.setJsr(userDto.getAlias());
                    }
                }
                this.entityMapper.batchSaveSelective(bdcHmdDOList);
            }
        }
    }

    @Override
    public void batchDeleteBdcHmd(List<String> hmdIdList) {
        if (CollectionUtils.isEmpty(hmdIdList)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到黑名单ID集合");
        }
        for(String hmdid : hmdIdList){
            this.entityMapper.deleteByPrimaryKey(BdcHmdDO.class, hmdid);
        }
    }

    @Override
    public void jsBljlxx(List<BdcHmdDO> bdcHmdDOList) {
        if(CollectionUtils.isEmpty(bdcHmdDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到黑名单信息");
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        for(BdcHmdDO bdcHmdDO : bdcHmdDOList){
            bdcHmdDO.setHmdzt(CommonConstantUtils.HMD_STATUS_HISTORY);
            bdcHmdDO.setJssj(new Date());
            if(Objects.nonNull(userDto)){
                bdcHmdDO.setJsr(userDto.getAlias());
            }
        }
        this.entityMapper.batchSaveSelective(bdcHmdDOList);
    }
}
