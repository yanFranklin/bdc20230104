package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLscsDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyPrintDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyUrlDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyXtLscsFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/07/27
 * @description 查询子系统打印
 */
@RestController
@RequestMapping("/rest/v1.0/print")
public class ZrzyPrintController extends BaseController {
    @Autowired
    private ZrzyXtLscsFeignService zrzyXtLscsFeignService;
    @Autowired
    private ZrzyPrintFeignService zrzyPrintFeignService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;
    /**
     * 档案地址
     */
    @Value("${url.archive:}")
    protected String archiveUrl;
    /**
     * 审核登簿服务地址
     */
    @Value("${url.register:}")
    protected String registerUrl;
    /**
     * 审核登簿UI服务地址
     */
    @Value("${url.register-ui:}")
    protected String registerUiUrl;
    /**
     * 证书归档服务地址
     */
    @Value("${url.certificate:}")
    protected String certificateUrl;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 金额单位
     */
    @Value("${jedw:万元}")
    private String jedw;


    @Value("#{'${cg:}'.split(',')}")
    private List<String> cgList;


    /**
     * 保存打印注销清册数据查询参数
     *
     * @param xmDOList 项目查询参数
     * @return {String} 缓存到临时参数表的标识
     */
    @PostMapping("/zxqc/param")
    public String saveZxqcPrintParam(@RequestBody List<BdcXmDO> xmDOList) {
        if (CollectionUtils.isEmpty(xmDOList)) {
            throw new MissingArgumentException("未定义要打印的内容");
        }

        List<ZrzyXtLscsDO> lscsDOList = new ArrayList<>(xmDOList.size());
        String paramKey = UUIDGenerator.generate16();
        for (BdcXmDO bdcXmDO : xmDOList) {
            ZrzyXtLscsDO lscsDO = new ZrzyXtLscsDO();
            lscsDO.setCsmc(paramKey);
            lscsDO.setCsz(bdcXmDO.getXmid());
            lscsDO.setSfsc(CommonConstantUtils.SF_S_DM);
            lscsDOList.add(lscsDO);
        }

        zrzyXtLscsFeignService.addValues(lscsDOList);
        return paramKey;
    }

    /**
     * 获取注销清册的打印数据
     *
     * @param lscsmc 临时参数名称
     * @return {String} 打印XML数据
     */
    @GetMapping("/zxqc/{lscsmc}/xml")
    public String zxqc(@PathVariable("lscsmc") String lscsmc) {
        if (StringUtils.isBlank(lscsmc)) {
            throw new MissingArgumentException("未定义要打印的内容");
        }

        Map<String, List<Map>> paramMap = new HashMap();
        HashMap map = new HashMap();
        map.put("lscsmc", lscsmc);

        paramMap.put("zxqc", Collections.singletonList(map));
        return zrzyPrintFeignService.print(paramMap);
    }

    /**
     * 保存自定义查询打印参数
     *
     * @param paramList 参数（默认选中行所有字段数据）
     * @return {String} 缓存到Redis key
     */
    @PostMapping("/zdycx/param")
    public String saveZdycxPrintParam(@RequestBody List<Map<String, Object>> paramList) {
        if (CollectionUtils.isEmpty(paramList)) {
            throw new MissingArgumentException("自定义查询打印缺失参数");
        }

        String redisKey = CommonConstantUtils.REDIS_ZDYCX_PRINT + UUIDGenerator.generate16();
        return redisUtils.addStringValue(redisKey, JSON.toJSONString(paramList), 600);
    }

    /**
     * 获取自定义查询打印XML数据
     *
     * @param dylx     打印类型
     * @param redisKey 参数缓存到Redis key
     * @return {String} 打印XML数据
     */
    @GetMapping("/zdycx/{dylx}/{redisKey}/xml")
    public String getZdycxPrintXml(@PathVariable("dylx") String dylx, @PathVariable("redisKey") String redisKey) {
        if (StringUtils.isBlank(dylx) || StringUtils.isBlank(redisKey)) {
            throw new MissingArgumentException("自定义查询打印缺失参数");
        }

        String data = redisUtils.getStringValue(redisKey);
        if (StringUtils.isBlank(data)) {
            throw new AppException("自定义查询打印失败：未获取到要打印的数据");
        }

        List<Map> paramList = JSON.parseArray(data, Map.class);
        List<Map> newParamList = new ArrayList<>(paramList.size());

        for (Map<String, Object> param : paramList) {
            Map<String, Object> newParam = new HashMap<>();
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                // 将参数键名转小写（自定义查询页面字段名称都是大写）
                newParam.put(StringUtils.lowerCase(entry.getKey()), entry.getValue());
            }
            newParamList.add(newParam);
        }

        Map<String, List<Map>> paramMap = new HashMap<>(1);
        paramMap.put(dylx, newParamList);

        return zrzyPrintFeignService.print(paramMap);
    }


    /**
     * @param zsid 证书ID
     * @param zslx 证书打印类型
     * @return String 证书打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 单个证书打印
     */
    @GetMapping("/zs/{zsid}/{zslx}/singleXml")
    String printZs(@PathVariable(name = "zsid") String zsid,
                   @PathVariable(name = "zslx") String zslx,
                   @RequestParam(name = "userName", required = false) String userName,
                   @RequestParam(name = "processInsId", required = false) String processInsId) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(zsid) || StringUtils.isBlank(zslx)) {
            throw new MissingArgumentException("缺失打印证书的ID或者证书打印类型");
        }
        // 封装打印参数
        ZrzyPrintDTO zrzyPrintDTO = new ZrzyPrintDTO();
        zrzyPrintDTO.setZrzyUrlDTO(bdcUrl());
        zrzyPrintDTO.setDylx(zslx);
        zrzyPrintDTO.setZsid(zsid);
        zrzyPrintDTO.setGzlslid(processInsId);
        if (StringUtils.isNotBlank(userName)) {
            zrzyPrintDTO.setJbr(URLDecoder.decode(userName, "UTF-8"));
        }
        return zrzyPrintFeignService.singleZsPrintXml(zrzyPrintDTO);
    }

    /**
     * @return BdcUrlDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前系统配置的url参数
     */
    public ZrzyUrlDTO bdcUrl() {
        ZrzyUrlDTO zrzyUrlDTO = new ZrzyUrlDTO();
        zrzyUrlDTO.setRegisterUrl(registerUrl);
        zrzyUrlDTO.setArchiveUrl(archiveUrl);
        zrzyUrlDTO.setCertificateUrl(certificateUrl);
        zrzyUrlDTO.setSignImageUrl(signImageUrl);
        zrzyUrlDTO.setRegisterUiUrl(registerUiUrl);
        zrzyUrlDTO.setJedw(jedw);
        zrzyUrlDTO.setCgList(cgList);
        return zrzyUrlDTO;
    }
}
