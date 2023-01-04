package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.gtc.clients.OrganizationManagerClient;
import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.supervise.core.domain.*;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjCkbjDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjCqbjDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjFgzsjbjDTO;
import cn.gtmap.realestate.supervise.core.qo.*;
import cn.gtmap.realestate.supervise.service.LfYcbjyjService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/22
 * @description 廉防1：异常办件预警服务类
 */
@Service
public class LfYcbjyjServiceImpl implements LfYcbjyjService {
    private static Logger logger = LoggerFactory.getLogger(LfYcbjyjServiceImpl.class);

    @Autowired
    private Repo repo;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcXmFeignService xmFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private OrganizationManagerClient organizationManagerClient;

    @Autowired
    private UserManagerClient userManagerClient;

    /**
     * 超快办件，排除的工作流定义id
     */
    @Value("${ckbj.pcGzldyid:}")
    private String pcGzldyid;

    /**
     * 超期办件，排除的工作流定义id
     */
    @Value("${cqbj.pcGzldyid:}")
    private String cqPcGzldyid;

    /**
     * 分页查询超期办件数据
     *
     * @param pageable 分页参数
     * @param cqbjQO   查询参数
     * @return {Page} 超期办件信息
     */
    @Override
    public Page<BdcLfYcbjyjCqbjDTO> listCqbjData(Pageable pageable, LfCqbjQO cqbjQO) {
        cqbjQO.setPcGzldyid(cqPcGzldyid);
        // 查部门
        if (cqbjQO != null && StringUtils.isNotBlank(cqbjQO.getBmid()) && StringUtils.isBlank(cqbjQO.getRymc())) {
            String rymc = organizeBmParam(cqbjQO.getBmid());
            if (StringUtils.isBlank(rymc)){
                return new PageImpl(new ArrayList(), pageable, 0);
            }else{
                cqbjQO.setRymc(rymc);
            }
        }
        Page<BdcLfYcbjyjCqbjDTO> cqbjDOPage = repo.selectPaging("listCqbjByPageOrder", cqbjQO, pageable);
        if (CollectionUtils.isNotEmpty(cqbjDOPage.getContent())) {
            // 部门名称
            for (BdcLfYcbjyjCqbjDTO cqbjDTO : cqbjDOPage.getContent()) {
                cqbjDTO.setBmmc(queryBmByAlias(cqbjDTO.getSlr()));
            }
        }
        return cqbjDOPage;
    }

    /**
     * 根据部门id查人员名称
     *
     * @param bmidParam 部门id
     * @return 人员名称
     */
    private String organizeBmParam(String bmidParam) {
        List<String> bmids = Arrays.asList(bmidParam.split(","));
        List<String> rymcList = new ArrayList<>();
        String rymc = "";
        if (CollectionUtils.isNotEmpty(bmids)) {
            for (String bmid : bmids) {
                List<UserDto> users = organizationManagerClient.listUsersByOrg(bmid);
                List<String> aliasList = users.stream().map(UserDto::getAlias).collect(Collectors.toList());
                rymcList.addAll(aliasList);
            }
        }
        if (CollectionUtils.isNotEmpty(rymcList)) {
            rymc = rymcList.stream().collect(Collectors.joining(","));

        }
        return rymc;
    }

    /**
     * 根据人员名称查询部门名称
     *
     * @param alias 人员名称
     * @return 部门名称
     */
    private String queryBmByAlias(String alias) {
        String bmmc = "";
        if (StringUtils.isNotBlank(alias)) {
            List<UserDto> userDtos = userManagerClient.allUsers(null, alias, 1);
            if (CollectionUtils.isNotEmpty(userDtos)) {
                List<OrganizationDto> organizationDtoList = userManagerUtils.getOrgListByUserName(userDtos.get(0).getUsername());
                if (CollectionUtils.isNotEmpty(organizationDtoList)) {
                    bmmc = organizationDtoList.get(0).getName();
                }
            }
        }
        return bmmc;
    }

    /**
     * 分页查询超快办件数据
     * @param pageable 分页参数
     * @param ckbjQO   查询参数
     * @return {Page} 超快办件信息
     */
    @Override
    public Page<BdcLfYcbjyjCkbjDTO> listCkbjData(Pageable pageable, LfCkbjQO ckbjQO) {
        ckbjQO.setPcGzldyid(pcGzldyid);
        // 查部门
        if (ckbjQO != null && StringUtils.isNotBlank(ckbjQO.getBmid()) && StringUtils.isBlank(ckbjQO.getRymc())) {
            String rymc = organizeBmParam(ckbjQO.getBmid());
            if (StringUtils.isBlank(rymc)){
                return new PageImpl(new ArrayList(), pageable, 0);
            }else{
                ckbjQO.setRymc(rymc);
            }
        }
        Page<BdcLfYcbjyjCkbjDTO> ckbjDOPage = repo.selectPaging("listCkbjByPageOrder", ckbjQO, pageable);
        if (CollectionUtils.isNotEmpty(ckbjDOPage.getContent())) {
            // 部门名称
            for (BdcLfYcbjyjCkbjDTO ckbjDTO : ckbjDOPage.getContent()) {
                ckbjDTO.setBmmc(queryBmByAlias(ckbjDTO.getSlr()));
            }
        }
        return ckbjDOPage;
    }

