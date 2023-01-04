package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.hyxx.request.HyxxCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.jmsf.request.JmsfCxywcsRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjcxsjlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.SjgxxxzlpfResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 省级平台查询逻辑接口定义
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/09/03
 */
public interface BdcSjptCxService {

    /**
     * 民政婚姻查询
     *
     * @param pageable 分页对象
     * @param hyxxCxywcsRequestDTO 婚姻信息请求对象
     * @return {Page} 婚姻信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Page listBdcHyxxByPage(Pageable pageable, HyxxCxywcsRequestDTO hyxxCxywcsRequestDTO);

    /**
     * 社会组织信息查询
     *
     * @param zzmc  社会组织名称
     * @return {Object} 社会组织信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Page listBdcShzzByPage(Pageable pageable, String zzmc);

    /**
     * 居民身份信息查询
     *
     * @param jmsfCxywcsRequestDTO  居民身份请求对象
     * @return {Object}  {"head": {"code": "0000", "msg": "success"}, "data": {"cxjg": []}}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Object queryJmsf(JmsfCxywcsRequestDTO jmsfCxywcsRequestDTO);

    /**
     * 居民身份信息查询
     *
     * @param jmsfCxywcsRequestDTO  居民身份请求对象
     * @return {Object} 居民身份信息 JSON
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Page queryJmsfSqfk(JmsfCxywcsRequestDTO jmsfCxywcsRequestDTO);

    /**
     * 人口基准信息
     * @param xm
     * @param zjh
     * @return
     */
    public Object listRkkjzxxByPage(String xm, String zjh);

    /**
     * 人口库身份核查
     * @param xm
     * @param zjh
     * @return
     */
    public Object listRkksfhc(String xm, String zjh);

    /**
     * @param kssj,jssj
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 省级查询数据量
     */
    public SjcxsjlDTO getXxgxzlpf(String kssj, String jssj);
}
