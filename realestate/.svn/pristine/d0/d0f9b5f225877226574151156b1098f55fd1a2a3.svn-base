package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcNewPrintDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPrintDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcFzjlFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsPirntFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcXtLscsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcLogVO;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/07/27
 * @description 查询子系统打印
 */
@RestController
@RequestMapping("/rest/v1.0/print")
public class BdcPrintController extends BaseController {

    @Value("#{'${slym.print.pldylx:sjd}'.split(',')}")
    private List<String> pldyList;

    /**
     * 打印内网地址
     */
    @Value("${url.print-ip:}")
    private String printIp;

    /**
     * 审核登簿UI地址
     */
    @Value("${url.register-ui:}")
    private String registerUi;

    /**
     * 证书归档服务地址
     */
    @Value("${url.certificate:}")
    private String certificateUrl;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    private String signImageUrl;

    @Autowired
    private BdcXtLscsFeignService bdcXtLscsFeignService;
    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    private BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcDjxlPzFeignService bdcDjxlPzFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcShxxFeignService bdcShxxFeignService;
    @Autowired
    BdcFzjlFeignService bdcFzjlFeignService;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    BdcZsPirntFeignService bdcZsPirntFeignService;
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 房产证明处理服务
     */
    @Autowired
    private BdcBzbZmFeignService bdcBzbZmFeignService;

    @Autowired
    private UserManagerUtils userManagerUtils;
    /**
     * 保存打印注销清册数据查询参数
     * @param xmDOList 项目查询参数
     * @return {String} 缓存到临时参数表的标识
     */
    @PostMapping("/zxqc/param")
    public String saveZxqcPrintParam(@RequestBody List<BdcXmDO> xmDOList) {
        if(CollectionUtils.isEmpty(xmDOList)) {
            throw new MissingArgumentException("未定义要打印的内容");
        }

        List<BdcXtLscsDO> lscsDOList = new ArrayList<>(xmDOList.size());
        String paramKey = UUIDGenerator.generate16();
        for(BdcXmDO bdcXmDO : xmDOList) {
            BdcXtLscsDO lscsDO = new BdcXtLscsDO();
            lscsDO.setCsmc(paramKey);
            lscsDO.setCsz(bdcXmDO.getXmid());
            lscsDO.setSfsc(CommonConstantUtils.SF_S_DM);
            lscsDOList.add(lscsDO);
        }

        bdcXtLscsFeignService.addValues(lscsDOList);
        return paramKey;
    }

    /**
     * 保存打印参数到临时数据表
     * @param paramList 查询参数
     * @return {String} 缓存到临时参数表的标识
     */
    @PostMapping("/params")
    public String savePrintParam(@RequestBody List<List<Object>> paramList) {
        if(CollectionUtils.isEmpty(paramList)) {
            throw new MissingArgumentException("未定义要打印的内容");
        }

        List<BdcXtLscsDO> lscsDOList = new ArrayList<>(paramList.size());
        String paramKey = UUIDGenerator.generate16();
        for(List<Object> params : paramList) {
            BdcXtLscsDO lscsDO = new BdcXtLscsDO();
            lscsDO.setCsmc(paramKey);
            lscsDO.setSfsc(CommonConstantUtils.SF_S_DM);
            lscsDOList.add(lscsDO);

            if(CollectionUtils.size(params) > 0 && null != params.get(0)) {
                lscsDO.setCsz(String.valueOf(params.get(0)));
            }
            if(CollectionUtils.size(params) > 1 && null != params.get(1)) {
                lscsDO.setCsz1(String.valueOf(params.get(1)));
            }
            if(CollectionUtils.size(params) > 2 && null != params.get(2)) {
                lscsDO.setCsz2(String.valueOf(params.get(2)));
            }
            if(CollectionUtils.size(params) > 3 && null != params.get(3)) {
                lscsDO.setCsz3(String.valueOf(params.get(3)));
            }
        }

        bdcXtLscsFeignService.addValues(lscsDOList);
        return paramKey;
    }

