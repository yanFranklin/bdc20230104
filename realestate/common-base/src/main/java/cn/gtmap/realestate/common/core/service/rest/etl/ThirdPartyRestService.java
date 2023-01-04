package cn.gtmap.realestate.common.core.service.rest.etl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 交易三方系统
 */
public interface ThirdPartyRestService {

    /**
     * @param fileId 文件id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据文件id返回图片
     */
    @GetMapping("/realestate-etl/rest/v1.0/third/party/photo/info")
    void getFileInfoByFileId(@RequestParam(value = "fileId") String fileId, HttpServletResponse response);
}
