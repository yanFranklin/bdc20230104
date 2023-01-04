package cn.gtmap.realestate.inquiry.service.bengbu.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcDwgzglDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.bengbu.BdcDwgzglService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 16:28 2020/7/27
 * @description 单位公章管理实现
 */
@Service
public class BdcDwgzglServiceImpl implements BdcDwgzglService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDwgzglServiceImpl.class);
    /**
     * 日期格式
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分");

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repo repo;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * 分页查询单位公章信息
     *
     * @param pageable
     * @param bdcDwgzglDO
     * @return BdcCfxxDTO                                                             ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Page<BdcDwgzglDO> listBdcDwgzByPage(Pageable pageable, BdcDwgzglDO bdcDwgzglDO) {

        return repo.selectPaging("listDwgzByPageOrder", bdcDwgzglDO, pageable);
    }

    /**
     * @param bdcDwgzglDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 插入新的单位公章信息
     */
    @Override
    public BdcDwgzglDO insertBdcDwgz(BdcDwgzglDO bdcDwgzglDO) {
        if (bdcDwgzglDO != null) {
            if (StringUtils.isBlank(bdcDwgzglDO.getId())) {
                bdcDwgzglDO.setId(UUIDGenerator.generate());
            }
            UserDto userDto = userManagerUtils.getCurrentUser();
            bdcDwgzglDO.setXgry(userDto.getAlias());
            bdcDwgzglDO.setXgsj(new Date());
            //新增时，创建文件夹用于保存附件
            StorageDto storageDto = storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, bdcDwgzglDO.getId(), bdcDwgzglDO.getDwmc(), "");
            if (storageDto != null && StringUtils.isNotBlank(storageDto.getId())) {
                bdcDwgzglDO.setFjcl(storageDto.getId());
            }
            entityMapper.insertSelective(bdcDwgzglDO);
        }
        return bdcDwgzglDO;
    }

    /**
     * 更新单位公章信息
     *
     * @param bdcDwgzglDO@return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 19:52 2020/7/27
     */
    @Override
    public int updateBdcDwgz(BdcDwgzglDO bdcDwgzglDO) {
        int count = 0;
        UserDto userDto = userManagerUtils.getCurrentUser();
        bdcDwgzglDO.setXgry(userDto.getAlias());
        bdcDwgzglDO.setXgsj(new Date());
        if (StringUtils.isNotBlank(bdcDwgzglDO.getId())) {
            count = entityMapper.updateByPrimaryKeySelective(bdcDwgzglDO);
        }
        return count;
    }
}