    /**
     * 获取自定义查询打印XML数据（打印数据源根据临时缓存数据进行查询）
     * @param dylx 打印类型
     * @param datakey 临时缓存数据标识
     * @return {String} 打印XML数据
     */
    @GetMapping("/data/{dylx}/{dataKey}/xml")
    public String getPrintXml(@PathVariable("dylx")String dylx, @PathVariable("dataKey")String datakey) {
        if(StringUtils.isBlank(dylx) || StringUtils.isBlank(datakey)) {
            throw new MissingArgumentException("打印缺失参数");
        }

        // 生成 年月日6位流水号 编号（新需求不同的话可以继续else修改）
        Integer lsh = bdcBhFeignService.queryLsh(dylx, "DAY");
        String cxbh = DateFormatUtils.format(new Date(), DateUtils.sdf_ymd) + StringToolUtils.appendZero(String.valueOf(lsh), 6);

        List<Map> paramList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("datakey", datakey);
        param.put("bh", cxbh);
        param.put("zwrq", StringToolUtils.getChineseDate());
        param.put("cxfw", bdcBzbZmFeignService.getCxfw());
        paramList.add(param);
        Map<String, List<Map>> paramMap = new HashMap<>(1);
        paramMap.put(dylx, paramList);

        String xml = bdcPrintFeignService.print(paramMap);
        BdcLogVO bdcLogVO = new BdcLogVO();
        bdcLogVO.setCxjg(xml);
        bdcLogVO.setZmbh(cxbh);
        String redisKey = CommonConstantUtils.REDIS_ZDYCX_PRINT + datakey;
        redisUtils.addStringValue(redisKey, JSON.toJSONString(bdcLogVO), 600);

        return xml;
    }

