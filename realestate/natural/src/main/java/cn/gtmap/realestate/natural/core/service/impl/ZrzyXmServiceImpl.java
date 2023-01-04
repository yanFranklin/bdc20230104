package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgmbDo;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.natural.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyDbxxQO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyXmListQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.natural.core.mapper.ZrzyXmMapper;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import cn.gtmap.realestate.natural.core.service.ZrzySlbhService;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源项目服务
 */
@Service
public class ZrzyXmServiceImpl implements ZrzyXmService, ZrzyCommonService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyXmServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    ZrzyXmMapper zrzyXmMapper;

    @Autowired
    private Repository repository;

    @Autowired
    private ZrzySlbhService zrzySlbhService;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;

    @Autowired
    @Lazy
    UserManagerUtils userManagerUtils;

    @Autowired
    @Lazy
    WorkFlowUtils workFlowUtils;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理编号自增时间范围
     */
    @Value("${slbh.zzsjfw:DAY}")
    private String zzsjfw;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理编号自增序列号位数
     */
    @Value("${slbh.zzxlh:4}")
    private Integer zzxlh;
    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 受理编号前缀
     */
    @Value("${slbh.prebh:}")
    private String prebh;


    @Override
    public ZrzyXmDO queryZrzyXmByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<ZrzyXmDO> zrzyXmList = queryAllZrzyXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(zrzyXmList)) {
                return zrzyXmList.get(0);
            }
        }
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 自然资源项目信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取自然资源项目信息
     */
    @Override
    public List<ZrzyXmDO> queryAllZrzyXmByGzlslid(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(ZrzyXmDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            List<ZrzyXmDO> zrzyXmList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zrzyXmList)) {
                return zrzyXmList;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public ZrzyXmDO queryZrzyXmByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            return entityMapper.selectByPrimaryKey(ZrzyXmDO.class, xmid);
        }
        return null;

    }

    @Override
    public Page<ZrzyXmDTO> zrzyXmList(Pageable pageable, ZrzyXmListQO zrzyXmListQO) {
        return repository.selectPaging("listXmDtoByPage", zrzyXmListQO, pageable);
    }

    @Override
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String xmid, ZrzySlymYwxxDTO zrzySlymYwxxDTO) {
        if (zrzySlymYwxxDTO == null) {
            zrzySlymYwxxDTO = new ZrzySlymYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid) && zrzySlymYwxxDTO.getZrzyXm() == null) {
            ZrzyXmDO zrzyXmDO = entityMapper.selectByPrimaryKey(ZrzyXmDO.class, xmid);
            if (zrzyXmDO != null) {
                zrzySlymYwxxDTO.setZrzyXm(zrzyXmDO);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        String regionCode = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getCurrentUserName());
        ZrzyXmDO zrzyXmDO = new ZrzyXmDO();
        initDozerMapper.map(zrzyInitQO, zrzyXmDO);
        initDozerMapper.map(zrzyInitQO.getZrzyXtLcpzDO(), zrzyXmDO);
        if (zrzyInitQO.getJbzkDTO() != null) {
            initDozerMapper.map(zrzyInitQO.getJbzkDTO(), zrzyXmDO);
        } else if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            ZrzyXmDO yzrzyXmDO = queryZrzyXmByXmid(zrzyInitQO.getYxmid());
            if (yzrzyXmDO != null) {
                initDozerMapper.map(yzrzyXmDO, zrzyXmDO, "yxm");
            }
        }
        //生成受理编号
        zrzyXmDO.setSlbh(zrzySlbhService.generateSlbh(CommonConstantUtils.YWLX_SLBH, zzsjfw, zzxlh, prebh));
        if (Objects.nonNull(regionCode)) {
            zrzyXmDO.setQxdm(regionCode);
        }
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (userDto != null) {
            zrzyXmDO.setSlr(userDto.getAlias());
            zrzyXmDO.setSlrid(userDto.getId());
            if (CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                OrganizationDto organizationDto = userDto.getOrgRecordList().get(0);
                if (organizationDto != null) {
                    zrzyXmDO.setDjjg(organizationDto.getName());
                }

            }
        }
        zrzyXmDO.setSlsj(new Date());
        if (org.apache.commons.lang3.StringUtils.isBlank(zrzyXmDO.getGzldymc())) {
            ProcessDefData processDefData = workFlowUtils.getAllProcessDefDataByGzldyid(zrzyXmDO.getGzldyid());
            if (processDefData != null) {
                zrzyXmDO.setGzldymc(processDefData.getName());
            }
        }
        zrzyXmDO.setDjdymc(zrzyInitQO.getJbzkDTO().getMc());
        zrzyInitServiceDTO.setZrzyXmDO(zrzyXmDO);

        return zrzyInitServiceDTO;

    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyXmDOList())) {
            zrzyInitResultDTO.getZrzyXmDOList().forEach(zrzyXmDO -> entityMapper.insertSelective(zrzyXmDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyXmDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }


    @Override
    public void updateZrzyXmDbxx(ZrzyDbxxQO zrzyDbxxQo) {
        if (StringUtils.isBlank(zrzyDbxxQo.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        // 不生成权利的项目，也要更新项目的登簿信息（登簿后为历史(此时当前项目的qszt为现势)，退回后还原为临时）
        if (CommonConstantUtils.QSZT_VALID.equals(zrzyDbxxQo.getQszt())) {
            //zrzyDbxxQo.setQszt(CommonConstantUtils.QSZT_HISTORY);
        }
        // 该参数是为了过滤掉生成权利信息的数据，只对不生产权利的项目进行操作（上面的代码已经对当前流程的所有代码做了更新操作）
        zrzyDbxxQo.setSfscql(CommonConstantUtils.SF_F_DM);
        zrzyXmMapper.updateZrzyXmDbxxAndQsztByGzlslid(zrzyDbxxQo);

        LOGGER.info("已完成流程{}：更新当前项目和当前权利的权属状态和登簿信息", zrzyDbxxQo.getGzlslid());
    }


    @Override
    public void updateYxmDbxx(ZrzyDbxxQO zrzyDbxxQo) {
        if (StringUtils.isBlank(zrzyDbxxQo.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        // 更新原项目的权属状态
        zrzyXmMapper.updateYxmQsztByGzlslid(zrzyDbxxQo);
        LOGGER.info("已完成流程{}：更新原项目和原权利的权属状态和注销登簿信息", zrzyDbxxQo.getGzlslid());
    }

    @Override
    public List<ZrzyGgmbDo> listGgmbByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            return null;
        }
        // 更新原项目的权属状态
        return zrzyXmMapper.listGgmbByXmid(xmid);
    }

    @Override
    public void addGgmb(String xmid, String mbxxid, String mbnr) {
        zrzyXmMapper.insertGgmb(mbxxid, xmid, mbnr);
    }

    @Override
    public void updateGgmbByXmid(String xmid, String mbnr) {
        zrzyXmMapper.updateGgmbByXmid(xmid, mbnr);
    }
}
