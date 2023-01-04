package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.etl.HtbaxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.yth.YthTsyclpDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/10.
 * @description 盐城一体化推送服务
 */
public interface YanchengYthRestService {

    /**
     * 推送一体化受理信息
     *
     * @param processInsId 工作流实例ID
     * @return java.lang.Object 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/tsslxx")
    Object ythTsSlxx(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 推送一体化审核信息
     *
     * @param processInsId 工作流实例ID
     * @return java.lang.Object 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/tsshxx")
    Object ythTsShxx(@RequestParam("processInsId") String processInsId);

    /**
     * 一体化推送发证信息
     *
     * @param processInsId 工作流实例ID
     * @return java.lang.Object 推送结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/tsfzxx")
    Object ythTsFzxx(@RequestParam("processInsId") String processInsId);

    /**
     * 推送一体化预测楼盘信息
     *
     * @param bdcdyhList
     * @return CommonResponse
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/tsyclp")
    CommonResponse ythTsyclp(@RequestBody YthTsyclpDTO bdcdyhList);

    /**
     * 登记删除业务，通知一体化
     *
     * @param processInsId gzlslid
     * @Date 2020/12/4
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/deletettzyth")
    CommonResponse deleteTzYth(@RequestParam("processInsId") String processInsId, @RequestParam("reason") String reason);

    /**
     * 一体化平台推送合同的相关信息，备案接受
     *
     * @param htbaxxDTO 合同备案信息
     * @return
     * @Date 2021/4/15
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/htbajs")
    Object ythHtbajs(@RequestBody HtbaxxDTO htbaxxDTO);

    /**
     * 一体化平台推送合同的相关信息，备案撤销
     *
     * @param htbaxxDTO 合同备案信息
     * @return
     * @Date 2021/4/15
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/yancheng/yth/htbacx")
    Object ythHtbacx(@RequestBody HtbaxxDTO htbaxxDTO);
}
