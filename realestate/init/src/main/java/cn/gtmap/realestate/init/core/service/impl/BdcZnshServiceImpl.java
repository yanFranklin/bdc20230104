package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.dto.init.znsh.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcYxmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.BdcZnshService;
import cn.gtmap.realestate.init.service.other.BdcSynchService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/5/12
 * @description 智能审核
 */
@Service
public class BdcZnshServiceImpl implements BdcZnshService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZnshServiceImpl.class);

    // 权籍
    private static final String SJLY_QJ = "权籍";
    // 原产权
    private static final String SJLY_YCQ = "原产权";
    // 共享接口
    private static final String SJLY_GXJK = "共享接口";
    // 一致
    private static final Integer YZ = 1;
    // 不一致
    private static final Integer BYZ = 0;


    @Autowired
    private BdcQlrService bdcQlrService;

    @Autowired
    private BdcXmService bdcXmService;

    @Autowired
    private BdcZdCache bdcZdCache;


    @Autowired
    private BdcQllxService bdcQllxService;

    @Autowired
    private BdcSynchService bdcSynchService;

    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 项目权力信息需要比对字段
     */
    @Value("#{${znsh.xmqlxxbd.zdmcMap:{'BDC_XM':'zl,zdzhmj,dzwmj,zdzhyt,dzwyt,bdcdyh,qlxz','BDC_FDCQ':'szc,zcs,tdsyjssj'}}}")
    private Map<String, String> zdmcMap;

    /**
     * 项目权力信息共享接口需要比对字段
     */
    @Value("#{${znsh.xmqlxxbd.gxjkzdMap:{'BDC_XM':'zl'}}}")
    private Map<String, String> gxjkzd;

    @Override
    public BdcSjjyDTO znsh(String xmid) {
        BdcSjjyDTO bdcSjjyDTO = new BdcSjjyDTO();
        BdcXmDO bdcXmQO = new BdcXmDO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = bdcXmService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new AppException("智能审核，项目不存在");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        // 合同信息
        FcjyClfHtxxDTO htxxDTO = null;
        if (StringUtils.isNotBlank(bdcXmDO.getJyhth()) && StringUtils.isNotBlank(bdcXmDO.getGzldyid())) {
            htxxDTO = getJyxx(bdcXmDO.getJyhth(), bdcXmDO.getGzldyid());
        }
        // 权利人
        getBdcQlrSjjyDTO(xmid, bdcSjjyDTO, htxxDTO, CommonConstantUtils.QLRLB_QLR);
        // 义务人
        getBdcQlrSjjyDTO(xmid, bdcSjjyDTO, htxxDTO, CommonConstantUtils.QLRLB_YWR);
        // 登记系统数据来源
        List<BdcCshFwkgSlDO> cshFwkgSlDOS = bdcXmService.listBdCshSlById(bdcXmDO.getGzlslid(), xmid);
        if (CollectionUtils.isEmpty(cshFwkgSlDOS)) {
            throw new AppException("智能审核，初始化服务开关配置表不存在");
        }
        BdcCshFwkgSlDO cshFwkgSlDO = cshFwkgSlDOS.get(0);
        // 权利数据来源 1：权籍 2：上一手  可组合(1,2)
        String qlsjly = cshFwkgSlDO.getQlsjly();
        List<String> sjlyList = new ArrayList<>();
        BdcSjjyMkDTO sjjyMkDTO = new BdcSjjyMkDTO();
        // 数据校验信息
        List<BdcSjjyxxDTO> sjjyxxList = new ArrayList<>();
        Map<String, List<String>> zdConfig = dealZdmcConfig(zdmcMap);
        if (CommonConstantUtils.QLSJLY_LPB.equals(qlsjly)) {
            // 比较登记和权籍
            sjlyList.add(SJLY_QJ);
            sjjyxxList = compareDjAndQj(xmid, zdConfig);
        } else if (CommonConstantUtils.QLSJLY_YXM.equals(qlsjly)) {
            // 比较登记和原产权
            sjlyList.add(SJLY_YCQ);
            sjjyxxList = compareDjAndYcq(xmid, zdConfig, bdcXmDO);
        } else if ("1,2".equals(qlsjly)) {
            // 比较登记和权籍，比较登记和原产权
            sjlyList.add(SJLY_QJ);
            sjlyList.add(SJLY_YCQ);
            sjjyxxList = compareDjAndYcqAndQj(xmid, zdConfig, bdcXmDO, sjlyList);
        }
        // 共享接口
        if (gxjkzd != null && htxxDTO != null) {
            sjlyList.add(SJLY_GXJK);
            Map<String, List<String>> gxjkzdMap = dealZdmcConfig(gxjkzd);
            sjjyxxList = compareGXJK(htxxDTO, bdcXmDO, sjjyxxList, gxjkzdMap);
        }
        sjjyMkDTO.setSjlyList(sjlyList);
        sjjyMkDTO.setSjjyxxList(sjjyxxList);
        bdcSjjyDTO.setXmqlxx(sjjyMkDTO);

        return bdcSjjyDTO;
    }

    /**
     * 权利人或义务人权力信息比对
     *
     * @param xmid       项目id
     * @param bdcSjjyDTO 数据校验信息
     * @param htxxDTO    合同信息
     * @param qlrlb      权利人类别
     */
    private void getBdcQlrSjjyDTO(String xmid, BdcSjjyDTO bdcSjjyDTO, FcjyClfHtxxDTO htxxDTO, String qlrlb) {
        BdcSjjyQlrmkDTO qlrmkDTO = new BdcSjjyQlrmkDTO();
        List<BdcQlrDO> bdcQlrDOList = bdcQlrService.listBdcQlrOrderBySxh(xmid, qlrlb);
        qlrmkDTO.setBdcQlrList(bdcQlrDOList);
        if (Objects.isNull(htxxDTO)) {
            LOGGER.info("项目的合同编号为空，没有房屋交易信息,不进行比对,xmid:{},qlrlb:{}", xmid, qlrlb);
            if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
                bdcSjjyDTO.setQlr(qlrmkDTO);
            } else {
                bdcSjjyDTO.setYwr(qlrmkDTO);
            }
            return;
        }
        List<BdcSjjyQlrbdDTO> qlrbdList = new ArrayList<>();
        BdcSjjyQlrbdDTO qlrbdDTO = new BdcSjjyQlrbdDTO();
        // 共享接口
        List<BdcQlrDO> gxQlrList = htxxDTO.getBdcQlr().stream().filter(item -> item.getQlrlb().equals(qlrlb)).collect(Collectors.toList());
        qlrbdDTO.setBdcQlrList(gxQlrList);
        qlrbdDTO.setSjly(SJLY_GXJK);
        // 比对
        boolean flag = compareQlr(bdcQlrDOList, gxQlrList);
        if (flag) {
            qlrbdDTO.setSfyz(YZ);
        } else {
            qlrbdDTO.setSfyz(BYZ);
        }
        qlrbdList.add(qlrbdDTO);
        qlrmkDTO.setQlrbdList(qlrbdList);
        if (CommonConstantUtils.QLRLB_QLR.equals(qlrlb)) {
            bdcSjjyDTO.setQlr(qlrmkDTO);
        } else {
            bdcSjjyDTO.setYwr(qlrmkDTO);
        }
    }

    /**
     * 获取房产交易信息
     *
     * @param htbh    合同编号
     * @param gzldyid 工作流定义id
     * @return 房产交易合同信息
     */
    private FcjyClfHtxxDTO getJyxx(String htbh, String gzldyid) {
        Map<String, String> param = new HashMap<>(2);
        param.put("contractNo", htbh);
        String spfGzldyid = bdcSlJbxxFeignService.spfGzldyid();
        Object response;
        if (spfGzldyid.indexOf(gzldyid) > -1) {
            LOGGER.info("智能审核查询商品房合同信息,beanName为：{}，param：{}", "fcjySpfBaxx", param);
            response = exchangeInterfaceFeignService.requestInterface("fcjySpfBaxx", param);
        } else {
            LOGGER.info("智能审核查询存量房合同信息,beanName为：{}，param：{}", "fcjyClfHtxx", param);
            response = exchangeInterfaceFeignService.requestInterface("fcjyClfHtxx", param);
        }
        if (Objects.isNull(response)) {
            LOGGER.error("调取合肥合同信息接口返回值为空,htbh:{},gzldyid:{}", htbh, gzldyid);
        }
        String jsonString = JSONObject.toJSONString(response);
        LOGGER.info("调取合肥合同信息接口返回值：{}", jsonString);
        FcjyClfHtxxDTO fcjyClfHtxxDTO = JSONObject.parseObject(jsonString, FcjyClfHtxxDTO.class);
        return fcjyClfHtxxDTO;
    }

    /**
     * 比较权利人数据
     *
     * @param qlr1 权利人
     * @param qlr2 权利人
     * @return true 一致 false 不一致
     */
    private boolean compareQlr(List<BdcQlrDO> qlr1, List<BdcQlrDO> qlr2) {
        boolean result = true;
        Map<String, Integer> map = new HashMap<>();
        // 将List1元素放入Map，计数1
        for (BdcQlrDO bdcQlrDO : qlr1) {
            String key = bdcQlrDO.getQlrmc() + "_" + bdcQlrDO.getZjh();
            map.put(key, 1);
        }
        // 遍历List2，在Map中查找List2的元素，找到则计数+1；未找到则放入map，计数1
        for (BdcQlrDO bdcQlrDO : qlr2) {
            String key = bdcQlrDO.getQlrmc() + "_" + bdcQlrDO.getZjh();
            Integer count = map.get(key);
            if (count != null) {
                map.put(key, ++count);
                continue;
            }
            map.put(key, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                result = false;
            }
        }
        return result;
    }


    /**
     * 处理字段名称的参数
     *
     * @param zdmcMap 字段名称配置
     * @return 处理后参数
     */
    private Map<String, List<String>> dealZdmcConfig(Map<String, String> zdmcMap) {
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String, String> entry : zdmcMap.entrySet()) {
            String tableName = entry.getKey();
            List<String> zdList = Arrays.asList(entry.getValue().split(","));
            result.put(tableName.toUpperCase(Locale.ROOT), zdList);
        }
        return result;
    }


    /**
     * 比较登记和权籍
     *
     * @param xmid     项目id
     * @param zdConfig 字段名称配置项
     * @return 数据校验结果
     */
    private List<BdcSjjyxxDTO> compareDjAndQj(String xmid, Map<String, List<String>> zdConfig) {
        List<BdcSjjyxxDTO> sjjyxxList = new ArrayList<>();
        try {
            // 登记系统和权籍
            List<BdcQjtbxxDTO> qjtbxxDTOS = bdcSynchService.queryLpbDataDzxx(xmid);
            // qjtbxxDTOS转换成sjjyxxList
            sjjyxxList = dealQjtbxxDTO(zdConfig, qjtbxxDTOS, SJLY_QJ);
        } catch (Exception e) {
            LOGGER.error("智能审核比较登记系统和权籍数据失败，失败信息：{},xmid:{}", e.getMessage(), xmid);
            throw new AppException("智能审核比较登记系统和权籍数据失败");
        }
        return sjjyxxList;
    }


    /**
     * 处理QjtbxxDTO，转化成需要的数据校验List<BdcSjjyxxDTO>
     *
     * @param zdConfig   字段配置项
     * @param qjtbxxDTOS 比较结果
     * @param sjly       数据来源
     * @return List<BdcSjjyxxDTO>
     */
    private List<BdcSjjyxxDTO> dealQjtbxxDTO(Map<String, List<String>> zdConfig, List<BdcQjtbxxDTO> qjtbxxDTOS, String sjly) {
        List<BdcSjjyxxDTO> sjjyxxList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(qjtbxxDTOS)) {
            for (BdcQjtbxxDTO qjtbxxDTO : qjtbxxDTOS) {
                List<String> zdmcList = zdConfig.get(qjtbxxDTO.getTable());
                if (CollectionUtils.isNotEmpty(zdmcList) && zdmcList.contains(qjtbxxDTO.getField())) {
                    BdcSjjyxxDTO sjjyxxDTO = new BdcSjjyxxDTO();
                    // 登记系统
                    sjjyxxDTO.setZdmc(qjtbxxDTO.getFieldDesc());
                    sjjyxxDTO.setSourceVal(qjtbxxDTO.getSourceVal());
                    // 比对
                    BdcSjjybdDTO bdDTO = new BdcSjjybdDTO();
                    bdDTO.setSfyz(qjtbxxDTO.getSfxd());
                    bdDTO.setSjly(sjly);
                    bdDTO.setValue(qjtbxxDTO.getTargetVal());
                    // 组装
                    List<BdcSjjybdDTO> bdDTOList = new ArrayList<>();
                    bdDTOList.add(bdDTO);
                    sjjyxxDTO.setBdcSjjyxxbdList(bdDTOList);
                    sjjyxxList.add(sjjyxxDTO);
                }
            }
        }
        return sjjyxxList;
    }


    /**
     * 比较登记和原产权
     *
     * @param xmid     项目id
     * @param zdConfig 字段配置项
     * @param bdcXmDO  项目信息
     * @return 数据校验结果
     */
    private List<BdcSjjyxxDTO> compareDjAndYcq(String xmid, Map<String, List<String>> zdConfig, BdcXmDO bdcXmDO) {
        List<BdcSjjyxxDTO> sjjyxxList = new ArrayList<>();
        // 先比较项目信息
        BdcXmQlxxDTO xmQlxxDTO = new BdcXmQlxxDTO();
        xmQlxxDTO.setBdcXm(bdcXmDO);
        // 原项目
        BdcXmQlxxDTO yxmQlxxDTO = new BdcXmQlxxDTO();
        BdcYxmQO bdcYxmQO = new BdcYxmQO();
        List<String> xmidList = new ArrayList<>();
        xmidList.add(xmid);
        bdcYxmQO.setXmidList(xmidList);
        List<BdcXmDO> yxm = bdcXmService.listYxmByYxmQO(bdcYxmQO);
        if (CollectionUtils.isEmpty(yxm)) {
            LOGGER.error("智能审核，原项目不存在,xmid:{}", xmid);
            return sjjyxxList;
        }
        yxmQlxxDTO.setBdcXm(yxm.get(0));
        BdcQl bdcQl = bdcQllxService.queryQllxDO(xmid);
        BdcQl ybdcQl = bdcQllxService.queryBdcYqlxx(xmid);
        String qlmc = bdcQllxService.getTableName(bdcQl);
        String yqlmc = bdcQllxService.getTableName(ybdcQl);
        // 权利相同时比较
        if (StringUtils.isNotBlank(qlmc) && qlmc.equals(yqlmc)) {
            xmQlxxDTO.setBdcQl(bdcQl);
            yxmQlxxDTO.setBdcQl(bdcQl);
        } else {
            LOGGER.info("智能审核，原权利与登记权利不一致，不进行比较,xmid:{},qlmc:{},yqlmc:{}", xmid, qlmc, yqlmc);
        }
        try {
            // 将数据转换成字段,并比较
            List<BdcQjtbxxDTO> qjtbxxDTOList = dealXmQlxxDTO(xmQlxxDTO, yxmQlxxDTO);
            sjjyxxList = dealQjtbxxDTO(zdConfig, qjtbxxDTOList, SJLY_YCQ);
        } catch (IllegalAccessException e) {
            LOGGER.error("智能审核,转换项目权利信息失败，失败信息：{}，xmid：{}", e.getMessage(), xmid);
            throw new AppException("智能审核，处理项目权利信息失败");
        }
        return sjjyxxList;

    }

    /**
     * 比较登记,原产权,权籍
     *
     * @param xmid     项目id
     * @param zdConfig 字段配置项
     * @param bdcXmDO  项目信息
     * @param sjlyList 数据来源
     * @return 数据校验结果
     */
    private List<BdcSjjyxxDTO> compareDjAndYcqAndQj(String xmid, Map<String, List<String>> zdConfig, BdcXmDO bdcXmDO, List<String> sjlyList) {
        List<BdcSjjyxxDTO> resultList = new ArrayList<>();

        List<BdcSjjyxxDTO> djAndYcq = compareDjAndYcq(xmid, zdConfig, bdcXmDO);
        List<BdcSjjyxxDTO> djAndQj = compareDjAndQj(xmid, zdConfig);
        if (CollectionUtils.isNotEmpty(djAndQj) && CollectionUtils.isNotEmpty(djAndYcq)) {
            for (BdcSjjyxxDTO djAndYcqDTO : djAndYcq) {
                for (BdcSjjyxxDTO djAndQjDTO : djAndQj) {
                    if (djAndYcqDTO.getZdmc().equals(djAndQjDTO.getZdmc())) {
                        BdcSjjyxxDTO result = new BdcSjjyxxDTO();
                        result.setZdmc(djAndYcqDTO.getZdmc());
                        result.setSourceVal(djAndYcqDTO.getSourceVal());
                        djAndYcqDTO.getBdcSjjyxxbdList().add(djAndQjDTO.getBdcSjjyxxbdList().get(0));
                        result.setBdcSjjyxxbdList(djAndYcqDTO.getBdcSjjyxxbdList());
                        resultList.add(result);
                    }
                }
            }
        } else if (CollectionUtils.isEmpty(djAndYcq)) {
            LOGGER.info("智能审核，登记与权籍，原产权比较，原产权数据为空，只比较登记与权籍,xmid:{},登记和原产权比较数据：{}，登记与权籍比较数据：{}", xmid, JSONObject.toJSONString(djAndYcq), JSONObject.toJSONString(djAndQj));
            sjlyList.remove(SJLY_YCQ);
            return djAndQj;
        } else if (CollectionUtils.isEmpty(djAndQj)) {
            LOGGER.info("智能审核，登记与权籍，原产权比较，权籍数据为空，只比较登记与原产权,xmid:{}，登记和原产权比较数据：{}，登记与权籍比较数据：{}", xmid, JSONObject.toJSONString(djAndYcq), JSONObject.toJSONString(djAndQj));
            sjlyList.remove(SJLY_QJ);
            return djAndYcq;
        }

        return resultList;
    }


    /**
     * 共享接口比较
     *
     * @param htxxDTO     合同信息
     * @param bdcXmDO     项目信息
     * @param ysjjyxxList 原数据校验结果
     * @param zdConfig    字段配置项
     * @return 增加共享接口数据后的数据校验结果
     */
    private List<BdcSjjyxxDTO> compareGXJK(FcjyClfHtxxDTO htxxDTO, BdcXmDO bdcXmDO, List<BdcSjjyxxDTO> ysjjyxxList, Map<String, List<String>> zdConfig) {
        BdcXmQlxxDTO xmQlxxDTO = new BdcXmQlxxDTO();
        xmQlxxDTO.setBdcXm(bdcXmDO);
        BdcXmQlxxDTO gxXmQlxxDTO = new BdcXmQlxxDTO();
        BdcXmDO bdcXmDO1 = new BdcXmDO();
        BeanUtils.copyProperties(htxxDTO.getBdcSlXmDO(), bdcXmDO1);
        gxXmQlxxDTO.setBdcXm(bdcXmDO1);
        List<BdcSjjyxxDTO> sjjyxxList = new ArrayList<>();
        try {
            // 将数据转换成字段,并比较
            List<BdcQjtbxxDTO> qjtbxxDTOList = dealXmQlxxDTO(xmQlxxDTO, gxXmQlxxDTO);
            // 登记和共享比较
            sjjyxxList = dealQjtbxxDTO(zdConfig, qjtbxxDTOList, SJLY_GXJK);
            if (CollectionUtils.isNotEmpty(ysjjyxxList)) {
                //在之前的基础上增加共享比较结果
                sjjyxxList = addGxjkResult(ysjjyxxList, sjjyxxList);
            }

        } catch (IllegalAccessException e) {
            LOGGER.error("智能审核,转换项目权利信息失败，{}", e.getMessage());
            throw new AppException("智能审核，处理项目权利信息失败");
        }

        return sjjyxxList;
    }

    /**
     * 在之前的基础上增加共享比较结果
     *
     * @param target 原来的数据校验结果
     * @param source 共享数据校验结果
     * @return
     */
    private List<BdcSjjyxxDTO> addGxjkResult(List<BdcSjjyxxDTO> target, List<BdcSjjyxxDTO> source) {
        for (BdcSjjyxxDTO ySjjy : target) {
            for (BdcSjjyxxDTO sjjy : source) {
                if (ySjjy.getZdmc().equals(sjjy.getZdmc())) {
                    // 增加比对的
                    ySjjy.getBdcSjjyxxbdList().add(sjjy.getBdcSjjyxxbdList().get(0));
                    ySjjy.setBdcSjjyxxbdList(ySjjy.getBdcSjjyxxbdList());
                } else {
                    // 不需要比对的设置为null
                    BdcSjjybdDTO bdDTO = new BdcSjjybdDTO();
                    bdDTO.setSjly(SJLY_GXJK);
                    ySjjy.getBdcSjjyxxbdList().add(bdDTO);
                    ySjjy.setBdcSjjyxxbdList(ySjjy.getBdcSjjyxxbdList());
                }
            }
        }
        return target;
    }


    /**
     * 转换BdcXmQlxxDTO，1.项目权利信息转换成按表字段存储；2.表字段中的字典值转换成中文
     * 比较source和target的数据
     *
     * @param source 登记
     * @param target 原项目或者共享数据
     * @return List<BdcQjtbxxDTO>
     * @throws IllegalAccessException
     */
    private List<BdcQjtbxxDTO> dealXmQlxxDTO(BdcXmQlxxDTO source, BdcXmQlxxDTO target) throws IllegalAccessException {
        List<BdcQjtbxxDTO> list = new ArrayList<>();
        // 获取BdcXmQlxxDTO的所有字段bdcxm，bdcql
        Field[] xmqlxxFields = BdcXmQlxxDTO.class.getDeclaredFields();
        for (Field resultField : xmqlxxFields) {
            resultField.setAccessible(true);
            Object sourceObj = resultField.get(source);
            Object targetObj = resultField.get(target);
            if (sourceObj == null && targetObj == null) {
                continue;
            }
            Class resultClass = null;
            String table;
            String tableDesc;
            if (sourceObj != null) {
                resultClass = sourceObj.getClass();
                // 获取@Table注解里name的值
                table = sourceObj.getClass().getAnnotation(Table.class).name();
                // 获取@ApiModel里description的值
                tableDesc = sourceObj.getClass().getAnnotation(ApiModel.class).description();
            } else {
                resultClass = targetObj.getClass();
                table = targetObj.getClass().getAnnotation(Table.class).name();
                tableDesc = targetObj.getClass().getAnnotation(ApiModel.class).description();
            }
            //取单个对象的所有字段，bdcxm或bdcql中的字段
            Field[] dtoFields = resultClass.getDeclaredFields();
            for (Field dtoField : dtoFields) {
                dtoField.setAccessible(true);
                BdcQjtbxxDTO bdcQjtbxxDTO = new BdcQjtbxxDTO();
                bdcQjtbxxDTO.setTable(table);
                bdcQjtbxxDTO.setTableDesc(tableDesc);
                bdcQjtbxxDTO.setField(dtoField.getName());
                String sourceVal;
                String targetVal;
                Zd zd = dtoField.getAnnotation(Zd.class);
                if (zd != null) {
                    sourceVal = sourceObj == null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(), dtoField.get(sourceObj), zd.tableClass()), dealDataFormat(dtoField.get(sourceObj)));
                    targetVal = targetObj == null ? StringUtils.EMPTY : StringUtils.defaultIfBlank(bdcZdCache.getFeildValue(zd.tableClass().getSimpleName(), dtoField.get(targetObj), zd.tableClass()), dealDataFormat(dtoField.get(targetObj)));
                } else {
                    sourceVal = sourceObj == null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(sourceObj));
                    targetVal = targetObj == null ? StringUtils.EMPTY : dealDataFormat(dtoField.get(targetObj));
                }
                bdcQjtbxxDTO.setFieldDesc(dtoField.getAnnotation(ApiModelProperty.class).value());
                bdcQjtbxxDTO.setSourceVal(sourceVal);
                bdcQjtbxxDTO.setTargetVal(targetVal);
                bdcQjtbxxDTO.setSfxd(StringUtils.equals(bdcQjtbxxDTO.getSourceVal(), bdcQjtbxxDTO.getTargetVal()) ? CommonConstantUtils.SF_S_DM : CommonConstantUtils.SF_F_DM);
                list.add(bdcQjtbxxDTO);
            }
        }
        return list;
    }

    /**
     * 格式化日期
     *
     * @param obj
     * @return
     */
    private String dealDataFormat(Object obj) {
        String result = StringUtils.EMPTY;
        if (obj != null) {
            if (obj instanceof Date) {
                result = DateUtils.formateYmdZw((Date) obj);
            } else {
                result = obj.toString();
            }
        }
        return result;
    }
}
