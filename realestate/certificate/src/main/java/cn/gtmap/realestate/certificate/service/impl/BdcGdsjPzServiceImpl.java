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
 * @description ???????????????
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

    /**??????????????????*/
    @Value("${gdzhyz:false}")
    private boolean gdzhyz;
    /**
     * ?????????????????????"#"?????????????????????
     */
    @Value("${certificate.gdsfclzl:true}")
    private boolean gdsfclzl;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    // ????????????xml
    private static volatile Node gdxxInitNode;

    // ????????????xml
    private static volatile Node dgmcInitNode;

    /**
     * @description ?????????????????????xml???????????????xml
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
     * @description ????????????????????? ????????????
     */
    @Override
    public Map queryBdcGdxx(Map gdpz) {
        String sqls = String.valueOf(gdpz.get("GDSJY"));
        if (StringUtils.isEmpty(sqls)) {
            throw new AppException("????????????????????????");
        }
        if (sqls.matches(CommonConstantUtils.ZF_ZW_FH)) {
            throw new AppException("??????????????????????????????????????????????????????");
        }
        List<String> sqlList = Lists.newArrayList(sqls.split(CommonConstantUtils.ZF_YW_FH));
        List<Map> resultList = Lists.newArrayList();
        sqlList.forEach(sql -> {
            if (!sql.contains(CommonConstantUtils.SQL_CS_XMID)) {
                throw new AppException("?????????sql????????????????????????");
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
     * @param gzlslid ???????????????id
     * @return ??????xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ?????????????????????
     */
    @Override
    public String gdBdcXx(String gzlslid, String xmid) {
        Boolean isDj = true;
        /**
         * 1.???????????????id??????????????????
         */
        Example example = new Example(BdcXmDO.class);
        example.createCriteria().andEqualTo("gzlslid", gzlslid);
        List<BdcXmDO> bdcXmDOList = entityMapper.selectByExampleNotNull(example);
        List<BdcSlXmDO> bdcSlXmDOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(bdcXmDOList)) {

            //?????????????????????
            bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if(CollectionUtils.isEmpty(bdcSlXmDOS)) {
                throw new AppException("??????????????????????????????????????????");
            }
            isDj = false;
            bdcXmDOList = new ArrayList<>();
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOS) {
                BdcXmDO bdcXmDO = new BdcXmDO();
                BeanUtils.copyProperties(bdcSlXmDO,bdcXmDO);
                bdcXmDOList.add(bdcXmDO);
            }
        }
        //??????????????????+?????????????????????????????????
        String dagsd = "";
        if (gdzhyz && isDj){
            //???????????????id?????????????????????
            Example example1 = new Example(BdcXmFbDO.class);
            example1.createCriteria().andEqualTo("gzlslid",gzlslid);
            List<BdcXmFbDO> bdcXmFbDOList = entityMapper.selectByExampleNotNull(example1);
            if (CollectionUtils.isEmpty(bdcXmFbDOList)){
                throw new AppException("????????????????????????????????????????????????");
            }
            dagsd = bdcXmFbDOList.get(0).getDagsd();
            if (StringUtils.isBlank(dagsd)){
                throw new AppException("?????????????????????????????????????????????");
            }
        } else if (!isDj) {
            dagsd = bdcSlXmDOS.get(0).getDagsd();
            if (StringUtils.isBlank(dagsd)){
                throw new AppException("?????????????????????????????????????????????");
            }
        }

        //??????xmid ??????
        bdcXmDOList.sort((o1, o2) -> o1.getXmid().compareTo(o2.getXmid()));
        String djxl = bdcXmDOList.get(0).getDjxl();
        if (StringUtils.isEmpty(djxl)) {
            throw new AppException("??????????????????????????????????????????");
        }
        Map configParam = Maps.newHashMap();
        configParam.put("djxl", djxl);
        Map gdpz = bdcGdsjPzMapper.queryBdcGdsjPz(configParam);
        if (MapUtils.isEmpty(gdpz)) {
            throw new AppException("?????????????????????" + djxl + "??????????????????????????????");
        }
        if (Objects.isNull(dgmcInitNode)) {
            throw new AppException("???????????????????????????xml???" + CommonConstantUtils.XML_PATH_GDMC);
        }
        String gdlx = XmlUtils.getGdlxpz(dgmcInitNode);
        LOGGER.info("------????????????:{}", gdlx);

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
        // ????????????xml
        Node gdxxNode = null;
        if (Objects.isNull(gdxxInitNode)) {
            throw new AppException("?????????????????????xml???" + CommonConstantUtils.XML_PATH_GDXX);
        }
        if (gdxxInitNode.children().size() >= 2) {
            gdxxNode = XmlUtils.getNodeByType(gdxxInitNode, type);
        }
        LOGGER.info("------????????????xml:{}", gdxxNode);

        if (Objects.isNull(gdxxNode)) {
            throw new AppException("xml????????????type???" + type + "?????????!");
        }

        /**???????????????????????????*/
        int djxxgdt = Integer.parseInt(String.valueOf(gdpz.get(CommonConstantUtils.GD_DJXXGDT)));
        /**doc xml?????????*/
        StringBuilder xmlBulider = new StringBuilder();
        /**????????????????????????????????????xmid????????????????????????xmid?????????????????????*/
        if (djxxgdt == 0 && StringUtils.isNotBlank(xmid)) {
            bdcXmDOList = bdcXmDOList
                    .stream()
                    .filter(bdcXmDO -> StringUtils.equals(xmid, bdcXmDO.getXmid()))
                    .collect(Collectors.toList());
        }

        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(bdcXmDO);
            String gdlxValue = String.valueOf(jsonObject.get(gdlx));
            //???gdlxValue????????????gdzhyz?????????true????????????????????????????????????????????????
            if ((StringUtils.isBlank(gdlxValue) || "null".equals(gdlxValue)) && gdzhyz){
                gdlxValue = dagsd+bdcXmDO.getDjxl();
            }
            String archiveName = XmlUtils.getArchiveModelName(gdlxValue, dgmcInitNode);
            gdpz.put("xmid", bdcXmDO.getXmid());
            gdxxNode = XmlUtils.setXmlArchiveName(gdxxNode, archiveName);
            Map bdcGdxx = queryBdcGdxx(gdpz);
            // ???????????????????????????????????????#?????????????????????
            if(gdsfclzl){
                // ??????????????????# ?????????encode
                for (Object key : bdcGdxx.keySet()) {
                    if (bdcGdxx.containsKey(key) && bdcGdxx.get(key) != null && bdcGdxx.get(key).toString().indexOf("#") > 0) {
                        String value = UriEncoder.encode(bdcGdxx.get(key).toString());
                        bdcGdxx.put(key, value);
                    }
                }
            }
            String gdxml = XmlUtils.processGdXml(gdxxNode, bdcGdxx, queryFiles(gzlslid));
            LOGGER.info("????????????id{}??????xml??????{}", gzlslid, JSON.toJSONString(gdxml));
            xmlBulider.append(gdxml);
            if (djxxgdt == 0) {
                break;
            }
        }
        String result = XmlUtils.processCharacterSet(XmlUtils.replacePageInXml(gdxxNode, xmlBulider.toString()));
        LOGGER.info("??????xml==" + result);
        return result;
    }

    /**
     * @param path ??????
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????????????????xml
     */
    @Override
    public Node getBdcGdNode(String path) {
        Node gdNode = null;
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        try (InputStream inputStream = fileSystemResource.getInputStream()) {
            gdNode = XmlUtils.getXmlNodeByInputStream(inputStream);
            LOGGER.info("xml????????????====" + gdNode.text());
        } catch (IOException e) {
            // ???????????????????????????????????????????????????????????????????????????????????????
            return null;
        }
        return gdNode;
    }

    /**
     * @param bdcXmDO
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????????????????xml
     */
    @Override
    public List<Map> queryBdcGdYw(BdcXmDO bdcXmDO) {
        if (bdcXmDO == null || StringUtils.isBlank(bdcXmDO.getXmid())) {
            return Lists.newArrayList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(bdcXmDO.getGzlslid());
        LOGGER.info("??????????????????????????? gzlslid???{}", bdcXmDO.getGzlslid());
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
        LOGGER.info("??????????????????????????? gzlslid???{}", gzlslid);
        LOGGER.info("??????????????????????????????id{}?????????????????????????????????????????????{}", gzlslid, JSON.toJSONString(storageDtoList));

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
        // ??????????????????
        List<Map> sjlxMapList = bdcZdFeignService.queryBdcZd("sjlx");
        BdcSlSjclDTO bdcSlSjclDTO = new BdcSlSjclDTO();
        if (MapUtils.isNotEmpty(sjclDOMap) && sjclDOMap.containsKey(storageDto.getId())) {
            BdcSlSjclDO bdcSlSjclDO = sjclDOMap.get(storageDto.getId()).get(0);
            // ????????????
            bdcSlSjclDTO.setClmc(bdcSlSjclDO.getClmc());
            // ??????
            bdcSlSjclDTO.setXh(bdcSlSjclDO.getXh());
            // ??????
            bdcSlSjclDTO.setYs(bdcSlSjclDO.getYs());
            // ????????????
            bdcSlSjclDTO.setSjcldz(String.format(gdFileDownloadUrl, storageDto.getId()));
            // ????????????
            bdcSlSjclDTO.setMrfs(bdcSlSjclDO.getMrfs());
            // ????????????
            bdcSlSjclDTO.setSjlx(bdcSlSjclDO.getSjlx());
            // ??????????????????
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
            // ????????????
            bdcSlSjclDTO.setClmc(clmc);
            // ??????
            bdcSlSjclDTO.setXh(xh);
            // ??????
            bdcSlSjclDTO.setYs(1);
            // ????????????
            bdcSlSjclDTO.setSjcldz(String.format(gdFileDownloadUrl, storageDto.getId()));
            return bdcSlSjclDTO;
        }
    }
}
