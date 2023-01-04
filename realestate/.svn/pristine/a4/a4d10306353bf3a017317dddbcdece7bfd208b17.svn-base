package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.BdcResponseDTO;
import cn.gtmap.realestate.common.core.dto.analysis.BdcXzztXxDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 限制状态查询服务
 */
public interface BdcXzztRestService {
    /**
     * version 1.0
     *
     * @param cxfs   值域为bhls/ls/xs   bhls查询结果包含已失效和有效的数据，
     *               ls查询已失效数据 xs查询有效的数据
     * @param bdcqzh 不动产权证号
     * @description 获取查封详细的信息
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/cfxx/{cxfs}/{bdcqzh}")
    BdcResponseDTO queryCfxx(@PathVariable("cxfs") String cxfs, @PathVariable("bdcqzh") String bdcqzh);


    /**
     * version 1.0
     *
     * @param cxfs   值域为bhls/ls/xs   bhls查询结果包含已失效和有效的数据，
     *               ls查询已失效数据 xs查询有效的数据
     * @param bdcqzh 不动产权证号
     * @description 获取查封详细的信息
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/dyaxx/{cxfs}/{bdcqzh}")
    BdcResponseDTO queryDyaxx(@PathVariable("cxfs") String cxfs, @PathVariable("bdcqzh") String bdcqzh);


    /**
     * version 1.0
     *
     * @param cxfs    值域为bhls/ls/xs   bhls查询结果包含已失效和有效的数据，
     *                ls查询已失效数据 xs查询有效的数据
     * @param bdcqzmh 预告证明号
     * @param qlr     权利人
     * @param zjh     证件号
     * @description 获取预告详细的信息
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/ygxx/{cxfs}")
    BdcResponseDTO queryYgxx(@PathVariable("cxfs") String cxfs, @RequestParam("bdcqzmh") String bdcqzmh,
                             @RequestParam("qlr") String qlr, @RequestParam("zjh") String zjh);


    /**
     * version 1.0
     *
     * @param xmid 项目ID
     * @return
     * @description 根据xmid查询现势限制状态
     * @date 2019/2/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/{xmid}/xzzt")
    BdcXzztXxDTO getXzztByXmid(@PathVariable("xmid") String xmid);


    /**
     * version 1.0
     *
     * @param xmidList 项目ID
     * @return
     * @description 根据xmidList 批量查询现势限制状态
     * @date 2019/2/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/xzzt/xmid")
    List<BdcXzztXxDTO> listXsXzztByXmidList(@RequestParam("xmidList") List<String> xmidList);
}
