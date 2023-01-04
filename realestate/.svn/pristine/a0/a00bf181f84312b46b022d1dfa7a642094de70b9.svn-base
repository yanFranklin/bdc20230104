package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.jmsf.request.JmsfCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjcxsjlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjgxxxzlpfResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSjptCxRestService;
import cn.gtmap.realestate.inquiry.service.BdcSjptCxService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 省级平台查询服务实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/09/4
 */
@RestController
public class BdcSjptCxRestController implements BdcSjptCxRestService {
    /**
     * 省级平台查询服务
     */
    @Autowired
    private BdcSjptCxService bdcSjptCxService;

    /**
     * 民政婚姻查询
     *
     * @param pageable 分页对象
     * @param qlrzjh   权利人证件号
     * @return {Page} 婚姻信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询婚姻信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", paramType = "body"),
            @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", paramType = "query")})
    public Page listHyxxByPage(Pageable pageable, @RequestParam(name = "qlrzjh", required = false) String qlrzjh) {
        HyxxCxywcsRequestDTO hyxxCxywcsRequestDTO = new HyxxCxywcsRequestDTO();
        if (StringUtils.isNotBlank(qlrzjh)) {
            hyxxCxywcsRequestDTO.setQlrzjh(qlrzjh);
        }
        return bdcSjptCxService.listBdcHyxxByPage(pageable, hyxxCxywcsRequestDTO);
    }

    /**
     * 社会组织查询
     *
     * @param pageable 分页对象
     * @param zzmc   组织名称
     * @return {Object} 社会组织信息JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询社会组织信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", paramType = "body"),
            @ApiImplicitParam(name = "zzmc", value = "权利人证件号", paramType = "query")})
    public Page listShzzByPage(Pageable pageable, @RequestParam(name = "zzmc", required = false) String zzmc) {
        return bdcSjptCxService.listBdcShzzByPage(pageable, zzmc);
    }

    /**
     * 居民身份查询申请
     *
     * @param qlrzjh   权利人证件号
     * @return {Object}  {"head": {"code": "0000", "msg": "success"}, "data": {"cxjg": []}}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "居民身份查询申请")
            @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", paramType = "query")
    public Object queryJmsf(@RequestParam(name = "qlrzjh", required = false) String qlrzjh) {
        JmsfCxywcsRequestDTO jmsfCxywcsRequestDTO = new JmsfCxywcsRequestDTO();
        if (StringUtils.isNotBlank(qlrzjh)) {
            jmsfCxywcsRequestDTO.setSfzh(qlrzjh);
        }
        return bdcSjptCxService.queryJmsf(jmsfCxywcsRequestDTO);
    }

    /**
     * 居民身份申请反馈
     *
     * @param qlrzjh   权利人证件号
     * @return {Page} 居民信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "居民身份申请反馈")
    @ApiImplicitParam(name = "qlrzjh", value = "权利人证件号", paramType = "query")
    public Page queryJmsfSqfk(@RequestParam(name = "qlrzjh", required = false) String qlrzjh) {
        JmsfCxywcsRequestDTO jmsfCxywcsRequestDTO = new JmsfCxywcsRequestDTO();
        if (StringUtils.isNotBlank(qlrzjh)) {
            jmsfCxywcsRequestDTO.setSfzh(qlrzjh);
        }
        return bdcSjptCxService.queryJmsfSqfk(jmsfCxywcsRequestDTO);
    }

    /**
     * 人口基准信息
     *
     * @param xm
     * @param zjh
     * @return
     */
    @Override
    public Object queryRkkjzxxcx(String xm, String zjh) {
        return bdcSjptCxService.listRkkjzxxByPage(xm,zjh);
    }

    /**
     * 人口库身份核查
     *
     * @param xm
     * @param zjh
     * @return
     */
    @Override
    public Object queryRkksfhc(String xm, String zjh) {
        return bdcSjptCxService.listRkksfhc(xm,zjh);
    }

    /**
     * @param kssj,jssj
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 省级查询数据量
     */
    @Override
    public SjcxsjlDTO getXxgxzlpf(@RequestParam(value = "kssj") String kssj, @RequestParam(value = "jssj") String jssj) {
        return bdcSjptCxService.getXxgxzlpf(kssj,jssj);
    }

}
