package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjxlPzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.register.service.BdcDypzService;
import cn.gtmap.realestate.register.service.BdcPrintService;
import cn.gtmap.realestate.register.service.BdcShbdPrintService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/21
 * @description 表单打印实现类
 */
@Service
public class BdcShbdPrintServiceImpl implements BdcShbdPrintService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcPrintService bdcPrintService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcDjxlPzRestService bdcDjxlPzRestService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcDypzService bdcDypzService;
    @Autowired
    BdcDysjPzService bdcDysjPzService;

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcShbdPrintServiceImpl.class.getName());
    /**
     * 打印需要传给数据源的节点的个数，不配置时，默认为3个
     */
    @Value("${shxx.jd-number:3}")
    String jdNum;
    /**
     * 非住房，主房 的房屋用途，如车库、阁楼、车位
     */
    @Value("${shxx.fwyt-fzf-tszf: 阁楼,车库,车位}")
    String fzfytStr;
    @Value("#{'${shxx.yszlc.gzldyid:}'.split(',')}")
    List<String> yszGzldyidList;

    @Value("#{'${print.spb.dylx:}'.split(',')}")
    private List<String> spbDylxList;

    @Value("#{'${print.onespb.dylx:}'.split(',')}")
    private List<String> oneSpbDylxList;

    /**
     * @param bdcPrintDTO 打印参数
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印的xml
     */
    @Override
    public String bdPrintXml(BdcPrintDTO bdcPrintDTO) {
        if ((StringUtils.isBlank(bdcPrintDTO.getGzlslid()) && StringUtils.isBlank(bdcPrintDTO.getGzlslids())) || StringUtils.isBlank(bdcPrintDTO.getDylx())) {
            throw new MissingArgumentException("审批表打印缺失gzlslid或dylx！");
        }
        List<Map> maps = new ArrayList<>(1);
        List<Map> resultMaps = new ArrayList<>(1);
        if (StringUtils.isNotBlank(bdcPrintDTO.getGzlslids())) {
            String[] gzlslidArray = bdcPrintDTO.getGzlslids().split(CommonConstantUtils.ZF_YW_DH);
            for (String gzlslid : gzlslidArray) {
                BdcPrintDTO printDTO = new BdcPrintDTO();
                printDTO.setGzlslid(gzlslid);
                printDTO.setDylx(bdcPrintDTO.getDylx());
                printDTO.setBdcUrlDTO(bdcPrintDTO.getBdcUrlDTO());
                printDTO.setPrintMode(bdcPrintDTO.getPrintMode());
                List jdmcList = new ArrayList();
                List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(gzlslid);
                userTaskDataList.forEach(taskData -> {
                    jdmcList.add(taskData.getName());
                });
                printDTO.setJdmcList(jdmcList);
                maps = getPrintMaps(printDTO);
                resultMaps.addAll(maps);
            }
        } else {
            maps = getPrintMaps(bdcPrintDTO);
            resultMaps.addAll(maps);
        }
        Map<String, List<Map>> paramMap = new HashMap(1);
        paramMap.put(bdcPrintDTO.getDylx(), resultMaps);
        return bdcPrintService.print(paramMap);
    }

    /**
     * @param bdcPrintDTO 打印参数
     * @param clmc        材料名称
     * @return Map<String, String>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 组织打印参数
     */
    private Map<String, Object> initParam(BdcPrintDTO bdcPrintDTO, String clmc) {
        Map<String, Object> map = new HashMap(5);
        map.put("gzlslid", bdcPrintDTO.getGzlslid());
        map.put("xmid", bdcPrintDTO.getXmid());
        map.put("clmc", clmc);
        map.put("signImageUrl", bdcPrintDTO.getBdcUrlDTO().getSignImageUrl());
        map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcPrintDTO.getSlbh());
        map.put("djxl", bdcPrintDTO.getDjxlListStr());
        initMapJdmc(map, bdcPrintDTO.getJdmcList(), bdcPrintDTO.getGzlslid());
        // 判断是否是注销流程
        map.put("yxmid", "");
        if (null != bdcPrintDTO.getZxlc() && bdcPrintDTO.getZxlc()) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcPrintDTO.getXmid(), null, 0);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                map.put("yxmid", bdcSlXmLsgxDOList.get(0).getYxmid());
            }
        }
        return map;
    }

    /**
     * @param xmid
     * @param gzlslid    工作流实例ID
     * @param sjclIdsStr
     * @return clmc
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程的材料名称
     */
    private String getClmc(String xmid, String gzlslid, String sjclIdsStr) {
        // 获取收件材料名称
        StringBuilder sjclMcSb = new StringBuilder();
        List<String> xmClmcList = bdcSlSjclFeignService.listSjclMc(gzlslid, sjclIdsStr);
        if (CollectionUtils.isNotEmpty(xmClmcList)) {
            for (int i = 1; i <= xmClmcList.size(); i++) {
                sjclMcSb.append(i).append(".").append(xmClmcList.get(i - 1)).append(" ");
            }
        }

        return sjclMcSb.toString();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程配置的审批表打印类型
     */
    @Override
    public Map<String, List<String>> getShxxDylx(String gzlslid) {
        Map<String, List<String>> dylxListMaps = new HashMap();
        if (StringUtils.isBlank(gzlslid)) {

            return dylxListMaps;
        }
        Map<String, Set<String>> dylxMaps = new HashMap();
        String[] djxlArr = bdcXmxxService.getListDjxlByGzlslid(gzlslid);
        ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzRestService.queryBdcDjxlPzByGzldyid(processInstanceData.getProcessDefinitionKey());
//        Set<String> dylxSet = new HashSet();
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                String djxl = bdcDjxlPzDO.getDjxl();
                if (StringUtils.isNotBlank(bdcDjxlPzDO.getSpbdylx()) && ArrayUtils.contains(djxlArr, djxl)) {
                    String[] spbdylxArr = StringUtils.split(bdcDjxlPzDO.getSpbdylx(), CommonConstantUtils.ZF_YW_DH);
                    for (String spbDylx : spbdylxArr) {
                        Set<String> djxlSet;
                        if (dylxMaps.containsKey(spbDylx)) {
                            djxlSet = dylxMaps.get(spbDylx);

                        } else {
                            djxlSet = new HashSet();
                        }
                        djxlSet.add(djxl);
                        dylxMaps.put(spbDylx, djxlSet);
                    }
                }
            }
        }
        // 将set转换为List
        for (Map.Entry<String, Set<String>> dylxSetMap : dylxMaps.entrySet()) {
            dylxListMaps.put(dylxSetMap.getKey(), new ArrayList(dylxSetMap.getValue()));
        }
        return dylxListMaps;
    }

    /**
     * @param bdcPrintDTO 打印参数
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印的xml（南通特供）
     */
    @Override
    public String bdPrintXmlNantong(BdcPrintDTO bdcPrintDTO) {
        if (StringUtils.isBlank(bdcPrintDTO.getGzlslid()) || StringUtils.isBlank(bdcPrintDTO.getDylx())) {
            throw new MissingArgumentException("审批表打印缺失gzlslid或dylx！");
        }
        String dylx = bdcPrintDTO.getDylx();
        List<Map> maps = new ArrayList();
        if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_SC, dylx)) {
            // 首次
            maps = initScSpbParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_GR, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DW, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_HY, dylx)) {
            // 个人 或 单位（两者只是打印模板不一样，其他都一致）
            maps = initSpbParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_GR_ZX, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DW_ZX, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_HYZX, dylx)) {
            // 个人或单位注销
            maps = initGrSpbZxParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DYA, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_PLHZ, dylx)) {
            // 抵押审批表(这里的批量汇总，在南通的需求里就是对批量抵押的一个表单)
            maps = initSpbParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DYA_ZX, dylx)) {
            // 抵押注销
            maps = initDyaSpbZxParam(bdcPrintDTO);
        }

        // 调用打印服务
        Map<String, List<Map>> paramMap = new HashMap(1);
        paramMap.put(dylx, maps);
        LOGGER.info("打印类型：{}。工作流示例ID：{}的打印参数为：{}：", bdcPrintDTO.getDylx(), bdcPrintDTO.getGzlslid(), maps.toString());
        return bdcPrintService.print(paramMap);
    }

    @Override
    public BdcDysjDTO getPrintDataMap(BdcPrintDTO bdcPrintDTO) {
        if (StringUtils.isBlank(bdcPrintDTO.getSlbh())) {
            BdcXmDO bdcXmDO = bdcXmxxService.getBdcXmByXmid(bdcPrintDTO.getXmid());
            bdcPrintDTO.setSlbh(bdcXmDO.getSlbh());
            bdcPrintDTO.setDjxlListStr(bdcXmDO.getDjxl());
        }
        String clmc = this.getClmc(bdcPrintDTO.getXmid(), bdcPrintDTO.getGzlslid(), bdcPrintDTO.getSjclIdsStr());
        Map configParam = this.initParam(bdcPrintDTO, clmc);
        String dylx = bdcPrintDTO.getDylx();
        if (StringUtils.isEmpty(dylx)) {
            throw new MissingArgumentException("打印类型");
        }
        /**
         * 获取主表配置
         */
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOS = bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOS)) {
            throw new AppException("打印配置主表不能为空");
        }
        /**
         * 获取字表配置
         */
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOS = bdcDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
        /**
         * 根据主表配置查询数据
         */
        Map dataMap = bdcDysjPzService.queryPrintDatasList(configParam, "bdcRegisterConfigMapper", bdcDysjPzDOS);
        /**
         * 根据子表配置查询数据
         */
        Multimap<String, List<Map>> detailMap = bdcDysjPzService.queryPrintDetailList(configParam, "bdcRegisterConfigMapper", bdcDysjZbPzDOS);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSON.toJSONString(dataMap));
        bdcDysjDTO.setDyzd(JSON.toJSONString(detailMap));
        return bdcDysjDTO;
    }

    /**
     * @param bdcPrintDTO 前台打印参数
     * @return 最终组装的打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成审批表的打印参数
     */
    private List<Map> initSpbParam(BdcPrintDTO bdcPrintDTO) {
        String gzlslid = bdcPrintDTO.getGzlslid();
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到项目信息");
        }
        // 设置受理编号的值
        bdcPrintDTO.setSlbh(bdcXmDOList.get(0).getSlbh());

        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcCshFwkgSlDOList)) {
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到初始化开关受理信息！");
        }

        // 根据djxl分组
        Map<String, List<BdcCshFwkgSlDO>> djxlGroupMap = bdcCshFwkgSlDOList.stream().collect(groupingBy(BdcCshFwkgSlDO::getDjxl));
        if (CollectionUtils.size(djxlGroupMap) == 1) {
            return this.initSpbParamCommon(bdcPrintDTO, djxlGroupMap);
        }

        // 分组数量不为1时，只获取当前打印类型对应的登记小类的项目信息
        String[] djxlArr = StringUtils.split(bdcPrintDTO.getDjxlListStr(), CommonConstantUtils.ZF_YW_DH);
        Map<String, List<BdcCshFwkgSlDO>> djxlGroupMapTemp = new HashMap();
        for (String djxl : djxlArr) {
            for (Map.Entry<String, List<BdcCshFwkgSlDO>> djxlGroup : djxlGroupMap.entrySet()) {
                if (StringUtils.equals(djxl, djxlGroup.getKey())) {
                    djxlGroupMapTemp.put(djxlGroup.getKey(), djxlGroup.getValue());
                }
            }
        }
        return this.initSpbParamCommon(bdcPrintDTO, djxlGroupMapTemp);
    }

    /**
     * @param bdcPrintDTO  前台打印参数
     * @param djxlGroupMap 已经根据具体的djxl过滤和分组的信息（只打印抵押审批表的信息或者是只打印个人审批表的信息）
     * @return 共同打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 抵押审批表和个人审批表打印的公共参数
     */
    private List<Map> initSpbParamCommon(BdcPrintDTO bdcPrintDTO, Map<String, List<BdcCshFwkgSlDO>> djxlGroupMap) {
        List<Map> maps = new ArrayList();
        // 根据djxl分组后的信息
        for (Map.Entry<String, List<BdcCshFwkgSlDO>> djxlGroup : djxlGroupMap.entrySet()) {
            List<BdcCshFwkgSlDO> bdcCshFwkgSlByDjxl = djxlGroup.getValue();

            // 证书序号为空的，默认一个项目生成一本证书
            List<BdcCshFwkgSlDO> zsxhIsNullList = bdcCshFwkgSlByDjxl.stream().filter(bdcCshFwkgSlDO -> null == bdcCshFwkgSlDO.getZsxh()).collect(Collectors.toList());
            for (BdcCshFwkgSlDO bdcCshFwkgSlDO : zsxhIsNullList) {
                Map<String, Object> map = new HashMap();
                initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlDO);
                String xmid = bdcCshFwkgSlDO.getId();
                BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
                map.put("zl", bdcXmDO.getZl());
                // 证书序号为空时，默认序号为项目id，写数据源时要对应将zsxh为空的值，处理成xmid
                map.put("zsxh", xmid);
                maps.add(map);
            }

            // 对证书序号不为空的，根据证书序号再分组
            List<BdcCshFwkgSlDO> zsxhNotNullList = bdcCshFwkgSlByDjxl.stream().filter(bdcCshFwkgSlDO -> null != bdcCshFwkgSlDO.getZsxh()).collect(Collectors.toList());
            Map<Integer, List<BdcCshFwkgSlDO>> zsxhGroupMap = zsxhNotNullList.stream().collect(groupingBy(BdcCshFwkgSlDO::getZsxh));
            for (Map.Entry<Integer, List<BdcCshFwkgSlDO>> zsxhGroup : zsxhGroupMap.entrySet()) {
                List<BdcCshFwkgSlDO> bdcCshFwkgSlByZsxh = zsxhGroup.getValue();
                if (CollectionUtils.size(bdcCshFwkgSlByZsxh) > 1) {
                    // 是否有主房
                    boolean yzf = false;
                    for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlByZsxh) {
                        // 多个物取主房的项目
                        if (CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfzf())) {
                            yzf = true;
                            Map<String, Object> map = new HashMap();
                            initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlDO);
                            // 多个物需要加"等"
                            map.put(Constants.ZL_SFX, "等");
                            // 多个物生成坐落信息
                            map.put("zl", generateMulZl(bdcCshFwkgSlByZsxh, bdcCshFwkgSlDO, yzf));
                            maps.add(map);
                            break;
                        }
                    }
                    if (!yzf) {
                        // 如果没有主房，取任意一条数据
                        Map<String, Object> map = new HashMap();
                        initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlByZsxh.get(0));
                        // 多个物需要加"等"
                        map.put(Constants.ZL_SFX, "等");
                        // 多个物生成坐落信息
                        map.put("zl", generateMulZl(bdcCshFwkgSlByZsxh, bdcCshFwkgSlByZsxh.get(0), yzf));
                        maps.add(map);
                    }
                } else {
                    Map<String, Object> map = new HashMap();
                    initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlByZsxh.get(0));
                    // 多个物生成坐落信息
                    map.put("zl", generateMulZl(bdcCshFwkgSlByZsxh, bdcCshFwkgSlByZsxh.get(0), false));
                    maps.add(map);
                }
            }
        }
        return maps;
    }

    /**
     * @param bdcCshFwkgSlByZsxh 一本证书上的物信息
     * @param bdcCshFwkgSlDO     选择做参数的xmid 的对应开关信息
     * @param yzf                当前证书是否已设置主房
     * @return String 拼接的坐落信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 一本证明书上有多个物，若物的个数大于3，则坐落显示其中一个物（主房）+等，
     * 若物小于3个，则坐落显示所有坐落，按照主房、阁楼、车库的顺序排序。
     */
    private String generateMulZl(List<BdcCshFwkgSlDO> bdcCshFwkgSlByZsxh, BdcCshFwkgSlDO bdcCshFwkgSlDO, boolean yzf) {
        int size = CollectionUtils.size(bdcCshFwkgSlByZsxh);
        String xmid = bdcCshFwkgSlDO.getId();
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        String zl = bdcXmDO.getZl();
        if (size > 3) {
            return zl + "等";
        } else if (size <= 3 && size > 1) {
            // 阁楼坐落
            String glZl = "";
            // 车库坐落
            String ckzl = "";
            // 车位坐落
            String cwzl = "";
            StringBuilder zlBuilder = new StringBuilder(zl).append(CommonConstantUtils.ZF_YW_DH);
            if (yzf) {
                // 有主房
                List<Map> fwytZd = bdcZdFeignService.queryBdcZd("fwyt");
                for (BdcCshFwkgSlDO bdcCshFwkgSlDOTemp : bdcCshFwkgSlByZsxh) {
                    if (!StringUtils.equals(xmid, bdcCshFwkgSlDOTemp.getId())) {
                        BdcXmDO bdcXmDOTemp = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcCshFwkgSlDOTemp.getId());
                        String fwyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getDzwyt(), fwytZd);
                        if (StringUtils.indexOf(fwyt, Constants.FWYT_GL) > -1) {
                            glZl = bdcXmDOTemp.getZl();
                            continue;
                        }
                        if (StringUtils.indexOf(fwyt, Constants.FWYT_CK) > -1) {
                            ckzl = bdcXmDOTemp.getZl();
                            continue;
                        }
                        if (StringUtils.indexOf(fwyt, Constants.FWYT_CW) > -1) {
                            cwzl = bdcXmDOTemp.getZl();
                            continue;
                        }
                        zlBuilder.append(bdcXmDOTemp.getZl()).append(CommonConstantUtils.ZF_YW_DH);
                    }
                }
                zlBuilder.append(StringUtils.isBlank(glZl) ? "" : glZl + CommonConstantUtils.ZF_YW_DH);
                zlBuilder.append(StringUtils.isBlank(ckzl) ? "" : ckzl + CommonConstantUtils.ZF_YW_DH);
                zlBuilder.append(StringUtils.isBlank(cwzl) ? "" : cwzl + CommonConstantUtils.ZF_YW_DH);

            } else {
                for (BdcCshFwkgSlDO bdcCshFwkgSlDOTemp : bdcCshFwkgSlByZsxh) {
                    if (!StringUtils.equals(xmid, bdcCshFwkgSlDOTemp.getId())) {
                        zlBuilder.append(entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcCshFwkgSlDOTemp.getId()).getZl()).append(CommonConstantUtils.ZF_YW_DH);
                    }
                }
            }
            zl = zlBuilder.toString();
            if (StringUtils.isNotBlank(zl)) {
                //返回拼接字符串，需要去掉最后多余的分隔符号
                zl = zl.substring(0, zl.length() - CommonConstantUtils.ZF_YW_DH.length());
            }
        }
        return zl;
    }

    /**
     * @param bdcPrintDTO 前台打印参数
     * @return 最终组装的打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成抵押注销类流程的打印参数（多个物也打印一个，目前所需参数同产权的个人审批表参数）
     * 后续抵押注销的参数有变化时，则继续完善该方法
     */
    private List<Map> initDyaSpbZxParam(BdcPrintDTO bdcPrintDTO) {
        return this.initGrSpbZxParam(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 前台打印参数
     * @return 最终组装的打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成个人审批表的注销流程的打印参数（多个物也打印一个）
     */
    private List<Map> initGrSpbZxParam(BdcPrintDTO bdcPrintDTO) {
        List<Map> maps = new ArrayList();
        String gzlslid = bdcPrintDTO.getGzlslid();

        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到项目信息");
        }
        bdcPrintDTO.setSlbh(bdcXmDOList.get(0).getSlbh());

        Map<String, Object> map = new HashMap();
        initPublicParam(map, bdcPrintDTO);
        map.put("xmid", bdcXmDOList.get(0).getXmid());
        if (CollectionUtils.size(bdcXmDOList) > 1) {
            map.put(Constants.ZL_SFX, "等");
        }
        maps.add(map);
        return maps;
    }

    /**
     * @param map            参数封装map
     * @param bdcPrintDTO    流程参数
     * @param bdcCshFwkgSlDO 流程初始化配置参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 封装个人审批表公共打印参数
     */
    private void initGrSpbPublicParam(Map<String, Object> map, BdcPrintDTO bdcPrintDTO, BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        initPublicParam(map, bdcPrintDTO);
        map.put("xmid", bdcCshFwkgSlDO.getId());
        map.put("djxl", bdcCshFwkgSlDO.getDjxl());
        map.put("zsxh", bdcCshFwkgSlDO.getZsxh());
    }


    /**
     * @param bdcPrintDTO 前台获取到的参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成首次审批表的打印参数
     */
    private List<Map> initScSpbParam(BdcPrintDTO bdcPrintDTO) {
        List<Map> maps = new ArrayList();
        String gzlslid = bdcPrintDTO.getGzlslid();

        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到项目信息");
        }

        Map<String, Object> map = new HashMap(5);
        // 节点信息
        initMapJdmc(map, bdcPrintDTO.getJdmcList(), bdcPrintDTO.getGzlslid());

        map.put("gzlslid", bdcPrintDTO.getGzlslid());
        map.put("signImageUrl", bdcPrintDTO.getBdcUrlDTO().getSignImageUrl());
        map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcXmDOList.get(0).getSlbh());
        if (CollectionUtils.size(bdcXmDOList) == 1) {
            map.put("xmid", bdcXmDOList.get(0).getXmid());
            map.put(Constants.ZL_SFX, CommonConstantUtils.ZF_KG_CHAR);
        } else {
            map.put(Constants.ZL_SFX, "等");
            map.put("xmid", getScSpbXmid(bdcXmDOList));
        }
        maps.add(map);
        return maps;
    }

    /**
     * @param bdcXmDOList 当前流程项目信息
     * @return 符合首次打印条件的xmid
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取符合打印条件的xmid（取其中一个物（非车库、阁楼））
     */
    private String getScSpbXmid(List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return null;
        }
        List<Map> fwytZd = bdcZdFeignService.queryBdcZd("fwyt");
        // 非主房用途标识
        String[] fzfytArr = StringUtils.split(fzfytStr, CommonConstantUtils.ZF_YW_DH);
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            String fwyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getDzwyt(), fwytZd);
            if (StringUtils.isBlank(fwyt)) {
                return bdcXmDO.getXmid();
            } else {
                boolean iszf = true;
                for (String fzfyt : fzfytArr) {
                    if (StringUtils.indexOf(fwyt, fzfyt) > -1) {
                        iszf = false;
                        break;
                    }
                }
                if (iszf) {
                    return bdcXmDO.getXmid();
                }
            }
        }
        return bdcXmDOList.get(0).getXmid();
    }

    /**
     * @param map         map参数
     * @param bdcPrintDTO 打印参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 初始化公共参数
     */
    private void initPublicParam(Map<String, Object> map, BdcPrintDTO bdcPrintDTO) {
        // 节点信息
        initMapJdmc(map, bdcPrintDTO.getJdmcList(), bdcPrintDTO.getGzlslid());
        map.put("gzlslid", bdcPrintDTO.getGzlslid());
        map.put("signImageUrl", bdcPrintDTO.getBdcUrlDTO().getSignImageUrl());
        map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcPrintDTO.getSlbh());
        map.put(Constants.ZL_SFX, CommonConstantUtils.ZF_KG_CHAR);
    }

    /**
     * @param map      map对象
     * @param jdmcList 节点List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 给map初始化节点名称参数
     */
    private void initMapJdmc(Map<String, Object> map, List<String> jdmcList, String gzlslid) {
        if (CollectionUtils.isNotEmpty(jdmcList)) {
            int i = 1;
            for (String jdmc : jdmcList) {
                map.put("jdmc" + i, jdmc);
                i++;
            }
            // 未配置的节点，补空
            while (i <= Integer.parseInt(jdNum)) {
                map.put("jdmc" + i, " ");
                i++;
            }
            if (CollectionUtils.isNotEmpty(yszGzldyidList) && StringUtils.isNotBlank(gzlslid)) {
                List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && yszGzldyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                    //一审制流程去除原来的所有节点信息，增加新的参数
                    // 未配置的节点，补空
                    int j = 1;
                    while (j <= jdmcList.size()) {
                        map.put("jdmc" + j, " ");
                        j++;
                    }
                    map.put("yszjdmc", jdmcList.get(0));
                } else {
                    map.put("yszjdmc", "");
                }
            } else {
                map.put("yszjdmc", "");
            }
        }
    }

    private List<Map> getPrintMaps(BdcPrintDTO bdcPrintDTO) {
        String gzlslid = bdcPrintDTO.getGzlslid();
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        String dylx = bdcPrintDTO.getDylx();
        boolean exitXmid = false;
        if (StringUtils.isNotBlank(bdcPrintDTO.getXmid())) {
            exitXmid = true;
        }
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("工作流实例ID：" + gzlslid + "。未查询到项目信息");
        }
        bdcPrintDTO.setSlbh(bdcXmDOList.get(0).getSlbh());

        List<Map> maps = new ArrayList();
        if (!Objects.equals(3, bdcPrintDTO.getPrintMode())) {
            if (ArrayUtils.contains(Constants.DYLX_SPB_ARR, dylx)) {
                // 非合并打印
                String clmc = "";
                // 批量流程 获取 材料名称
                if (CollectionUtils.size(bdcXmDOList) > 2) {
                    clmc = getClmc("", gzlslid, bdcPrintDTO.getSjclIdsStr());
                }
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    String xmid = bdcXmDO.getXmid();
                    if (StringUtils.isBlank(clmc)) {
                        clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
                    }
                    //DTO里面没有xmid
                    if (!exitXmid) {
                        bdcPrintDTO.setXmid(xmid);
                    }
                    maps.add(initParam(bdcPrintDTO, clmc));
                    if (bdcPrintDTO.getDysl() != null && bdcPrintDTO.getDysl().equals(1) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
                        break;
                    }
                }
            } else if (ArrayUtils.contains(Constants.DYLX_SPB_PLHB_ARR, dylx) || spbDylxList.contains(dylx)) {
                Set<BdcXmDO> newBdcXm = null;
                List<BdcCshFwkgSlDO> cshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                    newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                    //项目分组数据
                    Map<Object, List<BdcCshFwkgSlDO>> groupFwkgSlMap = new HashMap<>();
                    //先查询出证书序号为空的或者不是抵押权的数据
                    List<BdcCshFwkgSlDO> nullZsxhBdcCshFwkgSlDOS = cshFwkgSlDOList
                            .stream().filter(xm -> xm.getZsxh() == null ||
                                    !Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, xm.getQllx()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(nullZsxhBdcCshFwkgSlDOS)) {
                        if (xmlx == 3) {
                            //为空的按登记小类分组
                            Map<String, List<BdcCshFwkgSlDO>> djxlXmMap = nullZsxhBdcCshFwkgSlDOS
                                    .stream()
                                    .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                            if (MapUtils.isNotEmpty(djxlXmMap)) {
                                groupFwkgSlMap.putAll(djxlXmMap);
                            }
                            cshFwkgSlDOList.removeAll(nullZsxhBdcCshFwkgSlDOS);
                        } else {
                            Map<String, List<BdcCshFwkgSlDO>> xmMap = nullZsxhBdcCshFwkgSlDOS
                                    .stream()
                                    .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getId));
                            if (MapUtils.isNotEmpty(xmMap)) {
                                groupFwkgSlMap.putAll(xmMap);
                            }
                            cshFwkgSlDOList.removeAll(nullZsxhBdcCshFwkgSlDOS);
                        }
                    }
                    //不为空或抵押权按照证书序号分组
                    if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                        Map<Integer, List<BdcCshFwkgSlDO>> zsxhXmMap = cshFwkgSlDOList
                                .stream()
                                .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                        if (MapUtils.isNotEmpty(zsxhXmMap)) {
                            groupFwkgSlMap.putAll(zsxhXmMap);
                        }
                    }
                    //处理需要打印的数据
                    if (MapUtils.isNotEmpty(groupFwkgSlMap)) {
                        List<String> xmids = new ArrayList<>();
                        for (Map.Entry<Object, List<BdcCshFwkgSlDO>> objectListEntry : groupFwkgSlMap.entrySet()) {
                            xmids.add(objectListEntry.getValue().get(0).getId());
                        }
                        List<BdcXmDO> printXmList = bdcXmDOList.stream().filter(bdcxm -> xmids.contains(bdcxm.getXmid())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(printXmList)) {
                            newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getXmid));
                            newBdcXm.addAll(printXmList);
                        } else {
                            newBdcXm.addAll(bdcXmDOList);
                        }
                    } else {
                        newBdcXm.addAll(bdcXmDOList);
                    }
                } else {
                    newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                    newBdcXm.addAll(bdcXmDOList);
                }

                for (BdcXmDO bdcXm : newBdcXm) {
                    // 批量合并打印（批量组合按照登记小类分组打印）
                    String xmid = bdcXm.getXmid();
                    String clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
                    //DTO里面没有xmid
                    if (!exitXmid) {
                        bdcPrintDTO.setXmid(xmid);
                    }
                    maps.add(initParam(bdcPrintDTO, clmc));
                    if (bdcPrintDTO.getDysl() != null && bdcPrintDTO.getDysl().equals(1) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
                        break;
                    }
                }
