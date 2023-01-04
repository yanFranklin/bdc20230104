package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxqlrDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxsqsDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxsqsQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.mapper.BdcCxsqsMapper;
import cn.gtmap.realestate.inquiry.service.BdcCxsqsService;
import cn.gtmap.realestate.inquiry.service.BdcCxsqsSlbhScService;
import cn.gtmap.realestate.inquiry.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/22
 * @description 不动产查询申请书实现类
 */
@Service
public class BdcCxsqsServiceImpl implements BdcCxsqsService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCxsqsServiceImpl.class);

    @Autowired
    BdcCxsqsMapper bdcCxsqsMapper;

    /**
     * ORM操作
     */
    @Autowired
    private Repo repo;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    BdcCxsqsSlbhScService bdcCxsqsSlbhScService;

    /**
     * @param bdcCxsqsDTO 查询申请书对象
     * @return Integer 操纵结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新查询申请书信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcCxsqsDTO saveOrUpdateBdcCxsqs(BdcCxsqsDTO bdcCxsqsDTO) {
        if (null == bdcCxsqsDTO) {
            throw new MissingArgumentException("缺失查询申请书信息，所得数据为null");
        }
        // 插入新数据，否则更新查询申请书信息
        if (StringUtils.isBlank(bdcCxsqsDTO.getSqsid()) && StringUtils.isBlank(bdcCxsqsDTO.getCxsldjbh())) {
            // 生成查审申请书编号
            BdcCxsqsDO bdcCxsqsDOTemp = bdcCxsqsSlbhScService.generateCxsqsSldjBh();
            bdcCxsqsDTO.setCxsldjbh(bdcCxsqsDOTemp.getCxsldjbh());
            bdcCxsqsDTO.setSqsid(bdcCxsqsDOTemp.getSqsid());
        }
        // 查询申请书表信息
        BdcCxsqsDO bdcCxsqsDO = new BdcCxsqsDO();
        BeanUtils.copyProperties(bdcCxsqsDTO, bdcCxsqsDO);
        // 如果是新增信息，再生成编号的时候已经入库了，所以这里都是直接更新库
        entityMapper.updateByPrimaryKeySelective(bdcCxsqsDO);

        // 查询权利人信息
        List<BdcCxqlrDO> bdcCxqlrDOList = bdcCxsqsDTO.getBdcCxqlrDOList();
        // 保存查询权利人信息
        List<BdcCxqlrDO> bdcCxqlrDOS = this.batchSaveCxQlr(bdcCxsqsDTO.getSqsid(), bdcCxqlrDOList);
        bdcCxsqsDTO.setBdcCxqlrDOList(bdcCxqlrDOS);

        return bdcCxsqsDTO;
    }

    /**
     * @param bdcCxsqsQO 查询申请书QO
     * @return 申请书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询申请书信息
     */
    @Override
    public List<BdcCxsqsDTO> queryBdcCxSqs(BdcCxsqsQO bdcCxsqsQO) {
        if (null == bdcCxsqsQO) {
            throw new MissingArgumentException("缺失查询参数bdcCxsqsQO");
        }
        Example example = new Example(BdcCxsqsDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcCxsqsQO.getSqsid())) {
            criteria.andEqualTo(Constants.KEY_SQSID, bdcCxsqsQO.getSqsid());
        }
        if (StringUtils.isNotBlank(bdcCxsqsQO.getCxsldjbh())) {
            criteria.andEqualTo("cxsldjbh", bdcCxsqsQO.getCxsldjbh());
        }
        List<BdcCxsqsDO> bdcCxsqsDOList = entityMapper.selectByExampleNotNull(example);

        List<BdcCxsqsDTO> bdcCxsqsDTOList = new ArrayList();
        if (CollectionUtils.isEmpty(bdcCxsqsDOList)) {
            return bdcCxsqsDTOList;
        }
        for (BdcCxsqsDO bdcCxsqsDO : bdcCxsqsDOList) {
            BdcCxsqsDTO bdcCxsqsDTO = new BdcCxsqsDTO();
            BeanUtils.copyProperties(bdcCxsqsDO, bdcCxsqsDTO);

            Example exampleTemp = new Example(BdcCxqlrDO.class);
            exampleTemp.createCriteria().andEqualTo(Constants.KEY_SQSID, bdcCxsqsDO.getSqsid());
            bdcCxsqsDTO.setBdcCxqlrDOList(entityMapper.selectByExampleNotNull(exampleTemp));

            bdcCxsqsDTOList.add(bdcCxsqsDTO);
        }
        return bdcCxsqsDTOList;
    }

    /**
     * @param pageable
     * @param bdcCxsqsQO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询
     */
    @Override
    public Page<BdcCxsqsDO> listBdcCxsqsPage(Pageable pageable, BdcCxsqsQO bdcCxsqsQO) {
        if (null == bdcCxsqsQO) {
            throw new NullPointerException("查询子系统：查询申请书查询参数为空！");
        }

        return repo.selectPaging("listBdcCxsqsPage", bdcCxsqsQO, pageable);
    }

    /**
     * @param sqsid          查询申请书ID
     * @param bdcCxqlrDOList 查询权利人信息
     * @return List<BdcCxqlrDO> 保存的权利人信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量保存查询申请权利人新信息
     */
    @Override
    public List<BdcCxqlrDO> batchSaveCxQlr(String sqsid, List<BdcCxqlrDO> bdcCxqlrDOList) {
        if (CollectionUtils.isEmpty(bdcCxqlrDOList) || StringUtils.isBlank(sqsid)) {
            LOGGER.info("缺失查询权利人信息，或者缺失查询申请书ID");
            return new ArrayList();
        }
        // 保存前，先删除已有的权利人信息
        Example example = new Example(BdcCxqlrDO.class);
        example.createCriteria().andEqualTo(Constants.KEY_SQSID, sqsid);
        entityMapper.deleteByExampleNotNull(example);

        // 重新保存重新权利人信息
        List<BdcCxqlrDO> bdcCxqlrDOParam = new ArrayList();
        for (BdcCxqlrDO bdcCxqlrDO : bdcCxqlrDOList) {
            if (StringUtils.isNotBlank(bdcCxqlrDO.getQlrmc())) {
                bdcCxqlrDO.setQlrid(UUIDGenerator.generate16());
                bdcCxqlrDO.setSqsid(sqsid);
                bdcCxqlrDOParam.add(bdcCxqlrDO);
            }
        }
        if (CollectionUtils.isNotEmpty(bdcCxqlrDOParam)) {
            entityMapper.batchSaveSelective(bdcCxqlrDOParam);
        }
        return bdcCxqlrDOParam;
    }
}
