package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.supervise.core.domain.BdcLfCxjzjsDO;
import cn.gtmap.realestate.supervise.core.qo.LfWgxwQO;
import cn.gtmap.realestate.supervise.service.LfCxjzjsService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/10
 * @description 廉防7：诚信机制建设
 */
@Service
public class LfCxjzjsServiceImpl implements LfCxjzjsService {
    private static Logger logger = LoggerFactory.getLogger(LfCxjzjsServiceImpl.class);

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private StorageClientMatcher storageClient;


    /**
     * 违规信息查询（分页台账）
     * @param pageable 分页参数
     * @param lfWgxwQO 业务查询参数
     * @return 违规信息
     */
    @Override
    public Page<BdcLfCxjzjsDO> listWgxxTableData(Pageable pageable, LfWgxwQO lfWgxwQO) {
        return repo.selectPaging("listWgxwByPageOrder", lfWgxwQO, pageable);
    }

    /**
     * 查询指定违规信息记录
     *
     * @param id 违规信息ID
     * @return 违规信息
     */
    @Override
    public BdcLfCxjzjsDO getWgxxById(String id) {
        if(StringUtils.isBlank(id)) {
            throw new MissingArgumentException("未定义要查询违规信息ID");
        }
        return entityMapper.selectByPrimaryKey(BdcLfCxjzjsDO.class, id);
    }

    /**
     * 保存违规信息
     *
     * @param bdcLfCxjzjsDO 违规信息
     * @return {String} 违规信息ID
     */
    @Override
    public String saveWgxx(BdcLfCxjzjsDO bdcLfCxjzjsDO) {
        if(!CheckParameter.checkAnyParameter(bdcLfCxjzjsDO)) {
            throw new MissingArgumentException("未定义要保存的违规数据！");
        }

        if(StringUtils.isBlank(bdcLfCxjzjsDO.getId())) {
            bdcLfCxjzjsDO.setId(UUIDGenerator.generate16());
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            bdcLfCxjzjsDO.setJlr(userDto.getAlias());
            bdcLfCxjzjsDO.setJlrid(userDto.getId());
        }
        bdcLfCxjzjsDO.setJlsj(new Date());

        entityMapper.saveOrUpdate(bdcLfCxjzjsDO, bdcLfCxjzjsDO.getId());
        logger.debug("保存或更新违规信息一条，对应ID：{}", bdcLfCxjzjsDO.getId());
        return bdcLfCxjzjsDO.getId();
    }

    /**
     * 删除违规信息
     *
     * @param wgxxDOList 违规信息
     * @return {Integer} 删除记录
     */
    @Override
    public Integer deleteWgxxs(List<BdcLfCxjzjsDO> wgxxDOList) {
        if(CollectionUtils.isEmpty(wgxxDOList)) {
            throw new MissingArgumentException("未定义要删除的违规数据！");
        }

        int num = 0;
        for(BdcLfCxjzjsDO wgxxDO : wgxxDOList) {
            num += entityMapper.deleteByPrimaryKey(BdcLfCxjzjsDO.class, wgxxDO.getId());
        }
        logger.info("删除违规信息：{}", wgxxDOList);
        return num;
    }

    /**
     * 上传违规证明文件
     *
     * @param file   违规报告
     * @param wgxxid 违规信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    @Override
    public StorageDto uploadWgxxFile(MultipartFile file, String wgxxid) {
        if(StringUtils.isBlank(wgxxid) || "null".equals(wgxxid)) {
            throw new MissingArgumentException("未定义违规证明关联违规信息ID，无法上传！");
        }

        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = Constants.CXJZJS_WGZMWJ;

            byte[] fileBytes = file.getBytes();
            // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(Constants.CXJZJS_WGZMWJ_ID, Constants.CXJZJS_WGZMWJ_SPACE_ID, null, fileName, null, 6);
            if (CollectionUtils.isNotEmpty(storageDtoList)) {
                storageDto = storageDtoList.get(0);
                List<String> listId = new ArrayList();
                for (StorageDto storageDtoTemp : storageDtoList) {
                    listId.add(storageDtoTemp.getId());
                }
                if (CollectionUtils.isNotEmpty(listId)) {
                    boolean result = storageClient.deleteStorages(listId);
                    logger.warn("已有违规证明文件删除结果：{}。删除信息为：{}", result, JSONObject.toJSONString(storageDtoList));
                }
            }

            // 创建新的文件夹，已存在，则不再创建
            storageDto = storageClient.createRootFolder(Constants.CXJZJS_WGZMWJ_ID, Constants.CXJZJS_WGZMWJ_SPACE_ID, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                logger.info("违规证明文件上传信息：{}", JSONObject.toJSONString(storageDto));
            }

            // 保存附件信息
            BdcLfCxjzjsDO wgxxDO = new BdcLfCxjzjsDO();
            wgxxDO.setId(wgxxid);
            wgxxDO.setZmwjid(storageDto.getId());
            wgxxDO.setZmwjlj(storageDto.getDownUrl());
            wgxxDO.setZmwjmc(fileName);
            wgxxDO.setZmwjscsj(new Date());
            entityMapper.updateByPrimaryKeySelective(wgxxDO);
            logger.info("保存违规证明文件上传信息：{}", wgxxDO);
        } catch (Exception e) {
            logger.error("违规证明文件上传异常!", e);
        }
        return storageDto;
    }

    /**
     * 保存审核信息
     *
     * @param bdcLfCxjzjsDO 违规审核信息
     * @return {String} 违规信息ID
     */
    @Override
    public String saveWgxxShxx(BdcLfCxjzjsDO bdcLfCxjzjsDO) {
        if(StringUtils.isBlank(bdcLfCxjzjsDO.getId())) {
            throw new MissingArgumentException("未定义要审核的违规信息");
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            bdcLfCxjzjsDO.setShry(userDto.getAlias());
            bdcLfCxjzjsDO.setShrid(userDto.getId());
        }
        bdcLfCxjzjsDO.setShsj(new Date());
        bdcLfCxjzjsDO.setShzt(1);

        entityMapper.updateByPrimaryKeySelective(bdcLfCxjzjsDO);
        return bdcLfCxjzjsDO.getId();
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
