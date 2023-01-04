package cn.gtmap.realestate.natural.core.service.impl;


import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyDysjDTO;
import cn.gtmap.realestate.common.core.enums.BdcDyBeanEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyDysjPzVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.core.thread.DetailPrintThread;
import cn.gtmap.realestate.natural.core.thread.PageStringThread;
import cn.gtmap.realestate.natural.service.ZrzyDypzService;
import cn.gtmap.realestate.natural.service.ZrzyDysjPzService;
import cn.gtmap.realestate.natural.core.service.ZrzyPrintCoreService;
import cn.gtmap.realestate.natural.service.ZrzyZsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import groovy.util.Node;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/11
 * @description fr3 打印业务实现类
 */
@Service
public class ZrzyPrintCoreServiceImpl implements ZrzyPrintCoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyPrintCoreServiceImpl.class);
    /**
     * 设置最大打印数据量，超出采用多线程
     */
    private static final Integer MAX_NUM = 50;

    @Autowired
    ZrzyDysjPzService zrzyDysjPzService;
    @Autowired
    ZrzyDypzService zrzyDypzService;
    @Autowired
    ThreadEngine threadEngine;

    @Autowired
    ZrzyXmService zrzyXmService;

    @Autowired
    ZrzyZsService zrzyZsService;
    /**
     * 替换子表空值：子表id
     */
    @Value("${print.replaceNull.zbid:}")
    private String zbids;
    /**
     * 替换子表空值：替换符号
     */
    @Value("${print.replaceNull.symbol:}")
    private String symbol;

    @Autowired
    BdcPrintFeignService bdcPrintFeignService;

    /**
     * @param paramMap {
     *                 "spb":[
     *                 {
     *                 "xmid":"difficultid",
     *                 "sjxxid":"1"
     *                 },
     *                 {
     *                 "xmid":"f166cf582831402c803c66cf58280001",
     *                 "sjxxid":"2"
     *                 }
     *                 ],
     *                 "sjd":[
     *                 {
     *                 "xmid":"difficultid",
     *                 "sjxxid":"1"
     *                 },
     *                 {
     *                 "xmid":"f166cf582831402c803c66cf58280001",
     *                 "sjxxid":"2"
     *                 }
     *                 ]
     *                 }
     * @return xml字符串
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印xml
     */
    @Override
    public String print(Map<String, List<Map>> paramMap) {
        if (MapUtils.isEmpty(paramMap)) {
            throw new MissingArgumentException("打印参数不能为空！");
        }
        Date startTime = new Date();
        final Integer[] valueSize = {0};
        StringBuilder xmlBulider = new StringBuilder();
        paramMap.forEach((key, value) -> {
            if (StringUtils.isEmpty(key)) {
                throw new MissingArgumentException("打印类型");
            }
            /**
             * 获取主表配置
             */
            BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
            bdcDysjPzDO.setDylx(key);
            List<BdcDysjPzDO> bdcDysjPzDOS = zrzyDypzService.listBdcDysjPz(bdcDysjPzDO);
            if (CollectionUtils.isEmpty(bdcDysjPzDOS)) {
                throw new AppException("打印配置主表不能为空");
            }
            /**
             * 获取字表配置
             */
            BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
            bdcDysjZbPzDO.setDylx(key);
            List<BdcDysjZbPzDO> bdcDysjZbPzDOS = zrzyDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
            /**
             * 获取xml配置文件
             */
            String dyzd = StringUtils.trimToEmpty(bdcDysjPzDOS.get(0).getDyzd());
            Node xmlNode = XmlUtils.getXmlNodeByString(dyzd);
            StringBuilder pageBulider;
            if (CollectionUtils.size(value) > MAX_NUM) {
                pageBulider = generatePageBuliderThread(bdcDysjPzDOS, bdcDysjZbPzDOS, xmlNode, value);
            } else {
                pageBulider = generatePageBulider(bdcDysjPzDOS, bdcDysjZbPzDOS, xmlNode, value, key);
            }
            valueSize[0] += CollectionUtils.size(value);
            String xml = XmlUtils.processCharacterSet(XmlUtils.replacePageInXml(xmlNode, pageBulider.toString()));
            xmlBulider.append(xml);
        });
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.info("打印总共执行用时：{},数据量：{}", time, valueSize[0]);
        return xmlBulider.toString();
    }

    /**
     * @param
     * @param configParamList
     * @param key
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成所有数据的打印结果
     */
    private StringBuilder generatePageBulider(List<BdcDysjPzDO> bdcDysjPzDOS, List<BdcDysjZbPzDO> bdcDysjZbPzDOS, Node xmlNode, List<Map> configParamList, String key) {
        StringBuilder pageBulider = new StringBuilder();
        configParamList.forEach(configParam -> {
            configParam.put(CommonConstantUtils.BDC_DYLX, key);
            boolean sfdy = configParamList.size() > 1;
            String page = getPage(bdcDysjPzDOS, bdcDysjZbPzDOS, xmlNode, configParam, sfdy);
            pageBulider.append(page);
        });
        return pageBulider;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 多线生成打印信息
     */
    private StringBuilder generatePageBuliderThread(List<BdcDysjPzDO> bdcDysjPzDOS, List<BdcDysjZbPzDO> bdcDysjZbPzDOS, Node xmlNode, List<Map> configParamList) {
        StringBuilder pageBulider = new StringBuilder();
        List<PageStringThread> pageStringThreadList = new ArrayList();
        for (Map configParam : configParamList) {
            PageStringThread pageStringThread = new PageStringThread(this, bdcDysjPzDOS, bdcDysjZbPzDOS, xmlNode, configParam);
            pageStringThreadList.add(pageStringThread);
        }
        threadEngine.excuteThread(pageStringThreadList, true);
        for (PageStringThread pageStringThread : pageStringThreadList) {
            pageBulider.append(pageStringThread.getPageString());
        }
        return pageBulider;
    }

    /**
     * @param sfdy 是否多页打印
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成每份数据的打印结果
     */
    @Override
    public String getPage(List<BdcDysjPzDO> bdcDysjPzDOS, List<BdcDysjZbPzDO> bdcDysjZbPzDOS, Node xmlNode, Map configParam, boolean sfdy) {
        /**
         * 根据主表配置查询数据
         */
        Map dataMap = zrzyDysjPzService.queryPrintDatasList(configParam, "bdcRegisterConfigMapper", bdcDysjPzDOS);
        /**
         * 根据子表配置查询数据
         */
        Multimap<String, List<Map>> detailMap = zrzyDysjPzService.queryPrintDetailList(configParam, "bdcRegisterConfigMapper", bdcDysjZbPzDOS);
        /**
         * 子表配置 xml
         */
        Map<String, String> detailXml = XmlUtils.converDetailXmlString(xmlNode);
        /**
         * 子表配置 row xml
         */
        Map<String, String> detailRowXml = XmlUtils.converDetailRowXmlString(xmlNode);
        /**
         *  子表配置的所有 字段
         */
        Map<String, List<String>> configKeys = XmlUtils.getDetailXmlKeys(xmlNode);
        /**
         * 替换值 后的子表xml
         */

        Map<String, String> detailXmlMap = Maps.newHashMap();
        detailMap.entries().forEach(entry -> {
            Date startTime = new Date();

            String key = entry.getKey();
            boolean replaceSymbol = Lists.newArrayList(zbids.split(",")).contains(key);
            List<Map> value = entry.getValue();
            String detailStr = value.size() > MAX_NUM ? processDetailXmlMultiThread(configKeys, detailXml, detailRowXml, key, value) : processDetailXml(configKeys, detailXml, detailRowXml, key, value, replaceSymbol);
            detailXmlMap.put(key, detailStr);

            long time = System.currentTimeMillis() - startTime.getTime();
            LOGGER.info("子表{}打印总共执行用时：{},数据量：{}", key, time, value.size());
        });
        //sfdy 为true时表示需要多页打印，需要page标签
        return sfdy ? processPrintXml(xmlNode, dataMap, detailXmlMap) : processPrintXmlNoPage(xmlNode, dataMap, detailXmlMap);
    }

    /**
     * @param configKeys   xml 配置的字段
     * @param detailXml    子表全部xml
     * @param detailRowXml 子表 行 xml
     * @param key          子表id
     * @param detailList   sql配置执行结果
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 子表xml
     */
    private String processDetailXml(Map<String, List<String>> configKeys, Map<String, String> detailXml, Map<String, String> detailRowXml, String key, List<Map> detailList, boolean replaceSymbol) {
        StringBuilder rowBulider = new StringBuilder();
        if (StringUtils.isBlank(key) || MapUtils.isEmpty(configKeys) || MapUtils.isEmpty(detailXml) || MapUtils.isEmpty(detailRowXml)) {
            return rowBulider.toString();
        }
        List<String> keys = configKeys.get(key);
        String rowXml = detailRowXml.get(key);
        if (CollectionUtils.isEmpty(keys) || StringUtils.isBlank(rowXml)) {
            return rowBulider.toString();
        }
        if (CollectionUtils.isEmpty(detailList)) {
            Map<String, String> nullMap = Maps.newHashMap();
            detailList.add(nullMap);
        }
        for (Map detail : detailList) {
            keys.forEach(column ->
                    detail.putIfAbsent(column, replaceSymbol ? symbol : "")
            );
            String xml = rowXml;
            Iterator iterator = detail.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                xml = StringToolUtils.replaceXml((String) entry.getKey(), entry.getValue(), xml);
            }
            rowBulider.append(xml);
        }
        return rowBulider.toString();
    }

    /**
     * @param configKeys   xml 配置的字段
     * @param detailXml    子表全部xml
     * @param detailRowXml 子表 行 xml
     * @param key          子表id
     * @param detailList   sql配置执行结果
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 多线程获取 子表xml
     */
    private String processDetailXmlMultiThread(Map<String, List<String>> configKeys, Map<String, String> detailXml, Map<String, String> detailRowXml, String key, List<Map> detailList) {
        StringBuilder rowBulider = new StringBuilder();
        if (CollectionUtils.isEmpty(detailList) || StringUtils.isBlank(key) || MapUtils.isEmpty(configKeys) || MapUtils.isEmpty(detailXml) || MapUtils.isEmpty(detailRowXml)) {
            return rowBulider.toString();
        }
        List<DetailPrintThread> detailPrintThreads = new ArrayList<>(detailList.size());
        for (Map detailData : detailList) {
            DetailPrintThread pageStringThread = new DetailPrintThread(configKeys.get(key), detailXml.get(key), detailRowXml.get(key), key, detailData);
            detailPrintThreads.add(pageStringThread);
        }
        threadEngine.excuteThread(detailPrintThreads, true);
        for (DetailPrintThread detailPrintThread : detailPrintThreads) {
            rowBulider.append(detailPrintThread.getDetailXmlString());
        }
        return rowBulider.toString();
    }

    /**
     * @param zyzyDysjDTOList
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 打印已有数据
     */
    @Override
    public String printDatas(List<ZrzyDysjDTO> zyzyDysjDTOList) {
        if (CollectionUtils.isEmpty(zyzyDysjDTOList)) {
            throw new AppException("配置结果集不能为空");
        }
        StringBuilder xmlBulider = new StringBuilder();
        StringBuilder pageBulider = new StringBuilder();
        Node xmlNode = XmlUtils.getXmlNodeByString(zyzyDysjDTOList.get(0).getDyzd());
        zyzyDysjDTOList.forEach(zrzyDysjDTO -> {
            String dyzd = zrzyDysjDTO.getDyzd();
            if (StringUtils.isBlank(dyzd)) {
                throw new AppException("xml配置文件不能为空");
            }
            Map dataMap = JSON.parseObject(zrzyDysjDTO.getDysj(), HashMap.class);
            Map detailMap = JSON.parseObject(zrzyDysjDTO.getDyzbsj(), HashMap.class);
            String page = "";
            if (zyzyDysjDTOList.size() > 1) {
                page = processOtherDataSourcePrintXml(xmlNode, dataMap, detailMap);
            } else {
                page = processOtherDataSourcePrintXmlNoPage(xmlNode, dataMap, detailMap);
            }
            pageBulider.append(page);

        });
        String xml = XmlUtils.processCharacterSet(XmlUtils.replacePageInXml(xmlNode, pageBulider.toString()));
        xmlBulider.append(xml);
        return xmlBulider.toString();
    }

    /**
     * @param page
     * @param dataMap
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public String parsePrintDatasXml(Node page, Map dataMap) {
        StringBuilder builder = new StringBuilder();
        boolean emptyMap = MapUtils.isEmpty(dataMap);
        if (emptyMap) {
            dataMap = Maps.newHashMap();
        }
        List<String> configKeys = Lists.newArrayList();
        configKeys = XmlUtils.queryDataConfigKeys(page, configKeys);
        String xml = XmlUtils.queryDataXml(page, builder);
        symbol = emptyMap ? symbol : "";
        /**空字符串替换null,添加未查询出的字段*/
        if (CollectionUtils.isNotEmpty(configKeys)) {
            for (String key : configKeys) {
                dataMap.putIfAbsent(key, symbol);
            }
        }
        Iterator iterator = dataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            xml = StringToolUtils.replaceXml((String) entry.getKey(), entry.getValue(), xml);
        }
        return xml;
    }

    /**
     * @param page
     * @param dataMap
     * @param detailDataMap
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public String processPageXml(Node page, Map dataMap, Map detailDataMap) {
        String datas = parsePrintDatasXml(page, dataMap);
        String detail = XmlUtils.parsePrintDetailXml(page, detailDataMap);
        StringBuilder pageStr = new StringBuilder();
        pageStr.append(datas);
        pageStr.append(detail);
        return pageStr.toString();
    }

    /**
     * @param page
     * @param dataMap
     * @param detailDataMap
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public String processOtherDataSourcePageXml(Node page, Map dataMap, Map detailDataMap) {
        String datas = parsePrintDatasXml(page, dataMap);
        String detail = XmlUtils.parseOtherDataSourcePrintDetailXml(page, detailDataMap);
        StringBuilder pageStr = new StringBuilder();
        if (StringUtils.isBlank(datas)) {
            datas = "<datas></datas>";
        }
        pageStr.append(datas);
        pageStr.append(detail);
        return pageStr.toString();
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public String processPrintXml(Node xml, Map dataMap, Map detailDataMap) {
        StringBuilder pages = new StringBuilder();
        if (xml == null) {
            return pages.toString();
        }
        xml.children().forEach(page -> {
            pages.append("<page>");
            String pageStr = processPageXml((Node) page, dataMap, detailDataMap);
            pages.append(pageStr);
            pages.append("</page>");
        });
        return pages.toString();
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    public String processPrintXmlNoPage(Node xml, Map dataMap, Map detailDataMap) {
        StringBuilder pages = new StringBuilder();
        if (xml == null) {
            return pages.toString();
        }
        xml.children().forEach(page -> {
            String pageStr = processPageXml((Node) page, dataMap, detailDataMap);
            pages.append(pageStr);
        });
        return pages.toString();
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 处理其他数据源 xml
     */
    @Override
    public String processOtherDataSourcePrintXml(Node xml, Map dataMap, Map detailDataMap) {
        StringBuilder pages = new StringBuilder();
        if (xml == null) {
            return pages.toString();
        }
        xml.children().forEach(page -> {
            pages.append("<page>");
            String pageStr = processOtherDataSourcePageXml((Node) page, dataMap, detailDataMap);
            pages.append(pageStr);
            pages.append("</page>");
        });
        return pages.toString();
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 处理其他数据源 xml
     */
    @Override
    public String processOtherDataSourcePrintXmlNoPage(Node xml, Map dataMap, Map detailDataMap) {
        StringBuilder pages = new StringBuilder();
        if (xml == null) {
            return pages.toString();
        }
        xml.children().forEach(page -> {
            String pageStr = processOtherDataSourcePageXml((Node) page, dataMap, detailDataMap);
            pages.append(pageStr);
        });
        return pages.toString();
    }

    /**
     * @param zrzyDysjPzVO 打印数据配置VO
     * @return 校验结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 校验打印配置
     */
    @Override
    public Object jypzxx(ZrzyDysjPzVO zrzyDysjPzVO) {
        if (null == zrzyDysjPzVO) {
            throw new MissingArgumentException("获得到的打印配置验证对象为null！");
        }
        // 打印验证参数
        List<Map<String, Object>> dycsMapList = zrzyDysjPzVO.getDycsMapList();
        if (CollectionUtils.isEmpty(dycsMapList)) {
            throw new MissingArgumentException("打印校验参数为null！");
        }
        // 打印主表信息
        BdcDysjPzDO bdcDysjPzDO = zrzyDysjPzVO.getBdcDysjPzDO();
        if (null == bdcDysjPzDO || StringUtils.isBlank(bdcDysjPzDO.getDylx()) || StringUtils.isBlank(bdcDysjPzDO.getDyzd())) {
            throw new MissingArgumentException("请检查主表配置信息，打印类型、打印字段xml模板不允许为空！");
        }
        // 打印类型
        String dylx = bdcDysjPzDO.getDylx();
        // 打印字段
        String dyzd = bdcDysjPzDO.getDyzd();
        // 打印子表信息
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = zrzyDysjPzVO.getBdcDysjZbPzDOList();

        // 组织最终对象
        List<BdcDysjPzDO> bdcDysjPzDOList = new ArrayList();
        bdcDysjPzDOList.add(bdcDysjPzDO);

        List<ZrzyDysjDTO> zyzyDysjDTOList = new ArrayList();
        for (Map<String, Object> dycsMap : dycsMapList) {
            // 获取主表查询数据 TODO
            Map datas = zrzyDysjPzService.queryPrintDatasList(dycsMap, BdcDyBeanEnum.BdcDyConfigService.getBeanName(), bdcDysjPzDOList);
            // 获取子表查询数据 TODO
            Multimap detail = zrzyDysjPzService.queryPrintDetailList(dycsMap, BdcDyBeanEnum.BdcDyConfigService.getBeanName(), bdcDysjZbPzDOList);

            ZrzyDysjDTO zrzyDysjDTO = new ZrzyDysjDTO();
            zrzyDysjDTO.setDylx(dylx);
            zrzyDysjDTO.setDyzd(dyzd);
            zrzyDysjDTO.setDysj(JSONObject.toJSONString(datas));
            zrzyDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));

            zyzyDysjDTOList.add(zrzyDysjDTO);
        }

        Map resultMap = new HashMap(2);
        resultMap.put("code", true);
        resultMap.put("result", this.printDatas(zyzyDysjDTOList));
        return resultMap;
    }
}
