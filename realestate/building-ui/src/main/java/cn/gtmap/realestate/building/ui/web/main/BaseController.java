package cn.gtmap.realestate.building.ui.web.main;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingZdConvertFeignService;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import freemarker.template.Template;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description
 */
public class BaseController {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    BuildingZdConvertFeignService buildingZdConvertFeignService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Value("${lpb.view.version:}")
    private String version;

    /**
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("portalUrl", EnvironmentConfig.getEnvironment().getProperty("app.portal"));
        request.setAttribute("buildingUrl", EnvironmentConfig.getEnvironment().getProperty("app.building"));
        request.setAttribute("storageUrl", EnvironmentConfig.getEnvironment().getProperty("app.storage"));
    }

    /**
     * 获得需要的所有字典项list
     *
     * @param zdList
     * @return
     */
    public Map getZdList(Class[] zdList) {
        if (ArrayUtils.isNotEmpty(zdList)) {
            Map<String, Class> requestMap = new HashMap(zdList.length);
            for (Class zdClass : zdList) {
                requestMap.put(zdClass.getSimpleName(), zdClass);
            }
            if (MapUtils.isNotEmpty(requestMap)) {
                return buildingZdConvertFeignService.listZdTable(requestMap);
            }
        }
        return new HashMap(0);
    }


    /**
     * sly 页面请求时的默认返回参数
     *
     * @param success 是否成功
     * @param msg 要返回的信息
     * @return
     */
    public static Map returnHtmlResult(Boolean success, String msg) {
        Map resultMap = new HashMap(1);
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return resultMap;
    }

    public String getFtlPath(String path) {
        if (StringUtils.isNotBlank(path) && path.indexOf("version") > 0) {
            String ftlpath = path.replace("version", version);
            String ftlName = ftlpath + CommonConstantUtils.FILE_FTL;
            try {
                Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(ftlName);
                path = ftlpath;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                path = path.replace("/version", "");
            }
        }
        return path;
    }

    public void addLog(String data, String hllx) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        map.put(CommonConstantUtils.VIEW_TYPE, CommonConstantUtils.BUILDING_UI_DM);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, hllx);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(CommonConstantUtils.HLNR, data);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.RZLX_HSHB, map);
        zipkinAuditEventRepository.add(auditEvent);
    }
}
