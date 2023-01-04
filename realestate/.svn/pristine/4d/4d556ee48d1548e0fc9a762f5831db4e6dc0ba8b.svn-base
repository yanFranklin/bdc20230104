package cn.gtmap.realestate.register.service.impl;


import cn.gtmap.realestate.common.config.Encrypt.AESutil;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.enums.BdcDyBeanEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDysjPzVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import cn.gtmap.realestate.register.core.thread.DetailPrintThread;
import cn.gtmap.realestate.register.core.thread.PageStringThread;
import cn.gtmap.realestate.register.service.BdcDypzService;
import cn.gtmap.realestate.register.service.BdcPrintService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/11
 * @description fr3 打印业务实现类
 */
@Service
public class BdcPrintServiceImpl implements BdcPrintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPrintServiceImpl.class);
    /**
     * 设置最大打印数据量，超出采用多线程
     */
    private static final Integer MAX_NUM = 50;

    /**
     * 设置xml字符串正则匹配表达式
     */
    private static final String REGEXP = "<|>";

    /**
     * 预编译
     */
    private static final Pattern PATTERN = Pattern.compile(REGEXP);

    @Autowired
    BdcDysjPzService bdcDysjPzService;
    @Autowired
    BdcDypzService bdcDypzService;
    @Autowired
    ThreadEngine threadEngine;
    @Autowired
    AESutil aeSutil;
    /**
     * 替换子表空值：子表id
     */
    @Value("${print.replaceNull.zbid}")
    private String zbids;
    /**
     * 替换子表空值：替换符号
     */
    @Value("${print.replaceNull.symbol}")
    private String symbol;

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
            List<BdcDysjPzDO> bdcDysjPzDOS = bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
            if (CollectionUtils.isEmpty(bdcDysjPzDOS)) {
                throw new AppException("打印配置主表不能为空");
            }
            for (Map param : value) {
                encodeParam(bdcDysjPzDOS.get(0),param);
            }
            /**
             * 获取子表配置
             */
            BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
            bdcDysjZbPzDO.setDylx(key);
            List<BdcDysjZbPzDO> bdcDysjZbPzDOS = bdcDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
            // 如果表格属性不为空，将其拼接在子表id后面
            for(BdcDysjZbPzDO bdcDysjZbPz: bdcDysjZbPzDOS){
                if(StringUtils.isNotBlank(bdcDysjZbPz.getDyzbsx()) && null != bdcDysjZbPz.getDyzbid()){
                    String zbid = bdcDysjZbPz.getDyzbid() + bdcDysjZbPz.getDyzbsx();
                    bdcDysjZbPz.setDyzbid(zbid);
                }
            }
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
        Map dataMap = bdcDysjPzService.queryPrintDatasList(configParam, "bdcRegisterConfigMapper", bdcDysjPzDOS);

        /**
         * 解密查询结果
         */
        decodeResult(bdcDysjPzDOS.get(0),dataMap);

        /**
         * 转义主表大于号，小于号
         */
        if (CollectionUtils.isNotEmpty(dataMap.values())) {
            dataMap.forEach((key, value) -> {
                if (value != null){
                    StringBuffer stringBuffer = replace(value.toString());
                    dataMap.replace(key,stringBuffer);
                }
            });
        }

        /**
         * 根据子表配置查询数据
         */
        Multimap<String, List<Map>> detailMap = bdcDysjPzService.queryPrintDetailList(configParam, "bdcRegisterConfigMapper", bdcDysjZbPzDOS);

        /**
         * 解密查询结果
         */
        for (Map.Entry<String, List<Map>> entry : detailMap.entries()) {
            for (Map map : entry.getValue()) {
                decodeResult(bdcDysjPzDOS.get(0),map);
            }
        }

        /**
         * 转义子表大于号，小于号
         */
         detailMap.forEach((key, value) -> {
             value.forEach(datamap -> {
                 datamap.forEach((datamapKey, datamapValue) ->{
                     if (datamapValue != null){
                         StringBuffer stringBuffer = replace(datamapValue.toString());
                         datamap.replace(datamapKey,stringBuffer);
                     }
                 });
             });
         });

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
        String rowXml=detailRowXml.get(key);
        if (CollectionUtils.isEmpty(keys) || StringUtils.isBlank(rowXml)) {
            return rowBulider.toString();
        }
        if (CollectionUtils.isEmpty(detailList)) {
            Map<String, String> nullMap = Maps.newHashMap();
            detailList.add(nullMap);
        }
        for(Map detail:detailList){
            keys.forEach(column->
                    detail.putIfAbsent(column,replaceSymbol?symbol:"")
            );
            String xml=rowXml;
            Iterator iterator=detail.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry entry= (Map.Entry) iterator.next();
                xml=StringToolUtils.replaceXml((String) entry.getKey(),entry.getValue(),xml);
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
     * @param bdcDysjDTOList
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 打印已有数据
     */
    @Override
    public String printDatas(List<BdcDysjDTO> bdcDysjDTOList) {
        if (CollectionUtils.isEmpty(bdcDysjDTOList)) {
            throw new AppException("配置结果集不能为空");
        }
        StringBuilder xmlBulider = new StringBuilder();
        StringBuilder pageBulider = new StringBuilder();
        Node xmlNode = XmlUtils.getXmlNodeByString(bdcDysjDTOList.get(0).getDyzd());
        bdcDysjDTOList.forEach(bdcDysjDTO -> {
            String dyzd = bdcDysjDTO.getDyzd();
            if (StringUtils.isBlank(dyzd)) {
                throw new AppException("xml配置文件不能为空");
            }
            Map dataMap = JSON.parseObject(bdcDysjDTO.getDysj(), HashMap.class);
            Map detailMap = JSON.parseObject(bdcDysjDTO.getDyzbsj(), HashMap.class);
            String page = "";
            if (bdcDysjDTOList.size() > 1) {
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
        boolean emptyMap=MapUtils.isEmpty(dataMap);
        if(emptyMap){
            dataMap= Maps.newHashMap();
        }
        List<String> configKeys = Lists.newArrayList();
        configKeys=XmlUtils.queryDataConfigKeys(page,configKeys);
        String xml=XmlUtils.queryDataXml(page,builder);
        symbol=emptyMap?symbol:"";
        /**空字符串替换null,添加未查询出的字段*/
        if(CollectionUtils.isNotEmpty(configKeys)){
            for (String key:configKeys){
                dataMap.putIfAbsent(key,symbol);
            }
        }
        Iterator iterator=dataMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            xml=StringToolUtils.replaceXml((String) entry.getKey(),entry.getValue(),xml);
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
        if(StringUtils.isBlank(datas)){
            datas="<datas></datas>";
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
        if(xml==null){
            return pages.toString();
        }
        xml.children().forEach(page->{
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
        if(xml==null){
            return pages.toString();
        }
        xml.children().forEach(page->{
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
        if(xml==null){
            return pages.toString();
        }
        xml.children().forEach(page->{
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
        if(xml==null){
            return pages.toString();
        }
        xml.children().forEach(page->{
            String pageStr = processOtherDataSourcePageXml((Node) page, dataMap, detailDataMap);
            pages.append(pageStr);
        });
        return pages.toString();
    }

    /**
     * @param bdcDysjPzVO 打印数据配置VO
     * @return 校验结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 校验打印配置
     */
    @Override
    public Object jypzxx(BdcDysjPzVO bdcDysjPzVO) {
        if (null == bdcDysjPzVO) {
            throw new MissingArgumentException("获得到的打印配置验证对象为null！");
        }
        // 打印验证参数
        List<Map<String, Object>> dycsMapList = bdcDysjPzVO.getDycsMapList();
        if (CollectionUtils.isEmpty(dycsMapList)) {
            throw new MissingArgumentException("打印校验参数为null！");
        }
        // 打印主表信息
        BdcDysjPzDO bdcDysjPzDO = bdcDysjPzVO.getBdcDysjPzDO();
        if (null == bdcDysjPzDO || StringUtils.isBlank(bdcDysjPzDO.getDylx()) || StringUtils.isBlank(bdcDysjPzDO.getDyzd())) {
            throw new MissingArgumentException("请检查主表配置信息，打印类型、打印字段xml模板不允许为空！");
        }
        // 打印类型
        String dylx = bdcDysjPzDO.getDylx();
        // 打印字段
        String dyzd = bdcDysjPzDO.getDyzd();
        // 打印子表信息
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDysjPzVO.getBdcDysjZbPzDOList();
        // 如果表格属性不为空，将其拼接在子表id后面
        for(BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjZbPzDOList){
            if(StringUtils.isNotBlank(bdcDysjZbPzDO.getDyzbsx()) && null != bdcDysjZbPzDO.getDyzbid()){
                String zbid = bdcDysjZbPzDO.getDyzbid() + bdcDysjZbPzDO.getDyzbsx();
                bdcDysjZbPzDO.setDyzbid(zbid);
            }
        }

        // 组织最终对象
        List<BdcDysjPzDO> bdcDysjPzDOList = new ArrayList();
        bdcDysjPzDOList.add(bdcDysjPzDO);

        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList();
        for(Map<String, Object> dycsMap : dycsMapList){
            // 获取主表查询数据 TODO
            Map datas = bdcDysjPzService.queryPrintDatasList(dycsMap, BdcDyBeanEnum.BdcDyConfigService.getBeanName(), bdcDysjPzDOList);
            // 获取子表查询数据 TODO
            Multimap detail = bdcDysjPzService.queryPrintDetailList(dycsMap, BdcDyBeanEnum.BdcDyConfigService.getBeanName(), bdcDysjZbPzDOList);

            BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
            bdcDysjDTO.setDylx(dylx);
            bdcDysjDTO.setDyzd(dyzd);
            bdcDysjDTO.setDysj(JSONObject.toJSONString(datas));
            bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));

            bdcDysjDTOList.add(bdcDysjDTO);
        }

        Map resultMap = new HashMap(2);
        resultMap.put("code", true);
        resultMap.put("result", this.printDatas(bdcDysjDTOList));
        return resultMap;
    }


    /**
     *
     * @param bdcDysjPzDO
     * @param param
     */
    public void encodeParam(BdcDysjPzDO bdcDysjPzDO,Map param){
        String jmcs = bdcDysjPzDO.getJmcs();
        if(StringUtils.isEmpty(jmcs)){
            return;
        }
        String[] split = jmcs.split(CommonConstantUtils.ZF_YW_DH);
        for (String cs : split) {
            if(param.containsKey(cs)
                    && Objects.nonNull(param.get(cs))
            ){
                String encrypt = aeSutil.encrypt((String) param.get(cs));
                param.put(cs , encrypt);
            }
        }
    }


    /**
     * 对结果进行解密
     * @param bdcDysjPzDO
     * @param param
     */
    public void decodeResult(BdcDysjPzDO bdcDysjPzDO,Map data){
        String jmjg = bdcDysjPzDO.getJmjg();
        if(StringUtils.isEmpty(jmjg)){
            return;
        }
        String[] split = jmjg.split(CommonConstantUtils.ZF_YW_DH);
        for (String jg : split) {
            if(data.containsKey(jg)
                    && Objects.nonNull(data.get(jg))
            ){
                String decrypt = aeSutil.decrypt((String) data.get(jg));
                data.put(jg , decrypt);
            }
        }
    }

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @param str
     * @description 对xml字符串中的"<",">"替换成转义字符 "<![CDATA[<]]>","<![CDATA[>]]>"
     */
    public StringBuffer replace(String str) {
        Map replacements = new HashMap(2) {{
            put("<", "<![CDATA[<]]>");
            put(">", "<![CDATA[>]]>");
        }};

        StringBuffer stringBuffer = new StringBuffer();
        Matcher m = PATTERN.matcher(str);
        while (m.find()) {
            m.appendReplacement(stringBuffer, (String) replacements.get(m.group()));
        }
        m.appendTail(stringBuffer);

        return stringBuffer;
    }
}
