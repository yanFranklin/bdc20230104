package cn.gtmap.realestate.natural.service.impl;


import cn.gtmap.realestate.common.core.annotations.ZsDyZt;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyDysjDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyPrintDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.natural.core.service.ZrzyPrintCoreService;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import cn.gtmap.realestate.natural.service.*;
import cn.gtmap.realestate.natural.util.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ZrzyPrintServiceImpl implements ZrzyPrintService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyPrintServiceImpl.class);

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

    @Autowired
    ZrzyPrintCoreService zrzyPrintCoreService;

    @Autowired
    ZrzyZdService zrzyZdService;

    /**
     * 正则匹配：匹配 fwlx@1 这样的格式的数据
     */
    private static Pattern FIND_PATTERN = Pattern.compile("[a-zA-Z]+\\@[0-9]+");

    /**
     * @param zrzyPrintDTO 证书打印对象
     * @return 单个证书的打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印xml
     */
    @Override
    @ZsDyZt(paramName = Constants.ZSID)
    public String singleZsPrintXml(ZrzyPrintDTO zrzyPrintDTO) {
        return generatePrintData(zrzyPrintDTO.getGzlslid(), zrzyPrintDTO.getDylx(), zrzyPrintDTO.getXmid());
    }


    /**
     * @param gzlslid 工作流实例id
     * @param dylx    打印类型
     * @return String  获取受理库收件单打印数据
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    public String generatePrintData(String gzlslid,
                                    String dylx,
                                    String xmid) {

        String xml = "";
        List<ZrzyXmDO> zrzyXmDOList = zrzyXmService.queryAllZrzyXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(zrzyXmDOList)) {
            List<ZrzyDysjDTO> zrzyDysjDTOS = new ArrayList<>();
            //根据xmid排序，批量流程取最新的项目
            zrzyXmDOList.sort(Comparator.comparing(ZrzyXmDO::getXmid));
            ZrzyDysjDTO zrzyDysjDTO;
            //单个流程和批量流程打印一份
            for (ZrzyXmDO zrzyXmDO : zrzyXmDOList) {
                zrzyDysjDTO = generateXml(dylx, zrzyXmDO);
                zrzyDysjDTOS.add(zrzyDysjDTO);
            }
            LOGGER.info("打印类型:{},受理编号:{}", dylx, zrzyXmDOList.get(0).getSlbh());
            xml = zrzyPrintCoreService.printDatas(zrzyDysjDTOS);
            if (StringUtils.isNotBlank(xml)) {
                xml = replaceZdCodeToMc(xml);
            }
        }
        LOGGER.info("打印类型{},生成的数据源xml{}", dylx, xml);
        return xml;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织数据源(数据均来自受理库
     */
    private ZrzyDysjDTO generateXml(String dylx, ZrzyXmDO zrzyXmDO) {
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        Map configParam = Maps.newHashMap();
//        configParam.put("djlxmc", "");
//        configParam.put("djlx", "");
        configParam.put("xmid", zrzyXmDO.getXmid());
        configParam.put("gzlslid", zrzyXmDO.getGzlslid());

        ZrzyZsQO zrzyZsQO = new ZrzyZsQO();
        zrzyZsQO.setGzlslid(zrzyXmDO.getGzlslid());
        List<ZrzyZsDO> zrzyZsDOList = zrzyZsService.listZryzZs(zrzyZsQO);
        if(CollectionUtils.isNotEmpty(zrzyZsDOList)){
            configParam.put("zsid", zrzyZsDOList.get(0).getZsid());
        }
        LOGGER.info("打印类型:{},受理编号:{},受理数据打印入参{}", dylx, zrzyXmDO.getSlbh(), configParam);
        //执行sql从数据库获取需要打印的数据
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = zrzyDypzService.listBdcDysjPz(bdcDysjPzDO);
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = zrzyDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
        ZrzyDysjDTO zrzyDysjDTO = new ZrzyDysjDTO();
        Map datas = zrzyDysjPzService.queryPrintDatasList(configParam,
                "zrzyConfigMapper",
                bdcDysjPzDOList);
        Multimap detail = zrzyDysjPzService.queryPrintDetailList(configParam,
                "zrzyConfigMapper",
                bdcDysjZbPzDOList);
        zrzyDysjDTO.setDylx(dylx);
        zrzyDysjDTO.setDyzd(bdcDysjPzDOList.get(0).getDyzd());
        zrzyDysjDTO.setDysj(JSONObject.toJSONString(datas));
        zrzyDysjDTO.setDyzbsj(JSONObject.toJSONString(detail));
        return zrzyDysjDTO;
    }


    // 替换数据中所有为字典项dm的值
    private String replaceZdCodeToMc(String xmlStr) {
        //获取登记的所有字典项
        Map<String, List<Map>> allDjZdMap = zrzyZdService.listZrzyzd();
        // 正则表达式，用于匹配fwlx@1这样格式的数据
        Matcher matcher = FIND_PATTERN.matcher(xmlStr);
        while (matcher.find()) {
            String matchValue = matcher.group();
            String[] matchValueAry = matchValue.split("\\@");
            String zdType = matchValueAry[0];
            String zdKey = matchValueAry[1];
            if (CollectionUtils.isEmpty(allDjZdMap.get(zdType))) {
                continue;
            }
            String zdMc = StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(zdKey), allDjZdMap.get(zdType));
            xmlStr = xmlStr.replace(matchValue, zdMc);
        }

        //去除为空的字典项，列入 fwlx@ ,
        xmlStr = xmlStr.replaceAll("[a-zA-z]+\\@", "");
        return xmlStr;
    }
}
