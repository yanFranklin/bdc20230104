package cn.gtmap.realestate.common.core.support.sjsh;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcSjXgShDTO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/28
 * @description 表单数据审核
 */
@RestController
@RequestMapping(value = "/rest/v1.0/common/sjsh")
public class BdSjshController {

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  是否启用表单数据审核功能
     */
    @Value("${bdsjsh.enabled:false}")
    private Boolean bdsjsh;

    /**
     * 拥有数据修改审核权限的角色
     */
    @Value("${sjxgsh.rolename:}")
    private String roleName;


    /**
     * 表单数据审核可以修改的字段
     */
    @Value("${bdsjsh.bdsjshxgzd:}")
    private String bdsjshxgzd;
    /**
     * 修改内容高亮显示登记小类集合
     */
    @Value("${default.xgnrglxs:}")
    private String xgnrglxs;

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  查询数据审核配置功能
      */
    @GetMapping("/pzxx")
    public Object getPzxx(){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("bdsjsh",bdsjsh);
        resultMap.put("bdsjshxgzd",bdsjshxgzd);
        resultMap.put("xgnrglxs",xgnrglxs);
        return resultMap;
    }

    /**
     * 数据修改审核校验用户是否拥有权限，并且记录日志
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseBody
    @PostMapping("/sjxgsh/log")
    public BdcCommonResponse saveSjxgLog(@RequestBody BdcSjXgShDTO bdcSjXgShDTO) {
        // 权限校验
        BdcCommonResponse bdcCommonResponse = userVerify(bdcSjXgShDTO);
        if (!StringUtils.equals(bdcCommonResponse.getCode(), "1")) {
            return bdcCommonResponse;
        }
        AuditEvent auditEvent = new AuditEvent(bdcSjXgShDTO.getUsername(), "数据修改审核", JSONObject.toJSONString(bdcSjXgShDTO));
        zipkinAuditEventRepository.add(auditEvent);
        return BdcCommonResponse.ok();
    }

    /**
     * 数据修改审核校验用户是否拥有权限，并且记录日志
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ResponseBody
    @PostMapping("/sjxgsh")
    public BdcCommonResponse userVerify(@RequestBody BdcSjXgShDTO bdcSjXgShDTO) {
        if (StringUtils.isBlank(roleName)) {
            return BdcCommonResponse.fail("系统未预设拥有数据修改审核权限的角色！");
        }
        String username = bdcSjXgShDTO.getUsername();
        String password = bdcSjXgShDTO.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            return BdcCommonResponse.fail("请输入审核用户的用户名和密码！");
        }

        UserDto user = userManagerUtils.getUserByName(username);
        if (user == null || StringUtils.isBlank(user.getId())) {
            return BdcCommonResponse.fail("不存在此用户！");
        }

        String encodePassword = new String(Base64Utils.decodeBase64StrToByte(password));
        boolean login = userManagerUtils.checkUserPassword(user.getId(), encodePassword);
        if (!login) {
            return BdcCommonResponse.fail("用户名或密码错误！");
        }

        boolean auth = userManagerUtils.hasRoleByUserIdAndRoleName(user.getId(), roleName);
        if (!auth) {
            return BdcCommonResponse.fail("当前输入用户无审核权限！");
        }

        return BdcCommonResponse.ok();
    }
}