    /**
     * 分页查询非工作时间办件数据
     *
     * @param pageable  分页参数
     * @param fgzsjbjQO 查询参数
     * @return {Page} 非工作时间办件信息
     */
    @Override
    public Page<BdcLfYcbjyjFgzsjbjDTO> listFgsjbjData(Pageable pageable, LfFgzsjbjQO fgzsjbjQO) {
        // 查部门
        if (fgzsjbjQO != null && StringUtils.isNotBlank(fgzsjbjQO.getBmid()) && StringUtils.isBlank(fgzsjbjQO.getRymc())) {
            String rymc = organizeBmParam(fgzsjbjQO.getBmid());
            if (StringUtils.isBlank(rymc)){
                return new PageImpl(new ArrayList(), pageable, 0);
            }else{
                fgzsjbjQO.setRymc(rymc);
            }
        }
        Page<BdcLfYcbjyjFgzsjbjDTO> fgzsjbjDOPage = repo.selectPaging("listFgsjbjByPageOrder", fgzsjbjQO, pageable);
        if (CollectionUtils.isNotEmpty(fgzsjbjDOPage.getContent())) {
            // 部门名称
            for (BdcLfYcbjyjFgzsjbjDTO fgzsjbjDTO : fgzsjbjDOPage.getContent()) {
                fgzsjbjDTO.setBmmc(queryBmByAlias(fgzsjbjDTO.getSlr()));
            }
        }
        return fgzsjbjDOPage;
    }

    /**
     * 查询异常办件审核信息
     * @param type 异常办件类型
     * @param id   主键ID
     * @return 异常信息
     */
    @Override
    public Object queryYcbjShxx(String type, String id) {
        if(StringUtils.isBlank(type) || StringUtils.isBlank(id)) {
            throw new MissingArgumentException("未定义异常信息查询参数");
        }

        Class objClass = null;
        if("cqbj".equals(type)) {
            objClass = BdcLfYcbjyjCqbjDO.class;
        } else if("ckbj".equals(type)) {
            objClass = BdcLfYcbjyjCkbjDO.class;
        } else if("fgzsjbj".equals(type)) {
            objClass = BdcLfYcbjyjFgzsjbjDO.class;
        } else {
            throw new MissingArgumentException("未知异常类型参数");
        }

        return entityMapper.selectByPrimaryKey(objClass, id);
    }

    /**
     * 保存异常办件审核信息
     * @param type 异常办件类型
     * @param data 审核信息数据
     * @return 异常信息ID
     */
    @Override
    public String saveYcbjShxx(String type, Map<String, Object> data) {
        if(StringUtils.isBlank(type) || MapUtils.isEmpty(data)) {
            throw new MissingArgumentException("未定义要保存的审核信息");
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            data.put("shry", userDto.getAlias());
            data.put("shrid", userDto.getId());
        }
        data.put("shsj", new Date());
        data.put("shzt", 1);


        Class objClass = null;
        if("cqbj".equals(type)) {
            objClass = BdcLfYcbjyjCqbjDO.class;
        } else if("ckbj".equals(type)) {
            objClass = BdcLfYcbjyjCkbjDO.class;
        } else if("fgzsjbj".equals(type)) {
            objClass = BdcLfYcbjyjFgzsjbjDO.class;
        } else {
            throw new MissingArgumentException("未知异常类型参数");
        }

        entityMapper.updateByPrimaryKeySelective(JSON.parseObject(JSON.toJSONString(data), objClass));
        return String.valueOf(data.get("id"));
    }