//                // 登记小类分组
//                Set<String> djxlSet = new HashSet();
//                for (BdcXmDO bdcXmDO : bdcXmDOList) {
//                    if (!djxlSet.contains(bdcXmDO.getDjxl())) {
//                        djxlSet.add(bdcXmDO.getDjxl());
//
//                        // 批量合并打印（批量组合按照登记小类分组打印）
//                        String xmid = bdcXmDO.getXmid();
//                        String clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
//                        //DTO里面没有xmid
//                        if (!exitXmid) {
//                            bdcPrintDTO.setXmid(xmid);
//                        }
//                        maps.add(initParam(bdcPrintDTO, clmc));
//                        if (bdcPrintDTO.getDysl() != null && bdcPrintDTO.getDysl().equals(1) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
//                            break;
//                        }
//                    }
//                }
            }
        } else if (Objects.equals(3, bdcPrintDTO.getPrintMode()) && CollectionUtils.isNotEmpty(spbDylxList) && spbDylxList.contains(dylx) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
            //常州mode=3 且自定义配置了审批表打印类型，审批表打印一份
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            String xmid = bdcXmDO.getXmid();
            String clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
            //DTO里面没有xmid
            if (!exitXmid) {
                bdcPrintDTO.setXmid(xmid);
            }
            maps.add(initParam(bdcPrintDTO, clmc));
        }
        return maps;
    }
}
