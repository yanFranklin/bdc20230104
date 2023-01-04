package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyDbxxQO;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.service.ZrzyDbxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/2
 * @description 登簿信息服务实现类
 */
@Service
public class ZrzyDbxxServiceImpl implements ZrzyDbxxService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyDbxxServiceImpl.class);


    @Autowired
    private EntityService entityService;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    ZrzyXmService zrzyXmService;

    /**
     * @param gzlslid         工作流实例ID
     * @param currentUserName 当前用户信息
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @Override
    public void updateZrzyDbxx(String gzlslid, String currentUserName) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少工作流实例 ID!");
        }


        ZrzyDbxxQO zrzyDbxxQo = new ZrzyDbxxQO();
        try {
            // 流程转发中没有用户认证信息，所以用此方法获取
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            if (null != userDto) {
                zrzyDbxxQo.setDbr(userDto.getAlias());
                if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList()) &&
                        StringUtils.isNotBlank(userDto.getOrgRecordList().get(0).getName())) {
                    zrzyDbxxQo.setDjjg(userDto.getOrgRecordList().get(0).getName());
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}:审核登簿子系统——更新登簿信息以及权属状态报错：调用大云用户信息失败{}", gzlslid, e.getMessage());
        }

        zrzyDbxxQo.setDjsj(new Date());
        zrzyDbxxQo.setGzlslid(gzlslid);

        zrzyDbxxQo.setQszt(CommonConstantUtils.QSZT_VALID);
        zrzyDbxxQo.setZxQszt(CommonConstantUtils.QSZT_HISTORY);
        LOGGER.info("{} ：登簿信息：{}", gzlslid, zrzyDbxxQo);
        updateZrzyDbxxAndQszt(zrzyDbxxQo);
    }

    /**
     * @param xmid 项目ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新项目案件状态为2已完成状态，并更新项目结束时间
     */
    @Override
    public void changeAjzt(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            return;
        }
        List<ZrzyXmDO> zrzyXmDOList = zrzyXmService.queryAllZrzyXmByGzlslid(processInsId);
        if(CollectionUtils.isNotEmpty(zrzyXmDOList)) {
            ZrzyXmDO zrzyXmDO = new ZrzyXmDO();
            for (ZrzyXmDO xmDO : zrzyXmDOList) {
                zrzyXmDO.setXmid(xmDO.getXmid());
                zrzyXmDO.setAjzt(CommonConstantUtils.AJZT_YB_DM.shortValue());
                /**
                 * 登簿时会延迟 60s，导致查封等登簿办结同时执行的流程 jssj 比 djsj 早，办结统一延迟 60s 确保 jssj 在 djsj 之后
                 */
                zrzyXmDO.setJssj(new Date(System.currentTimeMillis()));

                entityMapper.updateByPrimaryKeySelective(zrzyXmDO);
                LOGGER.info("更新项目{}的案件状态为已完成", xmDO.getXmid());
            }
        }
    }


    /**
     * @param dbxxQO 登簿信息QO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    public void updateZrzyDbxxAndQszt(ZrzyDbxxQO zrzyDbxxQo) {
        String gzlslid = zrzyDbxxQo.getGzlslid();
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺少工作流实例 ID!");
        }

        Date startTime = new Date();
        LOGGER.info("登簿开始：{}流程{}项目登簿更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态", "ZrzyDbxxServiceImpl", gzlslid);

        zrzyXmService.updateZrzyXmDbxx(zrzyDbxxQo);
        //原项目
        zrzyXmService.updateYxmDbxx(zrzyDbxxQo);

        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.warn("{}已完成流程{}项目登簿更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态，所耗时间：{}", "ZrzyDbxxServiceImpl" +
                "", gzlslid, time);
    }


}
