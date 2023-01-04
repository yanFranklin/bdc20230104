package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.accept.config.PropsConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.core.service.BdcYxbdcdyKgPzService;
import cn.gtmap.realestate.accept.service.BdcYxBdcdyService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.AcceptBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/15
 * @description 已选不动产单元服务
 */
@Service
public class BdcYxBdcdyServiceImpl implements BdcYxBdcdyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYxBdcdyServiceImpl.class);
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    PropsConfig propsConfig;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcYxbdcdyKgPzService bdcYxbdcdyKgPzService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmService bdcSlXmService;

    @Autowired
    AcceptBdcdyFeignService acceptBdcdyFeignService;


    /**
     * @param bdcSlYwxxDTOPage 不动产业务信息分页
     * @param jbxxid           基本信息ID
     * @return 已选不动产单元
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 获取已选不动产单元
     */
    @Override
    public YxBdcdyDTO queryYxBdcdyDTO(Page<BdcSlYwxxDTO> bdcSlYwxxDTOPage, String jbxxid, String gzldyid) {
        YxBdcdyDTO yxBdcdyDTO = new YxBdcdyDTO();
        if (bdcSlYwxxDTOPage != null) {
            if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOPage.getContent())) {
                List<BdcYxYwxxDTO> bdcYxYwxxDTOList = listBdcYxYwxxDTO(bdcSlYwxxDTOPage.getContent());
                if (CollectionUtils.isNotEmpty(bdcYxYwxxDTOList)) {
                    yxBdcdyDTO.setBdcYxYwxxDTOList(bdcYxYwxxDTOList);
                }
            }
            yxBdcdyDTO.setCount(bdcSlYwxxDTOPage.getTotalElements());
            yxBdcdyDTO.setCurr(bdcSlYwxxDTOPage.getNumber());
        }

        YxBdcdyKgDTO yxBdcdyKgDTO = queryYxBdcdyKgDTO(jbxxid, gzldyid,"");
        yxBdcdyDTO.setBdcdyKgDTO(yxBdcdyKgDTO);
        return yxBdcdyDTO;
    }

    /**
     * @param bdcSlYwxxDTOList 不动产业务信息集合
     * @param jbxxid           基本信息ID
     * @return 已选不动产单元
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取已选不动产单元
     */
    @Override
    public YxBdcdyDTO queryYxBdcdyDTO(List<BdcSlYwxxDTO> bdcSlYwxxDTOList, String jbxxid, String gzldyid,String djxl) {
        YxBdcdyDTO yxBdcdyDTO = new YxBdcdyDTO();

        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            List<BdcYxYwxxDTO> bdcYxYwxxDTOList = listBdcYxYwxxDTO(bdcSlYwxxDTOList);
            if (CollectionUtils.isNotEmpty(bdcYxYwxxDTOList)) {
                yxBdcdyDTO.setBdcYxYwxxDTOList(bdcYxYwxxDTOList);
            }
        }

        YxBdcdyKgDTO yxBdcdyKgDTO = queryYxBdcdyKgDTO(jbxxid, gzldyid,djxl);
        yxBdcdyDTO.setBdcdyKgDTO(yxBdcdyKgDTO);
        return yxBdcdyDTO;
    }

    @Override
    public List<YxBdcdyDTO> queryYxBdcdyDTOGroupByDjxl(String slXmQO, String jbxxid, String gzldyid, String sfazfz) {
        if (StringUtils.isBlank(jbxxid)) {
            throw new MissingArgumentException("jbxxid");
        }
        List<YxBdcdyDTO> yxBdcdyDTOList = new ArrayList<>();
        Map map = JSONObject.parseObject(slXmQO, HashMap.class);
        //已选单元信息列表
        List<BdcSlYwxxDTO> bdcSlYwxxDTOList = bdcSlXmService.listBdcSLXmDTO(map);
        if(CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOList = bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
            //如果流程整体默认生成一本证,无需分组
            if (CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOList) && bdcYxbdcdyKgPzDOList.size() == 1
                    && Constants.ZSSL_MRZ.equals(bdcYxbdcdyKgPzDOList.get(0).getZsslmrz()) &&StringUtils.isBlank(bdcYxbdcdyKgPzDOList.get(0).getDjxl())) {
                //相关开关配置
                YxBdcdyDTO yxBdcdyDTO = new YxBdcdyDTO();
                if ("true".equals(sfazfz)){
                    yxBdcdyDTO = queryYxBdcdyAzfzDTO(bdcSlYwxxDTOList, jbxxid, gzldyid,"");
                }else{
                    yxBdcdyDTO = queryYxBdcdyDTO(bdcSlYwxxDTOList, jbxxid, gzldyid,"");
                }

                yxBdcdyDTOList.add(yxBdcdyDTO);
            }else{
                // 当没有配置 或者 配置多条 则按djxl分组
                List<BdcSlYwxxDTO> sortbdcSlYwxxDOList = new ArrayList<>();
                //登记小类分组结果
                Map<String, List<BdcSlYwxxDTO>> djxlXmMap =new HashMap<>();
                //证书序号分组结果
                Map<Integer, List<BdcSlYwxxDTO>> zsxhXmMap =new HashMap<>();
                //先查询出证书序号为空的数据，为空按登记小类分组
                List<BdcSlYwxxDTO> nullZsxhSlYwxxDTOList = bdcSlYwxxDTOList.stream().filter(bdcSlYwxxDTO -> bdcSlYwxxDTO.getZsxh() ==null).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(nullZsxhSlYwxxDTOList)){
                    //根据登记小类分组显示
                    djxlXmMap = nullZsxhSlYwxxDTOList.stream().collect(Collectors.groupingBy(BdcSlYwxxDTO::getDjxl));
                    if (MapUtils.isNotEmpty(djxlXmMap)) {
                        //整理分组后的数据
                        for (Map.Entry<String, List<BdcSlYwxxDTO>> entry : djxlXmMap.entrySet()) {
                            List<BdcSlYwxxDTO> djxlXmList = entry.getValue();
                            djxlXmList.sort(Comparator.comparing(BdcSlYwxxDTO::getXmid));
                            if (CollectionUtils.isNotEmpty(djxlXmList)) {
                                //取分组后各个list中第一条,用于后续排序
                                sortbdcSlYwxxDOList.add(djxlXmList.get(0));
                            }
                        }
                    }
                    bdcSlYwxxDTOList.removeAll(nullZsxhSlYwxxDTOList);
                }
                if(CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)) {
                    //对证书序号不为空的根据证书序号进行分组
                    zsxhXmMap = bdcSlYwxxDTOList.stream().collect(Collectors.groupingBy(BdcSlYwxxDTO::getZsxh));
                    if(MapUtils.isNotEmpty(zsxhXmMap)) {
                        //整理分组后的数据
                        for (Map.Entry<Integer, List<BdcSlYwxxDTO>> entry : zsxhXmMap.entrySet()) {
                            List<BdcSlYwxxDTO> zsxhXmList = entry.getValue();
                            zsxhXmList.sort(Comparator.comparing(BdcSlYwxxDTO::getXmid));
                            if (CollectionUtils.isNotEmpty(zsxhXmList)) {
                                //取分组后各个list中第一条,用于后续排序
                                sortbdcSlYwxxDOList.add(zsxhXmList.get(0));
                            }
                        }
                    }
                }

                if (MapUtils.isNotEmpty(djxlXmMap) ||MapUtils.isNotEmpty(zsxhXmMap)) {
                    //根据xmid排序
                    sortbdcSlYwxxDOList.sort(Comparator.comparing(BdcSlYwxxDTO::getXmid));
                    if (CollectionUtils.isNotEmpty(sortbdcSlYwxxDOList)) {
                        for (BdcSlYwxxDTO bdcSlYwxxDTO : sortbdcSlYwxxDOList) {
                            List<BdcSlYwxxDTO> groupXmList =new ArrayList<>();
                            if(MapUtils.isNotEmpty(djxlXmMap) &&bdcSlYwxxDTO.getZsxh() ==null) {
                                groupXmList = djxlXmMap.get(bdcSlYwxxDTO.getDjxl());
                            }else if(MapUtils.isNotEmpty(zsxhXmMap) &&bdcSlYwxxDTO.getZsxh() != null) {
                                groupXmList = zsxhXmMap.get(bdcSlYwxxDTO.getZsxh());
                            }
                            if (CollectionUtils.isNotEmpty(groupXmList)) {
                                //相关开关配置
                                YxBdcdyDTO djxlyxBdcdyDTO = new YxBdcdyDTO();
                                if ("true".equals(sfazfz)){
                                    djxlyxBdcdyDTO = queryYxBdcdyAzfzDTO(groupXmList, jbxxid, gzldyid,groupXmList.get(0).getDjxl());
                                }else{
                                    djxlyxBdcdyDTO = queryYxBdcdyDTO(groupXmList, jbxxid, gzldyid,groupXmList.get(0).getDjxl());
                                }
                                if (djxlyxBdcdyDTO != null) {
                                    //登记小类
                                    djxlyxBdcdyDTO.setDjxl(groupXmList.get(0).getDjxl());
                                    yxBdcdyDTOList.add(djxlyxBdcdyDTO);
                                }
                            }
                        }
                    }
                }
            }
        }
        return yxBdcdyDTOList;
    }

    /**
     * @param jbxxid 基本信息ID
     * @return 选不动产单元开关
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 组织选不动产单元开关
     */
    private YxBdcdyKgDTO queryYxBdcdyKgDTO(String jbxxid, String gzldyid,String djxl) {
        YxBdcdyKgDTO yxBdcdyKgDTO = new YxBdcdyKgDTO();
        BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO = new BdcYxbdcdyKgPzDO();
        if (StringUtils.isBlank(gzldyid)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByJbxxid(jbxxid);
            if (bdcSlJbxxDO != null) {
                ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(bdcSlJbxxDO.getGzlslid());
                if (StringUtils.isBlank(processInstanceData.getProcessDefinitionKey())) {
                    throw new AppException("工作流实例未找到,请联系管理员");
                }
                bdcYxbdcdyKgPzDO = queryBdcYxbdcdyKgPzDOByGzldyid(processInstanceData.getProcessDefinitionKey(),djxl);
            }
        } else {
            bdcYxbdcdyKgPzDO = queryBdcYxbdcdyKgPzDOByGzldyid(gzldyid,djxl);
        }
        Field[] pzFields = BdcYxbdcdyKgPzDO.class.getDeclaredFields();
        //配置表属性名称
        List<String> pzFieldsNameList = BeansResolveUtils.getClassFieldsName(pzFields);
        /**
         * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
         * @description 优先读取配置文件
         */
        Field[] fields = YxBdcdyKgDTO.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                //获取对象属性名称
                String fieldname = StringUtils.lowerCase(field.getName());
                if (propsConfig.getYxbdcdyKgOfMapItem(fieldname)) {
                    field.setAccessible(true);
                    field.set(yxBdcdyKgDTO, true);
                } else if (bdcYxbdcdyKgPzDO != null) {
                    /**
                     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
                     * @description 读取配置表, 当配置表中对应为1时开启
                     */
                    if (pzFieldsNameList.contains(field.getName())) {
                        Field pzField = BdcYxbdcdyKgPzDO.class.getDeclaredField(field.getName());
                        pzField.setAccessible(true);
                        //获取对应属性值
                        Object value = pzField.get(bdcYxbdcdyKgPzDO);
                        if (StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(), String.valueOf(value))) {
                            if (field.getType() == Boolean.class) {
                                field.setAccessible(true);
                                field.set(yxBdcdyKgDTO, true);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (bdcYxbdcdyKgPzDO != null) {
            yxBdcdyKgDTO.setZsslmrz(bdcYxbdcdyKgPzDO.getZsslmrz());
        }
        return yxBdcdyKgDTO;
    }

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  匹配获取不动产单元开关,djxl可以匹配以djxl匹配为准,没有匹配按流程配置
      */
    private BdcYxbdcdyKgPzDO queryBdcYxbdcdyKgPzDOByGzldyid(String gzldyid,String djxl){
        List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOList = bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
        if(CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOList)){
            if(StringUtils.isBlank(djxl)){
                return bdcYxbdcdyKgPzDOList.get(0);
            }
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOS =new ArrayList<>();
            for(BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO:bdcYxbdcdyKgPzDOList){
                //djxl匹配
                if(StringUtils.equals(djxl,bdcYxbdcdyKgPzDO.getDjxl())){
                    return bdcYxbdcdyKgPzDO;
                }else if(StringUtils.isBlank(bdcYxbdcdyKgPzDO.getDjxl())){
                    bdcYxbdcdyKgPzDOS.add(bdcYxbdcdyKgPzDO);
                }
            }
            if(CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOS)) {
                return bdcYxbdcdyKgPzDOS.get(0);
            }
        }
        return new BdcYxbdcdyKgPzDO();

    }

    /**
     * @param bdcSlYwxxDTOList 不动产受理项目前台
     * @return 不动产已选业务信息前台
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 组织已选业务信息页面数据
     */
    private List<BdcYxYwxxDTO> listBdcYxYwxxDTO(List<BdcSlYwxxDTO> bdcSlYwxxDTOList) {
        List<BdcYxYwxxDTO> bdcYxYwxxDTOList = new ArrayList<>();
        for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
            BdcYxYwxxDTO bdcYxYwxxDTO = new BdcYxYwxxDTO();
            bdcYxYwxxDTO.setBdcSlYwxxDTO(bdcSlYwxxDTO);
            //目前购物车页面不展示外联证书，此处代码暂时注释
//                List<BdcYxWlzsDTO> bdcYxWlzsDTOList = listBdcYxWlzsDTO(bdcSlYwxxDTO.getXmid());
//                if (CollectionUtils.isNotEmpty(bdcYxWlzsDTOList)) {
//                    bdcYxYwxxDTO.setBdcYxWlzsDTOList(bdcYxWlzsDTOList);
//                }
            bdcYxYwxxDTOList.add(bdcYxYwxxDTO);
        }
        return bdcYxYwxxDTOList;
    }

    /**
     * @param xmid 已选业务项目id
     * @return 不动产已选外联证书
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织已选外联证书数据
     */
    private List<BdcYxWlzsDTO> listBdcYxWlzsDTO(String xmid) {
        List<BdcYxWlzsDTO> bdcYxWlzsDTOList = new ArrayList<>();
        if (StringUtils.isNotBlank(xmid)) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                for (BdcSlXmLsgxDO bdcSlXmLsgxDO : bdcSlXmLsgxDOList) {
                    if (StringUtils.isNotBlank(bdcSlXmLsgxDO.getYxmid())) {
                        BdcXmQO bdcXmQO = new BdcXmQO();
                        bdcXmQO.setXmid(bdcSlXmLsgxDO.getYxmid());
                        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                            BdcYxWlzsDTO bdcYxWlzsDTO = new BdcYxWlzsDTO();
                            bdcYxWlzsDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            bdcYxWlzsDTO.setZl(bdcXmDO.getZl());
                            bdcYxWlzsDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                            bdcYxWlzsDTO.setQlr(bdcXmDO.getQlr());
                            bdcYxWlzsDTO.setGxid(bdcSlXmLsgxDO.getGxid());
                            bdcYxWlzsDTO.setZxyql(bdcSlXmLsgxDO.getZxyql());
                            bdcYxWlzsDTO.setSfwlzs(bdcSlXmLsgxDO.getSfwlzs());
                            bdcYxWlzsDTOList.add(bdcYxWlzsDTO);
                        }
                    }
                }
            }
        }
        return bdcYxWlzsDTOList;

    }

    /**
     * @param bdcSlYwxxDTOList 不动产业务信息集合
     * @param jbxxid           基本信息ID
     * @return 已选不动产单元按幢分组
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 获取已选不动产单元
     */
    private YxBdcdyDTO queryYxBdcdyAzfzDTO(List<BdcSlYwxxDTO> bdcSlYwxxDTOList, String jbxxid, String gzldyid, String djxl) {
        YxBdcdyDTO yxBdcdyDTO = new YxBdcdyDTO();
        List<YxBdcdyAzfzDTO> azfzDTOS = new ArrayList<>();
        Map<String, List<BdcSlYwxxDTO>> stringListMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bdcSlYwxxDTOList)){
            // 根据qjgldm区分权籍库,然后再根据逻辑幢分组
            Set<String> qjgldmSet = new HashSet<>();
            Map<String,List<BdcSlYwxxDTO>> qjgldmMap = bdcSlYwxxDTOList.stream().collect(Collectors.groupingBy(item->StringUtils.isBlank(item.getQjgldm())?"":item.getQjgldm()));
            for (BdcSlYwxxDTO bdcSlYwxxDTO : bdcSlYwxxDTOList) {
                qjgldmSet.add(StringUtils.isBlank(bdcSlYwxxDTO.getQjgldm()) ? "" : bdcSlYwxxDTO.getQjgldm());
            }
            if (CollectionUtils.isNotEmpty(qjgldmSet)) {
                for (String qjgldm : qjgldmSet) {
                    // 分组后受理项目
                    List<BdcSlYwxxDTO> slYwxxDTOS= qjgldmMap.get(qjgldm);
                    stringListMap = acceptBdcdyFeignService.getLjzxx(slYwxxDTOS, qjgldm);
                    LOGGER.info("qjgldm:{},building按幢分组返回参数：{}", qjgldm,JSONObject.toJSONString(stringListMap));
                    List<YxBdcdyAzfzDTO> bdcdyAzfzDTOList = getYxBdcdyAzfzDTOList(stringListMap);
                    azfzDTOS.addAll(bdcdyAzfzDTOList);
                }
            }
        }
        YxBdcdyKgDTO yxBdcdyKgDTO = queryYxBdcdyKgDTO(jbxxid, gzldyid, djxl);
        yxBdcdyDTO.setBdcdyKgDTO(yxBdcdyKgDTO);
        yxBdcdyDTO.setYxBdcdyAzfzDTOList(azfzDTOS);
        return yxBdcdyDTO;
    }

    /**
     * 组织YxBdcdyAzfzDTO
     *
     * @param stringListMap
     * @return list
     */
    private List<YxBdcdyAzfzDTO> getYxBdcdyAzfzDTOList(Map<String, List<BdcSlYwxxDTO>> stringListMap) {
        Set<String> keySet = stringListMap.keySet();
        List<YxBdcdyAzfzDTO> list = new ArrayList<>();
        for (String key : keySet) {
            // key为idzl拼接Str
            String zl = key.substring(key.indexOf(CommonConstantUtils.ZF_YW_DH) + 1);
            YxBdcdyAzfzDTO yxBdcdyAzfzDTO = new YxBdcdyAzfzDTO();
            yxBdcdyAzfzDTO.setZl(zl);
            List<BdcSlYwxxDTO> bdcSlYwxxDTOList = stringListMap.get(key);
            yxBdcdyAzfzDTO.setBdcYxYwxxDTOList(listBdcYxYwxxDTO(bdcSlYwxxDTOList));
            list.add(yxBdcdyAzfzDTO);
        }
        return list;
    }
}
