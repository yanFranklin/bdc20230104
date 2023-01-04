package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBody;
import cn.gtmap.realestate.exchange.core.bo.wwsq.ParamBodyList;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 响应以及参数结构体公共方法
 * @author <a href=""mailto:lisongtao@gtmap.cn>lisongtao</a>
 * @version 1.0, 2018/5/4
 * @description
 */
@Component
@ConfigurationProperties
public class BodyUtil {
    @Autowired
    private DozerUtil dozerUtil;

    private Map<String,List<String>> required;

    public Map<String, List<String>> getRequired() {
        return required;
    }

    public void setRequired(Map<String, List<String>> required) {
        this.required = required;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(BodyUtil.class);

    /**
     * 参数封装公用代码
     * @param method method
     * @param request request
     * @param obj obj
     * @return ParamBody ParamBody
     */
    public ParamBody getParamBody(HttpServletRequest request, String method, Object... obj){
        //封装参数
        ParamBody paramBody=null;
        try{
            String param= IOUtils.toString(request.getInputStream(),"UTF-8");
            paramBody= JSONObject.parseObject(param, ParamBody.class);
        } catch (Exception e) {
            LOGGER.error(null,e);
        }
        if(paramBody!=null && paramBody.getData()!=null){
            Boolean check=checkRequired(method,paramBody.getData());
            if(!check){
                paramBody.setData(null);
            }else{
                if(obj!=null && obj.length>0){
                    for(Object o:obj){
                        dozerUtil.exchangeBeanDateConvert(paramBody.getData(),o);
                        dozerUtil.sameBeanDateConvert(paramBody.getData(),o,true);
                    }
                }
            }
        }
        return paramBody;
    }

    /**
     * 外网申请参数封装代码
     * @param method
     * @param request
     * @param obj
     * @return
     */
    public ParamBody getWwsqParamBody(HttpServletRequest request, String method, Object... obj){
        //封装参数
        ParamBody paramBody=null;
        try{
            String param= IOUtils.toString(request.getInputStream(),"UTF-8");
            LOGGER.info("外网申请参数方法名：{}，参数:{}", method, param);
            paramBody = JSONObject.parseObject(param, ParamBody.class);
        } catch (Exception e) {
            LOGGER.error(null,e);
        }
        if(paramBody!=null && paramBody.getData()!=null){
            Boolean check=checkRequired(method,paramBody.getData());
            if(!check){
                paramBody.setData(null);
            }else{
                if(obj!=null && obj.length>0){
                    for(Object o:obj){
                        dozerUtil.exchangeBeanDateConvert(paramBody.getData(),o);
                    }
                }
            }
        }
        return paramBody;
    }

    /**
     * 外网申请参数封装代码
     * @param method
     * @param request
     * @param obj
     * @return
     */
    public ParamBodyList getWwsqParamBodyList(HttpServletRequest request, String method, Object... obj){
        //封装参数
        ParamBodyList paramBodyList=null;
        try{
            String param= IOUtils.toString(request.getInputStream(),"UTF-8");
            LOGGER.info("外网申请参数方法名：{}，参数:{}", method, param);
            paramBodyList = JSONObject.parseObject(param, ParamBodyList.class);
        } catch (Exception e) {
            LOGGER.error(null,e);
        }
        //用于记录参数列表必填字段
        Boolean flag = true;
        if(paramBodyList!=null && CollectionUtils.isNotEmpty(paramBodyList.getData())){
            for (Map map : paramBodyList.getData()) {
                Boolean check=checkRequired(method,map);
                if(!check){
                    flag = false;
                }else{
                    if(obj!=null && obj.length>0){
                        for(Object o:obj){
                            dozerUtil.exchangeBeanDateConvert(paramBodyList.getData(),o);
                        }
                    }
                }
            }
            if(!flag) {
                paramBodyList.setData(null);
            }
        }
        return paramBodyList;
    }

    /**
     * 返回结果封装公用代码
     * @param headMap
     * @param bodyMap
     * @return
     */
    public Map getReturnBody(Map headMap, Object bodyMap){
        //封装参数
        Map returnBody=new HashMap();
        if(headMap!=null){
            returnBody.put("head",headMap);
        }
        if(bodyMap!=null){
            returnBody.put("data",bodyMap);
        }
        return returnBody;
    }


    /**
     * 返回分页结果
     * @param headMap
     * @param pageObj
     * @return
     */
    public Map getPageReturnBody(Map headMap, Object pageObj){
        //封装参数
        Map returnBody=new HashMap();
        if(headMap!=null){
            returnBody.put("head",headMap);
        }
        if(pageObj!=null){
            returnBody.put("pageinfo",pageObj);
        }
        return returnBody;
    }

    /**
     * 检查接口必填字段
     * @param method
     * @param map
     * @return
     */
    private Boolean checkRequired(String method, Map map){
        Boolean status = true;
        if(StringUtils.isNotBlank(method) && map!=null  && map.size()>0){
            if(required != null && required.containsKey(method)){
                for(String key : required.get(method)){
                    if(!map.containsKey(key) || StringUtils.isBlank(map.get(key).toString())){
                        status=false;
                        break;
                    }
                }
            }
        }else{
            status=false;
        }
        return status;
    }

    public <T> T parseXmlObj(HttpServletRequest request, Class<T> clazz) {
        try {
            String param = IOUtils.toString(request.getInputStream(), "UTF-8");
            if (StringUtils.isNotBlank(param)) {
                return XmlEntityConvertUtil.toBean(param, clazz);
            }
        } catch (Exception e) {
            LOGGER.error("请求xml参数转换错误", e);
        }
        return null;
    }

    public <T> T parseXmlObjByParam(String param , Class<T> clazz) {
        try {
            if (StringUtils.isNotBlank(param)) {
                return XmlEntityConvertUtil.toBean(param, clazz);
            }
        } catch (Exception e) {
            LOGGER.error("请求xml参数转换错误", e);
        }
        return null;
    }
}
