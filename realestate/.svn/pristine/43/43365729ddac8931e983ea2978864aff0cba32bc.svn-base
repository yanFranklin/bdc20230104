package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcCzrzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/3/26
 * @description 操作日志
 */
public interface BdcCzrzRestService {

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成操作日志(工作流事件)
     */
    @PostMapping(value = "/init/rest/v1.0/czrz/gzlslid")
    void initBdcCzrz(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "reason",required = false) String reason, @RequestParam(value = "czlx") Integer czlx, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param spxtywh 审批系统业务号
     * @param orderBy 排序方式
     * @return 操作日志信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据审批系统业务号获取操作日志
     */
    @GetMapping(value = "/init/rest/v1.0/czrz/spxtywh/{spxtywh}")
    List<BdcCzrzDO> listBdcCzrzBySpxtywh(@PathVariable(name = "spxtywh") String spxtywh, @RequestParam(value = "orderBy",required = false) String orderBy);

    /**
     * 添加不动产操作日志内容
     * @param bdcCzrzDO 操作日志信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/init/rest/v1.0/czrz")
    void addBdcCzrz(@RequestBody BdcCzrzDO bdcCzrzDO);

    /**
     * 根据操作日志ID，修改不动产操作日志内容
     * @param bdcCzrzDO 操作日志信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PutMapping(value = "/init/rest/v1.0/czrz/rzid")
    void modifyBdcCzrzByRzid(@RequestBody BdcCzrzDO bdcCzrzDO);

    /**
     * 查询不动产操作日志内容
     * @param bdcCzrzDO 操作日志信息
     * @return {@code BdcCzrzDO} 操作日志DO集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/init/rest/v1.0/czrz/list")
    List<BdcCzrzDO> listBdcCzrz(@RequestBody BdcCzrzDO bdcCzrzDO);

    /**
     * 添加删除操作日志
     * <p>原因获取流程的审批意见内容</p>
     * @param gzlslid 工作流实例ID
     * @param opinion 流程审核意见
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping(value = "/init/rest/v1.0/czrz/sclc")
    void addScCzrzWithOpinion(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name="opinion") String opinion);

    /**
     * 添加删除操作日志
     * <p>原因获取流程的审批意见内容</p>
     * @param gzlslid 工作流实例ID
     * @param opinion 流程审核意见
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @PostMapping(value = "/init/rest/v1.0/czrz/sclc/with/xmxx")
    void addScCzrzWithOpinionWithXmxx(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name="opinion") String opinion, @RequestBody BdcXmDO bdcXmDO);

}
