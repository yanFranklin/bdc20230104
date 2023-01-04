package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.clients.CollectionManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserCollectionDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.enums.BdcSclxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.portal.ui.service.BdcCollectService;
import cn.gtmap.realestate.portal.ui.service.ProcessService;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.xlsx4j.sml.Col;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/3/7
 * @description 收藏功能服务
 */
@RestController
@RequestMapping("/rest/v1.0/collect")
@Api(tags = "收藏功能")
public class BdcCollectController extends BaseController {

    /**
     * 首页模块隐藏配置
     */
    @Value("#{'${portal.symk.hide:}'.split(',')}")
    private Set<String> symkHideList;

    @Autowired
    CollectionManagerClient collectionManagerClient;


    @Autowired
    ProcessService processService;

    @Autowired
    BdcCollectService bdcCollectService;

    /**
     * @param userCollectionDto
     * @return userCollectionDto
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @PutMapping("/user")
    @ApiOperation(value = "保存收藏关系")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "userCollectionDto", value = "收藏信息", required = true, dataType = "UserCollectionDto")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCollectionDto saveUserCollect(@RequestBody UserCollectionDto userCollectionDto) {
        if (StringUtils.isBlank(userCollectionDto.getCode()) || StringUtils.isBlank(userCollectionDto.getName())) {
            throw new AppException("保存的收藏信息不能为空！");
        }
        String userName = userManagerUtils.getCurrentUserName();
        List<UserCollectionDto> list = collectionManagerClient.listUserCollectionByType(userName, BdcSclxEnum.DJLC.getType());
        userCollectionDto.setType(BdcSclxEnum.DJLC.getType());
        userCollectionDto.setTypeName(BdcSclxEnum.DJLC.getTypeName());
        int sequenceNumber = 0;
        for (UserCollectionDto collectionDto : list) {
            if (collectionDto.getSequenceNumber() > sequenceNumber) {
                sequenceNumber = collectionDto.getSequenceNumber();
            }
        }
        userCollectionDto.setSequenceNumber(sequenceNumber + 1);
        return collectionManagerClient.saveUserCollection(userName, userCollectionDto);
    }

    /**
     * 更新收藏顺序
     *
     * @param collectionDtos 收藏对象集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新收藏顺序")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "collectionDtos", value = "收藏信息列表", required = true, dataType = "List<UserCollectionDto>")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUserCollect(@RequestBody List<UserCollectionDto> collectionDtos) {
        if (CollectionUtils.isEmpty(collectionDtos) || collectionDtos.size() != 2) {
            throw new AppException("移动的收藏信息不能为空！");
        }
        UserCollectionDto collectionA = collectionDtos.get(0);
        UserCollectionDto collectionB = collectionDtos.get(1);
        int temp = collectionA.getSequenceNumber();
        collectionA.setSequenceNumber(collectionB.getSequenceNumber());
        collectionB.setSequenceNumber(temp);
        String username = userManagerUtils.getCurrentUserName();
        collectionManagerClient.saveUserCollection(username, collectionA);
        collectionManagerClient.saveUserCollection(username, collectionB);
    }

    /**
     * @param code
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除收藏
     */
    @DeleteMapping
    @ApiOperation(value = "删除收藏信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "code", value = "收藏代码（唯一值）", required = true, paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCollect(@RequestParam(name = "code") String code) {
        if (StringUtils.isBlank(code)) {
            throw new MissingArgumentException("要删除的code不能为空！");
        }
        String username = userManagerUtils.getCurrentUserName();
        collectionManagerClient.deleteRelation(username, code);
    }

