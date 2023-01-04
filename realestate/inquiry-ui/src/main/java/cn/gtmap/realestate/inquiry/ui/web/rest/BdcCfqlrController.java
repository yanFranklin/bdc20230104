package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.clients.OauthManagerClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.util.Constants;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/12/19
 * @description 拆分权利人
 */
@RestController
@RequestMapping(value = "/cfqlr")
public class BdcCfqlrController extends BaseController {
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private LogMessageClient logMessageClient;
    @Autowired
    private OauthManagerClient oauthManagerClient;

    @Value("${security.oauth2.client.client-id}")
    private String inquiryUiClientId;

    /**
     * 拆分权利人信息
     *
     * @param qlrid
     * @return
     */
    @GetMapping(value = "/split/{qlrid}")
    @ResponseStatus(HttpStatus.OK)
    public Object splitQlr(@PathVariable(name = "qlrid") String qlrid) {
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrid(qlrid);
        // 查询权利人信息
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isEmpty(bdcQlrDOList)) {
            throw new AppException("未找到权利人信息");
        }
        // 处理权利人信息中的数据
        BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
        String[] qlrmc = bdcQlrDO.getQlrmc().split(",|，|、");
        String[] zjh = bdcQlrDO.getZjh().split(",|，|、");
        // 组织返回值
        Map result = new HashMap();
        result.put("qlrmc", qlrmc);
        result.put("zjh", zjh);

        return result;
    }

    /**
     * 保存拆分后的权利人信息
     *
     * @param bdcQlrDOList
     * @param qlrid
     */
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.OK)
    public void saveQlrList(@RequestBody List<BdcQlrDO> bdcQlrDOList, String qlrid) throws Exception {
        if (CollectionUtils.isEmpty(bdcQlrDOList) || StringUtils.isBlank(qlrid)) {
            throw new AppException("传入参数不足");
        }
        // 查询权利人信息
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrid(qlrid);
        List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isEmpty(bdcQlrDOS)) {
            throw new AppException("未找到权利人信息");
        }
        // 处理权利人信息中的数据
        BdcQlrDO bdcQlr = bdcQlrDOS.get(0);
        List<BdcQlrDO> bdcQlrDOListBefore = new ArrayList<>();
        bdcQlrDOListBefore.add(bdcQlr);
        List<BdcQlrDO> bdcQlrDOListAfter = new ArrayList<>();

        // 处理拆分后数据
        // 保存拆分后权利人
        for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
            BdcQlrDO bdcQlrDONew = new BdcQlrDO();
            BeanUtils.copyProperties(bdcQlr, bdcQlrDONew);
            bdcQlrDONew.setQlrmc(bdcQlrDO.getQlrmc());
            bdcQlrDONew.setZjh(bdcQlrDO.getZjh());
            bdcQlrDONew.setQlrid(UUIDGenerator.generate());

            bdcQlrDOListAfter.add(bdcQlrDONew);
            // 保存拆分后数据
            bdcQlrFeignService.insertBdcQlr(bdcQlrDONew);
        }
        // 删除拆分前权利人
        bdcQlrFeignService.deleteBdcQlr(qlrid);
        // 生成日志数据
        Map<String, Object> data = initData(bdcQlr.getXmid(), bdcQlr.getQlrlb(), bdcQlrDOListBefore, bdcQlrDOListAfter);

        // 记录日志
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.CFQLR, data);
        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     * 获取拆分时间
     *
     * @param qlrid
     */
    @GetMapping(value = "/cfsj/{qlrid}")
    @ResponseStatus(HttpStatus.OK)
    public Object getCfsj(@PathVariable(name = "qlrid") String qlrid) {
        // 查询权利人信息
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrid(qlrid);
        List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isEmpty(bdcQlrDOS)) {
            throw new AppException("未找到权利人信息");
        }
        // 处理权利人信息中的数据
        BdcQlrDO bdcQlr = bdcQlrDOS.get(0);
        List<QueryLogCondition> conditions = Lists.newArrayList();
        QueryLogCondition queryLogCondition = new QueryLogCondition();
        queryLogCondition.setType(CommonConstantUtils.TYPE_EQUAL);
        queryLogCondition.setKey(Constants.CFQLR_PARAMCH);
        queryLogCondition.setValue(bdcQlr.getXmid() + bdcQlr.getQlrlb());
        conditions.add(queryLogCondition);
        Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(0, 10,
                Constants.CFQLR, userManagerUtils.getCurrentUserName(), null, null, null, conditions);

        Date cfsj = null;
        if (auditLogDtoPage.hasContent()) {
            for (AuditLogDto auditLogDto : auditLogDtoPage) {
                cfsj = auditLogDto.getTimestamp_millis();
                if (cfsj != null) {
                    break;
                }
            }
        }
        return cfsj;
    }

    /**
     * 初始化 日志 data 数据
     *
     * @param bdcQlrDOListBefore 业务信息对象修改前
     * @param bdcQlrDOListAfter  业务信息对象修改后
     * @param xmid               项目 id
     * @param qlrlb              权利人类别
     * @return {Map} 返回日志保存需要的对象
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     */
    private Map<String, Object> initData(String xmid, String qlrlb, List<BdcQlrDO> bdcQlrDOListBefore, List<BdcQlrDO> bdcQlrDOListAfter) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.CFQLR_AFTER, RSAEncryptUtils.encrypt(JSON.toJSONString(bdcQlrDOListAfter)));
        data.put(Constants.CFQLR_BEFORE, RSAEncryptUtils.encrypt(JSON.toJSONString(bdcQlrDOListBefore)));
        data.put(Constants.CFQLR_PARAMCH, xmid + qlrlb);
        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "权利人拆分更新信息");
        data.put("eventName", Constants.CFQLR);
        return data;
    }

    /**
     * @param moduleCode 模块编码
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取角色 模块权限
     */
    @GetMapping("/module/authority")
    @ApiOperation(value = "获取角色模块权限")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "moduleCode", value = "模块编码", paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    public Object queryModuleAuthority(@RequestParam(name = "moduleCode", required = false) String moduleCode) {
        String userName = userManagerUtils.getCurrentUserName();
        return oauthManagerClient.findModuleAuthority(userName, moduleCode, null);
    }
}