    /**
     * 获取注销清册的打印数据
     * @param lscsmc 临时参数名称
     * @return {String} 打印XML数据
     */
    @GetMapping("/zxqc/{lscsmc}/xml")
    public String zxqc(@PathVariable("lscsmc")String lscsmc) {
        if (StringUtils.isBlank(lscsmc)) {
            throw new MissingArgumentException("未定义要打印的内容");
        }

        Map<String, List<Map>> paramMap = new HashMap();
        HashMap map = new HashMap();
        map.put("lscsmc", lscsmc);

        paramMap.put("zxqc", Collections.singletonList(map));
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * 保存自定义查询打印参数
     * @param paramList 参数（默认选中行所有字段数据）
     * @return {String} 缓存到Redis key
     */
    @PostMapping("/zdycx/param")
    public String saveZdycxPrintParam(@RequestBody List<Map<String, Object>> paramList) {
        if(CollectionUtils.isEmpty(paramList)) {
            throw new MissingArgumentException("自定义查询打印缺失参数");
        }

        String redisKey = CommonConstantUtils.REDIS_ZDYCX_PRINT + UUIDGenerator.generate16();
        return redisUtils.addStringValue(redisKey, JSON.toJSONString(paramList), 600);
    }

    /**
     * 获取自定义查询打印XML数据
     * @param dylx 打印类型
     * @param redisKey 参数缓存到Redis key
     * @return {String} 打印XML数据
     */
    @GetMapping("/zdycx/{dylx}/{redisKey}/xml")
    public String getZdycxPrintXml(@PathVariable("dylx")String dylx, @PathVariable("redisKey")String redisKey) {
        if(StringUtils.isBlank(dylx) || StringUtils.isBlank(redisKey)) {
            throw new MissingArgumentException("自定义查询打印缺失参数");
        }

        String data = redisUtils.getStringValue(redisKey);
        if(StringUtils.isBlank(data)){
            throw new AppException("自定义查询打印失败：未获取到要打印的数据");
        }

        List<Map> paramList = JSON.parseArray(data, Map.class);
        List<Map> newParamList = new ArrayList<>(paramList.size());

        for(Map<String, Object> param : paramList) {
            Map<String, Object> newParam = new HashMap<>();
            for(Map.Entry<String, Object> entry : param.entrySet()) {
                // 将参数键名转小写（自定义查询页面字段名称都是大写）
                newParam.put(StringUtils.lowerCase(entry.getKey()), entry.getValue());
            }
            newParamList.add(newParam);
        }

        Map<String, List<Map>> paramMap = new HashMap<>(1);
        paramMap.put(dylx, newParamList);

        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param bdcSlPrintDTO
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 批量标准版打印参数组织 只支持相同打印类型
     * @date : 2022/6/28 11:14
     */
    @ResponseBody
    @GetMapping("/standard/pl")
    public BdcNewPrintDTO generateStandardPrintPl(BdcSlPrintDTO bdcSlPrintDTO) {
        // 生成打印类型
        Set<String> dylxSet = new HashSet<>(1);
        String gzlslids = bdcSlPrintDTO.getGzlslids();
        if (StringUtils.isNotBlank(gzlslids)) {
            String[] gzlslidArr = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
            if (gzlslidArr.length > 0) {
                for (String gzlslid : gzlslidArr) {
                    bdcSlPrintDTO.setGzlslid(gzlslid);
                    getSjdSqsDylx(bdcSlPrintDTO);
                    String dylx = bdcSlPrintDTO.getDylx();
                    if (CollectionUtils.isNotEmpty(pldyList) && pldyList.contains(bdcSlPrintDTO.getDylx())) {
                        int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                        if (Objects.equals(CommonConstantUtils.LCLX_PL, lclx)) {
                            dylx += "pl";
                        }
                    }
                    dylxSet.add(dylx);
                }
            }
        }
        // 确保只有一个打印类型
        BdcNewPrintDTO bdcNewPrintDTO = new BdcNewPrintDTO();
        if (dylxSet.size() == 1) {
            bdcNewPrintDTO = bdcSlPrintFeignService.generatePrintPlDTO(bdcSlPrintDTO);
            String modelurl = bdcSlPrintDTO.getUrl() + "/static/printModel/" + bdcSlPrintDTO.getDylx() + CommonConstantUtils.WJHZ_FR3;
            bdcNewPrintDTO.setModelurl(modelurl);
            bdcNewPrintDTO.setDylx(dylxSet.stream().findFirst().get());
        }
        else {
            throw new AppException("打印类型不一致，无法打印！");
        }
        return bdcNewPrintDTO;
    }

    /**
     * @param
     * @param gzlslids 工作流id集合，多个用逗号隔开
     * @param dylx 打印类型
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/19 11:46
     * @description 请求批量审批表数据
     **/
    @GetMapping(value = "/spb/{gzlslids}/{dylx}/xml/pl")
    public String spbPrintXmlPl(@PathVariable(value = "gzlslids") String gzlslids,
                                @PathVariable(value="dylx") String dylx) {
        BdcUrlDTO bdcUrlDTO = new BdcUrlDTO();
        bdcUrlDTO.setRegisterUiUrl(registerUi);
        bdcUrlDTO.setSignImageUrl(signImageUrl);
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setBdcUrlDTO(bdcUrlDTO);
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setGzlslids(gzlslids);
        bdcPrintDTO.setPrintMode(1);
        return bdcShxxFeignService.bdPrintXml(bdcPrintDTO);
    }

    /**
     * @param gzlslids
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 查询打印类型
     * @date : 2022/6/28 11:14
     */
    @GetMapping("/dypz/dylx")
    public String queryDylxFromDypz(@NotBlank(message = "获取打印类型工作流实例id不可为空") String gzlslids) {
        Set<String> dylxSet = new HashSet<>(1);
        if (StringUtils.isNotBlank(gzlslids)) {
            String[] gzlslidArr = gzlslids.split(CommonConstantUtils.ZF_YW_DH);
            for (String gzlslid : gzlslidArr) {
                String dylx = "bdcSqSpb";
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    if (StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid()) && StringUtils.isNotBlank(bdcXmDOList.get(0).getDjxl())) {
                        BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
                        if (Objects.nonNull(bdcDjxlPzDO) && StringUtils.isNotBlank(bdcDjxlPzDO.getSpbdylx())) {
                            dylx = bdcDjxlPzDO.getSpbdylx();
                        }
                    }
                }
                dylxSet.add(dylx);
            }
        }
        if (dylxSet.size() == 1) {
            return dylxSet.stream().findFirst().get();
        }
        else {
            throw new AppException("打印类型不一致，无法打印！");
        }
    }

