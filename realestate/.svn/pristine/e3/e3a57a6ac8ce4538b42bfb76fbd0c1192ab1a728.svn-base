package cn.gtmap.realestate.common.core.service.rest.register;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">chenyucheng</a>
 * @version 1.3, 2018/12/29
 * @description 对于流程一些操作的工作流事件服务汇总
 */
public interface BdcLcczxgRestService {
    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程撤销删除要撤案交易信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/zlfca", method = RequestMethod.POST)
    void zlfca(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查封流程办结后的需要发送回执单给法院
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/cfhzPdf", method = RequestMethod.POST)
    void cfhzPdf(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 没有办结之前 挂起被修的流程并锁定bdcdyh和证书
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/xzlc/gqsd", method = RequestMethod.POST)
    void xzlcGqAndSd(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName);

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修正流程 流程办结后 需要解挂和解锁被修正的流程
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/xzlc/jgjs", method = RequestMethod.POST)
    void xzlcJgAndJs(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "currentUserName") String currentUserName);

    /**
     *
     * @param processInsId
     * @param currentUserName
     * @description 修正流程删除，需要解锁和解挂流程
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/workflow/xzlc/delete", method = RequestMethod.POST)
    void zxlcDelete(@RequestParam(name="processInsId") String processInsId,@RequestParam(name="currentUserName") String currentUserName);
}

