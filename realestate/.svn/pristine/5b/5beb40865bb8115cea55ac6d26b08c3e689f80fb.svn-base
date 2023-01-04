package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxSqryDO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfSqxxDTO;
import cn.gtmap.realestate.supervise.core.qo.BdcLfSqxxQO;
import cn.gtmap.realestate.supervise.service.LfZzqnjgService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/10
 * @description 4 职责权能监管-授权信息管理逻辑
 */
@Service
public class LfZzqnjgServiceImpl implements LfZzqnjgService {
    private static Logger logger = LoggerFactory.getLogger(LfZzqnjgServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private UserManagerClient userManagerClient;
    @Autowired
    private StorageClientMatcher storageClient;


    /**
     * 授权信息查询（分页台账）
     * @param pageable    分页参数
     * @param bdcLfSqxxQO 业务查询参数
     * @return 授权信息
     */
    @Override
    public Page<BdcLfSqxxDTO> listSqxxTableData(Pageable pageable, BdcLfSqxxQO bdcLfSqxxQO) {
        return repo.selectPaging("listSqxxByPageOrder", bdcLfSqxxQO, pageable);
    }

    /**
     * 查询指定授权信息记录
     *
     * @param id 授权信息ID
     * @return 授权信息
     */
    @Override
    public BdcLfSqxxDO getSqxxById(String id) {
        if(StringUtils.isBlank(id)) {
            throw new MissingArgumentException("未定义要查询授权信息ID");
        }
        return entityMapper.selectByPrimaryKey(BdcLfSqxxDO.class, id);
    }

    /**
     * 查询指定授权信息关联的授权用户
     *
     * @param sqxxid 授权信息ID
     * @return 授权用户
     */
    @Override
    public List<BdcLfSqxxSqryDO> getSqryBySqxxid(String sqxxid) {
        if(StringUtils.isBlank(sqxxid)) {
            throw new MissingArgumentException("未定义关联的授权信息ID");
        }

        Example example = new Example(BdcLfSqxxSqryDO.class);
        example.createCriteria().andEqualTo("sqxxid", sqxxid);
        return entityMapper.selectByExampleNotNull(example);
    }

    /**
     * 保存授权信息
     *
     * @param sqxx 授权信息
     * @return {String} 授权信息ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveSqxx(BdcLfSqxxDTO sqxx) {
        if(!CheckParameter.checkAnyParameter(sqxx) || (CollectionUtils.isEmpty(sqxx.getSqrys()) && StringUtils.isBlank(sqxx.getSqryxx()))) {
            throw new MissingArgumentException("未定义要保存的授权数据或授权人员！");
        }

        // 保存授权信息
        BdcLfSqxxDO bdcLfSqxxDO = new BdcLfSqxxDO();
        bdcLfSqxxDO.setId(sqxx.getId());
        bdcLfSqxxDO.setSqsm(sqxx.getSqsm());
        bdcLfSqxxDO.setCzsj(new Date());
        bdcLfSqxxDO.setSqlx(sqxx.getSqlx());

        if(StringUtils.isBlank(bdcLfSqxxDO.getId())) {
            bdcLfSqxxDO.setId(UUIDGenerator.generate16());
        } else {
            this.deleteSqrys(bdcLfSqxxDO.getId());
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            bdcLfSqxxDO.setCzr(userDto.getAlias());
            bdcLfSqxxDO.setCzrid(userDto.getId());
        }

        entityMapper.saveOrUpdate(bdcLfSqxxDO, bdcLfSqxxDO.getId());
        logger.debug("保存或更新授权信息一条，对应ID：{}", bdcLfSqxxDO.getId());

        // 保存授权人员信息
        this.generateSqrys(sqxx, bdcLfSqxxDO);
        int num = entityMapper.insertBatchSelective(sqxx.getSqrys());
        logger.info("新增授权用户{}条", num);

        return bdcLfSqxxDO.getId();
    }

    /**
     * 删除授权信息
     *
     * @param sqxxDOList 授权信息
     * @return {Integer} 删除记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteSqxxs(List<BdcLfSqxxDO> sqxxDOList) {
        if(CollectionUtils.isEmpty(sqxxDOList)) {
            throw new MissingArgumentException("未定义要删除的授权数据！");
        }

        int num = 0;
        for(BdcLfSqxxDO sqxxDO : sqxxDOList) {
            // 删除授权信息
            num += entityMapper.deleteByPrimaryKey(BdcLfSqxxDO.class, sqxxDO.getId());
            // 删除关联授权人员
            this.deleteSqrys(sqxxDO.getId());
        }
        logger.info("删除授权信息：{}", sqxxDOList);
        return num;
    }

    /**
     * 上传授权信息文件
     *
     * @param file   授权
     * @param sqxxid 授权信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @Override
    public StorageDto uploadSqxxFile(MultipartFile file, String sqxxid) {
        if(StringUtils.isBlank(sqxxid) || "null".equals(sqxxid)) {
            throw new MissingArgumentException("未定义授权关联授权信息ID，无法上传！");
        }

        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = Constants.ZZQNJG_SQWJ;

            byte[] fileBytes = file.getBytes();
            // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(Constants.ZZQNJG_SQWJ_ID, Constants.ZZQNJG_SQWJ_SPACE_ID, null, fileName, null, 6);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    logger.warn("已有授权文件删除结果：{}。删除信息为：{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }

            // 创建新的文件夹，已存在，则不再创建
            storageDto = storageClient.createRootFolder(Constants.ZZQNJG_SQWJ_ID, Constants.ZZQNJG_SQWJ_SPACE_ID, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                logger.info("授权文件上传信息：{}", JSONObject.toJSONString(storageDto));
            }

            // 保存附件信息
            BdcLfSqxxDO sqxx = new BdcLfSqxxDO();
            sqxx.setId(sqxxid);
            sqxx.setSpwjid(storageDto.getId());
            sqxx.setSpwjlj(storageDto.getDownUrl());
            sqxx.setSpwjmc(fileName);
            sqxx.setSpwjscsj(new Date());
            entityMapper.updateByPrimaryKeySelective(sqxx);
            logger.info("保存授权文件上传信息：{}", sqxx);
        } catch (Exception e) {
            logger.error("授权文件上传异常!", e);
        }
        return storageDto;
    }

    /**
     * 删除授权人员
     * @param sqxxid 授权信息ID
     */
    private void deleteSqrys(String sqxxid) {
        Example example = new Example(BdcLfSqxxSqryDO.class);
        example.createCriteria().andEqualTo("sqxxid", sqxxid);
        int num = entityMapper.deleteByExampleNotNull(example);
        logger.info("删除授权信息{}关联的授权用户{}记录", sqxxid, num);
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

    /**
     * 处理授权信息关联的授权人员信息
     * @param sqxx 授权信息
     * @param bdcLfSqxxDO 要保存的授权信息
     */
    private void generateSqrys(BdcLfSqxxDTO sqxx, BdcLfSqxxDO bdcLfSqxxDO) {
        // 传递了授权人员信息集合
        if(CollectionUtils.isNotEmpty(sqxx.getSqrys())) {
            for(BdcLfSqxxSqryDO sqry : sqxx.getSqrys()) {
                sqry.setId(UUIDGenerator.generate16());
                sqry.setSqxxid(bdcLfSqxxDO.getId());
                sqry.setSqsj(new Date());
            }
            return;
        }

        // 传值了授权人员ID字符串
        List<String> userIds = Arrays.asList(sqxx.getSqryxx().split(","));
        List<UserDto> userDtoList = userManagerClient.listUserByIds(userIds);
        if(CollectionUtils.isEmpty(userDtoList)) {
            logger.error("未找到授权人员用户账号信息{}", sqxx.getSqryxx());
            return;
        }
        sqxx.setSqrys(new ArrayList<>());
        for(UserDto user : userDtoList) {
            BdcLfSqxxSqryDO sqry = new BdcLfSqxxSqryDO();
            sqry.setId(UUIDGenerator.generate16());
            sqry.setSqxxid(bdcLfSqxxDO.getId());
            sqry.setSqry(user.getAlias());
            sqry.setSqryid(user.getId());
            sqry.setSqsj(new Date());
            List<OrganizationDto> organizationDtos = user.getOrgRecordList();
            if (CollectionUtils.isNotEmpty(organizationDtos)){
                sqry.setBmid(organizationDtos.get(0).getId());
                sqry.setBmmc(organizationDtos.get(0).getName());
            }
            sqry.setKs(sqxx.getKs());
            sqxx.getSqrys().add(sqry);
        }
    }
}
