package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.service.BdcGgtsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/07/23/0:05
 * @Description: 公告推送service
 */
@Service
public class BdcGgtsServiceImpl implements BdcGgtsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGgtsServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    /**
     * 事后公告，获取原项目的信息进行保存
     * @param processInsId 工作流实例ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pushBdcGgtz(String processInsId) {
        int result = 0;
        if(StringUtils.isBlank(processInsId)){
            return result;
        }
        // 删除原有公告信息
        this.deleteOriginGgxx(processInsId);

        // 根据项目附表中是否公告来生成公告数据
        BdcXmFbQO bdcXmFbQO =new BdcXmFbQO();
        bdcXmFbQO.setGzlslid(processInsId);
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isEmpty(bdcXmFbDOList)) {
            return result;
        }
        bdcXmFbDOList = bdcXmFbDOList.stream().filter(bdcXmFbDO -> bdcXmFbDO.getSfgg() != null && bdcXmFbDO.getSfgg()== 1).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(bdcXmFbDOList)){
            // 获取原项目信息
            List<BdcXmDO> yxmList = this.bdcXmFeignService.listYxmByGzlslid(new BdcXmLsgxDO(), processInsId, null);

            if(CollectionUtils.isEmpty(yxmList)){
                LOGGER.error("生成事后公告失败，未获取到原项目信息，当前流程工作流实例ID:{}", processInsId);
                return result;
            }

            // 1、生成公告信息
            String ggid = this.generateGgid(processInsId);

            if(StringUtils.isNotBlank(ggid)){
                result = 1;
            }
            // 2、生成公告业务数据
            List<BdcGgywsjDO> bdcGgywsjDOList = this.generateGgYwsj(ggid, yxmList);

            // 3、生成公告关系
            this.generateGggx(ggid, bdcGgywsjDOList);
        }
        return result;
    }

    /**
     * 删除原有公告信息
     */
    private void deleteOriginGgxx(String gzlslid){
        //获取原有公告内容
        Example example = new Example(BdcGgDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        List<BdcGgDO> bdcGgDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bdcGgDOList)) {
            for (BdcGgDO bdcGgDO : bdcGgDOList) {
                // 1、删除公告信息
                this.entityMapper.deleteByPrimaryKey(BdcGgDO.class, bdcGgDO.getGgid());
                // 2、删除公告关系
                this.deleteBdcGggx(bdcGgDO.getGgid());
                // 3、删除公告业务数据
                this.deleteBdcGgYwsj(bdcGgDO.getGgid());
            }
        }
    }

    /**
     * 根据公告ID删除公告关系
     */
    private void deleteBdcGggx(String ggid){
        Example example = new Example(BdcGggxDO.class);
        example.createCriteria().andEqualTo("ggid", ggid);
        this.entityMapper.deleteByExample(BdcGggxDO.class, example);
    }

    /**
     * 根据公告ID删除公告业务数据
     */
    private void deleteBdcGgYwsj(String ggid){
        Example example = new Example(BdcGgywsjDO.class);
        example.createCriteria().andEqualTo("ggid", ggid);
        this.entityMapper.deleteByExample(BdcGgywsjDO.class, example);
    }

    /**
     * 生成公告信息
     */
    private String generateGgid(String processInsId){
        String ggid = UUIDGenerator.generate16();
        BdcGgDO bdcGgDO = new BdcGgDO();
        bdcGgDO.setGgid(ggid);
        bdcGgDO.setGzlslid(processInsId);
        bdcGgDO.setGgzt("0");
        entityMapper.insertSelective(bdcGgDO);
        return ggid;
    }

    /**
     * 生成公告业务数据
     */
    private List<BdcGgywsjDO> generateGgYwsj(String ggid, List<BdcXmDO> yxmList){
        List<BdcGgywsjDO> bdcGgywsjDOList = new ArrayList<>(yxmList.size());
        for(BdcXmDO bdcXmDO : yxmList){
            BdcGgywsjDO bdcGgywsjDO = new BdcGgywsjDO();
            BeanUtils.copyProperties(bdcXmDO, bdcGgywsjDO);
            bdcGgywsjDO.setQllx(String.valueOf(bdcXmDO.getQllx()));
            bdcGgywsjDO.setZdzhmj(String.valueOf(bdcXmDO.getZdzhmj()));
            bdcGgywsjDO.setDzwyt(String.valueOf(bdcXmDO.getDzwyt()));
            bdcGgywsjDO.setYwsjid(UUIDGenerator.generate16());
            bdcGgywsjDO.setGgid(ggid);
            bdcGgywsjDOList.add(bdcGgywsjDO);
        }
        this.entityMapper.insertBatchSelective(bdcGgywsjDOList);
        return bdcGgywsjDOList;
    }

    /**
     * 生成公告关系
     */
    private void generateGggx(String ggid, List<BdcGgywsjDO> bdcGgywsjDOList){
        List<BdcGggxDO> bdcGggxDOList = new ArrayList<>(bdcGgywsjDOList.size());
        for(BdcGgywsjDO bdcGgywsjDO : bdcGgywsjDOList){
            BdcGggxDO bdcGggxDO = new BdcGggxDO();
            bdcGggxDO.setGxid(UUIDGenerator.generate16());
            bdcGggxDO.setGgid(ggid);
            bdcGggxDO.setXmid(bdcGgywsjDO.getXmid());
            bdcGggxDO.setYwsjid(bdcGgywsjDO.getYwsjid());
            bdcGggxDOList.add(bdcGggxDO);
        }
        this.entityMapper.insertBatchSelective(bdcGggxDOList);
    }
}
