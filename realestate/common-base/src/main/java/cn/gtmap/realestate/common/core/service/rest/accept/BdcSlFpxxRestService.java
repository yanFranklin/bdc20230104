package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFpxxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/5/11
 * @description 不动产受理发票信息Rest服务
 */
public interface BdcSlFpxxRestService {
    /**
     * 根据收费信息ID获取不动产受理发票信息
     * @param sfxxid 收费信息ID
     * @return 不动产受理发票信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/fpxx/{sfxxid}")
    List<BdcSlFpxxDO> queryBdcSlFpxxBySfxxid(@PathVariable(value = "sfxxid") String sfxxid);

    /**
     * 获取发票信息，并上传发票信息
     * @param sfxxid 收费信息ID
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/fpxx/upload")
    void getFpxxAndUploadFpxx(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value="gzlslid") String gzlslid);

    /**
     * 开具发票
     * @param sfxxid 收费信息
     * @param qlrlb 权利人类别
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/fpxx/inovice")
    void inovice(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value="gzlslid") String gzlslid);

    /**
     * 获取电子发票号
     * @param sfxxid  收费信息ID
     * @param userCode 用户code
     * @return 电子票号
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/fpxx/eticketNum")
    String queryDzph(@RequestParam(value = "sfxxid") String sfxxid, @RequestParam(value = "userCode") String userCode);

    /**
     * 查询缴款情况成功，并自动执行获取发票号、开具发票信息功能
     * @param processInsId 工作流实例ID
     * @param currentUserName 当前用户名称
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/fpxx/auto/exec")
    void queryJkqkAndAutoExec(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName);

    /**
     * 保存不动产受理发票信息
     * @param bdcSlFpxxDO 发票信息DO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/fpxx")
    void saveBdcSlFpxx(@RequestBody BdcSlFpxxDO bdcSlFpxxDO);
}
