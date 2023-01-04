package cn.gtmap.realestate.supervise.service.impl;

import cn.gtmap.gtc.clients.RoleManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.supervise.core.dto.LfCxycDTO;
import cn.gtmap.realestate.supervise.core.mapper.LfCxycjgMapper;
import cn.gtmap.realestate.supervise.core.qo.LfCxycQO;
import cn.gtmap.realestate.supervise.core.qo.LfLogQO;
import cn.gtmap.realestate.supervise.core.qo.LfRoleQO;
import cn.gtmap.realestate.supervise.service.LfCxycjgService;
import cn.gtmap.realestate.supervise.service.LfUserService;
import cn.gtmap.realestate.supervise.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 查询异常监管逻辑
 */
@Service
public class LfCxycjgServiceImpl implements LfCxycjgService {
    private static Logger logger = LoggerFactory.getLogger(LfCxycjgServiceImpl.class);

    @Autowired
    private LfUserService userService;

    @Autowired
    private RoleManagerClient roleManagerClient;

    @Autowired
    private LfCxycjgMapper cxycjgMapper;

    @Autowired
    private Repo repo;

    @Autowired
    UserManagerUtils userManagerUtils;


    /**
     * 统计岗位查询异常（分页台账）
     * @param pageable 分页参数
     * @param lfCxycQO 业务查询参数
     * @return {Page} 异常查询统计信息
     */
    @Override
    public Page<LfCxycDTO> listGwcxycTableData(Pageable pageable, LfCxycQO lfCxycQO) {
        List<LfCxycDTO> result = this.listGwcxycData(lfCxycQO);
        if(CollectionUtils.isEmpty(result)) {
            return new PageImpl(Collections.emptyList(), pageable, 0);
        }

        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        if(startIndex > result.size()) {
            return new PageImpl(Collections.emptyList(), pageable, 0);
        }

        int endIndex = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        List<LfCxycDTO> pageData;
        if(endIndex > result.size()) {
            pageData = result.subList(startIndex, result.size());
        } else {
            pageData = result.subList(startIndex, endIndex);
        }
        return new PageImpl(pageData, pageable, result.size());
    }

    /**
     * 统计岗位查询异常（柱状图，封装展示数据）
     * @param lfCxycQO 业务查询参数
     * @return {List} 异常查询统计信息
     */
    @Override
    public List<Map> listGwcxycChartData(LfCxycQO lfCxycQO) {
        List<LfCxycDTO> queryList = this.listGwcxycData(lfCxycQO);
        if(CollectionUtils.isEmpty(queryList)) {
            return Collections.emptyList();
        }

        List<String> userNames = queryList.stream().map(LfCxycDTO::getYhm).distinct().collect(Collectors.toList());
        List<String> roleNames = queryList.stream().map(LfCxycDTO::getRyjs).distinct().collect(Collectors.toList());
        // 每个角色对应异常查询用户分组
        Map<String, List<LfCxycDTO>> roleUsersMap = queryList.stream().collect(Collectors.groupingBy(LfCxycDTO::getRyjs));

        /**
         *  封装Echarts柱状图展示数据，实例格式数据例如：
         *  {'角色': '角色1', '人员1': 43.3, '人员2': 85.8},
         *  {'角色': '角色2', '人员1': 83.1, '人员2': 73.4},
         *  {'角色': '角色3', '人员1': 86.4, '人员2': 65.2}
         */
        List<Map> chartShowDataList = new ArrayList<>();
        for(String roleName : roleNames) {
            Map<String, LfCxycDTO> userQueryMap = this.generateUserQueryMap(roleUsersMap, roleName);

            Map<String, Object> chartShowData = new LinkedHashMap<>();
            chartShowData.put("角色", roleName);

            // 每个角色下人员查询次数数据
            for(String userName : userNames) {
                if(null == userQueryMap.get(userName)) {
                    chartShowData.put(userName, 0);
                } else {
                    chartShowData.put(userName, userQueryMap.get(userName).getCxcs());
                }
            }
            chartShowDataList.add(chartShowData);
        }
        return chartShowDataList;
    }

