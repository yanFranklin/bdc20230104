package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.realestate.common.matcher.OrganizationManagerClientMatcher;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.bdcdsflog.BdcDsfLogMapper;
import cn.gtmap.realestate.exchange.core.vo.BdcDsfRzTjYsVO;
import cn.gtmap.realestate.exchange.service.impl.inf.log.BdcDsfLogInAllServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.log.BdcDsfRzYsPzServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.log.BdcDsfLogService;
import cn.gtmap.realestate.exchange.util.ExportExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @version 1.0
 * @description 不动产第三方日志台账
 */
@Controller
@RequestMapping("/realestate-exchange/bdcDsfLog")
public class BdcDsfLogController {

    private static final Logger logger = LoggerFactory.getLogger(BdcDsfLogController.class);

    @Autowired
    private OrganizationManagerClientMatcher organizationManagerClient;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Resource(name = "bdcDsfLogInEsServiceImpl")
    private BdcDsfLogService bdcDsfLogInEsServiceImpl;

    @Resource(name = "bdcDsfLogServiceImpl")
    private BdcDsfLogService bdcDsfLogServiceImpl;

    @Resource(name = "bdcDsfLogInAllServiceImpl")
    private BdcDsfLogInAllServiceImpl bdcDsfLogInAllService;

    @Autowired
    private WorkFlowUtils workFlowUtils;

    // 查询类型 为 查询ES 日志
    private static final String LOG_IN_ES = "logines";

    // 查询类型 为 查询数据库日志
    private static final String LOG_IN_DB = "logindb";

    // 查询类型 为 所有数据
    private static final String LOG_IN_ALL = "loginall";

    @Autowired
    private BdcDsfRzYsPzServiceImpl bdcDsfRzYsPzService;
    @Autowired
    private BdcDsfLogMapper bdcDsfLogMapper;


