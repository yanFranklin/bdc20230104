package cn.gtmap.realestate.certificate.web.main;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.InterfacePermission;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzQqjlService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzTokenService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/5
 */
@Controller
public class DzzzController  {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected BdcDzzzTokenService bdcDzzzTokenService;
    @Autowired
    protected BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    protected BdcDzzzQqjlService bdcDzzzQqjlService;
    @Autowired
    protected BdcDzzzService bdcDzzzService;
    @Autowired
    protected BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Value("${base64.security}")
    protected String base64Security;
    @Value("${token.expiryTime}")
    protected long tokenExpiryTime;
    /*@Value("${server.context-path}")
    protected String serverContextPath;*/
    @Autowired
    protected InterfacePermission interfacePermission;

    @Value("${app.oauth:}")
    private String authUrl;

    @Value("${security.oauth2.client.client-id:}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret:}")
    private String clientSecret;
    @Autowired
    RestTemplate restTemplate;


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

}
