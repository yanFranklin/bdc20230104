package cn.gtmap.realestate.register.ui.web.main;

import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-01
 * @description
 */
public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${app.oauth:}")
    protected String authUrl;

    @Value("${security.oauth2.client.client-id:}")
    protected String clientId;

    @Value("${security.oauth2.client.client-secret:}")
    protected String clientSecret;

    @Autowired
    protected RestTemplate restTemplate;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;

    /**
     * 外网签名图片地址(存在内外网时候，外网url)
     */
    @Value("${url.sign.wwimage:}")
    protected String signImageWwUrl;

    /**
     * 档案地址
     */
    @Value("${url.archive:}")
    protected String archiveUrl;
    /**
     * 审核登簿服务地址
     */
    @Value("${url.register:}")
    protected String registerUrl;
    /**
     * 审核登簿UI服务地址
     */
    @Value("${url.register-ui:}")
    protected String registerUiUrl;
    /**
     * 证书归档服务地址
     */
    @Value("${url.certificate:}")
    protected String certificateUrl;
    /**
     * 打印 dataUrl 的 IP
     */
    @Value("${url.print-ip:}")
    protected String printip;
    /**
     * 是否开启根据房屋用途隐藏首次证明单的土地基本信息
     */
    @Value("${sczmd.tdjbxx-fwyt-hide:false}")
    protected boolean tdjbxxHide;
    /**
     * 隐藏土地基本信息的房屋用途
     */
    @Value("${sczmd.fwyt-dm:}")
    protected String tdjbxxHideFwyt;
    /**
     * 需要隐藏的页面的字段值（和VO对象字段名一致）
     */
    @Value("${sczmd.tdjbxx-elementName:}")
    protected String tdjbxxElementName;

    /**
     * 版本路径
     */
    @Value("${html.version:hefei}")
    protected String systemVersion;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 金额单位
     */
    @Value("${jedw:万元}")
    private String jedw;

    /*
     * 盐城层高配置值
     * */
    @Value("#{'${cg:}'.split(',')}")
    private List<String> cgList;

    /**
     * 常州的注销登记流程id
     */
    @Value("${zxdj.processId:}")
    protected String zxdjProcessId;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 项目内多幢,不需要同步分幢面积之和到建筑面积的宗地特征码
      */
    @Value("${xmndz.nosumfzmj.zdtzm:}")
    protected String nosumfzmjZdtzm;

    /**
     * 户室图请求url
     */
    @Value("${hst.httpurl:}")
    protected String hstHttpUrl;

    @Value("${djb.xssjys:}")
    protected String djbxssjys;




    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    protected UserManagerUtils userManagerUtils;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;

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
     * @param pageable
     * @param map
     * @return java.util.Map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理排序信息，处理pageable中sort传入的参数，
     * 将排序信息和查询信息整理成一个map作为查询条件
     */

    public static Map pageableSort(Pageable pageable, Map map) {
        if (pageable.getSort() != null && !"UNSORTED".equals(String.valueOf(pageable.getSort()))) {
            String sort = String.valueOf(pageable.getSort());
            String sortParameter = sort.replace(":", "");
            map.put("sortParameter", sortParameter);
        }
        return map;
    }

    /**
     * @return BdcUrlDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前系统配置的url参数
     */
    public BdcUrlDTO bdcUrl() {
        BdcUrlDTO bdcUrlDTO = new BdcUrlDTO();
        bdcUrlDTO.setRegisterUrl(registerUrl);
        bdcUrlDTO.setArchiveUrl(archiveUrl);
        bdcUrlDTO.setCertificateUrl(certificateUrl);
        bdcUrlDTO.setSignImageUrl(signImageUrl);
        bdcUrlDTO.setRegisterUiUrl(registerUiUrl);
        bdcUrlDTO.setJedw(jedw);
        bdcUrlDTO.setCgList(cgList);
        bdcUrlDTO.setPrintIp(printip);
        bdcUrlDTO.setZxdjProcessId(zxdjProcessId);
        bdcUrlDTO.setNosumfzmjZdtzm(nosumfzmjZdtzm);
        bdcUrlDTO.setHstHttpUrl(hstHttpUrl);
        bdcUrlDTO.setDjbxssjys(djbxssjys);
        return bdcUrlDTO;
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取用户信息
     */
    @GetMapping("/rest/v1.0/user/info")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取用户基本资料")
    public Object queryUserInfo() {
        return userManagerUtils.getCurrentUser();
    }

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取字典
     */
    @ResponseBody
    @PostMapping("/bdczd")
    public Map<String, List<Map>> initzd() {
        Map<String, List<Map>> zdMap = new HashMap<>();
        Map<String, List<Map>> slZdMap = new HashMap<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
            slZdMap = bdcSlZdFeignService.listBdcSlzd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        zdMap.putAll(slZdMap);
        return zdMap;
    }

    /**
     * 获取Token
     * @return Token
     */
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
