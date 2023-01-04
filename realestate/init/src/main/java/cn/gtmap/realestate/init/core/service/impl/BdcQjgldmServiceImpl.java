package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcJsQxdmGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.dto.BdcJsPzDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.init.core.mapper.BdcQjgldmMapper;
import cn.gtmap.realestate.init.core.service.BdcQjgldmService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description
 */
@Service
public class BdcQjgldmServiceImpl implements BdcQjgldmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcQjgldmServiceImpl.class);

    @Autowired
    private BdcQjgldmMapper bdcQjgldmMapper;

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    private Repo repo;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 缓存角色区县代码配置表
     */
    private Map<String,List<String>> jsQxdmMap = new HashMap<>();

    /**
     * 启动初始化处理
     */
    @PostConstruct
    public void init() {
        // 查询权籍管理代码并缓存
        List<BdcJsQxdmGxDO> bdcJsQxdmGxDOList= bdcQjgldmMapper.listJsQxdmGx();
        if(CollectionUtils.isNotEmpty(bdcJsQxdmGxDOList)){
            for(BdcJsQxdmGxDO bdcJsQxdmGxDO:bdcJsQxdmGxDOList){
                if(StringUtils.isNoneBlank(bdcJsQxdmGxDO.getRolecode(),bdcJsQxdmGxDO.getQxdm())) {
                    List<String> qxdmList = Arrays.asList(bdcJsQxdmGxDO.getQxdm().split(CommonConstantUtils.ZF_YW_DH));
                    jsQxdmMap.put(bdcJsQxdmGxDO.getRolecode(), qxdmList);
                }
            }
        }
    }

    @Override
    public List<String> listQjgldmByRoleCode(String roleCode){
        if(StringUtils.isNotBlank(roleCode)){
            return bdcQjgldmMapper.listQjgldmByRoleCode(roleCode);
        }
        return Collections.emptyList();

    }

    @Override
    public List<BdcZdQjgldmDO> listDistinctQjgldmByUserId(String userId){
        if(StringUtils.isNotBlank(userId)){
            List<RoleDto> roleDtoList =userManagerUtils.getRolesByUserId(userId);
            if(CollectionUtils.isNotEmpty(roleDtoList)){
                //获取角色编码
                List<String> roleCodeList = roleDtoList.stream().filter(Objects::nonNull).
                        filter(roleDto -> StringUtils.isNotBlank(roleDto.getName()))
                        .collect(Collectors.mapping(roleDto -> roleDto.getName(), Collectors.toList()));
                if(CollectionUtils.isNotEmpty(roleCodeList)) {
                    return bdcQjgldmMapper.listDistinctQjgldmByRoleCodeList(roleCodeList);
                }


            }

        }

        return Collections.emptyList();

    }

    @Override
    public BdcJsPzDTO queryJsPzDTOByUserId(UserDto userDto){
        BdcJsPzDTO bdcJsPzDTO =new BdcJsPzDTO();
        if(StringUtils.isNotBlank(userDto.getId())){
            List<RoleDto> roleDtoList =userManagerUtils.getRolesByUserId(userDto.getId());
            List<BdcZdQjgldmDO> qjgldmList =new ArrayList<>();
            if(CollectionUtils.isNotEmpty(roleDtoList)){
                //获取角色编码
                List<String> roleCodeList = roleDtoList.stream().filter(Objects::nonNull).
                        filter(roleDto -> StringUtils.isNotBlank(roleDto.getName()))
                        .collect(Collectors.mapping(roleDto -> roleDto.getName(), Collectors.toList()));
                if(CollectionUtils.isNotEmpty(roleCodeList)) {
                    qjgldmList =bdcQjgldmMapper.listDistinctQjgldmByRoleCodeList(roleCodeList);

                }
            }
            if(CollectionUtils.isEmpty(qjgldmList)){
                List<String> qjgldmFilter= Container.getBean(BdcConfigUtils.class).qxdmFilterWithUser(userDto);

                if(CollectionUtils.isNotEmpty(qjgldmFilter)){
                    qjgldmList =bdcQjgldmMapper.listDistinctQjgldmByDm(qjgldmFilter);
                }
            }
            if(CollectionUtils.isNotEmpty(qjgldmList)){
                bdcJsPzDTO.setQjgldmList(qjgldmList);
            }

        }

        return bdcJsPzDTO;


    }

    /**
     * 获取用户角色关联最多的那个权籍管理代码
     * @param userId 用户ID
     * @return {String} 权籍管理代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public String listMostQjgldmByUserId(String userId) {
        if(StringUtils.isBlank(userId)) {
            throw new MissingArgumentException("未定义查询用户");
        }

        List<RoleDto> roleList = userManagerUtils.getRolesByUserId(userId);
        if(CollectionUtils.isNotEmpty(roleList)){
            List<String> roleCodeList = roleList.stream().filter(Objects::nonNull).
                    filter(roleDto -> StringUtils.isNotBlank(roleDto.getName())).map(RoleDto::getName).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(roleCodeList)) {
                return bdcQjgldmMapper.listMostQjgldmByUserId(roleCodeList);
            }
        }
        return null;
    }

    @Override
    public List<String> listQxdmByRoleCodeList(List<String> roleCodeList){
        if(CollectionUtils.isNotEmpty(roleCodeList)) {
            Set<String> qxdmSet = new HashSet<>();
            for(String roleCode:roleCodeList){
                List<String> qxdmList =jsQxdmMap.get(roleCode);
                if(CollectionUtils.isNotEmpty(qxdmList)) {
                    qxdmSet.addAll(jsQxdmMap.get(roleCode));
                }
            }
            return new ArrayList<>(qxdmSet);
        }
        return null;

    }

    @Override
    public Page<BdcJsQxdmGxDO> listBdcJsQxdmGxByPage(Pageable pageable, BdcJsQxdmGxDO bdcJsQxdmGxDO){
        return repo.selectPaging("listBdcJsQxdmGxByPage", bdcJsQxdmGxDO, pageable);
    }

    @Override
    public void deleteJsQxdmPzByRoleCode(String rolecode){
        if(StringUtils.isNotBlank(rolecode)){
            Example example =new Example(BdcJsQxdmGxDO.class);
            example.createCriteria().andEqualTo("rolecode",rolecode);
            entityMapper.deleteByExampleNotNull(example);
        }
    }

    @Override
    public void saveJsQxdmPz(BdcJsQxdmGxDO bdcJsQxdmGxDO){
        if(bdcJsQxdmGxDO != null &&StringUtils.isNotBlank(bdcJsQxdmGxDO.getQxdm()) &&StringUtils.isNotBlank(bdcJsQxdmGxDO.getRolecode())){
            if(StringUtils.isNotBlank(bdcJsQxdmGxDO.getGxid())){
                entityMapper.saveOrUpdate(bdcJsQxdmGxDO,bdcJsQxdmGxDO.getGxid());
            }else{
                BdcJsQxdmGxDO jsQxdmGxDO =queryBdcJsQxdmGxByRoleCode(bdcJsQxdmGxDO.getRolecode());
                if(jsQxdmGxDO != null){
                    jsQxdmGxDO.setQxdm(bdcJsQxdmGxDO.getQxdm());
                    entityMapper.updateByPrimaryKeySelective(jsQxdmGxDO);
                }else{
                    bdcJsQxdmGxDO.setGxid(UUIDGenerator.generate16());
                    entityMapper.insertSelective(bdcJsQxdmGxDO);
                }
            }
        }

    }

    public BdcJsQxdmGxDO queryBdcJsQxdmGxByRoleCode(String rolecode){
        if(StringUtils.isNotBlank(rolecode)){
            Example example =new Example(BdcJsQxdmGxDO.class);
            example.createCriteria().andEqualTo("rolecode",rolecode);
            List<BdcJsQxdmGxDO> bdcJsQxdmGxDOList =entityMapper.selectByExampleNotNull(example);
            if(CollectionUtils.isNotEmpty(bdcJsQxdmGxDOList)){
                return bdcJsQxdmGxDOList.get(0);
            }
        }
        return null;

    }

    @Override
    public void refreshBdcJsQxdmPz(String rolecode){
        if(StringUtils.isNotBlank(rolecode)){
            BdcJsQxdmGxDO bdcJsQxdmGxDO =queryBdcJsQxdmGxByRoleCode(rolecode);
            if(bdcJsQxdmGxDO != null &&StringUtils.isNotBlank(bdcJsQxdmGxDO.getQxdm())){
                List<String> qxdmList = Arrays.asList(bdcJsQxdmGxDO.getQxdm().split(CommonConstantUtils.ZF_YW_DH));
                jsQxdmMap.put(rolecode,qxdmList);
            }else{
                jsQxdmMap.remove(rolecode);
            }

        }
    }
}
