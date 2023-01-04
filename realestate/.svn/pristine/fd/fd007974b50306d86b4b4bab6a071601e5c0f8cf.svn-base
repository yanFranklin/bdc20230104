package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcDsffjMrmlPzDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcDsffjXxItemDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlFjtzDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.BdcFjtzService;
import cn.gtmap.realestate.inquiry.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wangyinghao
 */
@Service
public class BdcFjtzServiceImpl implements BdcFjtzService {
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    StorageClientMatcher storageClient;

    public static final String DKFJLX = "dkfj";
    /**
     * 新增附件信息
     *
     * @param bdcSlFjtzDTO
     * @return
     */
    @Override
    public BdcDsffjXxItemDO addFjxx(BdcSlFjtzDTO bdcSlFjtzDTO) {
        UserDto currentUser = userManagerUtils.getCurrentUser();
        BdcDsffjXxItemDO bdcDsffjXxItemDO = new BdcDsffjXxItemDO();
        bdcDsffjXxItemDO.setId(UUIDGenerator.generate());
        bdcDsffjXxItemDO.setDkbh(bdcSlFjtzDTO.getDkbh());
        bdcDsffjXxItemDO.setZl(bdcSlFjtzDTO.getZl());
        bdcDsffjXxItemDO.setBdcdyh(bdcSlFjtzDTO.getBdcdyh());
        bdcDsffjXxItemDO.setWjzxid(UUIDGenerator.generate());
        bdcDsffjXxItemDO.setCjsj(new Date());
        bdcDsffjXxItemDO.setCjr(currentUser.getUsername());
        entityMapper.insertSelective(bdcDsffjXxItemDO);

        //创建默认目录
        Example example = new Example(BdcDsffjMrmlPzDO.class);
        example.createCriteria().andEqualTo("fjlx",DKFJLX);
        example.setOrderByClause("sxh");
        List<BdcDsffjMrmlPzDO> bdcDsffjMrmlPzDOS = entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bdcDsffjMrmlPzDOS)){
            StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, bdcDsffjXxItemDO.getWjzxid(), bdcSlFjtzDTO.getDkbh(), "");
            for (BdcDsffjMrmlPzDO bdcDsffjMrmlPzDO : bdcDsffjMrmlPzDOS) {
                storageClient.createFolder(CommonConstantUtils.WJZX_CLIENTID,  bdcDsffjXxItemDO.getId(), storageDto.getId(), bdcDsffjMrmlPzDO.getMrml(),"");
            }
        }
        return bdcDsffjXxItemDO;
    }
}
