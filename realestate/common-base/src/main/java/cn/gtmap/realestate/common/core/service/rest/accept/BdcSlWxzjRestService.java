package cn.gtmap.realestate.common.core.service.rest.accept;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/22
 * @description 不动产受理维修资金信息rest服务
 */
public interface BdcSlWxzjRestService {

    /**
     * 维修资金支付通知
     *
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return 通知结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/wxzj/tz")
    Object noticeWxzj(@RequestParam(name = " xmid") String xmid, @RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 生成维修资金收费信息
     *
     * @param bdcSlWxjjxxDO 不动产维修基金信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/wxzj/scwxjjxx")
    void generateWxzjXmxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO);

    /**
     * 根据维修基金信息DO获取不动产受理维修基金信息
     *
     * @param bdcSlWxjjxxDO 维修基金信息DO
     * @return 受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/wxzj/queryWxjjxx")
    List<BdcSlWxjjxxDO> queryBdcSlWxjjxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO);

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDO 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 维修基金信息
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/wxzj/insertWxjjxx")
    BdcSlWxjjxxDO insertBdcSlWxjjxxDO(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO);

    /**
     * 新增不动产受理维修基金信息
     * @param bdcSlWxjjxxDOList 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 维修基金信息
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/wxzj/insertBatchWxjjxx")
    void insertBatchBdcSlWxjjxxDO(@RequestBody List<BdcSlWxjjxxDO>  bdcSlWxjjxxDOList);

    /**
     * 根据维修基金信息ID删除不动产受理维修基金信息
     * @param wxjjxxid 维修基金信息ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 删除结果
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/wxzj/deleteWxjjxxByWxjjxxid")
    Integer deleteWxjjxxByWxjjxxid(@RequestParam(name = "wxjjxxid") String wxjjxxid);

    /**
     * 根据工作流实例ID删除不动产受理维修基金信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 删除结果
     */
    @GetMapping(value = "/realestate-accept/rest/v1.0/wxzj/deleteWxjjxxByGzlslid")
    Integer deleteWxjjxxByGzlslid(@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 更新不动产受理维修基金信息
     * @param bdcSlWxjjxxDO 不动产受理维修基金信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 更新情况
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/wxzj/updateWxjjxx")
    int updateBdcSlWxjjxx(@RequestBody BdcSlWxjjxxDO bdcSlWxjjxxDO);
}
