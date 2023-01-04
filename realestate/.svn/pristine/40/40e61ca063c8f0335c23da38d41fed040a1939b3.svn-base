package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcGdxxMapper;
import cn.gtmap.realestate.certificate.service.BdcGdxxService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcGdxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Lists;
import groovy.util.Node;
import groovy.util.XmlParser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/16
 * @description 归档
 */
@Service
public class BdcGdxxServiceImpl implements BdcGdxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGdxxServiceImpl.class);
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcGdxxMapper bdcGdxxMapper;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    protected Repo repo;

    /**
     * @param bdcGdxxDO
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 新增归档信息
     */
    @Override
    public void insertBdcGdxx(BdcGdxxDO bdcGdxxDO) {
        entityMapper.insertSelective(bdcGdxxDO);
    }

    /**
     * @param bdcGdxxDO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 根据xmid更新归档信息, 存在则更新不存在则插入
     */
    @Override
    public void updateBdcGdxx(BdcGdxxDO bdcGdxxDO) {
        if (StringUtils.isNotBlank(bdcGdxxDO.getXmid())) {
            List<BdcGdxxDO> gdxxDOList = bdcGdxxMapper.queryBdcGdxxByXmid(bdcGdxxDO.getXmid());
            if (CollectionUtils.isEmpty(gdxxDOList)) {
                // 没查到则插入
                entityMapper.insertSelective(bdcGdxxDO);
            } else {
                // 查到则更新
                for (BdcGdxxDO gdxxDO : gdxxDOList) {
                    bdcGdxxDO.setGdxxid(gdxxDO.getGdxxid());
                    entityMapper.updateByPrimaryKeySelective(bdcGdxxDO);
                }
            }
        }
    }

    /**
     * @param xmId
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @description 根据xmid查询daid
     */
    @Override
    public String getDaIdByXmId(String xmId) {
        return bdcGdxxMapper.searchDaIdByXmId(xmId);
    }


    /**
     * @param archiveUrl,archiveXml
     * @return responseXml
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 调用档案接口并获取归档结果xml
     */
    @Override
    public String postArchiveInfo(String archiveUrl, String archiveXml) {
        String responseXml = "";
        if (StringUtils.isBlank(archiveUrl)) {
            LOGGER.info("未配置档案地址，不予继续归档！");
            return responseXml;
        }
        if (StringUtils.isNotBlank(archiveXml)) {
            RestTemplate restTemplate = new RestTemplate();
            //String param = "?data=" + archiveXml;
            try {
                JSONObject json = new JSONObject();
                json.put("data", archiveXml);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);

                HttpEntity<Object> entity = new HttpEntity<Object>(json, headers);
                restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
                responseXml = restTemplate.postForObject(archiveUrl, entity, String.class);
            } catch (Exception e) {
                LOGGER.error("归档失败,归档地址：{}", archiveUrl, e);
                Throwable targetException = e;
                if (targetException instanceof RuntimeException) {
                    throw new RuntimeException("接口调用异常", targetException);
                }
            }
        }
        return responseXml;
    }

    /**
     * @param archiveResponseXml
     * @return List<BdcGdxxDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 解析档案返回的xml封装至BdcGdxxDO对象
     */
    @Override
    public List<BdcGdxxDO> initBdcGdxx(String archiveResponseXml) {
        List archiveResponseList = new ArrayList();
        try {
            if (StringUtils.isNotBlank(archiveResponseXml)) {

                Node lisNode = new XmlParser().parseText(archiveResponseXml);
                List<Node> archiveNodeList = lisNode.children();
                if (CollectionUtils.isNotEmpty(archiveNodeList)) {
                    for (Node aNode : archiveNodeList) {
                        BdcGdxxDO bdcGdxxDO = new BdcGdxxDO();
                        String result = (String) aNode.attribute("result");

                        bdcGdxxDO.setDamx((String) aNode.attribute("type"));

                        List<Node> fieldList = aNode.children();
                        if (CollectionUtils.isNotEmpty(fieldList)) {
                            for (Node fNode : fieldList) {
                                String fieldName = (String) fNode.attribute("name");
                                if (StringUtils.equals("succeed", result)) {

                                    if (StringUtils.equals("id", fieldName)) {
                                        bdcGdxxDO.setDaid(fNode.text());
                                    }
                                    if (StringUtils.equals("ajh", fieldName)) {
                                        bdcGdxxDO.setAjh(fNode.text());
                                    }
                                    if (StringUtils.equals("mlh", fieldName)) {
                                        bdcGdxxDO.setMlh(fNode.text());
                                    }
                                } else {
                                    String msg = (String) aNode.attribute("msg");
                                    bdcGdxxDO.setGdxx(msg);
                                }
                                if (StringUtils.equals("proId", fieldName)) {
                                    bdcGdxxDO.setXmid(fNode.text());
                                }
                            }
                        }
                        archiveResponseList.add(bdcGdxxDO);
                    }
                }
            }
        } catch (SAXException e) {
            LOGGER.error("xml格式错误:", e);
        } catch (ParserConfigurationException e) {
            LOGGER.error("配置错误:", e);
        } catch (IOException e) {
            LOGGER.error("流读取异常:", e);
        }
        return archiveResponseList;
    }

    /**
     * @param bdcGdxxQO
     * @return List<BdcGdxxDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 查询归档信息集合
     */
    @Override
    public List<BdcGdxxDO> listBdcGdxx(BdcGdxxQO bdcGdxxQO) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(bdcGdxxQO.getBdcdyh())) {
            map.put("bdcdyh", bdcGdxxQO.getBdcdyh());
        }
        if (StringUtils.isNotBlank(bdcGdxxQO.getBdcqzh())) {
            map.put("bdcqzh", bdcGdxxQO.getBdcqzh());
        }
        if (StringUtils.isNotBlank(bdcGdxxQO.getSlbh())) {
            map.put("slbh", bdcGdxxQO.getSlbh());
        }
        if (StringUtils.isNotBlank(bdcGdxxQO.getZl())) {
            map.put("zl", bdcGdxxQO.getZl());
        }
        if (StringUtils.isNotBlank(bdcGdxxQO.getXmid())) {
            map.put("xmid", bdcGdxxQO.getXmid());
        }
        if (StringUtils.isNotBlank(bdcGdxxQO.getDaid())) {
            map.put("daid", bdcGdxxQO.getDaid());
        }
        return bdcGdxxMapper.listBdcGdxx(map);
    }

    /**
     * 查询分页数据
     *
     * @param pageable
     * @param param
     * @return
     */
    @Override
    public Page<BdcXmGdxxDTO> listBdcGdxxByPage(Pageable pageable,
                                                HashMap<String, Object> param,
                                                String showGdxxType) {
        List<BdcXmGdxxDTO> bdcXmGdxxDTOList = new ArrayList<>();


        if (Constants.SHOW_GDXX_TYPE_HF == Integer.parseInt(showGdxxType)) {
            Page<BdcXmGdxxDTO> pageList = repo.selectPaging("listBdcGdxxHfByPage", param, pageable);
            long totalElements = pageList.getTotalElements();
            if (totalElements > 10000){
                throw new MissingArgumentException("数据过多，请增加查询条件!");
            }
            //查询登记的数据
            bdcXmGdxxDTOList = bdcGdxxMapper.listBdcGdxxHfByPage(param);
        } else {
            Page<BdcXmGdxxDTO> pageList = repo.selectPaging("listBdcGdxxNtByPage", param, pageable);
            long totalElements = pageList.getTotalElements();
            if (totalElements > 10000){
                throw new MissingArgumentException("数据过多，请增加查询条件!");
            }
            bdcXmGdxxDTOList = bdcGdxxMapper.listBdcGdxxNtByPage(param);
        }
        //受理的信息
        List<BdcXmGdxxDTO> bdcslGdxxDTOList = getBdcXmGdxxDTOS(param, showGdxxType);
        //过滤掉当前已经在登记查询出来的数据-- 根据受理编号查询,正常的数据在两边都可以查询到
        List<String> djSlbh = bdcXmGdxxDTOList
                .stream()
                .map(BdcXmGdxxDTO::getSlbh)
                .collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(djSlbh)) {
            bdcslGdxxDTOList = bdcslGdxxDTOList.stream()
                    .filter(bdcXmGdxxDTO -> !djSlbh.contains(bdcXmGdxxDTO.getSlbh()))
                    .collect(Collectors.toList());
        }

        bdcXmGdxxDTOList.addAll(bdcslGdxxDTOList);

        //内存分页
        Page page = PageUtils.listToPage(bdcXmGdxxDTOList, pageable);

        if(Constants.SHOW_GDXX_TYPE_HF == Integer.parseInt(showGdxxType)){
            for (int i = 0; i < page.getContent().size(); i++) {
                BdcXmGdxxDTO bdcXmGdxxDTO = (BdcXmGdxxDTO) page.getContent().get(i);
                String slbh = bdcXmGdxxDTO.getSlbh();
                if(StringUtils.isBlank(slbh)){
                    continue;
                }
                BdcXmQO qo = new BdcXmQO();
                qo.setSlbh(slbh);
                List<BdcXmDO> listxm = bdcXmFeignService.listBdcXm(qo);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    Set<String> bdcdyhSet = new HashSet();
                    Set<String> zlSet = new HashSet();
                    Set<String> bdcqzhSet = new HashSet();
                    for (BdcXmDO bdcXmDO : listxm) {
                        bdcdyhSet.add(bdcXmDO.getBdcdyh());
                        zlSet.add(bdcXmDO.getZl());
                        bdcqzhSet.add(bdcXmDO.getBdcqzh());
                    }

                    if (CollectionUtils.size(bdcdyhSet) > 1) {
                        bdcXmGdxxDTO.setBdcdyh(bdcXmGdxxDTO.getBdcdyh() + CommonConstantUtils.SUFFIX_PL);
                    }
                    if (CollectionUtils.size(zlSet) > 1) {
                        bdcXmGdxxDTO.setZl(bdcXmGdxxDTO.getZl() + CommonConstantUtils.SUFFIX_PL);
                    }
                    if (CollectionUtils.size(bdcqzhSet) > 1) {
                        bdcXmGdxxDTO.setBdcqzh(bdcXmGdxxDTO.getBdcqzh() + CommonConstantUtils.SUFFIX_PL);
                    }
                }
            }
        }

        return page;
    }

    /**
     * 获取受理数据
     *
     * @param param
     * @param showGdxxType
     * @return
     */
    private List<BdcXmGdxxDTO> getBdcXmGdxxDTOS(HashMap<String, Object> param,
                                                String showGdxxType) {
        List<BdcXmGdxxDTO> resp = new ArrayList<>();
        if (!param.containsKey("bdcqzh") && (param.containsKey("slbh") || param.containsKey("smqsr"))) {
            //必须要有受理编号才查询受理库否则可能数据过多
            List<BdcXmGdxxDTO> bdcslGdxxDTOList = bdcSlXmFeignService.listBdcGdxxHf(param);
            //处理受理库查出来的归档信息进行二次过滤
            if (CollectionUtils.isNotEmpty(bdcslGdxxDTOList)) {
                List<String> slxmidList = bdcslGdxxDTOList
                        .stream()
                        .map(BdcXmGdxxDTO::getXmid)
                        .collect(Collectors.toList());
                param.put("xmidList", slxmidList);

                //如果使用的移交单表的查询条件则做强制关联，
                // 如果用了这些查询条件没有查出对应的数据则要反过来将查询到的受理项目数据过滤掉
                // 如果没有用这些条件则是用查到的项目数据做关联
                boolean srrongFilter = false;
                if (param.containsKey("sfygd") || param.containsKey("sfyyj") || param.containsKey("gdrxm")) {
                    srrongFilter = true;
                }

                List<BdcXmGdxxDTO> bdcSlGdxx = bdcGdxxMapper.listBdcSlGdxx(param);
                Map<String, BdcXmGdxxDTO> slGdMap = bdcSlGdxx
                        .stream()
                        .collect(Collectors.toMap(BdcXmGdxxDTO::getXmid, o -> o));

                if (Constants.SHOW_GDXX_TYPE_HF == Integer.parseInt(showGdxxType)) {
                    Map<String, List<BdcXmGdxxDTO>> slAlldataGroup = bdcslGdxxDTOList
                            .stream()
                            .collect(Collectors.groupingBy(BdcXmGdxxDTO::getGzlslid));
                    for (Map.Entry<String, List<BdcXmGdxxDTO>> stringListEntry : slAlldataGroup.entrySet()) {
                        List<BdcXmGdxxDTO> bdcXmGdxxs = stringListEntry.getValue();
                        boolean findMatch = false;
                        for (BdcXmGdxxDTO bdcXmGdxx : bdcXmGdxxs) {
                            if (slGdMap.containsKey(bdcXmGdxx.getXmid())) {
                                BdcXmGdxxDTO bdcXmGdxxDTO = slGdMap.get(bdcXmGdxx.getXmid());
                                bdcXmGdxx.setMlh(bdcXmGdxxDTO.getMlh());
                                bdcXmGdxx.setAjh(bdcXmGdxxDTO.getAjh());
                                bdcXmGdxx.setGdrxm(bdcXmGdxxDTO.getGdrxm());
                                bdcXmGdxx.setDaid(bdcXmGdxxDTO.getDaid());
                                bdcXmGdxx.setYjdid(bdcXmGdxxDTO.getYjdid());
                                bdcXmGdxx.setGdxx(bdcXmGdxxDTO.getGdxx());
                                bdcXmGdxx.setGdsj(bdcXmGdxxDTO.getGdsj());
                                bdcXmGdxx.setAjjs(bdcXmGdxxDTO.getAjjs());
                                bdcXmGdxx.setAjys(bdcXmGdxxDTO.getAjys());
                                //BeanUtils.copyProperties(slGdMap.get(bdcXmGdxx.getXmid()), bdcXmGdxx);
                                findMatch = true;
                                resp.add(bdcXmGdxx);
                                break;
                            }
                        }
                        //没有找到匹配则任取一条,非强制关联下能查出,强制关联则不返回
                        if (!findMatch && (!srrongFilter)) {
                            resp.add(bdcXmGdxxs.get(0));
                        }
                    }
                } else {
                    List<BdcXmGdxxDTO> filterData = new ArrayList<>();
                    //补充信息
                    for (BdcXmGdxxDTO slGdxx : bdcslGdxxDTOList) {
                        if (slGdMap.containsKey(slGdxx.getXmid())) {
                            BeanUtils.copyProperties(slGdMap.get(slGdxx.getXmid()), slGdxx);
                            filterData.add(slGdxx);
                        } else if (!srrongFilter) {
                            filterData.add(slGdxx);
                        }
                    }
                    resp = filterData;
                }
            }
        }
        for (BdcXmGdxxDTO bdcXmGdxxDTO : resp) {
            bdcXmGdxxDTO.setSjly("1");
        }
        return resp;
    }

    /**
     * @param gdxxid
     * @return BdcGdxxDO
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 根据主键查询归档信息
     */
    @Override
    public BdcGdxxDO queryBdcGdxx(String gdxxid) {
        if (StringUtils.isNotBlank(gdxxid)) {
            return entityMapper.selectByPrimaryKey(BdcGdxxDO.class, gdxxid);
        }
        return null;
    }

    /**
     * @param xmid
     * @return daid
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid查询对应的daid
     */
    @Override
    public String queryBdcDaid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID!");
        }
        Example example = new Example(BdcGdxxDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        List<BdcGdxxDO> bdcGdxxDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isNotEmpty(bdcGdxxDOList)) {
            BdcGdxxDO bdcGdxxDO = bdcGdxxDOList.get(0);
            return bdcGdxxDO.getDaid();
        }
        return null;
    }

    /**
     * @param xmid
     * @return
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xmid删除归档信息
     */
    @Override
    public int deleteBdcGdxxByXmid(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("缺失项目ID!");
        }
        Example example = new Example(BdcGdxxDO.class);
        example.createCriteria().andEqualTo("xmid", xmid);
        return entityMapper.deleteByExampleNotNull(example);
    }

}