    /**
     * @param zdNames 逗号隔开的字典名称
     * @return java.util.Map
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @RequestMapping("/zdMul")
    @ResponseBody
    public Map mulListZd(String zdNames) {
        if (StringUtils.isNotBlank(zdNames)) {
            String[] arr = zdNames.split(",");
            Map<String, List<Map>> resultMap = Maps.newHashMapWithExpectedSize(arr.length);
            if (ArrayUtils.isNotEmpty(arr)) {
                for (String zdName : arr) {
                    if (StringUtils.equals("djbmdm", zdName)) {
                        List<Map> list = new ArrayList<>();
                        List<OrganizationDto> organizationDtoList = this.organizationManagerClient.findRootOrgs();
                        if (CollectionUtils.isNotEmpty(organizationDtoList)) {
                            for (OrganizationDto organizationDto : organizationDtoList) {
                                Map map = new HashMap();
                                map.put("DM", organizationDto.getCode());
                                map.put("MC", organizationDto.getName());
                                list.add(map);
                            }
                        }
                        resultMap.put(zdName, list);
                    } else {
                        resultMap.put(zdName, bdcZdFeignService.queryBdcZd(zdName));
                    }
                }
            }
            return resultMap;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/queryZdList", method = RequestMethod.POST)
    public Object queryZdList(String zdmc) {
        /*List<Map> result = new ArrayList<>(3);
        if (StringUtils.equals("qx",zdmc)) {
            Map map = new HashMap<>();
            map.put("DM","320100");
            map.put("MC","南京");
            result.add(map);
        } else if (StringUtils.equals("gxbmbz",zdmc)) {
            Map map = new HashMap<>();
            map.put("DM","WWSQ");
            map.put("MC","互联网");
            result.add(map);
        }*/
        return bdcZdFeignService.queryBdcZd(zdmc);
    }

    @ResponseBody
    @RequestMapping(value = "/queryOrgList", method = RequestMethod.POST)
    public Object queryOrgList() {
        return this.organizationManagerClient.findRootOrgs();
    }

    /**
     * 调用大云接口获取工作流定义名称
     *
     * @return: java.lang.Object
     * @description
     */
    @ResponseBody
    @RequestMapping(value = "/queryGzldymcList", method = RequestMethod.POST)
    public Object queryGzldymcList() {
        List<ProcessDefData> processDefDataList = workFlowUtils.getAllProcessDefData();
        processDefDataList = processDefDataList.stream().filter(processDefData ->
                processDefData.getSuspensionState() == 1
        ).collect(Collectors.toList());
        return processDefDataList;
    }

    @ResponseBody
    @GetMapping(value = "/getInterfaceLogMode")
    public CommonResponse<BdcDwJkDO> getInterfaceLogMode(@RequestParam(name = "interfaceId") String interfaceId) {
        return bdcDsfLogInAllService.getInterfaceLogMode(interfaceId);
    }

    @ResponseBody
    @GetMapping(value = "/listInterfaceLogMode")
    public CommonResponse<List<BdcDwJkDO>> listInterfaceLogMode(@RequestParam(name = "interfaceIds") String interfaceIds) {
        return bdcDsfLogInAllService.listInterfaceLogMode(interfaceIds);
    }

    @ResponseBody
    @RequestMapping(value = "/getBdcDsfLogPagesJson", method = RequestMethod.POST)
    public Object getAccessJson(@LayuiPageable Pageable pageable, String gxbmbz,
                                String jkmc, String slbh, String xmid, String bdcdz,
                                String dsfdz, String kssj, String jssj,
                                String type, String bdcjs, String jkid, String czr,
                                String alias,String bmid,
                                String jkmcmh,String qxdm) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(jkmcmh)) {
            map.put("jkmcmh", jkmcmh);
        }
        if (StringUtils.isNotBlank(slbh)) {
            map.put("slbh", slbh);
        }
        if (StringUtils.isNotBlank(xmid)) {
            map.put("xmid", xmid);
        }
        if (StringUtils.isNotBlank(jkid)) {
            map.put("jkid", jkid);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(dsfdz)) {
            map.put("dsfdz", dsfdz);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        if (StringUtils.isNotBlank(czr)) {
            map.put("czr", czr);
        }
        if (StringUtils.isNotBlank(bmid)) {
            map.put("bmid", bmid);
        }
        if (StringUtils.isNotBlank(alias)) {
            map.put("alias", alias);
        }
        if (StringUtils.isNotBlank(qxdm)) {
            map.put("qxdm", qxdm);
        }
        logger.info("查询日志入参:{},查询类型:{}",map.toString(),type);
        return getLogServiceWithType(type).listBdcDsfRzByPage(pageable, map);
    }

    @ResponseBody
    @RequestMapping(value = "/countQqsl", method = RequestMethod.POST)
    public Object countQqsl(String fzxmc, String gzldymc, String gxbmbz, String jkmc,String jkmcs, String kssj
            , String jssj, String type, String bdcjs) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(fzxmc)) {
            map.put("fzxmc", fzxmc);
        }
        if (StringUtils.isNotBlank(gzldymc)) {
            map.put("gzldymc", gzldymc);
        }
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(jkmcs)) {
            map.put("jkmcs", jkmcs);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        initListParam(map);
        return getLogServiceWithType(type).countBdcDsfLogByGxbmbz(map);
    }

    private void initListParam(HashMap map) {
        //jkmcs是给db查询用的
        if (map.containsKey("jkmcs") && map.get("jkmcs") != null) {
            String jkmcs = (String) map.get("jkmcs");
            map.put("jkmcs", jkmcs.split(","));
            map.put("jkmc",jkmcs);
        }
        if (map.containsKey("jkmc") && map.get("jkmc") != null) {
            String jkmc = (String) map.get("jkmc");
            if (jkmc.contains(",")) {
                map.put("jkmcs", jkmc.split(","));
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/countDyqs", method = RequestMethod.POST)
    public Object countDyqs(String fzxmc, String gzldymc, String gxbmbz, String jkmc,String jkmcs, String kssj
            , String jssj, String type, String bdcjs) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(fzxmc)) {
            map.put("fzxmc", fzxmc);
        }
        if (StringUtils.isNotBlank(gzldymc)) {
            map.put("gzldymc", gzldymc);
        }
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(jkmcs)) {
            map.put("jkmcs", jkmcs);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        initListParam(map);
        return getLogServiceWithType(type).countBdcDsfLogXtdyqs(map);
    }

    @ResponseBody
    @RequestMapping(value = "/countMx", method = RequestMethod.POST)
    public Object countMx(String fzxmc, String gzldymc, String gxbmbz, String jkmc,String jkmcs, String kssj
            , String jssj, String type, String bdcjs) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(fzxmc)) {
            map.put("fzxmc", fzxmc);
        }
        if (StringUtils.isNotBlank(gzldymc)) {
            map.put("gzldymc", gzldymc);
        }
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        if (StringUtils.isNotBlank(jkmcs)) {
            map.put("jkmcs", jkmcs);
        }
        initListParam(map);
        return getLogServiceWithType(type).countMx(map);
    }

    /**
     * @return gxbmgs 共享部门个数
     * jkzs 接口总数
     * cgzs 成功总数
     * sbzs 失败总数
     * pjxysj 平均响应时间
     */
    @ResponseBody
    @RequestMapping(value = "/countZj", method = RequestMethod.POST)
    public Object countZj(String fzxmc, String gzldymc, String gxbmbz, String jkmc,String jkmcs,
                          String kssj, String jssj, String type, String bdcjs) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(fzxmc)) {
            map.put("fzxmc", fzxmc);
        }
        if (StringUtils.isNotBlank(gzldymc)) {
            map.put("gzldymc", gzldymc);
        }
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        if (StringUtils.isNotBlank(jkmcs)) {
            map.put("jkmcs", jkmcs);
        }
        initListParam(map);
        return getLogServiceWithType(type).countZj(map);
    }

    @ResponseBody
    @RequestMapping(value = "/countGxtDymx", method = RequestMethod.POST)
    public Object countGxtDymx(String gxbmmc, String fzxmc, String gzldymc,
                               String gxbmbz, String jkmc, String kssj, String jssj, String type, String bdcjs) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(gxbmmc)) {
            map.put("gxbmmc", gxbmmc);
        }
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(fzxmc)) {
            map.put("fzxmc", fzxmc);
        }
        if (StringUtils.isNotBlank(gzldymc)) {
            map.put("gzldymc", gzldymc);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        initListParam(map);
        return getLogServiceWithType(type).countGxtDymx(map);
    }


    /**
     * @param type
     * @return cn.gtmap.realestate.exchange.service.inf.log.BdcDsfLogService
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 判断使用哪种 查询服务
     */
    private BdcDsfLogService getLogServiceWithType(String type) {
        if (LOG_IN_DB.equals(type)) {
            return bdcDsfLogServiceImpl;
        } else if (LOG_IN_ES.equals(type)) {
            return bdcDsfLogInEsServiceImpl;
        } else {
            return bdcDsfLogInAllService;
        }
    }

    @RequestMapping(value = "/exportExcelDsfrz")
    public void exportExcelDsfrz(HttpServletResponse response, String fzxmc, String gzldymc,
                                 String gxbmbz, String jkmc, String kssj, String jssj, String bdcjs) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(fzxmc)) {
            map.put("fzxmc", fzxmc);
        }
        if (StringUtils.isNotBlank(gzldymc)) {
            map.put("gzldymc", gzldymc);
        }
        if (StringUtils.isNotBlank(gxbmbz)) {
            map.put("gxbmbz", gxbmbz);
        }
        if (StringUtils.isNotBlank(jkmc)) {
            map.put("jkmc", jkmc);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(bdcjs)) {
            map.put("bdcjs", bdcjs);
        }
        ExportExcelUtil.exportExcelBdcDsfLog(response, exportExcelBdcDsfLog(), exportExcelLogFzxmc(map), mulListZd("gxbmbz,djbmdm"));
    }

    /**
     * @return
     * @description 导出excel获取头信息
     */
    private List<JSONObject> exportExcelBdcDsfLog() {
        List<JSONObject> result = new ArrayList<>(3);
        List<Map> mapList = bdcDsfLogMapper.countBdcDsfLogByGxbmbz(new HashMap<>());
        if (CollectionUtils.isNotEmpty(mapList)) {
            Map mxMap = new HashMap<>();
            for (Map map : mapList) {
                List<String> mcList = new ArrayList<>(5);
                String gxbmbz = String.valueOf(map.get("GXBMBZ"));

                JSONObject gxbmbzJob = new JSONObject();
                gxbmbzJob.put("GXBMBZ", gxbmbz);
                result.add(gxbmbzJob);

                mxMap.put("gxbmbz", gxbmbz);
                List<Map> mxList = bdcDsfLogMapper.countGxtMx(mxMap);
                if (CollectionUtils.isNotEmpty(mxList)) {
                    for (Map mapMx : mxList) {
                        mcList.add(String.valueOf(mapMx.get("MC")));
                    }
                    gxbmbzJob.put("JKMC", mcList);
                }
            }
        }
        return result;
    }

    /**
     * @param paramMap
     * @return
     * @description 导出excel获取导出数据
     */
    private List<JSONObject> exportExcelLogFzxmc(Map paramMap) {
        List<JSONObject> result = new ArrayList<>(3);
        List<Map> mapList = bdcDsfLogMapper.countBdcDsfLogByFzx(paramMap);
        if (CollectionUtils.isNotEmpty(mapList)) {
            Map mxMap = new HashMap<>();
            for (Map map : mapList) {
                JSONObject resultData = new JSONObject();
                String djbmdm = (String) map.get("DJBMDM");
                mxMap.put("gxbmbz", map.get("GXBMBZ"));
                mxMap.put("fzxmc", StringUtils.isEmpty(djbmdm) ? "" : djbmdm);
                resultData.put("MX", bdcDsfLogMapper.countGxtMxFzx(mxMap));
                resultData.put("GXBMBZ", map.get("GXBMBZ"));
                resultData.put("DJBMDM", StringUtils.isEmpty(djbmdm) ? "" : djbmdm);
                result.add(resultData);
            }
        }
        return result;
    }

    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.vo.BdcDsfRzTjYsVO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 颜色配置页面 获取颜色配置列表
     */
    @RequestMapping("/getyspzlist")
    @ResponseBody
    public List<BdcDsfRzTjYsVO> getYsPzList() {
        return bdcDsfRzYsPzService.getYsPzList();
    }

    /**
     * @param yspzJson
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存颜色配置
     */
    @RequestMapping("/saveyspz")
    @ResponseBody
    public void saveYsPz(String yspzJson) {
        bdcDsfRzYsPzService.saveYspz(yspzJson);
    }

}
