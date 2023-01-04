package cn.gtmap.realestate.accept.ui.web.main;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPrintDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcDjxlPzFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description
 */
public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${url.storageUrl:}")
    protected String storageUrl;
    @Value("${app.oauth:}")
    private String authUrl;

    @Value("${security.oauth2.client.client-id:}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret:}")
    private String clientSecret;
    /**
     * 版本路径
     */
    @Value("${html.version:standard}")
    protected String systemVersion;
    @Value("${slym.dyzl:fr3}")
    protected String dyzl;

    @Value("#{'${sjddylx.cqsjd:}'.split(',')}")
    private List<String> cqsjdList;
    @Value("#{'${sjddylx.dyasjd:}'.split(',')}")
    private List<String> dyasjdList;
    @Value("#{'${sjddylx.noczsjd:}'.split(',')}")
    private List<String> noczsjdList;
    @Value("#{'${sjddylx.oneSjdDyid:}'.split(',')}")
    private List<String> oneSjdDyidList;

    /**
     * 创建流程后，需要涉税同步的流程配置
     */
    @Value("${swtb.gzldyids:}")
    private String swtbGzldyids;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;

    /**
     * @param request
     * @param response
     */

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        request.setAttribute("storageUrl", EnvironmentConfig.getEnvironment().getProperty("app.storage"));
    }

    /**
     * 处理分页结构数据 添加code字段
     *
     * @param page
     * @return
     */
    public Object addLayUiCode(Page page) {
        Map map = null;
        if (page != null) {
            map = JSONObject.parseObject(JSONObject.toJSONString(page), Map.class);
            if (map != null) {
                map.put("msg", "成功");
            }
            if (map == null) {
                map = new HashMap(1);
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }

    /**
     * sly 前台分页默认从1开始，后台分页默认从0开始，在这里做减1处理
     *
     * @param pageable
     * @return
     */
    public static Pageable delPageRequest(Pageable pageable) {
        if (pageable.getPageNumber() > 0) {
            return new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        } else {
            return pageable;
        }
    }

    public String queryToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;
        if (null != authentication && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        }
        if (StringUtils.isBlank(token)) {
            String authPath = "";
            if (authUrl != null && authUrl.lastIndexOf("/") != (authUrl.length() - 1)) {
                authPath = authUrl + "/";
            } else {
                authPath = authUrl;
            }
            StringBuilder builder = new StringBuilder(authPath);
            builder.append("oauth/token?grant_type=client_credentials&client_id=").append(clientId).append("&client_secret=").append(clientSecret);

            ResponseEntity<Map> o = restTemplate.postForEntity(builder.toString(), (Object) null, Map.class);
            if (o.getStatusCode() == HttpStatus.OK) {
                Map map = o.getBody();
                if (map != null && null != map.get("access_token")) {
                    token = (String) map.get("access_token");
                }
            }
        }
        return token;
    }

    public String getCommonDylx(List<BdcXmDO> bdcXmDOList, BdcSlPrintDTO bdcSlPrintDTO) {
        String dylx = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS) && !StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL)) {
            String djxl = bdcXmDOList.get(0).getDjxl();
            if (StringUtils.isNotBlank(djxl)) {
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJD) || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJDPL)) {
                    if (cqsjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_CQSJD;
                    } else if (dyasjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_DYASJD;
                    } else if (noczsjdList.indexOf(djxl) > -1) {
                        dylx = CommonConstantUtils.DYLX_NOCZSJD;
                    } else {
                        dylx = CommonConstantUtils.DYLX_QTSJD;
                    }
                    if (oneSjdDyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                        dylx = CommonConstantUtils.DYLX_ZHSJD;
                    }
                }
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SFD)
                        || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_JNPZ)) {
                    if (CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDOList.get(0).getQllx())) {
                        dylx = String.format("dy%s", bdcSlPrintDTO.getDylx());
                    } else {
                        dylx = bdcSlPrintDTO.getDylx();
                    }
                }
            }
        }
        // 打印类型为sqs,sqspl
        if (CollectionUtils.isNotEmpty(bdcXmDOList) && StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS) || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL)){
            BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
            if (StringUtils.isNotBlank(bdcDjxlPzDO.getSqsdylx())){
                dylx = bdcDjxlPzDO.getSqsdylx();
            }
        }
        return dylx;
    }

    public Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[]{});
                } catch (Exception e) {
                    LOGGER.error("反射取值出错{}", e.toString());
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase()) || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID") && (ms[i].getName().toUpperCase().equals("ID") || ms[i].getName().substring(3).toUpperCase().equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
            LOGGER.error("取方法出错！{}", e.toString());
        }
        return null;
    }


    public <T> List<T> depCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            LOGGER.error("IOException{}", e.toString());
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException{}", e.toString());
        }
        return null;

    }

    /**
     * 处理外部接口返回
     *
     * @param obj
     * @return 增加 code 和 msg
     */
    public Object addLayUiCodeForWw(Object obj) {
        Map map = null;
        if (obj instanceof LinkedHashMap) {
            map = (LinkedHashMap) obj;
            Object content = map.get("content");
            if (content != null) {
                map.put("msg", "成功");
                if (content instanceof ArrayList) {
                    ArrayList list = (ArrayList) content;
                    if (list.size() == 0) {
                        map.put("msg", "无数据");
                    } else {
                        map.put("msg", "成功");
                    }
                }
            } else {
                map.put("msg", "无数据");
            }
            map.put("code", 0);
        }
        return map;
    }

    /**
     * 判断当前流程是否为：税务同步流程
     */
    public boolean isSwtbLc(String gzldyid){
        if(StringUtils.isNotBlank(gzldyid)){
            return StringUtils.isNotBlank(swtbGzldyids) && swtbGzldyids.indexOf(gzldyid)> -1;
        }
        return false;
    }
}
