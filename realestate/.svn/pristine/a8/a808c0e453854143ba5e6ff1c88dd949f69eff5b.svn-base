package cn.gtmap.realestate.inquiry.ui.web.rest.changzhou;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtFphFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.changzhou.BdcFpDyFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.inquiry.ui.core.dto.BdcFhplDTO;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 发票号查询控制器 <strong>常州<strong/>
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 9:46 上午 2020/11/27
 */
@RestController
@RequestMapping(value = "/rest/v1.0/changzhou/fph")
@ConfigurationProperties("changzhou.fph")
public class BdcFphDyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFphDyController.class);
    /**
     * 获取项目类别的配置
     */
    private Map<String, String> xmlbdb;

    @Autowired
    private BdcFpDyFeignService bdcFpDyFeignService;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    private BdcSlSfxmFeignService bdcSlSfxmFeignService;

    @Autowired
    private BdcXtFphFeignService bdcXtFphFeignService;

    /**
     * 查询收费信息「单个」<br>
     * 基于 sfxx 基础上额外查询 qlrmc，gzldymc 和 收费项目合计金额
     *
     * @param slbh slbh
     * @param fplb fplb 「读取配置，逗号隔开与 sfxmdm 关联」
     * @return {List} 收费信息集合
     */
    @GetMapping("/query")
    public Object queryFpcxSfxx(@RequestParam String slbh, @RequestParam String fplb) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        if (StringUtils.isBlank(fplb)) {
            throw new MissingArgumentException("fplb");
        }
        return bdcFpDyFeignService.queryFpcxSfxx(slbh, MapUtils.getString(xmlbdb, fplb));
    }

    @GetMapping("/sf/xm/new")
    public Object listBdcSlSfxmBySfxxid(@RequestParam String sfxxid, @RequestParam String fplb) {
        if (StringUtils.isBlank(sfxxid)) {
            return Collections.emptyList();
        }
        BdcSlSfxmQO bdcSlSfxmQO = new BdcSlSfxmQO();
        bdcSlSfxmQO.setSfxxid(sfxxid);
        bdcSlSfxmQO.setSfxmdm(MapUtils.getString(xmlbdb, fplb));
        List<BdcSlSfxmDO> bdcSlSfxmDOS = bdcSlSfxmFeignService.listFpcxSfxm(bdcSlSfxmQO);
        return bdcSlSfxmDOS;
    }


    /**
     * 批量查询收费信息<br>
     * 区分权利人，查询 jkfs 为 扫码支付并且 jspzfph 和 fssrfph 均为空的收费信息
     *
     * @param qlrlb qlrlb
     * @param fplb  fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @GetMapping("/list")
    public Object listFpcxSfxx(@RequestParam String qlrlb, @RequestParam String fplb) {
        if (StringUtils.isBlank(qlrlb)) {
            throw new MissingArgumentException("qlrlb");
        }
        if (StringUtils.isBlank(fplb)) {
            throw new MissingArgumentException("fplb");
        }
        return bdcFpDyFeignService.listFpcxSfxx(qlrlb, fplb);
    }

    /**
     * 批量查询收费信息<br>
     * 核对页面使用
     *
     * @param sfxxids sfxxids
     * @param fplb    fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @GetMapping("/list/hd")
    public Object listFpcxSfxxHd(@RequestParam String sfxxids, @RequestParam String fplb) {
        if (StringUtils.isBlank(sfxxids)) {
            throw new MissingArgumentException("sfxxids");
        }
        if (StringUtils.isBlank(fplb)) {
            throw new MissingArgumentException("fplb");
        }
        List<String> sfxxiList = Arrays.asList(StringUtils.split(sfxxids, CommonConstantUtils.ZF_YW_DH));
        return bdcFpDyFeignService.listFpcxSfxx(sfxxiList, fplb);
    }

    /**
     * 发票，单个打印，区分 fplb
     *
     * @param
     * @return java.lang.Object
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @GetMapping("/dy/xml/{sfxxid}")
    public Object dy(@PathVariable(name = "sfxxid") String sfxxid, @RequestParam String fplb) {
        Map<String, List<Map>> printParam = Maps.newHashMap();
        List params = Collections.singletonList(ImmutableMap.of("sfxxid", sfxxid, "fplb", fplb));
        String dylx = StringUtils.equals(fplb, CommonConstantUtils.FPLB_FSSR) ? CommonConstantUtils.DYLX_FSSR :
                CommonConstantUtils.DYLX_JSPZ;
        printParam.put(dylx, params);
        LOGGER.info("打印类型:{},传参: {}", dylx, params);
        return bdcPrintFeignService.print(printParam);
    }

    @GetMapping("/dy/xml")
    public Object dy(@RequestParam(name = "sfxxidList") List<String> sfxxidList, @RequestParam String fplb) {
        if (CollectionUtils.isEmpty(sfxxidList)) {
            throw new AppException("要打印的收费信息不能为空！");
        }
        Map<String, List<Map>> printParam = Maps.newHashMap();
        List<Map> list = Lists.newArrayList();
        sfxxidList.forEach(sfxxid -> {
            list.add(ImmutableMap.of("sfxxid", sfxxid, "fplb", fplb));
        });
        String dylx = StringUtils.equals(fplb, CommonConstantUtils.FPLB_FSSR) ? CommonConstantUtils.DYLX_FSSR :
                CommonConstantUtils.DYLX_JSPZ;
        printParam.put(dylx, list);
        LOGGER.info("打印类型:{},传参: {}", dylx, list);
        return bdcPrintFeignService.print(printParam);
    }

    /**
     * 获取发票号
     *
     * @param sfxxid sfxxid
     * @param slbh   slbh
     * @param fplb   fplb
     * @return bdcSlSfxxDOList
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping
    public Object getFph(@RequestParam String sfxxid, @RequestParam String slbh, @RequestParam String fplb) {
        if (StringUtils.isAnyBlank(sfxxid, slbh, fplb)) {
            throw new MissingArgumentException("sfxxid,slbh,fplb");
        }
        BdcSlSfxxDO bdcSlSfxxDO = bdcSlSfxxFeignService.queryBdcSlSfxxBySfxxid(sfxxid);
        List<BdcSlSfxxDO> bdcFphs = bdcXtFphFeignService.getBdcFph(Lists.newArrayList(bdcSlSfxxDO), slbh, fplb);
        if (StringUtils.equals(fplb, CommonConstantUtils.FPLB_FSSR)) {
            bdcFphs.forEach(bdcSlSfxxDO1 -> bdcSlSfxxDO1.setFph(bdcSlSfxxDO1.getFssrfph()));
        }
        if (StringUtils.equals(fplb, CommonConstantUtils.FPLB_JSPZ)) {
            bdcFphs.forEach(bdcSlSfxxDO1 -> bdcSlSfxxDO1.setFph(bdcSlSfxxDO1.getJspzfph()));
        }
        return bdcFphs;
    }

    @PostMapping("/pl")
    public String getFphPl(@RequestBody List<BdcFhplDTO> bdcFhplDTOS, @RequestParam(name = "fplb") String fplb) {
        if (CollectionUtils.isEmpty(bdcFhplDTOS)) {
            throw new AppException("要打印的收费信息不能为空！");
        }
        if (StringUtils.isBlank(fplb)) {
            throw new MissingArgumentException("fplb");
        }

        StringBuilder strBuilder = new StringBuilder();
        bdcFhplDTOS.forEach(bdcFhplDTO -> {
            List<BdcSlSfxxDO> fph = bdcXtFphFeignService.getBdcFph(Lists.newArrayList(bdcFhplDTO.getBdcSlSfxxDO()), bdcFhplDTO.getSlbh(), fplb);
            if (CollectionUtils.isNotEmpty(fph)) {
                strBuilder.append(bdcFhplDTO.getBdcSlSfxxDO().getSfxxid()).append(CommonConstantUtils.ZF_YW_DH);
            }
        });
        String result = strBuilder.toString();
        if (result.endsWith(CommonConstantUtils.ZF_YW_DH)) {
            result = result.substring(0, result.length() - CommonConstantUtils.ZF_YW_DH.length());
        }
        return result;
    }

    public Map<String, String> getXmlbdb() {
        return xmlbdb;
    }

    public void setXmlbdb(Map<String, String> xmlbdb) {
        this.xmlbdb = xmlbdb;
    }
}
