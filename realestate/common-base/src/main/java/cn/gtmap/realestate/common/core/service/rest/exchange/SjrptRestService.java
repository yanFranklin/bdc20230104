package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @Description 安徽省金融平台
 * @Date 2022/9/29
 **/
public interface SjrptRestService {

    /**
     * 驳回互联网申请
     * @param spxtywh 审批系统业务号
     * @param reason 原因
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/sjrpt/bhhlwsq")
    void bhHlwSq(@RequestParam(name = "spxtywh") String spxtywh,@RequestParam(name = "reason") String reason,@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 推送ems信息
     * @param processInsId
     */
    @PostMapping(value = "/realestate-exchange/rest/v1.0/sjrpt/tsems/{processInsId}")
    void tsEmsxx(@PathVariable("processInsId") String processInsId);
}