    /**
     * @param type 收藏类型
     * @return UserCollectionDto
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据收藏类型获取当前用户的收藏
     */
    @GetMapping
    @ApiOperation(value = "获取收藏信息列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "type", value = "收藏类型（默认是DJLC）", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public List<UserCollectionDto> listUserCollectionDto(@RequestParam(name = "type", required = false) String type) {
        String userName = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(type)) {
            type = BdcSclxEnum.DJLC.getType();
        }
        List<UserCollectionDto> userCollectionDtos = collectionManagerClient.listUserCollectionByType(userName, type);
        if((!type.equals(BdcSclxEnum.DJLC.getType())) || CollectionUtils.isEmpty(userCollectionDtos)){
            return userCollectionDtos;
        }
        // 获取任务列表
        List<ProcessDefData> processDefDataList = processService.listAllProcess(userManagerUtils.getCurrentUser());
        if(CollectionUtils.isEmpty(processDefDataList)){
            //没有任何权限
            return Collections.emptyList();
        }
        List<String> processDefKeys = processDefDataList.stream().map(ProcessDefData::getProcessDefKey).collect(Collectors.toList());
        //过滤掉目前没有权限的数据
        List<UserCollectionDto> vaildUserCollectionDtos = userCollectionDtos.stream()
                .filter(userCollectionDto -> processDefKeys.contains(userCollectionDto.getCode()))
                .collect(Collectors.toList());
        //清除掉没有权限的收藏
        if (vaildUserCollectionDtos.size() != userCollectionDtos.size()) {
            //筛选出当前没有权限的收藏
            List<UserCollectionDto> novaildUserCollectionDtos = userCollectionDtos.stream()
                    .filter(userCollectionDto -> !processDefKeys.contains(userCollectionDto.getCode()))
                    .collect(Collectors.toList());
            //删除收藏
            if(CollectionUtils.isNotEmpty(novaildUserCollectionDtos)) {
                for (UserCollectionDto novaildUserCollectionDto : novaildUserCollectionDtos) {
                    bdcCollectService.deleteRelation(userName, novaildUserCollectionDto.getCode());
                }
            }
        }
        return vaildUserCollectionDtos;
    }

    /**
     * 获取首页模块顺序列表
     *
     * @return content 获取到首页模块的 content
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/module")
    @ApiOperation(value = "获取首页模块顺序列表")
    @ResponseStatus(HttpStatus.OK)
    public String listUserCollectionContent() {
        String userName = userManagerUtils.getCurrentUserName();
        List<UserCollectionDto> list = collectionManagerClient.listUserCollectionByType(userName, BdcSclxEnum.SYMK.getType());
        String content = StringUtils.EMPTY;
        if (CollectionUtils.isNotEmpty(list)) {
            content = list.get(0).getContent();
        }

        // 追加统一的symk隐藏的配置
        if (CollectionUtils.isNotEmpty(symkHideList)) {
            if (StringUtils.isNotBlank(content)) {
                for (String item : symkHideList) {
                    if (content.indexOf(item) == -1) {
                        content += CommonConstantUtils.ZF_YW_DH + item;
                    }
                }
            } else {
                content = StringUtils.join(symkHideList, CommonConstantUtils.ZF_YW_DH);
            }
        }
        return content;
    }

    /**
     * 首页自定义模块删除接口
     *
     * @param code 前端页面传递的数据均保存在 Content 字段中
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/module")
    @ApiOperation(value = "自定义模块删除接口")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "code", value = "模块代码", required = true, paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public void saveUserModules(@RequestParam(name = "code") String code) {
        if (StringUtils.isBlank(code)) {
            throw new AppException("模块编号不能为空！");
        }
        String userName = userManagerUtils.getCurrentUserName();
        UserCollectionDto userCollectionDto = new UserCollectionDto();
        List<UserCollectionDto> list = collectionManagerClient.listUserCollectionByType(userName, BdcSclxEnum.SYMK.getType());
        if (CollectionUtils.isEmpty(list)) {
            userCollectionDto.setCode(userName + BdcSclxEnum.SYMK.getType());
            userCollectionDto.setName(userName + BdcSclxEnum.SYMK.getTypeName());
            userCollectionDto.setType(BdcSclxEnum.SYMK.getType());
            userCollectionDto.setTypeName(BdcSclxEnum.SYMK.getTypeName());
            userCollectionDto.setContent(code);
            collectionManagerClient.saveUserCollection(userName, userCollectionDto);
        } else {
            if (StringUtils.isBlank(list.get(0).getContent())) {
                list.get(0).setContent(code);
                collectionManagerClient.saveCollection(list.get(0));
            } else {
                String[] content = list.get(0).getContent().split(",");
                // 避免重复删除同一模块
                if (!ArrayUtils.contains(content, code)) {
                    // 字符串如果为空，直接拼接会带入 null 字符
                    code = list.get(0).getContent() + "," + code;
                    list.get(0).setContent(code);
                    collectionManagerClient.saveCollection(list.get(0));
                }
            }
        }
    }

    /**
     * 首页自定义模块显示接口
     *
     * @param code 前端页面传递的数据均保存在 Content 字段中
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/module/back")
    @ApiOperation(value = "自定义模块显示接口")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "code", value = "模块代码", required = true, paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserModules(@RequestParam(name = "code") String code) {
        if (StringUtils.isBlank(code)) {
            throw new AppException("模块编号不能为空！");
        }
        String userName = userManagerUtils.getCurrentUserName();
        List<UserCollectionDto> list = collectionManagerClient.listUserCollectionByType(userName, BdcSclxEnum.SYMK.getType());
        if (CollectionUtils.isEmpty(list) || StringUtils.isBlank(list.get(0).getContent())) {
            throw new AppException("获取不到对应的模块信息！");
        } else {
            // 在 content 中删除掉对应 code 重新保存即可
            String[] codes = list.get(0).getContent().split(",");
            StringBuilder content = new StringBuilder();
            for (String s : codes) {
                if (!StringUtils.equals(code, s)) {
                    if (StringUtils.isBlank(content)) {
                        content.append(s);
                    } else {
                        content.append(",").append(s);
                    }
                }
            }

            list.get(0).setContent(content.toString());
            collectionManagerClient.saveCollection(list.get(0));
        }
    }

    /**
     * 首页自定义模块恢复默认接口
     * 恢复默认即清除操作记录(每个用户自定义模块的记录保存在 用户名+SYMK 的 code 中)
     *
     * @return boolean 是否成功删除
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping("/module")
    @ApiOperation(value = "自定义模块恢复默认顺序")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public boolean restoreDefault() {
        String userName = userManagerUtils.getCurrentUserName();
        return collectionManagerClient.deleteCollection(userName + BdcSclxEnum.SYMK.getType());
    }


    /**
     * 获取首页自定义区块，默认隐藏配置
     *
     * @return List 隐藏的元素code(1:办理情况; 2:查封到期; 3:工作提醒; 4:数据分析; 5: 操作日志; 6:版本更新;)
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/symk/pz")
    @ApiOperation(value = "获取首页自定义区块，默认隐藏配置")
    @ResponseStatus(HttpStatus.OK)
    public Object getSymkPz() {
        return symkHideList;
    }

}