    /**
     * @param
     * @param gzlslids 工作流ids 多个用逗号隔开
     * @param dylx 打印类型
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/19 14:08
     * @description 批量打印发证记录
     **/
    @GetMapping(value = "/fzjl/xml/pl")
    String fzjlPrintXmlPl(@RequestParam(name = "gzlslids") String gzlslids,
                          @RequestParam(name = "dylx") String dylx) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        BdcUrlDTO bdcUrlDTO = new BdcUrlDTO();
        bdcUrlDTO.setRegisterUiUrl(registerUi);
        bdcPrintDTO.setGzlslids(gzlslids);
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setBdcUrlDTO(bdcUrlDTO);
        return bdcFzjlFeignService.fzjlPrintXml(bdcPrintDTO);
    }

    /**
     * @param zsids
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 批量打印户室图
     * @date : 2022/6/28 11:14
     */
    @GetMapping(value = "/zs/image/{dylx}/{zsids}/pl")
    public String printHstView(@PathVariable(name = "zsids") String zsids,
                               @PathVariable(name = "dylx") String dylx) {
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryZsByZsids(zsids);
        List<Map> paramList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcZsDOList)) {
            for (BdcZsDO bdcZsDO : bdcZsDOList) {
                if (StringUtils.isBlank(bdcZsDO.getBdcdyh())) {
                    continue;
                }
                String qjgldm = "";
                BdcXmDO bdcXmDO = new BdcXmDO();
                List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(bdcZsDO.getZsid());
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && null != bdcXmDOList.get(0)) {
                    bdcXmDO = bdcXmDOList.get(0);
                    if(StringUtils.isNotBlank(bdcXmDO.getXmid())) {
                        BdcXmFbQO bdcXmFbQO = new BdcXmFbQO();
                        bdcXmFbQO.setXmid(bdcXmDO.getXmid());
                        List<BdcXmFbDO> bdcXmFbDOList = bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                            qjgldm = bdcXmFbDOList.get(0).getQjgldm();
                        }
                    }
                }
                String bdcdyh = bdcZsDO.getBdcdyh();
                bdcdyh = StringUtils.replace(bdcdyh, "等", "");
                Map<String, String> param = Maps.newHashMap();
                param.put("HST", StringUtils.replace(registerUi + CommonConstantUtils.HST_URL, "{bdcdyh}", bdcdyh) + "?qjgldm=" + qjgldm);
                paramList.add(param);
            }
        }

        Map<String, List<Map>> paramMap = new HashMap<>(1);
        paramMap.put(dylx, paramList);
        return bdcPrintFeignService.print(paramMap);
    }

    /**
     * @param listZsidsStr
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 保存证书到redis
     * @date : 2022/6/28 11:14
     */
    @PostMapping("/batchzsprint")
    public String saveListZsidsToRedis(@RequestParam String listZsidsStr) {
        return bdcZsPirntFeignService.saveListZsidsToRedis(listZsidsStr);
    }

    /**
     * @param zslx
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 批量打印证书
     * @date : 2022/6/28 11:14
     */
    @GetMapping("/batchZs/{zslx}/{key}")
    String printBatchZsByRedisKey(@PathVariable(value = "zslx") String zslx,
                                  @PathVariable(value = "key") String key) {
        List<Integer> qsztList = new ArrayList<>(1);
        List<String> listZsid = bdcZsPirntFeignService.getZsidsByKey(key);
        // 只获取权属状态为现势的证书打印
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setZsidList(listZsid);
        bdcZsQO.setQsztList(qsztList);
        if (StringUtils.equals(CommonConstantUtils.DYLX_ZM, zslx)) {
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZM);
        }
        else if (StringUtils.equals(CommonConstantUtils.DYLX_ZS, zslx)) {
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZS);
        }
        else {
            bdcZsQO.setZslx(CommonConstantUtils.ZSLX_ZMD);
        }
        List<String> printZsidList = bdcZsFeignService.queryZsid(bdcZsQO);

        // 封装打印参数
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        BdcUrlDTO bdcUrlDTO = new BdcUrlDTO();
        bdcUrlDTO.setRegisterUiUrl(registerUi);
        bdcUrlDTO.setCertificateUrl(certificateUrl);
        bdcPrintDTO.setBdcUrlDTO(bdcUrlDTO);
        bdcPrintDTO.setDylx(zslx);
        bdcPrintDTO.setListZsid(printZsidList);
        return bdcZsPirntFeignService.batchZsPrintXml(bdcPrintDTO);
    }

    /**
     * @param gzlslids
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 批量打印抵押物清单
     * @date : 2022/6/28 11:14
     */
    @GetMapping(value = "/bdcdy/{gzlslids}/{dylx}/xml/pl")
    String bdcdyPrintXml(@PathVariable(value = "gzlslids") String gzlslids,
                         @PathVariable(value = "dylx") String dylx) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setGzlslids(gzlslids);
        bdcPrintDTO.setDylx(dylx);
        BdcUrlDTO bdcUrlDTO = new BdcUrlDTO();
        bdcUrlDTO.setRegisterUiUrl(registerUi);
        bdcPrintDTO.setBdcUrlDTO(bdcUrlDTO);
        return bdcBdcdyFeignService.bdcdyPrintXmlPl(bdcPrintDTO);
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/30 22:22
     * @description 获取打印内网地址
     **/
    @GetMapping("/getPrintIp")
    public String getPrintIp() {
        return printIp;
    }

    /**
     * @param
     * @return BdcLogVO
     * @author <a href="mailto:zahngxinyu@gtmap.cn">zahngxinyu</a>
     * @description 获取打印留档参数
     **/
    @GetMapping("/getDyldcs")
    public BdcLogVO getDyldcs(@RequestParam String dbkey) {
        String redisKey = CommonConstantUtils.REDIS_ZDYCX_PRINT + dbkey;
        String data = redisUtils.getStringValue(redisKey);
        return JSON.parseObject(data,BdcLogVO.class);
    }

    /**
     * 获取收件单、申请书打印类型
     * @param bdcSlPrintDTO
     */
    private void getSjdSqsDylx(BdcSlPrintDTO bdcSlPrintDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        // 打印类型为sqs
        String dylx = "";
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            if (StringUtils.isNotBlank(bdcXmDOList.get(0).getGzldyid()) && StringUtils.isNotBlank(bdcXmDOList.get(0).getDjxl())) {
                BdcDjxlPzDO bdcDjxlPzDO = bdcDjxlPzFeignService.queryBdcDjxlPzByGzldyidAndDjxl(bdcXmDOList.get(0).getGzldyid(), bdcXmDOList.get(0).getDjxl());
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQS) || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SQSPL)) {
                    if (StringUtils.isNotBlank(bdcDjxlPzDO.getSqsdylx())) {
                        dylx = bdcDjxlPzDO.getSqsdylx();
                    }
                }
                if (StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJD) || StringUtils.equals(bdcSlPrintDTO.getDylx(), CommonConstantUtils.DYLX_SJDPL)) {
                    if (StringUtils.isNotBlank(bdcDjxlPzDO.getSjddylx())) {
                        dylx = bdcDjxlPzDO.getSjddylx();
                    }
                }
            }
        }
        bdcSlPrintDTO.setDylx(StringUtils.isNotBlank(dylx) ? dylx : bdcSlPrintDTO.getDylx());
    }

}