    /**
     * 获取异常情况申报信息
     * @param processInsId 工作流实例ID
     * @param id           申报信息ID
     * @return {BdcLfYcqksbDO} 申报信息
     */
    @Override
    public BdcLfYcqksbDO getYcqksb(String processInsId, String id) {
        if(StringUtils.isBlank(processInsId) && StringUtils.isBlank(id)) {
            throw new MissingArgumentException("未定义查询申报信息所需参数！");
        }

        if(StringUtils.isNotBlank(id) && !"null".equals(id)) {
            return entityMapper.selectByPrimaryKey(BdcLfYcqksbDO.class, id);
        } else {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setGzlslid(processInsId);
            List<BdcXmDO> xmDOList = xmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(xmDOList) || null == xmDOList.get(0)) {
                throw new AppException("未查询到项目信息");
            }
            Example example = new Example(BdcLfYcqksbDO.class);
            example.createCriteria().andEqualTo("gzlslid", processInsId);
            example.setOrderByClause("tbsj asc");
            List<BdcLfYcqksbDO> result =entityMapper.selectByExampleNotNull(example);
            if (CollectionUtils.isNotEmpty(result)){
                // 如果有申报数据
                return result.get(0);
            }
            // 没有申报数据
            BdcLfYcqksbDO bdcLfYcqksbDO = new BdcLfYcqksbDO();
            bdcLfYcqksbDO.setGzlslid(processInsId);
            bdcLfYcqksbDO.setSlbh(xmDOList.get(0).getSlbh());
            bdcLfYcqksbDO.setSlsj(xmDOList.get(0).getSlsj());
            bdcLfYcqksbDO.setTbsj(new Date());
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(null != userDto) {
                bdcLfYcqksbDO.setTbr(userDto.getAlias());
            }
            return bdcLfYcqksbDO;
        }
    }

    /**
     * 保存申报信息
     * @param sbxx 申报信息
     * @return {String} 申报信息ID
     */
    @Override
    public String saveYcqksb(BdcLfYcqksbDO sbxx) {
        if(StringUtils.isBlank(sbxx.getGzlslid()) && StringUtils.isBlank(sbxx.getId())) {
            throw new MissingArgumentException("未定义要保存的申报信息数据！");
        }
        // 新增一条数据
        if(StringUtils.isBlank(sbxx.getId())) {
            sbxx.setId(UUIDGenerator.generate16());
            UserDto userDto = userManagerUtils.getCurrentUser();
            if(null != userDto) {
                sbxx.setTbr(userDto.getAlias());
                sbxx.setTbrid(userDto.getId());
                sbxx.setTbrzh(userDto.getUsername());
            }
        }
        entityMapper.saveOrUpdate(sbxx, sbxx.getId());
        return sbxx.getId();
    }

    /**
     * 异常申报信息查询（分页台账）
     * @param pageable   分页参数
     * @param lfYcqksbQO 业务查询参数
     * @return 异常申报信息
     */
    @Override
    public Page<BdcLfYcqksbDO> listYcqksbTableData(Pageable pageable, LfYcqksbQO lfYcqksbQO) {
        return repo.selectPaging("listYcqksbByPageOrder", lfYcqksbQO, pageable);
    }

    /**
     * 分页查询异常原因数据
     * @param pageable 分页参数
     * @param ycyyQO   查询参数
     * @return {Page} 异常原因数据
     */
    @Override
    public Page<BdcLfYcbjyjYcyyDO> listYcyyData(Pageable pageable, LfYcyyQO ycyyQO) {
        return repo.selectPaging("listYcyyByPageOrder", ycyyQO, pageable);
    }

    /**
     * 查询指定异常原因记录
     * @param id 异常原因ID
     * @return 异常原因
     */
    @Override
    public BdcLfYcbjyjYcyyDO getYcxxById(String id) {
        if(StringUtils.isBlank(id)) {
            throw new MissingArgumentException("未定义要查询异常原因信息ID");
        }
        return entityMapper.selectByPrimaryKey(BdcLfYcbjyjYcyyDO.class, id);
    }

    /**
     * 保存异常原因
     * @param ycyyDO 异常原因
     * @return {String} 异常原因ID
     */
    @Override
    public String saveYcxx(BdcLfYcbjyjYcyyDO ycyyDO) {
        if(!CheckParameter.checkAnyParameter(ycyyDO)) {
            throw new MissingArgumentException("未定义要保存的异常原因数据！");
        }

        if(StringUtils.isBlank(ycyyDO.getId())) {
            ycyyDO.setId(UUIDGenerator.generate16());
        }

        UserDto userDto = userManagerUtils.getCurrentUser();
        if(null != userDto) {
            ycyyDO.setLrr(userDto.getAlias());
            ycyyDO.setLrrid(userDto.getId());
        }
        ycyyDO.setLrsj(new Date());

        entityMapper.saveOrUpdate(ycyyDO, ycyyDO.getId());
        logger.debug("保存或更新异常原因信息一条，对应ID：{}", ycyyDO.getId());
        return ycyyDO.getId();
    }

    /**
     * 删除异常原因
     *
     * @param ycyyDOList 异常原因
     * @return {Integer} 删除记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteYcxxs(List<BdcLfYcbjyjYcyyDO> ycyyDOList) {
        if(CollectionUtils.isEmpty(ycyyDOList)) {
            throw new MissingArgumentException("未定义要删除的异常原因数据！");
        }

        int num = 0;
        for(BdcLfYcbjyjYcyyDO ycyyDO : ycyyDOList) {
            num += entityMapper.deleteByPrimaryKey(BdcLfYcbjyjYcyyDO.class, ycyyDO.getId());
        }
        logger.info("删除异常原因信息：{}", ycyyDOList);
        return num;
    }

    /**
     * 分页查询项目数据
     *
     * @param pageable 分页参数
     * @param bdcXmQO  查询参数
     * @return {Page} 异常原因数据
     */
    @Override
    public Page<BdcXmDO> listXmxxData(Pageable pageable, BdcXmQO bdcXmQO) {
        return repo.selectPaging("listXmxxByPageOrder", bdcXmQO, pageable);
    }
}
