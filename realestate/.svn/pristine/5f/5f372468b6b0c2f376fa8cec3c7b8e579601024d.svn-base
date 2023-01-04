package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.realestate.certificate.core.service.BdcDzzzMlxxService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照查看显示")
@RequestMapping(value = "/realestate-e-certificate")
public class BdcDzzzShowController extends DzzzController {

    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;

    @RequestMapping(value = "/showPdf", method = RequestMethod.GET)
    public void showPdf(HttpServletResponse response, String fid) {

        response.setContentType("application/pdf");
        try (ByteArrayInputStream in = new ByteArrayInputStream(bdcDzzzFileConfigService.download(fid));
             OutputStream out = response.getOutputStream()) {
            byte[] b = new byte[512];
            while ((in.read(b)) != -1) {
                out.write(b);
            }
            out.flush();
        } catch (IOException e) {
            logger.error("BdcDzzzShowController:showPdf:IOException:" + e.getMessage());
        } catch (Exception e) {
            logger.error("BdcDzzzShowController:showPdf:Exception:" + e.getMessage());
        }
    }

}
