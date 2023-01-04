package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.SjcxsjlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjgxxxzlpfResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSjptCxFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 省级平台查询服务
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/09/03
 */
@RestController
@RequestMapping("/rest/v1.0/sjpt")
public class BdcSjptController extends BaseController {
    /**
     * 省级平台查询服务
     */
    @Autowired
    private BdcSjptCxFeignService bdcSjptCxFeignService;

    /**
     * 民政婚姻查询
     *
     * @param pageable             分页对象
     * @param qlrzjh 权利人证件号
     * @return {Object} 婚姻信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/hyxx")
    public Object queryHyxx(@LayuiPageable Pageable pageable,@RequestParam("qlrzjh") String qlrzjh) {
        if (StringUtils.isBlank(qlrzjh)) {
            throw new MissingArgumentException("权利人身份证件号不能为空！");
        }
        return addLayUiCode(bdcSjptCxFeignService.listHyxxByPage(pageable, qlrzjh));
    }

    /**
     * 社会组织查询
     *
     * @param pageable             分页对象
     * @param zzmc 权利人证件号
     * @return {Object} 婚姻信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/shgx")
    public Object queryShzz(@LayuiPageable Pageable pageable, @RequestParam("zzmc") String zzmc) {
        if (StringUtils.isBlank(zzmc)) {
            throw new MissingArgumentException("社会组织名称不能为空！");
        }
        return addLayUiCode(bdcSjptCxFeignService.listShzzByPage(pageable, zzmc));
    }

    /**
     * 居民身份查询
     *
     * @param qlrzjh 权利人证件号
     * @return {Object}  {"head": {"code": "0000", "msg": "success"}, "data": {"cxjg": []}}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/jmsf")
    public Object queryJmsf(@RequestParam("qlrzjh") String qlrzjh) {
        if (StringUtils.isBlank(qlrzjh)) {
            throw new MissingArgumentException("居民证件号不能为空！");
        }
        return bdcSjptCxFeignService.queryJmsf(qlrzjh);
    }

    /**
     * 居民身份查询
     *
     * @param qlrzjh 权利人证件号
     * @return {Object} 居民身份信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/jmsfsqfk")
    public Object queryJmsfSqfk(@RequestParam("qlrzjh") String qlrzjh) {
        if (StringUtils.isBlank(qlrzjh)) {
            throw new MissingArgumentException("居民证件号不能为空！");
        }
        return addLayUiCode(bdcSjptCxFeignService.queryJmsfSqfk(qlrzjh));
    }


    /**
     * 人口基准信息
     * @param xm
     * @param zjh
     * @return
     */
    @GetMapping("/rkkjzxxcx")
    public Object queryRkkjzxxcx(@RequestParam("xm") String xm,@RequestParam("zjh") String zjh) {
        if (StringUtils.isBlank(xm) || StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("姓名或证件号不能为空！");
        }
        return bdcSjptCxFeignService.queryRkkjzxxcx(xm,zjh);
    }

    /**
     * 人口库身份核查
     * @param xm
     * @param zjh
     * @return
     */
    @GetMapping("/rkksfhc")
    public Object queryRkksfhc(@RequestParam("xm") String xm,@RequestParam("zjh") String zjh) {
        if (StringUtils.isBlank(xm) || StringUtils.isBlank(zjh)) {
            throw new MissingArgumentException("姓名或证件号不能为空！");
        }
        return bdcSjptCxFeignService.queryRkksfhc(xm,zjh);
    }


    /**
     * 信息共享质量评分查询
     * @param kssj
     * @param jssj
     * @return
     */
    @PostMapping("/xxgxzlpf")
    public SjcxsjlDTO queryXxgxzlpf(@RequestParam(value = "kssj") String kssj, @RequestParam(value = "jssj") String jssj) {
        if (StringUtils.isBlank(kssj) || StringUtils.isBlank(jssj)) {
            throw new MissingArgumentException("开始时间和结束时间不能为空！");
        }
        return bdcSjptCxFeignService.getXxgxzlpf(kssj,kssj);
    }
}
