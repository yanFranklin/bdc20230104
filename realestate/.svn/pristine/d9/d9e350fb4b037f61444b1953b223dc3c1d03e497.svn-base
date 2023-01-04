package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZgqkDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZjxxDO;
import cn.gtmap.realestate.supervise.core.qo.LfZjxxQO;
import cn.gtmap.realestate.supervise.service.LfDczjjgService;
import cn.gtmap.realestate.supervise.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/07
 * @description 廉防6：督查质检监管逻辑处理
 */
@Service
public class LfDczjjgServiceImpl implements LfDczjjgService {
    private static Logger logger = LoggerFactory.getLogger(LfDczjjgServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private StorageClientMatcher storageClient;


    /**
     * 质检信息查询（分页台账）
     * @param pageable 分页参数
     * @param zjxxQO   业务查询参数
     * @return 质检信息
     */
    @Override
    public Page<BdcLfDczjjgZjxxDO> listZjxxTableData(Pageable pageable, LfZjxxQO zjxxQO) {
        return repo.selectPaging("listZjxxByPageOrder", zjxxQO, pageable);
    }

    /**
     * 查询指定质检信息记录
     * @param id 质检信息ID
     * @return 质检信息
     */
    @Override
    public BdcLfDczjjgZjxxDO getZjxxById(String id) {
        if(StringUtils.isBlank(id)) {
            throw new MissingArgumentException("未定义要查询质检信息ID");
        }
        return entityMapper.selectByPrimaryKey(BdcLfDczjjgZjxxDO.class, id);
    }

    /**
     * 保存质检信息
     * @param bdcLfDczjjgZjxxDO 质检信息
     * @return {String} 质检信息ID
     */
    @Override
    public String saveZjxx(BdcLfDczjjgZjxxDO bdcLfDczjjgZjxxDO) {
        if(!CheckParameter.checkAnyParameter(bdcLfDczjjgZjxxDO)) {
            throw new MissingArgumentException("未定义要保存的质检数据！");
        }

        if(StringUtils.isBlank(bdcLfDczjjgZjxxDO.getId())) {
            bdcLfDczjjgZjxxDO.setId(UUIDGenerator.generate16());
        }
        bdcLfDczjjgZjxxDO.setZjsj(new Date());

        // 审核时设置审查时间
        if(StringUtils.isNotBlank(bdcLfDczjjgZjxxDO.getScryid()) || StringUtils.isNotBlank(bdcLfDczjjgZjxxDO.getScbmid())) {
            bdcLfDczjjgZjxxDO.setScsj(new Date());
        }

        entityMapper.saveOrUpdate(bdcLfDczjjgZjxxDO, bdcLfDczjjgZjxxDO.getId());
        logger.debug("保存或更新质检信息一条，对应ID：{}", bdcLfDczjjgZjxxDO.getId());
        return bdcLfDczjjgZjxxDO.getId();
    }

    /**
     * 删除质检信息
     * @param zjxxDOList 质检信息
     * @return {Integer} 删除记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteZjxxs(List<BdcLfDczjjgZjxxDO> zjxxDOList) {
        if(CollectionUtils.isEmpty(zjxxDOList)) {
            throw new MissingArgumentException("未定义要删除的质检数据！");
        }

        int num = 0;
        for(BdcLfDczjjgZjxxDO zjxxDO : zjxxDOList) {
            num += entityMapper.deleteByPrimaryKey(BdcLfDczjjgZjxxDO.class, zjxxDO.getId());
        }
        logger.info("删除质检信息：{}", zjxxDOList);
        return num;
    }

    /**
     * 上传质检报告文件到大云stroage
     * @param file 质检报告
     * @param zjxxid 质检信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @Override
    public StorageDto uploadZjxxFile(MultipartFile file, String zjxxid) {
        if(StringUtils.isBlank(zjxxid) || "null".equals(zjxxid)) {
            throw new MissingArgumentException("未定义质检报告关联质检信息ID，无法上传！");
        }

        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = Constants.DCZJJG_ZJWJ;

            byte[] fileBytes = file.getBytes();
            // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(Constants.DCZJJG_ZJWJ_ID, Constants.DCZJJG_ZJWJ_SPACE_ID, null, fileName, null, 6);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    logger.warn("已有质检报告文件删除结果：{}。删除信息为：{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }

            // 创建新的文件夹，已存在，则不再创建
            storageDto = storageClient.createRootFolder(Constants.DCZJJG_ZJWJ_ID, Constants.DCZJJG_ZJWJ_SPACE_ID, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                logger.info("质检报告文件上传信息：{}", JSONObject.toJSONString(storageDto));
            }

            // 保存附件信息
            BdcLfDczjjgZjxxDO zjxxDO = new BdcLfDczjjgZjxxDO();
            zjxxDO.setId(zjxxid);
            zjxxDO.setZjwjid(storageDto.getId());
            zjxxDO.setZjwjlj(storageDto.getDownUrl());
            zjxxDO.setZjwjmc(fileName);
            zjxxDO.setZjwjscsj(new Date());
            entityMapper.updateByPrimaryKeySelective(zjxxDO);
            logger.info("保存质检报告文件上传信息：{}", zjxxDO);
        } catch (Exception e) {
            logger.error("质检报告文件上传异常!", e);
        }
        return storageDto;
    }

    /**
     * 保存整改情况信息
     * @param zgqkDO 整改信息
     * @return {String} 整改情况ID
     */
    @Override
    public String saveZgqk(BdcLfDczjjgZgqkDO zgqkDO) {
        if(!CheckParameter.checkAnyParameter(zgqkDO)) {
            throw new MissingArgumentException("未定义要保存的整改数据！");
        }

        if(StringUtils.isBlank(zgqkDO.getId())) {
            zgqkDO.setId(UUIDGenerator.generate16());
        }
        zgqkDO.setZgsj(new Date());
        zgqkDO.setZgsfwc(CommonConstantUtils.SF_S_DM);

        entityMapper.saveOrUpdate(zgqkDO, zgqkDO.getId());
        logger.debug("保存或更新整改信息一条，对应ID：{}", zgqkDO.getId());
        return zgqkDO.getId();
    }

    /**
     * 删除整改情况信息
     *
     * @param zgqkDOList 整改信息
     * @return {Integer} 删除记录数
     */
    @Override
    public Integer deleteZgqk(List<BdcLfDczjjgZgqkDO> zgqkDOList) {
        if(CollectionUtils.isEmpty(zgqkDOList)) {
            throw new MissingArgumentException("未定义要删除的整改数据！");
        }

        int num = 0;
        for(BdcLfDczjjgZgqkDO zgqkDO : zgqkDOList) {
            num += entityMapper.deleteByPrimaryKey(BdcLfDczjjgZgqkDO.class, zgqkDO.getId());
        }
        logger.info("删除整改信息：{}", zgqkDOList);
        return num;
    }

    /**
     * 上传整改报告文件
     * @param file   整改报告
     * @param zgqkid 整改信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @Override
    public StorageDto uploadZgbgFile(MultipartFile file, String zgqkid) {
        if(StringUtils.isBlank(zgqkid) || "null".equals(zgqkid)) {
            throw new MissingArgumentException("未定义整改报告关联整改信息ID，无法上传！");
        }

        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = Constants.DCZJJG_ZGBG;

            byte[] fileBytes = file.getBytes();
            // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(Constants.DCZJJG_ZGBG_ID, Constants.DCZJJG_ZGBG_SPACE_ID, null, fileName, null, 6);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    logger.warn("已有整改报告文件删除结果：{}。删除信息为：{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }

            // 创建新的文件夹，已存在，则不再创建
            storageDto = storageClient.createRootFolder(Constants.DCZJJG_ZGBG_ID, Constants.DCZJJG_ZGBG_SPACE_ID, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                logger.info("整改报告文件上传信息：{}", JSONObject.toJSONString(storageDto));
            }

            // 保存附件信息
            BdcLfDczjjgZgqkDO zgqkDO = new BdcLfDczjjgZgqkDO();
            zgqkDO.setId(zgqkid);
            zgqkDO.setZgwjid(storageDto.getId());
            zgqkDO.setZgwjlj(storageDto.getDownUrl());
            zgqkDO.setZgwjmc(fileName);
            zgqkDO.setZgwjscsj(new Date());
            entityMapper.updateByPrimaryKeySelective(zgqkDO);
            logger.info("保存整改报告文件上传信息：{}", zgqkDO);
        } catch (Exception e) {
            logger.error("整改报告文件上传异常!", e);
        }
        return storageDto;
    }

    /**
     * 查询质检记录关联的所有整改情况
     *
     * @param zjxxid 质检信息ID
     * @return {List} 整改情况
     */
    @Override
    public List<BdcLfDczjjgZgqkDO> listZgqkOfZjxx(String zjxxid) {
        if(StringUtils.isBlank(zjxxid)) {
            throw new MissingArgumentException("未定义要查询的质检信息ID");
        }

        Example example = new Example(BdcLfDczjjgZgqkDO.class);
        example.createCriteria().andEqualTo("zjxxid", zjxxid);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * 组织大云上传参数
     * @param currentUserName 当前用户名
     * @param fileByte        文件字节
     * @param fileName        文件名称
     * @return MultipartDto 大云上传参数
     */
    private MultipartDto getUploadParamDto(String currentUserName, StorageDto storageDto, byte[] fileByte, String fileName) {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(storageDto.getClientId());
        multipartDto.setData(fileByte);
        if (fileByte != null) {
            multipartDto.setOwner(currentUserName);
            multipartDto.setContentType("application/octet-stream");
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(storageDto.getName());
        }
        return multipartDto;
    }

}
