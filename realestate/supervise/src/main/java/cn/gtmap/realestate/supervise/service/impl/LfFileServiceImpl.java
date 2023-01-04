package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZjxxDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfFjxxDO;
import cn.gtmap.realestate.supervise.core.mapper.LfDczjjgMapper;
import cn.gtmap.realestate.supervise.core.qo.LfZjxxQO;
import cn.gtmap.realestate.supervise.service.LfDczjjgService;
import cn.gtmap.realestate.supervise.service.LfFileService;
import cn.gtmap.realestate.supervise.util.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class LfFileServiceImpl implements LfFileService {
    private static Logger logger = LoggerFactory.getLogger(LfFileServiceImpl.class);
    private final ScheduledExecutorService scheduledExecutorService =  new ScheduledThreadPoolExecutor(1, new ThreadFactoryBuilder().setNameFormat("lf-pool-%d").build());

    @Autowired
    private Repo repo;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private LfDczjjgService lfDczjjgService;

    @Resource
    private LfDczjjgMapper lfDczjjgMapper;

    @Value("${zgtz.ydzgDay:7}")
    private Integer ydzgDay;

    @Value("${zgtz.jdzgDay:10}")
    private Integer jdzgDay;


    @Override
    public StorageDto uploadFile(MultipartFile file, String ywid) {
        if(StringUtils.isBlank(ywid) || "null".equals(ywid)) {
            throw new MissingArgumentException("未定义关联业务信息ID，无法上传！");
        }

        StorageDto storageDto = null;
        try {
            String userName = userManagerUtils.getCurrentUserName();
            // 上传的文件名称
            String fileName = file.getOriginalFilename();
            // 上传的文件夹名称
            String folderName = Constants.FJWJ;

            byte[] fileBytes = file.getBytes();
            // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
            List<StorageDto> storageDtoList = storageClient.listStoragesByName(Constants.FJWJ_ID, Constants.FJWJ_SPACE_ID, null, fileName, null, 6);
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
            storageDto = storageClient.createRootFolder(Constants.FJWJ_ID, Constants.FJWJ_SPACE_ID, folderName, null);
            if (storageDto != null) {
                MultipartDto multipartDto = this.getUploadParamDto(userName, storageDto, fileBytes, fileName);
                storageDto = storageClient.multipartUpload(multipartDto);
                logger.info("文件上传信息：{}", JSONObject.toJSONString(storageDto));
            }

            // 保存附件信息
            BdcLfFjxxDO fjxxDO = new BdcLfFjxxDO();
            fjxxDO.setId(UUIDGenerator.generate16());
            fjxxDO.setYwid(ywid);
            fjxxDO.setWjid(storageDto.getId());
            fjxxDO.setWjlj(storageDto.getDownUrl());
            fjxxDO.setWjmc(fileName);
            fjxxDO.setWjscsj(new Date());
            entityMapper.insertSelective(fjxxDO);
            logger.info("保存文件上传信息：{}，业务ID：{}", fjxxDO, ywid);
            // 上传质检报告后，如果是月度质检，则要求在质检台账上传后的7天内（可配置）必须上传整改报告
            BdcLfDczjjgZjxxDO zjxxDO1 = lfDczjjgService.getZjxxById(ywid);
            if (zjxxDO1 != null){
                // 月度质检,7天后，查询是否上传？是，没问提，否：标记一下
                if (zjxxDO1.getZjlx() == 2){
                    zgtzTimerTask("zgMonthTimer",ydzgDay,ywid,"2",zjxxDO1);
                } else if (zjxxDO1.getZjlx() == 3){
                    zgtzTimerTask("zgSeasonTimer",jdzgDay,ywid,"3",zjxxDO1);
                }
            }
        } catch (Exception e) {
            logger.error("文件上传异常!", e);
        }
        return storageDto;
    }

    /**
     * 督察质检监管，整改台账，上传整改报告的Timer定时器
     * @param timerName 定时器名称
     * @param day       间隔时长
     */
    private void zgtzTimerTask(String timerName, Integer day, String zjxxid, String zjlx, BdcLfDczjjgZjxxDO zjxxDO) {
        scheduledExecutorService.schedule(() -> {
            // 7天后，查询是否上传了整改报告
            LfZjxxQO lfZjxxQO = new LfZjxxQO();
            lfZjxxQO.setId(zjxxid);
            lfZjxxQO.setZjlx(zjlx);
            List<BdcLfDczjjgZjxxDO> list = lfDczjjgMapper.zjxxWithoutZgwj(lfZjxxQO);
            logger.info("查询没有上传整改报告的质检信息：{}", list.toString());
            // 没有按时上传整改报告
            if (CollectionUtils.isNotEmpty(list)) {
                zjxxDO.setSfassczgwj("false");
                entityMapper.updateByPrimaryKeySelective(zjxxDO);
                logger.info("成功标记没有按时上传整改文件的质检信息：{}", zjxxDO.toString());
            }
        }, day, TimeUnit.DAYS);
    }

    @Override
    public List<BdcLfFjxxDO> listLfFiles(String ywid) {
        if(StringUtils.isBlank(ywid)) {
            throw new MissingArgumentException("未定义要查询附件关联的业务ID");
        }

        Example example = new Example(BdcLfFjxxDO.class);
        example.createCriteria().andEqualTo("ywid", ywid);
        example.setOrderByClause("WJSCSJ DESC");
        return entityMapper.selectByExampleNotNull(example);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteFiles(List<BdcLfFjxxDO> fjxxDOList) {
        int num = 0;
        for(BdcLfFjxxDO fjxxDO : fjxxDOList) {
            num += entityMapper.deleteByPrimaryKey(BdcLfFjxxDO.class, fjxxDO.getId());
        }
        return num;
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
