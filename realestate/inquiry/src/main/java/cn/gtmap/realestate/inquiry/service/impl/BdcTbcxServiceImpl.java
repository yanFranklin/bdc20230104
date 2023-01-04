package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcTbcxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcTbcxMapper;
import cn.gtmap.realestate.inquiry.service.BdcTbcxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2021-04-14
 * @description 调拨查询服务imp
 */
@Service
public class BdcTbcxServiceImpl implements BdcTbcxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcTbcxServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcTbcxMapper bdcTbcxMapper;

    /**
     * @param bdcTbcxDO 调拨信息实体
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存调拨信息实体
     */
    @Override
    public BdcTbcxDO saveBdcTbxx(BdcTbcxDO bdcTbcxDO) {
        if(null != bdcTbcxDO){
            if(StringUtils.isBlank(bdcTbcxDO.getId())){
                bdcTbcxDO.setId(UUIDGenerator.generate16());
                bdcTbcxDO.setLrr(userManagerUtils.getUserAlias());
                bdcTbcxDO.setLrsj(new Date());
                entityMapper.insertSelective(bdcTbcxDO);

            }else{
                bdcTbcxDO.setLrr(userManagerUtils.getUserAlias());
                bdcTbcxDO.setLrsj(new Date());
                entityMapper.updateByPrimaryKeySelective(bdcTbcxDO);
            }
            return bdcTbcxDO;
        }
        return null;
    }

    /**
     * @param bdcTbcxDOList
     * @return {int} 操作数据记录数
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除调拨信息
     */
    @Override
    public int batchDeleteBdcTbxx(List<BdcTbcxDO> bdcTbcxDOList) {
        if(CollectionUtils.isNotEmpty(bdcTbcxDOList)){
            int count = 0;
            List<String> listWjzxid = new ArrayList<>();
            List<String> listId = new ArrayList<>();
            // 删除登记库的调拨信息
            for(BdcTbcxDO bdcTbcxDO : bdcTbcxDOList){
                if(StringUtils.isNotBlank(bdcTbcxDO.getId())){
                    listId.add(bdcTbcxDO.getId());
                }
                if(StringUtils.isNotBlank(bdcTbcxDO.getWjzxid())){
                    listWjzxid.add(bdcTbcxDO.getWjzxid());
                }
                count++;
            }

            if(CollectionUtils.isNotEmpty(listId)){
                bdcTbcxMapper.deleteZctbs(listId);
            }

            // 删除文件中心的文件
            if(CollectionUtils.isNotEmpty(listWjzxid)){
                storageClient.deleteStorages(listWjzxid);
            }
            return count;
        }
        return 0;
    }

    /**
     * @param id
     * @return BdcTbcxDO 资产调拨数据
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询资产调拨数据
     */
    @Override
    public BdcTbcxDO queryZctbxx(String id) {
        if(StringUtils.isNotBlank(id)){
            Example example = new Example(BdcTbcxDO.class);
            example.createCriteria().andEqualTo("id", id);
            List<BdcTbcxDO> list = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(list)){
                return list.get(0);
            }
        }
        return new BdcTbcxDO();
    }


}
