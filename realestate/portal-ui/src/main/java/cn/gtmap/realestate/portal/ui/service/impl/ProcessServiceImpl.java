package cn.gtmap.realestate.portal.ui.service.impl;

import cn.gtmap.gtc.clients.CollectionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserCollectionDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.CategoryProcessDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.dto.portal.BdcProcessDefData;
import cn.gtmap.realestate.common.core.enums.BdcSclxEnum;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.RegisterWorkflowFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.core.dto.BdcCategoryProcessDto;
import cn.gtmap.realestate.portal.ui.service.ProcessService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程任务服务
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlFeignService bdcSlFeignService;
    @Autowired
    private RegisterWorkflowFeignService registerWorkflowFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    /**
     * 流程任务
     */
    @Autowired
    private ProcessTaskClient processTaskClient;
    /**
     * 收藏
     */
    @Autowired
    private CollectionManagerClient collectionManagerClient;
    /**
     * 新建任务滚动大小
     */
    @Value("${workflow.list.size}")
    private Integer size;

    /**
     * 自然资源确权系统的流程描述
     */
    @Value("${workflow.zrzyDescription:}")
    private String zrzyDescription;

    @Value("${xjrwsjcdky:false}")
    private boolean xjrwsjcdky;

    /**
     * 获取新建任务列表
     *
     * @param userDto
     * @param processDefName
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public Map<String, Object> listCategoryProcess(UserDto userDto, String processDefName) {
        //将返回任务列表以及显示大小组织返回结果
        Map<String, Object> result = new HashMap<>();
        if (userDto != null) {
            List<BdcCategoryProcessDto> resultlist = new ArrayList<>();
            // 获取任务列表
            List<CategoryProcessDto> list = processTaskClient.listCategoryProcess(userDto.getUsername());
            //如果设置了自然资源确权系统的流程描述，则过滤掉
            if(StringUtils.isNotBlank(zrzyDescription)) {
                list = list.stream()
                        .filter(bdcCategoryProcessDto -> !bdcCategoryProcessDto.getDescription().startsWith(zrzyDescription))
                        .collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(list)) {
                // 获取收藏信息
                List<UserCollectionDto> userCollectionDtos = collectionManagerClient.listUserCollectionByType(userDto.getUsername(), BdcSclxEnum.DJLC.getType());
                Set<String> codeSet = Sets.newHashSet();
                userCollectionDtos.forEach(userCollectionDto ->
                        codeSet.add(userCollectionDto.getCode())
                );

                dealWithProcess(processDefName, resultlist, list, codeSet);
            }
            // 判断是否是三级菜单
            long count = resultlist.stream().filter(bdcCategoryProcessDto -> CollectionUtils.isNotEmpty(bdcCategoryProcessDto.getChildren())).count();
            result.put("xjrwsjcdky",xjrwsjcdky);
            result.put("three", count > 0);
            result.put("size", size);
            result.put("content", resultlist);
        }
        return result;
    }

    /**
     * 获取自然资源新建任务列表
     *
     * @param userDto
     * @param processDefName
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public Map<String, Object> listZrzyCategoryProcess(UserDto userDto, String processDefName) {
        //将返回任务列表以及显示大小组织返回结果
        Map<String, Object> result = new HashMap<>();
        if (userDto != null) {
            List<BdcCategoryProcessDto> resultlist = new ArrayList<>();
            // 获取任务列表
            List<CategoryProcessDto> list = processTaskClient.listCategoryProcess(userDto.getUsername());
            //如果设置了自然资源确权系统的流程描述，则只返回自然资源的流程
            if (StringUtils.isNotBlank(zrzyDescription)) {
                list = list.stream()
                        .filter(bdcCategoryProcessDto -> bdcCategoryProcessDto.getDescription().startsWith(zrzyDescription))
                        .collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(list)) {
                // 获取收藏信息
                List<UserCollectionDto> userCollectionDtos = collectionManagerClient.listUserCollectionByType(userDto.getUsername(), BdcSclxEnum.DJLC.getType());
                Set<String> codeSet = Sets.newHashSet();
                userCollectionDtos.forEach(userCollectionDto ->
                        codeSet.add(userCollectionDto.getCode())
                );

                dealWithProcess(processDefName, resultlist, list, codeSet);
            }
            // 判断是否是三级菜单
            long count = resultlist.stream()
                    .filter(bdcCategoryProcessDto -> CollectionUtils.isNotEmpty(bdcCategoryProcessDto.getChildren()))
                    .count();
            result.put("three", count > 0);
            result.put("size", size);
            result.put("content", resultlist);
        }
        return result;
    }

    /**
     * 处理流程信息
     * 通过递归实现对于子节点的遍历
     *
     * @param processDefName 工作流定义名称
     * @param resultlist     流程列表
     * @param list           大云查询得到的流程列表
     * @param codeSet        收藏列表
     */
    private void dealWithProcess(String processDefName, List<BdcCategoryProcessDto> resultlist, List<CategoryProcessDto> list, Set<String> codeSet) {
        for (CategoryProcessDto categoryProcessDto : list) {
            BdcCategoryProcessDto bdcCategoryProcessDto = new BdcCategoryProcessDto();
            //根据名称过滤
            if (StringUtils.isNotBlank(processDefName)) {
                if (CollectionUtils.isNotEmpty(categoryProcessDto.getProcessList())) {
                    categoryProcessDto.setProcessList(categoryProcessDto.getProcessList().stream().filter(
                            processDefData -> StringUtils.contains(processDefData.getName(), processDefName)).collect(Collectors.toList()));
                }
            }
            //新建任务列表组织
            BeanUtils.copyProperties(categoryProcessDto, bdcCategoryProcessDto);
            if (CollectionUtils.isNotEmpty(categoryProcessDto.getProcessList())) {

                List<BdcProcessDefData> processlist = new ArrayList<>();
                //流程列表处理
                for (ProcessDefData processDefData : bdcCategoryProcessDto.getProcessList()) {
                    BdcProcessDefData bdcProcessDefData = new BdcProcessDefData();
                    BeanUtils.copyProperties(processDefData, bdcProcessDefData);
                    //判断是否收藏
                    bdcProcessDefData.setCommonUse(codeSet.contains(bdcProcessDefData.getProcessDefKey()) ? CommonConstantUtils.SF_S_DM : CommonConstantUtils.SF_F_DM);
                    processlist.add(bdcProcessDefData);
                }
                bdcCategoryProcessDto.setProcessList(processlist);
            }

            // 多级目录处理
            if (CollectionUtils.isNotEmpty(bdcCategoryProcessDto.getChildren())) {
                List<BdcCategoryProcessDto> children = new ArrayList<>();
                dealWithProcess(processDefName, children, categoryProcessDto.getChildren(), codeSet);
                bdcCategoryProcessDto.setChildren(children);
            }
            if (CollectionUtils.isNotEmpty(bdcCategoryProcessDto.getChildren()) || CollectionUtils.isNotEmpty(bdcCategoryProcessDto.getProcessList())) {
                resultlist.add(bdcCategoryProcessDto);
            }
        }

    }


    /**
     * 获取流程列表
     *
     * @param userDto
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<ProcessDefData> listAllProcess(UserDto userDto) {
        // 获取任务列表
        List<CategoryProcessDto> list = processTaskClient.listCategoryProcess(userDto.getUsername());
        List<ProcessDefData> processDefDataList = new ArrayList<>();
        //刷选出所有的流程
        if(CollectionUtils.isNotEmpty(list)){
            dealWithProcess(processDefDataList,list);
        }
        return processDefDataList;
    }

    /**
     * 处理流程信息
     * 通过递归实现对于子节点的遍历
     *
     * @param resultlist     流程列表
     * @param list           大云查询得到的流程列表
     */
    private void dealWithProcess(List<ProcessDefData> resultlist, List<CategoryProcessDto> list) {
        for (CategoryProcessDto categoryProcessDto : list) {
            if(CollectionUtils.isNotEmpty(categoryProcessDto.getProcessList())){
                resultlist.addAll(categoryProcessDto.getProcessList());
            }
            // 多级目录处理
            if (CollectionUtils.isNotEmpty(categoryProcessDto.getChildren())) {
                dealWithProcess(resultlist, categoryProcessDto.getChildren());
            }
        }

    }

}