    /**
     * 统计岗位查询异常数据
     * @param lfCxycQO 业务查询参数
     * @return {List} 异常查询统计信息
     */
    @Override
    public List<LfCxycDTO> listGwcxycData(LfCxycQO lfCxycQO) {
        // 获取要统计的角色
        LfRoleQO roleQO = new LfRoleQO();
        if(null != lfCxycQO && StringUtils.isNotBlank(lfCxycQO.getRyjs())) {
            roleQO.setRoleIds(Collections.singletonList(lfCxycQO.getRyjs()));
        }

        List<RoleDto> roleDtoList = userService.listAllRoles(roleQO);
        if(CollectionUtils.isEmpty(roleDtoList)) {
            logger.info("岗位查询异常统计未查询到角色，统计中止");
            return Collections.emptyList();
        }

        if(null == lfCxycQO) lfCxycQO = new LfCxycQO();
        // 默认查询异常标准为平均查询次数的两倍
        Integer ycbz = null == lfCxycQO.getYcbz() ? Constants.GWCXYCBZ_2 : lfCxycQO.getYcbz();

        List<LfCxycDTO> queryDataList = new ArrayList<>();
        //  分别统计每个角色下查询异常人员
        for(RoleDto roleDto : roleDtoList) {
            List<UserDto> userDtoList = roleManagerClient.listEnableUsersByRoleId(roleDto.getId());
            if(CollectionUtils.isEmpty(userDtoList)) {
                continue;
            }

            // 查询每个角色平均查询次数
            lfCxycQO.setYhids(userDtoList.stream().map(UserDto::getId).collect(Collectors.toList()));
            double averageQueryNumber = cxycjgMapper.countRoleAverageQueryNumber(lfCxycQO);
            logger.info("查询异常监管：角色[{}]下用户平均查询次数：{}次", roleDto.getAlias(), averageQueryNumber);

            // 统计每个用户的查询次数
            List<LfCxycDTO> userQueryList = cxycjgMapper.countUserQueryNumber(lfCxycQO);
            if(CollectionUtils.isEmpty(userQueryList)) {
                continue;
            }

            for(LfCxycDTO userQuery : userQueryList) {
                // 筛选查询次数超过平均次数指定标准的人
                if(BigDecimal.valueOf(userQuery.getCxcs()).compareTo(BigDecimal.valueOf(averageQueryNumber * ycbz)) >= 0) {
                    userQuery.setRyjs(roleDto.getAlias());
                    UserDto userDto = userManagerUtils.getUserByUserid(userQuery.getYhid());
                    if (userDto != null){
                        userQuery.setZw(userDto.getTitle());
                    }
                    queryDataList.add(userQuery);
                    logger.info("查询异常监管：角色[{}]下用户{}查询次数{}超出平均查询次数异常标准{}倍，纳入异常范围", roleDto.getAlias(), userQuery.getYhm(), userQuery.getCxcs(), ycbz);
                }
            }
        }
        return queryDataList;
    }

    /**
     * 查询指定用户查询操作日志记录
     * @param pageable 分页参数
     * @param lfLogQO  查询参数
     * @return {Page} 用户查询日志
     */
    @Override
    public Page<BdcXtCxLogDO> listUserQueryLog(Pageable pageable, LfLogQO lfLogQO) {
        if(null == lfLogQO || StringUtils.isBlank(lfLogQO.getYhid())) {
            throw new MissingArgumentException("查询用户查询日志异常：未指定用户ID");
        }

        return repo.selectPaging("listUserQueryLogByPage", lfLogQO, pageable);
    }

    /**
     * 相同字段重复查询异常（分页台账）
     * @param pageable 分页参数
     * @param lfCxycQO 业务查询参数
     * @return 异常查询信息
     */
    @Override
    public Page<LfCxycDTO> listXtzdcfcxycTableData(Pageable pageable, LfCxycQO lfCxycQO) {
        if(null == lfCxycQO.getYcbz()) {
            lfCxycQO.setYcbz(Constants.XTTJCXYCBZ_30);
        }
        List<LfCxycDTO> result = cxycjgMapper.listXtzdcfcxycTableData(lfCxycQO);
        if (CollectionUtils.isEmpty(result)) {
            return new PageImpl(Collections.emptyList(), pageable, 0);
        }
        for (LfCxycDTO lfCxycDTO : result) {
            UserDto userDto = userManagerUtils.getUserByUserid(lfCxycDTO.getYhid());
            if (userDto != null) {
                lfCxycDTO.setZw(userDto.getTitle());
            }
        }
        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        if (startIndex > result.size()) {
            return new PageImpl(Collections.emptyList(), pageable, 0);
        }

        int endIndex = (pageable.getPageNumber() + 1) * pageable.getPageSize();
        List<LfCxycDTO> pageData;
        if (endIndex > result.size()) {
            pageData = result.subList(startIndex, result.size());
        } else {
            pageData = result.subList(startIndex, endIndex);
        }
        return new PageImpl(pageData, pageable, result.size());
    }

    /**
     * 统计指定字段重复查询次数
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    @Override
    public List<LfCxycDTO> listXtzdcfcxycChartData(LfCxycQO lfCxycQO) {
        if(null == lfCxycQO || StringUtils.isBlank(lfCxycQO.getCfcxzd())) {
            throw new MissingArgumentException("未指定要统计的重复查询字段");
        }

        if(null == lfCxycQO.getYcbz()) {
            lfCxycQO.setYcbz(Constants.XTTJCXYCBZ_30);
        }

        return cxycjgMapper.listXtzdcfcxycChartData(lfCxycQO);
    }

    /**
     * 统计指定字段重复查询次数,被查询人统计图表
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    @Override
    public List<LfCxycDTO> listXtzdcfcxycBcxrChartData(LfCxycQO lfCxycQO) {
        if(null == lfCxycQO.getYcbz()) {
            lfCxycQO.setYcbz(Constants.XTTJCXYCBZ_30);
        }

        return cxycjgMapper.listXtzdcfcxycBcxrChartData(lfCxycQO);
    }

    /**
     * 生成每个用户对应查询信息实体Map
     * @param roleUsersMap 角色对应异常查询信息
     * @param roleName 角色名称
     * @return {Map} 用户查询异常信息
     */
    private Map<String, LfCxycDTO> generateUserQueryMap(Map<String, List<LfCxycDTO>> roleUsersMap, String roleName) {
        List<LfCxycDTO> roleQueryData = roleUsersMap.get(roleName);

        Map<String, LfCxycDTO> result = new HashMap<>();
        for(LfCxycDTO lfCxycDTO : roleQueryData) {
            // 每个用户只会有一个查询信息实体
            result.put(lfCxycDTO.getYhm(), lfCxycDTO);
        }
        return result;
    }
}
