package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/21.
 * @description
 */
@Service("daxxDealServiceImpl")
public class DaxxDealServiceImpl {
    private final Logger LOGGER = LoggerFactory.getLogger(DaxxDealServiceImpl.class);

    /**
     * 获取档案id
     *
     * @param xml
     * @return
     */
    public Map getArchid(Map paramMap) {
        Map map = new HashMap();
        if (MapUtils.isNotEmpty(paramMap)) {
            //成功
            if (CommonConstantUtils.SF_F_DM.equals(paramMap.get("Code")) && paramMap.get("Data") != null) {
                SAXReader sr = new SAXReader();
                InputStream inputStream = new ByteArrayInputStream(paramMap.get("Data").toString().getBytes());
                Document document = null;
                try {
                    document = sr.read(inputStream);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                if (document != null) {
                    Element root = document.getRootElement();
                    List nodeList = root.selectNodes("//ds");
                    if (CollectionUtils.isNotEmpty(nodeList)) {
                        Element ele = (Element) nodeList.get(0);
                        for (Iterator iter = ele.elementIterator(); iter.hasNext(); ) {
                            Element child = (Element) iter.next();
                            map.put(child.getName(), child.getTextTrim());
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 获取档案目录信息
     *
     * @param paramMap
     * @return
     */
    public List<Map> getMlxx(Map paramMap) {
        List<Map> result = new ArrayList<>();
        if (MapUtils.isNotEmpty(paramMap)) {
            //成功
            if (CommonConstantUtils.SF_F_DM.equals(paramMap.get("Code")) && paramMap.get("Data") != null) {
                SAXReader sr = new SAXReader();
                InputStream inputStream = new ByteArrayInputStream(paramMap.get("Data").toString().getBytes());
                Document document = null;
                try {
                    document = sr.read(inputStream);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                if (document != null) {
                    Element fileRoot = document.getRootElement();
                    List filenodeList = fileRoot.selectNodes("//ds");
                    if (CollectionUtils.isNotEmpty(filenodeList)) {
                        for (Object obj : filenodeList) {
                            Map childMap = new HashMap();
                            Element ele = (Element) obj;
                            for (Iterator iter = ele.elementIterator(); iter.hasNext(); ) {
                                Element child = (Element) iter.next();
                                childMap.put(child.getName(), child.getTextTrim());
                            }
                            result.add(childMap);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取附件
     *
     * @param xml
     * @return
     */
    public Map getFjxx(String xml) {
        Map map = new HashMap();
        if (StringUtils.isNotBlank(xml)) {
            SAXReader sr = new SAXReader();
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document document = null;
            try {
                document = sr.read(inputStream);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            if (document != null) {
                Element fileRoot = document.getRootElement();
                String result = fileRoot.getTextTrim();
                if (StringUtils.isNotBlank(result)) {
                    Map jsonMap = JSONObject.parseObject(result, Map.class);
                    if (MapUtils.isNotEmpty(jsonMap)) {
                        //成功
                        if (CommonConstantUtils.SF_F_DM.equals(jsonMap.get("Code")) && jsonMap.get("Data") != null) {
                            String base64 = jsonMap.get("Data").toString();
                            base64 = base64.replaceAll("\\/", "/");
                            map.put("data", base64);
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 获取分层分户目录
     *
     * @param xml
     * @return
     */
    public List<Map> getFcfhtMl(Map paramMap) {
        List<Map> result = new ArrayList<>();
        if (MapUtils.isNotEmpty(paramMap)) {
            //成功
            if (CommonConstantUtils.SF_F_DM.equals(paramMap.get("Code")) && paramMap.get("Data") != null) {
                SAXReader sr = new SAXReader();
                InputStream inputStream = new ByteArrayInputStream(paramMap.get("Data").toString().getBytes());
                Document document = null;
                try {
                    document = sr.read(inputStream);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                if (document != null) {
                    Element fileRoot = document.getRootElement();
                    List filenodeList = fileRoot.selectNodes("//ds");
                    if (CollectionUtils.isNotEmpty(filenodeList)) {
                        for (Object obj : filenodeList) {
                            Map childMap = new HashMap();
                            Element ele = (Element) obj;
                            for (Iterator iter = ele.elementIterator(); iter.hasNext(); ) {
                                Element child = (Element) iter.next();
                                childMap.put(child.getName(), child.getTextTrim());
                            }
                            result.add(childMap);
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * 获取分层分户目录-给接口管理包一层data
     *
     * @param xml
     * @return
     */
    public Map getFcfhtMlForJkgl(Map paramMap) {
        Map map = new HashMap();
        List<Map> result = new ArrayList<>();
        if (MapUtils.isNotEmpty(paramMap)) {
            //成功
            if (CommonConstantUtils.SF_F_DM.equals(paramMap.get("Code")) && paramMap.get("Data") != null) {
                SAXReader sr = new SAXReader();
                InputStream inputStream = new ByteArrayInputStream(paramMap.get("Data").toString().getBytes());
                Document document = null;
                try {
                    document = sr.read(inputStream);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                if (document != null) {
                    Element fileRoot = document.getRootElement();
                    List filenodeList = fileRoot.selectNodes("//ds");
                    if (CollectionUtils.isNotEmpty(filenodeList)) {
                        for (Object obj : filenodeList) {
                            Map childMap = new HashMap();
                            Element ele = (Element) obj;
                            for (Iterator iter = ele.elementIterator(); iter.hasNext(); ) {
                                Element child = (Element) iter.next();
                                childMap.put(child.getName(), child.getTextTrim());
                            }
                            result.add(childMap);
                            map.put("data", result);

                        }
                    }
                }
            }
        }
        LOGGER.info("返回目录为：{}", null != map ? map.toString() : "为空！");
        return map;
    }
}
