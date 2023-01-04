package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.realestate.common.core.service.rest.etl.ThirdPartyRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@Api(tags = "交易")
public class ThirdPartyOrderRestController implements ThirdPartyRestService {

    @Autowired
    private StorageClientMatcher storageClient;

    /**
     * @param fileId 文件id
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据文件id返回图片
     */
    @Override
    public void getFileInfoByFileId(String fileId, HttpServletResponse response) {
        response.setContentType("image/jepg");
        try {
            OutputStream out = response.getOutputStream();
            MultipartDto download = storageClient.download(fileId);
            response.setHeader("Content-disposition", "p_w_upload;filename=" + new String(download.getName().getBytes("UTF-8"), "ISO-8859-1"));
            if (download != null) {
                out.write(download.getData());
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
