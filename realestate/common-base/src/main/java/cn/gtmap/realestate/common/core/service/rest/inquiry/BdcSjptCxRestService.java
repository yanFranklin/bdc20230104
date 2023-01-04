package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.SjcxsjlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjgxxxzlpfResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 省级平台查询服务实现类
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/09/03
 */
public interface BdcSjptCxRestService {

    /**
     * 民政婚姻查询
     *
     * @param pageable  分页信息
     * @param qlrzjh 权利人证件号
     * @return {Object} 婚姻信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/sjpt/hyxx")
    Page listHyxxByPage(Pageable pageable,
                           @RequestParam(name = "qlrzjh") String qlrzjh);


    /**
     * 社会组织查询
     *
     * @param pageable  分页信息
     * @param zzmc 组织名称
     * @return {Object} 社会组织 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/sjpt/shgx")
    Page listShzzByPage(Pageable pageable,
                           @RequestParam(name = "zzmc") String zzmc);


    /**
     * 居民身份查询
     *
     * @param qlrzjh 权利人证件号
     * @return {Object}  {"head": {"code": "0000", "msg": "success"}, "data": {"cxjg": []}}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/sjpt/jmsf")
    Object queryJmsf(@RequestParam(name = "qlrzjh") String qlrzjh);

    /**
     * 居民身份反馈查询
     *
     * @param qlrzjh 权利人证件号
     * @return {Object} 居民信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/sjpt/jmsfsqfk")
    Page queryJmsfSqfk(@RequestParam(name = "qlrzjh") String qlrzjh);

    /**
     * 人口基准信息
     * @param xm
     * @param zjh
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/sjpt/rkkjzxxcx")
    Object queryRkkjzxxcx(@RequestParam("xm") String xm,@RequestParam("zjh") String zjh);

    /**
     * 人口库身份核查
     * @param xm
     * @param zjh
     * @return
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/sjpt/rkksfhc")
    Object queryRkksfhc(@RequestParam("xm") String xm,@RequestParam("zjh") String zjh);

    /**
     * @param kssj,jssj
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 省级查询数据量
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/sjpt/getXxgxzlpf")
    SjcxsjlDTO getXxgxzlpf(@RequestParam(value = "kssj") String kssj, @RequestParam(value = "jssj") String jssj) ;
}
