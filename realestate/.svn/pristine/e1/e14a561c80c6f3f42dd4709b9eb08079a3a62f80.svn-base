package cn.gtmap.realestate.natural.resource.web.main;

import cn.gtmap.gtc.starter.gcas.util.HttpUtils;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
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
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/4
 * @description
 */
public class BaseController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Layui请求返回成功编码
     */
    public final static int LAYUI_SUCCESS_CODE = 0;
    /**
     * Layui请求返回错误编码
     */
    public final static int LAYUI_ERROR_CODE = 1;

    @Autowired
    protected BdcZdFeignService bdcZdFeignService;
    @Autowired
    protected BdcSlZdFeignService bdcSlZdFeignService;
    @Value("${app.oauth}")
    private String authUrl;
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Autowired
    RestTemplate restTemplate;

    public static HashMap returnSuccesResult(Boolean success, String msg) {
        HashMap resultMap = new HashMap(1);
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description Message提供者接口
     */
    @Autowired
    protected MessageProvider messageProvider;

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
            map.put("code", LAYUI_SUCCESS_CODE);
        }
        return map;
    }

    /**
     * 处理list结构数据 添加code字段
     *
     * @param list
     * @return
     */
    public Object addLayUiCode(List list) {
        Map<String, Object> map = new HashMap(3);
        if (CollectionUtils.isEmpty(list)) {
            map.put("msg", "无数据");
        } else {
            map.put("msg", "成功");
            map.put("data", list);
        }
        map.put("code", LAYUI_SUCCESS_CODE);

        return map;
    }

    /**
     * @param msg 错误信息
     * @return
     */
    public Object addLayUiErrorCode(String msg) {
        Map map = new HashMap(4);
        map.put("code", LAYUI_ERROR_CODE);
        map.put("msg", msg);
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
        if (pageable.getSort() != null) {
            String sort = String.valueOf(pageable.getSort());
            String sortParameter = sort.replace(":", "");
            map.put("sortParameter", sortParameter);
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

    /**
     * sly 前台分页默认从1开始，后台分页默认从0开始，在这里做减1处理
     *
     * @param msg
     * @return
     */
    public static Map returnHtmlResult(Boolean success, String msg) {
        Map resultMap = new HashMap(1);
        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return resultMap;
    }

    /**
     * 获得需要的所有字典项list
     *
     * @param zdNames
     * @return
     */
    public Map getZdList(String[] zdNames) {
        Map<String, List<Map>> resultMap = new HashMap<>(zdNames.length);
        if (ArrayUtils.isNotEmpty(zdNames)) {
            for (String zdName : zdNames) {
                resultMap.put(zdName, bdcZdFeignService.queryBdcZd(zdName));
            }
        }
        return resultMap;
    }

    /**
     * 获得需要的所有字典项list
     *
     * @param zdNames
     * @return
     */
    public Map getSlZdList(String[] zdNames) {
        Map<String, List<Map>> resultMap = new HashMap<>(zdNames.length);
        if (ArrayUtils.isNotEmpty(zdNames)) {
            for (String zdName : zdNames) {
                resultMap.put(zdName, bdcSlZdFeignService.queryBdcSlzd(zdName));
            }
        }
        return resultMap;
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


    public String getAccountToken() {
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
            String tokenString = HttpUtils.post(builder.toString(), null, null, null);
            JSONObject json = JSON.parseObject(tokenString);
            token = json.getString("access_token");
        }
        return token;

    }

    /**
     * 读取sheet页并写入对应Object
     *
     * @param dataClass   导出类名
     * @param sheetNumber 导出页号
     * @param workbook    导出文件
     * @return List 数据列表
     */
    public List importToObject(Class dataClass, Integer sheetNumber, Workbook workbook) throws InstantiationException, IllegalAccessException, ParseException {
        Sheet dataSheet = workbook.getSheet(sheetNumber);
        Field[] fieldExportDataList = dataClass.getDeclaredFields();
        List dataList = new ArrayList();
        // 遍历Excel
        for (int i = 1; i < dataSheet.getRows(); i++) {
            Object dataObject = dataClass.newInstance();
            for (int j = 0; j < fieldExportDataList.length; j++) {
                fieldExportDataList[j].setAccessible(true);
                String contentData = dataSheet.getCell(j, i).getContents();
                // 判断属性的类型
                if (StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.lang.String")) {
                    fieldExportDataList[j].set(dataObject, contentData);
                } else if ((StringUtils.equals(fieldExportDataList[j].getType().toString(), "int")
                        || StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.lang.Integer"))
                        && StringUtils.isNotBlank(contentData)) {
                    fieldExportDataList[j].set(dataObject, Integer.valueOf(contentData));
                } else if (StringUtils.equals(fieldExportDataList[j].getType().toString(), "class java.util.Date")
                        && StringUtils.isNotBlank(contentData)) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ROOT);
                    Date dateData = simpleDateFormat.parse(contentData);
                    fieldExportDataList[j].set(dataObject, dateData);
                }
            }
            dataList.add(dataObject);
        }
        return dataList;
    }
}
