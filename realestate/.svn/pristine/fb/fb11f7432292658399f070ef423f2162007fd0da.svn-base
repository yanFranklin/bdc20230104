package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.core.mapper.BdcConfigMapper;
import cn.gtmap.realestate.certificate.core.mapper.BdcGdsjPzMapper;
import cn.gtmap.realestate.certificate.service.BdcGdsjPzService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSjclDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import groovy.util.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/19
 * @description 归档业务类
 */
@Slf4j
@Service
@DependsOn("EnvironmentConfig")
public class BdcGdsjPzServiceImpl implements BdcGdsjPzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGdsjPzServiceImpl.class);

    @Autowired
    BdcGdsjPzMapper bdcGdsjPzMapper;
    @Autowired
    BdcConfigMapper bdcConfigMapper;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    StorageClientMatcher storageClient;

    @Value("${certificate.url.gdFileDownloadUrl:}")
    private String gdFileDownloadUrl;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    /**归档组合验证*/
    @Value("${gdzhyz:false}")
    private boolean gdzhyz;
    /**
     * 归档是否对包含"#"的坐落特殊处理
     */
    @Value("${certificate.gdsfclzl:true}")
    private boolean gdsfclzl;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    // 归档配置xml
    private static volatile Node gdxxInitNode;

    // 归档名称xml
    private static volatile Node dgmcInitNode;

    /**
     * @description 初始化归档配置xml和归档名称xml
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/21 9:07
     * @return void
     */
    @PostConstruct
    private void init() {
        String configPath = EnvironmentConfig.getEnvironment().getProperty("spring.config.location") == null ? "" : EnvironmentConfig.getEnvironment().getProperty("spring.config.location");
        LOGGER.info("------configPath:{}", configPath);

        String gdxxPath = configPath + CommonConstantUtils.XML_PATH_GDXX;
        String gdmcPath = configPath + CommonConstantUtils.XML_PATH_GDMC;
        gdxxInitNode = getBdcGdNode(gdxxPath);
        dgmcInitNode = getBdcGdNode(gdmcPath);
    }

    /**
     * @param gdpz
     * @return Map
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据配置表获取 归档信息
     */
    @Override
    public Map queryBdcGdxx(Map gdpz) {
        String sqls = String.valueOf(gdpz.get("GDSJY"));
        if (StringUtils.isEmpty(sqls)) {
            throw new AppException("数据源不能为空！");
        }
        if (sqls.matches(CommonConstantUtils.ZF_ZW_FH)) {
            throw new AppException("配置的归档数据源中不允许有中文字符！");
        }
        List<String> sqlList = Lists.newArrayList(sqls.split(CommonConstantUtils.ZF_YW_FH));
        List<Map> resultList = Lists.newArrayList();
        sqlList.forEach(sql -> {
            if (!sql.contains(CommonConstantUtils.SQL_CS_XMID)) {
                throw new AppException("配置的sql中缺少查询条件！");
            }
            gdpz.put("sql", sql);
            List<Map> result = bdcConfigMapper.executeConfigSql(gdpz);
            resultList.addAll(result);
        });
        Map result = Maps.newHashMap();
        resultList.forEach(map -> {
            result.putAll(map);
        });
        return result;
    }

    /**
     * @param gzlslid 工作流实例id
     * @return 归档xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档不动产信息
     */
    @Override
    public String gdBdcXx(String gzlslid, String xmid) {
        Boolean isDj = true;
        /**
         * 1.根据工作流id查询项目信息
         */
        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        List<BdcXmDO> bdcXmDOList = entityMapper.selectByExampleNotNull(example);
        List<BdcSlXmDO> bdcSlXmDOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcXmDOList)) {

            //尝试查询受理的
            bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isEmpty(bdcSlXmDOS)) {
                throw new AppException("此工作流对应的项目信息为空！");
            }
            isDj = false;
            bdcXmDOList = new ArrayList<>();
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOS) {
                BdcXmDO bdcXmDO = new BdcXmDO();
                BeanUtils.copyProperties(bdcSlXmDO,bdcXmDO);
                bdcXmDOList.add(bdcXmDO);
            }
        }
        //根据登记小类+档案归属地进行组合配置
        String dagsd = "";
        if (gdzhyz && isDj){
            //根据工作流id获取档案归属地
            Example example1 = new Example(BdcXmFbDO.class);
            example1.createCriteria().andEqualTo("gzlslid",gzlslid);
            List<BdcXmFbDO> bdcXmFbDOList = entityMapper.selectByExampleNotNull(example1);
            if (CollectionUtils.isEmpty(bdcXmFbDOList)){
                throw new AppException("此工作流对应的项目附表信息为空！");
            }
            dagsd = bdcXmFbDOList.get(0).getDagsd();
            if (StringUtils.isBlank(dagsd)){
                throw new AppException("项目附表中档案归属地不能为空！");
            }
        } else if (!isDj) {
            dagsd = bdcSlXmDOS.get(0).getDagsd();
            if (StringUtils.isBlank(dagsd)){
                throw new AppException("项目附表中档案归属地不能为空！");
            }
        }

        //根据xmid 排序
        bdcXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
        String djxl = bdcXmDOList.get(0).getDjxl();
        if (StringUtils.isEmpty(djxl)) {
            throw new AppException("项目表中的登记小类不能为空！");
        }
        Map configParam = Maps.newHashMap();
        configParam.put("djxl", djxl);
        Map gdpz = bdcGdsjPzMapper.queryBdcGdsjPz(configParam);
        if (MapUtils.isEmpty(gdpz)) {
            throw new AppException("当前登记小类：" + djxl + "，没有归档配置信息！");
        }
        if (Objects.isNull(dgmcInitNode)) {
            throw new AppException("未配置归档名称配置xml：" + CommonConstantUtils.XML_PATH_GDMC);
        }
        String gdlx = XmlUtils.getGdlxpz(dgmcInitNode);
        LOGGER.info("------归档类型:{}", gdlx);

        String type = "";
        if (StringUtils.equals("qllx", gdlx)) {
            type = gdlx + "=" + bdcXmDOList.get(0).getQllx();
        } else if (StringUtils.equals("djxl", gdlx)) {
            type = gdlx + "=" + bdcXmDOList.get(0).getDjxl();
        } else if (StringUtils.equals("bdclx", gdlx)) {
            type = gdlx + "=" + bdcXmDOList.get(0).getBdclx();
        } else if (isDj && StringUtils.equals("djlx", gdlx)) {
            type = gdlx + "=" + bdcXmDOList.get(0).getDjxl();
        } else if (gdzhyz){
            type = gdlx + "=" + (dagsd+bdcXmDOList.get(0).getDjxl());
        }

        LOGGER.info("------type:{}", type);
        // 归档配置xml
        Node gdxxNode = null;
        if (Objects.isNull(gdxxInitNode)) {
            throw new AppException("未配置归档信息xml：" + CommonConstantUtils.XML_PATH_GDXX);
        }
        if (gdxxInitNode.children().size() >= 2) {
            gdxxNode = XmlUtils.getNodeByType(gdxxInitNode, type);
        }
        LOGGER.info("------归档配置xml:{}", gdxxNode);

        if (Objects.isNull(gdxxNode)) {
            throw new AppException("xml中未设置type为" + type + "的模板!");
        }

        /**登记信息是否归多条*/
        int djxxgdt = Integer.parseInt(String.valueOf(gdpz.get(CommonConstantUtils.GD_DJXXGDT)));
        /**doc xml字符串*/
        StringBuilder xmlBulider = new StringBuilder();
        /**如果登记信息归一条，并且xmid不为空，则归档此xmid对应的项目信息*/
        if (djxxgdt == 0 && StringUtils.isNotBlank(xmid)) {
            bdcXmDOList = bdcXmDOList
                    .stream()
                    .filter(bdcXmDO -> StringUtils.equals(xmid, bdcXmDO.getXmid()))
                    .collect(Collectors.toList());
        }

        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(bdcXmDO);
            String gdlxValue = String.valueOf(jsonObject.get(gdlx));
            //若gdlxValue为空并且gdzhyz配置为true则为登记小类与档案归属地进行归档
            if ((StringUtils.isBlank(gdlxValue) || "null".equals(gdlxValue)) && gdzhyz){
                gdlxValue = dagsd+bdcXmDO.getDjxl();
            }
            String archiveName = XmlUtils.getArchiveModelName(gdlxValue, dgmcInitNode);
            gdpz.put("xmid", bdcXmDO.getXmid());
            gdxxNode = XmlUtils.setXmlArchiveName(gdxxNode, archiveName);
            Map bdcGdxx = queryBdcGdxx(gdpz);
            // 根据配置判断是否需要对包含#的坐落特殊处理
            if(gdsfclzl){
                // 处理坐落中的# 对坐落encode
                for (Object key : bdcGdxx.keySet()) {
                    if (bdcGdxx.containsKey(key) && bdcGdxx.get(key) != null && bdcGdxx.get(key).toString().indexOf("#") > 0) {
                        String value = UriEncoder.encode(bdcGdxx.get(key).toString());
                        bdcGdxx.put(key, value);
                    }
                }
            }
            String gdxml = XmlUtils.processGdXml(gdxxNode, bdcGdxx, queryFiles(gzlslid));
            LOGGER.info("流程实例id{}归档xml信息{}", gzlslid, JSON.toJSONString(gdxml));
            xmlBulider.append(gdxml);
            if (djxxgdt == 0) {
                break;
            }
        }
        String result = XmlUtils.processCharacterSet(XmlUtils.replacePageInXml(gdxxNode, xmlBulider.toString()));
        LOGGER.info("归档xml==" + result);
        return result;
    }

    /**
     * @param path 路径
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据路径获取xml
     */
    @Override
    public Node getBdcGdNode(String path) {
        Node gdNode = null;
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        try (InputStream inputStream = fileSystemResource.getInputStream()) {
            gdNode = XmlUtils.getXmlNodeByInputStream(inputStream);
            LOGGER.info("xml配置内容====" + gdNode.text());
        } catch (IOException e) {
            // 未配置归档信息和归档名称文件返回空，在调用接口进行非空判断
            return null;
        }
        return gdNode;
    }

    /**
     * @param bdcXmDO
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取原文归档xml
     */
    @Override
    public List<Map> queryBdcGdYw(BdcXmDO bdcXmDO) {
        if (bdcXmDO == null || StringUtils.isBlank(bdcXmDO.getXmid())) {
            return Lists.newArrayList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(bdcXmDO.getGzlslid());
        LOGGER.info("受理附件信息对应的 gzlslid：{}", bdcXmDO.getGzlslid());
        List<Map> docList = Lists.newArrayList();
        bdcSlSjclDOList.forEach(bdcSlSjclDO -> {
            StorageDto storageDto = storageClient.findById(bdcSlSjclDO.getWjzxid());
            BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
            BeanUtils.copyProperties(bdcSlSjclDO, bdcSlSjclDTO);
            bdcSlSjclDTO.setXmid(bdcXmDO.getXmid());
            if (storageDto != null) {
                bdcSlSjclDTO.setSjcldz(storageDto.getDownUrl());
            }
            Map<String, Object> sjcl = objectMapper.convertValue(bdcSlSjclDTO, Map.class);
            Map<String, Object> sjclMap = sjcl.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().toUpperCase(), entry -> entry.getValue() == null ? "" : entry.getValue()));
            docList.add(sjclMap);
        });
        return docList;
    }

    public List<Map> queryFiles(String gzlslid) {
        List<StorageDto> storageDtoList = storageClient.queryMenus("clientId", gzlslid, null, null, 1, null, null, null);
        List<BdcSlSjclDO> listSjclDO = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(gzlslid);
        LOGGER.info("受理附件信息对应的 gzlslid：{}", gzlslid);
        LOGGER.info("查询大云附件流程实例id{}，查询到所有附件信息包含文件夹{}", gzlslid, JSON.toJSONString(storageDtoList));

        Map<String, List<BdcSlSjclDO>> sjclDOMap = listSjclDO.stream().collect(Collectors.groupingBy(BdcSlSjclDO::getWjzxid));
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map> docList = Lists.newArrayList();
        for (StorageDto storageDto : storageDtoList) {
            List<StorageDto> allChildrenList = new ArrayList<>();
            if (Objects.equals(storageDto.getType(), 0)) {
                getChildrenList(allChildrenList, storageDto.getChildren());
            }
            BdcSlSjclDTO bdcSlSjclDTO = convertMenus(storageDto, sjclDOMap);
            if (Objects.nonNull(bdcSlSjclDTO) && StringUtils.isNotBlank(bdcSlSjclDTO.getSjcldz()) && CollectionUtils.isNotEmpty(allChildrenList)) {
                List<BdcSlSjclDTO> bdcSlSjclDTOChildrenList = new ArrayList<>(allChildrenList.size());
                int xh = 0;
                for (StorageDto entity : allChildrenList) {
                    BdcSlSjclDTO sjcl = convertFiles(entity, sjclDOMap, xh++);
                    if (Objects.nonNull(sjcl) && StringUtils.isNotBlank(sjcl.getSjcldz())) {
                        bdcSlSjclDTOChildrenList.add(sjcl);
                    }
                }
                if (CollectionUtils.isNotEmpty(bdcSlSjclDTOChildrenList)) {
                    bdcSlSjclDTO.setChildrenList(bdcSlSjclDTOChildrenList);
                }
            }

            Map<String, Object> sjcl = objectMapper.convertValue(bdcSlSjclDTO, Map.class);
            Map<String, Object> sjclMap = sjcl.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().toUpperCase(), entry -> entry.getValue() == null ? "" : entry.getValue()));
            docList.add(sjclMap);
        }
        return docList;

    }

    private void getChildrenList(List<StorageDto> allChildrenList, List<StorageDto> storageDtoList) {
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            for (StorageDto storageDto : storageDtoList) {
                if (Objects.equals(0, storageDto.getType())) {
                    getChildrenList(allChildrenList, storageDto.getChildren());
                } else {
                    allChildrenList.add(storageDto);
                }
            }
        }
    }

    private BdcSlSjclDTO convertMenus(StorageDto storageDto, Map<String, List<BdcSlSjclDO>> sjclDOMap) {
        if (StringUtils.isBlank(storageDto.getId())) {
            return null;
        }
        // 收件类型字典
        List<Map> sjlxMapList = bdcZdFeignService.queryBdcZd("sjlx");
        BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
        if (MapUtils.isNotEmpty(sjclDOMap) && sjclDOMap.containsKey(storageDto.getId())) {
            BdcSlSjclDO bdcSlSjclDO = sjclDOMap.get(storageDto.getId()).get(0);
            // 材料名称
            bdcSlSjclDTO.setClmc(bdcSlSjclDO.getClmc());
            // 序号
            bdcSlSjclDTO.setXh(bdcSlSjclDO.getXh());
            // 页数
            bdcSlSjclDTO.setYs(bdcSlSjclDO.getYs());
            // 下载地址
            bdcSlSjclDTO.setSjcldz(String.format(gdFileDownloadUrl, storageDto.getId()));
            // 默认份数
            bdcSlSjclDTO.setMrfs(bdcSlSjclDO.getMrfs());
            // 收件类型
            bdcSlSjclDTO.setSjlx(bdcSlSjclDO.getSjlx());
            // 收件类型名称
            bdcSlSjclDTO.setSjlxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcSlSjclDO.getSjlx(), sjlxMapList));
        }
        return bdcSlSjclDTO;
    }

    private BdcSlSjclDTO convertFiles(StorageDto storageDto, Map<String, List<BdcSlSjclDO>> sjclDOMap, int xh) {
        if (StringUtils.isBlank(storageDto.getId())) {
            return null;
        }

        if (MapUtils.isNotEmpty(sjclDOMap) && sjclDOMap.containsKey(storageDto.getId())) {
            return convertMenus(storageDto, sjclDOMap);
        } else {
            BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
            String clmc = storageDto.getName();
            if(StringUtils.isNotBlank(clmc) && clmc.indexOf(".") > 0){
                clmc = clmc.substring(0, clmc.indexOf("."));
            }
            // 材料名称
            bdcSlSjclDTO.setClmc(clmc);
            // 序号
            bdcSlSjclDTO.setXh(xh);
            // 页数
            bdcSlSjclDTO.setYs(1);
            // 下载地址
            bdcSlSjclDTO.setSjcldz(String.format(gdFileDownloadUrl, storageDto.getId()));
            return bdcSlSjclDTO;
        }
    }
}
